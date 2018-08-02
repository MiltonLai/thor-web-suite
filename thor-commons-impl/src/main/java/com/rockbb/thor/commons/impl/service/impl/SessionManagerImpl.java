package com.rockbb.thor.commons.impl.service.impl;

import com.rockbb.thor.commons.api.dto.AdminUserDTO;
import com.rockbb.thor.commons.api.dto.BaseUserDTO;
import com.rockbb.thor.commons.api.dto.ResultDTO;
import com.rockbb.thor.commons.api.dto.SessionDTO;
import com.rockbb.thor.commons.api.dto.SessionLogDTO;
import com.rockbb.thor.commons.api.dto.UserDTO;
import com.rockbb.thor.commons.api.service.AdminUserDTOService;
import com.rockbb.thor.commons.api.service.ConfigDTOService;
import com.rockbb.thor.commons.api.service.SessionManager;
import com.rockbb.thor.commons.api.service.UserDTOService;
import com.rockbb.thor.commons.impl.mapper.SessionLogMapper;
import com.rockbb.thor.commons.impl.mapper.SessionMapper;
import com.rockbb.thor.commons.lib.utilities.SecureUtil;
import com.rockbb.thor.commons.lib.utilities.StaticConfig;
import com.rockbb.thor.commons.lib.utilities.StringUtil;
import com.rockbb.thor.commons.lib.web.ArgGen;
import com.rockbb.thor.commons.lib.web.Pager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository("sessionManager")
public class SessionManagerImpl implements SessionManager
{
    private static Logger logger = LoggerFactory.getLogger(SessionManagerImpl.class);

    @Resource(name="sessionMapper")
    private SessionMapper sessionMapper;
    @Resource(name="sessionLogMapper")
    private SessionLogMapper sessionLogMapper;
    @Resource(name = "adminUserDTOService")
    private AdminUserDTOService adminUserDTOService;
    @Resource(name="configDTOService")
    private ConfigDTOService configDTOService;
    @Resource(name = "userDTOService")
    private UserDTOService userDTOService;

    private long cleanFlag;

    @Override
    public ResultDTO userLogin(
            String sid,
            String tel,
            String password,
            String code,
            int autologin,
            String ip,
            String userAgent,
            String openId) {

        if (StringUtils.isEmpty(tel)) {
            return ResultDTO.error("手机号不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            return ResultDTO.error("密码不能为空");
        }

        ResultDTO result = userDTOService.checkPassword(sid, ip, userAgent, tel, password);
        if (result.isFailed()) {
            return result;
        }
        // 更新session, 登入用户
        update(sid, result.getKey(), autologin, ip);
        return ResultDTO.success();
    }

    @Override
    public void userLogout(String sid) {
        update(sid, UserDTO.ANONYMOUS_UID, 0, "");
    }

    @Override
    public ResultDTO adminLogin(String sid, String email, String password, int autologin, String ip, String userAgent) {
        if (password == null || password.length() == 0) {
            return ResultDTO.error("密码不能为空");
        }

        ResultDTO result = adminUserDTOService.checkPassword(sid, ip, userAgent, email, password);

        if (result.isFailed()) {
            return result;
        } else {
            // 更新session, 登入用户
            update(sid, result.getKey(), autologin, ip);
            return ResultDTO.success();
        }
    }

    @Override
    public void adminLogout(String sid) {
        update(sid, AdminUserDTO.ANONYMOUS_UID, 0, "");
    }

    @Override
    public SessionDTO create(String app, String secure1, String secure2, String secure3, String timestamp, String hash) {
        if (secure1.length() == 0 || secure2.length() == 0 || secure3.length() == 0 || hash.length() == 0) {
            return null;
        }
        secure1 = StringUtil.shorten(secure1, 255);
        secure2 = StringUtil.shorten(secure2, 255);
        secure3 = StringUtil.shorten(secure3, 255);
        String token = configDTOService.getString("sys", "app_boot_token");
        String dummy = SecureUtil.md5(token + secure1 + secure2 + secure3 + timestamp);
        if (!dummy.equals(hash)) {
            return null;
        }
        return create(app, secure1, secure2, secure3, true);
    }

    /*
     * 基础方法
     */

    @Override
    public void cleanup() {
        if (System.currentTimeMillis() - 10 * 1000L > cleanFlag) {// To prevent it from being invoked too frequently
            cleanFlag = System.currentTimeMillis();
            long timelimit = System.currentTimeMillis() - 3600 * 1000;
            long before = sessionMapper.countAll();
            long count = sessionMapper.cleanExpired(timelimit, timelimit - 3600L * 1000 * 24 * 28);
            logger.debug("Sessions: " + before + " cleand: " + count);
        }
    }

    @Override
    public long size() {
        return sessionMapper.countAll();
    }

    @Override
    public SessionDTO getRaw(String sid) {
        if (sid != null && sid.length() > 0) {
            SessionDTO session = sessionMapper.select(sid);
            if (session != null)
                return session;
            else
                logger.debug("Session is null: " + sid);
        }
        return null;
    }

    @Override
    public SessionDTO get(String sid, String secure, long timestamp, String ip, boolean renew) {
        SessionDTO session = null;
        if (sid != null && sid.length() > 0) {
            session = sessionMapper.select(sid);
            if (session == null) {
                logger.debug("Session is null: " + sid);
            } else if (validate(session, timestamp, secure)) {
                if (renew) {
                    // 如果不活跃时间超过1小时(自动登录), 记录日志
                    if (!session.getUserId().equals(BaseUserDTO.ANONYMOUS_UID)
                            && session.getUpdatedAt() + 1000L*3600 < System.currentTimeMillis()) {
                        SessionLogDTO sessionLog = new SessionLogDTO().initialize();
                        sessionLog.setSessionId(sid);
                        sessionLog.setUserId(session.getUserId());
                        sessionLog.setIp(ip);
                        sessionLog.setAutologin(session.getAutologin());
                        sessionLog.setApp(session.getApp());
                        sessionLog.setLanguage(session.getLanguage());
                        sessionLog.setSecure1(session.getSecure1());
                        sessionLog.setSecure2(session.getSecure2());
                        sessionLog.setSecure3(session.getSecure3());
                        sessionLogMapper.insert(sessionLog);
                    }
                    session.setUpdatedAt(System.currentTimeMillis());
                    sessionMapper.update(session);
                }
            } else {
                logger.debug("Session expired or unmatched: " + session.getId());
                sessionMapper.delete(sid);
                session = null;
            }
        }
        return session;
    }

    @Override
    public int delete(String sid) {
        return sessionMapper.delete(sid);
    }

    @Override
    public String reset(String sid) {
        SessionDTO session = sessionMapper.select(sid);
        if (session != null) {
            session.setStartedAt(System.currentTimeMillis());
            session.setUpdatedAt(System.currentTimeMillis());
            session.setUserId(BaseUserDTO.ANONYMOUS_UID);
            session.setLanguage(0);
            session.setAutologin(0);
            session.setToken(SecureUtil.uuid());
            sessionMapper.update(session);
            return session.getToken();
        }
        return null;
    }

    @Override
    public String resetToken(String sid) {
        SessionDTO session = sessionMapper.select(sid);
        if (session != null) {
            session.setToken(UUID.randomUUID().toString().replace("-", ""));
            sessionMapper.update(session);
            return session.getToken();
        }
        return null;
    }

    @Override
    public List<SessionLogDTO> listLogs(
            Pager pager,
            String userId,
            String sessionId,
            String ip,
            Date timeBefore,
            Date timeAfter) {
        pager.setSorts(SessionLogMapper.ORDERBY);
        ArgGen args = new ArgGen()
                .addNotEmpty("userId", userId)
                .addNotEmpty("sessionId", sessionId)
                .addLike("ip", ip)
                .addNotEmpty("timeBefore", timeBefore)
                .addNotEmpty("timeAfter", timeAfter);

        return sessionLogMapper.list(pager, args.getArgs());
    }

    @Override
    public long countLogs(
            String userId,
            String sessionId,
            String ip,
            Date timeBefore,
            Date timeAfter) {
        ArgGen args = new ArgGen()
                .addNotEmpty("userId", userId)
                .addNotEmpty("sessionId", sessionId)
                .addLike("ip", ip)
                .addNotEmpty("timeBefore", timeBefore)
                .addNotEmpty("timeAfter", timeAfter);

        return sessionLogMapper.count(args.getArgs());
    }

    @Override
    public List<SessionDTO> list(Pager pager, String userId) {
        pager.setSorts(SessionMapper.ORDERBY);
        return sessionMapper.list(pager, new ArgGen().addNotEmpty("userId", userId).getArgs());
    }

    public static boolean validate(SessionDTO session, long timestamp, String secure) {
        if (session == null || isExpired(session.getAutologin(), session.getUpdatedAt()))
            return false;

        return SecureUtil.md5(session.getSecure1() + session.getSecure2() + session.getSecure3() + timestamp).equals(secure);
    }

    public static boolean isExpired(int autologin, long updatedAt) {
        if (autologin == 0)
            return (System.currentTimeMillis() - 3600L * 1000 - updatedAt) > 0;
        else
            return (System.currentTimeMillis() - 3600L * 1000 * 24 * 14 - updatedAt) > 0;
    }

    /**
     * 创建session
     *
     */
    private SessionDTO create(String app, String secure1, String secure2, String secure3, boolean persistent) {
        SessionDTO session = new SessionDTO();
        session.initialize();
        session.setUserId(UserDTO.ANONYMOUS_UID);
        session.setApp(app);
        session.setSecure1(secure1);
        session.setSecure2(secure2);
        session.setSecure3(secure3);

        if (persistent) {
            sessionMapper.insert(session);
        }
        return session;
    }

    /**
     * 更新session并记录日志
     */
    private void update(String sid, String uid, int autologin, String ip) {
        SessionDTO session = sessionMapper.select(sid);
        if (session != null) {
            session.setUserId(uid);
            session.setAutologin(autologin);
            sessionMapper.update(session);

            if (!uid.equals(BaseUserDTO.ANONYMOUS_UID)) { // 用户不是匿名用户时, 记录日志
                SessionLogDTO sessionLog = new SessionLogDTO().initialize();
                sessionLog.setSessionId(sid);
                sessionLog.setUserId(uid);
                sessionLog.setIp(ip);
                sessionLog.setAutologin(autologin);
                sessionLog.setApp(session.getApp());
                sessionLog.setLanguage(session.getLanguage());
                sessionLog.setSecure1(session.getSecure1());
                sessionLog.setSecure2(session.getSecure2());
                sessionLog.setSecure3(session.getSecure3());
                sessionLogMapper.insert(sessionLog);
            }
        }
    }

}

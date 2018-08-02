package com.rockbb.thor.admin.adapter;


import com.rockbb.thor.admin.common.SessionCookieHandler;
import com.rockbb.thor.commons.api.dto.SessionDTO;
import com.rockbb.thor.commons.api.service.ConfigDTOService;
import com.rockbb.thor.commons.api.service.SessionManager;
import com.rockbb.thor.commons.lib.net.InetAddressUtil;
import com.rockbb.thor.commons.lib.utilities.SecureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Created by Milton on 2015/7/18 at 23:10.
 */
@Service("sessionAdapter")
public class SessionAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SessionAdapter.class);

    @Resource(name = "sessionManager")
    private SessionManager sessionManager;
    @Resource(name = "configDTOService")
    private ConfigDTOService configDTOService;

    public SessionDTO get(String sid, String secure1, String secure2, String secure3, String ip, boolean renew) {
        long timestamp = System.currentTimeMillis();
        String secure = SecureUtil.md5(secure1 + secure2 + secure3 + timestamp);
        return sessionManager.get(sid, secure, timestamp, ip, renew);
    }

    public SessionDTO get(HttpServletRequest req, HttpServletResponse res, String ip) {
        String sid = SessionCookieHandler.getSid(req);
        // 从session池中获取session, 注: 当cookie为空时, 不创建新session
        String secure1 = req.getHeader("User-Agent");
        String secure2 = "n/a"; // oracle不支持空字符串
        String secure3 = "n/a";
        long timestamp = System.currentTimeMillis();
        String secure = SecureUtil.md5(secure1 + secure2 + secure3 + timestamp);

        SessionDTO session = null;
        if (sid != null) {
            session = sessionManager.get(sid, secure, timestamp, ip, true);
            if (session == null) {
                logger.debug("Got null when fetching session for sid: {}", sid);
                String token = configDTOService.getString("sys", "app_boot_token");
                secure = SecureUtil.md5(token + secure1 + secure2 + secure3 + timestamp);
                session = sessionManager.create("admin", secure1, secure2, secure3, String.valueOf(timestamp), secure);
                sid = session.getId();
            }
        } else {
            sid = java.util.UUID.randomUUID().toString();
            session = new SessionDTO();
            session.initialize();
        }
        SessionCookieHandler.setSid(res, sid);
        return session;
    }
}

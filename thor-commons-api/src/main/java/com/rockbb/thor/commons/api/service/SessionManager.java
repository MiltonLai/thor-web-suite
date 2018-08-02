package com.rockbb.thor.commons.api.service;

import com.rockbb.thor.commons.api.dto.ResultDTO;
import com.rockbb.thor.commons.api.dto.SessionDTO;
import com.rockbb.thor.commons.api.dto.SessionLogDTO;
import com.rockbb.thor.commons.lib.web.Pager;

import java.util.Date;
import java.util.List;

public interface SessionManager {
	/**
	 * Cleanup the session pool
	 */
	void cleanup();

	/**
	 *
	 * @return session pool size
	 */
	long size();

	/**
	 * 仅供可信赖的内部调用, 直接根据sid获取session, 无附加操作
	 *
	 * @param sid sid
	 * @return session
	 */
	SessionDTO getRaw(String sid);

	/**
	 * session 同步的核心方法
	 *
	 * @param renew			true: update the session time if the session exists and is not null
	 * @return session
	 */
	SessionDTO get(String sid, String secure, long timestamp, String ip, boolean renew);

	/**
	 * Delete a session
	 *
	 * @param sid sid
	 */
	int delete(String sid);

	/**
	 * Reset the session, but keep the sid
	 *
	 * @param sid sid
	 * @return token, null on non-exist
	 */
	String reset(String sid);

	/**
	 * Reset the token only
	 *
	 * @param sid sid
	 * @return token, null on non-exist
	 */
	String resetToken(String sid);

	/**
	 * 管理员登录
	 */
	ResultDTO adminLogin(String sid, String email, String password, int autologin, String ip, String userAgent);

	/**
	 * 管理员登出
	 */
	void adminLogout(String sid);

	/**
	 * 用户登录
	 *
	 * @param sid session ID
	 * @param tel cellphone number
	 * @param password password
	 * @param code 短信验证码code, null:不校验
	 * @param autologin 0:no, 1:yes
	 * @param ip remote IP address
	 * @param openId 微信ID, null:不绑定
	 */
	ResultDTO userLogin(
            String sid,
            String tel,
            String password,
            String code,
            int autologin,
            String ip,
            String userAgent,
            String openId);

	/**
	 * 用户登出
	 *
	 * @param sid session ID
	 */
	void userLogout(String sid);

	/**
	 * 创建Session
	 */
	SessionDTO create(String app, String secure1, String secure2, String secure3, String timestamp, String hash);

	/**
	 * 读取登录日志列表
	 */
	List<SessionLogDTO> listLogs(
            Pager pager,
            String userId,
            String sessionId,
            String ip,
            Date timeBefore,
            Date timeAfter);

	/**
	 * 统计登录日志数量
	 */
	long countLogs(
            String userId,
            String sessionId,
            String ip,
            Date timeBefore,
            Date timeAfter);

	/**
	 * 读取session列表
	 */
	List<SessionDTO> list(Pager pager, String userId);

}
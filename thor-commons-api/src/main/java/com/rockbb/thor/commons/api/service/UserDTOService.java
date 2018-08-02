package com.rockbb.thor.commons.api.service;

import java.util.List;

import com.rockbb.thor.commons.api.dto.ResultDTO;
import com.rockbb.thor.commons.api.dto.UserDTO;
import com.rockbb.thor.commons.lib.web.Pager;

public interface UserDTOService {

    /**
     * 删除用户, 仅在用户初注册未交易时可以
     */
    ResultDTO delete(String id);

    int update(UserDTO dto);

    UserDTO get(String id);

    UserDTO get(String id, boolean withProfile, boolean withAccount);

    List<UserDTO> get(List<String> ids, boolean withProfile, boolean withAccount);

    /**
     *
     * @param name 用户名, like, null:n/a
     * @param realName 真实姓名, like, null:n/a
     * @param email Email, like, null:n/a
     * @param cellphone 手机号, like, null:n/a
     */
    List<UserDTO> list(
            Pager pager,
            boolean withProfile,
            boolean withAccount,
            String name,
            String realName,
            String email,
            String cellphone);

    /**
     *
     * @param name 用户名, like, null:n/a
     * @param realName 真实姓名, like, null:n/a
     * @param email Email, like, null:n/a
     * @param cellphone 手机号, like, null:n/a
     */
    long count(
            String name,
			String realName,
			String email,
            String cellphone);


    UserDTO getByName(String userName, boolean withProfile, boolean withAccount);

	UserDTO getByEmail(String email, boolean withProfile, boolean withAccount);

    UserDTO getByCellphone(String cellphone, boolean withProfile, boolean withAccount);

	/**
	 * 校验用户密码是否正确
	 *
	 * @param identity 用户名/Email/用户ID
	 * @param password 密码
	 * @return 成功:message=用户ID
	 */
	ResultDTO checkPassword(String sid, String ip, String userAgent, String identity, String password);

    /**
     * 修改密码
     */
    ResultDTO changePassword(String userId, String oldPassword, String newPassword);

    ResultDTO changePassword(String sid, String cellphone, String code, String newPassword);

    ResultDTO resetPassword(String userId, String newPassword);
}
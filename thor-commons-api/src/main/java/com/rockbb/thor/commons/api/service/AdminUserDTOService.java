package com.rockbb.thor.commons.api.service;

import com.rockbb.thor.commons.api.dto.AdminUserDTO;
import com.rockbb.thor.commons.api.dto.ResultDTO;
import com.rockbb.thor.commons.lib.web.Pager;

import java.util.List;
import java.util.Map;

public interface AdminUserDTOService {

	AdminUserDTO get(String id);

	/**
	 * @param name			n/a:null
	 * @param realName		n/a:null
	 * @return list
	 */
	List<AdminUserDTO> list(
			Pager pager,
			String name,
			String realName,
			String email,
			String roleId);

	/**
	 *
	 * @param name		like, n/a:null
	 * @param realName	like, n/a:null
	 * @param email		like, n/a:null
	 * @param roleId    n/a:null
	 */
	long count(
			String name,
			String realName,
			String email,
			String roleId);

	AdminUserDTO getByName(String userName);

	AdminUserDTO getByEmail(String email);

	AdminUserDTO getByCellphone(String cellphone);

	/**
	 * 校验用户密码是否正确
	 *
	 * @param password 密码
	 * @return 成功:message=用户ID
	 */
	ResultDTO checkPassword(String sid, String ip, String userAgent, String identity, String password);
}

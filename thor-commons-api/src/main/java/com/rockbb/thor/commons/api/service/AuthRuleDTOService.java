package com.rockbb.thor.commons.api.service;

import com.rockbb.thor.commons.api.dto.AuthRuleDTO;
import com.rockbb.thor.commons.lib.web.Pager;

import java.util.List;

public interface AuthRuleDTOService {
	int STATUS_ON = 0;
	int STATUS_OFF = 1;

	int add(AuthRuleDTO dto);

	int delete(String id);

	int update(AuthRuleDTO dto);

	AuthRuleDTO get(String id);

	AuthRuleDTO getByIndex(int authIndex);

	/**
	 * @param status		n/a:-1
	 * @return list
	 */
	public List<AuthRuleDTO> list(Pager pager, int status);

	/**
	 * @param status	n/a:-1
	 * @return count
	 */
	long count(int status);

	List<AuthRuleDTO> getWorkingRules();

    boolean checkAuth(String path, String permissions);
}

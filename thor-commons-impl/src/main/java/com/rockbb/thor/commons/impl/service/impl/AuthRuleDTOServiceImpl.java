package com.rockbb.thor.commons.impl.service.impl;

import com.rockbb.thor.commons.api.dto.AuthRuleDTO;
import com.rockbb.thor.commons.api.service.AuthRuleDTOService;
import com.rockbb.thor.commons.impl.mapper.AuthRuleMapper;
import com.rockbb.thor.commons.lib.utilities.ACLUtil;
import com.rockbb.thor.commons.lib.web.Pager;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Milton on 2015/8/25 at 13:36.
 */
@Repository("authRuleDTOService")
public class AuthRuleDTOServiceImpl implements AuthRuleDTOService
{
    @Resource(name="authRuleMapper")
    private AuthRuleMapper authRuleMapper;

    @Override
    public int add(AuthRuleDTO dto) {
        return authRuleMapper.insert(dto);
    }

    @Override
    public int delete(String id) {
        if (id == null || id.length() == 0) return 0;
        return authRuleMapper.delete(id);
    }

    @Override
    public int update(AuthRuleDTO dto) {
        return authRuleMapper.update(dto);
    }

    @Override
    public AuthRuleDTO get(String id) {
        if (id == null || id.length() == 0) return null;
        return authRuleMapper.select(id);
    }

    @Override
    public List<AuthRuleDTO> list(Pager pager, int status) {
        pager.setSorts(AuthRuleMapper.ORDERBY);
        Map<String, Object> args = new HashMap<>();
        if (status > -1) args.put("status", status);
        return authRuleMapper.list(pager, args);
    }

    @Override
    public long count(int status) {
        Map<String, Object> args = new HashMap<>();
        if (status > -1) args.put("status", status);
        return authRuleMapper.count(args);
    }

    @Override
    public AuthRuleDTO getByIndex(int index) {
        return authRuleMapper.selectByIndex(index);
    }

    @Override
    public List<AuthRuleDTO> getWorkingRules() {
        return list(new Pager(0, Integer.MAX_VALUE - 1, 0, 1), STATUS_ON);
    }

    @Override
    public boolean checkAuth(String path, String permissions) {
        if (path == null || permissions == null) return false;

        for (AuthRuleDTO rule : getWorkingRules()) {
            if (path.matches(rule.getRegex())
                    && ACLUtil.IndexOf(permissions, rule.getIndex())) {
                return true;
            }
        }
        return false;
    }
}

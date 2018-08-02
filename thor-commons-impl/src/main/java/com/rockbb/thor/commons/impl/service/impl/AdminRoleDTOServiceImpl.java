package com.rockbb.thor.commons.impl.service.impl;

import com.rockbb.thor.commons.api.dto.AdminRoleDTO;
import com.rockbb.thor.commons.api.service.AdminRoleDTOService;
import com.rockbb.thor.commons.impl.mapper.AdminRoleMapper;
import com.rockbb.thor.commons.lib.web.ArgGen;
import com.rockbb.thor.commons.lib.web.Pager;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("adminRoleDTOService")
public class AdminRoleDTOServiceImpl implements AdminRoleDTOService {
    public static final String CACHE_KEY = "user_role";

    @Resource(name = "adminRoleMapper")
    private AdminRoleMapper adminRoleMapper;

    @Override
    public int add(AdminRoleDTO dto) {
        return adminRoleMapper.insert(dto);
    }

    @Override
    public int delete(String id) {
        return adminRoleMapper.delete(id);
    }

    @Override
    public int update(AdminRoleDTO dto) {
        return adminRoleMapper.update(dto);
    }

    @Override
    public AdminRoleDTO get(String id) {
        return adminRoleMapper.select(id);
    }

    @Override
    public List<AdminRoleDTO> list(Pager pager) {
        pager.setSorts(AdminRoleMapper.ORDERBY);
        return adminRoleMapper.list(pager, new ArgGen().getArgs());
    }

    @Override
    public long count() {
        return adminRoleMapper.count(new ArgGen().getArgs());
    }
}

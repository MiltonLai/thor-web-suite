package com.rockbb.thor.commons.api.service;

import com.rockbb.thor.commons.api.dto.AdminRoleDTO;
import com.rockbb.thor.commons.lib.web.Pager;

import java.util.List;

public interface AdminRoleDTOService {
    int add(AdminRoleDTO dto);

    int delete(String id);

    int update(AdminRoleDTO dto);

    AdminRoleDTO get(String id);

    List<AdminRoleDTO> list(Pager pager);

    long count();
}

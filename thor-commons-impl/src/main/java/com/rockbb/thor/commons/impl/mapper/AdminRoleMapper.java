package com.rockbb.thor.commons.impl.mapper;

import java.util.Map;
import java.util.List;
import com.rockbb.thor.commons.api.dto.AdminRoleDTO;
import com.rockbb.thor.commons.lib.web.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.lang.String;

@Repository("adminRoleMapper")
public interface AdminRoleMapper
{
    String[] ORDERBY = {"name", "updated_at"};

    int insert(AdminRoleDTO dto);

    int delete(String id);

    int update(AdminRoleDTO dto);

    int alter(@Param("id") String id, @Param("param") Map<String, Object> args);

    AdminRoleDTO select(String id);

    List<AdminRoleDTO> list(
            @Param("pager") Pager pager,
            @Param("param") Map<String, Object> args);

    long count(@Param("param") Map<String, Object> args);

}
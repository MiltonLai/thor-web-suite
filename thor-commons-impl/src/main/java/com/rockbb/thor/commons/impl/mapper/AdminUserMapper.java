package com.rockbb.thor.commons.impl.mapper;

import java.util.Map;
import java.util.List;

import com.rockbb.thor.commons.impl.po.AdminUserPO;
import com.rockbb.thor.commons.lib.web.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.lang.String;

@Repository("adminUserMapper")
public interface AdminUserMapper
{
    String[] ORDERBY = {"created_at", "name"};

    int insert(AdminUserPO po);

    int delete(String id);

    int update(AdminUserPO po);

    int alter(@Param("id") String id, @Param("param") Map<String, Object> args);

    AdminUserPO select(String id);

    String selectByName(String name);

    String selectByEmail(String email);

    String selectByCellphone(String cellphone);

    List<String> listIds(
            @Param("pager") Pager pager,
            @Param("param") Map<String, Object> args);

    long count(@Param("param") Map<String, Object> args);

}
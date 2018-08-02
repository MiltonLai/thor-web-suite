package com.rockbb.thor.commons.impl.mapper;

import java.util.List;
import java.util.Map;

import com.rockbb.thor.commons.impl.po.UserPO;
import com.rockbb.thor.commons.lib.web.Pager;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;

@Repository("userMapper")
public interface UserMapper
{
    String[] ORDERBY = {"created_at", "name"};

    int insert(UserPO dto);

    int delete(String id);

    int update(UserPO dto);

    int alter(@Param("id") String id, @Param("param") Map<String, Object> args);

    UserPO select(String id);

    String selectByName(String name);

    String selectByEmail(String email);

    String selectByCellphone(String cellphone);

    List<UserPO> list(
            @Param("pager") Pager pager,
            @Param("param") Map<String, Object> args);

    List<String> listIds(
            @Param("pager") Pager pager,
            @Param("param") Map<String, Object> args);

    long count(@Param("param") Map<String, Object> args);

}
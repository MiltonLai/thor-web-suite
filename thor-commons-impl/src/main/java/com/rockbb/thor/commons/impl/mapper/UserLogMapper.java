package com.rockbb.thor.commons.impl.mapper;

import com.rockbb.thor.commons.api.dto.UserLogDTO;
import java.util.List;
import java.util.Map;

import com.rockbb.thor.commons.lib.web.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("userLogMapper")
public interface UserLogMapper {
    String[] ORDERBY = {"session_id", "user_id", "action_type", "secure1", "secure2", "secure3", "notes", "created_at"};

    int insert(UserLogDTO dto);

    UserLogDTO select(String id);

    List<UserLogDTO> list(
            @Param("pager") Pager pager,
            @Param("param") Map<String, Object> args);

    List<String> listIds(
            @Param("pager") Pager pager,
            @Param("param") Map<String, Object> args);

    long count(@Param("param") Map<String, Object> args);

}
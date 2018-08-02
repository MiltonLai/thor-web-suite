package com.rockbb.thor.commons.impl.mapper;

import com.rockbb.thor.commons.api.dto.SessionLogDTO;
import com.rockbb.thor.commons.lib.web.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("sessionLogMapper")
public interface SessionLogMapper {
    String[] ORDERBY = {"CREATED_AT", "USER_ID", "IP", "APP"};

    int insert(SessionLogDTO dto);

    SessionLogDTO select(String id);

    List<SessionLogDTO> list(
            @Param("pager") Pager pager,
            @Param("param") Map<String, Object> args);

    long count(@Param("param") Map<String, Object> args);
}
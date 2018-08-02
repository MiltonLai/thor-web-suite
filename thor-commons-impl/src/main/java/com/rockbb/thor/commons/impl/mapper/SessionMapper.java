package com.rockbb.thor.commons.impl.mapper;

import java.util.Map;
import java.util.List;
import java.lang.String;
import com.rockbb.thor.commons.api.dto.SessionDTO;
import com.rockbb.thor.commons.lib.web.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("sessionMapper")
public interface SessionMapper
{
    String[] ORDERBY = {"updated_at", "started_at", "user_id"};

    int insert(SessionDTO dto);

    int delete(String id);

    int update(SessionDTO dto);

    int alter(@Param("id") String id, @Param("param") Map<String, Object> args);

    SessionDTO select(String id);

    long countAll();

    int cleanExpired(
            @Param("normalLimit") long normalLimit,
            @Param("autologinLimit") long autologinLimit);

    int truncate();

    List<SessionDTO> list(
            @Param("pager") Pager pager,
            @Param("param") Map<String, Object> args);

    long count(@Param("param") Map<String, Object> args);

}
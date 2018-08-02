package com.rockbb.thor.commons.impl.mapper;

import java.util.Map;
import java.util.List;
import com.rockbb.thor.commons.api.dto.AuthRuleDTO;
import com.rockbb.thor.commons.lib.web.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.lang.String;

@Repository("authRuleMapper")
public interface AuthRuleMapper
{
    String[] ORDERBY = {"auth_index", "name", "regex"};

    int insert(AuthRuleDTO dto);

    int delete(String ruleId);

    int update(AuthRuleDTO dto);

    int alter(@Param("id") String id, @Param("param") Map<String, Object> args);

    AuthRuleDTO select(String ruleId);

    AuthRuleDTO selectByIndex(int index);

    List<AuthRuleDTO> list(
            @Param("pager") Pager pager,
            @Param("param") Map<String, Object> args);

    long count(@Param("param") Map<String, Object> args);

}
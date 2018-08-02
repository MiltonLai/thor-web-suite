package com.rockbb.thor.commons.impl.mapper;

import com.rockbb.thor.commons.impl.po.ConfigPO;
import com.rockbb.thor.commons.lib.web.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("configMapper")
public interface ConfigMapper {
    String[] ORDERBY = {"category","name"};

    int insert(ConfigPO dto);

    int delete(
            @Param("category") String category,
            @Param("name") String name);

    int update(ConfigPO dto);

    ConfigPO select(
            @Param("category") String category,
            @Param("name") String name);

    List<String> listByCategory(String category);

    List<ConfigPO> list(
            @Param("pager") Pager pager,
            @Param("param") Map<String, Object> args);
}
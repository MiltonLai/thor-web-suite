package com.rockbb.thor.commons.api.service;

import com.rockbb.thor.commons.api.dto.ConfigDTO;
import com.rockbb.thor.commons.lib.web.Pager;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ConfigDTOService {
    int add(ConfigDTO dto);

    int update(ConfigDTO dto);

    ConfigDTO get(String category, String name);

    String getString(String category, String name);

    Integer getInt(String category, String name);

    List<String> getList(String category, String name);

    Map<String, String> getMap(String category, String name);

    List<Object> getObjectList(String category, String name);

    Map<String, Object> getObjectMap(String category, String name);

    Map<String, Object> getConfigs(String category);

    /**
     * @param category n/a:null
     * @param name n/a:null
     */
    List<ConfigDTO> list(Pager pager, String category, String name);

    void purgeCache(String category);
}

package com.rockbb.thor.commons.impl.service.impl;

import com.rockbb.thor.commons.api.dto.ConfigDTO;
import com.rockbb.thor.commons.api.service.ConfigDTOService;
import com.rockbb.thor.commons.impl.mapper.ConfigMapper;
import com.rockbb.thor.commons.impl.po.ConfigPO;
import com.rockbb.thor.commons.lib.cache.CacheFactory;
import com.rockbb.thor.commons.lib.json.JacksonUtils;
import com.rockbb.thor.commons.lib.utilities.TimeUtil;
import com.rockbb.thor.commons.lib.web.ArgGen;
import com.rockbb.thor.commons.lib.web.Pager;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("configDTOService")
public class ConfigDTOServiceImpl implements ConfigDTOService
{
    private static final String CACHE_KEY = "config_";

    @Resource(name = "cacheFactory")
    private CacheFactory cacheFactory;
    @Resource(name="configMapper")
    private ConfigMapper configMapper;

    @Override
    public int add(ConfigDTO dto) {
        int result = configMapper.insert(adapt(dto));
        purgeCache(dto.getCategory());
        return result;
    }

    @Override
    public int update(ConfigDTO dto) {
        int result = configMapper.update(adapt(dto));
        purgeCache(dto.getCategory());
        return result;
    }

    @Override
    public ConfigDTO get(String category, String name) {
        return adapt(configMapper.select(category, name));
    }

    @Override
    public String getString(String category, String name) {
        if (getConfigs(category) != null && getConfigs(category).get(name) != null)
            return (String) getConfigs(category).get(name);
        return null;
    }

    @Override
    public Integer getInt(String category, String name) {
        if (getConfigs(category) != null && getConfigs(category).get(name) != null)
            return (Integer) getConfigs(category).get(name);
        return null;
    }

    @Override
    public List<String> getList(String category, String name) {
        if (getConfigs(category) != null && getConfigs(category).get(name) != null)
            return (List<String>) getConfigs(category).get(name);
        return null;
    }

    @Override
    public Map<String, String> getMap(String category, String name) {
        if (getConfigs(category) != null && getConfigs(category).get(name) != null)
            return (Map<String, String>) getConfigs(category).get(name);
        return null;
    }

    @Override
    public List<Object> getObjectList(String category, String name) {
        if (getConfigs(category) != null && getConfigs(category).get(name) != null)
            return (List<Object>) getConfigs(category).get(name);
        return null;
    }

    @Override
    public Map<String, Object> getObjectMap(String category, String name) {
        if (getConfigs(category) != null && getConfigs(category).get(name) != null)
            return (Map<String, Object>) getConfigs(category).get(name);
        return null;
    }

    @Override
    public Map<String, Object> getConfigs(String category) {
        Element elm = getCache().get(CACHE_KEY + category);
        Map<String, Object> configs;
        if (elm == null) {
            configs = new HashMap<>();
            for (String name : configMapper.listByCategory(category)) {
                ConfigDTO dto = get(category, name);
                if (dto != null) {
                    switch (dto.getType()) {
                        case ConfigDTO.TYPE_STRING:
                            configs.put(name, dto.getValue());
                            break;
                        case ConfigDTO.TYPE_INT:
                            configs.put(name, dto.getInteger());
                            break;
                        case ConfigDTO.TYPE_DATE:
                            configs.put(name, dto.getDate());
                            break;
                        case ConfigDTO.TYPE_LIST:
                            configs.put(name, dto.getList());
                            break;
                        case ConfigDTO.TYPE_MAP:
                            configs.put(name, dto.getMap());
                            break;
                        case ConfigDTO.TYPE_LIST_OBJ:
                            configs.put(name, dto.getListObj());
                            break;
                        case ConfigDTO.TYPE_MAP_OBJ:
                            configs.put(name, dto.getMapObj());
                            break;
                    }
                }
            }
            getCache().put(new Element(CACHE_KEY + category, configs));
        } else {
            configs = (Map<String, Object>) elm.getObjectValue();
        }
        return configs;
    }

    @Override
    public List<ConfigDTO> list(Pager pager, String category, String name) {
        pager.setSorts(ConfigMapper.ORDERBY);
        ArgGen args = new ArgGen()
                .addNotEmpty("name", name)
                .addNotEmpty("category", category);
        return adapt(configMapper.list(pager, args.getArgs()));
    }

    /**
     * Remove all cache for a full reload
     */
    @Override
    public void purgeCache(String category) {
        getCache().remove(CACHE_KEY + category);
    }

    public static ConfigDTO adapt(ConfigPO po) {
        if (po == null) return null;
        ConfigDTO dto = new ConfigDTO();
        dto.setCategory(po.getCategory());
        dto.setTitle(po.getTitle());
        dto.setName(po.getName());
        dto.setType(po.getType());
        dto.setValue(po.getValue());
        dto.setDefaultValue(po.getDefaultValue());
        dto.setNotes(po.getNotes());
        switch (po.getType()) {
            case ConfigDTO.TYPE_STRING:
                break;
            case ConfigDTO.TYPE_INT:
                dto.setInteger(Integer.parseInt(po.getValue()));
                break;
            case ConfigDTO.TYPE_DATE:
                dto.setDate(TimeUtil.getDate(po.getValue(), TimeUtil.FORMAT_YMD_HM));
                break;
            case ConfigDTO.TYPE_LIST:
                dto.setList(JacksonUtils.extractList(po.getValue(), new ArrayList<>()));
                break;
            case ConfigDTO.TYPE_MAP:
                dto.setMap(JacksonUtils.extractMap(po.getValue(), new HashMap<>()));
                break;
            case ConfigDTO.TYPE_LIST_OBJ:
                dto.setListObj(JacksonUtils.extractList(po.getValue(), new ArrayList<>()));
                break;
            case ConfigDTO.TYPE_MAP_OBJ:
                dto.setMapObj(JacksonUtils.extractMap(po.getValue(), new HashMap<>()));
        }
        return dto;
    }

    public ConfigPO adapt(ConfigDTO dto) {
        if (dto == null) return null;
        ConfigPO po = new ConfigPO();
        po.setCategory(dto.getCategory());
        po.setTitle(dto.getTitle());
        po.setName(dto.getName());
        po.setType(dto.getType());
        po.setDefaultValue(dto.getDefaultValue());
        po.setNotes(dto.getNotes());
        switch(dto.getType()) {
            case ConfigDTO.TYPE_STRING:
                po.setValue(dto.getValue());
                break;
            case ConfigDTO.TYPE_INT:
                po.setValue(dto.getInteger() + "");
                break;
            case ConfigDTO.TYPE_DATE:
                po.setValue(TimeUtil.getStr(dto.getDate(), TimeUtil.FORMAT_YMD_HM));
                break;
            case ConfigDTO.TYPE_LIST:
                po.setValue(JacksonUtils.compressList(dto.getList()));
                break;
            case ConfigDTO.TYPE_MAP:
                po.setValue(JacksonUtils.compressMap(dto.getMap()));
                break;
            case ConfigDTO.TYPE_LIST_OBJ:
                po.setValue(JacksonUtils.compressList(dto.getListObj()));
                break;
            case ConfigDTO.TYPE_MAP_OBJ:
                po.setValue(JacksonUtils.compressMap(dto.getMapObj()));
                break;
        }
        return po;
    }

    private List<ConfigDTO> adapt(List<ConfigPO> pos) {
		List<ConfigDTO> dtos = new ArrayList<>();
		if (pos != null && pos.size() > 0) {
			for (ConfigPO po : pos) {
				ConfigDTO dto = adapt(po);
				if (dto != null) {
					dtos.add(dto);
				}
			}
		}
		return dtos;
	}

    public Cache getCache() {
        return cacheFactory.get(ConfigDTOServiceImpl.class.getName(), 500, 3600);
    }
}
package com.rockbb.thor.commons.api.service;

import com.rockbb.thor.commons.api.dto.UserLogDTO;
import com.rockbb.thor.commons.lib.web.Pager;

import java.util.List;

public interface UserLogDTOService {
    /**
     * 记录用户安全日志
     */
    int add(String sid, String uid, int actionType, String ip, String userAgent, String notes);

    UserLogDTO get(String id);

    List<UserLogDTO> list(Pager pager);

    List<String> listIds(Pager pager);

    long count();

}
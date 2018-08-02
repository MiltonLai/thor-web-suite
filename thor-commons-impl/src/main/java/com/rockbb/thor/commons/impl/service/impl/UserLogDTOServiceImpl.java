package com.rockbb.thor.commons.impl.service.impl;

import com.rockbb.thor.commons.api.dto.UserLogDTO;
import com.rockbb.thor.commons.api.service.UserLogDTOService;
import com.rockbb.thor.commons.impl.mapper.UserLogMapper;
import com.rockbb.thor.commons.lib.utilities.StringUtil;
import com.rockbb.thor.commons.lib.web.ArgGen;
import com.rockbb.thor.commons.lib.web.Pager;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("userLogDTOService")
public class UserLogDTOServiceImpl implements UserLogDTOService {

    @Resource(name="userLogMapper")
    private UserLogMapper userLogMapper;

    @Override
    public int add(String sid, String uid, int actionType, String ip, String userAgent, String notes) {
        UserLogDTO log = new UserLogDTO().initialize();
        log.setSessionId(sid);
        log.setUserId(uid);
        log.setActionType(actionType);
        log.setIp(StringUtil.shorten(ip, 32));
        log.setSecure1(StringUtil.shorten(userAgent, 255));
        log.setNotes(notes);
        return userLogMapper.insert(log);
    }

    @Override
    public UserLogDTO get(String id) {
        if (id == null || id.length() == 0) return null;
        return userLogMapper.select(id);
    }

    @Override
    public List<UserLogDTO> list(Pager pager) {
        pager.setSorts(UserLogMapper.ORDERBY);
        ArgGen args = new ArgGen();
        // Add more parameters here
        return userLogMapper.list(pager, args.getArgs());
    }

    @Override
    public List<String> listIds(Pager pager) {
        pager.setSorts(UserLogMapper.ORDERBY);
        ArgGen args = new ArgGen();
        // Add more parameters here
        return userLogMapper.listIds(pager, args.getArgs());
    }

    @Override
    public long count() {
        ArgGen args = new ArgGen();
        // Add more parameters here
        return userLogMapper.count(args.getArgs());
    }

}
package com.rockbb.thor.commons.impl.service.impl;

import com.rockbb.thor.commons.api.dto.AdminUserDTO;
import com.rockbb.thor.commons.api.dto.ResultDTO;
import com.rockbb.thor.commons.api.dto.UserLogDTO;
import com.rockbb.thor.commons.api.service.AdminUserDTOService;
import com.rockbb.thor.commons.api.service.UserLogDTOService;
import com.rockbb.thor.commons.api.util.Constants;
import com.rockbb.thor.commons.impl.mapper.AdminUserMapper;
import com.rockbb.thor.commons.impl.po.AdminUserPO;
import com.rockbb.thor.commons.lib.cache.CacheFactory;
import com.rockbb.thor.commons.lib.utilities.SecureUtil;
import com.rockbb.thor.commons.lib.web.ArgGen;
import com.rockbb.thor.commons.lib.web.Pager;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository("adminUserDTOService")
public class AdminUserDTOServiceImpl implements AdminUserDTOService {
    public static final String CACHE_KEY = "admin_user_";
    private static Logger logger = LoggerFactory.getLogger(AdminUserDTOServiceImpl.class);

    @Resource(name="adminUserMapper")
    private AdminUserMapper adminUserMapper;
    @Resource(name = "cacheFactory")
    private CacheFactory cacheFactory;
    @Resource(name = "userLogDTOService")
    private UserLogDTOService userLogDTOService;

    @Override
    public AdminUserDTO get(String id) {
        if (id == null || id.length() == 0) return null;
        if (id.equals(AdminUserDTO.ANONYMOUS_UID)) return new AdminUserDTO().anonymous();
        if (id.equals(Constants.SYS_USER_ID)) return new AdminUserDTO().system();
        Element elm = getCache().get(CACHE_KEY + id);
        if (elm != null) {
            return (AdminUserDTO)elm.getObjectValue();
        }
        AdminUserDTO admin = adapt(adminUserMapper.select(id));
        if (admin != null) {
            getCache().put(new Element(CACHE_KEY + id, admin));
        }
        return admin;
    }

    @Override
    public List<AdminUserDTO> list(Pager pager, String name, String realName, String email, String roleId) {
        pager.setSorts(AdminUserMapper.ORDERBY);
        ArgGen args = new ArgGen()
                .addLike("name", name)
                .addLike("realName", realName)
                .addLike("email", email)
                .addNotEmpty("roleId", roleId);
        return adaptIds(adminUserMapper.listIds(pager, args.getArgs()));
    }

    @Override
    public long count(String name, String realName, String email, String roleId) {
        ArgGen args = new ArgGen()
                .addLike("name", name)
                .addLike("realName", realName)
                .addLike("email", email)
                .addNotEmpty("roleId", roleId);
        return adminUserMapper.count(args.getArgs());
    }

    @Override
    public AdminUserDTO getByName(String name) {
        return get(adminUserMapper.selectByName(name));
    }

    @Override
    public AdminUserDTO getByEmail(String email) {
        return get(adminUserMapper.selectByEmail(email));
    }

    @Override
    public AdminUserDTO getByCellphone(String cellphone) {
        return get(adminUserMapper.selectByCellphone(cellphone));
    }

    @Override
    public ResultDTO checkPassword(String sid, String ip, String userAgent, String identity, String password) {
        if (identity == null || identity.length() == 0) {
            return ResultDTO.error("用户名或手机号不能为空");
        }

        AdminUserPO user = null;
        if (identity.length() == 32) {
            user = adminUserMapper.select(identity);
        }
        if (user == null && identity.length() == 11) {
            String id = adminUserMapper.selectByCellphone(identity);
            user = adminUserMapper.select(id);
        }
        if (user == null && identity.contains("@")) {
            String id = adminUserMapper.selectByEmail(identity);
            user = adminUserMapper.select(id);
        }
        if (user == null) {
            String id = adminUserMapper.selectByName(identity);
            user = adminUserMapper.select(id);
        }
        if (user == null) {
            return ResultDTO.error("用户不存在");
        }

        ResultDTO result = checkPassword(user, password);
        if (result.isFailed()) {
            // 记录失败的登录尝试
            userLogDTOService.add(sid, user.getId(), UserLogDTO.ACTION_PASSWORD_ERROR, ip, userAgent, user.getCellphone());
        }
        return result;
    }

    private ResultDTO checkPassword(AdminUserPO user, String password) {
        boolean pass = false;
        String passwordHash = SecureUtil.saltHash(user.getHash(), password);
        if (user.getPassword().equals(passwordHash)) {
            pass = true;
        }
        if (!pass) {
            return ResultDTO.error("密码不正确", user.getId());
        }
        return ResultDTO.success("密码验证通过", user.getId());
    }

     /**
     * @param noExt false:正常转换, true:仅作原po转换, dto扩展字段留空, 用于不需要附加属性的场景下提高效率
     */
    private AdminUserDTO adapt(AdminUserPO po, boolean noExt) {
        if (po == null) return null;
        AdminUserDTO dto = new AdminUserDTO();
        BeanUtils.copyProperties(po, dto);
        if (noExt) return dto;

        return dto;
    }

    private AdminUserDTO adapt(AdminUserPO po) {
        return adapt(po, true);
    }

    private List<AdminUserDTO> adaptIds(List<String> ids) {
        List<AdminUserDTO> dtos = new ArrayList<>();
        if (ids != null && ids.size() > 0) {
            for (String id : ids) {
                AdminUserDTO dto = get(id);
                if (dto != null) {
                    dtos.add(dto);
                }
            }
        }
        return dtos;
    }

    private AdminUserPO adapt(AdminUserDTO dto) {
        if (dto == null) return null;
        AdminUserPO po = new AdminUserPO();
        BeanUtils.copyProperties(dto, po);
        return po;
    }

    public Cache getCache() {
        return cacheFactory.get(AdminUserDTOServiceImpl.class.getName(), 500, 3600);
    }
}

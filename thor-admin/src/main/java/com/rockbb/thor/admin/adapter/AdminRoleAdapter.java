package com.rockbb.thor.admin.adapter;

import com.rockbb.thor.admin.vo.AdminRole;
import com.rockbb.thor.admin.web.base.SessionBean;
import com.rockbb.thor.commons.api.dto.AdminRoleDTO;
import com.rockbb.thor.commons.api.service.AdminRoleDTOService;
import com.rockbb.thor.commons.lib.cache.CacheFactory;
import com.rockbb.thor.commons.lib.json.JacksonUtils;
import com.rockbb.thor.commons.lib.utilities.ACLUtil;
import com.rockbb.thor.commons.lib.web.Pager;
import com.rockbb.thor.commons.lib.web.RequestBean;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("adminRoleAdapter")
public class AdminRoleAdapter {
    private static final String CACHE_KEY = "userrole_";

    @Resource(name = "cacheFactory")
    private CacheFactory cacheFactory;
    @Resource(name="adminRoleDTOService")
    private AdminRoleDTOService adminRoleDTOService;

    public int add(AdminRole vo, SessionBean sb, RequestBean rb) {
        AdminRoleDTO dto = adapt(vo);
        if (adminRoleDTOService.add(dto) == 1) {
            return 1;
        }
        return 0;
    }

    public int delete(String id, SessionBean sb, RequestBean rb) {
        AdminRole role = get(id);
        if (role != null && adminRoleDTOService.delete(id) == 1) {
            getCache().remove(CACHE_KEY + id);
            return 1;
        }
        return 0;
    }

    public int update(AdminRole vo, SessionBean sb, RequestBean rb) {
        AdminRoleDTO post = adapt(vo);
        AdminRoleDTO pre = adminRoleDTOService.get(vo.getDto().getId());
        if (adminRoleDTOService.update(post) == 1) {
            getCache().remove(CACHE_KEY + post.getId());
            return 1;
        }
        return 0;
    }

    public AdminRole get(String id) {
        if (id == null)
            return null;
        Element elm = getCache().get(CACHE_KEY + id);
        AdminRole bean;
        if (elm == null) {
            bean = getRaw(id);
            if (bean != null)
                getCache().put(new Element(CACHE_KEY + id, bean));
        } else {
            bean = (AdminRole) elm.getObjectValue();
        }
        return bean;
    }

    public AdminRole getRaw(String id) {
        return adapt(adminRoleDTOService.get(id));
    }

    /**
     * @return list
     */
    public List<AdminRole> list(Pager pager) {
        return adapt(adminRoleDTOService.list(pager));
    }

    /**
     * @return count
     */
    public long count() {
        return adminRoleDTOService.count();
    }

    public static AdminRole adapt(AdminRoleDTO dto) {
        if (dto == null) return null;
        AdminRole vo = new AdminRole(dto);
        vo.setPermissionList(ACLUtil.stringToPermissions(dto.getPermissions()));
        vo.setMenuList(JacksonUtils.extractList(dto.getMenus(), new ArrayList<String>()));
        return vo;
    }

    public static List<AdminRole> adapt(List<AdminRoleDTO> dtos) {
        List<AdminRole> vos = new ArrayList<>();
        if (dtos != null && dtos.size() > 0) {
            for (AdminRoleDTO dto : dtos) {
                vos.add(adapt(dto));
            }
        }
        return vos;
    }

    public AdminRoleDTO adapt(AdminRole vo) {
        if (vo != null) {
            vo.getDto().setMenus(JacksonUtils.compressList(vo.getMenuList()));
            vo.getDto().setPermissions(ACLUtil.permissionsToString(vo.getPermissionList()));
        }
        return vo.getDto();
    }

    public Cache getCache() {
        return cacheFactory.get(AdminRoleAdapter.class.getName(), 500, 3600);
    }
}

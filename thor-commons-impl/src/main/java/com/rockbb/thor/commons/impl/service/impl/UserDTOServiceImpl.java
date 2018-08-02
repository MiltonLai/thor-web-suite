package com.rockbb.thor.commons.impl.service.impl;

import com.rockbb.thor.commons.api.dto.AdminUserDTO;
import com.rockbb.thor.commons.api.dto.ResultDTO;
import com.rockbb.thor.commons.api.dto.SessionDTO;
import com.rockbb.thor.commons.api.dto.UserDTO;
import com.rockbb.thor.commons.api.dto.UserLogDTO;
import com.rockbb.thor.commons.api.service.UserDTOService;
import com.rockbb.thor.commons.api.service.UserLogDTOService;
import com.rockbb.thor.commons.api.util.LocalRuntimeException;
import com.rockbb.thor.commons.impl.mapper.AdminUserMapper;
import com.rockbb.thor.commons.impl.mapper.SessionMapper;
import com.rockbb.thor.commons.impl.mapper.UserMapper;
import com.rockbb.thor.commons.impl.po.UserPO;
import com.rockbb.thor.commons.lib.utilities.SecureUtil;
import com.rockbb.thor.commons.lib.utilities.StaticConfig;
import com.rockbb.thor.commons.lib.web.ArgGen;
import com.rockbb.thor.commons.lib.web.Pager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository("userDTOService")
public class UserDTOServiceImpl implements UserDTOService {
    public static final String CACHE_KEY = "user_";
    private static Logger logger = LoggerFactory.getLogger(UserDTOServiceImpl.class);

    @Resource(name = "sessionMapper")
    private SessionMapper sessionMapper;
    @Resource(name="userMapper")
    private UserMapper userMapper;

    @Resource(name = "userLogDTOService")
    private UserLogDTOService userLogDTOService;

    /**
     * 创建用户(以及相应的资金账户和扩展资料)
     */
    private ResultDTO add(
            UserPO po,
            String app,
            String inviterId,
            String refererUrl,
            String entryUrl,
            String promoteCode) {
        if (userMapper.insert(po) == 0) {
            throw new LocalRuntimeException("无法创建用户");
        }

        return ResultDTO.success();
    }

    /**
     * 删除新建用户
     */
    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=false)
    public ResultDTO delete(String id) {
        if (id == null || id.length() == 0) return ResultDTO.error("ID不能为空");
        logger.info("Deleting user, ID: " + id);
        UserPO user = userMapper.select(id);
        if (user == null) {
            return ResultDTO.error("用户不存在");
        } else if (user.getCreatedAt().before(new Date(System.currentTimeMillis() - 1000L * 3600 * 24 * 14))) {
            return ResultDTO.error("老用户不能删除, 如有疑问请与技术部门联系");
        }
        if (userMapper.delete(id) == 0) {
            throw new LocalRuntimeException("用户账户删除出错");
        }
        logger.info("User deleted, ID: {}", id);
        return ResultDTO.success();
    }

    @Override
    public int update(UserDTO dto) {
        return userMapper.update(adapt(dto));
    }

    @Override
    public UserDTO get(String id) {
        return get(id, false, false);
    }

    @Override
    public UserDTO get(String id, boolean withProfile, boolean withAccount) {
        if (id == null || id.length() == 0) return null;
        if (id.equals(AdminUserDTO.ANONYMOUS_UID)) return new UserDTO().anonymous();
        return adapt(userMapper.select(id), withProfile, withAccount);
    }

    @Override
    public List<UserDTO> get(List<String> ids, boolean withProfile, boolean withAccount) {
        List<UserDTO> dtos = new ArrayList<>();
        if (ids != null && ids.size() > 0) {
            for (String id : ids) {
                UserDTO dto = get(id, withProfile, withAccount);
                if (dto != null) {
                    dtos.add(dto);
                }
            }
        }
        return dtos;
    }

    @Override
    public List<UserDTO> list(
            Pager pager,
            boolean withProfile,
            boolean withAccount,
            String name,
            String realName,
            String email,
            String cellphone) {
        pager.setSorts(AdminUserMapper.ORDERBY);
        ArgGen args = new ArgGen()
                .addLike("name", name)
                .addLike("realName", realName)
                .addLike("email", email)
                .addLike("cellphone", cellphone);
        return get(userMapper.listIds(pager, args.getArgs()), withProfile, withAccount);
    }

    @Override
    public long count(
            String name,
            String realName,
            String email,
            String cellphone) {
        ArgGen args = new ArgGen()
                .addLike("name", name)
                .addLike("realName", realName)
                .addLike("email", email)
                .addLike("cellphone", cellphone);
        return userMapper.count(args.getArgs());
    }

    @Override
    public UserDTO getByName(String name, boolean withProfile, boolean withAccount) {
        return get(userMapper.selectByName(name), withProfile, withAccount);
    }

    @Override
    public UserDTO getByEmail(String email, boolean withProfile, boolean withAccount) {
        return get(userMapper.selectByEmail(email), withProfile, withAccount);
    }

    @Override
    public UserDTO getByCellphone(String cellphone, boolean withProfile, boolean withAccount) {
        return get(userMapper.selectByCellphone(cellphone), withProfile, withAccount);
    }

    @Override
    public ResultDTO checkPassword(String sid, String ip, String userAgent, String identity, String password) {
        if (identity == null || identity.length() == 0) {
            return ResultDTO.error("用户名或手机号不能为空");
        }

        UserPO user = null;
        if (identity.length() == 32) {
            user = userMapper.select(identity);
        }
        String userId = null;
        if (user == null && identity.length() == 11) {
            userId = userMapper.selectByCellphone(identity);
        }
        if (userId == null && identity.contains("@")) {
            userId = userMapper.selectByEmail(identity);
        }
        if (userId == null) {
            userId = userMapper.selectByName(identity);
        }
        if (userId == null) {
            return ResultDTO.error("用户不存在");
        }
        user = userMapper.select(userId);

        ResultDTO result = checkPassword(user, password);
        if (result.isFailed()) {
            // 记录失败的登录尝试
            userLogDTOService.add(sid, user.getId(), UserLogDTO.ACTION_PASSWORD_ERROR, ip, userAgent, user.getCellphone());

        }
        return result;
    }

    @Override
    public ResultDTO changePassword(String userId, String oldPassword, String newPassword) {
    	if (userId == null || userId.length() == 0) {
            return ResultDTO.error("用户ID不能为空");
        }
        UserPO user = userMapper.select(userId);
        if (user == null) {
            return ResultDTO.error("用户不存在");
        }
        boolean pass = false;
        //判断是否为旧系统用户, 并进行旧加密方式密码验证
        if(StringUtils.isEmpty(user.getHash()) || user.getPassword().length() == 32) {
            if (user.getPassword().equals(SecureUtil.aesEbcEnc(oldPassword))) {
                pass = true;
            }
        } else {
            String passwordHash = SecureUtil.saltHash(user.getHash(), oldPassword);
            if (user.getPassword().equals(passwordHash)) {
                pass = true;
            }
        }
        if (!pass) {
            return ResultDTO.error("原密码不正确");
        }

        int result = 0;
        if(StringUtils.isEmpty(user.getHash())) {
            result = setHashAndPassword(user.getId(), user.getVersion(), newPassword);
        } else {
            result = setPassword(user.getId(), user.getVersion(), user.getHash(), newPassword);
        }

        if (result == 1) {
            return ResultDTO.success("密码修改成功");
        } else {
            return ResultDTO.error("数据库更新失败");
        }
    }

    @Override
    public ResultDTO changePassword(String sid, String cellphone, String code, String newPassword) {
        String userId = userMapper.selectByCellphone(cellphone);
        if (userId == null) {
            return ResultDTO.error("用户不存在","cellphone");
        }

        UserPO user = userMapper.select(userId);
        int result = 0;
        if(StringUtils.isEmpty(user.getHash())) {
            result = setHashAndPassword(user.getId(), user.getVersion(), newPassword);
        } else {
            result = setPassword(user.getId(), user.getVersion(), user.getHash(), newPassword);
        }
        if (result == 1) {
            return ResultDTO.success("密码修改成功");
        } else {
            return ResultDTO.error("数据库更新失败");
        }
    }

    @Override
    public ResultDTO resetPassword(String userId, String newPassword) {
        UserPO user = userMapper.select(userId);
        if (user == null) {
            return ResultDTO.error("用户不存在","userId");
        }
        int result = 0;
        if(StringUtils.isEmpty(user.getHash())) {
            result = setHashAndPassword(user.getId(), user.getVersion(), newPassword);
        } else {
            result = setPassword(user.getId(), user.getVersion(), user.getHash(), newPassword);
        }
        if (result == 1) {
            return ResultDTO.success("重置密码成功");
        } else {
            return ResultDTO.error("重置密码失败");
        }
    }

    private ResultDTO checkPassword(UserPO user, String password) {
        boolean pass = false;
        //判断是否为旧系统用户, 并进行旧加密方式密码验证, 通过后, 则编码为新密码
        if (StringUtils.isEmpty(user.getHash()) || user.getPassword().length() == 32) {
            if (user.getPassword().equals(SecureUtil.aesEbcEnc(password))) {
                pass = true;
            }
        } else {
            String passwordHash = SecureUtil.saltHash(user.getHash(), password);
            if (user.getPassword().equals(passwordHash)) {
                pass = true;
            }
        }
        if (!pass) {
            return ResultDTO.error("密码不正确", user.getId());
        }

        // 对旧系统用户升级密码为新格式
        int result = 0;
        if (StringUtils.isEmpty(user.getHash())) {
            result = setHashAndPassword(user.getId(), user.getVersion(), password);
        } else if (user.getPassword().length() == 32) {
            result = setPassword(user.getId(), user.getVersion(), user.getHash(), password);
        }
        if (result == 0) {
            // 升级密码不应影响结果返回
            logger.error("Failed to update user legacy password");
        }

        return ResultDTO.success("密码验证通过", user.getId());
    }

    /**
     * 重设用户salt和密码
     *
     * @param userId 用户ID
     * @param version 版本号(必须)
     * @param password 新密码
     * @return 0:失败 1:成功
     */
    private int setHashAndPassword(String userId, long version, String password) {
        String[] newHashs = SecureUtil.saltHash(password);
        ArgGen args = new ArgGen()
                .add("version", version)
                .addNotEmpty("hash", newHashs[0])
                .addNotEmpty("password", newHashs[1]);
        return userMapper.alter(userId, args.getArgs());
    }

    /**
     * 不改变hash, 仅重设用户密码
     *
     * @param userId 用户ID
     * @param version 版本号(必须)
     * @param hash 现在的hash
     * @param password 新密码
     * @return 0:失败 1:成功
     */
    private int setPassword(String userId, long version, String hash, String password) {
        String passwordHash = SecureUtil.saltHash(hash, password);
        ArgGen args = new ArgGen()
                .add("version", version)
                .addNotEmpty("password", passwordHash);
        return userMapper.alter(userId, args.getArgs());
    }

    /**
     * @param withProfile 是否带profile
     */
    private UserDTO adapt(UserPO po, boolean withProfile, boolean withAccount) {
        if (po == null) return null;
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(po, dto);
        return dto;
    }

    private UserDTO adapt(UserPO po) {
        return adapt(po, false, false);
    }

    private UserPO adapt(UserDTO dto) {
        if (dto == null) return null;
        UserPO po = new UserPO();
        BeanUtils.copyProperties(dto, po);
        return po;
    }
}

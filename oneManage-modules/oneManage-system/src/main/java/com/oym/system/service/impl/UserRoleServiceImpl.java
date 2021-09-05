package com.oym.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.utils.Argument;
import com.oym.commons.utils.CollectionUtils;
import com.oym.system.dao.UserRoleDao;
import com.oym.system.domain.bo.UserRoleSaveBO;
import com.oym.system.domain.dataobject.UserRoleDO;
import com.oym.system.domain.dto.UserRoleDTO;
import com.oym.system.service.UserRoleService;
import com.oym.system.transform.UserRoleTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统用户角色关联关系service实现
 *
 * @author oneyuanma
 * @date 2021/07/17
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRoleDO> implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public OymResult<Boolean> saveUserRole(UserRoleSaveBO bo) {
        // 先删除之前的关联关系，再保存

        QueryWrapper<UserRoleDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("user_id", bo.getId());
        userRoleDao.delete(queryWrapper);

        Long[] roles = bo.getRoles();
        if (Argument.isNotNull(roles) && roles.length > 0) {
            for (Long roleId : roles) {
                userRoleDao.insert(new UserRoleDO(bo.getId(), roleId));
            }
        }
        return OymResult.success(true);
    }

    @Override
    public OymResult<List<UserRoleDTO>> getByUsers(List<Long> userIds) {

        if (Argument.isEmpty(userIds)) {
            return OymResult.error("用户id不能为空");
        }

        QueryWrapper<UserRoleDO> queryWrapper = Wrappers.query();
        queryWrapper.in("user_id", userIds);
        List<UserRoleDO> userRoleDOS = userRoleDao.selectList(queryWrapper);

        return OymResult.success(CollectionUtils.convert(userRoleDOS, UserRoleTransform.INS::transfer));
    }
}

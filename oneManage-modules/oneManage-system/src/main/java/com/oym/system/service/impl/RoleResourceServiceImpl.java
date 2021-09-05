package com.oym.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.utils.CollectionUtils;
import com.oym.system.ctrl.vo.RoleResourceVO;
import com.oym.system.dao.RoleResourceDao;
import com.oym.system.domain.bo.RoleResourceSaveBO;
import com.oym.system.domain.dataobject.RoleResourceDO;
import com.oym.system.service.RoleResourceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统角色资源权限关联关系service实现
 *
 * @author oneyuanma
 * @date 2021/07/17
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceDao, RoleResourceDO> implements RoleResourceService {

    @Autowired
    private RoleResourceDao roleResourceDao;

    private static final String COMMA = ",";

    /**
     * 角色资源关联关系保存
     *
     * @param bo
     * @return
     */
    @Override
    public OymResult<Boolean> saveRoleResource(RoleResourceSaveBO bo) {
        // 先删除之前关联关系
        QueryWrapper<RoleResourceDO> query = Wrappers.query();
        query.eq("role_id", bo.getRoleId());
        roleResourceDao.delete(query);

        // 保存关联关系
        String[] resources = StringUtils.split(bo.getResourceIds(), COMMA);
        for (String resourceId : resources) {
            roleResourceDao.insert(new RoleResourceDO(bo.getRoleId(), Long.parseLong(resourceId)));
        }

        return OymResult.success(true);
    }

    /**
     * 根据角色id获取与资源的关联关系
     *
     * @param roleId
     * @return
     */
    @Override
    public RoleResourceVO getRoleResource(Long roleId) {
        QueryWrapper<RoleResourceDO> query = Wrappers.query();
        query.eq("role_id", roleId);
        List<RoleResourceDO> list = roleResourceDao.selectList(query);
        return new RoleResourceVO(CollectionUtils.asList(list, RoleResourceDO::getResourceId));
    }
}

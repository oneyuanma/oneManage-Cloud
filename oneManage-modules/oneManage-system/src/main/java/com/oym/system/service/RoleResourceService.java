package com.oym.system.service;


import com.oym.commons.base.response.OymResult;
import com.oym.system.ctrl.vo.RoleResourceVO;
import com.oym.system.domain.bo.RoleResourceSaveBO;

/**
 * 系统角色资源权限关联关系service
 *
 * @author oneyuanma
 * @date 2021/07/17
 */
public interface RoleResourceService {


    /**
     * 角色资源关联关系保存
     * @param bo
     * @return
     */
    OymResult<Boolean> saveRoleResource(RoleResourceSaveBO bo);


    /**
     * 根据角色id获取与资源的关联关系
     * @param roleId
     * @return
     */
    RoleResourceVO getRoleResource(Long roleId);
}

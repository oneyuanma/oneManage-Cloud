package com.oym.system.service;


import com.oym.commons.base.response.OymResult;
import com.oym.system.domain.bo.UserRoleSaveBO;
import com.oym.system.domain.dto.UserRoleDTO;

import java.util.List;

/**
 * 系统用户角色关联service
 *
 * @author oneyuanma
 * @date 2021/07/17
 */
public interface UserRoleService {

    /**
     * 用户角色关联关系保存
     *
     * @param bo
     * @return
     */
    OymResult<Boolean> saveUserRole(UserRoleSaveBO bo);

    /**
     * 根据多个用户获取用户和角色的关联关系
     *
     * @param userIds
     * @return
     */
    OymResult<List<UserRoleDTO>> getByUsers(List<Long> userIds);
}

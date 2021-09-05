package com.oym.system.service;


import com.github.pagehelper.PageInfo;
import com.oym.commons.base.response.OymResult;
import com.oym.system.ctrl.vo.RoleSelectVO;
import com.oym.system.ctrl.vo.RoleVO;
import com.oym.system.domain.bo.GetRoleListBO;
import com.oym.system.domain.bo.GetRolePageListBO;
import com.oym.system.domain.bo.RoleSaveBO;

import java.util.List;

/**
 * 系统角色service
 *
 * @author oneyuanma
 * @date 2021/07/17
 */
public interface RoleService {

    /**
     * 分页获取角色列表
     * @return
     */
    PageInfo<RoleVO> pageList(GetRolePageListBO bo);

    /**
     * 角色列表
     * @return
     */
    List<RoleVO> list(GetRoleListBO bo);

    /**
     * 角色下拉列表项
     * @return
     */
    List<RoleSelectVO> selectOptions();

    /**
     * 角色保存
     * @return
     */
    OymResult<Boolean> save(RoleSaveBO bo);

    /**
     * 角色删除
     * @return
     */
    Boolean delete(Long id);

}

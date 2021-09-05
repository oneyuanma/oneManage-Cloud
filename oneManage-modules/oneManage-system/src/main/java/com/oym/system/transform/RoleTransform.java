package com.oym.system.transform;

import com.oym.system.ctrl.request.GetRoleListRequest;
import com.oym.system.ctrl.request.GetRolePageListRequest;
import com.oym.system.ctrl.request.RoleResourceSaveRequest;
import com.oym.system.ctrl.request.RoleSaveRequest;
import com.oym.system.ctrl.vo.RoleVO;
import com.oym.system.domain.bo.GetRoleListBO;
import com.oym.system.domain.bo.GetRolePageListBO;
import com.oym.system.domain.bo.RoleResourceSaveBO;
import com.oym.system.domain.bo.RoleSaveBO;
import com.oym.system.domain.dataobject.RoleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统角色实体转换器
 *
 * @author oneyuanma
 * @date 2020/06/23
 */
@Mapper(componentModel = "spring")
public interface RoleTransform {

    RoleTransform INS = Mappers.getMapper(RoleTransform.class);


    RoleDO transfer(GetRolePageListBO bo);

    RoleVO transfer(RoleDO roleDO);

    RoleDO transfer(RoleSaveBO bo);

    GetRolePageListBO transfer(GetRolePageListRequest request);

    RoleSaveBO transfer(RoleSaveRequest request);

    GetRoleListBO transfer(GetRoleListRequest request);

    RoleDO transfer(GetRoleListBO bo);

    RoleResourceSaveBO transfer(RoleResourceSaveRequest request);
}

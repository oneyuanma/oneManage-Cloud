package com.oym.system.transform;

import com.oym.system.domain.dataobject.UserRoleDO;
import com.oym.system.domain.dto.UserRoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统用户角色关联关系实体转换器
 *
 * @author oneyuanma
 * @date 2020/06/23
 */
@Mapper(componentModel = "spring")
public interface UserRoleTransform {

    UserRoleTransform INS = Mappers.getMapper(UserRoleTransform.class);

    UserRoleDTO transfer(UserRoleDO serRoleDO);

}

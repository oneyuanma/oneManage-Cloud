package com.oym.system.transform;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统角色权限资源关联关系实体转换器
 *
 * @author oneyuanma
 * @date 2020/06/23
 */
@Mapper(componentModel = "spring")
public interface RoleResourceTransform {

    RoleResourceTransform INS = Mappers.getMapper(RoleResourceTransform.class);

}

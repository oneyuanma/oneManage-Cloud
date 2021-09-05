package com.oym.system.transform;

import com.oym.system.ctrl.request.ResourceSaveRequest;
import com.oym.system.ctrl.vo.*;
import com.oym.system.domain.bo.ResourceSaveBO;
import com.oym.system.domain.dataobject.ResourceDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 系统权限资源实体转换器
 *
 * @author oneyuanma
 * @date 2020/06/23
 */
@Mapper(componentModel = "spring")
public interface ResourceTransform {

    ResourceTransform INS = Mappers.getMapper(ResourceTransform.class);

    ResourceTreeVO transfer(ResourceDO resourceDO);

    ResourceSaveBO transfer(ResourceSaveRequest request);

    ResourceDO transfer(ResourceSaveBO bo);

    ResourceTreeVO transfer2TreeVO(ResourceDO resourceDO);

    @Mappings({
            @Mapping(target = "resId", source = "id"),
            @Mapping(target = "name", source = "title")
    }
    )
    ResourceSelectTreeVO transfer(ResourceTreeVO vo);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "label", source = "title")
    }
    )
    ResourceAssignmentTreeVO transfer2TreeVO(ResourceTreeVO vo);
}

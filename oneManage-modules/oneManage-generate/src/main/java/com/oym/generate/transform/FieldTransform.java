package com.oym.generate.transform;

import com.oym.generate.ctrl.request.FieldSaveRequest;
import com.oym.generate.ctrl.vo.FieldVO;
import com.oym.generate.domain.bo.FieldSaveBO;
import com.oym.generate.domain.dataobject.FieldDO;
import com.oym.generate.domain.dto.FieldDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字段描述信息实体转换器
 *
 * @author oneyuanma
 * @date 2020/06/23
 */
@Mapper(componentModel = "spring")
public interface FieldTransform {

    FieldTransform INS = Mappers.getMapper(FieldTransform.class);

    FieldDO transfer(FieldSaveBO fieldSaveBO);

    FieldVO transfer(FieldDO fieldDO);

    FieldSaveBO transfer(FieldSaveRequest request);

    FieldDO transfer(FieldDTO fieldDTO);

}

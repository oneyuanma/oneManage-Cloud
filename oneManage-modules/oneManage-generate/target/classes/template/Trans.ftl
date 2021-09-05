package com.oym.${mouldName}.transform;

import com.oym.${mouldName}.ctrl.request.Get${className}PageListRequest;
import com.oym.${mouldName}.ctrl.request.${className}SaveRequest;
import com.oym.${mouldName}.ctrl.vo.${className}VO;
import com.oym.${mouldName}.domain.bo.Get${className}PageListBO;
import com.oym.${mouldName}.domain.bo.${className}SaveBO;
import com.oym.${mouldName}.domain.dataobject.${className}DO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * ${functionComment}实体转换器
 *
 * @author ${author}
 * @Date ${date}
 */
@Mapper(componentModel = "spring")
public interface ${className}Transform {

    ${className}Transform INS = Mappers.getMapper(${className}Transform.class);

    Get${className}PageListBO transfer(Get${className}PageListRequest request);

    ${className}DO transfer(${className}SaveBO ${classNameParam}SaveBO);

    ${className}VO transfer(${className}DO ${classNameParam}DO);

    ${className}SaveBO transfer(${className}SaveRequest request);
}

package com.oym.generate.transform;

import com.oym.generate.ctrl.request.GetGenerateDemoPageListRequest;
import com.oym.generate.ctrl.request.GenerateDemoSaveRequest;
import com.oym.generate.ctrl.vo.GenerateDemoVO;
import com.oym.generate.domain.bo.GetGenerateDemoPageListBO;
import com.oym.generate.domain.bo.GenerateDemoSaveBO;
import com.oym.generate.domain.dataobject.GenerateDemoDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 代码生成工具演示demo实体转换器
 *
 * @author oneyuanma
 * @Date 2021/08/12
 */
@Mapper(componentModel = "spring")
public interface GenerateDemoTransform {

    GenerateDemoTransform INS = Mappers.getMapper(GenerateDemoTransform.class);

    GetGenerateDemoPageListBO transfer(GetGenerateDemoPageListRequest request);

    GenerateDemoDO transfer(GenerateDemoSaveBO generateDemoSaveBO);

    GenerateDemoVO transfer(GenerateDemoDO generateDemoDO);

    GenerateDemoSaveBO transfer(GenerateDemoSaveRequest request);
}

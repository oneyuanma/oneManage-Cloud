package com.oym.generate.transform;

import com.oym.generate.ctrl.request.GetTablePageListRequest;
import com.oym.generate.ctrl.request.TableSaveRequest;
import com.oym.generate.ctrl.vo.TableVO;
import com.oym.generate.domain.bo.GetTablePageListBO;
import com.oym.generate.domain.bo.TableSaveBO;
import com.oym.generate.domain.dataobject.TableDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统键值对实体转换器
 *
 * @author oneyuanma
 * @date 2020/06/23
 */
@Mapper(componentModel = "spring")
public interface TableTransform {

    TableTransform INS = Mappers.getMapper(TableTransform.class);

    GetTablePageListBO transfer(GetTablePageListRequest request);

    TableDO transfer(TableSaveBO tableSaveBO);

    TableVO transfer(TableDO tableDO);

    TableSaveBO transfer(TableSaveRequest request);
}

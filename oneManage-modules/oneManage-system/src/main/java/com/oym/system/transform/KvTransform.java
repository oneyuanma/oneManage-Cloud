package com.oym.system.transform;

import com.oym.system.ctrl.request.KvSaveRequest;
import com.oym.system.ctrl.vo.KvVO;
import com.oym.system.domain.bo.KvSaveBO;
import com.oym.system.domain.dataobject.KvDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统键值对实体转换器
 *
 * @author oneyuanma
 * @date 2020/06/23
 */
@Mapper(componentModel = "spring")
public interface KvTransform {

    KvTransform INS = Mappers.getMapper(KvTransform.class);

    KvDO transfer(KvSaveBO kvSaveBO);

    KvVO transfer(KvDO kvDO);

    KvSaveBO transfer(KvSaveRequest request);
}

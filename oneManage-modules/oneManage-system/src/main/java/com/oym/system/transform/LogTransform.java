package com.oym.system.transform;

import com.oym.log.api.domain.request.LogOperateSaveRequest;
import com.oym.system.domain.message.OperateMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 日志实体转换器
 *
 * @author oneyuanma
 * @date 2020/06/23
 */
@Mapper(componentModel = "spring")
public interface LogTransform {

    LogTransform INS = Mappers.getMapper(LogTransform.class);

    LogOperateSaveRequest transfer(OperateMessage message);
}

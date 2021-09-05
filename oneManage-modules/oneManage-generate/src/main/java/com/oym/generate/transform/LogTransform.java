package com.oym.generate.transform;

import com.oym.generate.domain.message.OperateMessage;
import com.oym.log.api.domain.request.LogOperateSaveRequest;
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

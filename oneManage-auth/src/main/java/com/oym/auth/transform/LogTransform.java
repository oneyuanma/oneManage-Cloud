package com.oym.auth.transform;

import com.oym.auth.domain.message.LoginMessage;
import com.oym.log.api.domain.request.LogLoginSaveRequest;
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

    LogLoginSaveRequest transfer(LoginMessage loginMessage);
}

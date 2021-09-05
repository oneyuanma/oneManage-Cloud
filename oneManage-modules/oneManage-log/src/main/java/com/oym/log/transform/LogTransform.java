package com.oym.log.transform;

import com.oym.log.api.domain.request.LogLoginSaveRequest;
import com.oym.log.api.domain.request.LogOperateSaveRequest;
import com.oym.log.ctrl.vo.LoginLogVO;
import com.oym.log.ctrl.vo.OperateLogVO;
import com.oym.log.domain.bo.LoginLogBO;
import com.oym.log.domain.bo.OperateLogBO;
import com.oym.log.domain.dataobject.LogLoginDO;
import com.oym.log.domain.dataobject.LogOperateDO;
import com.oym.log.domain.dto.LoginLogDTO;
import com.oym.log.domain.dto.OperateLogDTO;
import com.oym.log.domain.message.LoginMessage;
import com.oym.log.domain.message.OperateMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统日志实体转换器
 *
 * @author oneyuanma
 * @date 2020/06/23
 */
@Mapper(componentModel = "spring")
public interface LogTransform {

    LogTransform INS = Mappers.getMapper(LogTransform.class);

    LoginLogDTO transfer(LoginMessage message);

    LoginLogVO transfer(LoginLogDTO dto);

    OperateLogDTO transfer(OperateMessage message);

    OperateLogVO transfer(OperateLogDTO dto);

    LogLoginDO transfer(LoginLogBO bo);

    LogOperateDO transfer(OperateLogBO bo);

    LoginLogBO transfer(LogLoginSaveRequest req);

    OperateLogBO transfer(LogOperateSaveRequest req);

    LoginLogVO transfer(LogLoginDO logLoginDO);

    OperateLogVO transfer(LogOperateDO logOperateDO);
}

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
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-04T23:45:48+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
@Component
public class LogTransformImpl implements LogTransform {

    @Override
    public LoginLogDTO transfer(LoginMessage message) {
        if ( message == null ) {
            return null;
        }

        LoginLogDTO loginLogDTO = new LoginLogDTO();

        loginLogDTO.setId( message.getId() );
        loginLogDTO.setTitle( message.getTitle() );
        loginLogDTO.setUid( message.getUid() );
        loginLogDTO.setUserName( message.getUserName() );
        loginLogDTO.setIp( message.getIp() );
        loginLogDTO.setOperateSystem( message.getOperateSystem() );
        loginLogDTO.setBrowser( message.getBrowser() );
        loginLogDTO.setType( message.getType() );
        loginLogDTO.setStatus( message.getStatus() );
        loginLogDTO.setRemark( message.getRemark() );
        loginLogDTO.setTime( message.getTime() );

        return loginLogDTO;
    }

    @Override
    public LoginLogVO transfer(LoginLogDTO dto) {
        if ( dto == null ) {
            return null;
        }

        LoginLogVO loginLogVO = new LoginLogVO();

        loginLogVO.setId( dto.getId() );
        loginLogVO.setTitle( dto.getTitle() );
        loginLogVO.setUid( dto.getUid() );
        loginLogVO.setUserName( dto.getUserName() );
        loginLogVO.setIp( dto.getIp() );
        loginLogVO.setOperateSystem( dto.getOperateSystem() );
        loginLogVO.setBrowser( dto.getBrowser() );
        loginLogVO.setType( dto.getType() );
        loginLogVO.setStatus( dto.getStatus() );
        loginLogVO.setRemark( dto.getRemark() );
        loginLogVO.setTime( dto.getTime() );

        return loginLogVO;
    }

    @Override
    public OperateLogDTO transfer(OperateMessage message) {
        if ( message == null ) {
            return null;
        }

        OperateLogDTO operateLogDTO = new OperateLogDTO();

        operateLogDTO.setId( message.getId() );
        operateLogDTO.setModule( message.getModule() );
        operateLogDTO.setOperatePath( message.getOperatePath() );
        operateLogDTO.setType( message.getType() );
        operateLogDTO.setUid( message.getUid() );
        operateLogDTO.setUserName( message.getUserName() );
        operateLogDTO.setIp( message.getIp() );
        operateLogDTO.setParameter( message.getParameter() );
        operateLogDTO.setResult( message.getResult() );
        operateLogDTO.setExecuteTime( message.getExecuteTime() );
        operateLogDTO.setRemark( message.getRemark() );
        operateLogDTO.setTime( message.getTime() );

        return operateLogDTO;
    }

    @Override
    public OperateLogVO transfer(OperateLogDTO dto) {
        if ( dto == null ) {
            return null;
        }

        OperateLogVO operateLogVO = new OperateLogVO();

        operateLogVO.setId( dto.getId() );
        operateLogVO.setModule( dto.getModule() );
        operateLogVO.setOperatePath( dto.getOperatePath() );
        operateLogVO.setType( dto.getType() );
        operateLogVO.setUid( dto.getUid() );
        operateLogVO.setUserName( dto.getUserName() );
        operateLogVO.setIp( dto.getIp() );
        operateLogVO.setParameter( dto.getParameter() );
        operateLogVO.setResult( dto.getResult() );
        operateLogVO.setExecuteTime( dto.getExecuteTime() );
        operateLogVO.setRemark( dto.getRemark() );
        operateLogVO.setTime( dto.getTime() );

        return operateLogVO;
    }

    @Override
    public LogLoginDO transfer(LoginLogBO bo) {
        if ( bo == null ) {
            return null;
        }

        LogLoginDO logLoginDO = new LogLoginDO();

        logLoginDO.setTitle( bo.getTitle() );
        logLoginDO.setUid( bo.getUid() );
        logLoginDO.setUserName( bo.getUserName() );
        logLoginDO.setIp( bo.getIp() );
        logLoginDO.setOperateSystem( bo.getOperateSystem() );
        logLoginDO.setBrowser( bo.getBrowser() );
        logLoginDO.setType( bo.getType() );
        logLoginDO.setStatus( bo.getStatus() );
        logLoginDO.setRemark( bo.getRemark() );
        if ( bo.getTime() != null ) {
            logLoginDO.setTime( java.time.LocalDateTime.ofInstant( bo.getTime().toInstant(), java.time.ZoneId.of( "UTC" ) ) );
        }

        return logLoginDO;
    }

    @Override
    public LogOperateDO transfer(OperateLogBO bo) {
        if ( bo == null ) {
            return null;
        }

        LogOperateDO logOperateDO = new LogOperateDO();

        logOperateDO.setModule( bo.getModule() );
        logOperateDO.setOperatePath( bo.getOperatePath() );
        logOperateDO.setType( bo.getType() );
        logOperateDO.setUid( bo.getUid() );
        logOperateDO.setUserName( bo.getUserName() );
        logOperateDO.setIp( bo.getIp() );
        logOperateDO.setParameter( bo.getParameter() );
        logOperateDO.setResult( bo.getResult() );
        logOperateDO.setExecuteTime( bo.getExecuteTime() );
        logOperateDO.setRemark( bo.getRemark() );
        if ( bo.getTime() != null ) {
            logOperateDO.setTime( java.time.LocalDateTime.ofInstant( bo.getTime().toInstant(), java.time.ZoneId.of( "UTC" ) ) );
        }

        return logOperateDO;
    }

    @Override
    public LoginLogBO transfer(LogLoginSaveRequest req) {
        if ( req == null ) {
            return null;
        }

        LoginLogBO loginLogBO = new LoginLogBO();

        loginLogBO.setTitle( req.getTitle() );
        loginLogBO.setUid( req.getUid() );
        loginLogBO.setUserName( req.getUserName() );
        loginLogBO.setIp( req.getIp() );
        loginLogBO.setOperateSystem( req.getOperateSystem() );
        loginLogBO.setBrowser( req.getBrowser() );
        loginLogBO.setType( req.getType() );
        loginLogBO.setStatus( req.getStatus() );
        loginLogBO.setRemark( req.getRemark() );
        loginLogBO.setTime( req.getTime() );

        return loginLogBO;
    }

    @Override
    public OperateLogBO transfer(LogOperateSaveRequest req) {
        if ( req == null ) {
            return null;
        }

        OperateLogBO operateLogBO = new OperateLogBO();

        operateLogBO.setModule( req.getModule() );
        operateLogBO.setOperatePath( req.getOperatePath() );
        operateLogBO.setType( req.getType() );
        operateLogBO.setUid( req.getUid() );
        operateLogBO.setUserName( req.getUserName() );
        operateLogBO.setIp( req.getIp() );
        operateLogBO.setParameter( req.getParameter() );
        operateLogBO.setResult( req.getResult() );
        operateLogBO.setExecuteTime( req.getExecuteTime() );
        operateLogBO.setRemark( req.getRemark() );
        operateLogBO.setTime( req.getTime() );

        return operateLogBO;
    }

    @Override
    public LoginLogVO transfer(LogLoginDO logLoginDO) {
        if ( logLoginDO == null ) {
            return null;
        }

        LoginLogVO loginLogVO = new LoginLogVO();

        if ( logLoginDO.getId() != null ) {
            loginLogVO.setId( String.valueOf( logLoginDO.getId() ) );
        }
        loginLogVO.setTitle( logLoginDO.getTitle() );
        loginLogVO.setUid( logLoginDO.getUid() );
        loginLogVO.setUserName( logLoginDO.getUserName() );
        loginLogVO.setIp( logLoginDO.getIp() );
        loginLogVO.setOperateSystem( logLoginDO.getOperateSystem() );
        loginLogVO.setBrowser( logLoginDO.getBrowser() );
        loginLogVO.setType( logLoginDO.getType() );
        loginLogVO.setStatus( logLoginDO.getStatus() );
        loginLogVO.setRemark( logLoginDO.getRemark() );
        if ( logLoginDO.getTime() != null ) {
            loginLogVO.setTime( java.util.Date.from( logLoginDO.getTime().toInstant( java.time.ZoneOffset.UTC ) ) );
        }

        return loginLogVO;
    }

    @Override
    public OperateLogVO transfer(LogOperateDO logOperateDO) {
        if ( logOperateDO == null ) {
            return null;
        }

        OperateLogVO operateLogVO = new OperateLogVO();

        if ( logOperateDO.getId() != null ) {
            operateLogVO.setId( String.valueOf( logOperateDO.getId() ) );
        }
        operateLogVO.setModule( logOperateDO.getModule() );
        operateLogVO.setOperatePath( logOperateDO.getOperatePath() );
        operateLogVO.setType( logOperateDO.getType() );
        operateLogVO.setUid( logOperateDO.getUid() );
        operateLogVO.setUserName( logOperateDO.getUserName() );
        operateLogVO.setIp( logOperateDO.getIp() );
        operateLogVO.setParameter( logOperateDO.getParameter() );
        operateLogVO.setResult( logOperateDO.getResult() );
        operateLogVO.setExecuteTime( logOperateDO.getExecuteTime() );
        operateLogVO.setRemark( logOperateDO.getRemark() );
        if ( logOperateDO.getTime() != null ) {
            operateLogVO.setTime( java.util.Date.from( logOperateDO.getTime().toInstant( java.time.ZoneOffset.UTC ) ) );
        }

        return operateLogVO;
    }
}

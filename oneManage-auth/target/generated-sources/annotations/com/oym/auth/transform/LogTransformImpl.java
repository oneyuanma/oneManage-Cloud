package com.oym.auth.transform;

import com.oym.auth.domain.message.LoginMessage;
import com.oym.log.api.domain.request.LogLoginSaveRequest;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-04T23:45:54+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
@Component
public class LogTransformImpl implements LogTransform {

    @Override
    public LogLoginSaveRequest transfer(LoginMessage loginMessage) {
        if ( loginMessage == null ) {
            return null;
        }

        LogLoginSaveRequest logLoginSaveRequest = new LogLoginSaveRequest();

        logLoginSaveRequest.setTitle( loginMessage.getTitle() );
        logLoginSaveRequest.setUid( loginMessage.getUid() );
        logLoginSaveRequest.setUserName( loginMessage.getUserName() );
        logLoginSaveRequest.setIp( loginMessage.getIp() );
        logLoginSaveRequest.setOperateSystem( loginMessage.getOperateSystem() );
        logLoginSaveRequest.setBrowser( loginMessage.getBrowser() );
        logLoginSaveRequest.setType( loginMessage.getType() );
        logLoginSaveRequest.setStatus( loginMessage.getStatus() );
        logLoginSaveRequest.setRemark( loginMessage.getRemark() );
        logLoginSaveRequest.setTime( loginMessage.getTime() );

        return logLoginSaveRequest;
    }
}

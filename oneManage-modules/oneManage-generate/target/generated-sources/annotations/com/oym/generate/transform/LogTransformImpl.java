package com.oym.generate.transform;

import com.oym.generate.domain.message.OperateMessage;
import com.oym.log.api.domain.request.LogOperateSaveRequest;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-05T14:34:17+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
@Component
public class LogTransformImpl implements LogTransform {

    @Override
    public LogOperateSaveRequest transfer(OperateMessage message) {
        if ( message == null ) {
            return null;
        }

        LogOperateSaveRequest logOperateSaveRequest = new LogOperateSaveRequest();

        logOperateSaveRequest.setModule( message.getModule() );
        logOperateSaveRequest.setOperatePath( message.getOperatePath() );
        logOperateSaveRequest.setType( message.getType() );
        logOperateSaveRequest.setUid( message.getUid() );
        logOperateSaveRequest.setUserName( message.getUserName() );
        logOperateSaveRequest.setIp( message.getIp() );
        logOperateSaveRequest.setParameter( message.getParameter() );
        logOperateSaveRequest.setResult( message.getResult() );
        logOperateSaveRequest.setExecuteTime( message.getExecuteTime() );
        logOperateSaveRequest.setRemark( message.getRemark() );
        logOperateSaveRequest.setTime( message.getTime() );

        return logOperateSaveRequest;
    }
}

package com.oym.quartz.transform;

import com.oym.quartz.ctrl.request.JobSaveRequest;
import com.oym.quartz.ctrl.vo.JobVO;
import com.oym.quartz.domain.bo.JobSaveBO;
import com.oym.quartz.domain.dataobject.JobDO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-05T14:31:21+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
@Component
public class JobTransformImpl implements JobTransform {

    @Override
    public JobVO transfer(JobDO jobDO) {
        if ( jobDO == null ) {
            return null;
        }

        JobVO jobVO = new JobVO();

        jobVO.setId( jobDO.getId() );
        jobVO.setJobName( jobDO.getJobName() );
        jobVO.setJobDescription( jobDO.getJobDescription() );
        jobVO.setJobGroupName( jobDO.getJobGroupName() );
        jobVO.setJobClassName( jobDO.getJobClassName() );
        jobVO.setTriggerName( jobDO.getTriggerName() );
        jobVO.setTriggerGroupName( jobDO.getTriggerGroupName() );
        jobVO.setCronExpression( jobDO.getCronExpression() );
        jobVO.setTriggerState( jobDO.getTriggerState() );

        return jobVO;
    }

    @Override
    public JobDO transfer(JobSaveBO bo) {
        if ( bo == null ) {
            return null;
        }

        JobDO jobDO = new JobDO();

        jobDO.setId( bo.getId() );
        jobDO.setJobName( bo.getJobName() );
        jobDO.setJobDescription( bo.getJobDescription() );
        jobDO.setJobGroupName( bo.getJobGroupName() );
        jobDO.setJobClassName( bo.getJobClassName() );
        jobDO.setTriggerName( bo.getTriggerName() );
        jobDO.setTriggerGroupName( bo.getTriggerGroupName() );
        jobDO.setCronExpression( bo.getCronExpression() );
        jobDO.setTriggerState( bo.getTriggerState() );

        return jobDO;
    }

    @Override
    public JobSaveBO transfer(JobSaveRequest request) {
        if ( request == null ) {
            return null;
        }

        JobSaveBO jobSaveBO = new JobSaveBO();

        jobSaveBO.setId( request.getId() );
        jobSaveBO.setJobName( request.getJobName() );
        jobSaveBO.setJobDescription( request.getJobDescription() );
        jobSaveBO.setJobGroupName( request.getJobGroupName() );
        jobSaveBO.setJobClassName( request.getJobClassName() );
        jobSaveBO.setTriggerName( request.getTriggerName() );
        jobSaveBO.setTriggerGroupName( request.getTriggerGroupName() );
        jobSaveBO.setCronExpression( request.getCronExpression() );
        jobSaveBO.setTriggerState( request.getTriggerState() );

        return jobSaveBO;
    }
}

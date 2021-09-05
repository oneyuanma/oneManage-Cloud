package com.oym.quartz.transform;

import com.oym.quartz.ctrl.request.JobSaveRequest;
import com.oym.quartz.ctrl.vo.JobVO;
import com.oym.quartz.domain.bo.JobSaveBO;
import com.oym.quartz.domain.dataobject.JobDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 定时任务实体转换器
 *
 * @author oneyuanma
 * @date 2020/06/23
 */
@Mapper(componentModel = "spring")
public interface JobTransform {

    JobTransform INS = Mappers.getMapper(JobTransform.class);

    JobVO transfer(JobDO jobDO);

    JobDO transfer(JobSaveBO bo);

    JobSaveBO transfer(JobSaveRequest request);

}

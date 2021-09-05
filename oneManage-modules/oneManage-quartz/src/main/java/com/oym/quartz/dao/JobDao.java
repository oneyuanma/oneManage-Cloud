package com.oym.quartz.dao;

import com.oym.commons.base.dao.BaseDao;
import com.oym.quartz.domain.dataobject.JobDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 定时任务dao层
 *
 * @author oneyuanma
 * @date 2020/04/17
 */
@Repository
public interface JobDao extends BaseDao<JobDO> {

    /**
     * 定时任务列表
     *
     * @param kvDO
     * @return
     */
    List<JobDO> list(JobDO kvDO);
}

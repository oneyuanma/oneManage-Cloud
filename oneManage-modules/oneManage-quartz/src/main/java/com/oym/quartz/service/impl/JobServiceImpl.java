package com.oym.quartz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.cons.StatusEnum;
import com.oym.commons.utils.Argument;
import com.oym.commons.utils.CollectionUtils;
import com.oym.quartz.ctrl.vo.JobVO;
import com.oym.quartz.dao.JobDao;
import com.oym.quartz.domain.bo.GetJobPageListBO;
import com.oym.quartz.domain.bo.JobSaveBO;
import com.oym.quartz.domain.dataobject.JobDO;
import com.oym.quartz.job.BaseJob;
import com.oym.quartz.service.JobService;
import com.oym.quartz.transform.JobTransform;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时任务service实现
 *
 * @author oneyuanma
 * @date 2021/07/17
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobDao, JobDO> implements JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    private JobDao jobDao;
    @Autowired
    private Scheduler scheduler;

    /**
     * 分页获取定时任务列表
     *
     * @param bo
     * @return
     */
    @Override
    public PageInfo<JobVO> pageList(GetJobPageListBO bo) {
        // 分页
        PageHelper.startPage(bo.getNowPageIndex(), bo.getPageSize());
        // 键值对列表
        List<JobDO> kvDOS = jobDao.list(new JobDO(bo.getJobName(), bo.getJobDescription(), bo.getJobGroupName()));
        // 构建返回分页对象
        PageInfo pageInfo = new PageInfo(kvDOS);
        pageInfo.setList(CollectionUtils.convert(kvDOS, JobTransform.INS::transfer));
        return pageInfo;
    }

    /**
     * 定时任务保存
     *
     * @param bo
     * @return
     */
    @Override
    public OymResult<Boolean> save(JobSaveBO bo) {
        // 修改
        if (Argument.isNotNull(bo.getId())) {
            updateJob(bo.getJobClassName(), bo.getJobGroupName(), bo.getCronExpression());
            return OymResult.success(jobDao.updateById(JobTransform.INS.transfer(bo)) > 0);
        }
        // 新增
        addJob(bo.getJobClassName(), bo.getJobGroupName(), bo.getCronExpression());
        return OymResult.success(jobDao.insert(JobTransform.INS.transfer(bo)) > 0);
    }

    /**
     * 定时任务删除
     *
     * @param id
     * @return
     */
    @Override
    public Boolean delete(Long id) {
        JobDO job = jobDao.selectById(id);
        deleteJob(job.getJobClassName(), job.getJobGroupName());
        return jobDao.deleteById(id) > 0;

    }


    /**
     * 用户状态设置
     *
     * @param id
     * @return
     */
    @Override
    public OymResult<Boolean> statusSet(Long id) {
        JobDO job = jobDao.selectById(id);
        if (Argument.isNull(job)) {
            return OymResult.error("定时任务不存在");
        }
        // 设置用户状态
        String status = job.getTriggerState();
        if (StatusEnum.isNormal(status)) {
            job.setTriggerState(StatusEnum.DISABLE.getValue());
            pauseJob(job.getJobClassName(), job.getJobGroupName());
        } else {
            job.setTriggerState(StatusEnum.NORMAL.getValue());
            resumeJob(job.getJobClassName(), job.getJobGroupName());
        }
        return OymResult.success(jobDao.updateById(job) > 0);
    }

    /**
     * 定时任务新增
     *
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @return
     */
    @Override
    public Boolean addJob(String jobClassName, String jobGroupName, String cronExpression) {
        try {
            // 启动调度器
            scheduler.start();
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName, jobGroupName).build();
            // 表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName).withSchedule(scheduleBuilder).build();
            // 将 JobDetail 提供给 Scheduler，并将给定的 Trigger 与其关联。
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            logger.error("JobServiceImpl add job err, msg:{}", ExceptionUtils.getStackTrace(e));
            return false;
        }
        return true;
    }

    /**
     * 定时任务更新
     *
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @return
     */
    @Override
    public Boolean updateJob(String jobClassName, String jobGroupName, String cronExpression) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (Exception e) {
            logger.error("JobServiceImpl update job err, msg:{}", ExceptionUtils.getStackTrace(e));
            return false;
        }
        return true;
    }

    /**
     * 定时任务删除
     *
     * @param jobClassName
     * @param jobGroupName
     * @return
     */
    @Override
    public Boolean deleteJob(String jobClassName, String jobGroupName) {
        try {
            // 删除定时任务
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
            scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (Exception e) {
            logger.error("JobServiceImpl delete job err, msg:{}", ExceptionUtils.getStackTrace(e));
            return false;
        }
        return true;
    }

    /**
     * 定时任务暂停
     *
     * @param jobClassName
     * @param jobGroupName
     * @return
     */
    @Override
    public Boolean pauseJob(String jobClassName, String jobGroupName) {
        try {
            // 暂定任务
            scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (Exception e) {
            logger.error("JobServiceImpl pause job err, msg:{}", ExceptionUtils.getStackTrace(e));
            return false;
        }
        return true;
    }

    /**
     * 定时任务恢复
     *
     * @param jobClassName
     * @param jobGroupName
     * @return
     */
    @Override
    public Boolean resumeJob(String jobClassName, String jobGroupName) {
        try {
            // 恢复任务
            scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (Exception e) {
            logger.error("JobServiceImpl resume job err, msg:{}", ExceptionUtils.getStackTrace(e));
            return false;
        }
        return true;
    }

    /**
     * 执行一次
     *
     * @param id
     * @return
     */
    @Override
    public Boolean runJob(Long id) {
        try {
            JobDO job = jobDao.selectById(id);
            if (Argument.isNull(job)) {
                return false;
            }
            // 恢复任务
            scheduler.triggerJob(JobKey.jobKey(job.getJobClassName(), job.getJobGroupName()));
        } catch (Exception e) {
            logger.error("JobServiceImpl resume job err, msg:{}", ExceptionUtils.getStackTrace(e));
            return false;
        }
        return true;
    }

    /**
     * 获取class
     *
     * @param classname
     * @return
     * @throws Exception
     */
    public static BaseJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob) class1.newInstance();
    }
}

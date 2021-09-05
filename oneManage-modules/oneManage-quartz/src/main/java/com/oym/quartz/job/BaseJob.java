package com.oym.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时任务基类
 *
 * @author oneyuanma
 * @date 2021/07/27
 */
public interface BaseJob extends Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException;
}

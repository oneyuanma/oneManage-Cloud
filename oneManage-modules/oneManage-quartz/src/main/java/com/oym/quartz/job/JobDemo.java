package com.oym.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class JobDemo implements BaseJob {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(new Date() + "定时任务Demo 任务开始------------------------------------");
        // 具体定时任务需要执行的业务
        System.out.println("定时任务Demo 工作中");
        System.out.println(new Date() + "定时任务Demo 任务结束------------------------------------");
    }
}

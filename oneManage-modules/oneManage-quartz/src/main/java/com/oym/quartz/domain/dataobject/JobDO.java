package com.oym.quartz.domain.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oym.commons.base.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 定时任务实体类
 *
 * @author oneyuanma
 * @date 2021/06/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_job")
@NoArgsConstructor
public class JobDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = -908257093621902519L;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务描述
     */
    private String jobDescription;

    /**
     * 任务组名
     */
    private String jobGroupName;

    /**
     * 类名
     */
    private String jobClassName;

    /**
     * 触发类
     */
    private String triggerName;

    /**
     * 触发组
     */
    private String triggerGroupName;

    /**
     * cron 表达式
     */
    private String cronExpression;

    /**
     * 状态
     */
    private String triggerState;

    public JobDO(String jobName, String jobDescription, String jobGroupName) {
        this.jobName = jobName;
        this.jobDescription = jobDescription;
        this.jobGroupName = jobGroupName;
    }
}

package com.oym.quartz.domain.bo;

import com.oym.commons.base.request.AbstractQuery;
import lombok.Data;

/**
 * 定时任务保存 BO
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
public class JobSaveBO extends AbstractQuery {

    private static final long serialVersionUID = 1883605514721169292L;

    /**
     * id
     */
    private Long id;

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
}

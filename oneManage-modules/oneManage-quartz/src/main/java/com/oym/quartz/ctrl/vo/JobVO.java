package com.oym.quartz.ctrl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class JobVO implements Serializable {

    private static final long serialVersionUID = -908257093621902519L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "任务描述")
    private String jobDescription;

    @ApiModelProperty(value = "任务组名")
    private String jobGroupName;

    @ApiModelProperty(value = "类名")
    private String jobClassName;

    @ApiModelProperty(value = "触发类")
    private String triggerName;

    @ApiModelProperty(value = "触发组")
    private String triggerGroupName;

    @ApiModelProperty(value = "cron表达式")
    private String cronExpression;

    @ApiModelProperty(value = "状态")
    private String triggerState;
}

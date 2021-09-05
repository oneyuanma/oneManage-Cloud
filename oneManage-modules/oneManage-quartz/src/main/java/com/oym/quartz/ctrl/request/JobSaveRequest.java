package com.oym.quartz.ctrl.request;

import com.oym.commons.base.request.PageAbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 定时任务保存 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "定时任务保存 Request")
public class JobSaveRequest extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

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

    @ApiModelProperty(value = "cron 表达式")
    private String cronExpression;

    @ApiModelProperty(value = "状态")
    private String triggerState;
}

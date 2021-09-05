package com.oym.quartz.ctrl.request;

import com.oym.commons.base.request.PageAbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页获取定时任务信息 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "分页获取定时任务信息 Request")
public class GetJobPageListRequest extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "任务描述")
    private String jobDescription;

    @ApiModelProperty(value = "任务组名")
    private String jobGroupName;
}

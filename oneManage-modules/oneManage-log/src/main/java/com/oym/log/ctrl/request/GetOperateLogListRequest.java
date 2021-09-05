package com.oym.log.ctrl.request;

import com.oym.commons.base.request.PageAbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取操作日志列表 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "获取操作日志列表 Request")
public class GetOperateLogListRequest extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    @ApiModelProperty(value = "模块名称")
    private String module;

    @ApiModelProperty(value = "操作位置")
    private String oparatePath;
}

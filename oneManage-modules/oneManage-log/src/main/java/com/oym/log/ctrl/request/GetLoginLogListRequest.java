package com.oym.log.ctrl.request;

import com.oym.commons.base.request.PageAbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取登录日志列表 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "获取登录日志列表 Request")
public class GetLoginLogListRequest extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    @ApiModelProperty(value = "日志标题")
    private String title;

    @ApiModelProperty(value = "登录账号名")
    private String userName;
}

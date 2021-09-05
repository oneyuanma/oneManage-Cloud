package com.oym.system.ctrl.request;

import com.oym.commons.base.request.PageAbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户设置 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "用户设置 Request")
public class UserSetRequest extends PageAbstractQuery {

    private static final long serialVersionUID = 3210542902973778640L;

    @ApiModelProperty(value = "id")
    private Long id;
}

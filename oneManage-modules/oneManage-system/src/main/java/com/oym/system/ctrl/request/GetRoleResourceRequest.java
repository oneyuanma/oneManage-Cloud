package com.oym.system.ctrl.request;

import com.oym.commons.base.request.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 获取角色资源关联关系列表 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "获取角色资源关联关系列表 Request")
public class GetRoleResourceRequest extends AbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    @NotNull
    @ApiModelProperty(value = "角色id")
    private Long roleId;

}

package com.oym.system.ctrl.request;

import com.oym.commons.base.request.PageAbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 角色资源关联关系保存 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "角色资源关联关系保存 Request")
public class RoleResourceSaveRequest extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    @NotNull
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @NotBlank
    @ApiModelProperty(value = "资源权限ids, 逗号隔开")
    private String resourceIds;

}

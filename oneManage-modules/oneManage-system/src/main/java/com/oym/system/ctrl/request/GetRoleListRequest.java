package com.oym.system.ctrl.request;

import com.oym.commons.base.request.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取角色列表 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "获取角色列表 Request")
public class GetRoleListRequest extends AbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;


    @ApiModelProperty(value = "角色编码")
    private String code;

    @ApiModelProperty(value = "角色名称")
    private String name;
}

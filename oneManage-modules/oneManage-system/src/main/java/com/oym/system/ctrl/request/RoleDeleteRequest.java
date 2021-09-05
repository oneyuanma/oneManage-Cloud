package com.oym.system.ctrl.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色删除 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "角色删除 Request")
public class RoleDeleteRequest {

    private static final long serialVersionUID = 6217714199061006731L;

    @ApiModelProperty(value = "id")
    private Long id;

}

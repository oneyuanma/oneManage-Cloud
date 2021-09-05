package com.oym.system.ctrl.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源权限删除 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "资源权限删除 Request")
public class ResourceDeleteRequest {

    private static final long serialVersionUID = 5089846059946515846L;

    @ApiModelProperty(value = "id")
    private Long id;

}

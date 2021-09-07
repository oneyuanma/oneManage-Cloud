package com.oym.system.ctrl.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 根据键获取值 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "根据键获取值 Request")
public class GetKvRequest {

    private static final long serialVersionUID = 8218658927355870227L;

    @ApiModelProperty(value = "键")
    private String k;

}

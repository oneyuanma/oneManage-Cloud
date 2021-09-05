package com.oym.generate.ctrl.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 代码生成 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "代码生成 Request")
public class TableGenerateRequest {

    private static final long serialVersionUID = 2755995279191171360L;

    @ApiModelProperty(value = "表信息id")
    private Long tableId;

}

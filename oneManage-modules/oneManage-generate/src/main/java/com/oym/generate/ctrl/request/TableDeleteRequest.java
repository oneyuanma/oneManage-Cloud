package com.oym.generate.ctrl.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 表描述信息删除 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "表描述信息删除 Request")
public class TableDeleteRequest {

    private static final long serialVersionUID = 2755995279191171360L;

    @ApiModelProperty(value = "id")
    private Long id;

}

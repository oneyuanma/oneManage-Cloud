package com.oym.generate.ctrl.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 代码生成工具演示demo删除 request
 *
 * @author oneyuanma
 * @Date 2021/08/12
 */
@Data
@ApiModel(description = "代码生成工具演示demo删除 Request")
public class GenerateDemoDeleteRequest {

    private static final long serialVersionUID = 2755995279191171360L;

    @ApiModelProperty(value = "id")
    private Long id;

}

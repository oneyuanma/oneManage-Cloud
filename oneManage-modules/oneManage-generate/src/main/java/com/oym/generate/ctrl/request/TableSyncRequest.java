package com.oym.generate.ctrl.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 表描述信息同步 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "表描述信息同步 Request")
public class TableSyncRequest {

    private static final long serialVersionUID = 2755995279191171360L;

    @ApiModelProperty(value = "表明")
    private String tableName;

}

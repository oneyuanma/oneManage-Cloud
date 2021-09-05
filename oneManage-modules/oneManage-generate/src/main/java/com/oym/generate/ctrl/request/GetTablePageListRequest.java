package com.oym.generate.ctrl.request;

import com.oym.commons.base.request.PageAbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页获取表描述信息 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "分页获取表描述信息 Request")
public class GetTablePageListRequest extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    /**
     * 表名称
     */
    @ApiModelProperty(value = "表名称")
    private String tableName;

    /**
     * 表描述
     */
    @ApiModelProperty(value = "表描述")
    private String tableDescription;

    /**
     * 作者
     */
    @ApiModelProperty(value = "作者")
    private String author;
}

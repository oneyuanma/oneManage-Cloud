package com.oym.generate.ctrl.request;

import com.oym.commons.base.request.PageAbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页获取字段描述信息 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "分页获取字段描述信息 Request")
public class GetFieldPageListRequest extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    /**
     * 字段名
     */
    @ApiModelProperty(value = "字段名")
    private String fieldName;

    /**
     * 字段描述
     */
    @ApiModelProperty(value = "字段描述")
    private String fieldDescription;

}

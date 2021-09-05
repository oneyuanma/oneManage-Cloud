package com.oym.system.ctrl.request;

import com.oym.commons.base.request.PageAbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 键值对保存 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "键值对保存 Request")
public class KvSaveRequest extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "键")
    private String k;

    @ApiModelProperty(value = "值")
    private String v;

    @ApiModelProperty(value = "备注")
    private String remarks;
}

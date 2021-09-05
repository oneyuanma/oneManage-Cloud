package com.oym.${mouldName}.ctrl.request;

import com.oym.commons.base.request.PageAbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ${functionComment}保存 request
 *
 * @author ${author}
 * @Date ${date}
 */
@Data
@ApiModel(description = "${functionComment}保存 Request")
public class ${className}SaveRequest extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    <#list columns as c>
        <#if c.ifEdit == "true">
    /**
     * ${c.columnComment}
     */
    @ApiModelProperty(value = "${c.columnComment}")
    private ${c.columnType} ${ c.columnName};
        </#if>
    </#list>

}

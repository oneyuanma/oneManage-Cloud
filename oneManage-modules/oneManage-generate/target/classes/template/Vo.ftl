package com.oym.${mouldName}.ctrl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * ${functionComment} vo
 *
 * @author ${author}
 * @Date ${date}
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ${className}VO implements Serializable {

    private static final long serialVersionUID = -5051503697914734693L;

    @ApiModelProperty(value = "id")
    private Long id;

    <#list columns as c>
    <#if c.ifList == "true">
    /**
    * ${c.columnComment}
    */
    @ApiModelProperty(value = "${c.columnComment}")
    private ${c.columnType} ${ c.columnName};
    </#if>
    </#list>

}

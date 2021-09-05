package com.oym.${mouldName}.domain.bo;

import com.oym.commons.base.request.AbstractQuery;
import lombok.Data;

/**
 * ${functionComment}保存 BO
 *
 * @author ${author}
 * @Date ${date}
 */
@Data
public class ${className}SaveBO extends AbstractQuery {

    private static final long serialVersionUID = 1883605514721169292L;

    private Long id;

    <#list columns as c>
    <#if c.ifEdit == "true">
    /**
     * ${c.columnComment}
     */
    private ${c.columnType} ${ c.columnName};
    </#if>
    </#list>
}

package com.oym.${mouldName}.domain.bo;

import com.oym.commons.base.request.PageAbstractQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ${functionComment}分页列表 bo
 *
 * @author ${author}
 * @Date ${date}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Get${className}PageListBO extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    <#list columns as c>
    <#if c.ifQuery == "true">
    /**
     * ${c.columnComment}
     */
    private ${c.columnType} ${ c.columnName};
    </#if>
    </#list>
}

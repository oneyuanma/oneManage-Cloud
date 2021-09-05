package com.oym.generate.domain.bo;

import com.oym.commons.base.request.PageAbstractQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页获取表描述信息信息 BO
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFieldPageListBO extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段描述
     */
    private String fieldDescription;

}

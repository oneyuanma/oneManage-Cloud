package com.oym.system.domain.bo;

import com.oym.commons.base.request.PageAbstractQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页获取键值对信息 BO
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetKvPageListBO extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    private String k;

    private String v;
}

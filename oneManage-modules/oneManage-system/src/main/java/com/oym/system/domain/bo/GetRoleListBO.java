package com.oym.system.domain.bo;

import com.oym.commons.base.request.PageAbstractQuery;
import lombok.Data;

/**
 * 获取角色列表 BO
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
public class GetRoleListBO extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    private String code;

    private String name;
}

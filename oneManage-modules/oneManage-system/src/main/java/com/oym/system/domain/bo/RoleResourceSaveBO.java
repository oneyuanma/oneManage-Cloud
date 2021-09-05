package com.oym.system.domain.bo;

import com.oym.commons.base.request.PageAbstractQuery;
import lombok.Data;

/**
 * 角色保存 BO
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
public class RoleResourceSaveBO extends PageAbstractQuery {

    private static final long serialVersionUID = 1883605514721169292L;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 资源ids
     */
    private String resourceIds;
}

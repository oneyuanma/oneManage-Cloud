package com.oym.system.domain.bo;

import com.oym.commons.base.request.AbstractQuery;
import lombok.Data;

/**
 * 角色保存 BO
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
public class RoleSaveBO extends AbstractQuery {

    private static final long serialVersionUID = 1883605514721169292L;

    private Long id;

    /**
     * 角色编码
     */
    private String code;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色描述
     */
    private String description;
}

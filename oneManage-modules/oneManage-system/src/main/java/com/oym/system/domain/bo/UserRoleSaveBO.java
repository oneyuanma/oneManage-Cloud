package com.oym.system.domain.bo;

import com.oym.commons.base.request.AbstractQuery;
import lombok.Data;

/**
 * 用户保存 BO
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
public class UserRoleSaveBO extends AbstractQuery {

    private static final long serialVersionUID = -29131609417671448L;

    private Long id;

    private Long[] roles;

    public UserRoleSaveBO() {
    }
    public UserRoleSaveBO(Long id, Long[] roles) {
        this.id = id;
        this.roles = roles;
    }
}

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
public class UserSaveBO extends AbstractQuery {

    private static final long serialVersionUID = 1883605514721169292L;

    private Long id;

    private String username;

    private String nickName;

    private String password;

    private String email;

    private String phone;

    private String sex;

    private Long[] roles;
}

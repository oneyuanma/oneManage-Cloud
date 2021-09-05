package com.oym.system.domain.bo;

import com.oym.commons.base.request.PageAbstractQuery;
import lombok.Data;

/**
 * 分页获取用户信息 BO
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
public class GetUserPageListBO extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    private String username;

    private String nickName;
}

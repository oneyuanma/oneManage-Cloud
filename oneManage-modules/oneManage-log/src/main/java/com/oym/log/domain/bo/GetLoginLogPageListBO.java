package com.oym.log.domain.bo;

import com.oym.commons.base.request.PageAbstractQuery;
import lombok.Data;

/**
 * 登录日志 bo
 *
 * @author zsh
 * @date 2020/7/6
 */
@Data
public class GetLoginLogPageListBO extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 登录账号名
     */
    private String userName;

    public GetLoginLogPageListBO(String title, String userName) {
        this.title = title;
        this.userName = userName;
    }
}

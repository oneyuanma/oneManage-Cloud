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
public class GetOperateLogPageListBO extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    /**
     * 模块名称
     */
    private String module;

    /**
     * 操作位置
     */
    private String operatePath;

    public GetOperateLogPageListBO(String module, String operatePath) {
        this.module = module;
        this.operatePath = operatePath;
    }
}

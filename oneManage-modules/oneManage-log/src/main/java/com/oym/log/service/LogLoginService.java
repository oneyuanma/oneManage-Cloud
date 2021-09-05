package com.oym.log.service;


import com.github.pagehelper.PageInfo;
import com.oym.commons.base.response.OymResult;
import com.oym.log.ctrl.vo.LoginLogVO;
import com.oym.log.domain.bo.GetLoginLogPageListBO;
import com.oym.log.domain.bo.LoginLogBO;

/**
 * 登录日志service
 *
 * @author oneyuanma
 * @date 2020/04/17
 */
public interface LogLoginService {

    /**
     * 登录日志保存
     * @return
     */
    OymResult<Boolean> save(LoginLogBO bo);

    /**
     * 分页获取登录日志列表
     * @return
     */
    PageInfo<LoginLogVO> pageList(GetLoginLogPageListBO bo);

}

package com.oym.log.service;


import com.github.pagehelper.PageInfo;
import com.oym.commons.base.response.OymResult;
import com.oym.log.ctrl.vo.OperateLogVO;
import com.oym.log.domain.bo.GetOperateLogPageListBO;
import com.oym.log.domain.bo.OperateLogBO;

/**
 * 操作日志service
 *
 * @author oneyuanma
 * @date 2020/04/17
 */
public interface LogOperateService {

    /**
     * 操作日志保存
     * @return
     */
    OymResult<Boolean> save(OperateLogBO bo);

    /**
     * 分页获取操作日志列表
     * @param bo
     * @return
     */
    PageInfo<OperateLogVO> pageList(GetOperateLogPageListBO bo);
}

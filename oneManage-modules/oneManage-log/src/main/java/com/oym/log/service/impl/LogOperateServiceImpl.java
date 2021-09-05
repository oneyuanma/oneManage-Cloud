package com.oym.log.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.utils.CollectionUtils;
import com.oym.log.ctrl.vo.OperateLogVO;
import com.oym.log.dao.LogOperateDao;
import com.oym.log.domain.bo.GetOperateLogPageListBO;
import com.oym.log.domain.bo.OperateLogBO;
import com.oym.log.domain.dataobject.LogOperateDO;
import com.oym.log.service.LogOperateService;
import com.oym.log.transform.LogTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作日志service实现
 *
 * @author oneyuanma
 * @date 2020/04/17
 */
@Service
public class LogOperateServiceImpl extends ServiceImpl<LogOperateDao, LogOperateDO> implements LogOperateService {

    @Autowired
    private LogOperateDao logOperateDao;

    /**
     * 操作日志保存
     *
     * @param bo
     * @return
     */
    @Override
    public OymResult<Boolean> save(OperateLogBO bo) {
        LogOperateDO logOperateDO = LogTransform.INS.transfer(bo);
        logOperateDO.setTime(LocalDateTime.now());
        return OymResult.success(logOperateDao.insert(logOperateDO) > 0);
    }

    /**
     * 分页获取操作日志
     *
     * @param bo
     * @return
     */
    @Override
    public PageInfo<OperateLogVO> pageList(GetOperateLogPageListBO bo) {
        // 分页
        PageHelper.startPage(bo.getNowPageIndex(), bo.getPageSize());
        // 查询登录日志列表
        List<LogOperateDO> logOperateDOS = logOperateDao.list(new LogOperateDO(bo.getModule(), bo.getOperatePath()));
        // 构建分页数据
        PageInfo pageInfo = new PageInfo(logOperateDOS);
        pageInfo.setList(CollectionUtils.convert(logOperateDOS, LogTransform.INS::transfer));
        return pageInfo;
    }
}

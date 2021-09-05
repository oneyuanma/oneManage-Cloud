package com.oym.log.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.utils.CollectionUtils;
import com.oym.log.ctrl.vo.LoginLogVO;
import com.oym.log.dao.LogLoginDao;
import com.oym.log.domain.bo.GetLoginLogPageListBO;
import com.oym.log.domain.bo.LoginLogBO;
import com.oym.log.domain.dataobject.LogLoginDO;
import com.oym.log.service.LogLoginService;
import com.oym.log.transform.LogTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 登录日志service实现
 *
 * @author oneyuanma
 * @date 2020/04/17
 */
@Service
public class LogLoginServiceImpl extends ServiceImpl<LogLoginDao, LogLoginDO> implements LogLoginService {

    @Autowired
    private LogLoginDao logLoginDao;

    /**
     * 登录日志保存
     *
     * @param bo
     * @return
     */
    @Override
    public OymResult<Boolean> save(LoginLogBO bo) {
        LogLoginDO logLoginDO = LogTransform.INS.transfer(bo);
        logLoginDO.setTime(LocalDateTime.now());
        return OymResult.success(logLoginDao.insert(logLoginDO) > 0);
    }

    /**
     * 分页获取登录日志
     *
     * @param bo
     * @return
     */
    @Override
    public PageInfo<LoginLogVO> pageList(GetLoginLogPageListBO bo) {
        // 分页
        PageHelper.startPage(bo.getNowPageIndex(), bo.getPageSize());
        // 查询登录日志列表
        List<LogLoginDO> logLoginDOS = logLoginDao.list(new LogLoginDO(bo.getTitle(), bo.getUserName()));
        // 构建分页数据
        PageInfo pageInfo = new PageInfo(logLoginDOS);
        pageInfo.setList(CollectionUtils.convert(logLoginDOS, LogTransform.INS::transfer));
        return pageInfo;
    }
}

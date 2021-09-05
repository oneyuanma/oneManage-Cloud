package com.oym.log.dao;

import com.oym.commons.base.dao.BaseDao;
import com.oym.log.domain.dataobject.LogLoginDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 登录日志dao层
 *
 * @author oneyuanma
 * @date 2020/04/17
 */
@Repository
public interface LogLoginDao extends BaseDao<LogLoginDO> {

    /**
     * 登录日志列表
     *
     * @param logLoginDO
     * @return
     */
    List<LogLoginDO> list(LogLoginDO logLoginDO);

}

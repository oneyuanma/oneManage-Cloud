package com.oym.log.dao;

import com.oym.commons.base.dao.BaseDao;
import com.oym.log.domain.dataobject.LogOperateDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作日志dao层
 *
 * @author oneyuanma
 * @date 2020/04/17
 */
@Repository
public interface LogOperateDao extends BaseDao<LogOperateDO> {

    /**
     * 操作日志列表
     *
     * @param logOperateDO
     * @return
     */
    List<LogOperateDO> list(LogOperateDO logOperateDO);

}

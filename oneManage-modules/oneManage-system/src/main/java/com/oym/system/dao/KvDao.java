package com.oym.system.dao;

import com.oym.commons.base.dao.BaseDao;
import com.oym.system.domain.dataobject.KvDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统键值对dao层
 *
 * @author oneyuanma
 * @date 2020/04/17
 */
@Repository
public interface KvDao extends BaseDao<KvDO> {

    /**
     * 根据key获取键值对
     *
     * @param key
     * @return
     */
    Integer getCountByKey(@Param("k") String key);

    /**
     * 键值对列表
     *
     * @param kvDO
     * @return
     */
    List<KvDO> list(KvDO kvDO);
}

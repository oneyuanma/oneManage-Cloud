package com.oym.generate.dao;

import com.oym.commons.base.dao.BaseDao;
import com.oym.generate.domain.dataobject.TableFieldDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 表和字段关联关系dao层
 *
 * @author oneyuanma
 * @date 2020/04/17
 */
@Repository
public interface TableFieldDao extends BaseDao<TableFieldDO> {

    /**
     * 表描述信息列表
     *
     * @param taableFieldDO
     * @return
     */
    List<TableFieldDO> list(TableFieldDO taableFieldDO);


    /**
     * 根据表id获取关联关系
     *
     * @param tableId
     * @return
     */
    List<TableFieldDO> getByTableId(@Param("tableId") Long tableId);
}

package com.oym.generate.dao;

import com.oym.commons.base.dao.BaseDao;
import com.oym.generate.domain.dataobject.FieldDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字段描述信息dao层
 *
 * @author oneyuanma
 * @date 2020/04/17
 */
@Repository
public interface FieldDao extends BaseDao<FieldDO> {

    /**
     * 字段描述信息列表
     *
     * @param fieldDO
     * @return
     */
    List<FieldDO> list(FieldDO fieldDO);

}

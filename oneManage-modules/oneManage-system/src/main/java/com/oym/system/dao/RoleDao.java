package com.oym.system.dao;

import com.oym.commons.base.dao.BaseDao;
import com.oym.system.domain.dataobject.RoleDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统角色dao层
 *
 * @author oneyuanma
 * @date 2020/04/17
 */
@Repository
public interface RoleDao extends BaseDao<RoleDO> {

    /**
     * 角色列表
     *
     * @param roleDO
     * @return List<RoleDO>
     */
    List<RoleDO> list(RoleDO roleDO);

    /**
     * 根据条件获取角色数量
     *
     * @param code, name
     * @return Integer
     */
    Integer getCountByName(@Param("code") String code, @Param("name") String name);

}

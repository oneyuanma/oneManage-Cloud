package com.oym.system.dao;

import com.oym.commons.base.dao.BaseDao;
import com.oym.system.domain.dataobject.ResourceDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统权限资源dao层
 *
 * @author oneyuanma
 * @date 2020/04/17
 */
@Repository
public interface ResourceDao extends BaseDao<ResourceDO> {

    /**
     * 资源权限列表
     *
     * @param resourceDO
     * @return List<ResourceDO>
     */
    List<ResourceDO> list(ResourceDO resourceDO);

    /**
     * 所有资源权限列表
     *
     * @return List<ResourceDO>
     */
    List<ResourceDO> listAll();

    /**
     * 根据父级资源获取列表
     * @param parentId
     * @return
     */
    List<ResourceDO> listByParentId(@Param("parentId") Long parentId);

    /**
     * 根据资源编码获取用户信息
     *
     * @param code
     * @return Integer
     */
    Integer getCountByCode(@Param("code") String code);

}

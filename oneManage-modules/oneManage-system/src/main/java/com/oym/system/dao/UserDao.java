package com.oym.system.dao;

import com.oym.commons.base.dao.BaseDao;
import com.oym.system.domain.dataobject.UserDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统用户dao层
 *
 * @author oneyuanma
 * @date 2020/04/17
 */
@Repository
public interface UserDao extends BaseDao<UserDO> {

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return UserDO
     */
    UserDO getUserByName(@Param("username") String username);

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return Integer
     */
    Integer getCountByName(@Param("username") String username);

    /**
     * 用户列表
     *
     * @param userDO
     * @return List<UserDO>
     */
    List<UserDO> list(UserDO userDO);
}

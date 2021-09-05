package com.oym.system.service;


import com.github.pagehelper.PageInfo;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.user.UserLoginInfo;
import com.oym.system.domain.bo.GetUserPageListBO;
import com.oym.system.domain.bo.UserSaveBO;
import com.oym.system.ctrl.vo.UserInfoVO;
import com.oym.system.ctrl.vo.UserVO;

/**
 * 系统用户service
 *
 * @author oneyuanma
 * @date 2021/07/17
 */
public interface UserService {

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    UserLoginInfo getUserByName(String username);

    /**
     * 保存用户登录信息
     * @param userId
     * @param ip
     * @return
     */
    Boolean saveLoginInfo(Long userId, String ip);

    /**
     * 获取用户信息
     *
     * @return
     */
    UserInfoVO getUserInfo(Long userId);

    /**
     * 校验当前用户是否有改url的访问权限
     * @param userId
     * @param url
     * @return
     */
    Boolean privilegeCheck(Long userId, String url);

    /**
     * 分页获取用户列表
     * @return
     */
    PageInfo<UserVO> pageList(GetUserPageListBO bo);

    /**
     * 用户保存
     * @return
     */
    OymResult<Boolean> save(UserSaveBO bo);

    /**
     * 用户删除
     * @return
     */
    Boolean delete(Long id);

    /**
     * 用户状态设置
     * @return
     */
    OymResult<Boolean> set(Long id);
}

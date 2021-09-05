package com.oym.system.feign;

import com.oym.commons.base.response.OymResponse;
import com.oym.commons.user.UserLoginInfo;
import com.oym.commons.utils.Argument;
import com.oym.system.api.UserApi;
import com.oym.system.api.domain.request.GetUserRequest;
import com.oym.system.api.domain.request.PrivilegeCheckRequest;
import com.oym.system.api.domain.request.SaveUserLoginInfoRequest;
import com.oym.system.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户api实现
 *
 * @author oneyuanma
 * @date 2021/06/22
 */
@RestController
public class UserFeignImpl implements UserApi {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public String demo(String name) {
        return "hello fegin";
    }

    /**
     * 根据用户名查询用户
     *
     * @param request
     * @return
     */
    @Override
    public OymResponse<UserLoginInfo> getUserByName(GetUserRequest request) {
        UserLoginInfo user = userService.getUserByName(request.getUsername());
        if (Argument.isNull(user)) {
            return OymResponse.error("用户不存在");
        }
        return OymResponse.success(user);
    }

    /**
     * 保存用户登录信息
     *
     * @param request
     * @return
     */
    @Override
    public OymResponse<Boolean> saveLoginInfo(SaveUserLoginInfoRequest request) {
        userService.saveLoginInfo(request.getUserId(), request.getIp());
        return OymResponse.success(true);
    }

    /**
     * 权限校验
     * 根据用户id判断是否有改url的权限
     *
     * @param request
     * @return
     */
    @Override
    public OymResponse<Boolean> privilegeCheck(PrivilegeCheckRequest request) {
        return OymResponse.success(userService.privilegeCheck(request.getUid(), request.getUrl()));
    }
}

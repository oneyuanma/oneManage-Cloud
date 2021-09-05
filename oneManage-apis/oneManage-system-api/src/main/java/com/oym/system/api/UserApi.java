package com.oym.system.api;

import com.oym.commons.base.response.OymResponse;
import com.oym.commons.user.UserLoginInfo;
import com.oym.system.api.domain.request.GetUserRequest;
import com.oym.system.api.domain.request.PrivilegeCheckRequest;
import com.oym.system.api.domain.request.SaveUserLoginInfoRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户相关API
 * @author oneyuanma
 * @date 2021/07/14
 */
@Api(value = "oneManage-system-api", tags = {"oneManage-system-api"})
@FeignClient("oneManage-system")
public interface UserApi {

    @ApiOperation(value = "feign 调用测试")
    @PostMapping(value = "/test/demo1")
    String demo(@RequestParam("name") String name);

    /**
     * 根据用户名查询用户
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "根据用户名查询用户")
    @PostMapping(value = "/user/get")
    OymResponse<UserLoginInfo> getUserByName(@RequestBody GetUserRequest request);

    /**
     * 登录成功保存用户登录信息
     * @param request
     * @return
     */
    @ApiOperation(value = "登录成功保存用户登录信息")
    @PostMapping(value = "/user/loginInfo/save")
    OymResponse<Boolean> saveLoginInfo(@RequestBody SaveUserLoginInfoRequest request);

    /**
     * 权限校验
     * 根据用户id判断是否有改url的权限
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "根据用户id判断是否有改url的权限")
    @PostMapping(value = "/privilege/check")
    OymResponse<Boolean> privilegeCheck(@RequestBody PrivilegeCheckRequest request);


}

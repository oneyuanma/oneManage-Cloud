package com.oym.system.ctrl;


import com.github.pagehelper.PageInfo;
import com.oym.commons.anonation.OperateLog;
import com.oym.commons.base.response.OymResponse;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.cons.OperateTypeEnum;
import com.oym.commons.user.WebUser;
import com.oym.commons.utils.Argument;
import com.oym.system.ctrl.request.GetUserPageListRequest;
import com.oym.system.ctrl.request.UserDeleteRequest;
import com.oym.system.ctrl.request.UserSaveRequest;
import com.oym.system.ctrl.request.UserSetRequest;
import com.oym.system.ctrl.vo.UserInfoVO;
import com.oym.system.ctrl.vo.UserVO;
import com.oym.system.service.UserService;
import com.oym.system.transform.UserTransform;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户管理
 *
 * @author oneyuanma
 * @date 2021/06/23
 */
@RestController
@RequestMapping("/user")
@Api(value = "系统用户API", tags = {"系统用户API"})
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     *
     * @return
     */
    @ApiOperation(value = "获取用户信息")
    @PostMapping("/getUserInfo")
    public OymResponse<UserInfoVO> getUserInfo() {
        UserInfoVO userInfoVO = userService.getUserInfo(WebUser.getCurrentUser().getUserId());
        if (Argument.isNull(userInfoVO)) {
            return OymResponse.error("获取系统用户信息失败");
        }
        return OymResponse.success(userInfoVO);
    }

    /**
     * 用户列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "用户列表")
    @PostMapping("/pageList")
    @OperateLog(module = "系统管理", operatePath = "系统管理-用户管理-用户列表", type = OperateTypeEnum.QUERY)
    public OymResponse<PageInfo<UserVO>> pageList(@RequestBody GetUserPageListRequest request) {
        return OymResponse.success(userService.pageList(UserTransform.INS.transfer(request)));
    }

    /**
     * 用户保存
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "用户保存")
    @PostMapping("/save")
    @OperateLog(module = "系统管理", operatePath = "系统管理-用户管理-用户保存", type = OperateTypeEnum.SAVE)
    public OymResponse<Boolean> save(@RequestBody UserSaveRequest request) {
        OymResult<Boolean> result = userService.save(UserTransform.INS.transfer(request));
        if (result.isFail()) {
            return OymResponse.error(result.getMsg());
        }
        return OymResponse.success(result.getData());
    }

    /**
     * 用户删除
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "用户删除")
    @PostMapping("/delete")
    @OperateLog(module = "系统管理", operatePath = "系统管理-用户管理-用户删除", type = OperateTypeEnum.DELETE)
    public OymResponse<Boolean> delete(@RequestBody UserDeleteRequest request) {
        return OymResponse.success(userService.delete(request.getId()));
    }

    /**
     * 用户状态设置
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "用户状态设置")
    @PostMapping("/set")
    @OperateLog(module = "系统管理", operatePath = "系统管理-用户管理-用户状态设置", type = OperateTypeEnum.SET)
    public OymResponse<Boolean> set(@RequestBody UserSetRequest request) {
        OymResult<Boolean> result = userService.set(request.getId());
        if (result.isFail()) {
            return OymResponse.error(result.getMsg());
        }
        return OymResponse.success(true);
    }
}

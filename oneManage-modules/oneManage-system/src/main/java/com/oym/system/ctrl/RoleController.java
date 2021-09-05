package com.oym.system.ctrl;


import com.github.pagehelper.PageInfo;
import com.oym.commons.anonation.OperateLog;
import com.oym.commons.base.response.OymResponse;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.cons.OperateTypeEnum;
import com.oym.system.ctrl.request.*;
import com.oym.system.ctrl.vo.RoleResourceVO;
import com.oym.system.ctrl.vo.RoleSelectVO;
import com.oym.system.ctrl.vo.RoleVO;
import com.oym.system.service.RoleResourceService;
import com.oym.system.service.RoleService;
import com.oym.system.transform.RoleTransform;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author oneyuanma
 * @date 2021/06/23
 */
@RestController
@RequestMapping("/role")
@Api(value = "系统角色API", tags = {"系统角色API"})
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleResourceService roleResourceService;

    /**
     * 角色分页列表
     * @param request
     * @return
     */
    @ApiOperation(value = "角色分页列表")
    @PostMapping("/pageList")
    @OperateLog(module = "系统管理", operatePath = "系统管理-角色管理-角色分页列表", type = OperateTypeEnum.QUERY)
    public OymResponse<PageInfo<RoleVO>> pageList(@RequestBody GetRolePageListRequest request) {
        return OymResponse.success(roleService.pageList(RoleTransform.INS.transfer(request)));
    }

    /**
     * 角色列表
     * @param request
     * @return
     */
    @ApiOperation(value = "角色列表")
    @PostMapping("/list")
    @OperateLog(module = "系统管理", operatePath = "系统管理-角色管理-角色列表", type = OperateTypeEnum.QUERY)
    public OymResponse<List<RoleVO>> list(@RequestBody GetRoleListRequest request) {
        return OymResponse.success(roleService.list(RoleTransform.INS.transfer(request)));
    }

    /**
     * 角色下拉选项列表
     * @return
     */
    @ApiOperation(value = "角色下拉选项列表")
    @PostMapping("/selectOptions")
    @OperateLog(module = "系统管理", operatePath = "系统管理-角色管理-角色下拉选项列表", type = OperateTypeEnum.QUERY)
    public OymResponse<List<RoleSelectVO>> selectOptions() {
        return OymResponse.success(roleService.selectOptions());
    }

    /**
     * 角色保存
     * @param request
     * @return
     */
    @ApiOperation(value = "角色保存")
    @PostMapping("/save")
    @OperateLog(module = "系统管理", operatePath = "系统管理-角色管理-角色保存", type = OperateTypeEnum.SAVE)
    public OymResponse<Boolean> save(@RequestBody RoleSaveRequest request) {
        OymResult<Boolean> result = roleService.save(RoleTransform.INS.transfer(request));
        if (result.isFail()) {
            return OymResponse.error(result.getMsg());
        }
        return OymResponse.success(result.getData());
    }

    /**
     * 角色删除
     * @param request
     * @return
     */
    @ApiOperation(value = "角色删除")
    @PostMapping("/delete")
    @OperateLog(module = "系统管理", operatePath = "系统管理-角色管理-角色删除", type = OperateTypeEnum.DELETE)
    public OymResponse<Boolean> delete(@RequestBody RoleDeleteRequest request) {
        return OymResponse.success(roleService.delete(request.getId()));
    }

    /**
     * 角色资源关联关系保存
     * @param request
     * @return
     */
    @ApiOperation(value = "角色资源关联关系保存")
    @PostMapping("/saveRoleResource")
    @OperateLog(module = "系统管理", operatePath = "系统管理-角色管理-角色资源关联关系保存", type = OperateTypeEnum.SAVE)
    public OymResponse<Boolean> saveRoleResource(@RequestBody @Validated RoleResourceSaveRequest request) {
        OymResult<Boolean> result = roleResourceService.saveRoleResource(RoleTransform.INS.transfer(request));
        if (result.isFail()) {
            return OymResponse.error(result.getMsg());
        }
        return OymResponse.success(result.getData());
    }

    /**
     * 根据角色获取与资源的关联关系
     * @param request
     * @return
     */
    @ApiOperation(value = "根据角色获取与资源的关联关系")
    @PostMapping("/getRoleResource")
    @OperateLog(module = "系统管理", operatePath = "系统管理-角色管理-根据角色获取与资源的关联关系", type = OperateTypeEnum.QUERY)
    public OymResponse<RoleResourceVO> getRoleResource(@RequestBody @Validated GetRoleResourceRequest request) {
        return OymResponse.success(roleResourceService.getRoleResource(request.getRoleId()));
    }

}

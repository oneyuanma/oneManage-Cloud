package com.oym.system.ctrl;


import com.oym.commons.anonation.OperateLog;
import com.oym.commons.base.response.OymResponse;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.cons.OperateTypeEnum;
import com.oym.commons.utils.CollectionUtils;
import com.oym.system.ctrl.request.ResourceDeleteRequest;
import com.oym.system.ctrl.request.ResourceSaveRequest;
import com.oym.system.ctrl.vo.ResourceAssignmentTreeVO;
import com.oym.system.ctrl.vo.ResourceSelectTreeVO;
import com.oym.system.ctrl.vo.ResourceTreeVO;
import com.oym.system.service.ResourceService;
import com.oym.system.transform.ResourceTransform;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zxp.esclientrhl.repository.ElasticsearchTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统权限资源
 *
 * @author oneyuanma
 * @date 2021/06/23
 */
@RestController
@RequestMapping("/resource")
@Api(value = "系统权限资源API", tags = {"系统权限资源API"})
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ResourceService resourceService;

    /**
     * 资源权限树形数据列表
     *
     * @return
     */
    @ApiOperation(value = "资源权限树形数据列表")
    @PostMapping("/treeList")
    @OperateLog(module = "系统管理", operatePath = "系统管理-资源管理-资源权限树形数据列表", type = OperateTypeEnum.QUERY)
    public OymResponse<List<ResourceTreeVO>> treeList() {
        return OymResponse.success(resourceService.treeList());
    }

    /**
     * 资源权限下数据拉列表
     *
     * @return
     */
    @ApiOperation(value = "资源权限下数据拉列表")
    @PostMapping("/treeSelect")
    @OperateLog(module = "系统管理", operatePath = "系统管理-资源管理-资源权限下数据拉列表", type = OperateTypeEnum.QUERY)
    public OymResponse<List<ResourceSelectTreeVO>> treeSelect() {
        List<ResourceTreeVO> resourceTreeVOS = resourceService.treeList();
        return OymResponse.success(CollectionUtils.convert(resourceTreeVOS, ResourceTransform.INS::transfer));
    }

    /**
     * 资源权限下数据拉列表
     *
     * @return
     */
    @ApiOperation(value = "资源权限下数据拉列表")
    @PostMapping("/treeAssignment")
    @OperateLog(module = "系统管理", operatePath = "系统管理-资源管理-资源权限下数据拉列表", type = OperateTypeEnum.QUERY)
    public OymResponse<List<ResourceAssignmentTreeVO>> treeAssignment() {
        List<ResourceTreeVO> resourceTreeVOS = resourceService.treeList();
        return OymResponse.success(CollectionUtils.convert(resourceTreeVOS, ResourceTransform.INS::transfer2TreeVO));
    }

    /**
     * 资源权限保存保存
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "资源权限保存保存")
    @PostMapping("/save")
    @OperateLog(module = "系统管理", operatePath = "系统管理-资源管理-资源权限保存保存", type = OperateTypeEnum.SAVE)
    public OymResponse<Boolean> save(@RequestBody ResourceSaveRequest request) {
        OymResult<Boolean> result = resourceService.save(ResourceTransform.INS.transfer(request));
        if (result.isFail()) {
            return OymResponse.error(result.getMsg());
        }
        return OymResponse.success(result.getData());
    }

    /**
     * 资源权限删除
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "资源权限删除")
    @PostMapping("/delete")
    @OperateLog(module = "系统管理", operatePath = "系统管理-资源管理-资源权限删除", type = OperateTypeEnum.DELETE)
    public OymResponse<Boolean> delete(@RequestBody ResourceDeleteRequest request) {
        return OymResponse.success(resourceService.delete(request.getId()));
    }
}

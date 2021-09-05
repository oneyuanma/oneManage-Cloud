package com.oym.system.ctrl;


import com.github.pagehelper.PageInfo;
import com.oym.commons.anonation.OperateLog;
import com.oym.commons.base.response.OymResponse;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.cons.OperateTypeEnum;
import com.oym.system.ctrl.request.GetKvPageListRequest;
import com.oym.system.ctrl.request.GetKvRequest;
import com.oym.system.ctrl.request.KvDeleteRequest;
import com.oym.system.ctrl.request.KvSaveRequest;
import com.oym.system.ctrl.vo.KvVO;
import com.oym.system.domain.bo.GetKvPageListBO;
import com.oym.system.service.KvService;
import com.oym.system.transform.KvTransform;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统键值对（kv）
 *
 * @author oneyuanma
 * @date 2021/06/23
 */
@RestController
@RequestMapping("/kv")
@Api(value = "系统键值对API", tags = {"系统键值对API"})
public class KvController {

    @Autowired
    private KvService kSevrvice;

    /**
     * 键值对列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "键值对列表")
    @PostMapping("/pageList")
    @OperateLog(module = "系统管理", operatePath = "系统管理-键值对管理-键值对列表", type = OperateTypeEnum.QUERY)
    public OymResponse<PageInfo<KvVO>> pageList(@RequestBody GetKvPageListRequest request) {
        GetKvPageListBO bo = new GetKvPageListBO();
        bo.setK(request.getK());
        bo.setV(request.getV());
        return OymResponse.success(kSevrvice.pageList(new GetKvPageListBO(request.getK(), request.getV())));
    }

    /**
     * 键值对保存
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "键值对保存")
    @PostMapping("/save")
    @OperateLog(module = "系统管理", operatePath = "系统管理-键值对管理-键值对保存", type = OperateTypeEnum.SAVE)
    public OymResponse<Boolean> save(@RequestBody KvSaveRequest request) {
        OymResult<Boolean> result = kSevrvice.save(KvTransform.INS.transfer(request));
        if (result.isFail()) {
            return OymResponse.error(result.getMsg());
        }
        return OymResponse.success(result.getData());
    }

    /**
     * 键值对删除
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "键值对删除")
    @PostMapping("/delete")
    @OperateLog(module = "系统管理", operatePath = "系统管理-键值对管理-键值对删除", type = OperateTypeEnum.DELETE)
    public OymResponse<Boolean> delete(@RequestBody KvDeleteRequest request) {
        return OymResponse.success(kSevrvice.delete(request.getId()));
    }

    /**
     * 键值对列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "根据键获取值")
    @PostMapping("/get")
    @OperateLog(module = "系统管理", operatePath = "系统管理-键值对管理-根据键获取键值对", type = OperateTypeEnum.QUERY)
    public OymResponse<String> get(@RequestBody GetKvRequest request) {
        return OymResponse.success(kSevrvice.getValueByKey(request.getK()));
    }
}

package com.oym.generate.ctrl;


import com.github.pagehelper.PageInfo;
import com.oym.commons.anonation.OperateLog;
import com.oym.commons.base.response.OymResponse;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.cons.OperateTypeEnum;
import com.oym.generate.ctrl.request.*;
import com.oym.generate.ctrl.vo.TableSelectVO;
import com.oym.generate.ctrl.vo.TableVO;
import com.oym.generate.service.TableService;
import com.oym.generate.transform.TableTransform;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 表信息描述
 *
 * @author oneyuanma
 * @date 2021/06/23
 */
@RestController
@RequestMapping("/table")
@Api(value = "表信息描述API", tags = {"表信息描述API"})
public class TableController {

    private static final Logger logger = LoggerFactory.getLogger(TableController.class);

    @Autowired
    private TableService tableService;

    /**
     * 表信息列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "表信息列表")
    @PostMapping("/pageList")
    @OperateLog(module = "系统管理", operatePath = "代码生成-表信息管理-表信息列表", type = OperateTypeEnum.QUERY)
    public OymResponse<PageInfo<TableVO>> pageList(@RequestBody GetTablePageListRequest request) {
        return OymResponse.success(tableService.pageList(TableTransform.INS.transfer(request)));
    }

    /**
     * 表信息保存
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "表信息保存")
    @PostMapping("/save")
    @OperateLog(module = "系统管理", operatePath = "代码生成-表信息管理-表信息保存", type = OperateTypeEnum.SAVE)
    public OymResponse<Boolean> save(@RequestBody TableSaveRequest request) {
        OymResult<Boolean> result = tableService.save(TableTransform.INS.transfer(request));
        if (result.isFail()) {
            return OymResponse.error(result.getMsg());
        }
        return OymResponse.success(result.getData());
    }

    /**
     * 表信息删除
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "表信息删除")
    @PostMapping("/delete")
    @OperateLog(module = "系统管理", operatePath = "系统管理-代码生成管理-表信息删除", type = OperateTypeEnum.DELETE)
    public OymResponse<Boolean> delete(@RequestBody TableDeleteRequest request) {
        return OymResponse.success(tableService.delete(request.getId()));
    }

    /**
     * 表信息同步
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "表信息同步")
    @PostMapping("/sync")
    @OperateLog(module = "系统管理", operatePath = "系统管理-代码生成管理-表信息同步", type = OperateTypeEnum.OPERATE)
    public OymResponse<Boolean> sync(@RequestBody TableSyncRequest request) {
        return OymResponse.success(tableService.sync(request.getTableName()));
    }

    /**
     * 表名列表
     *
     * @return
     */
    @ApiOperation(value = "表名列表")
    @PostMapping("/tableList")
    @OperateLog(module = "系统管理", operatePath = "系统管理-代码生成管理-表名列表", type = OperateTypeEnum.QUERY)
    public OymResponse<List<String>> tableList() {
        return OymResponse.success(tableService.tableList());
    }

    /**
     * 代码生成
     *
     * @param tableGenerateRequest
     * @return
     */
    @ApiOperation(value = "代码生成")
    @PostMapping("/generate")
    @OperateLog(module = "系统管理", operatePath = "系统管理-代码生成管理-代码生成", type = OperateTypeEnum.OPERATE)
    public OymResponse<Boolean> generate(@RequestBody TableGenerateRequest tableGenerateRequest) {
        return OymResponse.success(tableService.generate(tableGenerateRequest.getTableId()).getData());
    }

    /**
     * 数据库表下拉选项列表
     *
     * @return
     */
    @ApiOperation(value = "数据库表下拉选项列表")
    @PostMapping("/selectOptions")
    @OperateLog(module = "系统管理", operatePath = "系统管理-代码生成管理-数据库表下拉选项列表", type = OperateTypeEnum.QUERY)
    public OymResponse<List<TableSelectVO>> selectOptions() {
        return OymResponse.success(tableService.selectOptions());
    }
}

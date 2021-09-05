package com.oym.generate.ctrl;


import com.oym.commons.anonation.OperateLog;
import com.oym.commons.base.response.OymResponse;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.cons.OperateTypeEnum;
import com.oym.generate.ctrl.request.FieldDetailRequest;
import com.oym.generate.ctrl.request.FieldSaveRequest;
import com.oym.generate.ctrl.vo.FieldVO;
import com.oym.generate.service.FieldService;
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
 * 字段信息描述
 *
 * @author oneyuanma
 * @date 2021/06/23
 */
@RestController
@RequestMapping("/field")
@Api(value = "字段信息描述API", tags = {"字段信息描述API"})
public class FieldController {

    private static final Logger logger = LoggerFactory.getLogger(FieldController.class);

    @Autowired
    private FieldService fieldService;

    /**
     * 字段描述信息详情
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "字段描述信息详情")
    @PostMapping("/detail")
    @OperateLog(module = "系统管理", operatePath = "代码生成-字段信息管理-字段信息详情", type = OperateTypeEnum.QUERY)
    public OymResponse<List<FieldVO>> detail(@RequestBody FieldDetailRequest request) {
        return OymResponse.success(fieldService.detail(request.getTableId()));
    }

    /**
     * 字段信息保存
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "字段信息保存")
    @PostMapping("/save")
    @OperateLog(module = "系统管理", operatePath = "代码生成-字段信息管理-字段信息保存", type = OperateTypeEnum.SAVE)
    public OymResponse<Boolean> save(@RequestBody FieldSaveRequest request) {
        OymResult<Boolean> result = fieldService.save(request.getFieldDTOList());
        if (result.isFail()) {
            return OymResponse.error(result.getMsg());
        }
        return OymResponse.success(result.getData());
    }

}

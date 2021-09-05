package com.oym.generate.ctrl;


import com.github.pagehelper.PageInfo;
import com.oym.commons.base.response.OymResponse;
import com.oym.commons.base.response.OymResult;
import com.oym.generate.ctrl.request.GenerateDemoDeleteRequest;
import com.oym.generate.ctrl.request.GenerateDemoSaveRequest;
import com.oym.generate.ctrl.request.GetGenerateDemoPageListRequest;
import com.oym.generate.ctrl.vo.GenerateDemoVO;
import com.oym.generate.domain.bo.GetGenerateDemoPageListBO;
import com.oym.generate.service.GenerateDemoService;
import com.oym.generate.transform.GenerateDemoTransform;
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
 * 代码生成工具演示demo 控制类
 *
 * @author oneyuanma
 * @Date 2021/08/12
 */
@RestController
@RequestMapping("/generateDemo")
@Api(value = "代码生成工具演示demoAPI", tags = {"代码生成工具演示demoAPI"})
public class GenerateDemoController {

    private static final Logger logger = LoggerFactory.getLogger(GenerateDemoController.class);

    @Autowired
    private GenerateDemoService generateDemoSevrvice;

    /**
     * 代码生成工具演示demo列表
     * @param request
     * @return
     */
    @ApiOperation(value = "代码生成工具演示demo列表")
    @PostMapping("/pageList")
    public OymResponse<PageInfo<GenerateDemoVO>> pageList(@RequestBody GetGenerateDemoPageListRequest request) {
        GetGenerateDemoPageListBO bo = new GetGenerateDemoPageListBO();
        bo.setDescription(request.getDescription());
        bo.setTime(request.getTime());
        bo.setTitle(request.getTitle());
        return OymResponse.success(generateDemoSevrvice.pageList(bo));
    }

    /**
     * 代码生成工具演示demo保存
     * @param request
     * @return
     */
    @ApiOperation(value = "代码生成工具演示demo保存")
    @PostMapping("/save")
    public OymResponse<Boolean> save(@RequestBody GenerateDemoSaveRequest request) {
        OymResult<Boolean> result = generateDemoSevrvice.save(GenerateDemoTransform.INS.transfer(request));
        if (result.isFail()) {
            return OymResponse.error(result.getMsg());
        }
        return OymResponse.success(result.getData());
    }

    /**
     * 代码生成工具演示demo删除
     * @param request
     * @return
     */
    @ApiOperation(value = "代码生成工具演示demo删除")
    @PostMapping("/delete")
    public OymResponse<Boolean> delete(@RequestBody GenerateDemoDeleteRequest request) {
        return OymResponse.success(generateDemoSevrvice.delete(request.getId()));
    }

}

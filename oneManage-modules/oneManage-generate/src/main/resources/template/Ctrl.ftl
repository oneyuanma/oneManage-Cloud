package com.oym.${mouldName}.ctrl;


import com.github.pagehelper.PageInfo;
import com.oym.commons.anonation.OperateLog;
import com.oym.commons.base.response.OymResponse;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.cons.OperateTypeEnum;
import com.oym.${mouldName}.ctrl.request.Get${className}PageListRequest;
import com.oym.${mouldName}.ctrl.request.${className}DeleteRequest;
import com.oym.${mouldName}.ctrl.request.${className}SaveRequest;
import com.oym.${mouldName}.ctrl.vo.${className}VO;
import com.oym.${mouldName}.domain.bo.Get${className}PageListBO;
import com.oym.${mouldName}.service.${className}Service;
import com.oym.${mouldName}.transform.${className}Transform;
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
 * ${functionComment} 控制类
 *
 * @author ${author}
 * @Date ${date}
 */
@RestController
@RequestMapping("/${classNameParam}")
@Api(value = "${functionComment}API", tags = {"${functionComment}API"})
public class ${className}Controller {

    private static final Logger logger = LoggerFactory.getLogger(${className}Controller.class);

    @Autowired
    private ${className}Service ${classNameParam}Sevrvice;

    /**
     * ${functionComment}列表
     * @param request
     * @return
     */
    @ApiOperation(value = "${functionComment}列表")
    @PostMapping("/pageList")
    public OymResponse<PageInfo<${className}VO>> pageList(@RequestBody Get${className}PageListRequest request) {
        Get${className}PageListBO bo = new Get${className}PageListBO();
    <#list columns as c>
    <#if c.ifQuery == "true">
        bo.set${ c.upColumnName}(request.get${ c.upColumnName}());
    </#if>
    </#list>
        return OymResponse.success(${classNameParam}Sevrvice.pageList(bo));
    }

    /**
     * ${functionComment}保存
     * @param request
     * @return
     */
    @ApiOperation(value = "${functionComment}保存")
    @PostMapping("/save")
    public OymResponse<Boolean> save(@RequestBody ${className}SaveRequest request) {
        OymResult<Boolean> result = ${classNameParam}Sevrvice.save(${className}Transform.INS.transfer(request));
        if (result.isFail()) {
            return OymResponse.error(result.getMsg());
        }
        return OymResponse.success(result.getData());
    }

    /**
     * ${functionComment}删除
     * @param request
     * @return
     */
    @ApiOperation(value = "${functionComment}删除")
    @PostMapping("/delete")
    public OymResponse<Boolean> delete(@RequestBody ${className}DeleteRequest request) {
        return OymResponse.success(${classNameParam}Sevrvice.delete(request.getId()));
    }

}

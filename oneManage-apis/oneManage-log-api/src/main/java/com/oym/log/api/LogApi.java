package com.oym.log.api;

import com.oym.commons.base.response.OymResponse;
import com.oym.log.api.domain.request.LogLoginSaveRequest;
import com.oym.log.api.domain.request.LogOperateSaveRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 日志相关API
 * @author oneyuanma
 * @date 2021/07/14
 */
@Api(value = "oneManage-log-api", tags = {"oneManage-log-api"})
@FeignClient("oneManage-log")
public interface LogApi {

    /**
     * 登录日志保存
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "登录日志保存")
    @PostMapping(value = "/log/login/save")
    OymResponse<Boolean> loginLogSave(@RequestBody LogLoginSaveRequest request);

    /**
     * 操作日志保存
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "操作日志保存")
    @PostMapping(value = "/log/operate/save")
    OymResponse<Boolean> operateLogSave(@RequestBody LogOperateSaveRequest request);
}

package com.oym.log.feign;

import com.oym.commons.base.response.OymResponse;
import com.oym.commons.base.response.OymResult;
import com.oym.log.api.LogApi;
import com.oym.log.api.domain.request.LogLoginSaveRequest;
import com.oym.log.api.domain.request.LogOperateSaveRequest;
import com.oym.log.service.LogLoginService;
import com.oym.log.service.LogOperateService;
import com.oym.log.transform.LogTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志api实现
 *
 * @author oneyuanma
 * @date 2021/06/22
 */
@RestController
public class LogFeignImpl implements LogApi {

    @Autowired
    private LogLoginService logLoginService;

    @Autowired
    private LogOperateService logOperateService;

    /**
     * 登录日志保存
     *
     * @param request
     * @return
     */
    @Override
    public OymResponse<Boolean> loginLogSave(LogLoginSaveRequest request) {
        OymResult<Boolean> result = logLoginService.save(LogTransform.INS.transfer(request));
        if (result.isFail()) {
            return OymResponse.error("登录日志保存失败");
        }
        return OymResponse.success(result.getData());
    }

    /**
     * 操作日志保存
     *
     * @param request
     * @return
     */
    @Override
    public OymResponse<Boolean> operateLogSave(LogOperateSaveRequest request) {
        OymResult<Boolean> result = logOperateService.save(LogTransform.INS.transfer(request));
        if (result.isFail()) {
            return OymResponse.error("操作日志保存失败");
        }
        return OymResponse.success(result.getData());
    }

}

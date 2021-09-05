package com.oym.quartz.ctrl;


import com.github.pagehelper.PageInfo;
import com.oym.commons.anonation.OperateLog;
import com.oym.commons.base.response.OymResponse;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.cons.OperateTypeEnum;
import com.oym.quartz.ctrl.request.GetJobPageListRequest;
import com.oym.quartz.ctrl.request.JobRequest;
import com.oym.quartz.ctrl.request.JobSaveRequest;
import com.oym.quartz.ctrl.vo.JobVO;
import com.oym.quartz.domain.bo.GetJobPageListBO;
import com.oym.quartz.service.JobService;
import com.oym.quartz.transform.JobTransform;
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
 * 定时任务
 *
 * @author oneyuanma
 * @date 2021/06/23
 */
@RestController
@RequestMapping("/job")
@Api(value = "定时任务API", tags = {"定时任务API"})
public class JobController {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobevrvice;

    /**
     * 定时任务列表
     *
     * @param req
     * @return
     */
    @ApiOperation(value = " 定时任务列表")
    @PostMapping("/pageList")
    @OperateLog(module = "系统管理", operatePath = "系统管理-定时任务管理-定时任务列表", type = OperateTypeEnum.QUERY)
    public OymResponse<PageInfo<JobVO>> pageList(@RequestBody GetJobPageListRequest req) {
        return OymResponse.success(jobevrvice.pageList(new GetJobPageListBO(req.getJobName(), req.getJobDescription(), req.getJobGroupName())));
    }

    /**
     * 定时任务保存
     *
     * @param request
     * @return
     */
    @ApiOperation(value = " 定时任务保存")
    @PostMapping("/save")
    @OperateLog(module = "系统管理", operatePath = "系统管理-定时任务管理-定时任务保存", type = OperateTypeEnum.SAVE)
    public OymResponse<Boolean> save(@RequestBody JobSaveRequest request) {
        OymResult<Boolean> result = jobevrvice.save(JobTransform.INS.transfer(request));
        if (result.isFail()) {
            return OymResponse.error(result.getMsg());
        }
        return OymResponse.success(result.getData());
    }

    /**
     * 定时任务删除
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "定时任务删除")
    @PostMapping("/delete")
    @OperateLog(module = "系统管理", operatePath = "系统管理-定时任务管理-定时任务删除", type = OperateTypeEnum.DELETE)
    public OymResponse<Boolean> delete(@RequestBody JobRequest request) {
        return OymResponse.success(jobevrvice.delete(request.getId()));
    }

    /**
     * 定时任务执行一次
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "定时任务执行一次")
    @PostMapping("/run")
    @OperateLog(module = "系统管理", operatePath = "系统管理-定时任务管理-定时任务执行一次", type = OperateTypeEnum.OPERATE)
    public OymResponse<Boolean> run(@RequestBody JobRequest request) {
        return OymResponse.success(jobevrvice.runJob(request.getId()));
    }

    /**
     * @param request
     * @return
     */
    @ApiOperation(value = "定时任务状态设置")
    @PostMapping("/statusSet")
    @OperateLog(module = "系统管理", operatePath = "系统管理-定时任务管理-定时任务状态设置", type = OperateTypeEnum.SET)
    public OymResponse<Boolean> statusSet(@RequestBody JobRequest request) {
        OymResult<Boolean> result = jobevrvice.statusSet(request.getId());
        if (result.isFail()) {
            return OymResponse.error(result.getMsg());
        }
        return OymResponse.success(true);
    }
}

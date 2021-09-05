package com.oym.quartz.service;


import com.github.pagehelper.PageInfo;
import com.oym.commons.base.response.OymResult;
import com.oym.quartz.ctrl.vo.JobVO;
import com.oym.quartz.domain.bo.GetJobPageListBO;
import com.oym.quartz.domain.bo.JobSaveBO;

/**
 * 定时任务service
 *
 * @author oneyuanma
 * @date 20201/07/17
 */
public interface JobService {

    /**
     * 分页获取定时任务列表
     *
     * @param bo
     * @return
     */
    PageInfo<JobVO> pageList(GetJobPageListBO bo);

    /**
     * 查询定时任务
     * @return
     */
//    public PublicBean getJobAndTriggerVo();

    /**
     * 定时任务保存
     *
     * @param bo
     * @return
     */
    OymResult<Boolean> save(JobSaveBO bo);

    /**
     * 定时任务删除
     *
     * @param id
     * @return
     */
    Boolean delete(Long id);

    /**
     * 定时任务设置
     *
     * @return
     */
    OymResult<Boolean> statusSet(Long id);

    /**
     * 添加任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @return
     * @throws Exception
     */
    public Boolean addJob(String jobClassName, String jobGroupName, String cronExpression);

    /**
     * 更新定时任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @return
     * @throws Exception
     */
    public Boolean updateJob(String jobClassName, String jobGroupName, String cronExpression);

    /**
     * 删除定时任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @return
     * @throws Exception
     */
    public Boolean deleteJob(String jobClassName, String jobGroupName);

    /**
     * 暂停定时任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @return
     * @throws Exception
     */
    public Boolean pauseJob(String jobClassName, String jobGroupName);

    /**
     * 恢复任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @return
     * @throws Exception
     */
    public Boolean resumeJob(String jobClassName, String jobGroupName);

    /**
     * 立即执行任务
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Boolean runJob(Long id);
}

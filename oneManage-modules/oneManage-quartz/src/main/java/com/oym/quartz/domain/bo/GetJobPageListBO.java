package com.oym.quartz.domain.bo;

import com.oym.commons.base.request.PageAbstractQuery;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页获取定时任务信息 BO
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@NoArgsConstructor
public class GetJobPageListBO extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务描述
     */
    private String jobDescription;

    /**
     * 任务组名
     */
    private String jobGroupName;

    public GetJobPageListBO(String jobName, String jobDescription, String jobGroupName) {
        this.jobName = jobName;
        this.jobDescription = jobDescription;
        this.jobGroupName = jobGroupName;
    }
}

package com.oym.log.domain.dto;

import lombok.Data;
import org.zxp.esclientrhl.annotation.ESID;
import org.zxp.esclientrhl.annotation.ESMetaData;

import java.util.Date;

/**
 * 操作日志ES索引映射
 *
 * @author zsh
 * @date 2020/7/6
 */
@Data
@ESMetaData(indexName = "index_operate_log", number_of_shards = 5, number_of_replicas = 0)
public class OperateLogDTO {

    @ESID
    private String id;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作位置
     */
    private String operatePath;

    /**
     * @see com.oym.commons.cons.OperateTypeEnum
     * 操作类型
     */
    private String type;

    /**
     * 操作人id
     */
    private Long uid;

    /**
     * 操作人名称
     */
    private String userName;

    /**
     * 请求ip
     */
    private String ip;
    /**
     * 请求参数
     */
    private String parameter;

    /**
     * 返回结果
     */
    private String result;

    /**
     * 执行时间 ms
     */
    private Long executeTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作日期
     */
    private Date time;

}

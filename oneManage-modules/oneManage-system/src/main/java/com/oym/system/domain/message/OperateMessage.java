package com.oym.system.domain.message;

import lombok.Data;

import java.util.Date;

/**
 * 操作日志消息
 *
 * @author zsh
 * @date 2020/7/6
 */
@Data
public class OperateMessage {

    private String id;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作日志
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

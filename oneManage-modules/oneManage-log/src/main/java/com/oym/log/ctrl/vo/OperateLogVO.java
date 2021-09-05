package com.oym.log.ctrl.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 操作日志ES索引映射
 *
 * @author zsh
 * @date 2020/7/6
 */
@Data
public class OperateLogVO {

    private String id;

    /**
     * 操作模块
     */
    @ApiModelProperty(value = "操作模块")
    private String module;

    /**
     * 操作日志
     */
    @ApiModelProperty(value = "操作日志")
    private String operatePath;

    /**
     * @see com.oym.commons.cons.OperateTypeEnum
     * 操作类型
     */
    @ApiModelProperty(value = "操作类型")
    private String type;

    /**
     * 操作人id
     */
    @ApiModelProperty(value = "操作人id")
    private Long uid;

    /**
     * 操作人名称
     */
    @ApiModelProperty(value = "操作人名称")
    private String userName;

    /**
     * 请求ip
     */
    @ApiModelProperty(value = "请求ip")
    private String ip;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String parameter;

    /**
     * 返回结果
     */
    @ApiModelProperty(value = "返回结果")
    private String result;

    /**
     * 执行时间 ms
     */
    @ApiModelProperty(value = "执行时间 ms")
    private Long executeTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 操作日期
     */
    @ApiModelProperty(value = "操作日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

}

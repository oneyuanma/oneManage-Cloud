package com.oym.log.api.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oym.commons.base.request.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 查询用户信息request
 *
 * @author oneyuanma
 * @date 2021/06/22
 */
@Data
@ApiModel(description = "查询用户信息request")
public class LogOperateSaveRequest extends AbstractQuery {

    @ApiModelProperty(value = "操作模块")
    private String module;

    @ApiModelProperty(value = "操作位置")
    private String operatePath;

    @ApiModelProperty(value = "操作类型")
    private String type;

    @ApiModelProperty(value = "操作人id")
    private Long uid;

    @ApiModelProperty(value = "操作人名称")
    private String userName;

    @ApiModelProperty(value = "请求ip")
    private String ip;

    @ApiModelProperty(value = "请求参数")
    private String parameter;

    @ApiModelProperty(value = "返回结果")
    private String result;

    @ApiModelProperty(value = "执行时间 ms")
    private Long executeTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "创建日期")
    private Date time;

    public LogOperateSaveRequest() {
    }

}

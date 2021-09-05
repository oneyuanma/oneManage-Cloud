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
public class LogLoginSaveRequest extends AbstractQuery {

    @ApiModelProperty(value = "日志标题")
    private String title;

    @ApiModelProperty(value = "登录账号id")
    private Long uid;

    @ApiModelProperty(value = "登录账号名")
    private String userName;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "操作系统")
    private String operateSystem;

    @ApiModelProperty(value = "浏览器")
    private String browser;

    @ApiModelProperty(value = "操作类型")
    private String type;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "创建日期")
    private Date time;

    public LogLoginSaveRequest() {
    }

}

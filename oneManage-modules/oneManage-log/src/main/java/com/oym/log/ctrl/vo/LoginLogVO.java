package com.oym.log.ctrl.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 登录日志ES索引映射
 *
 * @author zsh
 * @date 2020/7/6
 */
@Data
public class LoginLogVO {

    private String id;

    /**
     * 日志标题
     */
    @ApiModelProperty(value = "日志标题")
    private String title;

    /**
     * 登录账号id
     */
    @ApiModelProperty(value = "登录账号id")
    private Long uid;

    /**
     * 登录账号名
     */
    @ApiModelProperty(value = "登录账号名")
    private String userName;

    /**
     * ip
     */
    @ApiModelProperty(value = "ip")
    private String ip;

    /**
     * 操作系统
     */
    @ApiModelProperty(value = "操作系统")
    private String operateSystem;

    /**
     * 浏览器
     */
    @ApiModelProperty(value = "浏览器")
    private String browser;

    /**
     * 操作类型
     * @see com.oym.log.cons.LoginTypeEnum
     */
    @ApiModelProperty(value = "操作类型")
    private String type;

    /**
     * 状态
     * @see com.oym.log.cons.LoginStatusEnum
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 创建日期
     */
    @ApiModelProperty(value = "操作日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

}

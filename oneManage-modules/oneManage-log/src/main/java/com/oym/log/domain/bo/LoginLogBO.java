package com.oym.log.domain.bo;

import lombok.Data;

import java.util.Date;

/**
 * 登录日志 bo
 *
 * @author zsh
 * @date 2020/7/6
 */
@Data
public class LoginLogBO {

    /**
     * 日志标题
     */
    private String title;

    /**
     * 登录账号id
     */
    private Long uid;

    /**
     * 登录账号名
     */
    private String userName;

    /**
     * ip
     */
    private String ip;

    /**
     * 操作系统
     */
    private String operateSystem;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作类型
     * @see com.oym.log.cons.LoginTypeEnum
     */
    private String type;

    /**
     * 状态
     * @see com.oym.log.cons.LoginStatusEnum
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作日期
     */
    private Date time;

}

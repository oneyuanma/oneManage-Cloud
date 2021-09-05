package com.oym.log.domain.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 登录日志消息
 *
 * @author oneyuanma
 * @date 2021/07/18
 */
@Data
public class LoginMessage {

    private String id;

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
     *
     * @see com.oym.log.cons.LoginTypeEnum
     */
    private String type;

    /**
     * 状态
     *
     * @see com.oym.log.cons.LoginStatusEnum
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date time;

}

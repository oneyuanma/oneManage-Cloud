package com.oym.log.domain.dto;

import lombok.Data;
import org.zxp.esclientrhl.annotation.ESID;
import org.zxp.esclientrhl.annotation.ESMetaData;

import java.util.Date;

/**
 * 登录日志ES索引映射
 *
 * @author zsh
 * @date 2020/7/6
 */
@Data
@ESMetaData(indexName = "index_login_log", number_of_shards = 5, number_of_replicas = 0)
public class LoginLogDTO {

    @ESID
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

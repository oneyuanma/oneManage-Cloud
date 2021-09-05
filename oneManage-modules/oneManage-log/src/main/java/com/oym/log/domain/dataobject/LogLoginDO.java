package com.oym.log.domain.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.oym.commons.base.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志实体类
 *
 * @author oneyuanma
 * @date 2021/06/29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("log_login")
public class LogLoginDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = 6936283373806075036L;

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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    public LogLoginDO() {
    }

    public LogLoginDO(String title, String userName) {
        this.title = title;
        this.userName = userName;
    }
}

package com.oym.log.domain.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oym.commons.base.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志实体类
 *
 * @author oneyuanma
 * @date 2021/06/29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("log_operate")
public class LogOperateDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = 6936283373806075036L;

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
    private LocalDateTime time;

    public LogOperateDO() {
    }

    public LogOperateDO(String module, String operatePath) {
        this.module = module;
        this.operatePath = operatePath;
    }
}

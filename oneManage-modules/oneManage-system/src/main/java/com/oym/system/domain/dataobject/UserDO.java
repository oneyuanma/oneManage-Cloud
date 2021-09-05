package com.oym.system.domain.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oym.commons.base.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户实体类
 *
 * @author oneyuanma
 * @date 2021/06/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class UserDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = 6936283373806075036L;

    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 性别
     */
    private String sex;
    /**
     * 用户账号状态
     */
    private String status;

    /**
     * 本次登录时间
     */
    private LocalDateTime nowLoginTime;

    /**
     * 上次登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 本次登录ip
     */
    private String nowLoginIp;

    /**
     * 上次登录ip
     */
    private String lastLoginIp;

}

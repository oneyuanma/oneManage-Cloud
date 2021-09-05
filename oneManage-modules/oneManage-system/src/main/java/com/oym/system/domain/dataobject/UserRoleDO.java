package com.oym.system.domain.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oym.commons.base.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统用户角色关联实体类
 *
 * @author oneyuanma
 * @date 2021/06/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_role")
public class UserRoleDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = -5000052225507307313L;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 角色id
     */
    private Long roleId;

    public UserRoleDO() {
    }

    public UserRoleDO(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}

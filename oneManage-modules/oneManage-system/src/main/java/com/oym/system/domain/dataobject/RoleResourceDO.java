package com.oym.system.domain.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oym.commons.base.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统角色资源关联实体类
 *
 * @author oneyuanma
 * @date 2021/06/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_resource")
public class RoleResourceDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = -5000052225507307313L;

    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 资源id
     */
    private Long resourceId;

    public RoleResourceDO() {
    }

    public RoleResourceDO(Long roleId, Long resourceId) {
        this.roleId = roleId;
        this.resourceId = resourceId;
    }
}

package com.oym.system.domain.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oym.commons.base.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统角色实体类
 *
 * @author oneyuanma
 * @date 2021/06/29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class RoleDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = 6936283373806075036L;

    /**
     * 角色编码
     */
    private String code;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色描述
     */
    private String description;
}

package com.oym.system.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 系统用户角色关联 DTO
 *
 * @author oneyuanma
 * @date 2021/06/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserRoleDTO {

    private static final long serialVersionUID = -5000052225507307313L;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 角色id
     */
    private Long roleId;

}

package com.oym.system.ctrl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 系统角色资源关联 VO
 *
 * @author oneyuanma
 * @date 2021/06/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleResourceVO implements Serializable {

    private static final long serialVersionUID = 3659962846305055968L;

    @ApiModelProperty(value = "资源id")
    private List<Long> resourceIds;

    public RoleResourceVO() {
    }

    public RoleResourceVO(List<Long> resourceIds) {
        this.resourceIds = resourceIds;
    }
}

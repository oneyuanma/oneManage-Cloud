package com.oym.system.ctrl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统角色下拉列表展示实体类
 *
 * @author oneyuanma
 * @date 2021/06/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleSelectVO implements Serializable {

    private static final long serialVersionUID = 961510260370494336L;

    @ApiModelProperty(value = "角色id")
    private Long value;

    @ApiModelProperty(value = "角色名称")
    private String label;


}

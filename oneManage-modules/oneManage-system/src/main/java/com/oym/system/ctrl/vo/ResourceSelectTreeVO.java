package com.oym.system.ctrl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 系统权限资源树形 下拉 vo
 *
 * @author oneyuanma
 * @date 2021/06/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceSelectTreeVO implements Serializable {

    private static final long serialVersionUID = -5051503697914734693L;

    @ApiModelProperty(value = "id")
    private Long resId;

    @ApiModelProperty(value = "上级资源ID")
    private Long parentId;

    @ApiModelProperty(value = "标题")
    private String name;

    @ApiModelProperty(value = "下级资源")
    private List<ResourceSelectTreeVO> children;
}

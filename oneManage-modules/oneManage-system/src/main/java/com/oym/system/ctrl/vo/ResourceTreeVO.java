package com.oym.system.ctrl.vo;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 系统权限资源树形前段展示实体
 *
 * @author oneyuanma
 * @date 2021/06/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceTreeVO implements Serializable {

    private static final long serialVersionUID = 5417642010576183005L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "上级资源ID")
    private Long parentId;

    @ApiModelProperty(value = "上级资源名称")
    private String parentTitle;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "后台请求路径")
    private String url;

    @ApiModelProperty(value = "前端组件路径")
    private String component;

    @ApiModelProperty(value = "资源编码,权限标识")
    private String code;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "资源层级")
    private Integer level;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "类型 menu : 菜单、button ：按钮")
    private String type;

    @ApiModelProperty(value = "下级资源")
    private List<ResourceTreeVO> children = Lists.newArrayList();
}

package com.oym.system.ctrl.request;

import com.oym.commons.base.request.PageAbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源权限保存 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "资源权限保存 Request")
public class ResourceSaveRequest extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "上级资源ID")
    private Long parentId;

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

}

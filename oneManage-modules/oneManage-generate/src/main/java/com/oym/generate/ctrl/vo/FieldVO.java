package com.oym.generate.ctrl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 表信息描述 vo
 *
 * @author oneyuanma
 * @date 2021/06/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FieldVO implements Serializable {

    private static final long serialVersionUID = -5051503697914734693L;

    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 字段名
     */
    @ApiModelProperty(value = "字段名")
    private String fieldName;

    /**
     * 字段描述
     */
    @ApiModelProperty(value = "字段描述")
    private String fieldDescription;

    /**
     * 数据类型
     */
    @ApiModelProperty(value = "数据类型")
    private String dataType;

    /**
     * 字段类型
     */
    @ApiModelProperty(value = "字段类型")
    private String fieldType;

    /**
     * 字段属性
     */
    @ApiModelProperty(value = "字段属性")
    private String fieldProperty;

    /**
     * 是否需要列表展示 0：不需要  1：需要
     */
    @ApiModelProperty(value = "是否需要列表展示 0：不需要  1：需要")
    private Boolean ifList;

    /**
     * 是否需要编辑 0：不需要  1：需要
     */
    @ApiModelProperty(value = "是否需要编辑 0：不需要  1：需要")
    private Boolean ifEdit;

    /**
     * 是否需要查询 0：不需要  1：需要
     */
    @ApiModelProperty(value = "是否需要查询 0：不需要  1：需要")
    private Boolean ifQuery;

    /**
     * 是否必填0：不必填  1：必填
     */
    @ApiModelProperty(value = "是否必填0：不必填  1：必填")
    private Boolean ifNull;

    /**
     * 查询方式
     */
    @ApiModelProperty(value = "查询方式")
    private String queryType;

    /**
     * 前端展示类型
     */
    @ApiModelProperty(value = "前端展示类型")
    private String viewType;

    /**
     * 字段顺序
     */
    @ApiModelProperty(value = "字段顺序")
    private Integer position;
}

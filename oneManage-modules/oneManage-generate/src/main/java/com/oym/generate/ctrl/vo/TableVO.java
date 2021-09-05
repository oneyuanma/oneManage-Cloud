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
public class TableVO implements Serializable {

    private static final long serialVersionUID = -5051503697914734693L;

    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 表名称
     */
    @ApiModelProperty(value = "表名称")
    private String tableName;

    /**
     * 表描述
     */
    @ApiModelProperty(value = "表描述")
    private String tableDescription;

    /**
     * 作者
     */
    @ApiModelProperty(value = "作者")
    private String author;

    /**
     * 类名
     */
    @ApiModelProperty(value = "类名")
    private String className;

    /**
     * 模块名
     */
    @ApiModelProperty(value = "模块名")
    private String moduleName;

    /**
     * 包路径
     */
    @ApiModelProperty(value = "包路径")
    private String packagePath;

    /**
     * 生成功能名，用作类描述，例如用户管理
     */
    @ApiModelProperty(value = "生成功能名")
    private String functionName;

    /**
     * 文件生成路径
     */
    @ApiModelProperty(value = "文件生成路径")
    private String path;
}

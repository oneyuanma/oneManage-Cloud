package com.oym.generate.ctrl.request;

import com.oym.commons.base.request.PageAbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 表描述信息保存 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "表描述信息保存 Request")
public class TableSaveRequest extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

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
    @ApiModelProperty(value = "生成功能名，用作类描述，例如用户管理")
    private String functionName;

    /**
     * 文件生成路径
     */
    @ApiModelProperty(value = "文件生成路径")
    private String path;
}

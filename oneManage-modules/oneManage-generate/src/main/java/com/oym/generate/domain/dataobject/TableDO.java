package com.oym.generate.domain.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oym.commons.base.dataobject.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 表信息描述实体类
 *
 * @author oneyuanma
 * @date 2021/06/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("generate_table")
@AllArgsConstructor
@NoArgsConstructor
public class TableDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = -908257093621902519L;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableDescription;

    /**
     * 作者
     */
    private String author;

    /**
     * 类名
     */
    private String className;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 包路径
     */
    private String packagePath;

    /**
     * 生成功能名，用作类描述，例如用户管理
     */
    private String functionName;

    /**
     * 文件生成路径
     */
    private String path;

    public TableDO(String tableName) {
        this.tableName = tableName;
    }

    public TableDO(String tableName, String tableDescription, String author) {
        this.tableName = tableName;
        this.tableDescription = tableDescription;
        this.author = author;
    }
}

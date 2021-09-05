package com.oym.generate.domain.bo;

import com.oym.commons.base.request.AbstractQuery;
import lombok.Data;

/**
 * 表描述信息 BO
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
public class TableSaveBO extends AbstractQuery {

    private static final long serialVersionUID = 1883605514721169292L;

    private Long id;

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
}

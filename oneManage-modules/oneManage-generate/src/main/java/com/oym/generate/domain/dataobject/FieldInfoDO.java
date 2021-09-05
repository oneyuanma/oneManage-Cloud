package com.oym.generate.domain.dataobject;

import lombok.Data;

/**
 * 列信息
 *
 * @author oneyuanma
 * @data 2021/07/31
 */
@Data
public class FieldInfoDO {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 类注释
     */
    private String columnComment;

    /**
     * 是否为空
     */
    private String isNullable;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * 字符最大长度
     */
    private Integer characterMaximumLength;

    /**
     * 数值精度(最大位数)
     */
    private Integer numericPrecision;

    /**
     * '小数精度'
     */
    private Integer numericScale;

    /**
     * 列的排列顺序
     */
    private Integer ordinalPosition;

    /**
     * '默认值'
     */
    private String columnDefault;

    /**
     * KEY
     */
    private String columnKey;

}

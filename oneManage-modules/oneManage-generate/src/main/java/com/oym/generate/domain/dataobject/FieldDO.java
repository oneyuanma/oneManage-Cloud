package com.oym.generate.domain.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oym.commons.base.dataobject.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 字段信息描述实体类
 *
 * @author oneyuanma
 * @date 2021/06/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("generate_field")
@AllArgsConstructor
@NoArgsConstructor
public class FieldDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = -908257093621902519L;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段描述
     */
    private String fieldDescription;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 字段属性
     */
    private String fieldProperty;

    /**
     * 是否需要列表展示 0：不需要  1：需要
     */
    private String ifList;

    /**
     * 是否需要编辑 0：不需要  1：需要
     */
    private String ifEdit;

    /**
     * 是否需要查询 0：不需要  1：需要
     */
    private String ifQuery;

    /**
     * 是否必填0：不必填  1：必填
     */
    private String ifNull;

    /**
     * 查询方式
     */
    private String queryType;

    /**
     * 前端展示类型
     */
    private String viewType;

    /**
     * 字符最大长度
     */
    private Integer length;

    /**
     * 字段顺序
     */
    private Integer position;
}

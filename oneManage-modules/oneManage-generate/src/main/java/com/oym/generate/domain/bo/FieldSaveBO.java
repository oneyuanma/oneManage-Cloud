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
public class FieldSaveBO extends AbstractQuery {

    private static final long serialVersionUID = 1883605514721169292L;

    private Long id;

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
    private Integer ifList;

    /**
     * 是否需要编辑 0：不需要  1：需要
     */
    private Integer ifEdit;

    /**
     * 是否需要查询 0：不需要  1：需要
     */
    private Integer ifQuery;

    /**
     * 是否必填0：不必填  1：必填
     */
    private Integer ifNull;

    /**
     * 查询方式
     */
    private String queryType;

    /**
     * 前端展示类型
     */
    private String viewType;

    /**
     * 字段顺序
     */
    private Integer position;
}

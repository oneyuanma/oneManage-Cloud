package com.oym.generate.domain.dataobject;

import lombok.Data;

/**
 * 表信息
 *
 * @author oneyuanma
 * @data 2021/07/31
 */
@Data
public class TableInfoDO {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表备注
     */
    private String tableComment;
}

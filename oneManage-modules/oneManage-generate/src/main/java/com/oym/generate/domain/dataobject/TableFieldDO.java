package com.oym.generate.domain.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oym.commons.base.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 表和字段关联实体类
 *
 * @author oneyuanma
 * @date 2021/06/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("generate_table_field")
@NoArgsConstructor
public class TableFieldDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = -908257093621902519L;

    /**
     * 表id
     */
    private Long tableId;

    /**
     * 字段id
     */
    private Long fieldId;

    public TableFieldDO(Long tableId, Long fieldId) {
        this.tableId = tableId;
        this.fieldId = fieldId;
    }
}

package com.oym.commons.base.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author oneyuanma
 * @Date 2021-06-17
 * @Description do层基础类
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseDO implements Serializable {

    private static final long serialVersionUID = -3138872994885259190L;

    /**
     * id 自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建日期
     */
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createDate;

    /**
     * 更新日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updateDate;

    /**
     *  删除标记（0：正常；1：删除；2：审核）
     */
    protected String delFlag;

    /**
     * 备注
     */
    protected String remarks;

    /**
     * 唯一
     */
//    private String bid;

}

package com.oym.generate.ctrl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 表信息 下拉列表
 *
 * @author oneyuanma
 * @date 2021/06/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TableSelectVO implements Serializable {

    private static final long serialVersionUID = -5051503697914734693L;

    @ApiModelProperty(value = "表名")
    private String value;

    @ApiModelProperty(value = "表名")
    private String label;


}

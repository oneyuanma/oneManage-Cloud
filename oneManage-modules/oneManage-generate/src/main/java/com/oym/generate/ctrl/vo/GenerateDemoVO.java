package com.oym.generate.ctrl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 代码生成工具演示demo vo
 *
 * @author oneyuanma
 * @Date 2021/08/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GenerateDemoVO implements Serializable {

    private static final long serialVersionUID = -5051503697914734693L;

    @ApiModelProperty(value = "id")
    private Long id;

    /**
    * 文本域例子
    */
    @ApiModelProperty(value = "文本域例子")
    private String description;
    /**
    * 时间选择器例子
    */
    @ApiModelProperty(value = "时间选择器例子")
    private String time;
    /**
    * input例子
    */
    @ApiModelProperty(value = "input例子")
    private String title;

}

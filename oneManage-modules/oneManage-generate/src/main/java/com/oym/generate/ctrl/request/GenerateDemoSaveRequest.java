package com.oym.generate.ctrl.request;

import com.oym.commons.base.request.PageAbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 代码生成工具演示demo保存 request
 *
 * @author oneyuanma
 * @Date 2021/08/12
 */
@Data
@ApiModel(description = "代码生成工具演示demo保存 Request")
public class GenerateDemoSaveRequest extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

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

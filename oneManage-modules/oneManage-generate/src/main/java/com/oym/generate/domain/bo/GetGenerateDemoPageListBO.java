package com.oym.generate.domain.bo;

import com.oym.commons.base.request.PageAbstractQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 代码生成工具演示demo分页列表 bo
 *
 * @author oneyuanma
 * @Date 2021/08/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetGenerateDemoPageListBO extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    /**
     * 文本域例子
     */
    private String description;
    /**
     * 时间选择器例子
     */
    private String time;
    /**
     * input例子
     */
    private String title;
}

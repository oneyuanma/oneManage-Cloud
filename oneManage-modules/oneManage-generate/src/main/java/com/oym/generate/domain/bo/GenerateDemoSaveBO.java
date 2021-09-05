package com.oym.generate.domain.bo;

import com.oym.commons.base.request.AbstractQuery;
import lombok.Data;

/**
 * 代码生成工具演示demo保存 BO
 *
 * @author oneyuanma
 * @Date 2021/08/12
 */
@Data
public class GenerateDemoSaveBO extends AbstractQuery {

    private static final long serialVersionUID = 1883605514721169292L;

    private Long id;

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

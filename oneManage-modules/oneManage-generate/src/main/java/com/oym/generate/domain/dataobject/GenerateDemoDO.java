package com.oym.generate.domain.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oym.commons.base.dataobject.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 代码生成工具演示demo
 *
 * @author oneyuanma
 * @Date 2021/08/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("generate_demo")
@AllArgsConstructor
@NoArgsConstructor
public class GenerateDemoDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = 1L;

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

package com.oym.${mouldName}.domain.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oym.commons.base.dataobject.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ${functionComment}
 *
 * @author ${author}
 * @Date ${date}
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("${tableName}")
@AllArgsConstructor
@NoArgsConstructor
public class ${className}DO extends BaseDO implements Serializable {

	private static final long serialVersionUID = 1L;

	<#list columns as c>
	/**
	 * ${c.columnComment}
	 */
	private ${c.columnType} ${ c.columnName};

	</#list>

}

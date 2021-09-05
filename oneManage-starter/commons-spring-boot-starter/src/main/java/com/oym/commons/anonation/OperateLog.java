package com.oym.commons.anonation;

import com.oym.commons.cons.OperateTypeEnum;

import java.lang.annotation.*;

/**
 * 操作日志 自定义注解
 * @author oneyuanma
 * @date 2021/11/08
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface OperateLog {

    /**
     * 模块名
     * @return
     */
    String module() default "";

    /**
     * 操作位置
     * @return
     */
    String operatePath() default "";

    /**
     * 操作类型
     * @see com.oym.commons.cons.OperateTypeEnum
     * @return
     */
    OperateTypeEnum type();
}

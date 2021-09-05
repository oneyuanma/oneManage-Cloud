package com.oym.commons.utils;

/*
 *  Copyright 2015 - 2018 WorkTrans.cn All right reserved.
 *  This software is the confidential and proprietary information of WorkTrans.cn ("Confidential  Information").
 *  You shall not disclose such Confidential Information and shall use
 *  it only in accordance with the terms of the license agreement you entered into with WorkTrans.cn
 *
 */


import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * 对入参数进行判断
 * @author oneyuanma
 * @date 2021/06/23
 */
public class Argument {

    /**
     * 正整数
     */
    public static boolean isPositive(Integer argument) {
        return argument != null && argument > 0;
    }

    /**
     * 正整数
     */
    public static boolean isPositive(Long argument) {
        return argument != null && argument > 0;
    }

    /**
     * 非正整数
     */
    public static boolean isNotPositive(Integer argument) {
        return argument == null || argument <= 0;
    }

    /**
     * 非正整数
     */
    public static boolean isNotPositive(Long argument) {
        return argument == null || argument <= 0;
    }

    public static boolean isPositive(Number argument) {
        if (argument == null) {
            return false;
        }
        return argument.floatValue() > 0f || argument.intValue() > 0;
    }

    public static boolean isNull(Object argument) {
        return argument == null;
    }

    public static boolean isBlank(String argument) {
        return StringUtils.isBlank(argument);
    }

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Collection argument) {
        return isNull(argument) || argument.isEmpty();
    }

    public static boolean isNotNull(Object argument) {
        return argument != null;
    }

    /**
     * 判断一个集合部位空
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Collection argument) {
        return !isEmpty(argument);
    }

    /**
     * 判断一个数组不为空
     */
    public static boolean isNotEmptyArray(Object[] array) {
        return !isEmptyArray(array);
    }

    /**
     * 判断时一个空数组（null或者length为0）
     */
    public static boolean isEmptyArray(Object[] array) {
        return isNull(array) || array.length == 0;
    }

    public static boolean isNotBlank(String argument) {
        return StringUtils.isNotBlank(argument);
    }

    /**
     * 2个Integer是否相等 <br>
     * Two null references are considered to be equal
     */
    public static boolean integerEqual(Integer num1, Integer num2) {
        return Objects.equals(num1, num2);
    }

    public static boolean longEqual(Long num1, Long num2) {
        return Objects.equals(num1, num2);
    }


}

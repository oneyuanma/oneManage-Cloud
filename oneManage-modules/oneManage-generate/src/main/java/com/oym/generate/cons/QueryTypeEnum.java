package com.oym.generate.cons;

import com.oym.commons.utils.Argument;

/**
 * @author oneyuanma
 * @Date 2021-06-17
 * @Description 查询类型类型枚举
 */
public enum QueryTypeEnum {

    EQUAL("equal", "="),
    NO_EQUAL("no_equal", "!="),
    MORE("more", ">"),
    MORE_EQUAL("more_equal", ">="),
    LESS("less", "<"),
    LESS_EQUAL("less_equal", "<="),
    LIKE("like", "like");

    private String value;
    private String name;

    QueryTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getName(String value) {
        if (Argument.isBlank(value)) {
            return "";
        }
        for (QueryTypeEnum at : QueryTypeEnum.values()) {
            if (at.getValue().equalsIgnoreCase(value)) {
                return at.getName();
            }
        }
        return "";
    }

}

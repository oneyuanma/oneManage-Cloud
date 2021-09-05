package com.oym.commons.cons;

/**
 * @author oneyuanma
 * @Date 2021-06-17
 * @Description 状态枚举
 */
public enum StatusEnum {

    NORMAL("0", "正常"),
    DISABLE("1", "禁用");

    private String value;
    private String name;

    StatusEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Boolean isNormal(String value) {
        return NORMAL.getValue().equals(value);
    }

    public static Boolean isDisable(String value) {
        return DISABLE.getValue().equals(value);
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
}

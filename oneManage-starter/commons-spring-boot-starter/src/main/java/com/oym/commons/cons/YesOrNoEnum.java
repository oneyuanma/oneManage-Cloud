package com.oym.commons.cons;

/**
 * @author oneyuanma
 * @Date 2021-06-17
 * @Description yes no 枚举
 */
public enum YesOrNoEnum {

    NO("false", "NO"),
    YES("true", "YES");

    private String value;
    private String name;

    YesOrNoEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Boolean isNo(String value) {
        return NO.getValue().equals(value);
    }

    public static Boolean isYes(String value) {
        return YES.getValue().equals(value);
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

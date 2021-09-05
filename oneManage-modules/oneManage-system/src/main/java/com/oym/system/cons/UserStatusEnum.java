package com.oym.system.cons;

/**
 * @author oneyuanma
 * @Date 2021-06-17
 * @Description 用户状态枚举
 */
public enum UserStatusEnum {

    NORMAL("0", "正常"),
    DISABLE("1", "禁用");

    private String value;
    private String name;

    UserStatusEnum(String value, String name) {
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
}

package com.oym.log.cons;

/**
 * @author oneyuanma
 * @Date 2021-06-17
 * @Description 登录类型
 */
public enum LoginTypeEnum {

    LOGIN("login", "登录"),
    LOGOUT("logout", "登出");

    private String value;
    private String name;

    LoginTypeEnum(String value, String name) {
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

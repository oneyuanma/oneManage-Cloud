package com.oym.log.cons;

/**
 * @author oneyuanma
 * @Date 2021-06-17
 * @Description 登录状态
 */
public enum LoginStatusEnum {

    SUCCESS("success", "成功"),
    FAIL("fail", "失败");

    private String value;
    private String name;

    LoginStatusEnum(String value, String name) {
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

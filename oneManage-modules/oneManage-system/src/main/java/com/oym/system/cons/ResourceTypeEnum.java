package com.oym.system.cons;

/**
 * @author oneyuanma
 * @Date 2021-06-17
 * @Description 返回码枚举
 */
public enum ResourceTypeEnum {

    MENU("menu", "目录"),
    BUTTON("button", "按钮");

    private String value;
    private String name;

    ResourceTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Boolean isButton(String value) {
        return BUTTON.getValue().equals(value);
    }

    public static Boolean isMenu(String value) {
        return MENU.getValue().equals(value);
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

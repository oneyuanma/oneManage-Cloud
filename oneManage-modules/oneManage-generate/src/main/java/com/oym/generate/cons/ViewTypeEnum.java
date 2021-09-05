package com.oym.generate.cons;

import com.oym.commons.utils.Argument;

/**
 * @author oneyuanma
 * @Date 2021-06-17
 * @Description 前端展示类型枚举
 */
public enum ViewTypeEnum {

    TEXT_INPUT("text_input", "文本框"),
    TEXT_AREA("text_area", "文本域"),
    DOWN_BOX("down_box", "下拉框"),
    SINGLE_BOX("single_box", "单选框"),
    CHECK_BOK("check_box", "复选框"),
    DATE("date", "日期控件");

    private String value;
    private String name;

    ViewTypeEnum(String value, String name) {
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
        for (ViewTypeEnum at : ViewTypeEnum.values()) {
            if (at.getValue().equalsIgnoreCase(value)) {
                return at.getName();
            }
        }
        return "";
    }

}

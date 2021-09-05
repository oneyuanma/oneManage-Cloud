package com.oym.commons.cons;

import com.oym.commons.utils.Argument;

/**
 * @author oneyuanma
 * @Date 2021-06-17
 * @Description 返回码枚举
 */
public enum OperateTypeEnum {

    QUERY("query", "查询"),
    DELETE("delete", "删除"),
    SET("set", "设置"),
    OPERATE("operate", "操作"),
    SAVE("save", "保存");

    private String value;
    private String name;

    OperateTypeEnum(String value, String name) {
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
        for (OperateTypeEnum at : OperateTypeEnum.values()) {
            if (at.getValue().equalsIgnoreCase(value)) {
                return at.getName();
            }
        }
        return "";
    }

}

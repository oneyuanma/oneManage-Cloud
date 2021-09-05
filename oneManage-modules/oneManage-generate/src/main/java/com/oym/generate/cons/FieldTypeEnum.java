package com.oym.generate.cons;

import com.oym.commons.utils.Argument;

/**
 * @author oneyuanma
 * @Date 2021-06-17
 * @Description 字段类型枚举
 */
public enum FieldTypeEnum {

    INTEGER("int,integer", "Integer"),
    STRING("char,varchar,tinyblob,tinytext,blob,text,longblob,longtext", "String"),
    LONG("bigint", "Long"),
    DOUBLE("double,float,decimal", "Double"),
    LOCAL_DATE_TIME("datetime", "LocalDateTime"),
    LOCAL_DATE("date", "LocalDate");


    private String value;
    private String name;

    FieldTypeEnum(String value, String name) {
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
        for (FieldTypeEnum at : FieldTypeEnum.values()) {
            if (at.getValue().contains(value)) {
                return at.getName();
            }
        }
        return STRING.getName();
    }

}

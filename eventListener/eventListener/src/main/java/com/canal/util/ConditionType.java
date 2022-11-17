package com.canal.util;

public enum ConditionType {
    DA(0, ">"),
    XIAO(1, "<"),
    DENG(2, "="),
    IN(2, "in");

    public static final String DA_VALUE = ">";
    public static final String XIAO_VALUE = "<";
    public static final String DENG_VALUE = "=";
    public static final String IN_VALUE = "in";

    private final Integer index;

    private final String value;
    ConditionType(Integer index, String value) {
        this.index = index;
        this.value = value;
    }

    public Integer getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }
}

package com.canal.util;


public enum CanalEventType {
    INSERT(0, 1),
    UPDATE(1, 2),
    DELETE(2, 3);

    public static final int INSERT_VALUE = 1;
    public static final Integer UPDATE_VALUE = 2;
    public static final Integer DELETE_VALUE = 3;

    private final Integer index;

    private final Integer value;
    CanalEventType(Integer index, Integer value) {
        this.index = index;
        this.value = value;
    }

    public Integer getIndex() {
        return index;
    }

    public Integer getValue() {
        return value;
    }
}

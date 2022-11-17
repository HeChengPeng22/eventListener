package com.canal.vo;

import lombok.Data;

@Data
public class ConditionVo {
    private String schemaName;
    /**
     * 监听的表名
     */
    private String tableName;

    /**
     * 数据列ID 若为空则全监听，不为空则指定监听
     */
    private String id;

    /**
     * 监听字段名
     */
    private String fieldName;

    /**
     * 条件
     */
    private Object condition;

    /**
     * 改变的监听字段值
     */
    private String changeFieldValue;

    /**
     * 增:1，删:2，改:3
     */
    private Integer type;

}

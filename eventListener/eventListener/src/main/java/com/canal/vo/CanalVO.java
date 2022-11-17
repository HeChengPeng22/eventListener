package com.canal.vo;

import java.util.Map;

public class CanalVO {
    private String schemaName;
    private String tableName; // 表名
    private String type; // 类型（更新、删除、插入）
    private Map<String,String> data; // 数据JSON  自己转对应表格实体类
    private String id;  // 更新或删除都是根据ID来

    public static CanalVO ok(String schemaName,String tableName,String type,Map<String,String> data,String id){
        CanalVO canalVO=new CanalVO();
        canalVO.setSchemaName(schemaName);
        canalVO.setId(id);
        canalVO.setTableName(tableName);
        canalVO.setType(type);
        canalVO.setData(data);
        return canalVO;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }
}
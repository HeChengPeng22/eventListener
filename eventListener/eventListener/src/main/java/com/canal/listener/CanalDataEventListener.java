package com.canal.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.canal.util.RequestUtils;
import com.canal.vo.CanalVO;
import com.canal.vo.ConditionVo;
import com.google.protobuf.InvalidProtocolBufferException;
import com.canal.annotation.CanalEventListener;
import com.canal.annotation.ListenPoint;
import com.canal.util.CanalEventType;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@CanalEventListener
public class CanalDataEventListener {
    private static List<ConditionVo> conditionVoList = new ArrayList<>();
    public static void init(List<ConditionVo> list){
        //先清空
        conditionVoList.clear();
        //再添加
        conditionVoList.addAll(list);
    }
    /**
     * destination 指定监听实例
     * schema	指定监听数据库名称
     * table	指定表名 {"user"} || {"user","user_info"}
     * eventType 指定监听CURD类型
     */
    @ListenPoint(eventType = {CanalEntry.EventType.INSERT,CanalEntry.EventType.UPDATE,CanalEntry.EventType.DELETE})
    public void listen(CanalEntry.EventType eventType, CanalEntry.Entry entry) {
        //修改
        List<ConditionVo> eventUpdateList = conditionVoList.stream().filter(item -> Objects.equals(item.getType(), CanalEventType.UPDATE_VALUE)).collect(Collectors.toList());
        //添加
        List<ConditionVo> eventInsertList = conditionVoList.stream().filter(item -> Objects.equals(item.getType(), CanalEventType.INSERT_VALUE)).collect(Collectors.toList());
        //删除
        List<ConditionVo> eventDeleteList = conditionVoList.stream().filter(item -> Objects.equals(item.getType(), CanalEventType.DELETE_VALUE)).collect(Collectors.toList());
        if (eventType == CanalEntry.EventType.DELETE) { //删除类
            saveDeleteSql(entry,eventDeleteList);
        } else if (eventType == CanalEntry.EventType.UPDATE) { //修改类
            saveUpdateSql(entry,eventUpdateList);
        } else if (eventType == CanalEntry.EventType.INSERT) { // 新增
            saveInsertSql(entry,eventInsertList);
        }
    }

    /**
     * 监听修改事件
     * @param entry
     */
    private void saveUpdateSql(CanalEntry.Entry entry,List<ConditionVo> conditionVoList) {
        try {
            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
            for (CanalEntry.RowData rowData : rowDatasList) {
                //数据变更前
                List<CanalEntry.Column> beforeColumnsList2 = rowData.getBeforeColumnsList();
                //数据变更后
                List<CanalEntry.Column> afterColumnsList2 = rowData.getAfterColumnsList();
                Map<String, String> data1 = new HashMap<>();
                afterColumnsList2.stream().forEach(item -> {
                    if(StringUtils.isNotEmpty(item.getValue())){
                        data1.put(lineToHump(item.getName()), item.getValue());
                    }
                });
                Map<String, String> data2 = new HashMap<>();
                beforeColumnsList2.stream().forEach(item -> {
                    if(StringUtils.isNotEmpty(item.getValue())){
                        data2.put(lineToHump(item.getName()), item.getValue());
                    }
                });
                //修改后的数据
                List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
                System.out.println("afterColumnsList.get(0).getValue() = " + afterColumnsList.get(0).getValue());
                CanalVO afterUpdate = CanalVO.ok(entry.getHeader().getSchemaName(),entry.getHeader().getTableName(), "UPDATE", data1, afterColumnsList.get(0).getValue());
                //修改前的数据
                List<CanalEntry.Column> oldColumnList = rowData.getAfterColumnsList();
                CanalVO before = CanalVO.ok(entry.getHeader().getSchemaName(),entry.getHeader().getTableName(),"UPDATE", data2, oldColumnList.get(0).getValue());
                //进行规则校验
                RequestUtils.updateRequest(before,afterUpdate,conditionVoList);
                System.out.println(JSON.toJSONString(before));
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
    /**
     * 监听删除事件
     *
     * @param entry
     */
    private void saveDeleteSql(CanalEntry.Entry entry,List<ConditionVo> conditionVoList) {
        try {
            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
            for (CanalEntry.RowData rowData : rowDatasList) {
                List<CanalEntry.Column> oldColumnList = rowData.getBeforeColumnsList();
                CanalVO delete = CanalVO.ok(entry.getHeader().getSchemaName(),entry.getHeader().getTableName(), "DELETE", null, oldColumnList.get(0).getValue());
                System.out.println("删除返回 ： " + JSON.toJSONString(delete));
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
    /**
     * 监听插入事件
     *
     * @param entry
     */
    private void saveInsertSql(CanalEntry.Entry entry,List<ConditionVo> conditionVoList) {
        try {
            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
            for (CanalEntry.RowData rowData : rowDatasList) {
                List<CanalEntry.Column> columnList = rowData.getAfterColumnsList();
                Map<String, String> data = new HashMap<>();
                columnList.stream().forEach(item -> {
                    if(StringUtils.isNotEmpty(item.getValue())){
                        data.put(lineToHump(item.getName()), item.getValue());
                    }
                });
                CanalVO insert = CanalVO.ok(entry.getHeader().getSchemaName(),entry.getHeader().getTableName(), "INSERT", data, null);
                System.out.println("插入返回 ： " + JSON.toJSONString(insert));
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}

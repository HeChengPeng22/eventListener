package com.canal.util;

import com.canal.vo.CanalVO;
import com.canal.vo.ConditionVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RequestUtils {

    public static void updateRequest(CanalVO before, CanalVO after, List<ConditionVo> conditionVos){

        for (ConditionVo conditionVo : conditionVos) {
            if (conditionVo.getTableName().equals(after.getTableName())){
                if (conditionVo.getId() != null){
                    //ID存在的情况
                     if (after.getId().equals(conditionVo.getId())){
                         String oldValue = before.getData().get(conditionVo.getFieldName());
                         String changeValue = after.getData().get(conditionVo.getFieldName());
                         if (null != changeValue && !oldValue.equals(changeValue)){
                             updateType(conditionVo.getCondition().toString(),changeValue,conditionVo.getChangeFieldValue(),conditionVo.getId());
                         }
                     }
                }else{
                    // ID不存在的情况
                    String oldValue = before.getData().get(conditionVo.getFieldName());
                    String changeValue = after.getData().get(conditionVo.getFieldName());
                    if (null != changeValue && !oldValue.equals(changeValue)){
                        updateType(conditionVo.getCondition().toString(),changeValue,conditionVo.getChangeFieldValue(),null);
                    }
                }
                // 不存在ID的情况
                // if (after.getId().equals(conditionVo.getId())){
                // }
            }
        }
    }

    public static void insertRequest(CanalVO insert,List<ConditionVo> conditionVoList){

    }

    public static void deleteRequest(CanalVO delete,List<ConditionVo> conditionVoList){

    }
    private static void updateType(String condition, String oldValue, String newValue,String id) {
        if (condition.equals(ConditionType.DA_VALUE)){
            if (Integer.parseInt(oldValue) > Integer.parseInt(newValue)){
                //发起请求
            }
        }else if (condition.equals(ConditionType.XIAO_VALUE)){
            if (Integer.parseInt(oldValue) < Integer.parseInt(newValue)){
                System.out.println("发送请求 <<<<<<<<<<");
            }
        }else if (condition.equals(ConditionType.DENG_VALUE)){
            if (oldValue.equals(newValue)){
                System.out.println("发送请求 ==========");
            }
        }else if (condition.equals(ConditionType.IN_VALUE)){
            if (oldValue.contains(newValue)){
                //发起请求
            }
        }
    }
    private static void insertType(String condition, String oldValue, String newValue) {
        if (condition.equals(ConditionType.DA_VALUE)){
            if (Integer.parseInt(oldValue) > Integer.parseInt(newValue)){
                //发起请求
            }
        }else if (condition.equals(ConditionType.XIAO_VALUE)){
            if (Integer.parseInt(oldValue) < Integer.parseInt(newValue)){
                System.out.println("发送请求 <<<<<<<<<<");
            }
        }else if (condition.equals(ConditionType.DENG_VALUE)){
            if (oldValue.equals(newValue)){
                System.out.println("发送请求 ==========");
            }
        }else if (condition.equals(ConditionType.IN_VALUE)){
            if (oldValue.contains(newValue)){
                //发起请求
            }
        }
    }
    private static void deleteType(String condition, String oldValue, String newValue) {
        if (condition.equals(ConditionType.DA_VALUE)){
            if (Integer.parseInt(oldValue) > Integer.parseInt(newValue)){
                //发起请求
            }
        }else if (condition.equals(ConditionType.XIAO_VALUE)){
            if (Integer.parseInt(oldValue) < Integer.parseInt(newValue)){
                System.out.println("发送请求 <<<<<<<<<<");
            }
        }else if (condition.equals(ConditionType.DENG_VALUE)){
            if (oldValue.equals(newValue)){
                System.out.println("发送请求 ==========");
            }
        }else if (condition.equals(ConditionType.IN_VALUE)){
            if (oldValue.contains(newValue)){
                //发起请求
            }
        }
    }
    public static void main(String[] args) {
        ConditionVo conditionVo = new ConditionVo();
        conditionVo.setTableName("user");
        conditionVo.setCondition("=");
        conditionVo.setId("1");
        conditionVo.setFieldName("account");
        conditionVo.setChangeFieldValue("2582721513");
        conditionVo.setType(CanalEventType.UPDATE_VALUE);

        ConditionVo conditionVo2 = new ConditionVo();
        conditionVo2.setTableName("user");
        conditionVo2.setCondition("<");
        conditionVo2.setId("1");
        conditionVo2.setFieldName("nickName");
        conditionVo2.setChangeFieldValue("10");
        conditionVo2.setType(CanalEventType.UPDATE_VALUE);
        ConditionVo conditionVo3 = new ConditionVo();
        conditionVo3.setTableName("user");
        conditionVo3.setCondition("<");
        conditionVo3.setId("1");
        conditionVo3.setFieldName("nickName");
        conditionVo3.setChangeFieldValue("10");
        conditionVo3.setType(CanalEventType.DELETE_VALUE);
        ConditionVo conditionVo4 = new ConditionVo();
        conditionVo4.setTableName("user");
        conditionVo4.setCondition("<");
        conditionVo4.setId("1");
        conditionVo4.setFieldName("nickName");
        conditionVo4.setChangeFieldValue("10");
        conditionVo4.setType(CanalEventType.INSERT_VALUE);
        List<ConditionVo> conditionVoList = new ArrayList<>();
        conditionVoList.add(conditionVo);
        conditionVoList.add(conditionVo2);
        conditionVoList.add(conditionVo3);
        conditionVoList.add(conditionVo4);
        List<ConditionVo> eventUpdateList = conditionVoList.stream().filter(item -> Objects.equals(item.getType(), CanalEventType.UPDATE_VALUE)).collect(Collectors.toList());
        List<ConditionVo> eventInsertList = conditionVoList.stream().filter(item -> Objects.equals(item.getType(), CanalEventType.INSERT_VALUE)).collect(Collectors.toList());
        List<ConditionVo> eventDeleteList = conditionVoList.stream().filter(item -> Objects.equals(item.getType(), CanalEventType.DELETE_VALUE)).collect(Collectors.toList());
        System.out.println("eventDeleteList = " + eventDeleteList);
        System.out.println("eventInsertList = " + eventInsertList);
        System.out.println("eventUpdateList = " + eventUpdateList);
    }
}

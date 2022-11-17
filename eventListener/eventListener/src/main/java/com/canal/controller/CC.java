package com.canal.controller;

import com.canal.config.CanalConfig;
import com.canal.listener.CanalDataEventListener;
import com.canal.util.CanalEventType;
import com.canal.util.CanalUtil;
import com.canal.vo.ConditionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("yyq")
public class CC {

    @Autowired
    CanalConfig canalConfig;
    @RequestMapping("start")
    public void start(){
        System.out.println("启动canal");
        CanalUtil.start(canalConfig);
    }
    @RequestMapping("stop")
    public void stop() throws InterruptedException {
        CanalUtil.stop(canalConfig);
        Thread.sleep(2000);
        System.out.println("关闭canal");
    }
    @RequestMapping("restart")
    public void reStart(){
        List<ConditionVo> conditionVoList = new ArrayList<>();
        ConditionVo conditionVo = new ConditionVo();
        conditionVo.setSchemaName("yyq_user");
        conditionVo.setTableName("user"); //表名
        conditionVo.setCondition("="); // 条件
        conditionVo.setId("1"); // 表ID
        conditionVo.setFieldName("account"); //监听字段
        conditionVo.setChangeFieldValue("2582721513"); // 监听字段内容
        conditionVo.setType(CanalEventType.UPDATE_VALUE); // 监听类型

        ConditionVo conditionVo2 = new ConditionVo();
        conditionVo2.setSchemaName("yyq_user");
        conditionVo2.setTableName("user");
        conditionVo2.setCondition("<");
        conditionVo2.setId("1");
        conditionVo2.setFieldName("nickName");
        conditionVo2.setChangeFieldValue("10");
        conditionVo2.setType(CanalEventType.UPDATE_VALUE);
        conditionVoList.add(conditionVo);
        conditionVoList.add(conditionVo2);
        CanalDataEventListener.init(conditionVoList);
        System.out.println("启动canal");
        CanalUtil.start(canalConfig);
    }
}

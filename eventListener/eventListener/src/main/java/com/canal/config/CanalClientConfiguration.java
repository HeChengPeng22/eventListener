package com.canal.config;


import com.canal.util.BeanUtil;
import com.canal.vo.ConditionVo;
import com.canal.client.CanalClient;
import com.canal.client.SimpleCanalClient;
import com.canal.client.transfer.MessageTransponders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

public class CanalClientConfiguration {

    private final static Logger logger = LoggerFactory.getLogger(CanalClientConfiguration.class);

    @Autowired
    private CanalConfig canalConfig;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public BeanUtil beanUtil() {
        return new BeanUtil();
    }

    @Bean
    private CanalClient canalClient() {
        ConditionVo conditionVo = new ConditionVo();
        // TODO 目前为SpringBoot 启动需要使用@EnableCanalClient标识启动类
        // TODO 后续可修改为触发执行
        CanalClient canalClient = new SimpleCanalClient(canalConfig, MessageTransponders.defaultMessageTransponder());
        canalClient.start();
        logger.info("Starting canal client....");
        return canalClient;
    }
}

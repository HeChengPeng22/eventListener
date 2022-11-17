package com.canal.util;

import com.canal.client.CanalClient;
import com.canal.client.SimpleCanalClient;
import com.canal.client.transfer.MessageTransponders;
import com.canal.config.CanalConfig;

public class CanalUtil {

    public static void start(CanalConfig canalConfig){

        CanalClient canalClient = new SimpleCanalClient(canalConfig, MessageTransponders.defaultMessageTransponder());
        canalClient.start();
    }
    public static void stop(CanalConfig canalConfig){
        CanalClient canalClient = new SimpleCanalClient(canalConfig, MessageTransponders.defaultMessageTransponder());
        canalClient.stop();
    }
}

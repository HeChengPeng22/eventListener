package com.canal.client.transfer;

import com.alibaba.otter.canal.client.CanalConnector;
import com.canal.client.ListenerPoint;
import com.canal.config.CanalConfig;
import com.canal.event.CanalEventListener;

import java.util.List;
import java.util.Map;


public interface TransponderFactory {

    /**
     * @param connector connector
     * @param config config
     * @param listeners listeners
     * @param annoListeners annoListeners
     * @return MessageTransponder
     */
    MessageTransponder newTransponder(CanalConnector connector, Map.Entry<String, CanalConfig.Instance> config, List<CanalEventListener> listeners,
                                      List<ListenerPoint> annoListeners);
}

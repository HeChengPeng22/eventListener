package com.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.canal.config.CanalConfig;
import com.canal.event.CanalEventListener;
import com.canal.util.BeanUtil;
import com.canal.annotation.ListenPoint;
import com.canal.client.transfer.TransponderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class SimpleCanalClient extends AbstractCanalClient {

    /**
     * executor
     */
    private ThreadPoolExecutor executor;

    /**
     * listeners which are used by implementing the Interface
     */
    private final List<CanalEventListener> listeners = new ArrayList<>();

    /**
     * listeners which are used by annotation
     */
    private final List<ListenerPoint> annoListeners = new ArrayList<>();

    private final static Logger logger = LoggerFactory.getLogger(SimpleCanalClient.class);

    public SimpleCanalClient(CanalConfig canalConfig, TransponderFactory factory) {
        super(canalConfig, factory);
        executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(), Executors.defaultThreadFactory());
        initListeners();
    }

    @Override
    protected void process(CanalConnector connector, Map.Entry<String, CanalConfig.Instance> config) {
        executor.submit(factory.newTransponder(connector, config, listeners, annoListeners));
    }

    @Override
    public void stop() {
        super.stop();
        executor.shutdown();
    }

    /**
     * init listeners
     */
    private void initListeners() {
        logger.info("{}: initializing the listeners....", Thread.currentThread().getName());
        List<CanalEventListener> list = BeanUtil.getBeansOfType(CanalEventListener.class);
        if (list != null) {
            listeners.addAll(list);
        }
        Map<String, Object> listenerMap = BeanUtil.getBeansWithAnnotation(com.canal.annotation.CanalEventListener.class);
        if (listenerMap != null) {
            for (Object target : listenerMap.values()) {
                Method[] methods = target.getClass().getDeclaredMethods();
                if (methods != null && methods.length > 0) {
                    for (Method method : methods) {
                        //???????????????ListenPointa
//                        ListenPoint l = AnnotationUtils.findAnnotation(method, ListenPoint.class);
                        //?????????????????????????????????
                        ListenPoint l = AnnotatedElementUtils.getMergedAnnotation(method, ListenPoint.class);
                        if (l != null) {
                            annoListeners.add(new ListenerPoint(target, method, l));
                        }
                    }
                }
            }
        }
        logger.info("{}: initializing the listeners end.", Thread.currentThread().getName());
        if (logger.isWarnEnabled() && listeners.isEmpty() && annoListeners.isEmpty()) {
            logger.warn("{}: No listener found in context! ", Thread.currentThread().getName());
        }
    }
}

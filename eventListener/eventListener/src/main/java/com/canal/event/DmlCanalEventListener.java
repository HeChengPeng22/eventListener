package com.canal.event;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.util.Objects;


public interface DmlCanalEventListener extends CanalEventListener {

    @Override
    default void onEvent(CanalEntry.EventType eventType, CanalEntry.Entry rowData) {
        Objects.requireNonNull(eventType);
        switch (eventType) {
            case INSERT:
                onInsert(rowData);
                break;
            case UPDATE:
                onUpdate(rowData);
                break;
            case DELETE:
                onDelete(rowData);
                break;
            default:
                break;
        }
    }

    /**
     * fired on insert event
     *
     * @param rowData rowData
     */
    void onInsert(CanalEntry.Entry rowData);

    /**
     * fired on update event
     *
     * @param rowData rowData
     */
    void onUpdate(CanalEntry.Entry rowData);

    /**
     * fired on delete event
     *
     * @param rowData rowData
     */
    void onDelete(CanalEntry.Entry rowData);

}

package com.canal.client.transfer;


public class MessageTransponders {

    public static TransponderFactory defaultMessageTransponder() {
        return new DefaultTransponderFactory();
    }

}

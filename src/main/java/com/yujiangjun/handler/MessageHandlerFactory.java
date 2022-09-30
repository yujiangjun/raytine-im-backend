package com.yujiangjun.handler;

import com.yujiangjun.constants.CoreEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageHandlerFactory {

    private MessageHandlerFactory(){}

    private static final Map<CoreEnum.MessageType,MessageHandler> messageHandlerMap = new ConcurrentHashMap<>();
    static {
        messageHandlerMap.put(CoreEnum.MessageType.TEXT,new TextMessageHandler());
        messageHandlerMap.put(CoreEnum.MessageType.IMAGE,new BinaryMessageHandler());
        messageHandlerMap.put(CoreEnum.MessageType.VIDEO,new BinaryMessageHandler());
    }


    public static MessageHandler getMessageHandler(CoreEnum.MessageType messageHandlerType){
        return messageHandlerMap.get(messageHandlerType);
    }
}

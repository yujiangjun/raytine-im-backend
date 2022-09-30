package com.yujiangjun.handler;

import com.yujiangjun.channel.WebSocketChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;

public class ChannelSupervise {

    private ChannelSupervise(){}
    private static final ChannelGroup globalGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final ConcurrentHashMap<ChannelId,String> channelMap = new ConcurrentHashMap<>();

    public static void addChannel(String userId,Channel channel){
        globalGroup.add(WebSocketChannel.createChannel(userId,channel));
        channelMap.put(channel.id(),userId);
    }

    public static void removeChannel(Channel channel){
        globalGroup.remove(channel);
        channelMap.remove(channel.id());
    }

    public static WebSocketChannel findChannel(Channel channel){
        Channel channel1 = globalGroup.find(channel.id());
        if (channel1 instanceof WebSocketChannel){
            return (WebSocketChannel) channel1;
        }
        return null;
    }

    public static String getUserId(Channel channel){
        WebSocketChannel websocketChannel = findChannel(channel);
        String userId;
        if (null == websocketChannel) {
            String val = channelMap.get(channel.id());
            if (null == val || "".equalsIgnoreCase(val)) {
                userId = "unknown";
            } else {
                userId = val;
            }
        } else {
            userId = websocketChannel.getUserId();
        }

        return userId;
    }
    public static void send2All(TextWebSocketFrame tws){
        globalGroup.writeAndFlush(tws);
    }
}

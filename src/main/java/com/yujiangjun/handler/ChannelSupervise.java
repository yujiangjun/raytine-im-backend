package com.yujiangjun.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;

public class ChannelSupervise {
    private static ChannelGroup globalGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static ConcurrentHashMap<String, ChannelId> channelMap = new ConcurrentHashMap<>();

    public static void addChannel(Channel channel){
        globalGroup.add(channel);
        channelMap.put(channel.id().asLongText(),channel.id());
    }

    public static void removeChannel(Channel channel){
        globalGroup.remove(channel);
        channelMap.remove(channel.id().asLongText());
    }

    public static Channel findChannel(String id){
        return globalGroup.find(channelMap.get(id));
    }

    public static void send2All(TextWebSocketFrame tws){
        globalGroup.writeAndFlush(tws);
    }
}

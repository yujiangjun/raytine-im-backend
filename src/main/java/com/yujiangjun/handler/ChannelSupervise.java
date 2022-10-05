package com.yujiangjun.handler;

import cn.hutool.core.util.StrUtil;
import com.yujiangjun.channel.WebSocketChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelSupervise {

    private ChannelSupervise(){}
    private static final ChannelGroup globalGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final ConcurrentHashMap<ChannelId,String> channelMap = new ConcurrentHashMap<>();

    public static void addChannel(String userId,Channel channel){
        globalGroup.add(channel);
        channelMap.put(channel.id(),userId);
    }

    public static void removeChannel(Channel channel){
        globalGroup.remove(channel);
        channelMap.remove(channel.id());
    }

    public static WebSocketChannel findChannel(Channel channel){
        Channel channel1 = globalGroup.find(channel.id());
//        if (channel1 instanceof WebSocketChannel){
//            return (WebSocketChannel) channel1;
//        }
        return null;
    }

    public static Channel getChannelByUserId(String userId){
        Channel channel = null;
        for (Map.Entry<ChannelId, String> entry : channelMap.entrySet()) {
            if (!StrUtil.equals(entry.getValue(),userId)){
                continue;
            }
            ChannelId key = entry.getKey();
            channel = globalGroup.find(key);
            if (channel == null){
                continue;
            }
            if (!channel.isActive()){
                continue;
            }
        }
        return channel;
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
//    public static void send2All(TextWebSocketFrame tws){
//        globalGroup.writeAndFlush(tws);
//    }
}

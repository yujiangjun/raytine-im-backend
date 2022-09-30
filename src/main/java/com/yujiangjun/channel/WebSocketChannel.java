package com.yujiangjun.channel;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class WebSocketChannel extends NioSocketChannel {
    private String userId;
    private Channel channel;

    private WebSocketChannel(String userId, Channel channel) {
        this.userId = userId;
        this.channel = channel;
    }

    public static WebSocketChannel createChannel(String userId,Channel channel){
        return new WebSocketChannel(userId,channel);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}

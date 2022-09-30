package com.yujiangjun.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public interface MessageHandler {

    void doHandle(ChannelHandlerContext ctx, WebSocketFrame frame);
}

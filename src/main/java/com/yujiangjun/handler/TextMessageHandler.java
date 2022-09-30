package com.yujiangjun.handler;

import cn.hutool.json.JSONUtil;
import com.yujiangjun.channel.WebSocketChannel;
import com.yujiangjun.message.TextMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TextMessageHandler implements MessageHandler{
    @Override
    public void doHandle(ChannelHandlerContext ctx, WebSocketFrame frame) {
        TextWebSocketFrame textMessage = (TextWebSocketFrame) frame;
        String req = textMessage.text();
        TextMessage message = JSONUtil.toBean(req, TextMessage.class);
        String targetId = message.getTargetId();
        Channel channel = ChannelSupervise.getChannelByUserId(targetId);
        frame.retain(2);
        channel.writeAndFlush(frame);
        if (log.isDebugEnabled()){
            log.debug("send channel id:{}",ctx.channel().id());
            log.debug("receive channel id:{}",channel.id());
        }
        ctx.writeAndFlush(frame);
    }
}

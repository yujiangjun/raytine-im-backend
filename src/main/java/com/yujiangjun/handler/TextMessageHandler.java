package com.yujiangjun.handler;

import cn.hutool.json.JSONUtil;
import com.yujiangjun.message.TextMessage;
import com.yujiangjun.util.DistributedIDUtil;
import com.yujiangjun.util.HisMessageUtil;
import com.yujiangjun.util.JsonUtil;
import com.yujiangjun.util.SpringContextUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
public class TextMessageHandler implements MessageHandler{
    @Override
    public void doHandle(ChannelHandlerContext ctx, WebSocketFrame frame) {
        TextWebSocketFrame textMessage = (TextWebSocketFrame) frame;
        String req = textMessage.text();
        TextMessage message = JSONUtil.toBean(req, TextMessage.class);
        String targetId = message.getTargetId();
        Channel channel = ChannelSupervise.getChannelByUserId(targetId);

        message.setMsgId(DistributedIDUtil.getId());
        TextWebSocketFrame resp = new TextWebSocketFrame(JsonUtil.writeObject(message));

        if (channel!=null){
            resp.retain(2);
            resp.content().markReaderIndex();
            ctx.writeAndFlush(resp);
            resp.content().resetReaderIndex();
            if (log.isDebugEnabled()){
                log.debug("send channel id:{}",ctx.channel().id());
                log.debug("receive channel id:{}",channel.id());
            }
            channel.writeAndFlush(resp);
        }else {
            ctx.writeAndFlush(resp);
            RedisTemplate<String,TextMessage> redisTemplate = SpringContextUtil.getBean(RedisTemplate.class,"textMessageRedisTemplate");
            redisTemplate.boundListOps(message.getMsgId()).leftPushAll(message);
        }



        HisMessageUtil.saveMessage(message);
    }
}

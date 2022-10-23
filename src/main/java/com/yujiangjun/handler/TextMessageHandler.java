package com.yujiangjun.handler;

import cn.hutool.json.JSONUtil;
import com.yujiangjun.constants.CoreEnum;
import com.yujiangjun.dto.Session;
import com.yujiangjun.message.TextMessage;
import com.yujiangjun.service.UnReadMesService;
import com.yujiangjun.service.remote.MessageSessionService;
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

import java.util.Date;
import java.util.Objects;

import static com.yujiangjun.constants.CoreEnum.MessageDict.RECEIVE;
import static com.yujiangjun.constants.CoreEnum.MessageDict.SEND;

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
        message.setDict(SEND.getCode());
        TextWebSocketFrame toMe = new TextWebSocketFrame(JsonUtil.writeObject(message));
        if (Objects.equals(CoreEnum.MessageCat.USER_MES.getCode(),message.getCat())){
            SpringContextUtil.getBean(UnReadMesService.class).undoMesCountUpdate(message.getTargetId(), message.getSendUserId());
        }else if (Objects.equals(CoreEnum.MessageCat.MES_ACK.getCode(),message.getCat())){
            SpringContextUtil.getBean(UnReadMesService.class).cleanUndoMsgCount(message.getTargetId(), message.getSendUserId());
        }
        saveSession(message);
        if (channel!=null){
            message.setDict(RECEIVE.getCode());
            TextWebSocketFrame toTarget = new TextWebSocketFrame(JsonUtil.writeObject(message));
            ctx.writeAndFlush(toMe);
            if (log.isDebugEnabled()){
                log.debug("send channel id:{}",ctx.channel().id());
                log.debug("receive channel id:{}",channel.id());
            }
            channel.writeAndFlush(toTarget);
        }else {
            ctx.writeAndFlush(toMe);
            RedisTemplate<String,TextMessage> redisTemplate = SpringContextUtil.getBean(RedisTemplate.class,"textMessageRedisTemplate");
            redisTemplate.boundListOps(message.getTargetId()).leftPushAll(message);
        }



        HisMessageUtil.saveMessage(message);
    }

    private void saveSession(TextMessage message){
        MessageSessionService messageSessionService = SpringContextUtil.getBean(MessageSessionService.class);
        Session session = new Session();
        session.setSessionTime(new Date());
        session.setMsgContent(message.getContent());
        session.setSendId(Integer.parseInt(message.getSendUserId()));
        session.setSendUserName(message.getSendUserName());
        session.setSendUserAvatar(message.getSendAvatar());
        session.setTargetId(Integer.parseInt(message.getTargetId()));
        session.setTargetName(session.getTargetName());
        session.setTargetAvatar(session.getTargetAvatar());
        session.setChatType(message.getType());
        messageSessionService.addOrUpdate(session);
    }
}

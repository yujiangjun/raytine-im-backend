package com.yujiangjun.handler;

import cn.hutool.core.text.CharSequenceUtil;
import com.yujiangjun.util.JwtUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.yujiangjun.constants.Constant.JWT_PASSWORD;
import static com.yujiangjun.constants.Constant.TOKEN;
import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;

@Slf4j
public class NioWebSocketHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketServerHandshaker handShaker;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
      if (log.isDebugEnabled()){
          log.debug("收到消息:{}",msg);
      }
      if (msg instanceof FullHttpRequest){
          handleHttpRequest(ctx,(FullHttpRequest) msg);
      }else if (msg instanceof WebSocketFrame){
          handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
      }

    }

    @Override
    public void channelActive(@NonNull ChannelHandlerContext ctx) throws Exception {
        if (log.isDebugEnabled()){
            log.debug("客户端连接:{}",ctx.channel());
        }
    }

    @Override
    public void channelInactive(@NonNull ChannelHandlerContext ctx) throws Exception {
        if (log.isDebugEnabled()){
            log.debug("客户端断开连接");
        }
        ChannelSupervise.removeChannel(ctx.channel());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handlerWebSocketFrame(ChannelHandlerContext ctx,WebSocketFrame frame){
        if (frame instanceof CloseWebSocketFrame){
            handShaker.close(ctx.channel(),(CloseWebSocketFrame) frame.retain());
            return;
        }
        if (frame instanceof PingWebSocketFrame){
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if (!(frame instanceof TextWebSocketFrame)){
            if (log.isDebugEnabled()){
                log.debug("暂不支持非文本消息");
            }
            throw new UnsupportedOperationException(String.format("%s frame types not supported",frame.getClass().getName()));
        }
        String request=((TextWebSocketFrame)frame).text();
        if (log.isDebugEnabled()){
            log.debug("收到消息:{}",request);
        }
        TextWebSocketFrame response = new TextWebSocketFrame(new Date().toString() + ctx.channel().id() + ":" + request);
        ChannelSupervise.send2All(response);
    }

    private void handleHttpRequest(ChannelHandlerContext ctx,FullHttpRequest req){
        if (!req.decoderResult().isSuccess()
    || (!"websocket".equals(req.headers().get("Upgrade")))){
            sendHttpResponse(ctx,req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        String token = req.headers().get(TOKEN);
        if (CharSequenceUtil.isEmpty(token)){
            if (log.isDebugEnabled()){
                log.debug("token为空,当前未登录");
            }
            sendHttpResponse(ctx,req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.BAD_REQUEST));
        }
        String userId = JwtUtil.verify(token, JWT_PASSWORD);
        ChannelSupervise.addChannel(userId,ctx.channel());

        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8081/websocket", null, false);
        handShaker = wsFactory.newHandshaker(req);
        if (handShaker==null){
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        }else {
            handShaker.handshake(ctx.channel(),req);
        }
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res){
        if (res.status().code()!=200){
            ByteBuf byteBuf = Unpooled.copiedBuffer(res.status().toString().getBytes(StandardCharsets.UTF_8));
            res.content().writeBytes(byteBuf);
            byteBuf.release();
        }
        ChannelFuture future = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.status().code()!=200){
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
}

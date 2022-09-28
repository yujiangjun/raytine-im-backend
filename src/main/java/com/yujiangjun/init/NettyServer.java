package com.yujiangjun.init;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NettyServer implements CommandLineRunner {

    @Value("${netty.port}")
    private int port;
    @Override
    public void run(String... args) throws Exception {
        if (log.isDebugEnabled()){
            log.debug("server 正在启动");
        }
        NioEventLoopGroup group = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        try{
          ServerBootstrap server = new ServerBootstrap();
          server.group(group,work);
          server.channel(NioServerSocketChannel.class);
          server.childHandler(new NioWebSocketChannelInitializer());
          Channel channel = server.bind(port).sync().channel();
          if (log.isDebugEnabled()){
              log.info("server 启动完成...");
          }
          channel.closeFuture().sync();
      }catch (InterruptedException e){
          log.error("",e);
      }finally {
          group.shutdownGracefully();
          group.shutdownGracefully();
      }

    }
}

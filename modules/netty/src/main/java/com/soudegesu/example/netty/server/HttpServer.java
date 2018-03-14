package com.soudegesu.example.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.MultithreadEventLoopGroup;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class HttpServer implements CommandLineRunner {

    @Autowired
    private HttpChannelnitializer channelnitializer;

    @Value("${server.port}")
    private int port;

    private MultithreadEventLoopGroup bossGroup = new KQueueEventLoopGroup(1);

    private MultithreadEventLoopGroup workerGroup = new KQueueEventLoopGroup();

//    private MultithreadEventLoopGroup bossGroup = new EpollEventLoopGroup(4);
//
//    private MultithreadEventLoopGroup workerGroup = new EpollEventLoopGroup();

    @Override
    public void run(String... args) {
        try {
            ServerBootstrap server = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(KQueueServerSocketChannel.class)
//                    .channel(EpollServerSocketChannel.class)
                    .childHandler(channelnitializer)
                    .option(ChannelOption.SO_BACKLOG, 512)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);

            ChannelFuture future = server.bind(port);
            future.sync();
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }

    @PreDestroy
    private void shutdown() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}

package com.lc.misc.netty.nettytest.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            //create ServerBootstrap instance
            ServerBootstrap b = new ServerBootstrap();
            //Specifies NIO transport, local socket address
            //Adds handler to channel pipeline
            b.group(boss, work).channel(NioServerSocketChannel.class)    //指定NIO的模式
                    //.option(ChannelOption.SO_BACKLOG, 1024)		//设置tcp缓冲区
                    //.option(ChannelOption.SO_SNDBUF, 32*1024)	//设置发送缓冲大小
                    //.option(ChannelOption.SO_RCVBUF, 32*1024)	//这是接收缓冲大小
                    .option(ChannelOption.SO_KEEPALIVE, true)    //保持连接
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            // ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
                            ch.pipeline()
                                    //.addLast(new DelimiterBasedFrameDecoder(1024, buf))
                                    .addLast("decoder", new StringDecoder(Charset.forName("UTF-8")))
                                    .addLast("encoder", new StringEncoder(Charset.forName("UTF-8")))
                                    .addLast(new SimpleChatServerHandler());
                        }
                    });
            //Binds server, waits for server to close, and releases resources
            ChannelFuture f = b.bind(4455).sync();
            System.out.println(EchoServer.class.getName() + "started and listen on " + f.channel().localAddress());
            f.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully().sync();
            work.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoServer(65535).start();
    }

}

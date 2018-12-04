package com.lc.misc.netty.nettytest.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class EchoClient {

	private final String host;
	private final int port;

	public EchoClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline()
                                    .addLast("decoder", new StringDecoder(Charset.forName("UTF-8")))
                                    .addLast("encoder", new StringEncoder(Charset.forName("UTF-8")))
									//.addLast(new StringEncoder())
                                 //   .addLast(new StringDecoder())
									.addLast(new EchoClientHandler());
						}
					});
			ChannelFuture f = b.connect(new InetSocketAddress(host, port)).sync();
            Channel channel = f.channel();
           BufferedReader br  =new BufferedReader(new InputStreamReader(System.in));
            String msg = "";
            while(!"see-you".equals(msg)){
                 msg = br.readLine();
                channel.writeAndFlush(msg);
            }
                     // f.channel().closeFuture().sync();
        } finally {
			group.shutdownGracefully().sync();
		}
	}

	public static void main(String[] args) throws Exception {
		new EchoClient("localhost", 4455).start();
	}
}

package com.lc.misc.netty.nettytest.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.UnsupportedEncodingException;

public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> { // (1)

	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
		Channel incoming = ctx.channel();
		// Broadcast a message to multiple Channels
		channels.writeAndFlush(getSendByteBuf("[SERVER] - " + incoming.remoteAddress() + " 加入\n"));
		
		channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
		Channel incoming = ctx.channel();
		// Broadcast a message to multiple Channels
		channels.writeAndFlush(getSendByteBuf("[SERVER] - " + incoming.remoteAddress() + " 离开\n"));

		// A closed Channel is automatically removed from ChannelGroup,
		// so there is no need to do "channels.remove(ctx.channel());"
    }

    private ByteBuf getSendByteBuf(String message) throws UnsupportedEncodingException {

        byte[] req = message.getBytes("GBK");
        ByteBuf pingMessage = Unpooled.buffer();
        pingMessage.writeBytes(req);

        return pingMessage;
    }
    @Override
	protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception { // (4)
		Channel incoming = ctx.channel();
		System.out.println(s);
		for (Channel channel : channels) {
            if (channel != incoming){
                channel.writeAndFlush("[" + incoming.remoteAddress() + "] " + s + "\n");
            } else {
            	channel.writeAndFlush("[you] " + s + "\n");
            }
        }
	}
  
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient: "+incoming.remoteAddress()+" -online");
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient:"+incoming.remoteAddress()+" -offline");
	}
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
    	Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient:"+incoming.remoteAddress()+" -exception");
        // 当出现异常就关闭连接
        //cause.printStackTrace();
        ctx.close();
    }
}
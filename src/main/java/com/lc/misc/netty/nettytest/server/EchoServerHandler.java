package com.lc.misc.netty.nettytest.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String response = (String) msg;
        System.out.println("Server received: " + (response).toString());
        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
        // ctx.write(msg);
        //ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
/**
 * ByteBuf buf = (ByteBuf) msg;
 * byte[] req = new byte[buf.readableBytes()];
 * buf.readBytes(req);
 * String body = new String(req, "utf-8");
 * System.out.println("Server :" + body );
 * String response = "进行返回给客户端的响应：" + body ;e
 * ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
 */
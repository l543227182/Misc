package main.cn.lc.rpc.netty;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import main.cn.lc.rpc.Serializer.KryoSerializer;

public class NettyKryoEncoder extends MessageToByteEncoder<Object>
{	
	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out)
			throws Exception 
	{
		KryoSerializer.serialize(msg, out);
	}
}

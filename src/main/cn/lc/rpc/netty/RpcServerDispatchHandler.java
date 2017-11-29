package main.cn.lc.rpc.netty;



import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import main.cn.lc.rpc.RpcRequest;
import main.cn.lc.rpc.client.RpcRequestWrapper;
import main.cn.lc.rpc.server.RpcServerRequestHandler;

public class RpcServerDispatchHandler extends ChannelInboundHandlerAdapter
{
	private RpcServerRequestHandler rpcServerRequestHandler;
		
	public RpcServerDispatchHandler(
			RpcServerRequestHandler rpcServerRequestHandler) 
	{
		this.rpcServerRequestHandler = rpcServerRequestHandler;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception 
	{		
		RpcRequest rpcRequest = (RpcRequest)msg;
		RpcRequestWrapper rpcRequestWrapper = new RpcRequestWrapper(rpcRequest, ctx.channel());
		
		rpcServerRequestHandler.addRequest(rpcRequestWrapper);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception 
	{
		
	}
}

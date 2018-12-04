package com.lc.misc.rpc.netty;
import com.lc.misc.rpc.RpcResponse;
import com.lc.misc.rpc.client.RpcFuture;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


public class RpcClientResponseHandler 
{
	private ConcurrentMap<Integer, RpcFuture> invokeIdRpcFutureMap = new ConcurrentHashMap<Integer, RpcFuture>();
	
	private ExecutorService threadPool;
	private BlockingQueue<RpcResponse> responseQueue = new LinkedBlockingQueue<RpcResponse>();
	
	public RpcClientResponseHandler(int threads)
	{
		threadPool = Executors.newFixedThreadPool(threads);
		for(int i=0; i<threads; i++)
		{
			threadPool.execute(new RpcClientResponseHandleRunnable(invokeIdRpcFutureMap, responseQueue));
		}
	}
	
	public void register(int id, RpcFuture rpcFuture)
	{
		invokeIdRpcFutureMap.put(id, rpcFuture);
	}
	
	public void addResponse(RpcResponse rpcResponse)
	{
		responseQueue.add(rpcResponse);
	}
}

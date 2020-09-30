package com.lc.misc.rpc.netty;

import com.lc.misc.rpc.RpcResponse;
import com.lc.misc.rpc.client.RpcFuture;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;


public class RpcClientResponseHandleRunnable implements Runnable {
    private ConcurrentMap<Integer, RpcFuture> invokeIdRpcFutureMap;
    private BlockingQueue<RpcResponse> responseQueue;

    public RpcClientResponseHandleRunnable(
            ConcurrentMap<Integer, RpcFuture> invokeIdRpcFutureMap,
            BlockingQueue<RpcResponse> responseQueue) {
        this.invokeIdRpcFutureMap = invokeIdRpcFutureMap;
        this.responseQueue = responseQueue;
    }

    public void run() {
        while (true) {
            try {
                RpcResponse rpcResponse = responseQueue.take();

                int id = rpcResponse.getId();
                RpcFuture rpcFuture = invokeIdRpcFutureMap.remove(id);

                if (rpcResponse.isInvokeSuccess())
                    rpcFuture.setResult(rpcResponse.getResult());
                else
                    rpcFuture.setThrowable(rpcResponse.getThrowable());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

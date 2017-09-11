package main.cn.lc.rpc;

public interface RpcFutureListener 
{
	public void onResult(Object result);
	public void onException(Throwable throwable);
}
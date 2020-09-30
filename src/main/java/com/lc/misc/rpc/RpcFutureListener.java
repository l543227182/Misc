package com.lc.misc.rpc;

public interface RpcFutureListener {
    void onResult(Object result);

    void onException(Throwable throwable);
}
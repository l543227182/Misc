package com.lc.misc.rpc.client;


import com.lc.misc.rpc.RpcInvokeHook;
import com.lc.misc.rpc.TestInterface;

/**
 * Created by luochao.byron on 2017/9/11.
 */
public class test {
    public static RpcInvokeHook rpcInvokeHook = new RpcInvokeHook(){

        @Override
        public void beforeInvoke(String methodName, Object[] args) {
            System.out.println("before");
        }

        @Override
        public void afterInvoke(String methodName, Object[] args) {
            System.out.println("after");
        }
    };
    public static  void  testSynchronous(){
        TestInterface build = RpcClientProxyBuilder.create(TestInterface.class).hook(rpcInvokeHook).timeout(0).
                connect("localhost",111).build();
        String hahaha = build.testMethod01("hahaha");
        System.out.println(hahaha);
    }
    public void testAynschronous(){
        RpcClientAsyncProxy rpcClientAsyncProxy = RpcClientProxyBuilder.create(TestInterface.class).timeout(0)
                .hook(rpcInvokeHook)
                .connect("127.0.0.1", 3721).buildAsyncProxy();
        RpcFuture call = rpcClientAsyncProxy.call("testMethod01", "qwerty");
        }
    public static void main(String args[]){
        testSynchronous();
    }
}

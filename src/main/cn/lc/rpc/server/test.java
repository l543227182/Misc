package main.cn.lc.rpc.server;

import main.cn.lc.rpc.RpcInvokeHook;
import main.cn.lc.rpc.TestInterface;

/**
 * Created by luochao.byron on 2017/9/19.
 */
public class test {

    public static void main(String[] args) {
        TestInterface testInterface = new TestInterface() {
            public String testMethod01(String string) {
                return string.toUpperCase();
            }
        };

        RpcInvokeHook hook = new RpcInvokeHook() {
            public void beforeInvoke(String methodName, Object[] args) {
                System.out.println("beforeInvoke " + methodName);
            }

            public void afterInvoke(String methodName, Object[] args) {
                System.out.println("afterInvoke " + methodName);
            }
        };
        RpcServer rpcServer = RpcServerBuilder.create()
                .serviceInterface(TestInterface.class)
                .serviceProvider(testInterface)
                .threads(4)
                .hook(hook)
                .bind(3721)
                .build();
        rpcServer.start();
    }

}

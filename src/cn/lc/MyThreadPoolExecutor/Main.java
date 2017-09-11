package cn.lc.MyThreadPoolExecutor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by luochao.byron on 2017/7/10.
 */
public class Main {
    public static AtomicInteger j = new AtomicInteger(0);
    public static void main(String args[])  {
        ThreadPoolExecutor poolExecutor =ThreadPoolExecutor.newInstance();
        for(int i = 0;i<8 ;i++){
            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                       System.out.println("hahahaha  "+ j.incrementAndGet() + "");
                }
            });
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        poolExecutor.shutdown();
    }
}

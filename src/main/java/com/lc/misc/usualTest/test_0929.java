package com.lc.misc.usualTest;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author luochao
 * @since 2020-09-29 19:10
 */
public class test_0929 {
    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,
            5,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024),
            new ThreadFactoryBuilder().setNameFormat("loggerPool").build(), new ThreadPoolExecutor.CallerRunsPolicy());
    static ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(2,
            5,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024),
            new ThreadFactoryBuilder().setNameFormat("loggerPool").build(), new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) {
        for (int j = 0; j <= 10; j++) {
            int finalJ = j;
            threadPoolExecutor2.execute(() -> {
                for (int i = 0; i < 10; i++) {
                    threadTestParamsPass(new A(i, finalJ + "a" + i, null), new A(i, null, finalJ + "b" + i));
                }
            });
        }

    }

    public static void threadTestParamsPass(A a, A b) {
        threadPoolExecutor.execute(() -> {
            System.out.println(a.a + "  -  " + b.b + "  -  " + a.num + "  -  " + b.num);
        });

    }

    static class A {
        Integer num;
        private String a;
        private String b;

        public A(Integer num, String a, String b) {
            this.num = num;
            this.a = a;
            this.b = b;
        }
    }
}

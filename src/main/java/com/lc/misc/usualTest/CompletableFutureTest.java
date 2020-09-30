package com.lc.misc.usualTest;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTest {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        // ImageReader ir=new PNGImageader(new Im)

        CompletableFuture<? extends Number> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 * 15);
                long result = new Random().nextInt(100);
                return result;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 0;
        }).whenComplete((a, b) -> {
            System.out.println("over");
        });
        Number number = completableFuture.get();
        System.out.println(number);
    }
}

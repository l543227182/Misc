package com.lc.misc.netty.niotest;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

/**
 * Created by luochao.byron on 2017/9/12.
 */
public class channeltest {

    public static void main(String args[]) throws Exception{
        //获取test0.txt文件的通道句柄
        RandomAccessFile raf0 = new RandomAccessFile("C:\\Users\\luochao.byron\\Desktop\\日常\\swap.txt", "r");
        FileChannel fc0 = raf0.getChannel();
        CountDownLatch countDownLatch =new CountDownLatch(10);
        //获取test1.txt文件的通道句柄
        for(int i=0;i<10;i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        RandomAccessFile raf1 = new RandomAccessFile("C:\\Users\\luochao.byron\\Desktop\\日常\\swap"+ finalI +".txt", "rw");
                        FileChannel fc1 = raf1.getChannel();
                        //将test0.txt传输到test1.txt
                        fc0.transferTo(1024*finalI, fc0.size(), fc1);
                        //强制刷新数据到磁盘
                        fc0.force(true);
                        //关闭通道
                        countDownLatch.countDown();
                        raf1.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }).start();
        }
        countDownLatch.await();
        raf0.close();
    }
}

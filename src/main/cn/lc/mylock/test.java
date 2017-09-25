/*
package main.cn.lc.mylock;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

*/
/**
 * Created by luochao.byron on 2017/7/5.
 *//*

interface  defualtInter{
    public default  void test () {
        System.out.println("Defualt test");
    }
}
public class test {
    //private static Lock lock = new ArrayLock(150);

    private static Lock lock = new CLHLock();

    //private static TimeCost timeCost = new TimeCost(new TTASLock());
    private static Integer[] a={1,2,5,3,1,22,11,22};
    private static List<Integer> list = Arrays.asList(a);
    private static volatile int value = 0;
    public static void method(){
        lock.lock();
        System.out.println("Value: " + ++value);
        lock.unlock();
    }

    public static void  func(Function<Integer,Integer> function ){
        Object b=function.apply(123);
        System.out.println(b);
    }
    public static void main(String[] args) {

    }

    private static void Stream() {
        System.out.println(list.stream().map(a -> {
           System.out.println(a);
           return a + 10;} ).collect(Collectors.toList()));
    }

    private static void threadtest() {
        for(int i = 0; i < 50; i ++){
            Thread t = new Thread(new Runnable(){
                @Override
                public void run() {
                    method();
                }
            });
            t.start();
        }
    }
}
*/

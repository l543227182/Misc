package main.cn.lc.Main.LRU;

/**
 * Created by luochao.byron on 2017/9/25.
 */
public class Main {
    public static void main(String args[]){
        LRUcache<String,String> lrUcache=new LRUcache(6);
        lrUcache.put("12","1");
        lrUcache.put("122","2");
        lrUcache.put("1222","3");
        lrUcache.put("12222","4");
        lrUcache.put("122222","5");
        lrUcache.put("1222222","6");
        String s = lrUcache.get(null);
        lrUcache.put("jj","the lastest");
        while(lrUcache.hasNext()){
            System.out.println(lrUcache.next());
        }
    }
}

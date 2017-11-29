package lc.Main;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Stream;

interface  test{
    public void testMethod();
    public int a = 0;
    public String str= "";
}
/** * Created by humac on 2017/9/7. */
public class Main {
  public static void main(String args[]) throws IOException {
      bitMapTest();
      LinkedHashMap link=new LinkedHashMap();
  }

    public static void bitMapTest(){
        int[] a={1,2,3,4,5,6,78,9,5,66,22,3,112,1233,555};
        BitSet bs=new BitSet();
        Arrays.stream(a).forEach(item->{
            bs.set(item,true);
        });
        System.out.print(bs.size());
    }
    private static void function2(){
        String[] str={"1","12","a231","A231","222"};
        Consumer<String> tConsumer = s -> {
            s.isEmpty();
            System.out.print(s);
        };
        tConsumer.accept("123321");
        Stream<String> strings = Arrays.stream(str);
        int a = Arrays.stream(str)
                .filter(s -> s.startsWith("A"))
                .mapToInt(String::length)
                .max().getAsInt();
        System.out.print(a);
    }
  private static void function(String s) {
    Queue queue = new LinkedList();
    int count = 1;
    char[] chars = s.toCharArray();
    queue.add(chars[0]);
    for (int i = 1; i < chars.length; i++) {
      if (chars[i] == '(') {
        queue.add(chars[i]);
      } else {
        count = count * queue.size();
        queue.poll();
      }
    }
    // (())(())()()()()
    System.out.println(count);
  }
  public static String simplifyPath(String path) {
      String[] split = path.split("/");
      final String[] result = {""};
      ArrayList<String> subPath = new ArrayList<>( );

      Arrays.stream(split).forEach(item ->{
          if(item.equals("") || item .equals(".")){
              return ;
          } else if(item.equals("..")) {
                if(subPath.size()>0){
                    subPath.remove(subPath.size()-1);
                }
          }else{
              subPath.add(item);
          }
      });
      //System.out.println("/");
      subPath.stream().forEach(item->{
          result[0] +=("/"+item);
      });
      return result[0].equals("")?"/":result[0];
  }
}
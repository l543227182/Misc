package main.cn.lc.Main;

import java.io.IOException;
import java.util.*;
/** * Created by humac on 2017/9/7. */
public class Main {
  public static void main(String args[]) throws IOException {
    Scanner sc = new Scanner(System.in);
    while (sc.hasNext()) {
      String s = sc.next();
    }
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
}
package main.cn.lc.Main;

import java.util.Stack;

/**
 * Created by luochao.byron on 2017/10/16.
 */
public class ThoughtWorks {
    public static void main(String args[]){
        int [] a = {1,5,9,4};
        int result[] =new int[a.length ];
        int len = a.length;
        Stack<Integer> stack =new Stack<>();
        for(int i=0;i<len;i++){
            while ((!stack.isEmpty()) && a[stack.peek()] < a[i]) {
                result[stack.pop()] = a[i];
            }
            stack.push(i);
        }
        while(!stack.isEmpty()) {
            result[stack.pop()] = -1;
        }
        for(int i=0;i<result.length;i++){
            System.out.print(result[i] + " ");
        }
     }
}

package com.lc.misc.main.minStack;

import java.util.LinkedList;

/**
 *
 */
public class minStack {
    private LinkedList<Integer> stack;
    private int size;
    private int min = 10;

    public minStack() {
        stack = new LinkedList<Integer>();
    }

    /**
     * @param obj
     */
    public void push(Integer obj) {
        if (stack.isEmpty()) {
            stack.push(0);
            min = obj;
        } else {
            stack.push(obj - min);
            if (obj < min) {
                min = obj;
            }
        }
    }

    public Integer pop() {
        Integer value = this.stack.pop();
        if (value < 0) {
            int oldMin = min;
            min = min - value;
            return oldMin;
        }
        return value + min;
    }

    public Integer peek() {
        Integer peek = this.stack.peek();
        return peek < 0 ? min : peek + min;
    }

    public Integer getMin() {
        return this.stack.isEmpty() ? null : min;
    }

    public static void main(String args[]) {
        minStack ms = new minStack();
        ms.push(-1);
        ms.push(2);
        ms.push(11);
        ms.push(-5);
        ms.push(7);
        System.out.println(ms.getMin());
        ms.pop();
        ms.pop();
        System.out.println(ms.getMin());
        System.out.println(ms.peek());
        System.out.println(ms.getMin());
    }
}
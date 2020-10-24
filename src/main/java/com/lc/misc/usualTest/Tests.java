package com.lc.misc.usualTest;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author luochao
 * @date 2020-06-02 11:43
 */
public class Tests {
    public static void main(String[] args) {

        int m = 8;
        int n = 4;
        List<Double> mList = new ArrayList<Double>();
        List<Double> nList = new ArrayList<Double>();

        Random r = new Random();
        for(int i=0;i<m;i++) {
            double randomM = r.nextDouble() * 35 + 1;
            mList.add(randomM);
        }
        for(int i=0;i<n;i++){
            double randomN = r.nextDouble() * 12 + 1;
            nList.add(randomN);
        }

        System.out.print(Joiner.on(" ").join(mList.stream().sorted().map(item->item<10? "0"+item:item+"").collect(Collectors.toList())).toString());
        System.out.print(":");
        System.out.println(Joiner.on(" ").join(nList.stream().sorted().map(item->item+"").collect(Collectors.toList())).toString());

        int cM = 1;
        int cN = 1;
        for(int i = m;i>=4;i--){
            cM = cM * i;
        }
        for(int i = 5;i>=1;i--){
            cM = cM / i;
        }
        System.out.println(cM);

        for(int i = n;i>=n-1;i--){
            cN = cN * i;
        }
        cN = cN / 2;

        System.out.println(cN);

        System.out.println(cM * cN);



    }
}

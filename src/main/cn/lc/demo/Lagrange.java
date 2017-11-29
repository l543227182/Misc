package lc.demo;

import java.util.Scanner;


public class Lagrange {
    private static double[] Lag(double x[],double y[],double x0[]){
        int m=x.length;
        int n=x0.length;
        double y0[]=new double[n];
        for(int ia=0;ia<n;ia++) {
            double j=0;
            for(int ib=0;ib<m;ib++) {
                double k=1;
                for(int ic=0;ic<m;ic++) {
                    if(ib!=ic){
                        k=k*(x0[ia]-x[ic])/(x[ib]-x[ic]);
                    }
                }
                k=k*y[ib];
                j=j+k;
            }
            y0[ia]= j / (Math.pow(ia,ia));
        }
        return y0;
    }
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        int m= 7;
        int n= 7;
        double x[]=new double[m];
        double y[]=new double[m];
        double x0[]=new double[n];
        System.out.println("依次输入给定的插值点:");
        for(int i=0;i<m;i++){
            x[i]=input.nextDouble();
        }
        input.nextLine();
        System.out.println("依次输入给定的插值点对应的函数值:");
        String[] split = input.nextLine().split(" ");
        for(int i=0;i<m;i++){
            y[i]= (double) split[i].charAt(0);
        }
        for(int i=0;i<n;i++){
            x0[i]= x[i];
        }
        double y0[]=Lag(x, y, x0);
        System.out.println("运用拉格朗日插值法求解得:");
        for(int i=0;i<n;i++){
            System.out.println(y0[i] +" ");
        }
        System.out.println();
        input.close();
    }
}


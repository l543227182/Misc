package com.lc.misc.demo;


/**
 * Created by luochao.byron on 2017/7/4.
 */
public class hello {
    public enum EventType {
        LIKE(0),
        COMMENT(1),
        LOGIN(2),
        MAIL(3),
        FOLLOW(4),
        UNFOLLOW(5),
        ADD_QUESTION(6);

        private int value;

        EventType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    static {
        int a = 0;
    }

    public static int j = 0;

    public static void main(String args[]) {
     /*ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0;i<2000 ;i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("hahahaha  "+ j++ + " ####");
                }
            });
        }*/
        for (int x = 1; x <= 11; ++x)
            System.out.print((char) (float) (0.006070781973020654 * x * x * x * x * x * x * x * x * x * x - 0.3637965317362993 * x * x * x * x * x * x * x * x * x + 9.477544982980568 * x * x * x * x * x * x * x * x - 140.81987408191296 * x * x * x * x * x * x * x + 1315.7226182880026 * x * x * x * x * x * x - 8035.66551336146 * x * x * x * x * x + 32288.66389559375 * x * x * x * x - 83652.25144306717 * x * x * x + 132446.92244138764 * x * x - 114213.82003897215 * x + 40054.12809497995));
        double[] x = {1, 2, 3, 4, 5, 6, 7};
        double[] y = {'l', 'u', 'o', 'c', 'h', 'a', 'o'};
        double[] lag = Lag(y, x, y);
        System.out.print(lag[0]);
    }

    private static double[] Lag(double x[], double y[], double x0[]) {
        int m = x.length;
        int n = x0.length;
        double y0[] = new double[n];
        for (int ia = 0; ia < n; ia++) {
            double j = 0;
            for (int ib = 0; ib < m; ib++) {
                double k = 1;
                for (int ic = 0; ic < m; ic++) {
                    if (ib != ic) {
                        k = k * (x0[ia] - x[ic]) / (x[ib] - x[ic]);
                    }
                }
                k = k * y[ib];
                j = j + k;
            }
            y0[ia] = j;
        }
        return y0;
    }
}

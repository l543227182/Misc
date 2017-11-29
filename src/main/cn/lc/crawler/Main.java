package lc.crawler;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by luochao.byron on 2017/11/16.
 */
public class Main {
    public static void main(String args[]) {
       // String url = "http://vacations.ctrip.com/grouptravel/p8455112s451.html?kwd=%E6%B2%B3%E5%8D%97";
       // String url = "http://vacations.ctrip.com/grouptravel/p8455112s0-comment-2.html";
        String url = "http://vacations.ctrip.com/tours/d-austria-100027";
        try {
            Response response = Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0)"
                    + " Gecko/20100101 Firefox/33.0")
                    .timeout(10000).execute();
            String body = response.body();
            System.out.println(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

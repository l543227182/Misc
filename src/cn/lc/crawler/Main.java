package cn.lc.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by luochao.byron on 2017/7/10.
 */
public class Main {
    private static Lock lock = new ReentrantLock();
    private static List<String> contentList = new ArrayList();
    public static void main(String args[]) {
        String url = "https://www.zhihu.com/explore#daily-hot";
        String content = "";
        BufferedReader in = null;
        try{
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while( (line = in.readLine()) != null){
                content += line +"\n";
            }
            Document doc = Jsoup.parse(content);
            Elements select = doc.select(".explore-feed");
            System.out.println(select.size());
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                if (in != null){
                    in.close();
                }
            }catch(Exception e2){
                e2.printStackTrace();
            }
        }
    }
}

package com.lc.web.crawler;

import com.lc.web.model.CrawlerBean;
import com.lc.web.model.ItemComment;
import com.lc.web.service.DataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.PriorityScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MainCrawler implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
    public static ConcurrentHashMap<String, CrawlerBean> pageMap = new ConcurrentHashMap<String, CrawlerBean>();

    @Autowired
    private DataService dataService;

    public void setComments(String pageURL, Html html) {
        String pageKey = pageURL.substring(pageURL.lastIndexOf("/") + 1, pageURL.lastIndexOf(".")).split("-")[0];
        CrawlerBean crawlerBean = pageMap.get(pageKey);
        //爬取评论
        Selectable allComments = html.xpath("//ul[@class='detail_comment_list']/li");
        //allComments
        int size = allComments.all().size();
        for (int i = 1; i <= size; i++) {
            ItemComment ic = new ItemComment();
            Selectable xpath = html.xpath("//ul[@class='detail_comment_list']/li[" + i + "]");
            //每条评论的整体评论
            String overallComment = xpath.xpath("//p[2]/text()").get();
            ic.setOverallComment(overallComment);
            StringBuffer sb = new StringBuffer();

            //每条评论对每个服务的简单评论
            for (int j = 1; j <= 4; j++) {
                String itemLi = xpath.xpath("//p[@class='detail_comment_info']/span[" + j + "]/text()").get();
                sb.append(itemLi + "#");
            }
            String serviceComment = sb.toString();
            ic.setServiceComment(serviceComment);


            String simplizeComment = xpath.xpath("//div[@class='detail_comment_l']/a/span/text()").get();
            ic.setSimplizeComment(simplizeComment);

            //每个服务的简评
            StringBuffer overallServiceComments = new StringBuffer();
            int detailServiceCount = xpath.xpath("//ul[@class='comment_label_list']/li").all().size();
            for (int j = 1; j <= detailServiceCount; j++) {
                String itemLi = xpath.xpath("//ul[@class='comment_label_list']/li[" + j + "]/text()").get();
                overallServiceComments.append(itemLi + "#");
            }
            String s = overallServiceComments.toString();
            ic.setItemServiceDetailComment("".equals(s.trim()) ? null : s.split("#"));

            //每个评论的评分
            String scroe = xpath.xpath("//div[@class='detail_comment_l']/strong/text()").get().charAt(0) + "";
            ic.setScore(Double.parseDouble(scroe));
                   /* //每条评论的旅游类型
                    String useType = xpath.xpath("//span['user_type']/text()").get();
                    ic.setUserType(useType);*/
            crawlerBean.getComments().add(ic);
        }
        return;
    }

    public void dealTargetPage(String pageURL, Html html, Page page) {

        String s = html.xpath("//ul[@id='scoreList']/li[1]/a/span/text()").get();
        //用户评论总数
        String userCommentCount = s.trim().replaceAll("[()]", "");
        int commentsCounts = Integer.parseInt(userCommentCount);
        int pages = commentsCounts % 10 == 0 ? commentsCounts / 10 : commentsCounts / 10 + 1;

        String commentsUrl = html.xpath("//a[@class='all_info_link']/@href").get();

        //生成mapKey
        String pageKey = commentsUrl.substring(commentsUrl.lastIndexOf("/") + 1, commentsUrl.lastIndexOf(".")).split("-")[0];
        CrawlerBean cb = new CrawlerBean();
        pageMap.put(pageKey, cb);

        // 生成评论链接
        for (int i = 1; i <= pages; i++) {
            String commentsItemURL = "http://" +
                    pageURL.split("\\/")[2] +
                    commentsUrl.split("\\.")[0] +
                    "-" + i + ".html";
            Request request = new Request(commentsItemURL);
            request.setPriority(15);
            page.addTargetRequest(request);
        }

        //获取评分
        String score = html.xpath("//dl[@class='dp_comment_total']/dt/em/text()").get();
        Selectable scoreDiv = html.xpath("//dl[@class='dp_comment_total']/dd/ul");
        StringBuffer eachScore = new StringBuffer();
        for (int i = 1; i <= 5; i++) {
            String itemScore = scoreDiv.xpath("//li[" + i + "]/span[@class='num']/text()").get().replaceAll("[()]", "");
            eachScore.append(itemScore + "#");
        }
        // 获取title
        String title = html.xpath("//div[@class='product_scroll_wrap new_scroll_wrap']/h1/text()").get();
        // System.out.println(title);
        cb.setAvargeScore(Double.parseDouble(score));
        cb.setResourceUrl(pageURL);
        cb.setCommentsCount(commentsCounts);
        cb.setTitle(title);
        cb.setEachScoreCount(eachScore.toString());
    }

    @Override
    public void process(Page page) {
        try {
            Html html = page.getHtml();
            String pageURL = page.getUrl().get();
            // System.out.println(pageURL);
            if (pageURL.contains("comment")) {
                setComments(pageURL, html);
            } else if ((pageURL.contains("morelinetravel") ||
                    pageURL.contains("grouptravel") ||
                    pageURL.contains("freetravel")) && !pageURL.contains("ask") && !pageURL.contains("tours")) {
                try {
                    dealTargetPage(pageURL, html, page);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Selectable links = page.getHtml().links();
                List<String> all = links.regex("http://vacations.ctrip.com/tours/.*").all();
                page.addTargetRequests(all);
                List<String> groupitemCityUrl = links.regex("http://vacations.ctrip.com/grouptravel/.*").all();
                List<String> morelineCityUrl = links.regex("http://vacations.ctrip.com/morelinetravel/.*").all();
                List<String> freeitemCityUrl = links.regex("http://vacations.ctrip.com/freetravel/.*").all();
                page.addTargetRequests(groupitemCityUrl);
                page.addTargetRequests(morelineCityUrl);
                page.addTargetRequests(freeitemCityUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public Thread ClawlerThread;

    public Spider startSpider() {
        Spider spider = Spider.create(new MainCrawler()).addUrl("http://pages.ctrip.com/public/sitemap/dj.html").thread(5);
        ClawlerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
                // ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor();
                httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
                        new Proxy("116.231.35.5", 8118)
                        , new Proxy("60.168.206.139", 808)));
                spider.setScheduler(new PriorityScheduler()
                        .setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)));
                serializeMap(spider);
                //spider.setDownloader(httpClientDownloader);
                spider.run();
            }
        });
        this.ClawlerThread.start();
        return spider;
    }

    public Timer serializeTimer;

    public void serializeMap(Spider spider) {
        this.serializeTimer = new Timer();
        this.serializeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (pageMap.size() > 10 || spider.getStatus() == Spider.Status.Stopped) {
                    try {
                        Set<Map.Entry<String, CrawlerBean>> entries = pageMap.entrySet();
                        BufferedWriter bw = new BufferedWriter(
                                new OutputStreamWriter
                                        (new FileOutputStream("C:\\Users\\luochao.byron\\Desktop\\日常\\comments.txt", true), "utf-8"));
                        for (Map.Entry<String, CrawlerBean> item : entries) {
                            CrawlerBean value = item.getValue();
                            if (value.getComments().size() < value.getCommentsCount()) {
                                continue;
                            }
                            dataService.addData(value);
                            StringBuffer sb = new StringBuffer();
                            sb.append(value.getAvargeScore() + "\n").append(value.getCity() + "\n").append(value.getCommentsCount() + "\n").
                                    append(value.getEachScoreCount() + "\n").append(value.getResourceUrl() + "\n").append(value.getTitle() + "\n");
                            sb.append("\n\n评论们：\n");
                            for (ItemComment ic : value.getComments()) {
                                ic.setPid(String.valueOf(value.getId()));
                                sb.append(StringUtils.join(ic.getItemServiceDetailComment()) + "\n").append(ic.getOverallComment() + "\n").append(ic.getScore() + "\n").append(ic.getServiceComment() + "\n")
                                        .append(ic.getSimplizeComment() + "\n").append(ic.getUserType() + "\n" + "\n");
                            }
                            String s = sb.toString();
                            bw.write(s, 0, s.length());
                            bw.flush();
                            pageMap.remove(item.getKey());
                        }
                        bw.close();
                        if (spider.getStatus() == Spider.Status.Stopped) {
                            System.out.println(spider.isExitWhenComplete());
                            System.exit(-1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }, 1000 * 5, 1000 * 10);
    }
}
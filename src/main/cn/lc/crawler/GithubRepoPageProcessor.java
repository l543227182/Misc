package lc.crawler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class GithubRepoPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
    private ConcurrentMap<String,Boolean> isOnly = new ConcurrentHashMap<String,Boolean>();
    @Override
    public void process(Page page) {
       /* page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-])").all());
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));*/
        //page.addTargetRequest();
        List<String> all = page.getHtml().links().regex("http://vacations.ctrip.com/tours/.*").all();
        page.addTargetRequests(removeRepeatable(all));
        System.out.println(page.getHtml().links().regex("http://vacations.ctrip.com/tours/.*").all());

    }

    public List<String> removeRepeatable(List<String> list) {
        List<String> thenews = list.stream().filter((item) -> {
            if(isOnly.containsKey(item)){
                return false;
            }else{
                isOnly.put(item,true);
                return true;
            }
        }).collect(Collectors.toList());
        return thenews;
    }
    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new GithubRepoPageProcessor()).addUrl("http://pages.ctrip.com/public/sitemap/dj.html").thread(5).run();

    }
}
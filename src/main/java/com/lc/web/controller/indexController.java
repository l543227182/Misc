package com.lc.web.controller;

import com.lc.web.Mapper.DataMapper;
import com.lc.web.Model.crawlerBean;
import com.lc.web.crawler.mainCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luochao.byron on 2017/11/9.
 */
@Controller
public class indexController {

    @Autowired
    private mainCrawler crawler;

    @Autowired
    private DataMapper dataMapper;

    @RequestMapping("/api/getCrawlerData")
    @ResponseBody
    public Map getCrawlerData(String start, String pageSize) {
        HashMap<String,Object> map=new HashMap<>();
        List<crawlerBean> crawlerBeans = dataMapper.selectDataForPage(Integer.valueOf(start), Integer.valueOf(pageSize));
        int total = dataMapper.countData();
        map.put("data",crawlerBeans);
        map.put("total",total);
        return map;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleError(HttpServletRequest req, Exception exception) {
    return "hello world";
    }
}

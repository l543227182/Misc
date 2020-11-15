package com.lc.web.controller;

import com.lc.web.crawler.MainCrawler;
import com.lc.web.mapper.DataMapper;
import com.lc.web.model.CrawlerBean;
import com.lc.web.model.SyncProjResp;
import com.lc.web.model.SyncTaskResp;
import com.lc.web.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by luochao.byron on 2017/11/9.
 */
@Controller
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:7788", "http://127.0.0.1:7788"})
public class indexController {

    @Autowired
    private MainCrawler crawler;

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private MockService mockService;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping("/getCrawlerData")
    @ResponseBody
    public Map getCrawlerData(@RequestParam(defaultValue = "1") String start, @RequestParam(defaultValue = "10") String pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        List<CrawlerBean> crawlerBeans = dataMapper.selectDataForPage(Integer.valueOf(start), Integer.valueOf(pageSize));
        int total = dataMapper.countData();
        map.put("data", crawlerBeans);
        map.put("total", total);
        return map;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(MultipartFile file) {
        System.out.println(file);
        try {
            String name = UUID.randomUUID().toString();
            //IOUtils.copy(file.getInputStream(),new FileOutputStream(new File("F:\\uploadFile", name + ".chunk")));
            return name;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @GetMapping("/getTask")
    @ResponseBody
    public SyncTaskResp getTask() {

        return mockService.getTask();
    }

    @GetMapping("/getProj")
    @ResponseBody
    public SyncProjResp getProj() {
        return mockService.getProj();
    }


}

package com.lc.web.service;

import com.lc.web.mapper.DataMapper;
import com.lc.web.mapper.SubDataMapper;
import com.lc.web.model.CrawlerBean;
import com.lc.web.model.ItemComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DataService {


    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private SubDataMapper subDataMapper;

    public int addData(CrawlerBean crawlerBean) {
        try {
            int id = dataMapper.insertData(crawlerBean);

            for (ItemComment ic : crawlerBean.getComments()) {
                ic.setPid(String.valueOf(crawlerBean.getId()));
                try {
                    subDataMapper.insertData(ic);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return crawlerBean.getId();
    }
}

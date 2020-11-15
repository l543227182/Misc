package com.lc.web.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luochao.byron on 2017/11/19.
 */
public class CrawlerBean {
    private int id;
    private String resourceUrl;
    private String title;
    private String province;
    private String country;
    private String city;
    private String content;
    private List<ItemComment> comments;
    private int commentsCount;
    private double avargeScore;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEachScoreCount() {
        return eachScoreCount;
    }

    public void setEachScoreCount(String eachScoreCount) {
        this.eachScoreCount = eachScoreCount;
    }

    private String eachScoreCount;


    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public double getAvargeScore() {
        return avargeScore;
    }

    public void setAvargeScore(double avargeScore) {
        this.avargeScore = avargeScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ItemComment> getComments() {
        return comments;
    }

    public CrawlerBean() {
        this.comments = new ArrayList<ItemComment>();
    }

    public void setComments(List<ItemComment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "crawlerBean{" +
                "id=" + id +
                ", resourceUrl='" + resourceUrl + '\'' +
                ", title='" + title + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", content='" + content + '\'' +
                ", comments=" + comments +
                ", commentsCount=" + commentsCount +
                ", avargeScore=" + avargeScore +
                ", remark='" + remark + '\'' +
                ", eachScoreCount='" + eachScoreCount + '\'' +
                '}';
    }
}


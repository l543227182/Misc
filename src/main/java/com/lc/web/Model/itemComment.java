package com.lc.web.Model;

import java.util.Arrays;

public class itemComment{
    private String userType;
    private double score;
    private String simplizeComment;
    private String serviceComment;
    private String  overallComment;
    private String [] itemServiceDetailComment = new String [4];
    private String pid;

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPid() {

        return pid;
    }

    public String getServiceComment() {
        return serviceComment;
    }

    public void setServiceComment(String serviceComment) {
        this.serviceComment = serviceComment;
    }

    public String[] getItemServiceDetailComment() {
        return itemServiceDetailComment;
    }

    public void setItemServiceDetailComment(String[] itemServiceDetailComment) {
        this.itemServiceDetailComment = itemServiceDetailComment;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getSimplizeComment() {
        return simplizeComment;
    }

    public void setSimplizeComment(String simplizeComment) {
        this.simplizeComment = simplizeComment;
    }

    public String getOverallComment() {
        return overallComment;
    }

    public void setOverallComment(String overallComment) {
        this.overallComment = overallComment;
    }

    @Override
    public String toString() {
        return "itemComment{" +
                "userType='" + userType + '\'' +
                ", score=" + score +
                ", simplizeComment='" + simplizeComment + '\'' +
                ", serviceComment='" + serviceComment + '\'' +
                ", overallComment='" + overallComment + '\'' +
                ", itemServiceDetailComment=" + Arrays.toString(itemServiceDetailComment) +
                ", pid='" + pid + '\'' +
                '}';
    }
}
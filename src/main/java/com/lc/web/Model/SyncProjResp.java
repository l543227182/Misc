package com.lc.web.Model;

import com.google.common.collect.Lists;

import java.util.List;

public class SyncProjResp {
    private String return_message;
    private Integer return_code;
    private ResponseData data;

    public SyncProjResp() {
        this.data = new ResponseData();
        this.return_code = 0;
        this.return_message = "success";
    }

    public String getReturn_message() {
        return return_message;
    }

    public void setReturn_message(String return_message) {
        this.return_message = return_message;
    }

    public Integer getReturn_code() {
        return return_code;
    }

    public void setReturn_code(Integer return_code) {
        this.return_code = return_code;
    }

    public ResponseData getData() {
        return data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }

    public static class ResponseData {
        private List<ProjModel> projectInfo;

        public ResponseData() {
            this.projectInfo = Lists.newArrayList();
        }

        public List<ProjModel> getProjectInfo() {
            return projectInfo;
        }

        public void setProjectInfo(List<ProjModel> projectInfo) {
            this.projectInfo = projectInfo;
        }
    }

    public static class ProjModel {
        private Integer org_id;
        private String org_name;
        private Integer project_id;
        private String project_name;

        public Integer getOrg_id() {
            return org_id;
        }

        public void setOrg_id(Integer org_id) {
            this.org_id = org_id;
        }

        public String getOrg_name() {
            return org_name;
        }

        public void setOrg_name(String org_name) {
            this.org_name = org_name;
        }

        public Integer getProject_id() {
            return project_id;
        }

        public void setProject_id(Integer project_id) {
            this.project_id = project_id;
        }

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }
    }
}

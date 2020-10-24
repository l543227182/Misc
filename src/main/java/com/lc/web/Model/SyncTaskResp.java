package com.lc.web.Model;


import com.google.common.collect.Lists;

import java.util.List;

public class SyncTaskResp {

    private ResponseData data;
    private Integer return_code;
    private String return_message;

    public SyncTaskResp(ResponseData data) {
        this.data = data;
    }

    public SyncTaskResp() {
        this.data = new ResponseData();
        this.return_code = 0;
        this.return_message = "success";
    }

    public ResponseData getData() {
        return data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }

    public Integer getReturn_code() {
        return return_code;
    }

    public void setReturn_code(Integer return_code) {
        this.return_code = return_code;
    }

    public String getReturn_message() {
        return return_message;
    }

    public void setReturn_message(String return_message) {
        this.return_message = return_message;
    }

    public static class ResponseData {
        private List<taskModel> outSourcingTask;

        public ResponseData() {
            this.outSourcingTask = Lists.newArrayList();
        }

        public List<taskModel> getOutSourcingTask() {
            return outSourcingTask;
        }

        public void setOutSourcingTask(List<taskModel> outSourcingTask) {
            this.outSourcingTask = outSourcingTask;
        }
    }

    public static class taskModel {
        private Integer task_id;
        private String task_name;
        private Integer project_id;
        private String task_creator;

        public Integer getTask_id() {
            return task_id;
        }

        public void setTask_id(Integer task_id) {
            this.task_id = task_id;
        }

        public String getTask_name() {
            return task_name;
        }

        public void setTask_name(String task_name) {
            this.task_name = task_name;
        }

        public Integer getProject_id() {
            return project_id;
        }

        public void setProject_id(Integer project_id) {
            this.project_id = project_id;
        }

        public String getTask_creator() {
            return task_creator;
        }

        public void setTask_creator(String task_creator) {
            this.task_creator = task_creator;
        }
    }
}

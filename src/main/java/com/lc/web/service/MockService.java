package com.lc.web.service;

import com.lc.web.Model.SyncProjResp;
import com.lc.web.Model.SyncTaskResp;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Scope("prototype")
public class MockService {
    private SyncProjResp projResp;
    private SyncTaskResp taskResp;
    private AtomicInteger count = new AtomicInteger(0);
    private Integer size = 10;
    public MockService(){
        this.init(this.size);
    }

    public void init(Integer size) {
        this.projResp = new SyncProjResp();
        SyncProjResp.ResponseData projRespData = this.projResp.getData();

        this.taskResp = new SyncTaskResp();
        SyncTaskResp.ResponseData taskRespData = this.taskResp.getData();

        int taskId = 1;
        for(int i=1;i<=size;i++) {
            SyncProjResp.ProjModel itemProj = new SyncProjResp.ProjModel();
            itemProj.setOrg_id(i);
            itemProj.setOrg_name("工作室-" + i);
            itemProj.setProject_id(i);
            itemProj.setProject_name("项目-" + i);

            projRespData.getProjectInfo().add(itemProj);
            for(int j=1;j<=size;j++) {
                SyncTaskResp.taskModel itemTask = new SyncTaskResp.taskModel();
                itemTask.setProject_id(i);
                itemTask.setTask_creator("luochao.byron");
                itemTask.setTask_id(taskId);
                itemTask.setTask_name("任务-" + taskId++);
                taskRespData.getOutSourcingTask().add(itemTask);
            }
        }
     }
    public SyncProjResp getProj() {
        SyncProjResp result = this.projResp;
        /*if (count % 2 == 0) {
            result = new SyncProjResp();
            List<SyncProjResp.ProjModel> projectInfo = this.projResp.getData().getProjectInfo();
            result.getData().setProjectInfo(projectInfo.subList(0, projectInfo.size() / 2));
        }*/
        return result ;
    }
    public SyncTaskResp getTask() {
        SyncTaskResp result = this.taskResp;
      /*  if (count % 2 == 0) {
            result = new SyncTaskResp();
            List<SyncTaskResp.taskModel> outSourcingTask = this.taskResp.getData().getOutSourcingTask();
            result.getData().setOutSourcingTask(outSourcingTask.subList(0, outSourcingTask.size() / 2));
        }*/
        return result ;
    }
}

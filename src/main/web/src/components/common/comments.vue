<template>
  <div>
    <el-dialog title="评论" v-model="commentsModel" :close-on-click-modal="false">
      <div style="height: 420px;overflow-y:scroll; ">
         <el-card v-for="(value,key) in commentsData" :key="key">
           <div class="line"><span>用户类型：</span>{{value.userType}}</div>
           <div class="line"><span>评论得分：</span>{{value.innerIP}}</div>
           <div class="line"><span>简评：</span>{{value.simplizeComment}}</div>
           <div class="line"><span>服务评价：</span>{{value.serviceComment}}</div>
           <div class="line"><span>整体评价：</span>{{value.overallComment}}</div>
           <div class="line"><span>具体评价：</span>{{value.itemServiceDetailComment}}</div>
           <div style="margin-left: 10px;margin-top: 10px">{{value.output}}</div>
         </el-card>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="commentsModel = false">关闭</el-button>
      </div>
    </el-dialog>
    <div>
      <el-button @click="showCommons"  type="primary"> 查看评论</el-button>
    </div>
  </div>
</template>
<script>
  import Vue from 'vue'
  import {Component} from 'vue-property-decorator'
  @Component({
    props: ['data']
  })
  export default class ScriptSelect extends Vue {
    commentsModel = false
    get commentsData () {
      return this.data
    }
    showCommons () {
      this.commentsModel = true
    }
    mounted () {
    }
  }
</script>
<style rel="stylesheet/less" lang="less">
</style>

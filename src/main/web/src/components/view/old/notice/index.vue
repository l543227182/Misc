<template>
  <div>
    <div class="breadcrumb">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item>通知维护</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <hr>
    <div class="content">
      <el-row>
      <el-col :span="20" :offset="2">
        <el-form ref="noticeForm" label-width="200px" :model="noticedata" :rules="rules">
          <el-form-item label="微信组号" prop="wechat_party_id">
            <el-input  size="small"  v-model="noticedata.wechat_party_id" style="width: 50%"></el-input>
          </el-form-item>
          <el-form-item label="电话通知人" prop="phones">
            <EditTag :tags="tags" style=""></EditTag>
          </el-form-item>
          <el-form-item label="电话通知内容" prop="content">
            <el-input  size="small" style="width: 50%" v-model="noticedata.content"></el-input>
          </el-form-item>
          <el-form-item>
            <div class="form-footer">
              <el-button type="primary" @click="onSubmitClick">确认</el-button>
              <el-button @click="fetchData()">取消</el-button>
            </div>
          </el-form-item>
        </el-form>
      </el-col>
      </el-row>
    </div>
  </div>
</template>
<script type="text/jsx">
  import Vue from 'vue'
  import {Component, Watch} from 'vue-property-decorator'
  @Component()
  export default class setArea extends Vue {
    noticedata = {
      wechat_party_id: '',
      phones: [],
      content: '开区失败'
    }
    tags = []
    mounted () {
      this.fetchData()
    }
    @Watch('tags')
    setPhones () {
      this.noticedata.phones = this.tags
    }
    fetchData () {
      const url = '/notifies'
      this.$loading({text: '加载中'})
      this.$http.get(url).then(res => {
        if(res.data.content){
          this.noticedata = res.data
          this.tags = this.noticedata.phones
        }
      }).catch(error => {
        this.$message.error(`加载失败，原因：${error}`)
      }).then(()=>{
        this.$loading().close()
      })
    }

    get rules () {
      return {
        content: [{required: true, message: '请输入通知内容', trigger: 'blur'}, {
          validator: (rule, value, callback) => {
           // if(/[\u4e00-\u9fa5]/.test(value)){
              if (value.length > 20) {
                callback(new Error('只能输入20个字符'))
              } else {
                callback()
              }
          },
          trigger: 'change'
        }],
        phones: [{
          required: true,
          validator: (rule, value, callback) => {
              var flag = false
              _.forEach(this.tags, item => {
                if (!/^[0]{0,1}1[34578]\d{9}$/.test(item)) {
                  flag = true
                }
              })
              if (flag) {
                callback(new Error('电话号码格式不正确'))
              } else {
                callback()
              }
          },
          trigger: 'blur'
        }]
      }
    }
    confirm (f, s, onConfirm, onCancel) {
      /*
      this.$confirm(text, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
      */
      this.$msgbox({
        title: '提示',
        message: <div>
          <p>{f.split('$$')[0]}</p>
          <p>{f.split('$$')[1]}</p>
          <br/>
          <p>{s.split('$$')[0]}</p>
          <p>{s.split('$$')[1]}</p>
        </div>,
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (typeof onConfirm === 'function') {
          onConfirm()
        }
      }).catch(() => {
        if (typeof onCancel === 'function') {
          onCancel()
        }
      })
    }

    onSubmitClick () {
      this.$refs.noticeForm.validate(valid => {
        if (valid) {
          const url = '/notifies'
          const params = {
            wechat_party_id: this.noticedata.wechat_party_id,
            phones: this.tags,
            content: this.noticedata.content
          }
          this.$http.post(url, params).then(res => {
            this.$message.success('修改成功')
            this.fetchData()
          }).catch(error => {
            this.$message.error('修改失败，原因:' + error)
          })
        }
      })
    }
    sendRequest () {
    }
  }
</script>
<style rel="stylesheet/less" lang="less">
</style>

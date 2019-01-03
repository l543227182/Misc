<template>
  <div>
    <div class="breadcrumb">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item>开区参数</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <hr>
    <div class="content">
      <el-row>
        <el-col :span="20" :offset="2">
          <el-form ref="areaParams" label-width="200px" :model="formData" :rules="rules">
            <el-form-item label="区前缀" prop="prefix" >
              <el-input  size="small" style="width: 50%" v-model="formData.prefix"></el-input>
            </el-form-item>
            <el-form-item label="区后缀" prop="suffix" >
              <el-input  size="small" style="width: 50%" v-model="formData.suffix"></el-input>
            </el-form-item>
            <el-form-item label="下一个新开区显示索引 " prop="idx" >
              <el-input type="number" size="small" style="width: 50%" v-model.number="formData.idx"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submit">确认</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
  import Vue from 'vue'
  import { Component } from 'vue-property-decorator'
  @Component()
  export default class setAutoArea extends Vue {
    loading = false
    formData = {
      prefix: '',
      suffix: '',
      idx: 0
    }
    mounted () {
      this.fetchData()
    }
    fetchData () {
      const url = '/groups/settings'
      this.$loading({text: '加载中'})
      this.$http.get(url).then(res => {
        if (res.data !== null) {
          this.formData = res.data
        }
      }).catch(error => {
        this.$message.error(`加载数据失败，原因：${error}`)
      }).then(() => {
        this.$loading().close()
      })
    }
    get rules () {
      return {
        idx: [{required: true, message: '请填写下一个新开区显示索引', type: 'number', trigger: 'blur'}]
      }
    }
    submit () {
      this.$confirm('确认修改开区参数？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const url = '/groups/settings'
        this.sendRequest(url)
      }).catch(() => {
      })
    }

    sendRequest (url) {
      this.$refs.areaParams.validate(callbackValue => {
        if (callbackValue) {
          this.$loading({text: '设置中'})
          this.$http.post(url, this.formData).then(res => {
            this.$message.success('设置成功')
            this.formData = res.data
          }).catch(error => {
            this.$message.error('设置失败，原因:' + error)
          }).then(() => {
            this.$loading().close()
          })
        }
      })
    }
  }
</script>
<style rel="stylesheet/less" lang="less">
</style>

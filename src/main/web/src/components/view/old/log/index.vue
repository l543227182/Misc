<template>
  <div>
    <div class="breadcrumb">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item> 日志</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <hr>
    <div class="content">
      <div>
        <el-table
                  :data="tableData"
                  v-loading.body="loading"
                  @sort-change="sortChange"
                  ref="character">
          <el-table-column
            column-key="tags"
            prop="time"
            width="210"
            label="时间" >
          </el-table-column>
          <el-table-column
            prop="operator"
            width="150"
            label="操作人" >
          </el-table-column>
          <el-table-column
            prop="type"
            width="120"
            label="类型" >
          </el-table-column>
          <el-table-column
            prop="message"
            label="信息">
          </el-table-column>
        </el-table>
        <div class="pagination">
          <pagination ref="page" :total="total" @change="fetchData"></pagination>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import Vue from 'vue'
  import {Component, Watch} from 'vue-property-decorator'
  import DataTableMixins from '@/mixins/DataTable'
  @Component({
    mixins: [DataTableMixins]
  })
  export default class recommendation extends Vue {
    loading = false
    tableData = []
    total = 0
    refreshObj = {}
    @Watch('condition', {deep: true})
    onConditionChange () {
      this.fetchData()
    }
    mounted () {
      this.fetchData()
      this.refreshObj = setInterval(this.refreshData, 1000 * 10)
    }
    fetchData () {
      const uri = '/logs'
      this.loading = true
      this.$http.get(uri, {
        params: this.getQueryParams()
      }).then(res => {
        this.tableData = res.data.items
        this.total = res.data.count
      }).catch(error => {
        this.$message.error('获取列表失败，原因:' + error)
      }).then(() => {
        this.loading = false
      })
    }
    refreshData () {
      const uri = '/logs'
      this.refreshDataRequest(uri, this.getQueryParams(), res => {
        this.tableData = res.data.items
        this.total = res.data.count
      })
    }
    beforeDestroy () {
      clearInterval(this.refreshObj)
    }
    refreshDataRequest (url, params, onsuccess) {
      this.$http.get(url, {params: params}).then(res => {
        if (typeof onsuccess === 'function') {
          onsuccess(res)
        }
      }).catch(error => {
        this.$message.error(`请求失败，原因：${error}`)
      })
    }
  }
</script>
<style rel="stylesheet/less" lang="less">
</style>

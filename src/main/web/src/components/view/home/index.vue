<template>
  <div>
    <div class="breadcrumb">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item>主页</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <hr>
    <div class="content">
      <el-table
        :data="crawlerData"
        v-loading.body="loading"
        ref="character">
        <el-table-column
          column-key="tags"
          prop="title"
          width="210"
          show-overflow-tooltip
          label="标题" >
        </el-table-column>
        <el-table-column
          width="150"
          show-overflow-tooltip
          label="国家-省-市" >
          <template scope="scope">
            {{scope.row.country - scope.row.province - scope.row.city}}
          </template>
        </el-table-column>
        <el-table-column
          prop="avargeScore"
          width="120"
          label="平均分" >
        </el-table-column>
        <el-table-column
          prop="resourceUrl"
          label="源url">
        </el-table-column>
        <el-table-column
          label="查看评论">
          <template scope="scope">
            <comments :data="scope.row.comments" ></comments>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <pagination ref="page" :total="total" @change="fetchData"></pagination>
      </div>
    </div>
  </div>
</template>
<script>
  import Vue from 'vue'
  import { Component } from 'vue-property-decorator'
  import TaskRefreshMixins from '@/mixins/TaskRefresh'

  @Component({
    mixins: [TaskRefreshMixins]
  })
  export default class index extends Vue {
    mounted () {
      this.fetchData()
    }
    total = 0
    loading = false
    currentCommentsData = {}
    showComments = false
    crawlerData = []
    fetchData () {
      const params = {}
      const pagination = this.$refs.page
      if (pagination) {
        params.start = pagination.cursor
        params.pageSize = pagination.pageSize
      }
      this.$http.get('/getCrawlerData', {params: params}).then(res => {
        var data = res.data
        if (data.data !== null) {
          this.crawlerData = data.data
          this.total = data.total
        }
      }).catch(error => {
        this.$message.error(`加载数据失败，原因：${error}`)
      }).then(() => {
      })
    }
    sendRequest (url) {
      this.$loading({text: '请求中'})
      this.$http.get(url).then(res => {
        const h = this.$createElement
        console.log(res.data)
        this.$msgbox({
          title: '消息',
          message: h('p', null, [
            h('span', null, ''),
            h('i', { style: 'color: teal' }, res.data.name)
          ]),
          showCancelButton: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          beforeClose: (action, instance, done) => {
            if (action === 'confirm') {
              instance.confirmButtonLoading = true
              instance.confirmButtonText = '执行中...'
              setTimeout(() => {
                done()
                setTimeout(() => {
                  instance.confirmButtonLoading = false
                }, 300)
              }, 3000)
            } else {
              done()
            }
          }
        }).then(action => {
          this.$message({
            type: 'info',
            message: 'action: ' + action
          })
        })
      }).catch(error => {
        this.$message.error('请求失败，原因:' + error)
      }).then(() => {
        this.$loading().close()
      })
    }
  }
</script>
<style rel="stylesheet/less" lang="less">
</style>

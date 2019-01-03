<template>
  <div>
    <div class="breadcrumb">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item>推荐区调整</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <hr>
    <div class="content">
      <div>
        <el-row>
          <el-col class="tool-bar">
            <div>
              <el-input
                style="width: 300px"
                placeholder="请输入区号查找"
                icon="search"
                v-model="search"
                size="small">
              </el-input>
              <el-button type="primary" size="small" class="search" @click="fetchData">搜索</el-button>
            </div>
          </el-col>
        </el-row>
        <el-table
                  :data="tableData"
                  v-loading.body="loading"
                  @sort-change="sortChange"
                  ref="character">
          <el-table-column
            column-key="tags"
            sortable="custom"
            prop="area_num"
            label="区号" >
          </el-table-column>
          <el-table-column
            prop="name"
            sortable="custom"
            label="区名" >
          </el-table-column>
          <el-table-column
            prop="display_name"
            sortable="custom"
            label="对外区名">
          </el-table-column>
          <el-table-column
            prop="description"
            show-overflow-tooltip
            sortable="custom"
            label="开区手段">
          </el-table-column>
          <el-table-column
            prop="characters"
            sortable="custom"
            :show-overflow-tooltip="true"
            width="100"
            label="角色数" >
          </el-table-column>
          <el-table-column
            prop="last_time"
            sortable="custom"
            width="180"
            show-overflow-tooltip
            label="最后更新时间" >
          </el-table-column>
          <el-table-column
            prop="open_time"
            sortable="custom"
            width="180"
            show-overflow-tooltip
            label="开区时间">
          </el-table-column>
          <el-table-column
            prop="recommend"
            width="100"
            label="推荐区">
            <template scope="scope">
              <span v-if="recommendGroups[scope.row.area_num + '#' + scope.row.group_num]"> 是</span>
              <span v-else> 否</span>
            </template>
          </el-table-column>
        <el-table-column
            label="操作">
            <template scope="scope">
            <el-button v-if="!recommendGroups[scope.row.area_num + '#' + scope.row.group_num]" type="text" size="small"  @click="setRecommend(scope.row)">设为推荐区</el-button>
           </template>
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
    search = ''
    total = 0
    recommendGroups = []
    refreshObj = {}
    @Watch('condition', {deep: true})
    onConditionChange () {
      this.fetchData()
    }
    mounted () {
      this.fetchData()
      this.getRecommendGroups()
      this.refreshObj = setInterval(this.refreshData, 1000 * 10)
    }
    fetchData () {
      const uri = '/groups'
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
      const uri = '/groups'
      this.refreshDataRequest(uri, this.getQueryParams(), res => {
        this.tableData = res.data.items
        this.total = res.data.count
      })
      this.getRecommendGroups()
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
    getRecommendGroups () {
      const uri = '/groups/recommend'
      this.$http.get(uri).then(res => {
        this.recommendGroups = this.reFormatRecommendData(res.data)
      }).catch(error => {
        this.$message.error('获取列表失败，原因:' + error)
      })
    }
    reFormatRecommendData (data) {
      var formatData = {}
      this.$set(formatData, data.area_num + '#' + data.group_num, true)
      return formatData
    }
    setRecommend (data) {
      const url = '/groups/recommend'
      const params = {
        area_num: data.area_num,
        group_num: data.group_num
      }
      this.confirm(`把${data.name}设置为推荐区？`, () => {
        this.sendRequest(url, params, () => {
          this.fetchData()
          this.getRecommendGroups()
        })
      })
    }
    confirm (text, onConfirm, onCancel) {
      this.$confirm(text, '提示', {
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
    sendRequest (url, params, onsuccess) {
      this.$loading({text: '提交中'})
      this.$http.post(url, params).then(res => {
        if (typeof onsuccess === 'function') {
          onsuccess(res)
        }
        this.$message.success('设置')
      }).catch(error => {
        this.$message.error(`请求失败，原因：${error}`)
      }).then(() => {
        this.$loading().close()
      })
    }
    routePush (name, param) {
      this.$router.push({
        name: name,
        query: param
      })
    }
  }
</script>
<style rel="stylesheet/less" lang="less">
</style>

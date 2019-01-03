<template>
  <div>
    <div class="breadcrumb">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item>手动开区</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <hr>
    <div class="content">
      <el-row>
        <el-col :span="20" :offset="2">
          <el-button type="primary" @click="openArea"> 立即开启</el-button>
        </el-col>
        <el-col :span="20" :offset="2" style="margin-top: 20px;margin-bottom: 5px;">
          <strong >当前待开区列表：</strong>
        </el-col>
        <el-col :span="20" :offset="2">
          <el-table
            :data="data"
            v-loading.body="loading">
            <el-table-column
              column-key="tags"
              prop="area_num"
              label="区号" >
            </el-table-column>
            <el-table-column
              prop="name"
              label="区名" >
            </el-table-column>
            <el-table-column
              column-key="tags"
              prop="ip"
              label="区服IP" >
            </el-table-column>
            <el-table-column
              column-key="tags"
              show-overflow-tooltip
              label="客户端时间" >
              <template scope="scope">
                <div v-if="scope.row.client_time_valid">{{scope.row.client_time}}</div>
                <div v-else style="color: red">{{scope.row.client_time}}</div>
              </template>
            </el-table-column>
            <el-table-column
              label="索引" >
              <template scope="scope">
              {{scope.row.index + indexData.idx}}
              </template>
            </el-table-column>
            <el-table-column
              label="对外区名" >
              <template scope="scope">
                {{indexData.prefix}}{{(scope.row.index + indexData.idx)}}{{indexData.suffix}}
              </template>
            </el-table-column>

          </el-table>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script type="text/jsx">
  import Vue from 'vue'
  import {Component} from 'vue-property-decorator'
  @Component()
  export default class setArea extends Vue {
    loading = false
    data = []
    indexData = {}
    refreshObj = {}
    mounted () {
      this.fetchData()
      this.refreshObj = setInterval(this.refreshData, 1000 * 10)
    }
    fetchData () {
      const url = '/groups/available'
      this.$loading({text: '加载中'})
      this.$http.get(url).then(res => {
        this.data = res.data
        _.forEach(this.data, (item, index) => {
          this.$set(item, 'index', index)
        })
      }).catch(error => {
        this.$message.error(`加载失败，原因：${error}`)
      })

      this.$http.get('/groups/settings').then(res => {
        this.indexData = res.data
      }).catch(error => {
        this.$message.error(`加载开区参数失败，原因：${error}`)
      }).then(() => {
        this.$loading().close()
      })
    }
    refreshData () {
      const uri = '/groups/available'
      this.refreshDataRequest(uri, {}, res => {
        this.data = res.data
        _.forEach(this.data, (item, index) => {
          this.$set(item, 'index', index)
        })
      })
      this.refreshDataRequest('/groups/settings', {}, res => {
        this.indexData = res.data
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
    openArea () {
      this.$loading({text: '加载中'})
      this.$http.get('/groups', {
        params: {
          offset: 0,
          limit: 1
        }
      }).then(res => {
        const data = res.data.items[0]
        const prevArea = `上一个区对外区名 ${data.display_name}$$(区号:${data.area_num},区名:${data.name} ${data.open_time === '' ? ')' : (',开区时间:' + data.open_time + ')')}`
        this.confirm(prevArea,`现在对外开放 ${this.indexData.prefix}${this.indexData.idx}${this.indexData.suffix}？$$(区号:${this.data[0].area_num},区名:${this.data[0].name})`, () => {
          this.sendRequest()
        })
      }).catch(error => {
        this.$message.error(`加载开区参数失败，原因：${error}`)
      }).then(() => {
        this.$loading().close()
      })
    }
    sendRequest () {
      const url = '/groups/manual'
      const params = {
        area_num: this.data[0].area_num,
        group_num: this.data[0].group_num,
        display_name: `${this.indexData.prefix}${this.indexData.idx}${this.indexData.suffix}`
      }
      this.$http.post(url, params).then(res => {
        this.$message.success('区组开启中,请等待页面刷新')
        this.fetchData()
      }).catch(error => {
        this.$message.error('开启失败，原因:' + error)
      }).then(() => {
      })
    }
  }
</script>
<style rel="stylesheet/less" lang="less">
</style>

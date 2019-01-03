<template>
    <el-pagination
            @size-change="sizeChange"
            @current-change="currentChange"
            :current-page="current"
            :page-sizes="sizes"
            :page-size="pageSize"
            :layout="layout"
            :total="total">
    </el-pagination>
</template>
<script>
  import Vue from 'vue'
  import {Component} from 'vue-property-decorator'
  @Component({
    name: 'Pagination',
    props: {
      total: Number,
      onChange: String,
      layout: {
        type: String,
        required: false,
        default: 'total, sizes, prev, pager, next, jumper'
      },
      size: {
        type: Number,
        required: false,
        default: 10
      },
      sizes: {
        type: Array,
        required: false,
        default: () => [10, 20, 30, 50]
      }
    }
  })
  export default class Pagination extends Vue {
    pageSize = this.size
    current = 1

    get cursor () {
      return (this.current - 1) * this.pageSize
    }

    sizeChange (val) {
      this.pageSize = val
      this.current = 1
      this.$emit('change')
    }
    currentChange (val) {
      this.current = val
      this.$emit('change')
    }
  }
</script>
<style rel="stylesheet/less" lang="less">
</style>

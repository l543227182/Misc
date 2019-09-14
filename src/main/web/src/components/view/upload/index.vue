<template>
  <div class="uploader">
    <uploader  style="width: 50%" class="uploader-example" ref="uploader"
               :options="options"
               @file-complete="fileComplete"
               @complete="complete"
                @test-method="testMethod">
      <uploader-btn>选择文件</uploader-btn>
      <uploader-list></uploader-list>
    </uploader>
  </div>
</template>
<script>
  import Vue from 'vue'
  import { Component } from 'vue-property-decorator'
  @Component()
  export default class upload extends Vue {
    statusText = '..'
    fileStatusText = {}
    mounted () {
    }
    complete () {
      console.log(arguments)
    }
    testMethod () {
      console.log(arguments)
    }
    fileComplete () {
      console.log('fileComplete')
      console.log(arguments)
    }
    options = {
      target: '/webapi/netdisk/upload_file_pieces',
      chunkSize: 1 * 1024 * 1024,
      checkChunkUploadedByResponse: function (a, b) {
        console.log(a)
        console.log(b)
        return false
      },
      testChunks: true
    }
  }
</script>
<style rel="stylesheet/less" lang="less">
</style>

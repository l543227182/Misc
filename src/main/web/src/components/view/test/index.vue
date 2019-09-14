<template>
  <div>
    <div class="breadcrumb">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item>测试</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <hr>
    <div class="content">

        <el-button type="primary" @click="upload.visible = true"><a>点击上传</a></el-button >

    </div>
    <el-dialog size="large" title="上传文件" :visible.sync="upload.visible" @close="onUploadClose"  :close-on-click-modal="false">
      <el-form ref="form" label-width="80px" class="script-upload">
        <el-form-item label="上传文件">
          <el-row>
            <el-col :span="20">

              <el-upload
                ref="upload"
                multiple
                :on-change="filesChange"
                :file-list="filelist"
                :show-file-list="false"
                :on-remove="onremove"
                :limit="10"
                :auto-upload="false"
                action="/"
              >
                <el-button type="primary" style="width: 150px;">选择文件</el-button>
                <div slot="tip" class="el-upload__tip" style="margin-top: 0;">文件大小不能超过5GB</div>
              </el-upload>
              <el-alert
                v-if="md5CountTasks"
                title=" md5计算中...."
                type="warning"
                show-icon>
              </el-alert>
              <table>
                <tr v-for="item in getFileList">
                    <td style="width: 20%">{{item.name}}</td>
                    <td style="width: 70%">
                      <el-progress v-if="requestQueue[item.name] && requestQueue[item.name].state === fileItemState.fail"  status="exception" :percentage="requestQueue[item.name].percent"></el-progress>
                      <el-progress v-else-if="requestQueue[item.name] && requestQueue[item.name].state === fileItemState.success"  status="success" :percentage="requestQueue[item.name].percent"></el-progress>
                      <el-progress  v-else  :percentage="requestQueue[item.name].percent"></el-progress>
                    </td>
                    <td>
                      <div></div>
                      <el-button type="text" @click="uploadChunks(requestQueue[item.name])" v-if="requestQueue[item.name] && requestQueue[item.name].state === fileItemState.fail">重试</el-button>
                      <el-button type="text" v-if="requestQueue[item.name] && requestQueue[item.name].state !== fileItemState.uploading"  @click="removeclick(item.name)" >删除</el-button>
                    </td>
                </tr>
              </table>
            </el-col>
            <el-col :span="2">
              <el-checkbox label="覆盖同名文件"
                           name="type"
                           v-model="upload.is_cover"
                           :true-label="1"
                           :false-label="0">
              </el-checkbox>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item>
          <el-button v-if="filelist.length" type="primary" size="small" :disabled="state!==0 || md5CountTasks !== 0 "
                     @click="startUpload">开始上传
          </el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="merge">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {Component} from 'vue-property-decorator'
  import Vue from 'vue'
  import SparkMD5 from 'spark-md5'
  @Component()
  export default class upload extends Vue {
    filelist = []
    state = 0
    // 所有文件上传 0 表示 没有文件在上传  1 有文件在上次中
    globalState = {
      'uploading': 1,
      'unruning': 0
    }
    // 单个文件的状态 0 未开始上传 1上传中 2上传成功 3 暂停 -1 上传失败
    fileItemState = {
      'normal': 0,
      'uploading': 1,
      'success': 2,
      'pause': 3,
      'fail': -1
    }
    upload = {          // upload
      visible: false,
      is_cover: 1
    }
    /*
     maxSize = 5 * 1024 * 1024 * 1024 // 上传最大文件限制
     multiUploadSize = 100 * 1024 * 1024 // 大于这个大小的文件使用分块上传(后端可以支持断点续传)
     */
    eachSize = 100 * 1024 * 1024 // 每块文件大小
    requestQueue = {} // 请求方法队列
    // 大文件md5计算慢 可能会没计算完成就点击上传
    md5CountTasks = 0
   /* check () {
      // 没有计算任务
      if (this.md5CountTasks === 0) {
        return;
      }
      setTimeout(check, 1000);
    } */
    async filesChange (file, fileList) {
      const fileName = file.name
      // todo 相同文件名
      if (!this.requestQueue[fileName]) {
        const chunks = Math.ceil(file.size / this.eachSize)
        const fileChunks = await this.splitFile(file.raw, this.eachSize, chunks)
        _.forEach(fileChunks, item => {
          this.md5CountTasks += 1
          this.computeMD5(item)
        })
        console.log(fileChunks)
        this.$set(this.requestQueue, fileName, {
          rawData: file,
          chunks: fileChunks,
          percent: 0,
          state: this.fileItemState.normal
        })
      }
      this.filelist = (fileList)
    }

    async startUpload () {
      // 进行中不能够在点击开始上传
      const keys = _.keys(this.requestQueue)
      for (var key of keys) {
        const itemData = this.requestQueue[key]
        if (itemData.state !== this.fileItemState.uploading) {
          this.uploadChunks(itemData)
        }
      }
    }
    get getFileList () {
      return this.filelist
    }
    removeclick (name) {
      delete this.requestQueue[name]
      this.filelist = _.filter(this.filelist, item => item.name !== name)
    }
    onremove (file, fileList) {
      delete this.requestQueue[file.name]
    }
    setGlobalState (state) {
      this.state = state
    }

    // just for control global state
    runingCount = 0
    setFileItemState (fileItem, state) {
      fileItem.state = state
      this.runingCount = state === this.fileItemState.uploading ? this.runingCount + 1 : this.runingCount - 1
      if (this.runingCount) {
        this.setGlobalState(this.globalState.uploading)
      } else {
        this.setGlobalState(this.globalState.unruning)
      }
      // todo 暂停
    }
    // 大文件分块上传
    uploadChunks (data) {
      return new Promise(async (resolve, reject) => {
        try {
          // 文件上传中
          this.setFileItemState(data, this.fileItemState.uploading)
          const { eachSize } = this
          const chunks = Math.ceil(data.rawData.size / eachSize)
          const fileChunks = data.chunks
          // let currentChunk = 0
          for (let i = 0; i < fileChunks.length; i++) {
            // 验证这一块是否已经上传
            const isuploaded = await this.validateFile(fileChunks[i].md5)
            if (isuploaded && isuploaded.code === 0) continue
            const uploadres = await this.postFile({
              chunked: true,
              chunk: i,
              chunks,
              eachSize,
              fileName: data.rawData.name,
              fullSize: data.rawData.size,
              file: fileChunks[i]
            }, data)
            if (uploadres && uploadres.code !== 0) {
              this.$message.error('文件' + data.rawData.name + '上传失败,原因:' + uploadres.message)
              this.setFileItemState(data, this.fileItemState.fail)
              return
            }
          }
          data.percent = 100
          this.setFileItemState(data, this.fileItemState.success)
          resolve()
        } catch (e) {
          this.$message.error('文件' + data.rawData.name + '上传失败,原因:' + e.message)
          this.setFileItemState(data, this.fileItemState.fail)
          reject(e)
        }
      })
    }

    // 文件分块,利用Array.prototype.slice方法
    splitFile (file, eachSize, chunks) {
      return new Promise((resolve, reject) => {
        try {
          setTimeout(() => {
            const fileChunk = []
            for (let chunk = 0; chunks > 0; chunks--) {
              fileChunk.push(file.slice(chunk, chunk + eachSize))
              chunk += eachSize
            }
            resolve(fileChunk)
          }, 0)
        } catch (e) {
          console.error(e)
          reject(new Error('文件切块发生错误'))
        }
      })
    }
    // 提交文件方法,将参数转换为FormData, 然后通过axios发起请求
    postFile (param, data) {
      const formData = new FormData()
      for (let p in param) {
        formData.append(p, param[p])
      }
      const config = {
        onUploadProgress: e => {
          data.percent = parseInt(Number(((((param.chunk * (param.eachSize - 1)) + (e.loaded)) / param.fullSize) * 100).toFixed(2)))
        }
      }
      return this.$http.post('/webapi/netdisk/upload_file_pieces', formData, config).then(rs => rs.data)
    }
    computeMD5 (file) {
      try {
        // 应该改成同步的
        let fileReader = new FileReader()
        let time = new Date().getTime()
        let md5 = ''
        fileReader.readAsArrayBuffer(file)
        fileReader.onloadstart = () => {
          time = new Date().getTime()
        }
        fileReader.onload = (e) => {
          md5 = SparkMD5.ArrayBuffer.hash(e.target.result)
          console.log(`MD5计算完毕：  MD5：${md5} 一共用时：${new Date().getTime() - time} ms,chunk size : ${file.size}`)
          file.md5 = md5
          this.md5CountTasks -= 1
        }
        fileReader.onerror = () => {
          this.md5CountTasks -= 1
          this.$message.error('FileReader onerror was triggered, maybe the browser aborted due to high memory usage.')
        }
      } catch (e) {
        this.md5CountTasks -= 1
        this.$message.error('FileReader onerror was triggered, maybe the browser aborted due to high memory usage.')
      }
    }
    // 文件校验方法
    validateFile (md5) {
      return this.$http.get('/webapi/netdisk/check_file_pieces', {params: {md5: md5}}).then(rs => rs.data)
    }
    merge () {
      _.forEach(this.requestQueue, item => {
        if (item.state === this.fileItemState.success) {
          const md5s = _.map(item.chunks, 'md5')
          if (md5s && md5s.length) {
            const md5str = md5s.join(',')
            this.mergeRequest(md5str, item.rawData)
          }
        }
      })
    }
    mergeRequest (md5str, file) {
      return this.$http.get('/webapi/netdisk/merge_file_pieces', {
        params: {
          md5: md5str,
          filename: file.name,
          pid: '/',
          is_cover: upload.is_cover
        }
      }).then(rs => rs.data)
    }
    onUploadClose () {
      this.$refs.upload.clearFiles()
    }
  }
</script>
<style rel="stylesheet/less" lang="less">
</style>

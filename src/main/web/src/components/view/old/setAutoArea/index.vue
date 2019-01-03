<template>
  <div>
    <div class="breadcrumb">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item>自动开区设置</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <hr>
    <div class="content">
      <el-row>
        <el-col :span="20" :offset="2">
          <el-form ref="setAreaForm" label-width="150px" :model="formData" :rules="rules">
            <el-form-item label="角色数阈值" prop="threshold" >
              <el-input type="number" size="small" style="width: 50%" v-model.number="formData.threshold"></el-input>
            </el-form-item>
            <el-form-item label="必开时间点" prop="include">
              <div v-for="(item, index) in formData.include" style="margin-bottom: 20px">
                <el-form-item :rules="validateInclude" :prop="'include['+ index+']'">
                <el-select v-model="item.type" placeholder="请选择">
                  <el-option
                    v-for="item in selectData"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
                  <el-time-picker
                    v-if="item.type === 'EVERYDAY'"
                    v-model="item.value"
                    format="HH:mm"
                    placeholder="起始时间">
                  </el-time-picker>
                <el-date-picker
                  v-if="item.type === 'ONCE'"
                  v-model="item.value"
                  format="yyyy-MM-dd HH:mm"
                  type="datetime"
                  placeholder="选择日期时间">
                </el-date-picker>
                <el-button type="text" icon="delete" class="color-danger" @click="delIncludeItem(index)"></el-button>
                </el-form-item>
              </div>
              <el-button type="text" @click="addIncludeItem">+ 添加</el-button>
            </el-form-item>


            <el-form-item label="不开时间段" prop="exclude">
              <div v-for="(item, index) in formData.exclude" style="margin-bottom: 20px">
                <el-form-item :rules="validateExclude" :prop="'exclude['+ index+']'">
                  <el-time-picker
                    v-model="item.begin"
                    format="HH:mm"
                    placeholder="起始时间">
                  </el-time-picker>
                  <el-time-picker
                    v-model="item.end"
                    format="HH:mm"
                    placeholder="结束时间">
                  </el-time-picker>
                <el-button type="text" icon="delete" class="color-danger" @click="delExcludeItem(index)"></el-button>
                </el-form-item>
              </div>
              <el-button type="text" @click="addExcludeItem">+ 添加</el-button>
            </el-form-item>
            <el-form-item v-if="showButton">
              <div v-if="formData.enable">
                <el-form-item>
                  <el-button type="primary" @click="switchArea(false)">停用自动开区</el-button>
                </el-form-item>

                <el-form-item>
                  <span style="color: darkgreen">当前状态：自动开区已启用</span>
                </el-form-item>
              </div>
              <div v-if="!formData.enable">
                <el-form-item>
                  <el-button  type="primary" @click="switchArea(true)">启用自动开区</el-button>
                </el-form-item>
                <el-form-item>
                  <span style="color: red">当前状态：自动开区已停用</span>
                </el-form-item>
              </div>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submit">确认</el-button>
<!--
              <el-button type="primary" @click="$router.push({name: 'list'})">取消</el-button>
-->
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
  import momentTZ from 'moment-timezone'
  @Component()
  export default class setAutoArea extends Vue {
    loading = false
    selectData = [{
      label: '每天',
      value: 'EVERYDAY'
    }, {
      label: '一次性',
      value: 'ONCE'
    }]
    // {type: 'EVERYDAY', value: '12:30'}
    // {type: 'ONCE', value: '2017-2-21 12:30'}
    showButton = false
    formData = {
      enable: false,
      threshold: '',
      include: [],
      exclude: []
    }
    mounted () {
      this.fetchData()
    }
    get validateInclude () {
      return [{
        validator: (rule, value, callback) => {
          if (value === null || value.value === '') {
            callback(new Error('请填写完整时间'))
          } else {
            callback()
          }
        },
        trigger: 'blur'
      }
      ]
    }
    get validateExclude () {
      return [{
        validator: (rule, value, callback) => {
          if (value.begin === '' || value.end === '') {
            if (value.begin === '' && value.end === '') {
              callback(new Error('请填完整时间段'))
            } else if (value.begin === '') {
              callback(new Error('请填开始时间'))
            } else {
              callback(new Error('请填结束时间'))
            }
          } else {
            const begin = value.begin.getTime()
            const end = value.end.getTime()
            if (begin > end) {
              callback(new Error('开始时间大于结束时间'))
            } else {
              callback()
            }
          }
        },
        trigger: 'submit'
      }
      ]
    }
    addIncludeItem () {
      this.formData.include.push({
        type: '',
        value: ''
      })
    }
    addExcludeItem () {
      this.formData.exclude.push({
        begin: '',
        end: ''
      })
    }
    delIncludeItem (index) {
      this.formData.include.splice(index, 1)
    }
    delExcludeItem (index) {
      this.formData.exclude.splice(index, 1)
    }
    fetchData () {
      const url = '/groups/auto'
      this.$loading({text: '加载中'})
      this.$http.get(url).then(res => {
        if (res.data === null) {
          this.showButton = false
        } else {
          this.showButton = true
          this.transferData(res.data)
          this.formData = res.data
        }
      }).catch(error => {
        this.$message.error(`加载失败，原因：${error}`)
      }).then(() => {
        this.$loading().close()
      })
    }
    transferData (data) {
      _.forEach(data.exclude, item => {
        item.begin = new Date('January 12,2012 ' + item.begin)
        item.end = new Date('January 12,2012 ' + item.end)
      })
      _.forEach(data.include, item => {
        if (item.type === 'EVERYDAY') {
          item.value = new Date('January 12,2012 ' + item.value)
        }
      })
    }
    switchArea (action) {
      // 启用
      const url = '/groups/auto'
      this.formData.enable = action
      this.sendRequest(url)
    }
    get rules () {
      return {
        threshold: [{required: true, message: '请填写角色数阈值', type: 'number', trigger: 'blur'}]
      }
    }
    backData = null
    submit () {
      const url = '/groups/auto'
      this.sendRequest(url)
    }

    formatParams () {
      this.backData = JSON.parse(JSON.stringify(this.formData))
      _.forEach(this.formData.exclude, item => {
        item.begin = momentTZ(item.begin).format('HH:mm')
        item.end = momentTZ(item.end).format('HH:mm')
      })
      _.forEach(this.formData.include, item => {
        if (item.type === 'EVERYDAY') {
          item.value = momentTZ(item.value).format('HH:mm')
        } else {
          item.value = momentTZ(item.value).format('YYYY-MM-DD HH:mm')
        }
      })
    }
    sendRequest (url) {
      this.$refs.setAreaForm.validate(callbackValue => {
        if (callbackValue) {
          this.formatParams()
          this.$loading({text: '设置中'})
          this.$http.post(url, this.formData).then(res => {
            this.$message.success('设置成功')
            this.formData = res.data
            this.transferData(this.formData)
          }).catch(error => {
            if (this.backData) {
              this.formData = this.backData
            }
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

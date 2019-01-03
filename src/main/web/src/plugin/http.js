import axios from 'axios'
import {MessageBox} from 'element-ui'
import paramsSerializer from './params-serializer'

// 未登录状态下的处理函数
const sessionTimeoutHandler = () => {
  MessageBox.alert('回话过期，请重新登录', '提示', {
    confirmButtonText: '确定',
    callback: () => {
      window.location.href = '/'
    }
  })
}

export default {
  install: function (Vue, options) {
    axios.defaults.headers.post['Content-Type'] = 'application/json'
    axios.defaults.headers.put['Content-Type'] = 'application/json'
    axios.defaults.paramsSerializer = paramsSerializer // url参数序列化方法
    let instance = axios.create(options || {})
    instance.interceptors.request.use(function (config) {
      // Do something before request is sent
      config.url = '/api' + config.url
      return config
    })
    // Add a response interceptor
    instance.interceptors.response.use(function (response) {
      // 判断返回的是否是html
      const data = response.data
      if (typeof data === 'string' && data.indexOf('<!DOCTYPE html>') !== -1) {
        sessionTimeoutHandler()
      }
      return response
    }, function (error) {
      if (error.message === 'Network Error') {
        sessionTimeoutHandler()
      }
      // Do something with response error
      const response = error.response
      if (response.data && !response.data.message) {
        response.data = {
          code: -response.status,
          message: error
        }
      }
    // typeof error.response.data.message === 'string' ? error.response.data.message : error.response.data.message.message
      return Promise.reject(typeof error.response.data.message === 'string' ? error.response.data.message : JSON.stringify(error.response.data.message))
    })
    Vue.$http = instance
    Vue.prototype.$http = instance
  }
}

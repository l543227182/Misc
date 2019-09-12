import Vue from 'vue'
import Router from 'vue-router'
import home from './home'
import test from './test'
import upload from './upload'

Vue.use(Router)

export default new Router({
  routes: [
    home, test, upload,
    {path: '/*', redirect: '/home/index'}
  ]
})

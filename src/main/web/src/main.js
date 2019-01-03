// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import '@/assets/custom-theme/style/index.css'
import '@/assets/less/index.less'

// promise
import promise from 'es6-promise'
promise.polyfill()

// vuex
import Vuex from 'vuex'
Vue.use(Vuex)
import initStore from './store'
let store = initStore(Vuex)

// element-ui
import ElementUI from 'element-ui'
Vue.use(ElementUI)

// axios
import HttpPlugin from './plugin/http'
Vue.use(HttpPlugin)

// custom components
import ComponentInstall from '@/components/common/install'
ComponentInstall(Vue)

// filter
import FilterInstall from '@/filter'
FilterInstall(Vue)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  template: '<App/>',
  components: { App }
})

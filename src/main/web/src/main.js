// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import '@/assets/custom-theme/style/index.css'
import '@/assets/less/index.less'
// promise
import promise from 'es6-promise'
// vuex
import Vuex from 'vuex'
import initStore from './store'
// element-ui
import ElementUI from 'element-ui'
// axios
import HttpPlugin from './plugin/http'
// custom components
import ComponentInstall from '@/components/common/install'
// filter
import FilterInstall from '@/filter'
import uploader from 'vue-simple-uploader'
import VueVideoPlayer from 'vue-video-player'
// require videojs style
import 'video.js/dist/video-js.css'

promise.polyfill()

Vue.use(Vuex)

let store = initStore(Vuex)

Vue.use(ElementUI)

Vue.use(HttpPlugin)

ComponentInstall(Vue)

FilterInstall(Vue)

Vue.config.productionTip = false

Vue.use(uploader)

// import 'vue-video-player/src/custom-t

Vue.use(VueVideoPlayer)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  template: '<App/>',
  components: {App}
})

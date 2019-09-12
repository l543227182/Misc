<template>
  <div id="app">
    <el-row class="header">
      <el-col >
        <div class="title">Jweb</div>
        <div style="float: right;margin-right: 40px;">
          <span>
            <i :class="{iconfont: true, 'icon-guanliyuan': !isAdmin, 'icon-guanliyuan1': isAdmin}" style="font-size: 20px;font-weight: 100;"></i>
            {{userName}}
          </span>
        </div>
      </el-col>
    </el-row>
    <el-row class="body">
      <el-col style="width: 200px;" class="side-bar">
        <el-menu theme="dark"  :default-openeds="['1']" :default-active='routerMatch.path'>
          <el-menu-item index="/home" @click="routePush('index')">主页</el-menu-item>
         <el-menu-item index="/test" @click="routePush('test')">测试</el-menu-item>
         <el-menu-item index="/upload" @click="routePush('upload')">upload</el-menu-item>
          <!-- <el-menu-item index="/manual" @click="routePush('manualArea')">手动开区</el-menu-item>
          <el-menu-item index="/recommendation" @click="routePush('recomList')">推荐区调整</el-menu-item>
          <el-menu-item index="/areaParam" @click="routePush('areaParam')">开区参数</el-menu-item>
          <el-menu-item index="/notice" @click="routePush('noticeIndex')">通知维护</el-menu-item>
          <el-menu-item index="/log" @click="routePush('logList')">日志</el-menu-item>-->
        </el-menu>
      </el-col>
      <el-col style="width: calc(100% - 200px);" class="page" v-loading="pageLoading" :element-loading-text="pageLoadingText">
        <router-view></router-view>
      </el-col>
    </el-row>
  </div>
</template>
<script>
  import {mapState} from 'vuex'
  export default {
    name: 'app',
    computed: {
      isAdmin () {
        return this.$store.state.is_admin
      },
      userName () {
        return this.$store.state.name
      },
      routerMatch () {
        return this.$route.matched[0] || {}
      },
      ...mapState([
        'pageLoading',
        'pageLoadingText'
      ])
    },
    created () {
      // this.getProfile()
    },
    methods: {
      routePush (name) {
        this.$router.push({
          name: name
        })
      },
      getProfile () {
        this.$http.get('/profile').then(res => {
          const userInfo = res.data
          this.$store.commit('setUserInfo', {
            id: userInfo.id,
            name: userInfo.name,
            is_admin: userInfo.is_admin
          })
        }).catch(error => {
          this.$message.error(`获取用户数据失败，原因：${error}`)
        })
      }
    }
  }
</script>

<style>
</style>

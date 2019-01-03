/**
 * Created by wangtongyao.victor on 2017/9/4.
 */
export default {
  data () {
    return {
      taskRefreshTimer: null,
      interval: 5 * 1000
    }
  },
  computed: {
  },
  mounted () {
    if (this.taskRefreshTimer === null) {
      this.taskRefreshTimer = setInterval(this.refreshMethod, this.interval)
    }
  },
  beforeDestroy () {
    if (this.taskRefreshTimer !== null) {
      clearInterval(this.taskRefreshTimer)
    }
  },
  methods: {
    refreshMethod () {}
  }
}

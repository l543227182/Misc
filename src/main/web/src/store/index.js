/**
 * Created by wangtongyao.victor on 2017/4/12.
 */
export default function (Vuex) {
  return new Vuex.Store({
    state: {
      id: '',
      name: '',
      is_admin: false,
      pageLoading: false,
      pageLoadingText: ''
    },
    getters: {},
    mutations: {
      setUserInfo(state, userInfo) {
        state.id = userInfo.id
        state.name = userInfo.name
        state.is_admin = !!userInfo.is_admin
      },
      'pageLoading/start'(state, text) {
        state.pageLoadingText = text
        state.pageLoading = true
      },
      'pageLoading/stop'(state) {
        state.pageLoading = false
      }
    },
    actions: {}
  })
}

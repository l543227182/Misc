/**
 * Created by wangtongyao.victor on 2017/5/4.
 */
import Pagination from './Pagination.vue'
import EditTag from './EditTag.vue'
import Comments from './comments.vue'

const components = {
  'pagination': Pagination,
  'EditTag': EditTag,
  'comments': Comments
}
export default function (Vue) {
  for (let key in components) {
    Vue.component(key, components[key])
  }
}

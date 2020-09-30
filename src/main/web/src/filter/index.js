import {NOTICE_TYPE, TASK_STATUS} from '@/constant'

const getMapFilter = (Map) => {
  return (value) => {
    return Map[value]
  }
}
const filters = {
  versionType: getMapFilter(NOTICE_TYPE),
  taskStatus: getMapFilter(TASK_STATUS),
  byte: (bytes) => {
    if (bytes === 0) return '0 B'
    const k = 1024
    const sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']
    const i = Math.floor(Math.log(bytes) / Math.log(k))
    let value = (bytes / Math.pow(k, i))
    if (i > 0) {
      value = value.toFixed(2)
    }
    return value + ' ' + sizes[i]
  }
}

export default (Vue) => {
  for (let key in filters) {
    Vue.filter(key, filters[key])
  }
}

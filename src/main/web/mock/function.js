/**
 * Created by wangtongyao.victor on 2017/8/11.
 */
const _ = require('lodash')

module.exports = {
  // 延时函数，执行完回调函数后清理timeout
  setTimeout: function (callback, timeout) {
    var timer
    timer = setTimeout(function () {
      try {
        callback()
      } finally {
        clearTimeout(timer)
      }
    }, timeout)
  },
  // 返回排序分页后的列表
  getPageResult: function (list, offset, limit, orderBy) {
    let orderedList = list
    const order = (orderBy || '').split(' ')
    if (order[0]) {
      orderedList = _.sortBy(orderedList, order[0])
      if (order[1] === 'desc') {
        orderedList = _.reverse(orderedList)
      }
    }
    if (!offset && offset !== 0) {
      return {
        items: orderedList,
        count: list.length
      }
    } else {
      return {
        items: orderedList.slice(offset, offset + limit),
        count: list.length
      }
    }
  }
}
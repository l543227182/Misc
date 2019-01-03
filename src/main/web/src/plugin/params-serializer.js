/**
 * Created by wangtongyao.victor on 2017/7/28.
 */
export default (params) => {
  if (params) {
    return json2FormParams(params)
  }
  return ''
}
function json2FormParams (obj) {
  let query = ''
  let name
  let value
  let fullSubName
  let subName
  let subValue
  let innerObj
  for (name in obj) {
    value = obj[name]
    if (value instanceof Array) {
      for (let i = 0; i < value.length; ++i) {
        subValue = value[i]
        fullSubName = name + '[' + i + ']'
        innerObj = {}
        innerObj[fullSubName] = subValue
        query += json2FormParams(innerObj) + '&'
      }
    } else if (value instanceof Object) {
      for (subName in value) {
        subValue = value[subName]
        fullSubName = name + '.' + subName
        innerObj = {}
        innerObj[fullSubName] = subValue
        query += json2FormParams(innerObj) + '&'
      }
    } else if (value !== undefined && value !== null) {
      query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&'
    }
  }
  return query.length ? query.substr(0, query.length - 1) : query
}

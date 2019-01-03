const Function = require('../function')
const Mock = require('mockjs')
const Random = Mock.Random
const _ = require('lodash')
const groups = []
const logs = []
const varBoole = [false, true]
const types = ['自动开区', '手动开区', '推荐区调整']
for (let i = 1; i <= 100; i++) {
  groups.push({
    area_num: i * 10,
    group_num: i * 10,
    name: 'groupName' + i,
    characters: 15 * i,
    last_time: Random.date('yyyy-MM-dd HH:mm:ss'),
    open_time: Random.date('yyyy-MM-dd HH:mm:ss'),
    description: 'description' + Random.date('yyyy-MM-dd HH:mm:ss'),
    display_name: 'display_name' + i,
    server_time: Random.date('yyyy-MM-dd HH:mm:ss'),
    client_time: Random.date('yyyy-MM-dd HH:mm:ss'),
    client_time_valid: varBoole[Random.natural(0, 1)],
    ip: `${Random.natural(1, 255)}.${Random.natural(1, 255)}.${Random.natural(1, 255)}.${Random.natural(1, 255)}`,
    port: Random.natural(1000, 9999)
  })
}

for (let i = 0; i < 120; i++) {
  logs.push({
    time: Random.date('yyyy-MM-dd HH:mm:ss'),
    type: types[Random.natural(0, 2)],
    operator: 'operator-operator' + i,
    message: 'messagemessagemessagemessagemessagemessagemessagemessage ' + i
  })
}
module.exports = {
  'GET /groups/auto': function (req, res) {
    Function.setTimeout(function () {
      res.json({
        enable: false,
        threshold: 111,
        include: [{
          type: 'EVERYDAY',
          value: '12:30'
        }, {
          type: 'ONCE',
          value: '2017-12-10 12:30'
        }, {
          type: 'EVERYDAY',
          value: '12:45'
        }],
        exclude: [{
          begin: '14:50',
          end: '18:00'
        }, {
          begin: '12:50',
          end: '21:00'
        }]
      })
    }, 300)
  },
  'POST /groups/auto': function (req, res) {
    Function.setTimeout(function () {
      res.json({
        enable: true,
        threshold: 111,
        include: [{
          type: 'EVERYDAY',
          value: '12:30'
        }, {
          type: 'ONCE',
          value: '2017-12-10 12:30'
        }],
        exclude: [{
          begin: '14:50',
          end: '18:00'
        }]
      })
    }, 300)
  },
  'GET /groups': function (req, res) {
    const offset = parseInt(req.query.offset)
    const limit = parseInt(req.query.limit)
    const orderBy = req.query.order_by
    Function.setTimeout(function () {
      res.json(Function.getPageResult(groups, offset, limit, orderBy))
    }, 300)
  },
  'GET /logs': function (req, res) {
    const offset = parseInt(req.query.offset)
    const limit = parseInt(req.query.limit)
    const orderBy = req.query.order_by
    Function.setTimeout(function () {
      res.json(Function.getPageResult(logs, offset, limit, orderBy))
    }, 300)
  },
  'GET /groups/available': function (req, res) {
    Function.setTimeout(function () {
      res.json(groups)
    }, 300)
  },
  'GET /groups/settings': function (req, res) {
    Function.setTimeout(function () {
      res.json({
        prefix: 'pre-',
        suffix: '-suf',
        idx: 10
      })
    }, 300)
  },
  'POST /groups/manual': function (req, res) {
    Function.setTimeout(function () {
      res.json({})
    }, 300)
  },
  'POST /groups/recommend': function (req, res) {
    Function.setTimeout(function () {
      res.json({})
    }, 300)
  },
  'GET /groups/recommend': function (req, res) {
    Function.setTimeout(function () {
      res.json(_.filter(groups, item => {
        return item.recommend
      }).map(item => {
        return {
          area_num: item.area_num,
          group_num: item.group_num
        }
      })[0])
    }, 300)
  },
  'GET /notifies': function (req, res) {
    Function.setTimeout(function () {
      res.json({})
    }, 300)
  },
  'POST /notifies': function (req, res) {
    Function.setTimeout(function () {
      res.json({
        wechat_party_id: 'wechat1',
        phones: ['13555554545', '13648686657'],
        content: 'come on , its on fire'
      })
    }, 300)
  },
  'GET /getCrawlerData': function (req, res) {
    Function.setTimeout(function () {
      res.json([])
    }, 0)
  }
}

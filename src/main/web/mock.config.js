// Learn more on how to config.
// - https://github.com/leeluolee/puer
// - http://mockjs.com

const mock = {};

require('fs').readdirSync(require('path').join(__dirname + '/mock/api'))
  .forEach(function (file) {
    Object.assign(mock, require('./mock/api/' + file));
  });

module.exports = mock;

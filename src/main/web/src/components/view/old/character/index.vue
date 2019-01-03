<template>
  <div>
    <div v-smart-gesture="options"></div>
  </div>
</template>

<script>
  import smartGesture from 'vue-smart-gesture'

  export default {
    data () {
      return {
        // initial options
        options: {
          enablePath: true,
          timeDelay: 100,
          lineColor: '#666',
          lineWidth: 4,
          triggerMouseKey: 'right',
          activeColor: 'rgba(0, 0, 0, .05)'
        }
      }
    },
    directives: {
      smartGesture
    },
    events: {
      'smart-gesture-onswipe': function (list) {},
      'smart-gesture-ongesture': function (res, points) {}
    }
  }
</script>

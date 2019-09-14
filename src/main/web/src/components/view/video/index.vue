<template>
  <video-player  class='video-player-box'
                 ref='videoPlayer'
                 :options='playerOptions'
                 :playsinline='true'
                 customEventName='customstatechangedeventname'
                 @play='onPlayerPlay($event)'
                 @pause='onPlayerPause($event)'
                 @ended='onPlayerEnded($event)'
                 @waiting='onPlayerWaiting($event)'
                 @playing='onPlayerPlaying($event)'
                 @loadeddata='onPlayerLoadeddata($event)'
                 @timeupdate='onPlayerTimeupdate($event)'
                 @canplay='onPlayerCanplay($event)'
                 @canplaythrough='onPlayerCanplaythrough($event)'
                 @statechanged='playerStateChanged($event)'
                 @ready='playerReadied'>
  </video-player>

</template>
<script>
  export default {
    data () {
      return {
        playerOptions: {
          // videojs options
          muted: true,
          language: 'en',
          playbackRates: [0.7, 1.0, 1.5, 2.0],
          sources: [{
            type: 'video/mp4',
            src: 'http://localhost:1122/api/videos/123'
          }],
          poster: '/api/static/images/author.jpg'
        }
      }
    },
    mounted () {
      console.log('this is current player instance object', this.player)
    },
    computed: {
      player () {
        return this.$refs.videoPlayer.player
      }
    },
    methods: {
      // listen event
      onPlayerPlay (player) {
        // console.log('player play!', player)
      },
      onPlayerPause (player) {
        // console.log('player pause!', player)
      },
      // ...player event

      // or listen state event
      playerStateChanged (playerCurrentState) {
        // console.log('player current update state', playerCurrentState)
      },

      // player is ready
      playerReadied (player) {
        console.log('the player is readied', player)
        // you can use it to do something...
        // player.[methods]
      }
    }
  }
</script>
<style rel='stylesheet/less' lang='less'>
</style>

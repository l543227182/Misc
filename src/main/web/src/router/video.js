/**
 * Created by wangtongyao.victor on 2017/8/18.
 */
import ViewBase from '@/components/ViewBase'
import video from '@/components/view/video'
export default {
  path: '/upload',
  component: ViewBase,
  children: [
    {
      path: 'video',
      name: 'video',
      component: video
    }
  ]
}

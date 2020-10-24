/**
 * Created by wangtongyao.victor on 2017/8/18.
 */
import ViewBase from '@/components/ViewBase'
import upload from '@/components/view/upload'

export default {
  path: '/upload',
  component: ViewBase,
  children: [
    {
      path: 'upload',
      name: 'upload',
      component: upload
    }
  ]
}

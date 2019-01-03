/**
 * Created by wangtongyao.victor on 2017/8/18.
 */
import ViewBase from '@/components/ViewBase'
import home from '@/components/view/home'
export default {
  path: '/home',
  component: ViewBase,
  children: [
    {
      path: 'index',
      name: 'index',
      component: home
    }
  ]
}

/**
 * Created by wangtongyao.victor on 2017/8/18.
 */
import ViewBase from '@/components/ViewBase'
import test from '@/components/view/test'

export default {
  path: '/test',
  component: ViewBase,
  children: [
    {
      path: 'test',
      name: 'test',
      component: test
    }
  ]
}

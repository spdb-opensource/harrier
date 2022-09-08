// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import iView from 'iview'
import i18n from '@/locale'
import config from '@/config'
import importDirective from '@/directive'
import { directive as clickOutside } from 'v-click-outside-x'
import installPlugin from '@/plugin'
import './index.less'
import '@/assets/icons/iconfont.css'
import TreeTable from 'tree-table-vue'
import VOrgTree from 'v-org-tree'
import 'v-org-tree/dist/v-org-tree.css'
import './assets/css/monitor.css'
// import Axios from 'axios'
import axiosIns from './common/ajax.js'
import { setToken } from '@/libs/util'
import echarts from 'echarts'
// import STreeTable from '_c/s-tree-table'
// 实际打包时应该不引入mock
/* eslint-disable */
// if (process.env.NODE_ENV !== 'production') require('@/mock')
Vue.use(iView, {
  i18n: function (path, options) {
    let value = i18n.t(path, options)
    if( value !== null && value !== undefined) return value
    return ''
  }
  // i18n: (key, value) => i18n.t(key, value)
})
// Vue.use(iView)
// Vue.use(STreeTable)
Vue.use(TreeTable)
Vue.use(VOrgTree)
/**
 * @description 注册admin内置插件
 */
installPlugin(Vue)
/**
 * @description 生产环境关掉提示
 */
Vue.config.productionTip = false
/**
 * @description 全局注册应用配置
 */
Vue.prototype.$config = config
Vue.prototype.$echarts = echarts
// Vue.prototype.$ajax = Axios
Vue.prototype.$ajax = axiosIns
/**
 * 注册指令
 */
importDirective(Vue)
Vue.directive('clickOutside', clickOutside)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  i18n,
  store,
  render: h => h(App)
})

/**
 * token超时处理
 **/
axiosIns.interceptors.response.use(
  resp => {
    let data = resp.data
    if (data.code == 10006) {
    //  window.location.replace('/harrier')
      iView.Modal.error({
        title: "提示",
        onOk: () => {
          setToken('')
          window.location.replace('/harrier/')
        },
        content: '登录过期，请重新登录！！！'
      })
      return resp
    }
    if (typeof data.success !== 'undefined' && !data.success) {
      iView.Modal.error({
        title: "提示",
        content: data.msg
      })
    }
    return resp
  },
  error => error
)

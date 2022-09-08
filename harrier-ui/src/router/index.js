import Vue from 'vue'
import Router from 'vue-router'
import routes from './routers'
import store from '@/store'
import iView from 'iview'
import { setToken, getToken, canTurnTo, setTitle, localSave, localRead } from '@/libs/util'
import { loadMenu, formatMenu } from '@/libs/router-util'
import { routeindex	} from '@/api/data'
import config from '@/config'
import axiosIns from '@/common/ajax.js'
const { homeName } = config



Vue.use(Router)
const router = new Router({
  routes: [...routes, ...loadMenu()],
  mode: 'hash'
})
// const router = new Router({
//   routes,
//   mode: 'hash' // history
//   // base: '/harrier' // http://localhost:8080/harrier
// })
// export const createRouter = (routers) => new Router({
//   routes: routers,
//   mode: 'hash' // history
//   // base: '/harrier' // http://localhost:8080/harrier
// })
// const router = createRouter(routes)

const LOGIN_PAGE_NAME = 'login'
const turnTo = (to, access, next) => {
  if (canTurnTo(to.name, access, routes)) next() // 有权限，可访问
  else next({ replace: true, name: 'error_401' }) // 无权限，重定向到401页面
}

router.beforeEach((to, from, next) => {
  iView.LoadingBar.start()
  if (to.name === 'register') {
    next()
  } else if (to.path === '/resetPasswords') {
    next()
  } else {
    const token = getToken()
    const menu = localRead('route') // 读取路由数据
    if (!token && to.name !== LOGIN_PAGE_NAME) {
      // 未登录且要跳转的页面不是登录页
      next({
        name: LOGIN_PAGE_NAME // 跳转到登录页
      })
    } else if (!token && to.name === LOGIN_PAGE_NAME) {
      // 未登陆且要跳转的页面是登录页
      next() // 跳转
    } else if (token && to.name === LOGIN_PAGE_NAME) {
      // 已登录且要跳转的页面是登录页
      next({
        name: homeName // 跳转到homeName页
      })
    } else {
      if (!menu || menu.length === 0) {
        const userName = window.sessionStorage.getItem('userName')
        let params = {}
        params.userName = userName
        const config = {
          url: 'menu/getRouters',
          params: params,
          method: 'post'
        }
        axiosIns(config).then(resp => {
          var list = []
          var menuData = resp.data
          localSave('route', JSON.stringify(menuData))
          // 格式化菜单
          list = formatMenu(menuData)
          // 将404路由动态注入，防止第一次没有路由数据跳转到404,
          list.push({
            path: '*',
            name: 'error_404',
            meta: {
              hideInMenu: true
            },
            component: () => import('@/view/error-page/404.vue')
          })
          // 刷新界面菜单
          store.commit('updateMenuList', list)
          // next() 因为 router 版本的原因，现在改为下面这种方式。
          router.push({ path: homeName }).catch(err => { console.log(err) })
        })
      } else {
        next()
      }
    }
  }
})

router.afterEach(to => {
  setTitle(to, router.app)
  iView.LoadingBar.finish()
  window.scrollTo(0, 0)
})

export default router

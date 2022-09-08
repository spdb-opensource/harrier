// import { axiosIns } from "speed4j-front-commonjs";
import axiosIns from '@/common/ajax.js'
import routers from '@/router'
import Main from '@/components/main'
import store from '@/store'
// import Cookies from 'cookie'
import qs from 'qs'

var utils = {
  /**
	 * 参数格式化
	 */
  strFmt: function (str) {
    let args = arguments,
      flag = true,
      i = 1
    str = str.replace(/%s/g, function () {
      let arg = args[i++]
      if (typeof arg === 'undefined') {
        flag = false
        return ''
      }
      return arg
    })
    return flag ? str : ''
  },
  /**
	 * 格式化日期
	 * @param date 日期
	 * @param fmt 格式
	 **/
  fmtDate: function (date, fmt) {
    if (typeof date === 'string') {
      date = new Date(date)
    }
    const o = {
      'yyyy': date.getFullYear(),
      'yy': date.getFullYear().toString().substr(2),
      'MM': date.getMonth() + 1,
      'dd': date.getDate(),
      'hh24': date.getHours(),
      'hh12': date.getHours() % 12,
      'hh': date.getHours(),
      'mm': date.getMinutes(),
      'mi': date.getMinutes(),
      'ss': date.getSeconds(),
      'SSS': date.getMilliseconds()
    }
    for (let k in o) {
      fmt = fmt.replace(k, () => {
        return utils.leftPad(o[k], '0')
      })
    }
    return fmt
  },
  /**
	 * 两个日期相减 格式(yyyyMMdd) 或 (yyyyMMdd hh24:mm:ss)
	 * @param sdate 开始日期
	 * @param edate 结束日期
	 **/
  dateDiff (sdate, edate, type) {
    let s = null
    let e = null
    if (type == 'date') {
      s = new Date(sdate.substring(0, 4) + '/' + sdate.substring(4, 6) + '/' + sdate.substring(6, 8))
      e = new Date(edate.substring(0, 4) + '/' + edate.substring(4, 6) + '/' + edate.substring(6, 8))
      return parseInt((e.getTime() - s.getTime()) / 1000 / 60 / 60 / 24)
    } else if (type == 'datetime') {
      s = new Date(sdate.substring(0, 4) + '/' + sdate.substring(4, 6) + '/' + sdate.substring(6))
      e = new Date(edate.substring(0, 4) + '/' + edate.substring(4, 6) + '/' + edate.substring(6))
      return parseInt((e.getTime() - s.getTime()) / 1000 / 60)
    }
  },
  /**
	 * 左填充
	 * @param val 要填充的字符串
	 * @param char 左边要填充的字符
	 * @param len 填充后的长度，默认2
	 **/
  leftPad: function (val, char, len = 2) {
    val = String(val)
    while (val.length < len) {
      val = `${String(char)}${val}`
    }
    return val
  },
  /**
	 * href文件下载
	 * @param url
	 */
  download: function (url, params = {}) {
    const temp_params = qs.stringify(params)
    let iframe = window.document.createElement('iframe')
    iframe.style.display = 'none'
    iframe.src = `${axiosIns.defaults.baseURL}${url}?${temp_params}`
    document.body.appendChild(iframe)
    iframe.onload = function () {
      document.body.lastElementChild.remove()
    }
  },
  /**
	 * 二进制下载
	 */
  blobDownload: function (res) {
    const blob = new Blob([res.data], { type: 'application/octet-stream' })
    const fileName = res.headers['content-disposition'].substring(res.headers['content-disposition'].indexOf('attachment;filename=') + 'attachment;filename='.length)
    if ('download' in document.createElement('a')) { // 非IE下载
      const elink = document.createElement('a')
      elink.download = fileName
      elink.style.display = 'none'
      elink.href = URL.createObjectURL(blob)
      document.body.appendChild(elink)
      elink.click()
      URL.revokeObjectURL(elink.href) // 释放URL 对象
      document.body.removeChild(elink)
    } else { // IE10+下载
      navigator.msSaveBlob(blob, fileName)
    }
  },
  testDownload: function (res) {
    const blob = new Blob([res.data], { type: 'application/octet-stream' })
    let reader = new FileReader()
    reader.readAsDataURL(blob)
    reader.onload = (e) => {
      const elink = document.createElement('a')
      const fileName = 'dddd.doc'
      elink.download = fileName
      elink.style.display = 'none'
      elink.href = e.target.result
      document.body.appendChild(elink)
      elink.click()
      document.body.removeChild(elink)
    }
  },

  /**
	 * 修复Tree组件如果子节点没有全部勾选，获取不到父节点的问题
	 * @param treeCompoent 树组件
	 */
  fixChecked: function (treeCompoent) {
    const checked = treeCompoent.getCheckedNodes()
    const flatState = treeCompoent.flatState

    const needAdd = []
    for (const e of checked) {
      const node = flatState[e.nodeKey]
      if (typeof node.parent !== 'undefined') {
        if (node.node.checked && !flatState[node.parent].node.checked) {
          if (!needAdd.some(e => e.Key == flatState[node.parent].node.Key)) {
            needAdd.push(flatState[node.parent].node)
          }
        }
      }
    }
    const f = checked.concat(needAdd)
    return f
  },
  isNullOrUndefined: function (val) {
    const n = [null, undefined]
    return n.some(e => e === val)
  },
  isLogin: function () {
    const username = window.sessionStorage.getItem('username')
    // return !utils.isNullOrUndefined(username);
    return !!username
  },
  logout: function (router) {
    const config = {
      method: 'GET',
      url: '/logout'
    }
    // 该方式不走路由守卫，但是会有刷新效果
    // console.log(window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1)));
    if (window.location.pathname === '/harrier/index.html') {
      window.location.replace('/harrier/index.html')
    } else {
      window.location.replace(window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1)))
    }
    // window.location.replace('/'); //该方式不走路由守卫，但是会有刷新效果
    // router.replace('/') //该方式会经过守卫，但是无缝跳转
    utils.delCookie('menu_id')
    utils.delCookie('menu_name')
    utils.delCookie('menu_path')
    utils.delCookie('menu_pid')
    if (window.sessionStorage.length !== 0) {
      axiosIns(config)
        .then(resp => {
          utils.clearSession()
        })
    }
  },
  updateOpenTabs: function (tabs, current) {
    window.sessionStorage.setItem('opentabs', JSON.stringify(tabs))
    window.sessionStorage.setItem('currentopen', current)
  },
  getOpenStatus: function () {
    let result = {}
    try {
      result.currentOpen = window.sessionStorage.getItem('currentopen')
      result.opentabs = JSON.parse(window.sessionStorage.getItem('opentabs'))
      if (!result.opentabs || !result.opentabs) {
        result = null
      }
    } catch (error) {
      result = null
    }
    return result
  },
  clearSession: function () {
    window.sessionStorage.clear()
  },
  setCookie (name, value, days) {
    var expires = ''
    var date = new Date()
    date.setTime(date.getTime() + (0 * 24 * 60 * 60 * 1000))
    expires = '; expires=' + date.toUTCString()
    document.cookie = name + '=' + value + '; path=/'
  },
  readCookie (name) {
    var searchName = name + '='
    if (document.cookie != '') {
      var cookies = document.cookie.split(';')
      for (var i = 0; i < cookies.length; i++) {
        var c = cookies[i]
        while (c.charAt(0) == ' ') {
          c = c.substring(1, c.length)
        }
        if (c.indexOf(searchName) == 0) {
				 	return c.substring(searchName.length, c.length)
        }
      }
      return ''
    }
  },
  delCookie (name) {
    utils.setCookie(name, '', -1)
  },
  // harrier getPlatformList
  getPlatformList () {
    let loadConfig = {
      method: 'GET',
      url: '/udsSystem/listQuery'
    }
    axiosIns(loadConfig)
      .then(resp => {
        return resp
      })
  },
  // harrier getUserPlatform
  async getUserPlatform (userId) {
    let userPlatform = new Set()
    let userSystem = {}
    const httpConfig = {
      method: 'GET',
      url: `/user/${userId}/platform/permiss`
    }
    await axiosIns(httpConfig)
      .then(async resp => {
        if (resp.data) {
          for (const permiss of resp.data) {
            userPlatform.add(permiss.platform)
            let temp = new Set()
            if (userSystem[permiss.platform]) {
              temp = userSystem[permiss.platform]
            }
            if (permiss.systems === '*') {
              const systemConfig = {
                method: 'GET',
                url: '/udsSystem/selectSystem',
                params: { platform: permiss.platform }
              }
              await axiosIns(systemConfig)
                .then(resp => {
                  resp.data.forEach(data => {
                    if (permiss.platform === data.platform && data.systems !== '*') {
                      temp.add(data.systems)
                    }
                  })
                })
            } else {
              temp.add(permiss.systems)
            }
            userSystem[permiss.platform] = temp
          }
        }
        userPlatform.forEach(data => {
          let temp = userSystem[data]
          if (temp) {
            userSystem[data] = Array.from(temp)
          }
        })
        const data = {
          userPlatform: Array.from(userPlatform),
          userSystem: userSystem
        }
        store.dispatch('setUserSystem', data)
      })
  },
  // harrier menuToRouter
  menuToRouter (menuList) {
    let routerList = []
    for (const menu of menuList) {
      if (menu.parentMenuId === 0) {

      }
    }
  },

  setRouter () {
    let routerList = {
      path: '/workflowTest',
      name: 'workflowTest',
      meta: {
      //  icon: 'md-cloud-upload',
        title: 'TestaddRouter'
      },
      component: Main,
      children: [
        {
          path: 'workflowmanageTest',
          name: 'workflowmanageTest',
          meta: {
          //  icon: 'ios-document',
            title: '工作流Test'
          },
          component: () => import('@/view/app/workflow/workflowmanage/Index.vue')
        },
        {
          path: 'newworkflowTest',
          name: 'newworkflowTest',
          meta: {
          //  icon: 'ios-document',
            title: '创建工作流Test'
          },
          component: () => import('@/view/app/workflow/newworkflow/Index.vue')
        },
        {
          path: 'updateworkflowTest',
          name: 'updateworkflowTest',
          meta: {
          //  icon: 'ios-document',
            title: '变更工作流Test'
          },
          component: () => import('@/view/app/workflow/updateworkflow/Index.vue')
        }
      ]
    }
    routers.addRoute(routerList)
  },
  /**
   * 数组排序
   * @param {*} attr 对象属性
   * @param {*} rev 默认升序，true升序，false降序
   * arr.sort(compare('age',false))
   */
  compare (attr, rev) {
    if (rev === undefined) {
      rev = 1
    } else {
      rev = (rev) ? 1 : -1
    }
    return function (a, b) {
      a = a[attr]
      b = b[attr]
      if (a < b) {
        return rev * -1
      }
      if (a > b) {
        return rev * 1
      }
      return 0
    }
  }
}

export default utils

import axios from 'axios'
import qs from 'qs'
import env from '../config'
import Cookies from 'js-cookie'
import Vue from 'vue'

let axiosIns = axios.create()
axiosIns.defaults.baseURL = env.baseUrl.dev
// 添加请求拦截器
axiosIns.interceptors.request.use(
  config => {
    config.timeout = 30000
    config.withCredentials = true
    if (['post', 'put'].includes(config.method.trim().toLowerCase())) {
      if (config.contentType && config.contentType === 'json') {
        config.headers['Content-Type'] = 'application/json'
        config.data = JSON.stringify(config.data)
      } else if (config.data && config.data.toString() !== '[object FormData]') {
        config.headers['Content-Type'] = 'application/x-www-form-urlencoded'
        config.data = qs.stringify(config.data, { arrayFormat: 'brackets' })
      }
    // } else {
    //   config.headers['Content-Type'] = 'application/json'
    //   config.data = JSON.stringify(config.data)
    }
    const token = Cookies.get('token')
    if (token) {
      config.headers['token'] = token
    }
    return config
  },
  error => {
    // 对请求错误做些什么
    console.log('请求错误')
    return Promise.reject(error)
  }
)
export default axiosIns

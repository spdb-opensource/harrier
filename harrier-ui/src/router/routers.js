import Main from '@/components/main'
import parentView from '@/components/parent-view'

/**
 * iview-admin中meta除了原生参数外可配置的参数:
 * meta: {
 *  title: { String|Number|Function }
 *         显示在侧边栏、面包屑和标签栏的文字
 *         使用'{{ 多语言字段 }}'形式结合多语言使用，例子看多语言的路由配置;
 *         可以传入一个回调函数，参数是当前路由对象，例子看动态路由和带参路由
 *  hideInBread: (false) 设为true后此级路由将不会出现在面包屑中，示例看QQ群路由配置
 *  hideInMenu: (false) 设为true后在左侧菜单不会显示该页面选项
 *  notCache: (false) 设为true后页面在切换标签后不会缓存，如果需要缓存，无需设置这个字段，而且需要设置页面组件name属性和路由配置的name一致
 *  access: (null) 可访问该页面的权限数组，当前路由设置的权限会影响子路由
 *  icon: (-) 该页面在左侧菜单、面包屑和标签导航处显示的图标，如果是自定义图标，需要在图标名称前加下划线'_'
 *  beforeCloseName: (-) 设置该字段，则在关闭当前tab页时会去'@/router/before-close.js'里寻找该字段名对应的方法，作为关闭前的钩子函数
 * }
 */
// 权限管理
const sysUser = resolve => require(['@/view/spdb/authority/user/Index'], resolve)
const sysRole = resolve => require(['@/view/spdb/authority/role/Index'], resolve)
const sysMenu = resolve => require(['@/view/spdb/authority/menu/Index'], resolve)

// 系统管理
const jobWeight = resolve => require(['@/view/spdb/systemsetting/jobWeight/Index.vue'], resolve)
const weightType = resolve => require(['@/view/spdb/systemsetting/weightType/Index.vue'], resolve)
const platformConfig = resolve => require(['@/view/spdb/systemsetting/platformConfig/Index.vue'], resolve)

const systemsettingmenus = [
  // 系统管理
  { path: 'user', name: 'user', meta: { title: '用户管理' }, component: sysUser },
  { path: 'role', name: 'role', meta: { title: '角色管理' }, component: sysRole },
  { path: 'menu', name: 'menu', meta: { title: '菜单管理' }, component: sysMenu },
  { path: 'sysConfig', name: 'sysConfig', meta: { title: '系统设置' } },
  { path: 'weightType', name: 'weightType', meta: { title: '资源类型管理' }, component: weightType },
  { path: 'jobWeight', name: 'jobWeight', meta: { title: '作业资源管理' }, component: jobWeight },
  { path: 'operatLog', name: 'operatLog', meta: { title: '操作日志' } },
  { path: 'platformConfig', name: 'platformConfig', meta: { title: '平台管理' }, component: platformConfig }
]

export default [
  {
    path: '/login',
    name: 'login',
    meta: {
      title: 'Login - 登录',
      hideInMenu: true
    },
    component: () => import('@/view/login/login.vue')
  },
  {
    path: '/register',
    name: 'register',
    meta: { title: '用户注册', hideInMenu: true },
    component: register => import('@/view/login/Register.vue')
  },
  {
    path: '/',
    redirect: '/login',
    name: 'harrier',
    meta: {
      title: 'harrier',
      hideInMenu: true
    }
  },
  {
    path: '/',
    name: '_home',
    redirect: '/home',
    component: Main,
    meta: {
      hideInMenu: true,
      notCache: true
    },
    children: [
      {
        path: '/home',
        name: 'home',
        meta: {
          hideInMenu: true,
          title: '首页',
          notCache: true,
          icon: 'md-home'
        },
        component: () => import('@/view/single-page/home')
      }
    ]
  },
  {
    path: '/job',
    name: 'job',
    meta: {
    //  icon: 'md-cloud-upload',
      title: '作业管理'
    },
    component: Main,
    children: [
      {
        path: 'jobList',
        name: 'jobList',
        meta: {
        //  icon: 'ios-document',
          title: '作业列表'
        },
        component: () => import('@/view/spdb/job/jobList/Index.vue')
      },
      {
        path: 'addJob',
        name: 'addJob',
        meta: {
        //  icon: 'md-clipboard',
          title: '新增作业'
        }
      },
      // {
      //   path: 'jobRecord',
      //   name: 'jobRecord',
      //   meta: {
      //   //  icon: 'md-clipboard',
      //     title: '作业记录查询'
      //   }
      // },
      // {
      //   path: 'addComplement',
      //   name: 'addComplement',
      //   meta: {
      //   //  icon: 'md-clipboard',
      //     title: '作业补数管理'
      //   }
      // },
      {
        path: 'sqlQuery',
        name: 'sqlQuery',
        meta: {
        //  icon: 'md-clipboard',
          title: '自定义sql查询'
        },
        component: () => import('@/view/spdb/job/sqlQuery/Index.vue')
      }
    ]
  },
  {
    path: '/alarm',
    name: 'alarm',
    meta: {
    //  icon: 'md-cloud-upload',
      title: '告警管理'
    },
    component: Main,
    children: [
      {
        path: 'opjobsummary',
        name: 'opjobsummary',
        meta: {
        //  icon: 'md-clipboard',
          title: '作业运行监控'
        },
        component: () => import('@/view/app/alarm/opjobsummary/Index.vue')
      },
      {
        path: 'alarmConfig',
        name: 'alarmConfig',
        meta: {
        //  icon: 'ios-document',
          title: '告警规则配置'
        },
        component: () => import('@/view/app/alarm/alarmmsg/Index.vue')
      },
      {
        path: 'alarmPlatformConfig',
        name: 'alarmPlatformConfig',
        meta: {
        //  icon: 'md-clipboard',
          title: '平台告警配置'
        },
        component: () => import('@/view/app/alarm/alarmconfig/Index.vue')
      },
      {
        path: 'alarmJobConfig',
        name: 'alarmJobConfig',
        meta: {
        //  icon: 'md-clipboard',
          title: '作业告警配置'
        },
        component: () => import('@/view/app/alarm/alarmjobconfig/Index.vue')
      },
      {
        path: 'alarmSendConfig',
        name: 'alarmSendConfig',
        meta: {
        //  icon: 'md-clipboard',
          title: '邮件短信配置'
        },
        component: () => import('@/view/app/alarm/alarmsendqueue/Index.vue')
      },
      {
        path: 'alarmUserGroup',
        name: 'alarmUserGroup',
        meta: {
        //  icon: 'md-clipboard',
          title: '告警联系组配置'
        },
        component: () => import('@/view/app/alarm/alarmusergroup/Index.vue')
      }
    ]
  },
  {
    path: '/workflow',
    name: 'workflow',
    meta: {
    //  icon: 'md-cloud-upload',
      title: '工作流管理'
    },
    component: Main,
    children: [
      {
        path: 'workflowmanage',
        name: 'workflowmanage',
        meta: {
        //  icon: 'ios-document',
          title: '工作流'
        },
        component: () => import('@/view/app/workflow/workflowmanage/Index.vue')
      },
      {
        path: 'newworkflow',
        name: 'newworkflow',
        meta: {
        //  icon: 'ios-document',
          title: '创建工作流'
        },
        component: () => import('@/view/app/workflow/newworkflow/Index.vue')
      },
      {
        path: 'updateworkflow',
        name: 'updateworkflow',
        meta: {
        //  icon: 'ios-document',
          title: '变更工作流'
        },
        component: () => import('@/view/app/workflow/updateworkflow/Index.vue')
      }
    ]
  },
  {
    path: '/serverConfig',
    name: 'serverConfig',
    meta: {
    //  icon: 'md-cloud-upload',
      title: '节点管理'
    },
    component: Main,
    children: [
      {
        path: 'serverManage',
        name: 'serverManage',
        meta: {
        //  icon: 'ios-document',
          title: '节点管理'
        }
      },
      {
        path: 'fileManage',
        name: 'fileManage',
        meta: {
        //  icon: 'md-clipboard',
          title: '文件管理'
        }
      }
    ]
  },
  {
    path: '/systemsetting',
    name: 'systemsetting',
    title: '系统管理',
    component: Main,
    meta: { title: '系统管理' },
    children: systemsettingmenus

  },
  {
    path: '/argu',
    name: 'argu',
    meta: {
      hideInMenu: true
    },
    component: Main,
    children: [
      {
        path: 'params/:id',
        name: 'params',
        meta: {
          icon: 'md-flower',
          title: route => `{{ params }}-${route.params.id}`,
          notCache: true,
          beforeCloseName: 'before_close_normal'
        },
        component: () => import('@/view/argu-page/params.vue')
      },
      {
        path: 'query',
        name: 'query',
        meta: {
          icon: 'md-flower',
          title: route => `{{ query }}-${route.query.id}`,
          notCache: true
        },
        component: () => import('@/view/argu-page/query.vue')
      }
    ]
  },
  {
    path: '/401',
    name: 'error_401',
    meta: {
      hideInMenu: true
    },
    component: () => import('@/view/error-page/401.vue')
  },
  {
    path: '/500',
    name: 'error_500',
    meta: {
      hideInMenu: true
    },
    component: () => import('@/view/error-page/500.vue')
  },
  {
    path: '*',
    name: 'error_404',
    meta: {
      hideInMenu: true
    },
    component: () => import('@/view/error-page/404.vue')
  }
]

<template>
  <div>
    <div class="spdb-form">
      <Form :label-width="80">
        <Row>
          <Col span="6">
            <FormItem label="用户名">
              <Input v-model="searchBean.userCode"/>
            </FormItem>
          </Col>
          <Col span="6">
            <FormItem label="用户状态">
              <Select filterable v-model="searchBean.isEnable">
                <Option v-for="item in enums.state" :key="item.key" :value="item.key">{{item.val}}</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
      </Form>
    </div>

    <div class="spdb-toolbar">
      <Button type="primary" icon="ios-search" @click="demand()">搜索</Button>
      <Button type="primary" icon="md-close" @click="clear()">清空查询</Button>
      <Button type="primary" icon="md-add" @click="add()">新增</Button>
      <Button type="primary" :loading="loadingdel" :disabled="ctrlDisable" icon="ios-trash-outline" @click="del()">删除</Button>
      <Button type="primary" :loading="loadingactive" :disabled="ctrlDisable" icon="ios-play" @click="active()">启用</Button>
      <Button type="primary" :loading="loadinginactive" :disabled="ctrlDisable" icon="ios-pause" @click="inactive()">停用</Button>
      <Button type="primary" :loading="loadingreset" :disabled="ctrlDisable" icon="md-refresh" @click="reset()">重置密码</Button>
    </div>

    <div class="spdb-table">
      <Table :loading="loading" :columns="gridTitles" :data="gridData" @on-selection-change="select"></Table>
    </div>

    <div class="spdb-page">
      <Page placement="top" :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat @on-change="changePage" @on-page-size-change="changePageSize"></Page>
    </div>
    <Modal
    v-model="roleDlg.show"
    title="分配角色"
    @on-ok="roleOk">
      <div>
        <div>
          <Form :label-width="80" inline>
            <FormItem label="用户名称">
              <Input readonly v-model="roleDlg.userCode"/>
            </FormItem>
          </Form>
        </div>
        <div>
          <Transfer
          :data="roleDlg.roleData"
          :target-keys="roleDlg.targetData"
          :titles="['所有角色', '当前角色']"
          :filter-method="filterRole"
          filterable
          @on-change="handleChange"></Transfer>
        </div>
      </div>
    </Modal>
    <Modal
      v-model="platformDlg.show"
      title="分配平台"
      width="600px"
      @on-ok="platformOk">
      <div>
        <div>
          <Form :label-width="80" inline>
            <FormItem label="用户名称">
              <Input readonly v-model="roleDlg.userCode"/>
            </FormItem>
          </Form>
        </div>
        <div>
        <Transfer
        :data="platformDlg.platformData"
        :target-keys="platformDlg.targetData"
        :titles="['所有平台', '当前角色资源']"
        :list-style="resourceStyle"
        :filter-method="filterResource"
        :render-format="renderResource"
        filterable
        @on-change="platformHandleChange"></Transfer>
        </div>
      </div>
    </Modal>
    <Modal
      v-model="systemDlg.show"
      title="分配平台"
      width="600px"
      @on-ok="systemOk">
      <div>
        <div>
          <Form :label-width="80" inline>
            <FormItem label="用户名称">
              <Input readonly v-model="roleDlg.userCode"/>
            </FormItem>
          </Form>
        </div>
        <div>
          <Tree ref="systemTree" @on-check-change="checkChange" :render="renderContent" :data="systemDlg.systemTreeData" show-checkbox></Tree>
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
import common from '@/mixins/common'
import store from '@/store/index'
import utils from '@/common/utils'
const enums = {
  state: [
    {
      key: '1',
      val: '启用'
    },
    {
      key: '0',
      val: '停用'
    }
  ]
}

export default {
  props: {
    transData: {
      type: Object,
      default: function () {
        return {}
      }
    }
  },
  mixins: [ common ],
  data: function () {
    return {
      cUserData: {
        platformData: [],
        systemData: [],
        roleData: {}
      },
      searchBean: {},
      loadingdel: false,
      loadingactive: false,
      loadinginactive: false,
      loadingreset: false,
      platformDlg: {
        show: false,
        platformData: [],
        targetData: []
      },
      systemDlg: {
        show: false,
        systemData: [],
        targetData: []
      },
      gridTitles: [
        /* {
        type: 'index',
        title: ' ',
        width: 80,
        align: 'center'
        }, */
        {
          type: 'selection',
          width: 60,
          align: 'center'
        },
        {
          title: '用户名',
          key: 'userCode'
        },
        {
          title: '中文名',
          key: 'userName'
        },
        {
          title: '工号',
          key: 'empId'
        },
        {
          title: '状态',
          key: 'isEnable',
          render: (h, params) => {
            const status = { true: '启用', false: '停用' }
            return h('span', {}, status[params.row.isEnable])
          }
        },
        {
          title: '操作',
          width: 320,
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'info',
                  size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    const userId = params.row.userId
                    this.checkAuth('update', userId)
                  }
                }
              }, '修改'),
              h('Button', {
                props: {
                  type: 'info',
                  size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.roleDlg.userId = params.row.userId
                    this.roleDlg.userCode = params.row.userCode
                    this.showRoleDlg()
                  }
                }
              }, '分配角色'),
              // h('Button', {
              //   props: {
              //     size: 'small',
              //     type: 'info'
              //   },
              //   style: {
              //     marginRight: '5px'
              //   },
              //   on: {
              //     click: () => {
              //       // this.currentSelect = {roleId: params.row.role_id, roleName: params.row.role_chname};
              //       this.roleDlg.userId = params.row.userId
              //       this.roleDlg.userCode = params.row.userCode
              //       this.showPlatformDlg()
              //     }
              //   }
              // }, '分配平台'),
              h('Button', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    // this.currentSelect = {roleId: params.row.role_id, roleName: params.row.role_chname};
                    this.roleDlg.userId = params.row.userId
                    this.roleDlg.userCode = params.row.userCode
                    this.showSystemDlg()
                  }
                }
              }, '分配平台')
            ])
          }
        }
      ],
      gridData: [],
      enums: enums,
      roleDlg: {
        show: false,
        userId: '',
        userCode: '',
        roleData: [],
        targetData: []
      }
    }
  },
  methods: {
    init: function () {
      if (this.transData.currentPage) {
        this.searchBean = this.transData.searchBean
        this.page.current = this.transData.currentPage
        this.page.size = this.transData.pageSize
      }

      this.search()
    },
    search: function () {
      this.selection = []
      let params = Object.assign({}, this.searchBean)
      if (!params.userCode || params.userCode === '') {
        delete params.userCode
      }
      params.current = this.page.current
      params.size = this.page.size

      let httpConfig = {
        method: 'GET',
        url: '/user',
        params: params
      }
      this.$ajax(httpConfig)
        .then(resp => {
          this.gridData = resp.data.records
          this.page.total = resp.data.total
        })
        .then(resp => {
          this.loading = false
        })
        .catch(error => {
          this.loading = false
          console.log(error)
        })
    },
    demand () {
      this.page.current = 1
      this.search()
    },
    clear: function () {
      this.searchBean = {}
      this.selection = []
      this.page = {
        current: 1,
        size: 10
      }
    },
    add: function () {
      this.$emit('exit', null)
    },
    del: function () {
      const userIds = Array.from(this.selection, e => e.userId)
      let deleteConfig = {
        method: 'DELETE',
        url: `/user/${userIds}/delete`,
        data: {}
      }
      let authSystems = 'ROLE_ADMIN' // store.getters.getSystems
      if (authSystems.indexOf('ROLE_ADMIN') == -1) {
        if (userIds.length > 1) {
          this.$Message.warning('请选择单个用户，仅系统管理员支持批量操作')
        } else {
          const msconfig = {
            url: 'resource/metasystem',
            method: 'GET',
            params: { userId: userIds[0] }
          }
          this.$ajax(msconfig)
            .then(res => {
              if (res.data) {
                for (let i = 0; i < res.data.length; i++) {
                  if (authSystems.indexOf(res.data[i]) == -1 && res.data[i] != 'nullnull') {
                    this.$Message.warning('该用户具有其他平台应用的权限，仅系统管理员可以操作该用户')
                    return
                  }
                }
                let param = authSystems.find(item => { if (item.indexOf('*') > -1) { return item } })
                deleteConfig.data.authps = param
                this.loadingdel = true
                this.$ajax(deleteConfig)
                  .then(resp => {
                    this.loadingdel = false
                    this.search()
                  })
              }
            })
        }
      } else {
        this.loadingdel = true
        this.$ajax(deleteConfig)
          .then(resp => {
            this.loadingdel = false
            this.search()
          })
      }
    },
    active: function () {
      const userIds = Array.from(this.selection, e => e.userId)
      let params = { userIds: userIds.toString() }
      const activeConfig = {
        method: 'POST',
        url: `/user/${userIds}/active`,
        data: params
      }
      let authSystems = 'ROLE_ADMIN' // store.getters.getSystems
      if (authSystems.indexOf('ROLE_ADMIN') == -1) {
        if (userIds.length > 1) {
          this.$Message.warning('请选择单个用户，仅系统管理员支持批量操作')
        } else {
          const msconfig = {
            url: 'resource/metasystem',
            method: 'GET',
            params: { userId: userIds[0] }
          }
          this.$ajax(msconfig)
            .then(res => {
              if (res.data) {
                for (let i = 0; i < res.data.length; i++) {
                  if (authSystems.indexOf(res.data[i]) == -1 && res.data[i] != 'nullnull') {
                    this.$Message.warning('该用户具有其他平台应用的权限，仅系统管理员可以操作该用户')
                    return
                  }
                }
                let param = authSystems.find(item => { if (item.indexOf('*') > -1) { return item } })
                activeConfig.data.authps = param
                this.loadingactive = true
                this.$ajax(activeConfig)
                  .then(resp => {
                    this.loadingactive = false
                    this.search()
                  })
              }
            })
        }
      } else {
        this.loadingactive = true
        this.$ajax(activeConfig)
          .then(resp => {
            this.loadingactive = false
            this.search()
          })
      }
    },
    inactive: function () {
      const userIds = Array.from(this.selection, e => e.userId)
      let params = { userIds: userIds.toString() }
      const inactiveConfig = {
        method: 'POST',
        url: `/user/${userIds}/inactive`,
        data: params
      }
      let authSystems = 'ROLE_ADMIN' // store.getters.getSystems
      if (authSystems.indexOf('ROLE_ADMIN') == -1) {
        if (userIds.length > 1) {
          this.$Message.warning('请选择单个用户，仅系统管理员支持批量操作')
        } else {
          const msconfig = {
            url: 'resource/metasystem',
            method: 'GET',
            params: { userId: userIds[0] }
          }
          this.$ajax(msconfig)
            .then(res => {
              if (res.data) {
                for (let i = 0; i < res.data.length; i++) {
                  if (authSystems.indexOf(res.data[i]) == -1 && res.data[i] != 'nullnull') {
                    this.$Message.warning('该用户具有其他平台应用的权限，仅系统管理员可以操作该用户')
                    return
                  }
                }
                let param = authSystems.find(item => { if (item.indexOf('*') > -1) { return item } })
                inactiveConfig.data.authps = param
                this.loadinginactive = true
                this.$ajax(inactiveConfig)
                  .then(resp => {
                    this.loadinginactive = false
                    this.search()
                  })
              }
            })
        }
      } else {
        this.loadinginactive = true
        this.$ajax(inactiveConfig)
          .then(resp => {
            this.loadinginactive = false
            this.search()
          })
      }
    },
    reset: function () {
      const userIds = Array.from(this.selection, e => e.userId)
      let params = { userIds: userIds.toString() }
      const resetConfig = {
        method: 'POST',
        url: `/user/${userIds}/reset`,
        data: params
      }
      let authSystems = 'ROLE_ADMIN' // store.getters.getSystems
      if (authSystems.indexOf('ROLE_ADMIN') == -1) {
        if (userIds.length > 1) {
          this.$Message.warning('请选择单个用户，仅系统管理员支持批量操作')
        } else {
          const msconfig = {
            url: 'resource/metasystem',
            method: 'GET',
            params: { userId: userIds[0] }
          }
          this.$ajax(msconfig)
            .then(res => {
              if (res.data) {
                for (let i = 0; i < res.data.length; i++) {
                  if (authSystems.indexOf(res.data[i]) == -1 && res.data[i] != 'nullnull') {
                    this.$Message.warning('该用户具有其他平台应用的权限，仅系统管理员可以操作该用户')
                    return
                  }
                }
                let param = authSystems.find(item => { if (item.indexOf('*') > -1) { return item } })
                resetConfig.data.authps = param
                this.loadingreset = true
                this.$ajax(resetConfig)
                  .then(resp => {
                    this.loadingreset = false
                    this.search()
                  })
              }
            })
        }
      } else {
        this.loadingreset = true
        this.$ajax(resetConfig)
          .then(resp => {
            this.loadingreset = false
            this.search()
          })
      }
    },
    checkAuth (opName, userId) {
      let authSystems = 'ROLE_ADMIN' // store.getters.getSystems
      if (authSystems.indexOf('ROLE_ADMIN') == -1) {
        const msconfig = {
          url: 'resource/metasystem',
          method: 'GET',
          params: { userId: userId }
        }
        this.$ajax(msconfig)
          .then(res => {
            if (res.data) {
              for (let i = 0; i < res.data.length; i++) {
                if (authSystems.indexOf(res.data[i]) == -1 && res.data[i] != 'nullnull') {
                  this.$Message.warning('该用户具有其他平台应用的权限，仅系统管理员可以操作该用户')
                  return
                }
              }
              if (opName == 'showRoleDlg') {
                this.showRoleDlgReal()
              } else if (opName == 'showPlatformDlg') {
                this.showPlatformDlgReal()
              } else if (opName == 'showSystemDlg') {
                this.showSystemDlgReal()
              } else if (opName == 'update') {
                let queryCache = { searchBean: this.searchBean, currentPage: this.page.current, pageSize: this.page.size }
                this.$emit('exit', Object.assign({ id: userId }, queryCache))
              }
            }
          })
      } else {
        if (opName == 'showRoleDlg') {
          this.showRoleDlgReal()
        } else if (opName == 'showPlatformDlg') {
          this.showPlatformDlgReal()
        } else if (opName == 'showSystemDlg') {
          this.showSystemDlgReal()
        } else if (opName == 'update') {
          let queryCache = { searchBean: this.searchBean, currentPage: this.page.current, pageSize: this.page.size }
          this.$emit('exit', Object.assign({ id: userId }, queryCache))
        }
      }
    },
    showRoleDlg: function () {
      this.checkAuth('showRoleDlg', this.roleDlg.userId)
    },
    showPlatformDlg: function () {
      this.checkAuth('showPlatformDlg', this.roleDlg.userId)
    },
    showSystemDlg: function () {
      this.checkAuth('showSystemDlg', this.roleDlg.userId)
    },
    showRoleDlgReal: function () {
      this.roleDlg.show = true
      this.roleDlg.roleData = []
      this.roleDlg.targetData = []
      // 初始化穿梭框
      const allRoleConfig = {
        method: 'GET',
        url: '/role',
        params: { currentPage: 1, pageSize: 999 }
      }
      this.$ajax(allRoleConfig)
        .then(resp => {
          // 获取该用户该有的角色
          const rolesConfig = {
            method: 'GET', url: `/role/${this.roleDlg.userCode}/list`
          }
          this.$ajax(rolesConfig)
            .then(res => {
              this.roleDlg.targetData = Array.from(res.data, e => e.roleId)

              if (resp.data) {
                resp.data.records.forEach(role => {
                  let temp = {}
                  temp.key = role.roleId
                  temp.label = role.roleName
                  temp.description = role.roleName
                  this.roleDlg.roleData.push(temp)
                })
              }
            })
        })
    },
    showPlatformDlgReal: function () {
      this.platformDlg = {
        show: true,
        platformData: [],
        targetData: []
      }
      // 初始化穿梭框
      const allResConfig = {
        method: 'GET',
        url: '/udsSystem/listQuery'
        // params: { currentPage: 1, pageSize: 999 }
      }
      this.$ajax(allResConfig)
        .then(resp => {
          let curdata = []
          let uobj = {}
          if (resp.data) {
            let sdata = resp.data
            for (let i = 0; i < sdata.length; i++) {
              if (uobj.hasOwnProperty(sdata[i].platform)) {
              } else {
                uobj[sdata[i].platform] = '1'
                curdata.push(sdata[i])
              }
            }
          }
          // 获取该角色该有的资源
          const roleResConfig = {
            method: 'GET',
            url: `/user/${this.roleDlg.userId}/platform/permiss`
          }
          this.$ajax(roleResConfig)
            .then(res => {
              this.platformDlg.targetData = Array.from(res.data, e => e.id)
              let authSystems = 'ROLE_ADMIN' // store.getters.getSystems
              if (authSystems.indexOf('ROLE_ADMIN') == -1) {
                this.platformDlg.platformData = this.cUserData.platformData
              } else {
                if (resp.data) {
                  curdata.forEach(platform => {
                    let temp = {}
                    temp.key = platform.id
                    temp.method = ''
                    temp.label = platform.etlPlatform
                    // temp.description = resource.remark;

                    this.platformDlg.platformData.push(temp)
                  })
                }
              }
            })
        })
    },
    showSystemDlgReal: function () {
      this.systemDlg = {
        show: true,
        systemTreeData: []
      }
      // 初始化应用
      const systemConfig = { method: 'GET', url: '/udsSystem/listQuery' }
      this.$ajax(systemConfig)
        .then(resp => {
          const userBySystemConfig = { method: 'GET', url: `/user/${this.roleDlg.userId}/platform/permiss` }
          this.$ajax(userBySystemConfig)
            .then(res => {
              let tree = getSystemTree(resp.data, res.data)
              this.systemDlg.systemTreeData = tree.systems // 把处理好的数据赋值给组件
            })
        })
      // 转换树形结构
      function getSystemTree (udsSystems, checkedSystems) {
        const systemMsgs = Array.from(checkedSystems, e => e.systems === '*' ? e.platform : e.platform + '-' + e.systems)
        let result = { systems: [], checked: true }
        udsSystems.forEach(udsSystem => {
          if (udsSystem.systems === '*') {
            let temp = {}
            temp.Key = udsSystem.id
            temp.title = udsSystem.platform
            temp.expand = true
            // const t = getSystemTree(menu.children, checkedMenus)
            // temp.children = t.menus
            // temp.checked = (checkedSystems.includes(temp.title) && temp.children.every(e => e.checked))
            temp.checked = systemMsgs.includes(temp.title)
            temp.children = []
            // 权限
            temp.permissions = ''
            let sysPermissions = checkedSystems.find(item => item.platform === udsSystem.platform && item.systems === udsSystem.systems)
            if (sysPermissions && sysPermissions.permissions) {
              temp.permissions = sysPermissions.permissions
            }
            result.systems.push(temp)
          }
        })
        udsSystems.forEach(udsSystem => {
          if (udsSystem.systems !== '*') {
            let temp = {}
            temp.Key = udsSystem.id
            temp.title = udsSystem.platform + '-' + udsSystem.systems
            temp.expand = true
            temp.checked = systemMsgs.includes(temp.title)
            // 权限
            temp.permissions = ''
            let sysPermissions = checkedSystems.find(item => item.platform === udsSystem.platform && item.systems === udsSystem.systems)
            if (sysPermissions && sysPermissions.permissions) {
              temp.permissions = sysPermissions.permissions
            }
            let platform = result.systems.find(item => item.title === udsSystem.platform)
            if (platform.permissions && (platform.permissions === 'W' || temp.permissions === '')) {
              temp.permissions = platform.permissions
            }
            platform.children.push(temp)
          }
        })
        return result
      }
    },
    renderContent (h, { root, node, data }) {
      return h('span', {
        style: {
          display: 'inline-block',
          width: '100%'
        }
      }, [
        h('span', [
          h('span', data.title)
        ]),
        h('span', {
          style: {
            display: 'inline-block',
            float: 'right',
            marginRight: '32px'
          }
        }, [
          h('RadioGroup', {
            props: {
              value: data.permissions,
              label: '读/写'
            },
            style: {
              width: '100px'
            },
            on: {
              'on-change': (val) => {
                data.permissions = val
              }
            }
          },
          [
            h('Radio', {
              props: {
                // value: 'R',
                // label: '读'
                label: 'R'
              },
              style: {
                width: '34px'
              }
            }, '读'),
            h('Radio', {
              props: {
                // value: 'W',
                // label: '写'
                label: 'W'
              },
              style: {
                width: '14px'
              }
            }, '写')
          ])
        ])
      ])
    },
    checkChange: function (data, checkData) {
      if (checkData.checked) {
        if (checkData.permissions && checkData.permissions !== '') {
          if (checkData.children) {
            checkData.children.forEach(children => {
              children.permissions = checkData.permissions
            })
          }
        } else {
          checkData.permissions = 'R'
          if (checkData.children) {
            checkData.children.forEach(children => {
              children.permissions = 'R'
            })
          }
        }
      } else {
        checkData.permissions = ''
        if (checkData.children) {
          checkData.children.forEach(children => {
            children.permissions = ''
          })
        }
      }
    },
    recusiveNode: function (needAdd, node, flatState) {
      if (typeof node.parent === 'undefined') {

      } else {
        for (const t of needAdd) {
          if (t.nodeKey === node.parent) {
            return
          }
        }
        needAdd.push(flatState[node.parent].node)
        this.recusiveNode(needAdd, flatState[node.parent], flatState)
      }
    },
    roleOk: function () {
      const roleIds = this.roleDlg.targetData
      const addRoles = {
        method: 'POST',
        url: `/user/${this.roleDlg.userId}/role`,
        data: { 'userId': this.roleDlg.userId, 'roleIds': roleIds.toString() }
      }
      this.$ajax(addRoles)
    },
    platformOk: function () {
      const platformIds = this.platformDlg.targetData
      let authSystems = 'ROLE_ADMIN' // store.getters.getSystems
      let param = authSystems.find(item => { if (item.indexOf('*') > -1) { return item } })
      const addResources = {
        method: 'POST',
        url: `/user/${this.roleDlg.userId}/platform`,
        data: { 'platformIds': platformIds.toString(), 'authps': param }
      }
      this.$ajax(addResources)
    },
    systemOk: function () {
      let checkeds = this.$refs['systemTree'].getCheckedNodes()
      if (!checkeds || checkeds.length === 0) {
        this.$Modal.error({
          title: '提示',
          content: '未选择平台！！'
        })
        return
      }
      // let flatState = this.$refs['systemTree'].flatState
      // for (const t of checkeds) {
      //   this.recusiveNode(checkeds, flatState[t.nodeKey], flatState)
      // }
      // const systemIds = Array.from(checkeds, e => e.Key)
      let permissList = []
      for (const t of checkeds) {
        let userSystems = {}
        userSystems.userId = this.roleDlg.userId
        userSystems.systemsId = t.Key
        if (t.permissions === undefined || t.permissions === '') {
          t.permissions = 'R'
        }
        userSystems.permissions = t.permissions
        permissList.push(userSystems)
      }
      const addPermiss = {
        method: 'POST',
        url: `/user/${this.roleDlg.userId}/allot/permiss`,
        data: permissList,
        contentType: 'json'
      }
      this.$ajax(addPermiss)
        .then(resp => {
          if (resp.status && resp.status === 200) {
            if (this.roleDlg.userId === this.$store.getters.getUserId) {
              utils.getUserPlatform(this.$store.getters.getUserId)
            }
          }
        })
    },
    filterRole: function (data, query) {
      return data.label.indexOf(query) > -1
    },
    handleChange: function (newTarget) {
      this.roleDlg.targetData = newTarget
    },
    filterResource: function (data, query) {
      return data.label.indexOf(query) > -1
    },
    renderResource: function (item) {
      return `<span class="resource-label">${item.label}</span>
					<span class="resource-method">${item.method}</span>`
    },
    platformHandleChange: function (newTarget) {
      this.platformDlg.targetData = newTarget
    },
    systemHandleChange: function (newTarget) {
      this.systemDlg.targetData = newTarget
    }
  },
  mounted () {
    //
  },
  computed: {
    resourceStyle () {
      return {
        width: '250px',
        height: '300px'
      }
    },
    systemDlgNew () {
      if (this.systemDlg.systemTreeData) {
        return JSON.parse(JSON.stringify(this.systemDlg.systemTreeData))
      }
    }
  },
  watch: {
    systemDlgNew: {
      handler (newVal, oldVal) {
        for (let i = 0; i < newVal.length; i++) {
          if (oldVal[i] && newVal[i].permissions && oldVal[i].permissions && newVal[i].permissions !== oldVal[i].permissions) {
            this.systemDlg.systemTreeData[i].children.forEach(children => {
              if (children.checked) {
                children.permissions = newVal[i].permissions
              }
            })
          }
        }
      },
      deep: true
    }
  }
}
</script>
<style>
/* .spdb-toolbar .i1
{
 position: relative;
 top: 5px;
 left: 0px;
} */

.resource-method {
	float: right;
	color: green;
}
</style>

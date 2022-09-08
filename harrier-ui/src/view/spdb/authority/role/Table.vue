<template>
  <div>
    <div class="spdb-form">
      <Form :label-width="100">
        <Row>
          <Col span="6">
            <FormItem label="角色名称">
              <Input v-model="searchBean.roleName"/>
            </FormItem>
          </Col>
        </Row>
      </Form>
    </div>

    <div class="spdb-toolbar">
      <Button type="primary" icon="ios-search" @click="demand()">查询</Button>
      <Button type="primary" icon="md-close" @click="clear()">清空查询</Button>
      <Button type="primary" icon="md-add" @click="add()">新增</Button>
      <Button type="primary" :loading="loadingdel" :disabled="ctrlDisable" icon="ios-trash-outline" @click="del()">删除</Button>
    </div>

    <div class="spdb-table">
      <Table :loading="loading" :columns="gridTitles" :data="gridData" @on-selection-change="select"></Table>
    </div>

    <div class="spdb-page">
      <Page placement="top" :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat @on-change="changePage" @on-page-size-change="changePageSize"></Page>
    </div>

    <Modal
      v-model="menuDlg.show"
      title="分配菜单"
      @on-ok="menuOk">
      <div>
        <div>
          <Form :label-width="100" inline>
            <FormItem label="角色名称">
              <Input readonly v-model="currentSelect.roleName"/>
            </FormItem>
          </Form>
        </div>
        <div>
          <Tree ref="menuTree" :data="menuDlg.menuTreeData" show-checkbox multiple></Tree>
        </div>
      </div>
    </Modal>

    <Modal
      v-model="resourceDlg.show"
      title="分配资源"
      width="600px"
      @on-ok="resourceOk">
      <div>
        <div>
          <Form :label-width="100" inline>
            <FormItem label="角色名称">
              <Input readonly v-model="currentSelect.roleName"/>
            </FormItem>
          </Form>
        </div>
        <div>
          <Transfer
          :data="resourceDlg.resourceData"
          :target-keys="resourceDlg.targetData"
          :titles="['所有资源', '当前角色资源']"
          :list-style="resourceStyle"
          :filter-method="filterResource"
          :render-format="renderResource"
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
            <FormItem label="角色名称">
              <Input readonly v-model="currentSelect.roleName"/>
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
      title="分配应用"
      width="600px"
      @on-ok="systemOk">
      <div>
        <div>
          <Form :label-width="80" inline>
            <FormItem label="角色名称">
              <Input readonly v-model="currentSelect.roleName"/>
            </FormItem>
          </Form>
        </div>
        <div>
          <Transfer
          :data="systemDlg.systemData"
          :target-keys="systemDlg.targetData"
          :titles="['所有应用', '当前角色资源']"
          :list-style="resourceStyle"
          :filter-method="filterResource"
          :render-format="renderResource"
          filterable
          @on-change="systemHandleChange"></Transfer>
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
import common from '@/mixins/common'
import utils from '@/common/utils'

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
  data () {
    return {
      searchBean: {},
      loadingdel: false,
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
          title: '角色名称',
          key: 'roleName'
        },
        {
          title: '角色描述',
          key: 'remark'
        },
        {
          title: '操作',
          render: (h, params) => {
            return h('div', [
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
                    let queryCache = { searchBean: this.searchBean, currentPage: this.page.current, pageSize: this.page.size }
                    this.$emit('exit', Object.assign({ id: params.row.roleId }, queryCache)) // 提交form事件，在parent中显示form
                  }
                }
              }, '编辑'),
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
                    this.currentSelect = { roleId: params.row.roleId, roleName: params.row.roleName }
                    this.showMenuDlg()
                  }
                }
              }, '分配菜单') /* ,
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
                    this.currentSelect = { roleId: params.row.roleId, roleName: params.row.roleName }
                    this.showResoureDlg()
                  }
                }
              }, '分配资源') */
              /* h('Button', {
                props: {
                  size: 'small',
                  type: 'info',
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.currentSelect = {roleId: params.row.roleId, roleName: params.row.roleName};
                    this.showPlatformDlg();
                  }
                }
              }, '分配平台'),
              h('Button', {
                props: {
                  size: 'small',
                  type: 'info',
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.currentSelect = {roleId: params.row.roleId, roleName: params.row.roleName};
                    this.showSystemDlg();
                  }
                }
              }, '分配应用') */
            ])
          }
        }
      ],
      gridData: [],
      menuDlg: {
        show: false,
        menuTreeData: []
      },
      resourceDlg: {
        show: false,
        resourceData: [],
        targetData: []
      },
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
      currentSelect: {
        roleId: '',
        roleName: ''
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
      // 初始化Table数据
      this.search()
    },
    search: function () {
      let params = this.searchBean
      params.current = this.page.current
      params.size = this.page.size

      let httpConfig = {
        method: 'GET',
        url: '/role',
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
      const roleIds = Array.from(this.selection, e => e.roleId)
      let delConfig = {
        method: 'DELETE',
        url: `/role/${roleIds}`
      }
      this.loadingdel = true
      this.$ajax(delConfig)
        .then(resp => {
          this.loadingdel = false
          this.search()
        })
    },
    showMenuDlg: function () {
      this.menuDlg = {
        show: true,
        menuTreeData: []
      }
      // 初始化菜单
      const menuConfig = { method: 'GET', url: '/menu' }
      this.$ajax(menuConfig)
        .then(resp => {
          const menusByRoleConfig = { method: 'GET', url: `/role/${this.currentSelect.roleId}/slectMenus` }
          this.$ajax(menusByRoleConfig)
            .then(res => {
              const menuIds = Array.from(res.data, e => e.menuId) // 提取该角色拥有的菜单的ID
              let tree = getMenuTree(resp.data, menuIds)
              this.menuDlg.menuTreeData = tree.menus // 把处理好的数据赋值给组件
            })
        })

      // 转换树形结构
      function getMenuTree (menus, checkedMenus) {
        let result = { menus: [], checked: true }
        menus.forEach(menu => {
          let temp = {}
          temp.Key = menu.id
          temp.title = menu.text
          temp.expand = true
          const t = getMenuTree(menu.children, checkedMenus)
          temp.children = t.menus
          temp.checked = (checkedMenus.includes(menu.id) && t.menus.every(e => e.checked))
          result.menus.push(temp)
        })
        return result
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
    menuOk: function () {
      let checkeds = utils.fixChecked(this.$refs['menuTree'])
      let flatState = this.$refs['menuTree'].flatState
      for (const t of checkeds) {
        this.recusiveNode(checkeds, flatState[t.nodeKey], flatState)
      }
      const menuIds = Array.from(checkeds, e => e.Key)
      const addMenus = {
        method: 'POST',
        url: `/role/${this.currentSelect.roleId}/allotMenus`,
        data: { 'menuIds': menuIds.toString() }
      }
      this.$ajax(addMenus)
    },
    showResoureDlg: function () {
      this.resourceDlg = {
        show: true,
        resourceData: [],
        targetData: []
      }
      // 初始化穿梭框
      const allResConfig = {
        method: 'GET',
        url: '/resource',
        params: { currentPage: 1, pageSize: 999 }
      }
      this.$ajax(allResConfig)
        .then(resp => {
          // 获取该角色该有的资源
          const roleResConfig = {
            method: 'GET',
            url: `/role/${this.currentSelect.roleId}/resource`
          }
          this.$ajax(roleResConfig)
            .then(res => {
              this.resourceDlg.targetData = Array.from(res.data, e => e.resource_id)
              if (resp.data) {
                resp.data.rows.forEach(resource => {
                  let temp = {}
                  temp.key = resource.resource_id
                  temp.method = resource.resource_method
                  temp.label = resource.resource_url
                  temp.description = resource.remark
                  this.resourceDlg.resourceData.push(temp)
                })
              }
            })
        })
    },
    showPlatformDlg: function () {
      this.platformDlg = {
        show: true,
        platformData: [],
        targetData: []
      }
      // 初始化穿梭框
      const allResConfig = {
        method: 'GET',
        url: '/mplatform',
        params: { currentPage: 1, pageSize: 999 }
      }
      this.$ajax(allResConfig)
        .then(resp => {
          // 获取该角色该有的资源
          const roleResConfig = {
            method: 'GET',
            url: `/role/${this.currentSelect.roleId}/platform`
          }
          this.$ajax(roleResConfig)
            .then(res => {
              this.platformDlg.targetData = Array.from(res.data, e => e.id)
              if (resp.data) {
                resp.data.rows.forEach(platform => {
                  let temp = {}
                  temp.key = platform.id
                  temp.method = ''
                  temp.label = platform.etlPlatform
                  // temp.description = resource.remark;
                  this.platformDlg.platformData.push(temp)
                })
              }
            })
        })
    },
    showSystemDlg: function () {
      this.systemDlg = {
        show: true,
        systemData: [],
        targetData: []
      }
      // 初始化穿梭框
      const allResConfig = {
        method: 'GET',
        url: '/jobsyscfg',
        params: { currentPage: 1, pageSize: 999 }
      }
      this.$ajax(allResConfig)
        .then(resp => {
          // 获取该角色该有的资源
          const roleResConfig = {
            method: 'GET',
            url: `/role/${this.currentSelect.roleId}/system`
          }
          this.$ajax(roleResConfig)
            .then(res => {
              this.systemDlg.targetData = Array.from(res.data, e => e.id)
              if (resp.data) {
                resp.data.rows.forEach(resource => {
                  let temp = {}
                  temp.key = resource.id
                  temp.method = resource.platform
                  temp.label = resource.system
                  // temp.description = resource.remark;
                  this.systemDlg.systemData.push(temp)
                })
              }
            })
        })
    },

    resourceOk: function () {
      const resourceIds = this.resourceDlg.targetData
      const addResources = {
        method: 'POST',
        url: `/role/${this.currentSelect.roleId}/resource`,
        data: { 'resourceIds': resourceIds.toString() }
      }
      this.$ajax(addResources)
    },
    platformOk: function () {
      const platformIds = this.platformDlg.targetData
      const addResources = {
        method: 'POST',
        url: `/role/${this.currentSelect.roleId}/platform`,
        data: { 'platformIds': platformIds.toString() }
      }
      this.$ajax(addResources)
    },
    systemOk: function () {
      const systemIds = this.systemDlg.targetData
      const addResources = {
        method: 'POST',
        url: `/role/${this.currentSelect.roleId}/system`,
        data: { 'systemIds': systemIds.toString() }
      }
      this.$ajax(addResources)
    },
    filterResource: function (data, query) {
      return data.label.indexOf(query) > -1
    },
    renderResource: function (item) {
      return `<span class="resource-label">${item.label}</span>
          <span class="resource-method">${item.method}</span>`
    },
    handleChange: function (newTarget) {
      this.resourceDlg.targetData = newTarget
    },
    platformHandleChange: function (newTarget) {
      this.platformDlg.targetData = newTarget
    },
    systemHandleChange: function (newTarget) {
      this.systemDlg.targetData = newTarget
    }
  },
  computed: {
    resourceStyle () {
      return {
        width: '250px',
        height: '300px'
      }
    }
  }
}
</script>

<style>
.resource-method {
  float: right;
  color: green;
}
</style>

<template>
  <div>
  <div class="spdb-toolbar">
    <Button type="primary" icon="md-add" @click="add()">新增</Button>
    <Button type="primary" :loading="loadingdel" :disabled="ctrlDisable" icon="ios-trash-outline" @click="del()">删除</Button>
  </div>
    <div class="spdb-table">
      <!-- <tree-table expand-key="sex" :expand-type="false" :selectable="false" :columns="columns" :data="data" >
        <template slot="likes" slot-scope="scope">
          <Button @click="handle(scope)">123</Button>
        </template>
      </tree-table> -->
      <!-- <tree-table expand-key="selection" :loading="loading" :columns="menuTitle" :data="menuData" @on-selection-change="select">
        <template slot="op" slot-scope="scope">
          <Button type="info" @click="edit(scope.row)" style="margin-right:5px" size="small">编辑</Button>
          <Button type="info" @click="addChildMenu(scope.row)" size="small">添加子菜单</Button>
        </template>
      </tree-table> -->
      <!-- <Table rowkey="id" :columns="menuTitleNew" :data="menuData" border></Table> -->
      <s-tree-table :loading="loading" :columns="menuTitle" :data="menuData" @on-selection-change="select"></s-tree-table>
      <!-- <Table :loading="loading" :columns="menuTitle" :data="menuData" @on-selection-change="select"></Table> -->
    </div>
  </div>
</template>

<script>
import STreeTable from '_c/s-tree-table'
import common from '@/mixins/common'

export default {
  mixins: [ common ],
  components: {
    STreeTable
  },
  data: function () {
    return {
      demouser: {},
      loadingdel: false,
      menuTitleNew: [
        {
          title: '菜单名称',
          key: 'menuName',
          tree: true
        },
        {
          title: '菜单链接',
          key: 'menuUrl'
        },
        {
          title: '菜单图标',
          key: 'menuIcon'
        }
      ],
      menuTitle: [
        {
          type: 'selection',
          width: 60,
          align: 'center'
        },
        {
          type: 'tree',
          width: 60,
          fixed: 'left',
        },
        {
          title: '菜单名称',
          width: 200,
          key: 'text'
        },
        {
          title: '菜单链接',
          key: 'attributes',
          width: 300,
          render: (h, { row, index, column }) => {
  
            return h('span', {}, row.attributes.url)
          }
        },
        {
          title: '菜单图标',
          key: 'attributes',
          render: (h, { row, index, column }) => {
            if (row.attributes.icon && row.attributes.icon.startsWith('spdb-')) {
              const icon = row.attributes.icon.replace('spdb', 'icon')
              return h('i', {
                class: [
                  'iconfont',
                  icon
                ]
              })
            } else {
              return h('Icon', {
                props: {
                  size: 20,
                  type: row.attributes.icon
                }
              })
            }
          }
        },
        {
          title: '操作',
          render: (h, { row, index, column }) => {
            return h('div', [
              h(
                'Button',
                {
                  props: {
                    size: 'small',
                    type: 'info'
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      let id = row.id
                      this.$emit('switch', { id: id, isNoPath: false }) // 提交form事件，在parent中显示formrow.level == 1?true:false
                    }
                  }
                },
                '编辑'
              ),
              h(
                'Button',
                {
                  props: {
                    size: 'small',
                    type: 'info',
                    disabled: row.level == 3
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      let id = row.id
                      this.$emit('switch', { parentId: id, isNoPath: row.level == 0 }) // 提交form事件，在parent中显示form
                    }
                  }
                },
                '添加子菜单'
              )
            ])
          }
        }
      ],
      menuData: [],
      request: {
        listReq: {
          method: 'GET',
          url: 'menu'
        },
        delReq: {
          method: 'DELETE',
          url: 'menu'
        }
      }
    }
  },
  methods: {
    init: function () {
      this.$ajax(this.request.listReq)
        .then(resp => {
          this.menuData = resp.data
          this.makeData()
        })
        .then(resp => {
          this.loading = false
        })
        .catch(error => {
          this.loading = false
          console.log(error)
        })
    },
    add: function () {
      this.$emit('switch', { isNoPath: false })
    },
    del: function () {
      const ids = Array.from(this.selection, e => e.id)
      this.request.delReq.url = 'menu/' + ids
      this.loadingdel = true
      this.$ajax(this.request.delReq)
        .then(resp => {
          this.loadingdel = false
          this.ctrlDisable = true
          this.init()
        })
    },
    makeData: function () {
      let temp = this.menuData
      let children = []
      let result = []
      let map = new Map()
      let level = 1
      while (temp.length > 0) {
        let current = temp.pop()
        current.level = level
        map.set(current.id, current)
        for (const t of current.children) {
          children.push(t)
        }
        if (current.level === 1) {
          result.push(current)
        }
        if (temp.length === 0) {
          temp = children
          children = []
          level++
        }
      }
      this.menuData = result
    },
    edit (row) {
      
    },
    addChildMenu (row) {

    }
  }
}
</script>

<style>
 .ivu-table .ivu-icon-android-add::before {
   content: "\F330"
 }
 .ivu-table .ivu-icon-ios-minus-empty::before {
   content: '\F330'
 }
</style>

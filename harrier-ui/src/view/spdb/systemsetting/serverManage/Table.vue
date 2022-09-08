<template>
  <div>
    <div class="spdb-form">
      <Form ref="serverForm" :model="formBean" :label-width="80" :rules="ruleValidate"></Form>
    </div>
    <Form ref="serverForm" :model="formBean" :label-width="70" :rules="ruleValidate">
      <Row>
        <Col span="3">
          <Form-Item prop="servername" label="节点名称">
            <Input v-model="formBean.servername" placeholder="支持模糊查询" />
          </Form-Item>
        </Col>
        <Col span="1">&nbsp;</Col>
        <Col span="4">
          <div class="spdb-toolbar">
            <Button type="primary" icon="ios-search" @click="demand">查询</Button>
            <Button type="primary" icon="md-close" @click="clear">清除查询</Button>
          </div>
        </Col>
      </Row>
    </Form>

    <!--
			<S-AuthButton type="primary" icon="md-add" @click="add" :requestConfig=request.addReq>新增</S-AuthButton>
			<S-AuthButton type="primary" icon="ios-trash-outline" @click="del" :requestConfig=request.delReq>删除</S-AuthButton>
			<Button type="primary" icon="ios-search" @click="download">下载</Button>
    -->

    <div class="spdb-table">
      <Table :columns="gridColumns" :data="gridData" stripe @on-selection-change="select"></Table>
    </div>

    <div class="spdb-page">
      <Page
        placement="top"
        :total="page.total"
        :current="page.current"
        :page-size="page.size"
        :page-size-opts="page.opt"
        show-total
        show-sizer
        show-elevat
        @on-change="changePage"
        @on-page-size-change="changePageSize"
      ></Page>
    </div>
  </div>
</template>

<script>
import utils from '@/common/utils'

const RESOURCE_PATH = '/udsServer'

export default {
  props: {
    transData: {
      type: Object,
      default: function () {
        return {}
      }
    }
  },
  data: function () {
    return {
      formBean: {},
      request: {
        delReq: {
          method: 'DELETE',
          url: RESOURCE_PATH
        },
        addReq: {
          method: 'POST',
          url: RESOURCE_PATH
        }
      },
      // statusList: [
      //  {
      //    value: '1',
      //    label: 'node-1'
      //  },
      //  {
      //    value: '0',
      //    label: 'node-0'
      //  }
      // ],
      gridColumns: [
        // {
        // 	type: 'index',
        // 	title: ' ',
        // 	width: 80,
        // 	//align: 'center',
        // 	fixed: 'left'
        // },
        // {
        // 	type: 'selection',
        // 	width: 60,
        // 	//align: 'center',
        // 	fixed: 'left'
        // },
        // {
        //   title: '对外服务名',
        //   key: 'serverRoleName'
        // },
        {
          title: '服务组名',
          key: 'serverRoleNameGroup',
          minWidth: 100
        },
        {
          title: '集群名',
          key: 'nodeClusterType',
          minWidth: 120
        },
        {
          title: '服务器名',
          key: 'serverName',
          minWidth: 160
        },
        {
          title: '服务器iP',
          key: 'ip',
          minWidth: 140
        },
        {
          title: '上次启动时间',
          key: 'lastStart',
          minWidth: 180
        },
        {
          title: '关闭时间',
          key: 'lastEnd',
          minWidth: 180
        },
        // {
        //   title: '最大并发数',
        //   key: 'maxJobNum',
        //   minWidth: 110
        // },
        {
          title: '是否启用',
          key: 'isEnable',
          minWidth: 100,
          render: (h, { column, index, row }) => {
            return h('div', [h('Span', {}, row.isEnable ? '是' : '否')])
          }
        },
        // {
        //   title: '节点标签',
        //   key: 'tags',
        //   minWidth: 100
        // },
        // {
        //   title: '逻辑集群',
        //   key: 'dbControl',
        //   minWidth: 100,
        //   render: (h, { column, index, row }) => {
        //     return h('div', [h('Span', {}, this.dbControlName[row.dbControl])])
        //   }
        // },
        // {
        //   title: '节点归属地',
        //   key: 'location',
        //   minWidth: 110,
        //   render: (h, { column, index, row }) => {
        //     return h('div', [h('Span', {}, this.locationName[row.location])])
        //   }
        // },
        {
        	title: '描述',
        	key: 'des',
          minWidth: 100
        },
        {
          title: '操作',
          width: 150,
          // fixed: 'right',
          render: (h, { column, index, row }) => {
            return h('div', [
              // h(
              //   'Button',
              //   {
              //     props: {
              //       size: 'small',
              //       type: 'info'
              //     },
              //     style: {
              //       marginRight: '5px'
              //     },
              //     on: {
              //       click: () => {
              //         let id = row.serverName
              //         let queryCache = {
              //           formBean: this.formBean,
              //           currentPage: this.page.current,
              //           pageSize: this.page.size
              //         }
              //         this.$emit(
              //           'switch',
              //           Object.assign(
              //             { id: id, statusName: 'form' },
              //             queryCache
              //           )
              //         ) // 提交form事件，在parent中显示form
              //       }
              //     }
              //   },
              //   '编辑'
              // ),
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
                      this.$Modal.confirm({
                        title: '提示',
                        onOk: () => {
                          let params = {}
                          params.serverName = row.serverName
                          params.isEnable = !row.isEnable
                          let httpConfig = {
                            method: 'POST',
                            url: RESOURCE_PATH + '/setEnable',
                            data: params
                          }
                          this.$ajax(httpConfig)
                            .then(resp => {
                              if (resp.status && resp.status === 200) {
                                this.search()
                              }
                            })
                        },
                        content: row.isEnable ? '是否确定禁用该节点！' : '是否确定启用该节点！'
                      })
                      // let id = row.serverName
                      // let queryCache = {
                      //   formBean: this.formBean,
                      //   currentPage: this.page.current,
                      //   pageSize: this.page.size
                      // }
                      // this.$emit(
                      //   'switch',
                      //   Object.assign(
                      //     { id: id, statusName: 'form' },
                      //     queryCache
                      //   )
                      // ) // 提交form事件，在parent中显示form
                    }
                  }
                },
                row.isEnable ? '禁用' : '启用'
              ),
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
                      let queryCache = {
                        formBean: this.formBean,
                        currentPage: this.page.current,
                        pageSize: this.page.size
                      }
                      this.$emit(
                        'switch',
                        Object.assign(
                          { info: row, statusName: 'loglist' },
                          queryCache
                        )
                      ) // 提交form事件，在parent中显示form
                    }
                  }
                },
                '查看日志'
              )
            ])
          }
        }
      ],
      gridData: [],
      selection: [],
      locationName: {},
      dbControlName: {},
      page: {
        current: 1,
        total: 10,
        size: 10,
        opt: [10, 50, 100]
      },
      ruleValidate: {}
    }
  },
  methods: {
    /*
     *paramChange () {
     *  if (this.formBean.param == 0) {
     *    this.$emit('switch', {ntable: 'true'})
     *  }
     *}
     */
    demand () {
      this.page.current = 1
      this.search()
    },
    /**
     * 初始化
     **/
    init () {
      if (this.transData.currentPage) {
        this.formBean = this.transData.formBean
        this.page.current = this.transData.currentPage
        this.page.size = this.transData.pageSize
      }
      this.search()
    },
    /**
     * 如果传过来的有初始数据则进行数据绑定
     **/
    bindData (fields) {
      try {
        const data = Object.assign({}, this.transData.initFormBean)
        if (fields) {
          // 进行可选字段初始化
          for (const field of fields) {
            this.formBean[field] = data[field]
          }
        } else {
          // 进行全量字段初始化（默认是只有主表的主键的）
          this.formBean = Object.assign({}, data)
        }
      } catch (error) {
        console.error(error)
      }
    },
    /**
     * 下载
     **/
    download () {
      let params = {}
      Object.assign(params, this.formBean)
      params.type = 'excel'
      utils.download(RESOURCE_PATH + '/downLoad', params)
    },
    /**
     * 查询
     **/
    search () {
      let params = {}
      Object.assign(params, this.formBean)
      params.current = this.page.current
      params.size = this.page.size

      if (params.servername && params.servername !== '') {
        params.servername = '%' + params.servername + '%'
      } else {
        delete params.servername
      }

      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/selectAll',
        params: params
      }

      this.$ajax(httpConfig).then(resp => {
        this.gridData = resp.data.rows
        this.page.total = resp.data.total
      })
    },
    /**
     * 清空
     **/
    clear () {
      this.formBean = {}
      this.selection = []
      this.page = {
        current: 1,
        size: 10
      }
    },
    /**
     * 新增
     **/
    add () {
      this.$emit('switch')
    },
    /**
     * 删除
     **/
    del () {
      const ids = Array.from(this.selection, e => e.serverName)
      ids.forEach(id => {
        this.request.delReq.url = RESOURCE_PATH + '/' + id
        this.request.delReq.method = 'DELETE'
        this.$ajax(this.request.delReq).then(resp => {
          this.search()
        })
      })
    },
    /**
     * 数据复选事件
     **/
    select (selection) {
      this.selection = selection
    },
    /**
     * 改变页码事件
     **/
    changePage (currentPage) {
      this.page.current = currentPage
      this.search()
    },
    /**
     * 改变分页大小事件
     **/
    changePageSize (pageSize) {
      this.page.size = pageSize
      this.search()
    },
    queryLocationName () {
      let loadConfig = {
        method: 'GET',
        url: '/dictionary',
        params: { dicCode: 'location' }
      }
      this.$ajax(loadConfig).then(resp => {
        resp.data.rows.forEach(data => {
          this.locationName[parseInt(data.dicValue)] = data.dicName
        })
        // this.search()
      })
    },
    queryDbControlName () {
      let loadConfig = {
        method: 'GET',
        url: '/dictionary',
        params: { dicCode: 'db_control' }
      }
      this.$ajax(loadConfig).then(resp => {
        resp.data.rows.forEach(data => {
          this.dbControlName[parseInt(data.dicValue)] = data.dicName
        })
        this.search()
      })
    }
  },
  /**
   * 视图挂载
   **/
  mounted () {
    this.init()
    // this.queryLocationName()
    // this.queryDbControlName()
  }
}
</script>

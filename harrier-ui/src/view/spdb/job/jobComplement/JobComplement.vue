<template>
	<div>
		<Row>
      <Form ref="udsjobForm" :model="formBean" :label-width="80" :rules="ruleValidate">
        <Row>
          <Col span="3">
            <FormItem prop="platform" label="平台名">
              <Select filterable v-model="formBean.platform" @on-change="getSystemList" clearable>
                <Option v-for="item in platformData" :value="item.value+''" :key="item.value">{{ item.label }}</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="3">
            <FormItem label="应用名">
              <Select ref="refsystem" filterable v-model="formBean.systems" placeholder="先选平台" clearable>
                <Option v-for="item in systemData" :value="item.value" :key="item.value">{{ item.label }}</Option>
              </Select>

            </FormItem>
          </Col>
          <Col span="3">
            <FormItem label="作业状态">
              <Select filterable v-model="formBean.lastStatus" clearable>
                <Option v-for="item in jobstatusData" :value="item.value" :key="item.value">{{ item.label }}</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="6">
            <FormItem label="作业名">
              <Input type="textarea" :autosize="{minRows: 1,maxRows: 5}" v-model="formBean.job" placeholder="支持模糊查询"/>
            </FormItem>
          </Col>
          <Col span="1">&nbsp;</Col>
          <Col span="8">
            <div class="spdb-toolbar">
              <Button type="primary" icon="ios-search" @click="search">查询</Button>
              <Button type="primary" icon="ios-arrow-back" @click="cancel" style="">返回</Button>

              <!-- <Button type="primary" icon="md-close" @click="clear">清除查询</Button> -->
            </div>
          </Col>
        </Row>
      </Form>
		</Row>
		<div class="spdb-table">
			<Table :columns="gridColumns" :data="gridData" :loading="loadingTable" stripe @on-selection-change="select">
			</Table>
		</div>
		<div class="spdb-page">
			<Page :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat show-elevator @on-change="changePage" @on-page-size-change="changePageSize"></Page>
		</div>
			<div slot="footer">
			</div>
		</Modal>
	</div>
</template>

<script>
import utils from '@/common/utils'
const RESOURCE_PATH = '/udsJobComplement'

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
      jobstatusData: [
        { label: 'Done', value: 'Done' },
        { label: 'Pending', value: 'Pending' },
        { label: 'Running', value: 'Running' },
        { label: 'Failed', value: 'Failed' },
        { label: 'Ready', value: 'Ready' }
      ],
      jobtypeList: {},
      loadingTable: false,
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
      streamTypeList: {},

      gridColumns: [
        {
          title: '平台名',
          ellipsis: true,
          width: 120,
          sortable: true,
          key: 'platform'
        },
        {
          title: '应用名',
          ellipsis: true,
          align: 'left',
          width: 120,
          sortable: true,
          className: 'uds-job-column',
          key: 'systems'
        },
        {
          title: '作业名',
          key: 'job',
          ellipsis: false,
          // tooltip: true,
          sortable: true,
          minWidth: 170,
          className: 'uds-job-column',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('a', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                  color: row.enable ? '' : 'red'
                },
                on: {
                  click: () => {
                    let id = row.job
                    if (this.transData.jobStatus) {
                      this.$emit('switch', Object.assign({}, { id: id, jobStatus: this.transData.jobStatus, statusName: 'jobdetail', jobData: row }, this.getPageParam())) // 提交form事件，在parent中显示form
                    } else {
                      this.$emit('switch', Object.assign({}, { id: id, statusName: 'jobdetail', jobData: row }, this.getPageParam())) // 提交form事件，在parent中显示form
                    }
                  }
                }
              }, row.job)
            ])
          }
        },
        {
          title: '执行节点',
          ellipsis: false,
          tooltip: true,
          minWidth: 110,
          maxWidth: 130,
          sortable: true,
          key: 'serverName',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('span', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                }
              }, row.serverName ? row.serverName : '')
            ])
          }
        },
        {
          title: '状态',
          ellipsis: true,
          tooltip: true,
          minWidth: 80,
          maxWidth: 100,
          sortable: true,
          key: 'lastStatus'
        },
        {
          title: '批次号',
          ellipsis: true,
          tooltip: true,
          minWidth: 90,
          maxWidth: 100,
          sortable: true,
          key: 'multiBatch'
        },
        {
          title: 'TXDate',
          ellipsis: false,
          tooltip: true,
          width: 170,
          sortable: true,
          key: 'jobDate',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('span', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                }
              }, row.jobDate.substring(0, 10))
            ])
          }
        },
        {
          title: '开始时间',
          ellipsis: false,
          tooltip: false,
          width: 180,
          sortable: true,
          key: 'startTime'
        },
        {
          title: '结束时间',
          ellipsis: false,
          tooltip: false,
          width: 180,
          sortable: true,
          key: 'endTime',
          render: (h, { column, index, row }) => {
            let endArr = ['', '']
            if (row.endTime) {
              endArr = row.endTime.split(' ')
            }
            return h('div', [
              h('div', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                }
              }, row.lastStatus === 'Running' ? '' : row.endTime)])
          }
        },
        {
          title: '描述',
          ellipsis: true,
          tooltip: true,
          minWidth: 80,
          maxWidth: 100,
          sortable: true,
          key: 'desc'
        }
      ],
      statusList: [
        {
          label: 'Done',
          key: 'Done'
        },
        {
          label: 'Failed',
          key: 'Failed'
        }
      ],
      gridData: [],
      selection: [],
      systemData: [],
      platformData: [],
      page: {
        current: 1,
        total: 10,
        size: 10,
        opt: [10, 50, 100]
      },
      ruleValidate: {
      }
    }
  },
  methods: {
    getSystemList () {
      this.systemData = []
      this.$refs.refsystem.setQuery('')
      if (this.formBean.platform) {
        let userSystemList = this.$store.getters.getUserSystem()
        let userSystem = userSystemList[this.formBean.platform]
        userSystem.forEach(data => {
          let tmp = {}
          tmp.value = data
          tmp.label = data
          this.systemData.push(tmp)
        })
      }
    },
    queryjobtypeList () {
      let loadConfig = {
        method: 'GET',
        url: '/dictionary',
        params: { dicCode: 'jobtype' }
      }
      this.$ajax(loadConfig)
        .then(resp => {
          this.jobtypeList = {}
          resp.data.rows.forEach(data => {
            this.jobtypeList[data.dicValue] = data.dicName
          })
        })
    },
    queryPlatform () {
      let loadConfig = {
        method: 'GET',
        url: '/msys/getPlatformList'
      }
      this.$ajax(loadConfig)
        .then(resp => {
          resp.data.forEach(data => {
            let tmp = {}
            tmp.value = data
            tmp.label = data
            this.platformData.push(tmp)
          })
        })
    },
    querySystem () {
      let loadConfig = {
        method: 'GET',
        url: '/msys/getSysList',
        params: { platform: this.formBean.platform }
      }
      this.systemData = []
      this.$refs.refsystem.setQuery('')
      if (this.formBean.platform) {
        this.$ajax(loadConfig)
          .then(resp => {
            resp.data.forEach(data => {
              let tmp = {}
              tmp.value = data
              tmp.label = data
              this.systemData.push(tmp)
            })
          })
      }
    },
    getStartTime (startTime) {
      this.formBean.startTime = startTime
    },
    getEndTime (endTime) {
      this.formBean.endTime = endTime
    },
    /**
     * 初始化
     **/
    init () {
      if (this.transData.jobData) {
        this.page.size = this.transData.pageSize
        this.page.current = this.transData.currentPage
        this.search()
      }
    },
    /**
     * 查询
     **/
    search () {
      let params = {}
      params.current = this.page.current
      params.size = this.page.size
      params.platform = this.formBean.platform
      params.systems = this.formBean.systems
      params.lastStatus = this.formBean.lastStatus
      params.complementId = this.transData.jobData.id
      if (this.formBean.job) {
        params.job = this.formBean.job
      }
      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/selectAll',
        params: params
      }
      this.loadingTable = true
      this.$ajax(httpConfig)
        .then(resp => {
          this.gridData = resp.data.rows
          this.page.total = resp.data.total
          this.loadingTable = false
        })
    },
    queryStreamTypeList () {
      let loadConfig = {
        method: 'GET',
        url: '/dictionary',
        params: { dicCode: 'stream_type' }
      }
      this.$ajax(loadConfig)
        .then(resp => {
          this.streamTypeList = {}
          resp.data.rows.forEach(data => {
            this.streamTypeList[data.dicValue] = data.dicName
          })
        })
    },
    getPageParam () {
      return { formBean: this.formBean,
        currentPage: this.page.current,
        pageSize: this.page.size }
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
      const ids = Array.from(this.selection, e => e.system)
      ids.forEach(id => {
        this.request.delReq.url = RESOURCE_PATH + '/' + id
        this.request.delReq.method = 'DELETE'
        this.$ajax(this.request.delReq)
          .then(resp => {
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
    filterMethod (value, option) {
      return option.toUpperCase().indexOf(value.toUpperCase()) !== -1
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
    /**
		 * 返回到数据视图
		 **/
    cancel () {
      let prevTab = ''
      if (this.transData.tabObj) {
        prevTab = this.transData.tabObj.prevTab
        // prevTab = 'joblist'
      }
      let queryCache = { formBean: this.transData.formBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize }
      let backParam = { statusName: 'table', prevTab: prevTab, type: 'back' }
      if (this.transData.tablePage) {
        queryCache = { formBean: this.transData.formBean, currentPage: this.tablePage.current, pageSize: this.tablePage.size }
      }
      if (this.transData.prequeryCache) {
        if (this.transData.jobStatus) {
          this.transData.prequeryCache.jobStatus = this.transData.jobStatus
        }
        this.$emit('switch', Object.assign({}, this.transData.prequeryCache, backParam))
      } else {
        this.$emit('switch', Object.assign({}, queryCache, backParam, { jobData: this.transData.jobData }))
      }
    },
    getPlatformList () {
      let platformList = this.$store.getters.getUserPlatform()
      platformList.forEach(data => {
        let tmp = {}
        tmp.value = data
        tmp.label = data
        this.platformData.push(tmp)
      })
      console.log(this.platformData)
      this.search()
    },
  },
  /**
   * 视图挂载
   **/
  mounted () {
    this.getPlatformList()
    this.init()
  }
}

</script>

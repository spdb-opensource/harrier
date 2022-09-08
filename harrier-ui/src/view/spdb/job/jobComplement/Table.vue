<template>
  <div>
    <div class="spdb-form" style="margin-top:20px;">
      <Form ref="udsjobForm" :model="formBean" :label-width="80" :rules="ruleValidate">
        <Row>
          <Col span="6">
            <FormItem label="补数名">
              <Input type="textarea" :autosize="{minRows: 1,maxRows: 5}" v-model="formBean.comName" placeholder="支持模糊查询"/>
            </FormItem>
          </Col>
          <Col span="1">&nbsp;</Col>
          <Col span="8">
            <div class="spdb-toolbar">
              <Button type="primary" icon="ios-search" @click="search">查询</Button>
              <Button type="primary" icon="md-close" @click="clear">清除查询</Button>
            </div>
          </Col>
        </Row>
      </Form>
    </div>

    <div v-if="transData.showTable" class="spdb-table" id="joblistTable">
      <Table  size="small"  :columns="gridColumns" :data="gridData" stripe @on-selection-change="select" @on-sort-change="changeSort">
      </Table>
    </div>

    <div v-if="transData.showTable" class="spdb-page">
      <Page placement="top" :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt"  show-total show-sizer show-elevator @on-change="changePage" @on-page-size-change="changePageSize" ></Page>
    </div>
  </div>
</template>

<script>
import utils from '@/common/utils'
import store from '@/store/index'
import common from '@/mixins/common'
const RESOURCE_PATH = '/udsComplement'

export default {
  mixins: [ common ],
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
      platformData: [],
      systemData: [],
      jobstatusData: [
        { label: 'Done', value: 'Done' },
        { label: 'Pending', value: 'Pending' },
        { label: 'Running', value: 'Running' },
        { label: 'Failed', value: 'Failed' },
        { label: 'Ready', value: 'Ready' }
      ],
      formBean: {},
      udsJobStyle: {
        status: 'black'
      },
      gridColumns: [
        {
          title: '补数名称',
          ellipsis: true,
          minWidth: 150,
          sortable: true,
          key: 'comName'
        },
        {
          title: '开始日期',
          ellipsis: false,
          tooltip: false,
          minWidth: 170,
          sortable: true,
          key: 'startTime'
        },
        {
          title: '结束日期',
          ellipsis: false,
          tooltip: false,
          minWidth: 170,
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
          title: '补数状态',
          ellipsis: true,
          tooltip: true,
          minWidth: 80,
          maxWidth: 100,
          sortable: true,
          key: 'lastStatus'
        },
        {
          title: '批次范围',
          ellipsis: true,
          tooltip: true,
          minWidth: 80,
          maxWidth: 100,
          sortable: true,
          key: 'batchRange'
        },
        {
          title: '服务器范围',
          ellipsis: false,
          tooltip: true,
          minWidth: 110,
          maxWidth: 130,
          sortable: true,
          key: 'serverNameRang'
        },
        {
          title: '作业并行数',
          ellipsis: false,
          tooltip: true,
          minWidth: 110,
          maxWidth: 130,
          sortable: true,
          key: 'maxRunJob'
        },
        {
          title: '详情',
          width: 80,
          align: 'center',
          // fixed: 'right',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('Button', {
                props: {
                  icon: 'md-reorder'
                },
                style: {
                  margin: '0px',
                  fontSize: '20px',
                  border: 0,
                  padding: '4px 8px',
                  color: '#03399b',
                  backgroundColor: 'transparent'
                },
                on: {
                  click: () => {
                    let id = row.complementId
                    this.$emit('switch', Object.assign({}, { id: id, jobData: row, statusName: 'jobComplement', curTab: 'Table' }, this.getPageParam())) // 提交form事件，在parent中显示form
                  }
                }
              }, '')
            ])
          }
        },
      ],
      gridData: [],
      selection: [],
      page: {
        current: 1,
        total: 10,
        size: 10,
        opt: [10, 50, 100]
      },
      ruleValidate: {
        // platform: [
        //   { validator: validatePlatform, trigger: 'change' }
        // ]
      },
      fromto: '1'
    }
  },
  methods: {
    /**
     * 初始化
     **/
    init () {
      if (this.transData.currentPage) {
        this.formBean = this.transData.formBean
        this.page.current = this.transData.currentPage
        this.page.size = this.transData.pageSize
      }

      if (window.sessionStorage.getItem('joblist_jobcomplement') || this.transData.jobStatus) {
        this.fromto = '0'
        // this.transData.jobStatus
        if (window.sessionStorage.getItem('joblist_jobcomplement') == '0') {
          this.formBean.enable = 0
        } else {
          this.formBean.enable = 1
          if (this.transData.formBean && (this.transData.formBean.lastStatus ||
          this.transData.formBean.lastStatus.length == 0)) { this.formBean.lastStatus = this.transData.formBean.lastStatus } else { this.formBean.lastStatus = window.sessionStorage.getItem('joblist_jobcomplement') }
          if (this.transData.jobData) {
            this.formBean.platform = this.transData.jobData.platform
          }
        }
      }
    },
    /**
         * 如果传过来的有初始数据则进行数据绑定
         **/
    bindData (fields) {
      try {
        const data = Object.assign({}, this.transData.initFormBean)
        if (fields) { // 进行可选字段初始化
          for (const field of fields) {
            this.formBean[field] = data[field]
          }
        } else { // 进行全量字段初始化（默认是只有主表的主键的）
          this.formBean = Object.assign({}, data)
        }
      } catch (error) {
        console.error(error)
      }
    },

    /**
     * 查询
     **/
    search () {
      if (
        window.sessionStorage.getItem('joblist_jobcomplement') ||
                this.transData.jobStatus
      ) {
        let prevTab = ''
        let backParam = {}
        if (this.transData.statusName === 'table') {
          backParam.statusName = this.transData.prevTab
        } else {
          backParam.statusName = this.transData.statusName
        }
        backParam.udsjobsearch = '1'
        this.$emit('switch', Object.assign({}, backParam))
      } else {
        this.$emit(
          'switch',
          Object.assign({}, { statusName: 'table' })
        )
      }
      let params = {}
      if (this.formBean.comName) {
        params.comName = this.formBean.comName
      }
      params.current = this.page.current
      params.size = this.page.size
      for (let key in params) {
        if (key === 'job') {
          if (params[key] && params[key] !== '') {
            params[key] = '%' + params[key].trim().toUpperCase() + '%'
          } else {
            delete params[key]
          }
        }
        if (key === 'systems' && params[key]) {
          params[key] = params[key].trim().toUpperCase()
        }
      }

      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/selectAll',
        params: params
      }
      this.$ajax(httpConfig)
        .then(resp => {
          if (resp.data.total && resp.data.total > 0) {
            if (resp.data.rows && resp.data.rows.length === 0) {
              this.page.current = 1
              this.search()
            }
          }
          this.gridData = resp.data.rows
          this.page.total = resp.data.total
        })
      // })
    },

    execOpt (flag, row, index, column) {

    },
    /**
     * 清空
     **/
    clear () {
      this.formBean = {}
      this.selection = []
      this.page = {
        current: 1,
        size: 10
      }
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
    /**
     * 列排序
     **/
    changeSort (column) {
      let classcon = window.event.currentTarget.className
      let sort, order
      if (classcon.indexOf('ivu-icon-md-arrow-dropup') > -1) {
        order = 'ASC'
      } else if (classcon.indexOf('ivu-icon-md-arrow-dropdown') > -1) {
        order = 'DESC'
      }
      // console.log("column,key,order"+ column.key );
      this.formBean.order = order
      this.formBean.sort = column.key
      this.search()
    },
    getPlatformList () {
      let platformList = this.$store.getters.getUserPlatform()
      platformList.forEach(data => {
        let tmp = {}
        tmp.value = data
        tmp.label = data
        this.platformData.push(tmp)
      })
      this.search()
    },
    // harrier getSystemList
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
    // harrier test end
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
          this.search()
          this.queryJobstatus()
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
    queryJobstatus () {
      let loadConfig = {
        method: 'GET',
        url: '/dictionary',
        params: { dicCode: 'm_job_status' }
      }
      this.$ajax(loadConfig)
        .then(resp => {
          this.jobstatusData = []
          resp.data.rows.forEach(data => {
            let tmp = {}
            tmp.value = data.dicName
            tmp.label = data.dicName
            this.jobstatusData.push(tmp)
            // this.formBean.platform = "UDS";
          })
        })
    },
    filterMethod (value, option) {
      return option.toUpperCase().indexOf(value.toUpperCase()) !== -1
    },
    getPageParam () {
      return { formBean: this.formBean,
        currentPage: this.page.current,
        pageSize: this.page.size }
    },
  },
  mounted () {
    this.getPlatformList()
  }

}
</script>
<style>
#joblistTable table td div{
  padding-left: 2px;
  padding-right: 2px;
}
#joblistTable table th div{
  padding-left: 5px;
  padding-right: 5px;
}

</style>

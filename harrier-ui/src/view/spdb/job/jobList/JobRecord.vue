<template>
	<div>
		<Row>
			<Col span="24">
				<div style="font-size:14px;font-weight: bold; color: #464c5b;margin-left:5px;">
				运行记录
				&nbsp;&nbsp;平台名:{{this.transData.jobData.platform}}
				&nbsp;&nbsp;应用名:{{this.transData.jobData.systems}}
				&nbsp;&nbsp;作业名:{{this.transData.jobData.job}}
				</div>
			</Col>
		</Row>
		<Row>
		<Form :label-width="80">
			<Col span="13">
			&nbsp;
			</Col>
			<Col span="4">
				<FormItem  label="TXDate" >
					<DatePicker :transfer="true" v-model="formBean.startTime" type="datetime"  @on-change="getStartTime" format="yyyyMMdd" placeholder="请选择" ></DatePicker>
				<!--
					<Select v-model="jobDate" @on-change="search" filterable multiple>
						<Option v-for="item in jobDateList" :value="item.value" :key="item.value">{{ item.label }}</Option>
					</Select>-->
				</FormItem>
			</Col>
			<Col span="1">
			&nbsp;
			</Col>
			<Col span="6">
				<Button type="primary" icon="ios-search" @click="search" style="">&nbsp;&nbsp;查询</Button>
				<!-- <Button type="primary" @click="showJobScriptOp" style="">所有脚本</Button>
        <Button type="primary" icon="md-cloud-download" @click="hisDownload" style="">下载</Button> -->
				<Button type="primary" icon="ios-arrow-back" @click="cancel" style="">返回</Button>
			</Col>
		</Form>
		</Row>
		<div class="spdb-table">
			<Table :columns="gridColumns" :data="gridData" :loading="loadingTable" stripe @on-selection-change="select">
			</Table>
		</div>
		<div class="spdb-page">
			<Page :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat show-elevator @on-change="changePage" @on-page-size-change="changePageSize"></Page>
		</div>
		<!-- <Modal id="jobScript"
			v-model="jobScriptOp.show"
			title="所有脚本"
			width='50%'
			:mask-closable="false"
			> -->
			<!-- <div>
				<div class="spdb-table" >
					<Table border :columns="scriptGridColumns" :data="scriptGridData" stripe>
					</Table>
				</div>
			</div> -->
			<div slot="footer">
			</div>
		</Modal>
	</div>
</template>

<script>
import utils from '@/common/utils'
const RESOURCE_PATH = '/udsJobRecord'

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
          key: 'platform',
          width: 90
        },
        {
          title: '应用名',
          key: 'systems',
          width: 90
        },
        {
          title: '作业名',
          key: 'job',
          minWidth: 175
          // render: (h, { column, index, row }) => {
          //   return h('div', [
          //     h('a', {
          //       props: {
          //         size: 'small',
          //         type: 'info'
          //       },
          //       style: {
          //         // marginRight: '5px'
          //         // color: "green"
          //       },
          //       on: {
          //         click: () => {
          //           let id = row.job
          //           this.$emit('switch', { id: id, statusName: 'jobdetail', jobData: row, formBean: this.formBean, currentPage: this.page.current, pageSize: this.page.size }) // 提交form事件，在parent中显示form
          //         }
          //       }
          //     }, row.job)
          //   ])
          // }
        },
        {
          title: '作业类型',
          key: 'jobType',
          width: 100
          // render: (h, { column, index, row }) => {
          //   return h('div', [
          //     h('span', {
          //       props: {
          //         size: 'small',
          //         type: 'info'
          //       },
          //       style: {
          //         // marginLeft: '5px',
          //         // color: '#3399ff'
          //       }
          //     }, this.jobtypeList[row.jobType])
          //   ])
          // }
        },
        {
          title: '作业最后状态',
          key: 'lastStatus',
          width: 130
        },
        {
          title: '触发方式',
          key: 'streamType',
          width: 120
          // render: (h, { column, index, row }) => {
          //   return h('div', [
          //     h('div', {
          //     }, this.streamTypeList[row.streamType])
          //   ])
          // }
        },
        // {
        // 	title: '作业进入pending状态时间',
        // 	key: 'pendingTime',
        // },
        {
          title: '开始时间',
          key: 'startTime',
          width: 175
        },
        {
          title: '结束时间',
          key: 'endTime',
          width: 175
        },
        {
          title: 'TxDate',
          key: 'jobDate',
          width: 120
        },
        {
          title: '批次号',
          key: 'multiBatch',
          width: 90
        },
        {
          title: '执行次数',
          key: 'numTimes',
          width: 100
        },
        {
          title: '日志',
          width: 80,
          align: 'center',
          // fixed: 'right',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('Button', {
                props: {
                  icon: 'md-search'
                },
                style: {
                  // marginRight: '5px'
                  margin: '0px',
                  fontSize: '20px',
                  border: 0,
                  padding: '4px 8px',
                  color: '#03399b',
                  background: 'transparent'
                },
                on: {
                  click: () => {
                    let id = row.id + ''
                    if (this.transData.jobStatus) {
                      this.$emit('switch', Object.assign({}, { id: id, jobStatus: this.transData.jobStatus, statusName: 'log', jobData: row }, this.getPageParam())) // 提交form事件，在parent中显示form
                    } else {
                      this.$emit('switch', Object.assign({}, { id: id, statusName: 'log', jobData: row }, this.getPageParam())) // 提交form事件，在parent中显示form
                    }
                  }
                }
              }, '')
            ])
          }
        }
        // {
        // 	title: '操作',
        // 	fixed: 'right',
        // 	render: (h, {column,index, row}) => {
        // 		return h('div', [
        // 			h('Button', {
        // 				props: {
        // 					size: 'small',
        // 					type: 'info',
        // 				},
        // 				style: {
        // 					marginRight: '5px'
        // 				},
        // 				on: {
        // 					click :() => {
        // 						let id = row.system;
        // 						this.$emit('switch', {id: id}); //提交form事件，在parent中显示form
        // 					}
        // 				}
        // 			}, '编辑')
        // 		])
        // 	}
        // }
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

      // modify by jcjin 20200529 for systemData返回值调整为key-value
      // this.$ajax(loadConfig)
      // .then(resp => {
      //  this.formBean.system = "";
      //  this.systemData = resp.data;
      //
      // })
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
        // Object.assign(this.formBean, this.transData.jobData)
        this.page.size = this.transData.pageSize
        this.page.current = this.transData.currentPage
        this.search()
      }
      // this.queryStreamTypeList()
      // this.queryPlatform()
      // this.queryjobtypeList()
      // this.search()
    },
    /**
     * 如果传过来的有初始数据则进行数据绑定
     **/
    // bindData (fields) {
    //   try {
    //     const data = Object.assign({}, this.transData.initFormBean)
    //     if (fields) { // 进行可选字段初始化
    //       for (const field of fields) {
    //         this.formBean[field] = data[field]
    //       }
    //     } else { // 进行全量字段初始化（默认是只有主表的主键的）
    //       this.formBean = Object.assign({}, data)
    //     }
    //   } catch (error) {
    //     console.error(error)
    //   }
    // },
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
      // if (this.formBean.startTime === '') {
      //   this.$Message.error('未填写开始时间')
      //   return
      // }
      // if (this.formBean.endTime === '') {
      //   this.$Message.error('未填写结束时间')
      //   return
      // }
      // let stime = new Date(this.formBean.startTime)
      // let etime = new Date(this.formBean.endTime)
      // let num = parseInt((etime.getTime() - stime.getTime()) / 1000 / 60 / 60 / 24)
      // if (num < 0 || num > 30) {
      //   this.$Message.error('查询时间应在30天之内')
      //   return
      // }
      // if (etime <= stime) {
      //   this.$Message.error('结束时间应大于开始时间')
      //   return
      // }
      // Object.assign(params, this.formBean)
      params.current = this.page.current
      params.size = this.page.size
      // for (let key in params) {
      //   if (key === 'job' && params[key]) {
      //     params[key] = params[key].trim().toUpperCase()
      //   }
      // }
      params.platform = this.transData.jobData.platform
      params.systems = this.transData.jobData.systems
      params.job = this.transData.jobData.job
      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/selectJobRecord',
        params: params
      }
      this.loadingTable = true
      this.$ajax(httpConfig)
        .then(resp => {
          if (resp.status && resp.status === 200) {
            this.gridData = resp.data.records
            this.page.total = resp.data.total
          }
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
    }
  },
  /**
   * 视图挂载
   **/
  mounted () {
    this.init()
    // this.queryStreamTypeList()
    // this.queryPlatform()
  }
}

</script>

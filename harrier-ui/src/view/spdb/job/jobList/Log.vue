<template>
	<div>
		<Row>
			<Col span="24">
				<div style="font-size:14px;font-weight: bold; color: #464c5b;margin-left:5px;">
				作业日志列表
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
			&nbsp;
				<!-- <FormItem  label="TXDate" >
					<DatePicker :transfer="true" v-model="formBean.startTime" type="datetime"  @on-change="getStartTime" format="yyyyMMdd" placeholder="请选择" ></DatePicker>
				</FormItem> -->
			</Col>
			<Col span="1">
			&nbsp;
			</Col>
			<Col span="6">
				<Button type="primary" icon="ios-search" @click="search" style="">&nbsp;&nbsp;查询</Button>
				<Button type="primary" @click="showJobScriptOp" style="">所有脚本</Button>
        <!-- <Button type="primary" icon="md-cloud-download" @click="hisDownload" style="">下载</Button> -->
				<Button type="primary" icon="ios-arrow-back" @click="cancel" style="">返回</Button>
			</Col>
		</Form>
		</Row>
		<div class="spdb-table">
			<Table border :columns="gridColumns" :data="gridData" stripe >
			</Table>
		</div>
		<div class="spdb-page">
			<Page :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat show-elevator @on-change="changePage" @on-page-size-change="changePageSize"></Page>
		</div>
		<Modal id="jobScript"
			v-model="jobScriptOp.show"
			title="所有脚本"
			width='50%'
			:mask-closable="false"
			>
			<div>
      <!--
				<div class="spdb-toolbar">
					<Button type="primary" icon="md-cloud-download" @click="downloadAllScriptfile">下载所有脚本</Button>
				</div>-->
				<div class="spdb-table" >
					<Table border :columns="scriptGridColumns" :data="scriptGridData" stripe>
					</Table>
				</div>
			</div>
			<div slot="footer">
			</div>
		</Modal>
	</div>
</template>

<script>
import utils from '@/common/utils'
const RESOURCE_PATH = '/udsJobStepRecord'

export default {
  props: {
    transData: {
      type: Object,
      default: function () {
    			return {}
      }
    }
  	},
  data () {
    return {
      jobDateList: [],
      jobDate: [],
      formBean: {},
      gridData: [],
      gridColumns: [
        {
          title: '执行次数',
          width: 110,
          key: 'numTimes'
        },
        {
          title: 'log',
          // key: 'logName',
          key: 'logName',
          minWidth: 150,
          render: (h, { column, index, row }) => {
            return h('div', [
              h('a', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                  // color: "green"
                },
                // href: row.logDir,
                on: {
                  click: () => {
                    // let id = row.job
                    // this.formBean.logPath = row.logPath
                    // this.formBean.logDir = row.logDir
                    // this.formBean.job = row.job
                    // this.formBean.serverName = row.serverName
                    this.downloadLogfile(row)
                  }
                }
              }, row.logName)
            ])
          }
        },
        {
          title: 'script',
          // key: 'scriptName',
          key: 'scriptName',
          minWidth: 140,
          render: (h, { column, index, row }) => {
            return h('div', [
              h('a', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                  // color: "green"
                },
                // href: row.scriptPath,
                on: {
                  click: () => {
                    // let id = row.job
                    // this.formBean.scriptPath = row.scriptPath
                    // this.formBean.job = row.job
                    // this.formBean.platform = row.platform
                    // this.formBean.system = row.system
                    // this.formBean.scriptDir = row.scriptDir
                    // this.formBean.serverName = row.serverName
                    this.downloadScriptfile(row)
                  }
                }
              }, row.scriptName)
            ])
          }
        },
        {
          title: '执行节点',
          ellipsis: false,
          tooltip: true,
          width: 120,
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
              }, row.serverName ? row.serverName : row.address)
            ])
          }
        },
        /* {
					title: '所有脚本',
					key: '',
					width: 90,
					render: (h, {column,index, row}) => {
						return h('div', [
							h('a', {
								props: {
									size: 'small',
									type: 'info',
								},
								style: {
									//marginRight: '5px'
									//color: "green"
								},
								on: {
									click :() => {
										this.showJobScriptOp(row);
									}
								}
							}, "查看")
						])
					}
				}, */
        {
          title: '开始时间',
          width: 175,
          key: 'startTime'
        },
        {
          title: '结束时间',
          width: 175,
          key: 'endTime',
          render: (h, params) => {
            let endTimeStr = ''
            endTimeStr = utils.fmtDate(params.row.endTime, 'yyyy')
            if (endTimeStr == '2099') {
              endTimeStr = ''
            } else {
              endTimeStr = params.row.endTime
            }
            // alert(endTimeStr);
            return h('div', {
              props: {
              }
            }, endTimeStr)
          }
        },
        {
          title: '耗时',
          width: 120,
          key: 'endTime',
          render: (h, params) => {
            let etime = null
            let startTime = null
            let elapsed = ''
            let str = utils.fmtDate(params.row.endTime, 'yyyy')
            if (str == '2099') {
              elapsed = ''
            } else {
              etime = utils.fmtDate(params.row.endTime, 'yyyyMMdd hh:mm:ss')
              startTime = utils.fmtDate(params.row.startTime, 'yyyyMMdd hh:mm:ss')
              let e = new Date(etime.substring(0, 4) + '/' + etime.substring(4, 6) + '/' + etime.substring(6))
		        	let s = new Date(startTime.substring(0, 4) + '/' + startTime.substring(4, 6) + '/' + startTime.substring(6))
              // let ss=
              let second = parseInt((e.getTime() -  s.getTime())) / 1000
              let hourTime = 0
              let minuteTime = 0
              let secondTime = 0
              if (second > 60) {
                minuteTime = Math.floor(second / 60)
                secondTime = Math.floor(second % 60)
                if (minuteTime >= 60) {
                  hourTime = Math.floor(minuteTime / 60)
                  minuteTime = Math.floor(minuteTime % 60)
                } else {
                  hourTime = 0
                }
              } else {
                hourTime = 0
                minuteTime = 0
                if (second == 60) {
                  minuteTime = 1
                  secondTime = 0
                } else {
                  secondTime = second
                }
              }
              elapsed = this.addZero(hourTime) + ':' + this.addZero(minuteTime) + ':' + this.addZero(secondTime)
            }
            return h('div', {
              props: {
              }
            }, elapsed)
          }
        },
        {
          title: 'TXDate',
          width: 120,
          key: 'jobDate',
          render: (h, params) => {
            return h('div', {
              props: {
              }
            }, this.transData.jobData.jobDate)
          }
        },
        // {
        // 	title: 'returnCode',
        // 	width: 110,
        // 	key: 'returnCode',
        // },
        {
          title: '运行状态',
          width: 110,
          key: 'returnCode',
          render: (h, params) => {
            let str = ''
            let endTimeStr = ''
            endTimeStr = utils.fmtDate(params.row.endTime, 'yyyy')
            if (params.row.returnCode == 0) {
              str = 'Done'
            } else {
              str = 'Failed'
            }
            if (endTimeStr == '2099') {
              str = 'Running'
            }
            return h('div', {
              props: {
              }
            }, str)
          }
        }

      ],
      gridData: [],
      selection: [],
      page: {
        current: 1,
        total: 10,
        size: 10,
        opt: [10, 50, 100]
      },
      ruleValidate: {
      },
      scriptGridData: [],
      scriptGridColumns: [
				 /* {
				 	type: 'index',
				 	title: ' ',
				 	width: 80,
				 	//align: 'center',
				 	fixed: 'left'
				 }, */
        {
          title: '脚本名',
          key: 'scriptName'
        },
        {
          title: '执行参数',
          minWidth: 150,
          key: 'parameter'
        },
        {
          title: '执行步数',
          width: 100,
          key: 'stepNum'
        },
        {
          title: '操作',
          width: 90,
          render: (h, { column, index, row }) => {
            return h('div', [
              h('Button', {
                props: {
                  size: 'small',
                  type: 'primary'
                },
                style: {
                  // marginRight: '5px'
                  // color: "green"
                },
                href: row.scriptPath,
                on: {
                  click: () => {
                    // this.formBean.scriptName = row.fileName
                    // this.formBean.job = this.transData.jobData.job
                    // this.formBean.platform = this.transData.jobData.platform
                    // this.formBean.system = this.transData.jobData.system
                    // this.formBean.scriptDir = row.path
                    // this.formBean.serverName = this.transData.jobData.serverName
                    this.downloadScriptfile(row)
                  }
                }
              }, '下载')
            ])
          }
        }

      ],
      jobScriptOp: {
        show: false,
        jobScriptParam: {}
      }
    }
  },
  methods: {
    /**
		 * 初始化
		 **/
    init () {
      this.search()
    },
    /**
		 * 如果传过来的有初始数据则进行数据绑定
		 **/
    bindData () {
      try {
        this.formBean = Object.assign({}, this.transData.initFormBean)
      } catch (error) {
        console.error(error)
      }
    },
    hisDownload () {
      let params = {}
      Object.assign(params, this.formBean)
      params.job = this.transData.id
      let startTime = new Date(this.formBean.startTime)
      params.jobDate = utils.fmtDate(this.formBean.startTime, 'yyyyMMdd')
      if (this.formBean.startTime == '') {
        params.jobDate = null
      }
      params.type = 'excel'
      // utils.download(RESOURCE_PATH + '/downLoad', params);
      utils.download(RESOURCE_PATH + '/logHisDownLoad', params)
    },
    downloadLogfile (row) {
      let params = {}
      // Object.assign(params, this.formBean)
      params.filename = row.logName
      params.uri = row.logPath
      utils.download('/file/loadFile', params)
    },
    downloadScriptfile (row) {
      let params = {}
      // Object.assign(params, this.formBean)
      params.filename = row.scriptName
      params.uri = row.scriptPath
      utils.download('/file/loadFile', params)
    },
    downloadAllScriptfile (param) {
      let params = {}
      if (!this.scriptGridData) {
        this.$Message.error({
          content: '无脚本可下载!',
          duration: 10,
          closable: true
        })
        return
      }
      Object.assign(params, this.jobScriptOp.jobScriptParam)
      // params.scriptName = scriptName
      utils.download(RESOURCE_PATH + '/downloadAllScriptfile', params)
    },
    showJobScriptOp () {
      this.jobScriptOp.show = true
      this.jobScriptOp.jobScriptParam = this.transData.jobData
      this.getAllScript(this.transData.jobData)
    },
    getAllScript (param) {
      let params = {}
      Object.assign(params, param)
      // params.currentPage = this.page.current;
      // params.pageSize = this.page.size;
      // params.job = this.transData.id;
      if (!this.transData.formBean) {
        params.currentPage = 1
      }
      let httpConfig = {
        method: 'GET',
        url: '/udsJobStep/selectJobStepList',
        params: params
      }

      this.$ajax(httpConfig)
        .then(resp => {
          // this.jobScriptOp.path = resp.data.path
          resp.data.records.forEach(data => {
            if (data.scriptPath) {
              data.scriptName = data.scriptPath.substring(data.scriptPath.lastIndexOf('/') + 1)
            }
          })
          this.scriptGridData = resp.data.records
        })
    },
    getStartTime (startTime) {

      // this.formBean.startTime= startTime;
      // let etime=new Date(startTime);
      // let num= parseInt((etime.getTime() -  startTime.getTime())/1000/60/60/24);
      // if(num<0||num>30){
      // 	this.$Message.error('查询时间应在30天之内');
      // 	return
      // }
      // search();
    },
    addZero (time) {
      let str = time
      if (time < 10) {
        str = '0' + time
      }
      return str
    },
    /**
		 * 查询
		 **/
    search () {
      let params = {}
      // Object.assign(params, this.formBean)
      params.currentPage = this.page.current
      params.pageSize = this.page.size
      params.job_record_id = this.transData.id
      // let startTime = new Date(this.formBean.startTime)
      // let etime = new Date()
      // let num = parseInt(((etime.getTime() -  startTime.getTime())) / 1000 / 60 / 60 / 24)
      // if (num < 0 || num > 60) {
      //   this.$Message.error('查询时间应在60天之内')
      //   return
      // }
      // if (this.formBean.startTime == null || this.formBean.startTime == '') {
      //   params.jobDate = null
      // } else {
      //   let str = utils.fmtDate(this.formBean.startTime, 'yyyyMMdd')
      //   params.jobDate = utils.fmtDate(this.formBean.startTime, 'yyyyMMdd')
      // }
      // alert(params.jobDate);
      if (!this.transData.formBean) {
        params.currentPage = 1
      }
      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/selectJobStepRecord',
        params: params
      }
      this.$ajax(httpConfig)
        .then(resp => {
          resp.data.forEach(data => {
            if (data.scriptPath) {
              data.scriptName = data.scriptPath.substring(data.scriptPath.lastIndexOf('/') + 1)
            }
            if (data.logPath) {
              data.logName = data.logPath.substring(data.logPath.lastIndexOf('/') + 1)
            }
          })
          this.gridData = resp.data
          this.page.total = resp.data.length
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
      this.formBean.order = order
      this.formBean.sort = column.key
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
      if (this.transData.prevTab) {
        prevTab = this.transData.prevTab
      }
      let queryCache = { formBean: this.transData.formBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize }
      if (this.transData.prequeryCache) {
        queryCache.prequeryCache = this.transData.prequeryCache
      }
      let backParam = { statusName: 'table', prevTab: prevTab, type: 'back' }
      // syncJobDetailForm
      if (this.transData.jobStatus) {
        queryCache.jobStatus = this.transData.jobStatus
      }
      if (this.transData.curTab) {
        backParam.statusName = this.transData.curTab
      }
      if (this.transData.parentData) {
        backParam.parentData = this.transData.parentData
      }
      if (this.transData.row) {
        backParam.row = this.transData.row
      }
      this.$emit('switch', Object.assign({}, queryCache, backParam))
    }
  },
  /**
	 * 视图挂载
	 **/
  mounted () {
    this.init()
  }
}

</script>

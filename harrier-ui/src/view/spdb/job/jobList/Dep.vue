<template>
	<div>
		<div>
			<!-- <Button type="primary" icon="android-add" @click="save">保存</Button> -->
			<Row>
				<Col span="20">
					<div style="font-size:14px;font-weight: bold; color: #464c5b;margin-left:5px;">依赖作业列表
					&nbsp;&nbsp;平台名:{{this.transData.jobData.platform}}
					&nbsp;&nbsp;应用名:{{this.transData.jobData.systems}}
					&nbsp;&nbsp;作业名:{{this.transData.jobData.job}}
					</div>
				</Col>
				<Col span="4">
					<!-- <Button size="small" type="primary" icon="md-cloud-download" @click="download" style="">下载</Button> -->
					<Button size="small" type="primary" icon="ios-arrow-back" @click="cancel" style="">返回</Button>
				</Col>
			</Row>
		</div>
		<div class="spdb-table" id="depTable">
			<Table border :columns="gridColumns" :data="gridData" stripe @on-sort-change="changeSort">
			</Table>
		</div>
		<div class="spdb-page">
			<Page :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat show-elevator @on-change="changePage" @on-page-size-change="changePageSize"></Page>
		</div>
		<Modal id="confirmDep"
			v-model="depDlg.show"
			title="修改依赖状态"
			@on-ok="depOk">
			<div>
				<div>
					<Form :label-width="90" >
						<Row>
							<FormItem label="平台名">
								<Input readonly v-model="formBean.depPlatform"/>
							</FormItem>
						</Row>
						<Row>
							<FormItem label="应用名">
								<Input readonly v-model="formBean.depSystem"/>
							</FormItem>
						</Row>
						<Row>
							<FormItem label="作业名">
								<Input readonly type="textarea" :autosize="{minRows: 1,maxRows: 5}" v-model="formBean.depJob"/>
							</FormItem>
						</Row>
						<Row>
							<FormItem label="是否启用">
								<RadioGroup v-model="formBean.isEnable">
									<Radio label='1'><span>启用</span></Radio>
									<Radio label='0'><span>禁用</span></Radio>
								</RadioGroup>
							</FormItem>
						</Row>
						<Row>
							<FormItem label="依赖批次号">
								<!-- <InputNumber :max="10000" :min="0" v-model="formBean.depBatch"></InputNumber> -->
								<Input readonly v-model="formBean.depBatch"/>
							</FormItem>
						</Row>
					</Form>
				</div>
			</div>
		</Modal>
	</div>
</template>

<script>
import utils from '@/common/utils'
const RESOURCE_PATH = '/udsJob'
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
      depDlg: { show: false, index: '' },
      formBean: {},
      gridData: [],
      gridColumns: [
        /** {
					type: 'selection',
					width: 60,
					//align: 'center',
					fixed: 'left'
				}, */
        {
          title: '平台名',
          sortable: true,
          width: 100,
          key: 'platform'
        },
        {
          title: '应用名',
          sortable: true,
          width: 100,
          key: 'systems'
        },
        {
          title: '作业名',
          sortable: true,
          minWidth: 200,
          key: 'job',
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
                  // color: row.isEnable === 0 ? 'red' : ''
                },
                on: {
                  click: () => {
                    let id = row.job
                    let queryCache = { id: id, statusName: 'jobdetail', jobData: row, previd: this.transData.id, prevTab: 'dep', curTab: 'dep', tablePage: this.tablePage, formBean: this.transData.formBean }
                    if (this.transData.jobStatus) {
                      queryCache.jobStatus = this.transData.jobStatus
                      this.$emit('switch', Object.assign({}, queryCache, this.getPageParam())) // 提交form事件，在parent中显示form
                    } else {
                      this.$emit('switch', Object.assign({}, queryCache, this.getPageParam())) // 提交form事件，在parent中显示form
                    }
                  }
                }
              }, row.job)
            ])
          }
        },
        {
          title: '执行节点',
          minWidth: 120,
          key: 'serverName'
        },
        {
          title: '类型',
          sortable: true,
          width: 90,
          key: 'jobType',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('a', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  marginRight: '5px',
                  color: '#3399ff'
                },
                on: {
                  click: () => {
                    let id = row.job
                    let queryCache = { formBean: this.transData.formBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize, page: this.page }
                    if (this.transData.prequeryCache) {
                      queryCache = this.transData.prequeryCache
                    }
                    if (row.jobType == 'M' || row.jobType == 'W') {
                      this.$emit('switch', Object.assign({}, { id: id, previd: this.transData.id, statusName: 'frequency', prevTab: 'dep', curTab: 'dep', jobData: row }, this.getPageParam()))
                    } else if (row.jobType == 'C') {
                      this.$emit('switch', Object.assign({}, { id: id, previd: this.transData.id, statusName: 'trigger', prevTab: 'dep', curTab: 'dep', jobData: row }, this.getPageParam()))
                      // this.$emit('switch', Object.assign({}, {id: id, statusName: 'trigger', jobData: row}, this.getPageParam()))
                    } else {
                      this.$Message.warning('日作业不存在定时或频度信息。')
                    }
                  }
                }
              }, this.jobtypeList[row.jobType])
            ])
          }
        },
        {
          title: '状态',
          sortable: true,
          width: 110,
          key: 'lastStatus'
        },
        {
          title: '依赖批次号',
          sortable: true,
          width: 130,
          key: 'depBatch'
        },
        {
          title: '开始时间',
          sortable: true,
          width: 175,
          key: 'startTime'
        },
        {
          title: '结束时间',
          sortable: true,
          width: 175,
          key: 'endTime',
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
              }, row.lastStatus == 'Running' ? '' : row.endTime)
            ])
          }
        },
        {
          title: 'TXDate',
          sortable: true,
          width: 120,
          key: 'jobDate'
        },
        {
          title: '描述',
          minWidth: 80,
          key: 'des'
        },
        {
          title: '依赖状态',
          width: 100,
          // fixed: 'right',
          key: 'isEnable',
          render: (h, { column, index, row }) => {
            let statusc = '#19be6b'
            if (row.isEnable == '0') {
              statusc = '#ed3f14'
            }
            return h('div', [
              h('span', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  marginRight: '5px',
                  color: statusc
                },
                on: {
                  click: () => {
                    // this.formBean = Object.assign({}, row)
                    // this.formBean.depPlatform = row.platform
                    // this.formBean.depSystem = row.system
                    // this.formBean.depJob = row.job
                    // this.formBean.isEnable = '' + row.isEnable
                    // this.depDlg.index = index
                    // this.showDepDlg()
                  }
                }
              }, row.isEnable != 1 ? '禁用' : '启用')
            ])
          }
        },
        {
          title: '操作',
          width: 80,
          // fixed: 'right',
          key: 'isEnable',
          render: (h, { column, index, row }) => {
            let statusc = '#19be6b'
            if (row.isEnable == '0') {
              statusc = '#ed3f14'
            }
            return h('div', [
              h('a', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  marginRight: '5px'
                  // color: statusc
                },
                on: {
                  click: () => {
                    this.orgIsEnabel = row.isEnable + ''
                    this.orgDepBatch = row.depBatch
                    this.formBean = Object.assign({}, row)
                    this.formBean.depPlatform = row.platform
                    this.formBean.depSystem = row.systems
                    this.formBean.depJob = row.job
                    this.formBean.isEnable = '' + row.isEnable
                    this.formBean.id = row.pid
                    this.depDlg.index = index
                    this.showDepDlg()
                  }
                }
              }, '操作')
            ])
          }
        }
      ],
      // gridData: [{platform: "BDP", system: "DLA", job: "BDP_DLA_TEST",depPlatform: "BDP", depSystem: "DLA", depJob: "BDP_DLA_TEST_STREAM",des:"ss",isEnable: "1"}],
      selection: [],
      page: {
        current: 1,
        total: 10,
        size: 10,
        opt: [10, 50, 100]
      },
      tablePage: {},
      ruleValidate: {
      },
      jobtypeList: { 'D': '日', 'C': '定时', 'W': '周', 'M': '月' },
      orgIsEnabel: '',
      orgDepBatch: null
    }
  },
  methods: {
    /**
		 * 初始化
		 **/
    init () {
      if (this.transData.id) {
        if (this.transData.curTab === 'Table') {
          this.tablePage.current = this.transData.currentPage
          this.tablePage.size = this.transData.pageSize
        }
        if (this.transData.tablePage) {
          this.tablePage = this.transData.tablePage
        }
        if (this.transData.prequeryCache) {
          // this.page = this.transData.prequeryCache.page
          this.page.current = this.transData.prequeryCache.currentPage
          this.page.size = this.transData.prequeryCache.pageSize
        }
      }
      this.search()
      // this.queryjobtypeList()
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
    /**
		 * 下载
		 **/
    download () {
      let params = {}
      Object.assign(params, this.formBean)
      params.type = 'excel'
      params.downloadType = 'dep'
      params.job = this.transData.id
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
      params.job = this.transData.id
      // if (!this.transData.formBean) {
      //   params.currentPage = 1
      // }
      if (!params.job) {
        params.job = this.$store.getters.getUmdata('dep')
        this.$store.dispatch('resetUmdata', { path: 'dep' })
      }
      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/UpjobList',
        params: params
      }

      this.$ajax(httpConfig)
        .then(resp => {
          resp.data.records.forEach(data1 => {
            if (data1.depIsEnable) {
              data1.isEnable = '1'
            } else {
              data1.isEnable = '0'
            }
          })
          this.gridData = resp.data.records
          this.page.total = resp.data.total
        })
    },

    /**
		 * 返回到数据视图
		 **/
    cancel () {
      let prevTab = ''
      if (this.transData.tabObj) {
        prevTab = this.transData.tabObj.prevTab
        prevTab = 'joblist'
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
    showDepDlg () {
      this.depDlg.show = true
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
    /**
         * 平台管理员操作权限特殊处理增加判断
         * 禁用启用依赖，仅当角色为平台管理员、平台是该用户拥有的才可以操作
         *
         **/
    isOwnPlatform (job) {
      let authPlatform = this.$store.getters.getPlatforms
      if (authPlatform.indexOf('ROLE_ADMIN') === -1) {
        if (authPlatform.length > 0) {
          if (authPlatform.includes(job.platform)) {
            return true
          } else {
            // 修改的平台不是所属平台
            this.$Message.error({
              content: '操作权限不足，请联系管理员确认！',
              duration: 15,
              closable: true
            })
            return false
          }
        }
      }
      return true
    },
    depOk (row) {
      let params = {}
      // console.log(this.formBean)
      // params.depJob = this.formBean.job
      // params.job = this.transData.jobData.job
      // params.isEnable = this.formBean.isEnable
      // params.depBatch = this.formBean.depBatch
      // params.isChangeEnable = '0'
      // params.authps = this.transData.jobData.platform + this.transData.jobData.system
      params.id = this.formBean.pid
      params.isEnable = this.formBean.isEnable
      let httpConfig = {
        url: RESOURCE_PATH,
        data: params
      }
      // if (!this.isOwnPlatform({ platform: this.transData.jobData.platform })) {
      //   return
      // }
      if (this.transData.id) {
        if (this.orgIsEnabel !== this.formBean.isEnable) {
          params.isChangeEnable = '1'
        }
        // httpConfig.method = 'PUT'
        // httpConfig.url = '/udsjobdependency/' + this.transData.id
        httpConfig.method = 'POST'
        httpConfig.url = '/udsJobDependency/update'
      } else {
        httpConfig.method = 'POST'
      }
      this.$ajax(httpConfig)
        .then(resp => {
          if (resp.status && resp.status == 200) {
            // if (resp.data === 1 && params.isChangeEnable === '1') {
            //   this.$Message.error({
            //     content: '是否启用依赖修改失败！',
            //     duration: 8,
            //     closable: true
            //   })
            // }
            // if (resp.data === 2) {
            //   this.$Message.error({
            //     content: '依赖批次号修改失败！',
            //     duration: 8,
            //     closable: true
            //   })
            // }
            // this.gridData[this.depDlg.index].isEnable = this.formBean.isEnable
            this.search()
          }
          // this.cancel();
        })
      // this.$emit('switch', {statusName: "dep"});
    },
    getPageParam () {
      return { formBean: this.formBean,
        currentPage: this.page.current,
        pageSize: this.page.size }
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
<style>
#depTable table td div{
	/* padding-left: 5px;
	padding-right: 0; */
}
#depTable table th div{
	/* padding-left: 5px;
	padding-right: 0; */
}
#confirmDep .ivu-modal-body{
	padding: 16px;
  font-size:14px;
  line-height:1.5;
  width: 100%;
}
</style>

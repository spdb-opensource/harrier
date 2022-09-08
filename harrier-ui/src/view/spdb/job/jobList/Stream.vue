<template>
	<div>
		<div>
			<!-- <Button type="primary" icon="md-add" @click="save">保存</Button> -->
			<Row>
				<Col span="20">
					<div style="font-size:14px;font-weight: bold; color: #464c5b;margin-left:5px;">触发作业列表
					&nbsp;&nbsp;平台名:{{this.transData.jobData.platform}}
					&nbsp;&nbsp;应用名:{{this.transData.jobData.system}}
					&nbsp;&nbsp;作业名:{{this.transData.jobData.job}}
					</div>
				</Col>
				<Col span="4">
					<Button size="small"  type="primary" icon="md-cloud-download" @click="download" style="">下载</Button>
					<Button size="small" type="primary" icon="ios-arrow-back" @click="cancel" style="">返回</Button>
				</Col>
			</Row>
		</div>
		<div class="spdb-table" id="streamTable">
			<Table border :columns="gridColumns" :data="gridData" stripe @on-sort-change="changeSort">
			</Table>
		</div>
		<div class="spdb-page">
			<Page :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat show-elevator @on-change="changePage" @on-page-size-change="changePageSize"></Page>
		</div>
		<Modal id="streamModForm"
			v-model="stmDlg.show"
			title="修改触发状态">
			<div>
				<div>
					<Form  :label-width="80" >
						<Row>
						<FormItem label="平台名">
							<Input readonly v-model="formBean.platform"/>
						</FormItem>
						</Row>
						<Row>
						<FormItem label="应用名">
							<Input readonly v-model="formBean.system"/>
						</FormItem>
						</Row>
						<Row>
							<Col span="24">
							<FormItem label="作业名">
								<Input readonly type="textarea" :autosize="{minRows: 1,maxRows: 5}" v-model="formBean.job"/>
							</FormItem>
							</Col>
						</Row>
						<Row>
						<FormItem prop="isEnable" label="是否启用">
							<RadioGroup v-model="formBean.isEnable">
								<Radio label='1'><span>启用</span></Radio>
								<Radio label='0'><span>禁用</span></Radio>
							</RadioGroup>
						</FormItem>
						</Row>
						<Row>
							<FormItem label="触发批次号">
								<InputNumber :max="10000" :min="0" v-model="formBean.stmBatch"></InputNumber>
							</FormItem>
						</Row>
					</Form>
				</div>
			</div>
			<div slot="footer">
				<div style="margin-left: 150px;">
					<Button type="primary" @click="showConfirm">确定</Button>
					<Button type="primary" @click="confirmCancel">取消</Button>
				</div>
			</div>
		</Modal>
		<Modal id="confirmSD"
			v-model="confirmSD.show"
			:mask-closable="false"
			title="提示">
			<div>
				<Form  ref="confirmSDForm" :label-width="100"  inline>
					<Alert class="m-warn-con" type="warning" >禁用触发后，该作业不会被触发起来及该作业的触发作业也不会触发起来</Alert>
					<Checkbox class="m-warn-con" v-model="res.isAgree" > 我承担该操作的一切后果</Checkbox>
				</Form>
			</div>
			<div slot="footer">
				<div style="margin-left: 150px;">
					<Button type="primary" @click="sdStmOk">确定</Button>
					<Button type="primary" @click="sdCancel">取消</Button>
				</div>
			</div>
		</Modal>
	</div>
</template>

<script>
import common from '@/mixins/common'
import utils from '@/common/utils'
const RESOURCE_PATH = '/udsjobstream'

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
  data () {
    return {
      confirmSD: { show: false },
      res: { isAgree: true },
      stmDlg: { show: false, index: '' },
      formBean: {},
      gridData: [],
      gridColumns: [
        /** {
					type: 'selection',
					width: 80,
					//align: 'center',
					fixed: 'left'
				}, */
        {
          title: '平台名',
          width: 80,
          sortable: true,
          key: 'platform'
        },
        {
          title: '应用名',
          sortable: true,
          width: 80,
          key: 'system'
        },
        {
          title: '作业名',
          sortable: true,
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
                },
                on: {
                  click: () => {
                    let queryCache = { formBean: this.transData.formBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize, page: this.page }
                    let id = row.job
                    if (this.transData.prequeryCache) {
                      queryCache = this.transData.prequeryCache
                    }
                    if (this.transData.jobStatus) {
                      this.$emit('switch', Object.assign({}, { queryCache: queryCache }, { id: id, jobStatus: this.transData.jobStatus, previd: this.transData.id, jobData: this.transData.jobData, statusName: 'jobdetail', prevTab: 'stream', curTab: 'stream' })) // 提交form事件，在parent中显示form
                    } else {
                      this.$emit('switch', Object.assign({}, { queryCache: queryCache }, { id: id, previd: this.transData.id, jobData: this.transData.jobData, statusName: 'jobdetail', prevTab: 'stream', curTab: 'stream' })) // 提交form事件，在parent中显示form
                    }
                  }
                }
              }, row.job)
            ])
          }
        },
        {
          title: '触发平台名',
          width: 100,
          sortable: true,
          key: 'stmPlatform'
        },
        {
          title: '触发应用名',
          width: 100,
          sortable: true,
          key: 'stmSystem'
        },
        {
          title: '触发作业名',
          key: 'stmJob',
          sortable: true,
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
                on: {
                  click: () => {
                    let queryCache = { formBean: this.transData.formBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize, page: this.page }
                    if (this.transData.prequeryCache) {
                      queryCache = this.transData.prequeryCache
                    }
                    let id = row.stmJob
                    if (this.transData.jobStatus) {
                      this.$emit('switch', Object.assign({}, { queryCache: queryCache }, { id: id, jobStatus: this.transData.jobStatus, previd: this.transData.id, jobData: this.transData.jobData, statusName: 'jobdetail', prevTab: 'stream', curTab: 'stream' })) // 提交form事件，在parent中显示form
                    } else {
                      this.$emit('switch', Object.assign({}, { queryCache: queryCache }, { id: id, previd: this.transData.id, jobData: this.transData.jobData, statusName: 'jobdetail', prevTab: 'stream', curTab: 'stream' })) // 提交form事件，在parent中显示form
                    }
                  }
                }
              }, row.stmJob)
            ])
          }
        },
        {
          title: '描述',
          width: 100,
          key: 'des'
        },
        {
          title: '触发批次号',
          width: 100,
          key: 'stmBatch'
        },
        {
          title: '状态',
          // fixed: 'right',
          width: 80,
          key: 'isEnable',
          render: (h, { column, index, row }) => {
            let statusc = '#19be6b'
            if (row.isEnable == '0') {
              statusc = '#ed3f14'
            }
            /* let ele = 'a'
            if (this.transData.id !== row.job) {
              ele = 'span'
              statusc = '#bbbec4'
            } */
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
                    /*  if (ele === 'a') {
                      this.formBean = Object.assign({}, row)
                      this.formBean.isEnable = '' + row.isEnable
                      this.stmDlg.index = index
                      this.showStmDlg()
                    } */
                  }
                }
              }, row.isEnable != 1 ? '禁用' : '启用')
            ])
          }
        },
        {
          title: '操作',
          // fixed: 'right',
          width: 80,
          key: 'isEnable',
          render: (h, { column, index, row }) => {
            let statusc = '#19be6b'
            if (row.isEnable == '0') {
              statusc = '#ed3f14'
            }
            let ele = 'a'
            if (this.transData.id !== row.job) {
              ele = 'span'
              statusc = '#bbbec4'
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
                    if (ele === 'a') {
                      this.formBean = Object.assign({}, row)
                      this.formBean.isEnable = '' + row.isEnable
                      this.stmDlg.index = index
                      this.showStmDlg()
                    }
                  }
                }
              }, ele === 'a' ? '编辑' : '')
            ])
          }
        }
      ],
      gridData: [],
      // gridData: [{platform: "BDP", system: "DLA", job: "BDP_DLA_TEST",stmPlatform: "BDP", stmSystem: "DLA", stmJob: "BDP_DLA_TEST_STREAM",des:"ss",isEnable: "1"}],
      selection: [],
      page: {
        current: 1,
        total: 10,
        size: 10,
        opt: [10, 50, 100]
      },
      ruleValidate: {
      }
    }
  },
  methods: {
    /**
		 * 初始化
		 **/
    init () {
      if (this.transData.prequeryCache && this.transData.prequeryCache.page) {
        this.page = this.transData.prequeryCache.page
      }
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
    /**
		 * 查询
		 **/
    search () {
      let params = {}
      // Object.assign(params, this.formBean);
      params.currentPage = this.page.current
      params.pageSize = this.page.size
      params.job = this.transData.id
      params.platform = this.transData.jobData.platform
      params.system = this.transData.jobData.system
      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH,
        params: params
      }
      this.$ajax(httpConfig)
        .then(resp => {
          this.gridData = resp.data.rows
          this.page.total = resp.data.total
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
      // console.log("column,key,order"+ column.key );
      this.formBean.order = order
      this.formBean.sort = column.key
      this.search()
    },
    /**
		 * 下载
		 **/
    download () {
      let params = {}
      Object.assign(params, this.formBean)
      params.job = this.transData.id
      params.platform = this.transData.jobData.platform
      params.system = this.transData.jobData.system
      params.type = 'excel'
      utils.download(RESOURCE_PATH + '/downLoad', params)
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
      // console.log(this.transData.formBean);
      let con = {}
      if (this.transData.prequeryCache) {
        if (this.transData.jobStatus) {
          this.transData.prequeryCache.jobStatus = this.transData.jobStatus
        }
        // con.platform = this.transData.jobData.platform;
        this.$emit('switch', Object.assign({}, this.transData.prequeryCache, backParam))
      } else {
        // if (this.transData.jobStatus) {
        //	this.transData.prequeryCache.jobStatus = this.transData.jobStatus;
        // }
        this.$emit('switch', Object.assign({}, queryCache, backParam, { jobData: this.transData.jobData }))
      }
    },
    showStmDlg () {
      this.stmDlg.show = true
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
    stmOk () {
      let params = {}
      Object.assign(params, this.formBean)
      params.authps = this.transData.jobData.platform + this.transData.jobData.system
      let httpConfig = {
        url: RESOURCE_PATH,
        data: params
      }
      if (!this.isOwnPlatform({ platform: this.transData.jobData.platform })) {
        return
      }
      if (this.transData.id) {
        httpConfig.method = 'PUT'
        httpConfig.url = RESOURCE_PATH + '/' + this.transData.id
      } else {
        httpConfig.method = 'POST'
      }
      this.$ajax(httpConfig)
        .then(resp => {
          if (resp.status && resp.status == 200) {
            this.gridData[this.stmDlg.index].isEnable = this.formBean.isEnable
            this.stmDlg.show = false
            this.confirmSD.show = false
            this.search()
          }
          // this.cancel();
        })
			   // this.$emit('switch', {statusName: "stream",});
    },
    showConfirm () {
      if (this.formBean.isEnable == 0) {
        this.confirmSD.show = true
      } else {
        this.stmOk()
      }
    },
    confirmCancel () {
      this.stmDlg.show = false
    },
    sdStmOk () {
      if (!this.res.isAgree == true) {
        this.$Message.warning({ content: '请同意 我承担该操作的一切后果!' })
        return
      }
      this.formBean.isAgree = this.res.isAgree
      this.stmOk()
    },
    sdCancel () {
      this.confirmSD.show = false
    }
  },
  /**
	 * 视图挂载
	 **/
  mounted () {
    // console.log(this.transData.jobStatus);
    // this.init();
  }
}

</script>
<style>
#streamModForm .ivu-modal-body{
	padding:16px;font-size:14px;line-height:1.5;width: 100%;
}
#streamTable table td div{
	padding-left: 5px;
	padding-right: 0;
}
#streamTable table th div{
	padding-left: 5px;
	padding-right: 0;
}
#confirmSD .ivu-modal-body{
	padding:16px;font-size:14px;line-height:1.5;width: 100%;
}
</style>

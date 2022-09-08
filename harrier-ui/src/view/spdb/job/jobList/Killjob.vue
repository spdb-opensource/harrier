<template>
	<div>
		<Form ref="mkillForm" :model="formBean" :label-width="70" :rules="formRule">
			<Row>
				<Col span="3">
					<FormItem prop="platform" label="平台名">
						<Input readonly v-model="formBean.platform"/>
					</FormItem>
				</Col>
				<Col span="3">
					<FormItem prop="system" label="应用名">
						<Input readonly v-model="formBean.system"/>
					</FormItem>
				</Col>
				<Col span="6">
					<FormItem prop="job" label="作业名">
						<Input readonly v-model="formBean.job"/>
					</FormItem>
				</Col>
				<Col span="3">
					<FormItem prop="jobType" label="kill类型">
						<Select v-model="formBean.jobType" filterable clearable @on-change="jobTypeChange">
							<Option v-for="item in jobTypeData" :value="item.value" :key="item.key">{{ item.label }}</Option>
						</Select>
					</FormItem>
				</Col>
				<Col span="4">
					<FormItem prop="param" label="参数">
						<Input v-model="formBean.param" placeholder="请输入参数" :disabled="paramDisable"/>
					</FormItem>
				</Col>
				<Col span="5" v-if="showApplicationId">
						<p style="color:red;font-size:12px;height:32px;line-height:32px" >*提示:{{formBean.tip}}</p>
				</Col>
			</Row>

			<Row>
				<Col span="5">
				 	<Button type="primary" icon="md-add" :disabled="bdisabled" @click="jobkill">Kill</Button>
					<Button type="primary" icon="ios-arrow-back" :disabled="bdisabled" @click="cancel" style="">返回</Button>
				</Col>
			</Row>

		</Form>

		<Modal id="confirmM"
			v-model="confirm.show"
			:mask-closable="false"
			title="提示">
			<div>
				<Alert class="m-warn-con" type="warning" >确认kill作业:&#8195{{confirm.job}}</Alert>
				<Alert class="m-warn-con" type="warning" >&#8195作业类型:&#8195{{confirm.jobType}}</Alert>
				<Alert class="m-warn-con" type="warning" >&#8195&#8195&#8195参数:&#8195{{confirm.param}}</Alert>
			</div>
			<div slot="footer">
				<div style="margin-left: 150px;">
					<Button type="primary" @click="save">确定</Button>
					<Button type="primary" @click="confirmCancel">取消</Button>
				</div>
			</div>
		</Modal>

		<div class="spdb-table">
			<Table :columns="gridColumns" :data="gridData" stripe >
			</Table>
		</div>

		<div class="spdb-page">
			<Page placement="top" :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat @on-change="changePage" @on-page-size-change="changePageSize"></Page>
		</div>

	</div>
</template>

<script>
import utils from '@/common/utils'

const RESOURCE_PATH = '/mkill'

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
      formBean: {},
      showApplicationId: false,
      bdisabled: false,
      paramDisable: false,
      confirm: {
        show: false
      },
      tipData: [],
      jobTypeData: [],
      gridData: [],
      page: {
        current: 1,
        total: 10,
        size: 10,
        opt: [10, 50, 100]
      },
      formRule: {
        jobType: [{
          required: true,
          message: '请输入数据！'
        }],
        param: [{
          validator: (rule, value, callback, source, options) => {
            if ((this.formBean.jobType == 'Hive' || this.formBean.jobType == 'Other') && (this.formBean.param == '' || this.formBean.param == null)) {
              callback(new Error('请输入数据！'))
            } else {
              return callback()
            }
          },
          trigger: 'blur'
        }]
      },
      gridColumns: [
        {
          title: '平台名',
          key: 'platform',
          width: 90
        },
        {
          title: '应用名',
          key: 'system',
          width: 90
        },
        {
          title: '作业名',
          key: 'job'
        },
        {
          title: '参数',
          key: 'param'
        },
        {
          title: '作业类型',
          key: 'jobType',
          width: 120
        },
        {
          title: '用户',
          key: 'userName',
          width: 100
        },
        {
          title: '操作时间',
          key: 'operateTime',
          width: 175
        },
        {
          title: '状态',
          key: 'status',
          width: 80
        },
        {
          title: '日志信息',
          width: 100,
          render: (h, { column, index, row }) => {
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
                    let params = {}
                    this.formBean.operateLog = row.operateLog
 										Object.assign(params, this.formBean)
                    params.check = '1'
                    params.type = 'excel'
                    let loadConfig = {
                      method: 'GET',
                      url: RESOURCE_PATH + '/logdownload',
                      params: params
                    }
                    this.$ajax(loadConfig)
                      .then(resp => {
                        if (resp.data.returnCode && resp.data.returnCode == 'fail') {
                          this.$Message.warning({
                            content: '日志不存在!',
                            duration: 5,
                            closable: true
                          })
                        } else {
                          params.check = '0'
                          utils.download(RESOURCE_PATH + '/logdownload', params)
                        }
                      })
                  }
                }
              }, '下载')
            ])
          }
        }
      ]
    }
  },
  methods: {
    /**
		 * 初始化
		 **/
    init () {
      this.bindData()
      this.search()
    },
    queryTipList () {
      let loadConfig = {
        method: 'GET',
        url: '/dictionary',
        params: { dicCode: 'm_kill_tip' }
      }
      this.$ajax(loadConfig)
        .then(resp => {
          resp.data.rows.forEach(data => {
            let tmp = {}
            tmp.value = data.dicValue
            tmp.label = data.dicName
            // this.formBean.tip=data.dicValue;
            this.tipData.push(tmp)
          })
        })
    },
    queryJobTypeList () {
      let loadConfig = {
        method: 'GET',
        url: '/dictionary',
        params: { dicCode: 'm_kill_jobtype' }
      }
      this.$ajax(loadConfig)
        .then(resp => {
          // this.jobTypeData = [];
          resp.data.rows.forEach(data => {
            let tmp = {}
            tmp.value = data.dicValue
            tmp.label = data.dicName
            this.jobTypeData.push(tmp)
          })
        })
    },
    /**
		 * 如果传过来的有初始数据则进行数据绑定
		 **/
    bindData () {
      try {
        // this.formBean = Object.assign({}, this.transData.jobData);
        this.formBean.platform = this.transData.jobData.platform
        this.formBean.system = this.transData.jobData.system
        this.formBean.job = this.transData.jobData.job
        if(this.transData.jobData.isComplement){
          this.formBean.jobId = this.transData.jobData.id
          this.formBean.isComplement = this.transData.jobData.isComplement
        }
      } catch (error) {
        console.error(error)
      }
    },
    jobTypeChange () {
      if (this.formBean.jobType) {
        this.showApplicationId = true
      } else {
        this.showApplicationId = false
      }

      if (this.formBean.jobType == 'Hive' || this.formBean.jobType == 'Other') {
        this.paramDisable = false
      } else {
        this.paramDisable = true
      }

      this.tipData.forEach(data => {
        if (this.formBean.jobType == data.label) {
          this.formBean.tip = data.value
        }
      })
    },
    jobkill () {
      this.$refs.mkillForm.validate(valid => {
        if (!valid) return
        this.confirm.show = true
        this.confirm.job = this.formBean.job
        this.confirm.jobType = this.formBean.jobType
        this.confirm.param = this.formBean.param
      })
    },
    confirmCancel () {
      this.confirm.show = false
    },
    /**
		 * 保存表单
		 **/
    save () {
      this.confirm.show = false
      let params = {}
      Object.assign(params, this.formBean)
      params.authps = this.formBean.platform + this.formBean.system
      let httpConfig = {
        url: RESOURCE_PATH,
        data: params,
        method: 'POST'
      }
      this.bdisabled = true
      this.$ajax(httpConfig)
        .then(resp => {
          this.bdisabled = false
          this.search()
        })
    },
    /**
		 * 返回到数据视图
		 **/
    cancel () {
      let prevTab = ''
      if (this.transData.tabObj) {
        prevTab = this.transData.tabObj.prevTab
      } else if (this.transData.prevTab) {
        prevTab = this.transData.prevTab
      }
      let queryCache = { formBean: this.transData.formBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize }
      let backParam = { id: this.transData.previd, statusName: 'table', prevTab: prevTab, type: 'back', curTab: 'jobdetail' }
      if (this.transData.jobStatus) {
        queryCache.jobStatus = this.transData.jobStatus
      }
      if (this.transData.jobData) {
        this.$emit('switch', Object.assign({}, queryCache, backParam, { prequeryCache: this.transData.queryCache }, { jobData: this.transData.jobData }))
      } else {
        this.$emit('switch', Object.assign({}, queryCache, backParam))
      }
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
		 * 查询
		 **/
    search () {
      let params = {}
      params.currentPage = this.page.current
      params.pageSize = this.page.size
      params.job = this.formBean.job
      params.platform = this.formBean.platform
      params.system = this.formBean.system
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
    }
  },

  /**
	 * 视图挂载
	 **/
  mounted () {
    this.queryJobTypeList()
    this.queryTipList()
    this.init()
  }
}

</script>
<style>
#confirmM .ivu-modal-body{
	padding:16px;font-size:12px;line-height:1.5;width: 100%;
}
</style>

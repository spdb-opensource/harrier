<template>
	<div>
		<div>
			<Row>
				<Col span="20">
					<div style="font-size:14px;font-weight: bold; color: #464c5b;margin-left:50px;">定时信息</div>
				</Col>
				<Col span="4">
					<Button size="small" type="primary" icon="ios-arrow-back" @click="cancel" style="">返回</Button>
				</Col>
			</Row>
		</div>
		<Form ref="udsjobdatetriggerForm" :model="formBean" :label-width="220" :rules="formRule">
			<!--<Row>
				<Col span="8">
					<FormItem prop="platform" label="平台">
						<Input readonly v-model="formBean.platform"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="system" label="系统">
						<Input readonly v-model="formBean.system"/>
					</FormItem>
				</Col>
			</Row>-->
			<Row>
				<Col span="8">
					<FormItem prop="job" label="作业名">
						<Input readonly v-model="formBean.job"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="startTime" label="下次执行时间">
						<Input readonly v-model="formBean.startTime"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<!--<Col span="8">
					<FormItem prop="endTime" label="停止时间">
						<Input readonly v-model="formBean.endTime"/>
					</FormItem>
				</Col>-->
				<Col span="8">
					<FormItem prop="second" label="秒">
						<Input readonly v-model="formBean.second"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="minute" label="分">
						<Input readonly v-model="formBean.minute"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="hour" label="时">
						<Input readonly v-model="formBean.hour"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="day" label="日">
						<Input readonly v-model="formBean.day"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="month" label="月">
						<Input readonly v-model="formBean.month"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="week" label="周">
						<Input readonly v-model="formBean.week"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="year" label="年">
						<Input readonly v-model="formBean.year"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="isEnable" label="是否启用">
						<Input readonly v-model="formBean.isEnable"/>
					</FormItem>
				</Col>
				<!-- <Col span="8">
					<FormItem prop="offsetDay" label="偏移天数">
						<Input readonly v-model="formBean.offsetDay"/>
					</FormItem>
				</Col> -->
			</Row>
			<!-- <Row>
				<Col span="8">
					<FormItem prop="isEnable" label="是否启用">
						<Input readonly v-model="formBean.isEnable"/>
					</FormItem>
				</Col>
			</Row> -->
			<!--<Row>
				<Col span="8">
					<FormItem prop="des" label="描述">
						<Input readonly v-model="formBean.des"/>
					</FormItem>
				</Col>
			</Row>-->
		</Form>
	</div>
</template>

<script>
const RESOURCE_PATH = '/udsJobTimeTrigger'

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
      formRule: {
      }
    }
  },
  methods: {
    /**
		 * 初始化
		 **/
    init () {
      let params = {}
      params.platform = this.transData.jobData.platform
      params.system = this.transData.jobData.systems
      params.job = this.transData.jobData.job
      if (this.transData.id) { // 如果传过来的存在ID则说明是编辑功能跳转过来
        let loadConfig = {
          method: 'GET',
          url: RESOURCE_PATH + '/selectJobTrigger',
          params: params
        }
        this.$ajax(loadConfig)
          .then(resp => {
            if (resp.status && resp.status === 200) {
              if (resp.data) {
                resp.data.isEnable == 0 ? resp.data.isEnable = '禁用' : resp.data.isEnable = '启用'
                this.formBean = resp.data
              }
            }
          })
      } else {
        this.bindData()
      }
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
		 * 保存表单
		 **/
    save () {
      this.$refs.udsjobdatetriggerForm.validate(valid => {
        if (!valid) return
        let params = {}
        Object.assign(params, this.formBean)
        let httpConfig = {
          url: RESOURCE_PATH,
          data: params
        }
        if (this.transData.id) {
          httpConfig.method = 'PUT'
          httpConfig.url = RESOURCE_PATH + '/' + this.transData.id
        } else {
          httpConfig.method = 'POST'
        }
        this.$ajax(httpConfig)
          .then(resp => {
            this.cancel()
          })
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

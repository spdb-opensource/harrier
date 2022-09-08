<template>
	<div>
		<div style="margin-bottom:5px;">
			<Row>
				<Col span="20">
					<div style="font-size:14px;font-weight: bold; color: #464c5b;margin-left:5px;">
					修改状态
					&nbsp;&nbsp;平台名:{{this.formBean.platform}}
					&nbsp;&nbsp;应用名:{{this.formBean.system}}
					&nbsp;&nbsp;作业名:{{this.formBean.job}}
					</div>
				</Col>
				<Col span="4">
					<Button size="small" type="primary" icon="ios-arrow-back" @click="cancel" style="">返回</Button>
				</Col>
			</Row>
		</div>
		<Form ref="statusForm" :model="formBean" :label-width="160" :rules="formRule">
			<Row>
				<Col span="8">
					<FormItem prop="platform" label="平台名">
						<Input readonly v-model="formBean.platform"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="system" label="应用名">
						<Input readonly v-model="formBean.system"/>
					</FormItem>
				</Col>
				</Row>
			<Row>
				<Col span="8">
					<FormItem prop="job" label="作业名">
						<Input readonly v-model="formBean.job"/>
					</FormItem>
				</Col>
			</Row>
			<!-- <Row>
				<Col span="8">
					<FormItem prop="jobDate" label="TXDate">
						<DatePicker :transfer="true"type="date" parse="yyyyMMdd" format="yyyyMMdd" v-model="formBean.jobDate"></DatePicker>
					</FormItem>
				</Col>
			</Row> -->
			<Row>
				<Col span="8">
					<FormItem prop="lastStatus" label="作业状态">
						<RadioGroup v-model="formBean.lastStatus">
							<Radio label='Pending'><span>Pending</span></Radio>
							<Radio label='Ready'><span>Ready</span></Radio>
							<Radio label='Done'><span>Done</span></Radio>
						</RadioGroup>
					</FormItem>
				</Col>
				</Row>

		</Form>

		<div>
			<Button type="primary" icon="md-add" @click="save">保存</Button>
			<Button type="primary" icon="ios-trash-outline" @click="cancel">取消</Button>
		</div>
	</div>
</template>

<script>
const RESOURCE_PATH = '/udsjob'

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
      srcJob: {},
      formRule: {
        lastStatus: [{required: true, message: '不能为空'}]
      }
    }
  },
  methods: {
    /**
		 * 初始化
		 **/
    init () {
      if (this.transData.id) { // 如果传过来的存在ID则说明是编辑功能跳转过来
        let loadConfig = {
          method: 'GET',
          url: RESOURCE_PATH + '/' + this.transData.id
        }
				this.$ajax(loadConfig)
          .then(resp => {
            this.srcJob = Object.assign({}, resp.data)
					this.formBean = resp.data
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
      this.$refs.statusForm.validate(valid => {
        if (!valid) return
				let params = {}
				Object.assign(params, this.formBean)
				params = {}
				params.jobDate = this.formBean.jobDate
				params.lastStatus = this.formBean.lastStatus
				params.authps = this.formBean.platform + this.formBean.system
				if (!(this.srcJob.lastStatus == 'Done' || this.srcJob.lastStatus == 'Failed')) {
          this.$Message.error({
            content: '当前作业状态不为Done或Failed',
            duration: 15,
            closable: true
          })
					return;
        }
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
            if (resp.status && resp.status == 200) {
              this.cancel()
					}
          })
			})
		},
    /**
		 * 返回到数据视图
		 **/
    cancel () {
      let prevTab = '';
      if (this.transData.tabObj) {
        prevTab = this.transData.tabObj.prevTab
				prevTab = 'joblist';
      }
      let queryCache = {formBean: this.transData.formBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize}
			let backParam = {statusName: 'table', prevTab: prevTab, type: 'back'}
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

<template>
	<div>
		<div style="margin-bottom:5px;">
			<Row>
				<Col span="20">
					<div style="font-size:14px;font-weight: bold; color: #464c5b;margin-left:5px;">
					INVOKE
					&nbsp;&nbsp;平台名:{{this.formBean.platform}}
					&nbsp;&nbsp;应用名:{{this.formBean.systems}}
					&nbsp;&nbsp;作业名:{{this.formBean.job}}
					</div>
				</Col>
				<Col span="4">
					<Button size="small" type="primary" icon="ios-arrow-back" @click="cancel" style="">返回</Button>
				</Col>
			</Row>
		</div>
		<Form ref="invokeForm" :model="formBean" :label-width="160" :rules="formRule">
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
						<Input readonly v-model="formBean.systems"/>
					</FormItem>
				</Col>
				</Row>
			<Row>
				<Col span="8">
					<FormItem prop="job" label="作业名">
						<Input readonly type="textarea" :autosize="{minRows: 1,maxRows: 5}" v-model="formBean.job"/>
					</FormItem>
				</Col>
				</Row>
				<Row>
					<Col span="8">
						<FormItem prop="job" label="状态">
							<Input readonly v-model="formBean.lastStatus"/>
						</FormItem>
					</Col>
				</Row>
				<Row>
				<Col span="8">
					<FormItem prop="jobDate" label="TXDate">
						<DatePicker :transfer="true"type="date" parse="yyyyMMdd" format="yyyyMMdd" v-model="formBean.jobDate"></DatePicker>
					</FormItem>
				</Col>
				</Row>

				<Row>
				<Col span="8">
					<FormItem prop="multiBatch" label="批次号">
						<Input v-model="formBean.multiBatch" :disabled="batchDisabled"/>
					</FormItem>
				</Col>
				</Row>

		</Form>

		<div style="margin-left: 150px;">
			<Button type="primary" icon="md-add" @click="confirmBtn">invoke</Button>
			<Button type="primary" icon="ios-trash-outline" @click="cancel">取消</Button>
		</div>
		<Modal id="confirmM"
			v-model="confirm.show"
			:mask-closable="false"
			title="提示">
			<div>
				<Alert type="warning" class="m-warn-con">
          <span style="font-weight:bold">友情提示:</span><br/>
          <div style="margin-left:4%">
          1.invoke会检查依赖<br/>
          2.invoke会触发下游作业执行<br/>
          3.invoke会修改TXDate<br/>
          4.invoke会修改作业状态
          </div>
          <span style="font-weight:bold">注意事项:</span><br/>
          <div style="margin-left:4%">
          1.invoke执行完后请修改TXDate到正常的日期
          </div>
          <!-- invoke会检查依赖,触发下游作业执行<br/>
          invoke执行完后请修改TXDate到正常的日期 -->
        </Alert>
			</div>
			<div slot="footer">
				<div style="margin-left: 150px;">
					<Button type="primary" :loading="loading" @click="save">确定</Button>
					<Button type="primary"  @click="confirmCancel">取消</Button>
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
      batchDisabled: true,
      confirm: {
        show: false
      },
      formBean: {},
      loading: false,
      oldJobDate: '',
      formRule: {
        systems: [{
          required: true,
          message: '请输入数据！'
        }],
        job: [{
          required: true,
          message: '请输入数据！'
        }],
        jobType: [{
          required: true,
          message: '请输入数据！'
        }],
        priority: [{
          required: true,
          message: '请输入数据！'
        }],
        timewindow: [{
          required: true,
          message: '请输入数据！'
        }],
        isEnable: [{
          required: true,
          message: '请输入数据！'
        }],
        isCheckFrequency: [{
          required: true,
          message: '请输入数据！'
        }]
      }
    }
  },
  methods: {
    /**
		 * 初始化
		 **/
    init () {
      this.oldJobDate = this.transData.jobData.jobDate
      this.formBean = this.transData.jobData
      this.batchDisabled = (this.formBean.multiBatch === 0)
    },
    confirmCancel () {
      this.confirm.show = false
    },
    confirmBtn () {
      this.formBean.jobDate = utils.fmtDate(this.formBean.jobDate, 'yyyyMMdd')
      let diffdays = utils.dateDiff(this.oldJobDate.replace(/\-/g, ''), this.formBean.jobDate, 'date')
      if (this.formBean.multiBatch === 0 && diffdays <= 0) {
        this.$Message.error({
          content: '单批次作业TXDate:' + this.formBean.jobDate + '应大于当前TXDate:' + this.oldJobDate,
          duration: 15,
          closable: true
        })
        return
      } else if (this.formBean.multiBatch > 0 && diffdays < 0) {
        this.$Message.error({
          content: '多批次作业TXDate:' + this.formBean.jobDate + '应大于等于当前TXDate:' + this.oldJobDate,
          duration: 15,
          closable: true
        })
        return
      }
      if (!(this.formBean.lastStatus === 'Done' || this.formBean.lastStatus === 'Ready')) {
        this.$Message.error({
          content: '当前作业状态不为Done或Ready',
          duration: 15,
          closable: true
        })
        return
      }
      this.confirm.show = true
    },
    save () {
      let params = {}
      params.platform = this.formBean.platform
      params.systems = this.formBean.systems
      params.job = this.formBean.job
      params.jobdate = this.formBean.jobDate
      params.multibatch = this.formBean.multiBatch
       
      // params.datalist = [this.formBean]
      // params.reqparams = {}
      // params.reqparams.invokeType = '0'
      // params.reqparams.oldJobDate = this.oldJobDate.replace(/-/g,'')
      // params.requesttype = 'lo'
      // params.authps = this.formBean.platform + this.formBean.system
      let httpConfig = {
        method: 'POST',
        url: RESOURCE_PATH + '/invokeJob',
        data: params
      }
      // httpConfig.method = 'POST'
      // httpConfig.async = false;
      this.loading = true
      this.$ajax(httpConfig)
        .then(resp => {
          this.loading = false
          // console.log(resp.data);
          if (resp.data === 1) {
            this.confirm.show = false
            this.$Message.success({
              content: 'invoke成功！',
              duration: 15,
              closable: true
            })
          } else {
            this.$Message.error({
              content: 'invoke失败！',
              duration: 15,
              closable: true
            })
            return
          }
          this.cancel()
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
      let queryCache = {formBean: this.transData.formBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize}
      let backParam = {statusName: 'table', prevTab: prevTab, type: 'back'}
      if (this.transData.jobStatus) {
        queryCache.jobStatus = this.transData.jobStatus
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
<style>
#confirmM .ivu-modal-body{
	padding:16px;font-size:12px;line-height:1.5;width: 100%;
}
</style>

<template>
	<div>
		<div>
			<!-- <Button type="primary" icon="md-add" @click="save">保存</Button> -->
			<Row>
				<Col span="20">
					<div style="font-size:14px;font-weight: bold; color: #464c5b;margin-left:50px;">报警详细信息</div>
				</Col>
				<Col span="4">
					<Button size="small" type="primary" icon="ios-arrow-back" @click="cancel" style="">返回</Button>
				</Col>
			</Row>
		</div>
		<Form ref="alarmForm" :model="formBean" :label-width="140" :rules="formRule">
			<Row>
				<Col span="8">
					<FormItem prop="platform" label="平台名">
						<Input v-model="formBean.platform"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="system" label="应用名">
						<Input v-model="formBean.system"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="job" label="作业名">
						<Input v-model="formBean.job" type="textarea" :autosize="{minRows: 1,maxRows: 5}"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="code" label="告警码">
						<Input v-model="formBean.code"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="alarmType" label="告警类型">
						<Input v-model="formBean.alarmType"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="alarmLevel" label="告警级别">
						<Input v-model="formBean.alarmLevel"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="times" label="告警时间">
						<Input v-model="formBean.times"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="alarmStatus" label="告警状态">
						<Input v-model="formBean.alarmStatus"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="noticeGroupName" label="告警通知用户组">
						<Input v-model="formBean.noticeGroupName"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="noticeCount" label="告警发送当前次数">
						<Input v-model="formBean.noticeCount"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="noticeTimes" label="通知次数">
						<Input v-model="formBean.noticeTimes"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="noticeCyce" label="通知周期">
						<Input v-model="formBean.noticeCyce"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="noticeSendTime" label="告警最后发送时间">
						<Input v-model="formBean.noticeSendTime"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="operationType" label="处理方式">
						<Input v-model="formBean.operationType"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="operationTime" label="处理时间">
						<Input v-model="formBean.operationTime"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="operationUser" label="处理用户">
						<Input v-model="formBean.operationUser"/>
					</FormItem>
				</Col>
			</Row>
			<!-- <Row>
				<Col span="8">
					<FormItem prop="operationUser" label="处理人">
						<Input v-model="formBean.operationUser"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem  prop="operation" label="处理方式">
						<Select filterable v-model="formBean.operation" >
							<Option v-for="item in operations" :value="item.value" :key="item.value">{{ item.label }}</Option>
						</Select>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="operationTime" label="处理时间">
						<Input v-model="formBean.operationTime"/>
					</FormItem>
				</Col>
			</Row>

			<Row>
				<Col span="8">
					<FormItem prop="smsSendTime" label="短信发送时间">
						<Input v-model="formBean.smsSendTime"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="sendCount" label="短信次数">
						<Input v-model="formBean.sendCount"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="emailSendTime" label="邮件发送时间">
						<Input v-model="formBean.emailSendTime"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="ovoCount" label="邮件次数">
						<Input v-model="formBean.emailCount"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="ovoSendTime" label="OVO发送时间">
						<Input v-model="formBean.ovoSendTime"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="ovoCount" label="OVO次数">
						<Input v-model="formBean.ovoCount"/>
					</FormItem>
				</Col>
			</Row> -->
			<!-- <Row>
				<Col span="8">
					<FormItem prop="isSend" label="是否发送邮件">
						<Input v-model="formBean.isSend"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="isSendcallp" label="是否发送手机">
						<Input v-model="formBean.isSendcallp"/>
					</FormItem>
				</Col>

			</Row> -->
			<Row>
				<Col span="20">
					<FormItem prop="srcContent" label="告警原始内容">
						<Input type="textarea" :autosize="{minRows: 1,maxRows: 5}" v-model="formBean.srcContent"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="20">
					<FormItem prop="srcParam" label="告警内容参数">
						<Input type="textarea" :autosize="{minRows: 1,maxRows: 10}" v-model="formBean.srcParam"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="20">
					<FormItem prop="title" label="告警标题">
						<Input type="textarea" :autosize="{minRows: 1,maxRows: 5}" v-model="formBean.title"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="20">
					<FormItem prop="content" label="告警内容">
						<Input v-model="formBean.content"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="20">
					<FormItem prop="remark" label="备注">
						<Input v-model="formBean.remark"/>
					</FormItem>
				</Col>
			</Row>
		</Form>

		<!-- <div>
			<Button type="primary" icon="md-add" @click="save">保存</Button>
			<Button type="primary" icon="ios-trash-outline" @click="cancel">取消</Button>
		</div> -->
	</div>
</template>

<script>
import utils from '@/common/utils'
const RESOURCE_PATH = '/alarm'

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

      },
      operations: [
        {
          label: '置虚跳过',
          value: '0'
        },
        {
          label: '再次执行',
          value: '1'
        },
        {
          label: '手工触发',
          value: '2'
        },
        {
          label: '置Done',
          value: '3'
        },
        {
          label: '中止执行',
          value: '4'
        },
        {
          label: '自动完成',
          value: '5'
        }
      ]
    }
  },
  methods: {
    /**
		 * 初始化
		 **/
    init () {
      console.log(this.transData)
      if (this.transData.row) { // 如果传过来的存在ID则说明是编辑功能跳转过来
        this.formBean = Object.assign({}, this.transData.row)
        // let loadConfig = {
        //   method: 'GET',
        //   url: RESOURCE_PATH + '/' + this.transData.id
        // }
        // this.$ajax(loadConfig)
        //   .then(resp => {
        //     let handlestatus = '已处理'
        //     if (resp.data.alarmStatus == '0') {
        //       handlestatus = '未处理'
        //     } else if (resp.data.alarmStatus == '2') {
        //       handlestatus = '暂停报警'
        //     }
        //     resp.data.alarmStatus = handlestatus
        //     resp.data.isSend == 1 ? resp.data.isSend = '是' : resp.data.isSend = '否'
        //     resp.data.isSendcallp == 1 ? resp.data.isSendcallp = '是' : resp.data.isSendcallp = '否'
        //     this.formBean = resp.data
        //     // this.loaduser()
        //   })
      } else {
        this.bindData()
      }
    },
    downloadLogfile () {
      let params = {}
      Object.assign(params, this.formBean)
      utils.download('/udsjobsteprecord/downloadLogfile', params)
      // RESOURCE_PATH +
    },
    downloadScriptfile () {
      let params = {}
      Object.assign(params, this.formBean)
      params.scriptName = this.formBean.runningScript
      utils.download('/udsjobsteprecord/downloadScriptfile', params)
      // RESOURCE_PATH +
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
      this.$refs.alarmForm.validate(valid => {
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
      let backParam = { id: this.transData.previd, statusName: 'table', prevTab: prevTab, type: 'back', curTab: 'alarmdetailTab' }
      this.$emit('switch', Object.assign({}, queryCache, backParam))
      // this.$emit('switch');
    },
    loaduser: function () {
      const loadConfig = {
        method: 'GET',
        url: 'user/getUserList',
        params: {}
      }
      this.$ajax(loadConfig).then(resp => {
        if (resp.data) {
          let userMap = {}
          resp.data.forEach(data => {
            // this.userlist[data.user_id] = data.userchname;
            userMap[data.username] = data.userchname
          })
          // console.log(userMap[this.formBean.mcontactuser])
          this.formBean.mcontactuser = userMap[this.formBean.mcontactuser] ? userMap[this.formBean.mcontactuser] : this.formBean.mcontactuser
          this.formBean.scontactuser = userMap[this.formBean.scontactuser] ? userMap[this.formBean.scontactuser] : this.formBean.scontactuser
          this.formBean.operationUser = userMap[this.formBean.operationUser]
        }
        // this.search();
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

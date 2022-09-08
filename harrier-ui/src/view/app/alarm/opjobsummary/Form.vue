<template>
	<div>
		<Form ref="alarmForm" :model="formBean" :label-width="100" :rules="formRule">
			<Row>
				<Col span="12">
					<FormItem prop="runningScript" label="运行中脚本">
						<Input v-model="formBean.runningScript"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="lastStartTime" label="最后开始时间">
						<Input v-model="formBean.lastStartTime"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem prop="lastEndTime" label="最后结束时间">
						<Input v-model="formBean.lastEndTime"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="lastJobStatus" label="作业最终状态">
						<Input v-model="formBean.lastJobStatus"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem prop="lastJobDate" label="最后TX日期">
						<Input v-model="formBean.lastJobDate"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="numTimes" label="作业执行次数">
						<Input v-model="formBean.numTimes"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem prop="alarmType" label="报警类型">
						<Input v-model="formBean.alarmType"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="updateDate" label="更新时间">
						<Input v-model="formBean.updateDate"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem prop="alarmTime" label="报警时间">
						<Input v-model="formBean.alarmTime"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="lastSendTime" label="最后发送时间">
						<Input v-model="formBean.lastSendTime"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem prop="influnceSys" label="影响应用">
						<Input v-model="formBean.influnceSys"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="mcontactuser" label="主要联系人">
						<Input v-model="formBean.mcontactuser"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem prop="scontactuser" label="次要联系人">
						<Input v-model="formBean.scontactuser"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="operation" label="处理方式">
						<Input v-model="formBean.operation"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem prop="operationTime" label="处理时间">
						<Input v-model="formBean.operationTime"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="operationUser" label="处理人">
						<Input v-model="formBean.operationUser"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem prop="alarmStatus" label="报警状态">
						<Input v-model="formBean.alarmStatus"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="sendCount" label="报警发送">
						<Input v-model="formBean.sendCount"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem prop="logPath" label="日志路径">
						<Input v-model="formBean.logPath"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="isSend" label="是否发送">
						<Input v-model="formBean.isSend"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem prop="isSendcallp" label="是否发送手机">
						<Input v-model="formBean.isSendcallp"/>
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
				operation: [{
					required: true,
					message: '请输入数据！'
				}],
				operationTime: [{
					required: true,
					message: '请输入数据！'
				}],
				operationUser: [{
					required: true,
					message: '请输入数据！'
				}],
				alarmStatus: [{
					required: true,
					message: '请输入数据！'
				}],
			}
		}
	},
	methods: {
		/**
		 * 初始化
		 **/
		init () {
			if(this.transData.id) { //如果传过来的存在ID则说明是编辑功能跳转过来
				let loadConfig = {
					method: 'GET',
					url: RESOURCE_PATH +'/'+ this.transData.id
				};
				this.$ajax(loadConfig)
				.then(resp => {
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
			this.$refs.alarmForm.validate(valid => {
				if(!valid) return
				let params = {}
				Object.assign(params, this.formBean);
				let httpConfig = {
					url: RESOURCE_PATH,
					data: params
				};
				if(this.transData.id) {
					httpConfig.method = 'PUT'
					httpConfig.url = RESOURCE_PATH +'/'+ this.transData.id
				}else {
					httpConfig.method = 'POST'
				}
				this.$ajax(httpConfig)
				.then(resp => {
					this.cancel()
				});
			});
		},
		/**
		 * 返回到数据视图
		 **/
		cancel () {
			this.$emit('switch')
		},
	},
	/**
	 * 视图挂载
	 **/
	mounted () {
		this.init()
	}
}

</script>

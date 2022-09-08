<template>
	<div>
		<Form ref="udsjobForm" :model="formBean" :label-width="160" :rules="formRule">
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
						<Input v-model="formBean.job"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="serverName" label="执行节点分配">
						<Input v-model="formBean.serverName"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="jobType" label="作业类型">
						<Input v-model="formBean.jobType"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="jobDate" label="最后执行作业日期">
						<DatePicker :transfer="true"type="date" parse="yyyy-MM-dd" format="yyyy-MM-dd" v-model="formBean.jobDate"></DatePicker>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="priority" label="作业执行优先级 ">
						<Input v-model="formBean.priority"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="timewindow" label="窗口执行时间">
						<Input v-model="formBean.timewindow"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="isEnable" label="是否执行 ">
						<Input v-model="formBean.isEnable"/>
					</FormItem>
				</Col>
				<Col span="8">
					<FormItem prop="isCheckFrequency" label="是否检测时间">
						<Input v-model="formBean.isCheckFrequency"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="isCheckTimeTrigger" label="检测是否采用时间触发">
						<Input v-model="formBean.isCheckTimeTrigger"/>
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
const RESOURCE_PATH = '/udsjob';

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
				platform: [{
					required: true,
					message: '请输入数据！'
				}],
				system: [{
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
					this.formBean = resp.data;
				})
			} else {
				this.bindData();
			}
		},
		/**
		 * 如果传过来的有初始数据则进行数据绑定
		 **/
		bindData () {
            try {
                this.formBean = Object.assign({}, this.transData.initFormBean);
            } catch (error) {
                console.error(error)
            }
        }, 
		/**
		 * 保存表单
		 **/
		save () {
			this.$refs.udsjobForm.validate(valid => {
				if(!valid) return;
				let params = {};
				Object.assign(params, this.formBean);
				let httpConfig = {
					url: RESOURCE_PATH,
					data: params
				};
				if(this.transData.id) {
					httpConfig.method = 'PUT';
					httpConfig.url = RESOURCE_PATH +'/'+ this.transData.id;
				}else {
					httpConfig.method = 'POST';
				}
				this.$ajax(httpConfig)
				.then(resp => {
					this.cancel();
				});
			});
		},
		/**
		 * 返回到数据视图
		 **/
		cancel () {
			this.$emit('switch', {statusName: "table"});
		},
	},
	/**
	 * 视图挂载
	 **/
	mounted () {
		this.init();
	}
}

</script>

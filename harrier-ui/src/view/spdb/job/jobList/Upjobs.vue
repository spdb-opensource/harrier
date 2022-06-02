<template>
	<div>
		<div style="margin-bottom:5px;">
			<Row>
				<Col span="20">
					<div style="font-size:14px;font-weight: bold; color: #464c5b;margin-left:5px;">
					上游作业
					&nbsp;&nbsp;平台名:{{this.transData.jobData.platform}} 
					&nbsp;&nbsp;应用名:{{this.transData.jobData.system}}
					&nbsp;&nbsp;作业名:{{this.transData.jobData.job}}
					</div>
				</Col>
				<Col span="4">
					<Button size="small" type="primary" icon="ios-arrow-back" @click="cancel" style="">返回</Button>
				</Col>
			</Row>
		</div>
		<Row>
			<Col span="8">
				<!--style="border:2px solid #C0C0C0;height:500px;" -->
				<Card :bordered="false">
					<div>
						<div>
							<Tree ref="upjobsTree" :data="upjobsTree" :load-data="loadData"></Tree>
						</div>
					</div>
				</Card>
			</Col>
			<Col span="16">
			<!-- style="border:2px solid #C0C0C0;height:500px;" -->
			<Card :bordered="false">
				<Form ref="upjobsForm" :model="formBean" :label-width="160" >
				<Row>
				<Col span="12">
					<FormItem prop="platform" label="平台名">
						<Input readonly v-model="formBean.platform"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="system" label="应用名">
						<Input readonly v-model="formBean.system"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="job" label="作业名">
						<Input readonly type="textarea" :autosize="{minRows: 1,maxRows: 5}" v-model="formBean.job"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="serverName" label="执行节点分配">
						<Input readonly v-model="formBean.serverName"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="jobType" label="作业类型">
						<Input readonly v-model="formBean.jobType"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="lastStatus" label="作业最后执行状态">
						<Input readonly v-model="formBean.lastStatus"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="startTime" label="开始执行时间">
						<Input readonly v-model="formBean.startTime"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="endTime" label="结束时间">
						<Input readonly v-model="formBean.endTime"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="jobDate" label="最后执行作业日期">
						<DatePicker :transfer="true"readonly type="date" parse="yyyy-MM-dd" format="yyyy-MM-dd" v-model="formBean.jobDate"></DatePicker>
					</FormItem>
				</Col>
				<!-- <Col span="12">
					<FormItem prop="lastScriptName" label="最后执行脚本">
						<Input readonly type="textarea" :autosize="{minRows: 1,maxRows: 5}" v-model="formBean.lastScriptName"/>
					</FormItem>
				</Col> -->
				<Col span="12">
					<FormItem prop="multiBatch" label="批次号">
						<Input readonly v-model="formBean.multiBatch"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="numTimes" label="执行次数">
						<Input readonly v-model="formBean.numTimes"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="priority" label="作业执行优先级 ">
						<Input readonly v-model="formBean.priority"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="timewindow" label="窗口执行时间">
						<Input readonly v-model="formBean.timewindow"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="isEnable" label="是否执行 ">
						<Input readonly v-model="formBean.isEnable"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="isCheckFrequency" label="是否检测时间">
						<Input readonly v-model="formBean.isCheckFrequency"/>
					</FormItem>
				</Col>
				<Col span="12">
					<FormItem prop="isCheckTimeTrigger" label="检测是否采用时间触发">
						<Input readonly v-model="formBean.isCheckTimeTrigger"/>
					</FormItem>
				</Col>
			</Row>
			</Form>
			</Card>
			</Col>
		</Row>
		<Row>
			<Col span="6">
				<div>
					查询层级: <Input size="small" v-model="formBean.levels" placeholder="请输入数字" style="width: 30%;" /> 层
				</div>
			</Col>
			<Col span="12">
				<Button size="small" type="primary" icon="ios-search" @click="queryUpjobsByLevel" style="">查询</Button>
				<Button size="small" type="primary"  @click="exportdata" style="">下载excel</Button>
			</Col>
		</Row>
	</div>
</template>

<script>
import utils from "@/common/utils"; 
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
			formRule: {},
			firstLevelData: [],
			upjobsTree: [{
				title: this.transData.id,
				loading: false,
				expand: false,
				children: []
			}]
		}
	},
	methods: {
		/**
		 * 初始化
		 **/
		init () {
			this.transData.jobData.lastStatus == "Running" ? this.transData.jobData.endTime = "" : this.transData.jobData.endTime = this.transData.jobData.endTime;
			this.formBean = this.transData.jobData;
			//this.search();
		},
		/*
		*处理树数据
		*/
		handleTreeData(data) {
			data.forEach((data, index) => {
				data.expand = true;
				if (data.children.length == 0) {
					data.children = [];
				} else {
					this.handleTreeData(data.children);
				}
			});
		},
		queryUpjobsByLevel() {
			let params = {}
			params.job = this.transData.jobData.job;
			params.level = this.formBean.levels;
			if (!this.formBean.levels || this.formBean.levels < 1) {
				this.upjobsTree = [];
				let tempupjobsTree= [{
						title: this.transData.id,
						loading: false,
						expand: false,
						children: []
					}]
				this.upjobsTree = tempupjobsTree;
				return;
			}
			let httpConfig = {
				method: 'GET',
				url: RESOURCE_PATH + "/upjobList",
				params: params
			};
			let datatmp = [];
			this.$ajax(httpConfig)
			.then(resp => {
				//console.log(resp.data);
				let tmpUpobsTree = this.upjobsTree;
				tmpUpobsTree[0].expand = true;
				let treeData = [];
				resp.data.forEach((data, index) => {
					if (data.children.length == 0) {
						data.children = [];
						data.expand = true;
					} else {
						data.expand = true;
						this.handleTreeData(data.children);
					}
				});
				tmpUpobsTree[0].children = resp.data;
				//console.log(resp.data);
				this.upjobsTree = tmpUpobsTree;
			})
		},
		loadData(item, callback) {
			let params = {};
			params.job = item.title;
			//console.log(item.title);
			let httpConfig = {
				method: 'GET',
				url: RESOURCE_PATH + "/upjobList",
				params: params
			};
			this.$ajax(httpConfig)
			.then(resp => {
				let data = [];
				let tmp = {};
				if (resp.data.length == 0) {
					this.$Message.info("该作业已经没有上游了");
				}
				resp.data.forEach(node => {
					tmp = {};
					tmp.title = node.id;
					tmp.loading = false;
					tmp.children = [];
					data.push(tmp);
				})
				callback(data);
			})
		},
		/**	
		 * 查询
		 **/
		search () {
			let params = {};
			Object.assign(params, this.formBean);
			params.currentPage = 1;
			params.pageSize = 100000000;
			params.job = this.transData.id;
			
			let httpConfig = {
				method: 'GET',
				url: RESOURCE_PATH + "/depList",
				params: params
			};
			this.firstLevelData=[];
			this.$ajax(httpConfig)
			.then(resp => {
				let treedata = {loading: false,children: []};
				resp.data.rows.forEach(data => {
					let tmp = Object.assign({},treedata);
					tmp.title = data.job;
					this.firstLevelData.push(tmp);
				});
				
			})
		}, 
		/**
		 * 下载
		 **/
		exportdata () {
			let params = {};
			Object.assign(params, this.formBean);
			params.type = 'excel';
			params.downloadType = "up";
			utils.download(RESOURCE_PATH + '/downLoadJobs', params);
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
			let prevTab = "";
			if(this.transData.tabObj) {
				prevTab = this.transData.tabObj.prevTab;
				prevTab = "joblist";
			}
			let queryCache = {formBean:this.transData.formBean,currentPage:this.transData.currentPage,pageSize:this.transData.pageSize};
			let backParam = {statusName: "table",prevTab: prevTab, type:"back"};
			if (this.transData.jobStatus) {
				queryCache.jobStatus = this.transData.jobStatus;
			}
			this.$emit('switch', Object.assign({}, queryCache, backParam));
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

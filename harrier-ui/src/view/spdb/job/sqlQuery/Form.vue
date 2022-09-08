<template>
	<div>
		<Form ref="systemconfigForm" :model="formBean" :label-width="150" :rules="formRule">
			<Row>
				<Col span="12">
					<FormItem prop="scPName" label="平台名">
						<Select v-model="formBean.scPName" filterable @on-change="querySystem">
							<Option v-for="item in platform" :value="item.value" :key="item.value">{{ item.label }}</Option>
						</Select>
					</FormItem>
				</Col>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem prop="scSysName" label="应用名">
						<Select v-model="formBean.scSysName" filterable>
							<Option v-for="item in system" :value="item.value" :key="item.value">{{ item.label }}</Option>
						</Select>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem prop="lastTxDate" label="TXDate">
						<Input type="text" v-model="formBean.lastTxDate" readonly/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem prop="timeOfTxDateChange" label="TXDate换日时间">
						<TimePicker :transfer="true"v-model="formBean.timeOfTxDateChange"  format="HH:mm" placeholder="Select time" style="width: 112px"></TimePicker>
						<!-- <Input v-model="formBean.timeOfTxDateChange" placeholder="例如 06:01 or 21:10"/> -->
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem prop="jobOfTxDateChange" label="TXDate换日作业">
						<Input type="text" v-model="formBean.jobOfTxDateChange" placeholder="支持英文、数字、下划线"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem prop="queryStatus" label="轮询是否启用">
						<Checkbox v-model="formBean.queryStatus" />
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem  prop="queryCycle" label="轮询周期">
						<Input v-model="formBean.queryCycle" placeholder="单位 秒"  />
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem prop="queryAtTimeStatus" label="每日查询是否启用">
						<Checkbox v-model="formBean.queryAtTimeStatus" />
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="12">
					<FormItem prop="queryTime" label="每日查询时间">
						<TimePicker :transfer="true"v-model="formBean.queryTime"  format="HH:mm" placeholder="Select time" style="width: 112px"></TimePicker>
						<!-- <Input v-model="formBean.queryTime" placeholder="例如 06:01 or 21:10"/> -->
					</FormItem>
				</Col>
			</Row>
		</Form>
		<br/>
		<div>
			<Row>
				<Col span="3">
				  &nbsp;
				</Col>
				<Col span="12">
					<Button type="primary" icon="md-add" @click="save">保存</Button>
					<Button type="primary" icon="ios-trash-outline" @click="cancel">取消</Button>
				</Col>
			</Row>
		</div>
	</div>
</template>

<script>
import utils from '@/common/utils'
const RESOURCE_PATH = '/systemconfig'

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
        scPName: [{
          required: true,
          message: '请输入数据！'
        }],
        scSysName: [{
          required: true,
          message: '请输入数据！'
        }],
        jobOfTxDateChange: [{
          validator: (rule, value, callback, source, options) => {
            let ruleRe = /^[a-zA-Z0-9_]+$/g
            if (typeof value === 'undefined' || !value.match(ruleRe)) {
              callback('JOB名称中含有特殊字符或为空')
            } else {
              callback()
            }
          }
        }],
        queryCycle: [{
          required: true,
          message: '请输入数据！'
        }, { validator: (rule, value, callback, source, options) => {
          let ruleRe = /^[1-9]{1}[0-9]{0,3}$/g
          if (typeof value === 'undefined' || !value.match(ruleRe)) {
            callback('只能为1-3位数字')
          } else {
            callback()
          }
        }
        }]

      },
      platform: [],
      system: []
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
            if (resp.data.queryStatus == 'true') {
              resp.data.queryStatus = true
            } else {
              resp.data.queryStatus = false
            }
            if (resp.data.queryAtTimeStatus == 'true') {
              resp.data.queryAtTimeStatus = true
            } else {
              resp.data.queryAtTimeStatus = false
            }
            this.formBean = resp.data
          })
      } else {
        this.bindData()
      }
      this.queryPlatform()
      // this.querySystem();
    },
    /**
		 * 如果传过来的有初始数据则进行数据绑定
		 **/
    bindData () {
      try {
        this.formBean = Object.assign({}, this.transData.initFormBean)
        if (typeof this.formBean.length === 'undefined') {
          this.formBean.lastTxDate = utils.fmtDate(new Date(), 'yyyy-MM-dd')
        }
      } catch (error) {
        console.error(error)
      }
    },
    /**
		 * 保存表单
		 **/
    save () {
      this.$refs.systemconfigForm.validate(valid => {
        if (!valid) return
        let params = {}
        this.$refs.systemconfigForm.$children.forEach(ele => {
          let propName = ele.$children[0].$children[0].prop
          let labelName = ele.$children[0].$children[0].label
          if (typeof this.formBean[propName] === 'undefined') {
            this.formBean[propName] = null
          }
          this.formBean[propName] = labelName + '#@#' + this.formBean[propName] + '#@#' + propName
        })
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
      this.$emit('switch')
    },
    queryPlatform () {
      let loadConfig = {
        method: 'GET',
        url: '/msys/getPlatformList'
      }
      this.$ajax(loadConfig)
        .then(resp => {
          let resdata = resp.data
          for (let i = 0; i < resdata.length; i++) {
            let tmp = {}
            tmp.label = resdata[i]
            tmp.value = resdata[i]
            this.platform.push(tmp)
          }
        })
    },
    querySystem () {
      let loadConfig = {
        method: 'GET',
        url: '/msys/getSysList',
        params: { platform: this.formBean.scPName }
      }
      this.$ajax(loadConfig)
        .then(resp => {
          let resdata = resp.data
          this.system = []
          for (let i = 0; i < resdata.length; i++) {
            let tmp = {}
            tmp.label = resdata[i]
            tmp.value = resdata[i]
            this.system.push(tmp)
          }
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

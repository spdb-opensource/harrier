<template>
	<div>
		<Form ref="udsjobweightlimitForm" :model="formBean" :label-width="100" :rules="formRule">
			<Row>
				<Col span="6">
					<FormItem prop="limitType" label="类型">
						<Select v-model="formBean.limitType" :disabled="disabledStatus" filterable  clearable>
							<Option v-for="item in limitTypeData" :value="item.value" :key="item.key">{{ item.label }}</Option>
						</Select>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="6">
					<FormItem prop="limitValue" label="上限">
						<InputNumber  v-model="formBean.limitValue" :max="999" :min="0" style="width:100%"/>
					</FormItem>
				</Col>
			</Row>
      <Row>
				<Col span="6">
					<FormItem prop="serverId" label="机器">
            <Select v-model="formBean.serverId" multiple clearable>
							<Option v-for="item in serverData" :value="item.id" :key="item.id">{{ item.serverName }}</Option>
						</Select>
						<!-- <Input  v-model="formBean.serverId" style="width:100%"/> -->
					</FormItem>
				</Col>
			</Row>
      <Row>
				<Col span="6">
					<FormItem prop="timeWindow" label="时间窗口">
            <TimePicker
              v-model="formBean.timeWindowS"
              format="HH:mm"
              placeholder="开始时间"
              style="width:50%"
            ></TimePicker>
            <TimePicker
                v-model="formBean.timeWindowE"
                format="HH:mm"
                placeholder="结束时间"
                style="width:50%"
              ></TimePicker>
						<!-- <Input  v-model="formBean.timeWindow" style="width:100%"/> -->
					</FormItem>
				</Col>
        <!-- <Col span="12">
        <FormItem>
          <TimePicker
                :value="formBean.timeWindow"
                format="HH:mm"
                placeholder="请选择"
              ></TimePicker>
        </FormItem>
        </Col> -->
			</Row>
			<!-- <Row>
				<Col span="6">
					<FormItem prop="presentValue" label="当前数值">
						<InputNumber  v-model="formBean.presentValue" style="width:100%"/>
					</FormItem>
				</Col>
			</Row> -->
			<Row>
				<Col span="6">
					<FormItem prop="des" label="描述">
						<Input v-model="formBean.des"/>
					</FormItem>
				</Col>
			</Row>
		</Form>

		<div>
			<Button type="primary" icon="md-add" :loading="saveLoding" @click="save">保存</Button>
			<Button type="primary" icon="ios-trash-outline" @click="cancel">取消</Button>
		</div>
	</div>
</template>

<script>
const RESOURCE_PATH = '/udsJobWeightLimit'

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
    const validateTimeWindow = (rule, value, callback) => {
      if (!this.formBean.timeWindowS || !this.formBean.timeWindowE) {
        return callback(new Error('请选择时间'))
      } else {
        return callback()
      }
    }
    return {
      serverData: [],
      limitTypeData: [],
      formBean: {},
      saveLoding: false,
      disabledStatus: false,
      formRule: {
        limitType: [{
          required: true,
          message: '请输入数据！'
        }],
        limitValue: [{
          required: true,
          type: 'number',
          message: '请输入数据！'
        }],
        serverId: [{
          required: true,
          message: '请选择机器！'
        }],
        timeWindow: [{
          validator: validateTimeWindow, trigger: 'blur'
        }]
        // presentValue: [{
        //   required: true,
        //   message: '请输入数据！'
        // }]
      }
    }
  },
  methods: {
    /**
		 * 初始化
		 **/
    init () {
      if (this.transData.id) { // 如果传过来的存在ID则说明是编辑功能跳转过来
        this.disabledStatus = true
        let loadConfig = {
          method: 'GET',
          url: RESOURCE_PATH + '/getDetail',
          params: { id: this.transData.id }
        }
        this.$ajax(loadConfig)
          .then(resp => {
            let tmp = Object.assign({}, resp.data)
            tmp.serverId = tmp.serverIds.split(',')
            tmp.timeWindowS = tmp.timeWinds.split('-')[0]
            tmp.timeWindowE = tmp.timeWinds.split('-')[1]
            this.formBean = Object.assign({}, tmp)
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
        this.formBean.limitValue = 1 // 设置初始值
      } catch (error) {
        console.error(error)
      }
    },
    /**
		 * 保存表单
		 **/
    save () {
      this.saveLoding = true
      this.$refs.udsjobweightlimitForm.validate(valid => {
        if (!valid) {
          this.saveLoding = false
          return
        }
        let params = {}
        Object.assign(params, this.formBean)
        params.authps = this.formBean.platform + this.formBean.system
        params.serverIds = params.serverId.toString()
        params.timeWinds = this.formBean.timeWindowS + '-' + this.formBean.timeWindowE
        delete params.timeWindowS
        delete params.timeWindowE
        let httpConfig = {
          url: RESOURCE_PATH,
          data: params
        }
        if (this.transData.id) {
          httpConfig.method = 'POST'
          httpConfig.url = RESOURCE_PATH + '/update'
        } else {
          httpConfig.method = 'PUT'
          httpConfig.url = RESOURCE_PATH + '/add'
        }
        this.$ajax(httpConfig)
          .then(resp => {
            this.saveLoding = false
            this.cancel()
          })
      })
    },
    queryLimitType () {
      this.limitTypeData = []
      let params = { dicCode: 'limitType' }
      const loadConfig = {
        method: 'GET',
        url: '/mDictionary/selectDicCode',
        params: params
      }
      this.$ajax(loadConfig)
        .then(resp => {
          if (resp.data) {
            resp.data.forEach(data1 => {
              let temp = {}
              temp.key = data1.dicValue
              temp.value = parseInt(data1.dicKey)
              temp.label = data1.dicValue
              this.limitTypeData.push(temp)
            })
          }
        })
    },
    /**
		 * 返回到数据视图
		 **/
    cancel () {
      // this.$emit('switch');
      // let backParam = {id: this.transData.id, statusName: "weightLimitTable"};
      let queryCache = { formBean: this.transData.formBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize }
      this.$emit('switch', Object.assign({}, queryCache))
    },
    getServerList () {
      let serverConfig = {
        method: 'GET',
        url: '/udsServer/listQuery'
      }
      this.$ajax(serverConfig)
        .then(resp => {
          this.serverData = [{ 'id': '*', 'serverName': '*' }]
          if (resp.status && resp.status === 200) {
            resp.data.forEach(e => {
              let tmp = {}
              tmp.id = e.id + ''
              tmp.serverName = e.serverName
              this.serverData.push(tmp)
            })
          }
        })
    }
  },
  /**
	 * 视图挂载
	 **/
  mounted () {
    this.getServerList()
    this.queryLimitType()
    this.init()
  }
}

</script>

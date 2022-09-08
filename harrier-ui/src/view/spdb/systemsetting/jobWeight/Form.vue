<template>
	<div>
		<Form ref="udsjobweightForm" :model="formBean" :label-width="100" :rules="formRule">
			<Row>
				<Col span="6">
					<FormItem prop="platform" label="平台名">
						<Select :disabled="disabledStatus" v-model="formBean.platform" filterable @on-change="getSystemList" clearable>
							<Option v-for="item in platformData" :value="item.value" :key="item.key">{{ item.label }}</Option>
						</Select>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="6">
					<FormItem prop="systems" label="应用名">
						<Select :disabled="disabledStatus" placeholder="请先选平台" v-model="formBean.systems" ref="refsystem" filterable  clearable>
							<Option v-for="item in systemData" :value="item.value" :key="item.key">{{ item.label }}</Option>
						</Select>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="6">
					<FormItem prop="job" label="作业名">
						<Input :disabled="disabledStatus" v-model="formBean.job"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="6">
					<FormItem prop="limitType" label="类型">
						<Select v-model="formBean.limitType" filterable  clearable>
							<Option v-for="item in limitTypeData" :value="item.value" :key="item.key">{{ item.label }}</Option>
						</Select>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="6">
					<FormItem prop="confValue" label="配置的全值">
						<!-- <Input type="number" v-model="formBean.confValue"/> -->
						<InputNumber v-model="formBean.confValue" style="width: 100%"/>
						<!--
						<input type="number" style="border-radius:5px; width: 70px"
							v-model="formBean.maxJobNum"
              oninput="if(value>300)value=300;if(value<0)value=0" />
						-->
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="6">
					<FormItem prop="desc" label="描述">
						<Input v-model="formBean.desc"/>
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
const RESOURCE_PATH = '/udsJobWeight'

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
      platformData: [],
      systemData: [],
      limitTypeData: [],
      disabledStatus: false,
      saveLoding: false,
      formRule: {
        platform: [{
          required: true,
          message: '请输入数据！'
        }],
        systems: [{
          required: true,
          message: '请输入数据！'
        }],
        job: [{
          required: true,
          message: '请输入数据！'
        }],
        limitType: [{
          required: true,
          message: '请输入数据！'
        }],
        confValue: [{
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
      this.getPlatformList()
      // this.querySystem()
      this.queryLimitType()
      if (this.transData.id) { // 如果传过来的存在ID则说明是编辑功能跳转过来
        this.disabledStatus = true
        let loadConfig = {
          method: 'GET',
          url: RESOURCE_PATH + '/getDetail',
          params: { id: this.transData.id }
        }
        this.$ajax(loadConfig)
          .then(resp => {
            if (resp.status && resp.status === 200) {
              this.formBean = resp.data
              this.getSystemList()
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
    getPlatformList () {
      let platformList = this.$store.getters.getUserPlatform()
      platformList.forEach(data => {
        let tmp = {}
        tmp.value = data
        tmp.label = data
        this.platformData.push(tmp)
      })
    },
    getSystemList () {
      this.systemData = []
      this.$refs.refsystem.setQuery('')
      if (this.formBean.platform) {
        let userSystemList = this.$store.getters.getUserSystem()
        let userSystem = userSystemList[this.formBean.platform]
        userSystem.forEach(data => {
          let tmp = {}
          tmp.value = data
          tmp.label = data
          this.systemData.push(tmp)
        })
      }
    },
    queryPlatform () {
      let loadConfig = {
        method: 'GET',
        url: '/msys/getPlatformList'
      }
      this.$ajax(loadConfig)
        .then(resp => {
          resp.data.forEach(data => {
            let tmp = {}
            tmp.value = data
            tmp.label = data
            this.platformData.push(tmp)
          })
        })
    },
    querySystem () {
      let loadConfig = {
        method: 'GET',
        url: '/msys/getSysList',
        params: { platform: this.formBean.platform }
      }
      this.systemData = [{ label: '*', value: '*' }]
      this.$refs.refsystem.setQuery('')
      if (this.formBean.platform) {
        this.$ajax(loadConfig)
          .then(resp => {
            resp.data.forEach(data => {
              let tmp = {}
              tmp.value = data
              tmp.label = data
              this.systemData.push(tmp)
            })
          })
      }
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
		 * 保存表单
		 **/
    save () {
      this.saveLoding = true
      this.$refs.udsjobweightForm.validate(valid => {
        if (!valid) {
          this.saveLoding = false
          return
        }
        let params = {}
        Object.assign(params, this.formBean)
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
            this.cancel()
            this.saveLoding = false
          })
      })
    },
    /**
		 * 返回到数据视图
		 **/
    cancel () {
      // this.$emit('switch');
      // let backParam = {id: this.transData.id, statusName: "weightTable"};
      let queryCache = { formBean: this.transData.formBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize }
      this.$emit('switch', Object.assign({}, queryCache))
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

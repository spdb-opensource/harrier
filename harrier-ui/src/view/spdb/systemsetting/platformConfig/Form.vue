<template>
	<div>
		<Form ref="systemForm" :model="formBean" :label-width="180" :rules="formRule">
			<Row>
				<Col span="5">
					<FormItem prop="platform" label="平台名">
						<Input v-model="formBean.platform" :disabled="isEdit"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="5">
					<FormItem prop="systems" label="应用名">
						<Input v-model="formBean.systems" :disabled="isEdit"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="5">
					<FormItem prop="des" label="描述">
						<Input v-model="formBean.des"/>
					</FormItem>
				</Col>
			</Row>
			<!--
			<Row>
				<Col span="5">
					<FormItem prop="recordKeepDay" label="记录保存天数">
						<input type="number" style="border-radius:5px; width: 100px" v-model="formBean.recordKeepDay"  oninput="if(value>60)value=60;if(value<0)value=0" />
					</FormItem>
				</Col>
			</Row>-->
			<Row>
				<Col span="7">
					<FormItem prop="maxRunJob" label="该应用的最大运行作业数">
						<!-- <input type="number" style="border-radius:5px" v-model="formBean.maxRunJob"  oninput="if(value<0)value=0" /> -->
            <InputNumber style="width: 100%" v-model="formBean.maxRunJob"  oninput="if(value<0)value=0" />
					</FormItem>
				</Col>
			</Row>
			<Row>
				<!--
				<Col span="5">
					<FormItem prop="strategy" label="分发策略">
						<input type="number" style="border-radius:5px; width: 100px" v-model="formBean.strategy"  oninput="if(value<0)value=0" />
					</FormItem>
				</Col>-->
				<Col span="7">
					<FormItem prop="select" label="分发策略" >
						<Select filterable v-model="formBean.select">
							<Option v-for="item in strategyList" :value="item.value" :key="item.label">{{ item.label }}</Option>
						</Select>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="7" >
					<FormItem prop="selectPro" label="策略参数">
						<Input v-model="formBean.selectPro" placeholder="执行节点名称以,隔开"/>
						<p>(提示:输入多个执行节点名称时用","隔开)</p>
						<!--
						<Select  filterable v-model="formBean.strategyPro" multiple  @on-change="get_num" style="width:150px" >
							<Option v-for="item in strategyProList" :value="item.value" :key="index">{{ item.label }}</Option>
						</Select>-->
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="6">
					<FormItem prop="usePlatform" label="是否启用">
						<!--<input type="number" style="border-radius:5px; width: 100px" v-model="formBean.usePlatform"  oninput="if(value<0)value=0" />-->
						<Select filterable v-model="formBean.usePlatform" style="width:150px">
							<Option v-for="item in usePlatformList" :value="item.value" :key="item.label">{{ item.label }}</Option>
						</Select>
					</FormItem>
				</Col>
			</Row>

		</Form>

		<div style="margin-left:5%">
			<Button type="primary" :loading="loading" icon="md-add" @click="save">保存</Button>
			<Button type="primary" icon="ios-trash-outline" @click="cancel">取消</Button>
		</div>
	</div>
</template>

<script>
import utils from '@/common/utils'

const RESOURCE_PATH = '/udsSystem'

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
      num: 0,
      // multiple:true,
      formBean: {},
      loading: false,
      strategyProList: [],
      usePlatformList: [
        {
          value: '0',
          label: '禁用'
        },
				 {
          value: '1',
          label: '启用'
        }
      ],
      strategyList: [],
      formRule: {
        platform: [{
          required: true,
          message: '请输入数据！'
        }],
        systems: [{
          required: true,
          message: '请输入数据！'
        }],
        recordKeepDay: [{
          required: true,
          message: '请输入数据！'
        }],
        maxRunJob: [{
          required: true,
          message: '请输入数据！'
        }]
      },
      isEdit: false
    }
  },
  methods: {
    get_num (data) {
      // console.log("data",data);
      this.num = data.length
    },
    loadStrategyPro: function () {
      this.strategyProList = []
      const loadConfig = {
        method: 'GET',
        url: '/server/getList',
        params: {}
      }
      this.$ajax(loadConfig).then(resp => {
        if (resp.data) {
          resp.data.forEach(data1 => {
            let temp = {}
            // temp.key = data1;
            temp.value = data1
            temp.label = data1
            this.strategyProList.push(temp)
          })
        }
      })
    },
    /**
		 * 初始化
		 **/
    init () {
      if (this.transData.sys && this.transData.platform) { // 如果传过来的存在ID则说明是编辑功能跳转过来
        this.isEdit = true
        this.formBean = this.transData.data
        // let loadConfig = {
        //   method: 'GET',
        //   url: RESOURCE_PATH + '/' + this.transData.platform + '_' + this.transData.sys+ '_' + this.transData.dbControl
        // }
        // this.$ajax(loadConfig)
        //   .then(resp => {
        //     this.formBean = resp.data
        //     this.formBean.strategy = resp.data.strategy + ''
        //     // if (resp.data.strategy == 0) {
        //     //   this.formBean.strategy = '0'
        //     // } else {
        //     //   this.formBean.strategy = '1'
        //     // }
        //     if (resp.data.usePlatform == 0) {
        //       this.formBean.usePlatform = '0'
        //     } else {
        //       this.formBean.usePlatform = '1'
        //     }
        //   })
      } else {
        this.bindData()
      }
    },
    /**
		 * 如果传过来的有初始数据则进行数据绑定
		 **/
    bindData () {
      this.$set(this.formBean, 'select', 1)
      this.$set(this.formBean, 'maxRunJob', 0)
      this.$set(this.formBean, 'usePlatform', '1')
      // try {
      //   this.formBean = Object.assign({}, this.transData.initFormBean)
      // } catch (error) {
      //   console.error(error)
      // }
    },
    /**
		 * 保存表单
		 **/
    save () {
      this.$refs.systemForm.validate(valid => {
        if (!valid) return
        let params = {}
        Object.assign(params, this.formBean)
        // params.authps = this.formBean.platform + this.formBean.systems
        let httpConfig = {
          url: RESOURCE_PATH + '/updateConcurrencyState',
          data: params
        }
        if (this.transData.sys && this.transData.platform) {
          httpConfig.method = 'POST'
        } else {
          httpConfig.method = 'PUT'
          httpConfig.url = RESOURCE_PATH + '/add'
        }
        this.loading = true
        this.$ajax(httpConfig)
          .then(resp => {
            this.loading = false
            let str = resp.data
            // alert(resp.data);
            // console.log("str",str);
            if (resp.status && resp.status == 200) {
              utils.getUserPlatform(this.$store.getters.getUserId)
              this.cancel()
            }
          })
      })
    },
    querySelectType () {
      this.strategyList = []
      let params = { dicCode: 'selectType' }
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
              this.strategyList.push(temp)
            })
          }
        })
    },
    /**
		 * 返回到数据视图
		 **/
    cancel () {
      let queryCache = { formBean: this.transData.formBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize }
      this.$emit('switch', Object.assign({ ntable: 'true' }, queryCache))
    }
  },
  /**
	 * 视图挂载
	 **/
  mounted () {
    this.querySelectType()
    this.init()
  }
}

</script>

<template>
	<div>
		<Form ref="dicForm" :model="formBean" :label-width="180" :rules="formRule">
			<Row>
				<Col span="5">
					<FormItem prop="dicCode" label="字典类型">
						<Input v-model="formBean.dicCode" :disabled="isEdit"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="5">
					<FormItem prop="dicKey" label="字典键">
						<Input v-model="formBean.dicKey" :disabled="isEdit"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="5">
					<FormItem prop="dicName" label="字典名称">
						<Input v-model="formBean.dicName"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="5">
					<FormItem prop="dicValue" label="字典值">
						<Input v-model="formBean.dicValue"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="5">
					<FormItem prop="dicDesc" label="描述">
						<Input v-model="formBean.dicDesc"/>
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
const RESOURCE_PATH = '/mDictionary'

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
      formRule: {
        dicCode: [{
          required: true,
          message: '请输入数据！'
        }],
        dicKey: [{
          required: true,
          message: '请输入数据！'
        }],
        dicValue: [{
          required: true,
          message: '请输入数据！'
        }]
      },
      isEdit: false
    }
  },
  methods: {
    /**
		 * 初始化
		 **/
    init () {
      console.log(this.transData)
      if (this.transData.dicCode && this.transData.dicKey) { // 如果传过来的存在ID则说明是编辑功能跳转过来
        this.isEdit = true
        this.formBean = this.transData.data
      } else {
        // this.bindData()
      }
    },
    /**
		 * 如果传过来的有初始数据则进行数据绑定
		 **/
    bindData () {
      this.$set(this.formBean, 'strategy', '0')
      this.$set(this.formBean, 'maxRunJob', 1)
      this.$set(this.formBean, 'usePlatform', '1')
    },
    /**
		 * 保存表单
		 **/
    save () {
      this.$refs.dicForm.validate(valid => {
        if (!valid) return
        let params = {}
        Object.assign(params, this.formBean)
        let httpConfig = {
          url: RESOURCE_PATH + '/update',
          data: params
        }
        if (this.transData.dicCode && this.transData.dicKey) {
          httpConfig.method = 'POST'
        } else {
          httpConfig.method = 'PUT'
          httpConfig.url = RESOURCE_PATH + '/add'
        }
        this.loading = true
        this.$ajax(httpConfig)
          .then(resp => {
            this.loading = false
            if (resp.status && resp.status == 200) { this.cancel() }
          })
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
    this.init()
  }
}

</script>

<template>
	<div>
		<Form ref="moperatlogForm" :model="formBean" :label-width="100" :rules="formRule">
		</Form>

		<div>
			<Button type="primary" icon="md-add" @click="save">保存</Button>
			<Button type="primary" icon="ios-trash-outline" @click="cancel">取消</Button>
		</div>
	</div>
</template>

<script>
const RESOURCE_PATH = '/moperatlog'

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
      formBean: {}
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
      this.$refs.moperatlogForm.validate(valid => {
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
      this.$emit('switch')
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

<template>
	<div>
		<div class="spdb-form">
			<Form ref="roleForm" :label-width="100" :model="formBean" :rules="formRule">
				<Row>
				<Col span="14">
					<FormItem prop="roleName" label="角色名称">
						<Input v-model="formBean.roleName"/>
					</FormItem>
				</Col>
				 </Row>
				<Row>
				<Col span="14">
				<FormItem prop="role_code" label="角色编码">
					<Input v-model="formBean.roleName">
						<span slot="prepend">ROLE_</span>
					</Input>
				</FormItem>
				</Col>
				 </Row>
				<Row>
				<Col span="14">
				<FormItem prop="remark" label="描述">
					<Input v-model="formBean.remark" type="textarea" :rows="3"/>
				</FormItem>
				</Col>
				</Row>
			</Form>
		</div>
		<div class="spdb-toolbar">
			<Button type="primary" :loading="loading" icon="md-add" @click="save()">保存</Button>
			<Button type="primary" icon="ios-redo-outline" @click="cancel()">取消</Button>
		</div>
	</div>
</template>

<script>
export default {
  props: {
    transData: {
      type: Object,
      default: function () {
        return {}
      }
    }
  },
  data: function () {
    return {
      formBean: {},
      loading: false,
      formRule: {
	        	roleName: [
		          { required: true, message: '请输入角色名称！' }
		        ]
		        // role_code: [
		        //   { required: true, message: '请输入角色编码！' }
		        // ]
		   }
    }
  },
  methods: {
    init: function () {
      let id = this.transData.id
      if (id) {
        let reqConfig = {
          method: 'GET',
          url: `/role/${id}`
        }
        this.$ajax(reqConfig)
          .then(resp => {
    			  this.formBean = resp.data
            this.formBean.roleName = this.formBean.roleName.replace('ROLE_', '')
      			})
      }
    },
    save: function () {
      this.$refs.roleForm.validate(valid => {
        if (!valid) return
        let reqConfig = {}
        let data = Object.assign({}, this.formBean)
        reqConfig.data = data
        delete reqConfig.data.createTime
        delete reqConfig.data.updateTime
        if (this.formBean.roleId) {
          reqConfig.method = 'PUT'
          reqConfig.url = `/role/${data.roleId}/update`
        } else {
          if (reqConfig.data.roleName) {
            // reqConfig.data.createUser = 'test'
            // reqConfig.data.updateUser = 'test'
            reqConfig.data.isEnable = true
          }
          reqConfig.method = 'POST'
          reqConfig.url = '/role/addRole'
        }
        // let roleCode = 'ROLE_'.concat(data.role_code)
        // reqConfig.data.role_code = roleCode
        this.loading = true
        this.$ajax(reqConfig)
          .then(resp => {
      				this.cancel()
      			})
	     	})
    },
    cancel: function () {
      let queryCache = { searchBean: this.transData.searchBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize }
      this.$emit('exit', Object.assign({}, queryCache))
    }
  },
  mounted: function () {
 		this.init()
  }
}
</script>

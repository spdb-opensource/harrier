<template>
	<div>
		<div class="spdb-form">
			<Form ref="udsjobweightForm" :model="formBean" :label-width="90" :rules='ruleValidate'>
				<Row>
					<Col span="4">
						<FormItem prop="platform" label="平台名">
							<Select filterable v-model="formBean.platform" @on-change="querySystem" clearable>
								<Option v-for="item in platformData" :value="item.value" :key="item.value">{{ item.label }}</Option>
							</Select>
						</FormItem>
					</Col>
					<Col span="4">
						<FormItem label="应用名">
							<Select ref="refsystem" filterable v-model="formBean.system" placeholder="请先选平台" clearable>
								<Option v-for="item in systemData" :value="item.value" :key="item.value">{{ item.label }}</Option>
							</Select>
						</FormItem>
					</Col>
					<Col span="4">
						<FormItem label="作业名">
							 <!-- <AutoComplete
								v-model="formBean.job"
								@on-search="queryJob"
								:transfer=true
								placeholder="支持模糊查询">
							 </AutoComplete>
							 <Option v-for="item in JobData" :value="item" :key="item">{{ item }}</Option> -->
							<Input v-model="formBean.job" placeholder="支持模糊查询" clearable/>
						</FormItem>
					</Col>
					<Col span="4">
						<FormItem label="类型">
							<Select v-model="formBean.limitType" filterable  clearable>
								<Option v-for="item in limitTypeData" :value="item.value" :key="item.key">{{ item.label }}</Option>
							</Select>
						</FormItem>
					</Col>
					<Col span="4">
						<FormItem label="配置的全值">
							<Input v-model="formBean.confValue" placeholder="请输入" clearable/>
						</FormItem>
					</Col>
				</Row>
			</Form>
		</div>
		<Modal id="importOp" v-model="importOp.show" title="选择文件" width="50%" :mask-closable="false" :closable="false" >
			<div>
				<div>
				<Form ref="importOpForm" :label-width="100" >
					<Row>
					<Col span="16">
						<FormItem label="导入文件名:" style="cursor:pointer;" >
						<Upload ref="importExcel" name="files" :before-upload="handleUpload" action="" >
							<Input style="width:400px;" type="textarea" :autosize="{minRows: 1,maxRows: 5}"v-model="files.length === 0? '' : files[0].name" placeholder="请选择文件"/>
						</Upload>
						</FormItem>
					</Col>
					<div class="spdb-toolbar">
						<Button icon="md-add" type="primary" @click="importExcel" :loading="loadingStatus" :disabled="loadingStatus" >{{ loadingStatus ? "Uploading" : "导入" }}</Button>
						<Button icon="md-close" type="primary" @click="closeImportOp" > 取消 </Button>
					</div>
					</Row>
				</Form>
				</div>
			</div>
			<div slot="footer"></div>
		</Modal>
		<div class="spdb-toolbar">
			<Button type="primary" icon="ios-search" @click="demand">查询</Button>
			<Button type="primary" icon="md-close" @click="clear">清除查询</Button>
			<s-auth-button type="primary" icon="md-add" @click="add" :requestConfig=request.addReq>新增</s-auth-button>
			<s-auth-button type="primary" icon="ios-trash-outline" :loading="delLoding" @click="del" :requestConfig=request.delReq>删除</s-auth-button>
			<Button type="primary" icon="md-download" @click="download">下载</Button>
			<Button type="primary" icon="md-cloud-download" @click="excelDownload">excel模板下载</Button>
			<Button type="primary" icon="md-cloud-upload" @click="importOpen">Excel导入</Button>
		</div>

		<div class="spdb-table">
			<Table :loading="loading" :columns="gridColumns" :data="gridData" stripe @on-selection-change="select">
			</Table>
		</div>

		<div class="spdb-page">
			<Page :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat @on-change="changePage" @on-page-size-change="changePageSize"></Page>
		</div>
	</div>
</template>

<script>
import utils from '@/common/utils'
import store from '@/store/index'
import SAuthButton from '_c/s-auth-button'
const RESOURCE_PATH = '/udsJobWeight'

export default {
  components: {
    SAuthButton
  },
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
      // JobData:[],
      delLoding: false,
      request: {
        delReq: {
          method: 'DELETE',
          url: RESOURCE_PATH
        },
        addReq: {
          method: 'POST',
          url: RESOURCE_PATH
        }
      },
      gridColumns: [
        // {
        // 	type: 'index',
        // 	title: ' ',
        // 	width: 80,
        // 	align: 'center',
        // 	fixed: 'left'
        // },
        {
          type: 'selection',
          width: 60,
          align: 'center',
          fixed: 'left'
        },
        {
          title: '平台',
          width: 100,
          key: 'platform'
        },
        {
          title: '应用',
          width: 100,
          key: 'systems'
        },
        {
          title: '作业名',
          key: 'job',
          minWidth: 180
        },
        {
          title: '类型',
          width: 120,
          key: 'limitType',
          render: (h, params) => {
            let limitType = params.row.limitType
            let str = ''
            this.limitTypeData.forEach(data1 => {
              if (data1.value == limitType) {
                str = data1.key
              }
            })
            if (str == '') {
              str = params.row.limitType
            }
            return h('div', {
              props: {
              }
            }, str)
          }
        },
        {
          title: '配置的全值',
          width: 120,
          key: 'confValue'
        },
        {
          title: '描述',
          minWidth: 200,
          key: 'desc'
        },
        {
          title: '操作',
          width: 100,
          fixed: 'right',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('Button', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    let id = row.id
                    // this.$emit('switch', {id: id}) //提交form事件，在parent中显示form
                    let queryCache = { formBean: this.formBean, currentPage: this.page.current, pageSize: this.page.size }
                    this.$emit('switch', Object.assign({}, { id: id }, queryCache)) // 提交form事件，在parent中显示form
                  }
                }
              }, '编辑')
            ])
          }
        }
      ],
      gridData: [],
      importOp: {
        show: false
      },
      files: [],
      loading: false,
      loadingStatus: false,
      limitTypeData: [],
      selection: [],
      systemData: [],
      platformData: [],
      page: {
        current: 1,
        total: 10,
        size: 10,
        opt: [10, 50, 100]
      },
      ruleValidate: {
      }
    }
  },
  methods: {
    /**
		 * 初始化
		 **/
    init () {
      // this.queryLimitType()
      // this.queryPlatform()
      // if (this.transData.statusName==="weightTable") {
      if (this.transData.currentPage) {
        this.formBean = this.transData.formBean
        this.page.current = this.transData.currentPage
        this.page.size = this.transData.pageSize
      }
      this.search()
    },
    /**
         * 如果传过来的有初始数据则进行数据绑定
         **/
    bindData (fields) {
      try {
        const data = Object.assign({}, this.transData.initFormBean)
        if (fields) { // 进行可选字段初始化
          for (const field of fields) {
            this.formBean[field] = data[field]
          }
        } else { // 进行全量字段初始化（默认是只有主表的主键的）
          this.formBean = Object.assign({}, data)
        }
      } catch (error) {
        console.error(error)
      }
    },
    /**
		 * 下载
		 **/
    download () {
      let params = {}
      Object.assign(params, this.formBean)
      params.type = 'excel'
      utils.download(RESOURCE_PATH + '/downLoad', params)
    },
    queryLimitType () {
      this.limitTypeData = []
      let params = { dictcode: 'limitType' }
      const loadConfig = {
        method: 'GET',
        url: 'file/loadList',
        params: params
      }
      this.$ajax(loadConfig)
        .then(resp => {
          if (resp.data) {
            resp.data.forEach(data1 => {
              let temp = {}
              temp.key = data1.dicName
              temp.value = parseInt(data1.dicValue)
              temp.label = data1.dicName
              this.limitTypeData.push(temp)
            })
          }
        })
    },
    demand () {
      this.page.current = 1
      this.search()
    },
    /**
		 * 查询
		 **/
    search () {
      let params = {}
      Object.assign(params, this.formBean)
      params.currentPage = this.page.current
      params.pageSize = this.page.size

      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/listQuery',
        params: params
      }
      this.loading = true
      this.$ajax(httpConfig)
        .then(resp => {
          this.loading = false
          // this.gridData = resp.data.rows
          // this.page.total = resp.data.total
          this.gridData = resp.data
          this.page.total = resp.data.length
        })
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
      this.systemData = []
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
    queryJob () {
      let loadConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/getJobList'
      }
      this.$ajax(loadConfig)
        .then(resp => {
          // resp.data.forEach(data1 => {
          // let tmp = {}
          // tmp.value = data1
          // tmp.label = data1
          // this.JobData.push(tmp)
          // })
        })
    },
    excelDownload () {
      let params = {}
      params.type = 'excel'
      utils.download(RESOURCE_PATH + '/template', params)
    },
    importOpen () {
      this.importOp.show = true
    },
    closeImportOp () {
      this.files = []
      this.importOp.show = false
      this.loadingStatus = false
    },
    handleRemove: function (file) {
      this.files.splice(this.files.findIndex(e => e.name === file.name), 1)
    },
    handleUpload: function (file) {
      if (!this.multiple) {
        if (this.files.length > 0) {
          this.fileError = `不能选择多个的文件`
          return false
        }
      }
      if (this.files.find(e => e.name == file.name)) {
        this.fileError = `${file.name}已存在上传列表，已自动过滤掉！`
        return false
      }
      this.files.push(file)
      return false
    },
    	importExcel () {
      let systems = ''
      let authSystems = store.getters.getSystems
      if (authSystems.indexOf('ROLE_ADMIN') == -1) {
        for (let j = 0; j < authSystems.length; j++) {
          if (authSystems[j].indexOf('*') != -1) {
            systems = authSystems[j]
          }
        }
      }
      var formData = new FormData()
      for (const key in this.formBean) {
        formData.append(key, this.formBean[key])
      }
      if (this.files.length == 0 || this.files == null) {
        this.$Message.warning('请选择导入Excel')
      } else {
        for (const ele of this.files) {
          formData.append('file', ele)
        }
        formData.authps = systems
        this.loadingStatus = true
        this.$ajax.post(RESOURCE_PATH + '/import', formData)
          .then(resp => {
            this.loadingStatus = false
            // this.$Message.warning(resp.data.msg)
            this.files = []
            // this.$Message.info("成功")
            this.importOp.show = false
            this.search()
            this.queryPlatform()
            // alert(resp.data.msg)
          })
      }
    },
    /**
		 * 清空
		 **/
    clear () {
      this.formBean = {}
      this.selection = []
      this.page = {
        current: 1,
        size: 10
      }
      this.search()
    },
    /**
		 * 新增
		 **/
    add () {
      this.$emit('switch')
    },
    /**
		 * 删除
		 **/
    del () {
      this.delLoding = true
      const ids = Array.from(this.selection, e => e.id)
      const jobs = Array.from(this.selection, e => e.job)
      const platformArr = Array.from(this.selection, e => e.platform)
      const systemArr = Array.from(this.selection, e => e.system)
      let authSystems = store.getters.getSystems
      if (authSystems.indexOf('ROLE_ADMIN') != -1) { // 是超级管理员 不做权限判断 批量删除
        let params = []
        params[0] = ids
        params[1] = jobs
        this.request.delReq.url = RESOURCE_PATH + '/delete'
        this.request.delReq.data = params
        this.request.delReq.method = 'DELETE'
        this.$ajax(this.request.delReq)
          .then(resp => {
            this.search()
            this.delLoding = false
          })
      } else { // 循环判断删除
        ids.forEach((id, index) => {
          this.request.delReq.url = RESOURCE_PATH + '/' + id + '/' + this.selection[index].job
          this.request.delReq.method = 'DELETE'
          this.request.delReq.data = { authps: platformArr[index] + systemArr[index] }
          this.$ajax(this.request.delReq)
            .then(resp => {
              this.search()
              this.delLoding = false
            })
        })
      }
    },
    /**
		 * 数据复选事件
		 **/
    select (selection) {
      this.selection = selection
    },
    /**
		 * 改变页码事件
		 **/
    changePage (currentPage) {
      this.page.current = currentPage
      this.search()
    },
    /**
		 * 改变分页大小事件
		 **/
    changePageSize (pageSize) {
      this.page.size = pageSize
      this.search()
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

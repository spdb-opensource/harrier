<template>
	<div>
		<div class="spdb-form">
			<Form ref="udsjobweightlimitForm" :model="formBean" :label-width="80" :rules='ruleValidate'>
				<Row>
					<Col span="4">
						<FormItem label="类型">
							<Select v-model="formBean.limitType" filterable  clearable>
								<Option v-for="item in limitTypeData" :value="item.value" :key="item.key">{{ item.label }}</Option>
							</Select>
						</FormItem>
					</Col>

					<!-- <Col span="4">
						<FormItem label="上限">
							<Input v-model="formBean.limitValue" placeholder="请输入" clearable/>
						</FormItem>
					</Col> -->

					<!-- <Col span="4">
						<FormItem label="当前数值">
							<Input v-model="formBean.presentValue" placeholder="请输入" clearable/>
						</FormItem>
					</Col> -->
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
							<Input style="width:400px;" type="textarea" :autosize="{minRows: 1,maxRows: 5}" v-model="files.length === 0? '' : files[0].name" placeholder="请选择文件"/>
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
      <Button type="primary" icon="md-add" @click="add">新增</Button>
      <Button type="primary" :loading="delLoading" :disabled="ctrlDisable" icon="ios-trash-outline" @click="del">删除</Button>
			<!-- <s-auth-button type="primary" icon="md-add" @click="add" :requestConfig=request.addReq>新增</s-auth-button>
			<s-auth-button type="primary" icon="ios-trash-outline" @click="del" :loading="delLoading" :requestConfig=request.delReq>删除</s-auth-button> -->
			<!-- <Button type="primary" icon="md-download" @click="download">下载</Button>
			<Button type="primary" icon="md-cloud-download" @click="excelDownload">excel模板下载</Button>
			<Button type="primary" icon="md-cloud-upload" @click="importOpen">Excel导入</Button> -->
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
import utils from '@/common/utils'
import store from '@/store/index'
import common from '@/mixins/common'
import SAuthButton from '_c/s-auth-button'
const RESOURCE_PATH = '/udsJobWeightLimit'

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
  mixins: [ common ],
  data: function () {
    return {
      formBean: {},
      limitTypeData: [],
      delLoading: false,
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
          title: '类型',
          // marginLeft: 60,
          // width: 100,
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
          title: '上限',
          key: 'limitValue'
        },
        {
          title: '机器',
          key: 'serverIds',
          render: (h, { column, index, row }) => {
            let serverName = ''
            if (row.serverIds.indexOf(',') === -1) {
              serverName = row.serverIds === '*' ? '*' : this.serverList[row.serverIds]
            } else {
              let arr = []
              row.serverIds.split(',').forEach(e => {
                if (this.serverList[e]) {
                  arr.push(this.serverList[e])
                } else {
                  arr.push(e)
                }
              })
              serverName = arr.toString()
            }
            return h('div', [
              h('span', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                }
              }, serverName)
            ])
          }
        },
        {
          title: '时间窗口',
          key: 'timeWinds'
        },
        {
          title: '描述',
          width: 200,
          key: 'des'
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
                    // this.$emit('switch', {id: id}); //提交form事件，在parent中显示form
                    let queryCache = { formBean: this.formBean, currentPage: this.page.current, pageSize: this.page.size }
                    this.$emit('switch', Object.assign({}, { id: id }, queryCache)) // 提交form事件，在parent中显示form udsjobweightlimit
                  }
                }
              }, '编辑')
            ])
          }
        }
      ],
      gridData: [],
      files: [],
      loadingStatus: false,
      importOp: {
        show: false
      },
      loading: false,
      selection: [],
      page: {
        current: 1,
        total: 10,
        size: 10,
        opt: [10, 50, 100]
      },
      ruleValidate: {
      },
      serverList: {}
    }
  },
  methods: {
    /**
		 * 初始化
		 **/
    init () {
      // this.queryLimitType()
      // if (this.transData.statusName==="weightLimitTable") {
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
            // this.$Message.warning(resp.data.msg);
            this.files = []
            // this.$Message.info("成功");
            this.importOp.show = false
            this.search()
            // alert(resp.data.msg);
          })
      }
    },
    getServerList () {
      let serverConfig = {
        method: 'GET',
        url: '/udsServer/listQuery'
      }
      this.$ajax(serverConfig)
        .then(resp => {
          this.serverList = { '*': '*' }
          if (resp.status && resp.status === 200) {
            resp.data.forEach(e => {
              this.serverList[e.id] = e.serverName
            })
          }
          this.init()
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
		 * 查询
		 **/
    search () {
      let params = {}
      Object.assign(params, this.formBean)
      params.current = this.page.current
      params.size = this.page.size

      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/selectAll',
        params: params
      }
      this.loading = true
      this.$ajax(httpConfig)
        .then(resp => {
          this.loading = false
          this.gridData = resp.data.rows
          this.page.total = resp.data.total
        })
    },
    demand () {
      this.page.current = 1
      this.search()
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
      this.delLoading = true
      const ids = Array.from(this.selection, e => e.id)
      let deleteConfig = {
        method: 'DELETE',
        url: `/udsJobWeightLimit/${ids}/delete`,
        data: {}
      }
      this.$ajax(deleteConfig)
        .then(resp => {
          this.delLoading = false
          this.search()
        })
    },
    excelDownload () {
      let params = {}
      params.type = 'excel'
      utils.download(RESOURCE_PATH + '/template', params)
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
    this.getServerList()
    this.queryLimitType()
    // this.init()
  }
}
</script>

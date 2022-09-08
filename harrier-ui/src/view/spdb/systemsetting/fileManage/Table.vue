<template>
	<div>
		<div class="spdb-form">
			<Form ref="udsfileForm" :label-width="80" :model="formBean" :rules="rules" >
				<Row>
					<!--<Col span="4">
						<Form-Item prop="param" label="节点名称">
							<Select  placeholder="请选择" v-model="formBean.nodeName"   @on-change="paramChange()" clearable>
								<Option v-for="item in nodeList" :value="item.value" :key="item.value">{{ item.label }}</Option>
							</Select>
						</Form-Item>
					</Col> -->
          <Col span="4">
						 <FormItem prop="serverName" label="执行节点">
							 <Select :transfer="true" filterable v-model="formBean.serverName" clearable placeholder="请选择">
                <Option v-for="item in serverData" :value="item.value" :key="item.value">{{ item.label }}</Option>
               </Select>
					   </FormItem>
					</Col>
					<Col span="6">
						<Form-Item prop="rootDir" label="目录名称">
							<Select filterable placeholder="请选择" v-model="formBean.rootDir" clearable>
								<Option v-for="item in dirList" :value="item.value" :key="item.key">{{ item.label }}</Option>
							</Select>
						</Form-Item>
					</Col>
					<Col span="5">
						<Form-Item prop="fileName" label="文件名">
							<!--<Input  placeholder="支持通配符*" v-model="formBean.nodeFileName"/>-->
							<Input  placeholder="请输入文件名" v-model="formBean.fileName"/>
						</Form-Item>
					</Col>
					<Col span="9">
						&nbsp;&nbsp;&nbsp;&nbsp;<Button type="primary" icon="ios-search" @click="search">查询</Button>
						&nbsp;&nbsp;&nbsp;&nbsp;<Button type="primary" icon="md-close" @click="clear">清除查询</Button>
            &nbsp;&nbsp;&nbsp;&nbsp;<Button v-if="ifShowBack" type="primary" icon="ios-arrow-back" @click="backToPre">返回上一级</Button>
          </Col>

				</Row>
			</Form>
		</div>
		<Modal id="UploadByRpc"
			v-model="uploadRpc.show"
			title="上传"
			width='50%'
			:mask-closable="false"
			>
			<div>
				<div>
					<Form  ref="uploadForm" :label-width="150"  inline>
						<Row>
							<FormItem label="文件:" style="cursor:pointer;" >
                <Col span="22">
                  <Upload ref="upload" name="files" :before-upload="handleUpload" action="" >
                  <Input style="width:100%;" type="textarea" :autosize="{minRows: 1,maxRows: 5}" v-model="files.length === 0? '' : files[0].name" placeholder="请选择文件"/>
                  </Upload>
                </Col>
                <Col span="2">
                  <div >
                    <Button type="primary" @click="upload" :loading="loadingTable">
                      {{ loadingTable ? 'Uploading' : '上传' }}
                    </Button>
                  </div>
                </Col>
              </FormItem>
						</Row>
					</Form>
				</div>
			</div>
			<div slot="footer"></div>
		</Modal>
		<div class="spdb-table">
			<Table :columns="gridColumns" :data="gridData" :loading="loadingTable" stripe @on-selection-change="select" @on-sort-change="changeSort">
			</Table>
		</div>

		<div class="spdb-page">
       <Page placement="top" :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat @on-change="changePage" @on-page-size-change="changePageSize"></Page>
		</div>
	</div>
</template>

<script>
import utils from '@/common/utils'

const RESOURCE_PATH = '/server'

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
      loadingTable: false,
      ifShowBack: false,
      formBean: {},
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
      serverData: [],
      dirList: [],
      currentDir: '',
      parentPath: '',
      newPath: '',
      files: [],
      fileError: '',
      file: null,
      uploadRpc: {
        show: false
      },
      dropdownItems: [{ val: '下载' }, { val: '删除' }, { val: '上传' }],
      gridColumns: [
        // {
        // 	type: 'index',
        // 	title: ' ',
        // 	width: 80,
        // 	//align: 'center',
        // 	fixed: 'left'
        // },
        // {
        // 	type: 'selection',
        // 	width: 60,
        // 	//align: 'center',
        // 	fixed: 'left'
        // },
        // {
        //   title: '目录名',
        //   minWidth: 120,
        //   key: 'rootDir'
        // },
        {
          title: '文件名',
          key: 'fileName',
          minWidth: 180,
          sortable: true,
          render: (h, { column, index, row }) => {
            if (row.dir) {
              return h('div', [
                h('Icon', {
                  props: {
                    size: 'small',
                    type: 'ios-folder'
                  },
                  style: {
                    marginRight: '5px'
                  }
                }),
                h('a', {
                  props: {
                    size: 'small',
                    type: 'text'
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      this.ifShowBack = true
                      let params = {}
                      Object.assign(params, this.formBean)
                      params.rootDir = row.path
                      this.parentPage.push(this.page.current)
                      this.page.current = 1
                      let isExit = false
                      this.dirList.forEach(element => {
                        if (element.value === params.rootDir) {
                          isExit = true
                        }
                      })
                      if (!isExit) {
                        this.dirList.push({ label: params.rootDir, value: params.rootDir })
                      }
                      this.$set(this.formBean, 'rootDir', params.rootDir)
                      delete params.fileName
                      this.searchFile(params)
                    }
                  }
                }, row.fileName)
              ])
            } else {
              return h('div', [
                h('Icon', {
                  props: {
                    // size: 'small',
                    type: 'md-document'
                  },
                  style: {
                    marginRight: '5px'
                  }
                }),
                h('span', {
                  props: {
                    size: 'small',
                    type: 'info'
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                    }
                  }
                }, row.fileName)
              ])
            }
          }
        },
        {
          title: '文件路径',
          key: 'filePath',
          minWidth: 200
        },
        {
          title: '权限',
          key: 'authority',
          width: 150
        },
        {
          title: '所属用户',
          width: 120,
          key: 'user'
        },

        {
          title: '所属组',
          width: 120,
          key: 'group'
        },
        {
          title: '文件大小',
          width: 120,
          // key: 'used',
          key: 'size',
          // align: 'center',
          render: (h, { column, index, row }) => {
            let u = row.size
            let reg = /^[0-9]*$/
            if (reg.test(row.size)) {
              u = row.size + 'B'
            }
            return h('div', [
              h('span', {

              }, u)
            ])
          }
        },
        {
          title: '创建时间',
          width: 180,
          sortable: true,
          key: 'time'
          // align: 'center'
        },
        /*
        {
          title: '操作',
          width: 100,
          fixed: 'right',
          render: (h, {column, index, row}) => {
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
                  // click: () => {
                  //   let params = {}
                  //   Object.assign(params, this.formBean)
                  //   params.rootDir = row.rootDir
                  //   params.nodeFileName = row.nodeFileName
                  //   params.type = 'excel'
                  //   utils.download(RESOURCE_PATH + '/fileDownLoad', params)
                  // }
                }
              }, '下载')
            ])
          }
        } */
        {
          title: '操作',
          width: 100,
          align: 'center',
          // fixed: 'right',
          render: (h, { column, index, row }) => {
            return h('Dropdown', {
              props: {
                trigger: 'click',
                placement: 'bottom-end',
                transfer: true
              },
              style: {
                paddingRight: '3px'
              },
              on: {
                'on-click': (value) => {
                  let flag = value
                  this.execOpt(flag, row, index, column)
                }
              }
            }, [
              h('a', [
                /* h('span', '更多'), */
                h('Button', {
                  props: {
                    icon: 'ios-more'
                  },
                  style: {
                  // marginRight: '5px'
                    margin: '0px',
                    fontSize: '20px',
                    border: 0,
                    padding: '4px 8px',
                    color: '#03399b',
                    background: 'transparent'
                  }
                })
              ]),
              h('DropdownMenu', {
                slot: 'list'
              },
              this.dropdownItems.map(function (data) {
                return h('DropdownItem', {
                  props: {
                    name: data.val
                  },
                  style: {
                    color: '#3399ff'
                  }
                }, data.val)
              })
              )
            ])
          }
        }
      ],
      gridData: [],
      selection: [],
      page: {
        current: 1, // 当前显示页数
        total: 10,
        size: 10, // 每页显示页数
        num: 1, // 共几页
        opt: [10, 50, 100]// 一页共数据个数
      },
      // 所有页面的数据
      totalPage: [],
      parentPage: [],
      rules: {
        serverName: [{
          required: true,
          message: '请输入数据！'
        }],
        rootDir: [{
          required: true,
          // type: 'string',
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
      // this.search();
      this.findServer()
      this.loadfile()
    },
    loadfile () {
      let loadConfig = {
        method: 'GET',
        url: '/mDictionary/selectDicCode',
        params: { dicCode: 'fileRootPath' }
      }
      this.$ajax(loadConfig)
        .then(resp => {
          this.dirList = []
          resp.data.forEach(data => {
            let tmp = {}
            tmp.value = data.dicValue
            tmp.label = data.dicValue
            this.dirList.push(tmp)
          })
        })
    },
    findServer () {
      let loadConfig = {
        method: 'GET',
        url: '/udsServer/listQuery'
      }
      this.serverData = []
      this.$ajax(loadConfig)
        .then(resp => {
          // let sData = []
          resp.data.forEach(data => {
            let tmp = {}
            tmp.value = data.ip + ':' + data.port
            tmp.label = data.serverName
            // sData.push(tmp)
            this.serverData.push(tmp)
          })
        })
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
    handleUpload: function (file) {
      // if (!this.multiple) {
      if (this.files.length > 0) {
        this.fileError = `不能选择多个的文件`
        return false
      }
      // }
      if (this.files.find(e => e.name == file.name)) {
        this.fileError = `${file.name}已存在上传列表，已自动过滤掉！`
        return false
      }
      this.files.push(file)
      return false
    },
    changeSort (param) {
      this.formBean.order = param.key + '_' + param.order
      this.search()
    },
    /**
		 * 查询
		 **/
    search () {
      this.$refs.udsfileForm.validate(valid => {
        if (!valid) return
        let params = {}
        Object.assign(params, this.formBean)
        params.uri = 'spdb://' + params.serverName + '/' + params.rootDir
        params.currentPage = this.page.current
        params.pageSize = this.page.size
        this.page.current = 1
        let httpConfig = {
          method: 'GET',
          url: '/file/selectFile',
          params: params
        }
        this.gridData = []
        this.page.total = 0
        this.loadingTable = true
        this.$ajax(httpConfig)
          .then(resp => {
            if (resp.status && resp.status === 200) {
              this.gridData = resp.data
              // this.gridData.path = resp.data.path
              // this.newPath = resp.data.path
              this.newPath = params.rootDir
              // 分页
              this.page.total = this.gridData.length
              this.page.num = Math.ceil(this.gridData.length / this.page.size) || 1
              for (let i = 0; i < this.page.num; i++) {
                this.totalPage[i] = this.gridData.slice(this.page.size * i, this.page.size * (i + 1))
              }
              this.gridData = this.totalPage[this.page.current - 1]
            }
            this.loadingTable = false
            this.parentPath = params.rootDir
            this.ifShowBack = false
            this.parentPage = []
          })
      })
    },
    searchFile (params) {
      params.uri = 'spdb://' + params.serverName + '/' + params.rootDir
      let loadConfig = {
        method: 'GET',
        url: '/file/selectFile',
        params: params
      }
      this.gridData = []
      this.loadingTable = true
      this.$ajax(loadConfig)
        .then(resp => {
          if (resp.status && resp.status === 200) {
            this.gridData = resp.data
            // this.gridData.path = resp.data.path
            this.newPath = params.rootDir
            // 分页
            this.page.total = this.gridData.length
            this.page.num = Math.ceil(this.gridData.length / this.page.size) || 1
            for (let i = 0; i < this.page.num; i++) {
              this.totalPage[i] = this.gridData.slice(this.page.size * i, this.page.size * (i + 1))
            }
            this.gridData = this.totalPage[this.page.current - 1]
            this.loadingTable = false
          }
          this.loadingTable = false
        })
    },
    backToPre (row) {
      let temp = this.newPath.substr(0, this.newPath.lastIndexOf('/'))
      if (temp === '') {
        temp = '/'
      }
      this.newPath = temp
      let lastPage = this.parentPage.length - 1
      this.page.current = this.parentPage[lastPage]
      this.parentPage.pop()
      this.formBean.rootDir = temp
      let params = {}
      Object.assign(params, this.formBean)
      // params.rootDir = temp
      delete params.fileName
      this.searchFile(params)
      if (this.parentPath === temp) {
        this.ifShowBack = false
      } else {
        this.ifShowBack = true
      }
    },
    execOpt (flag, row, index, column) {
      if (flag == '下载') {
        if (row.dir) {
          this.$Message.warning({
            content: '禁止下载文件夹！',
            duration: 5,
            closable: true
          })
          return
        }
        let params = {}
        Object.assign(params, this.formBean)
        params.srcFilePath = row.path
        params.filename = row.fileName
        params.uri = 'spdb://' + params.serverName + '/' + params.srcFilePath
        // params.uri = 'local:///C:/Users/c-yujh/Downloads/7月7日疫情风险地区提示单.doc'
        utils.download('/file/loadFile', params)
        // let loadConfig = {
        //   method: 'GET',
        //   url: '/file/loadFile',
        //   params: params
        // }
        // // this.gridData = []
        // this.$ajax(loadConfig)
        //   .then(resp => {
        //     if (resp.data) {
        //       params.type = 'excel'
        //       utils.download('/udsFileRpc/loadFile', params)
        //     }
        //   })
      } else if (flag == '删除') {
        let params = {}
        Object.assign(params, this.formBean)
        params.srcFilePath = row.path
        params.fileName = row.fileName
        params.dir = row.dir
        params.uri = 'spdb://' + params.serverName + '/' + params.srcFilePath
        // params.uri = 'local:///C:/Users/c-yujh/Downloads/7月13日疫情风险地区提示单（简版）(1).doc'
        if (row.dir) {
          this.$Message.warning({
            content: '禁止删除文件夹！',
            duration: 5,
            closable: true
          })
          return
        }
        // this.request.delReq.method = 'DELETE'
        let config = {
          method: 'GET',
          url: '/file/deleteFile',
          params: params
        }
        this.$ajax(config)
          .then(resp => {
            if (resp.data) {
              // 完成
              this.$Message.success('删除成功！')
              this.searchFile(params)
            } else {
              this.$Message.warning({
                content: '删除文件失败！',
                duration: 5,
                closable: true
              })

              // 失败
            }
          })
      } else if (flag == '上传') {
        this.uploadRpc.show = true
        this.uploadRpc.srcFilePath = row.path
        this.uploadRpc.fileName = row.fileName
        this.uploadRpc.dir = row.dir
      }
    },
    upload () {
      let params = {}
      Object.assign(params, this.formBean)
      // params.srcFilePath=row.path
      // params.fileName=row.fileName
      // params.isDir=row.isDir
      if (!this.uploadRpc.dir) {
        this.$Message.warning({
          content: '请在文件夹上进行上传文件！',
          duration: 5,
          closable: true
        })
        return
      }
      var formData = new FormData()
      for (const key in this.formBean) {
        formData.append(key, this.formBean[key])
      }
      formData.append('srcFilePath', this.uploadRpc.srcFilePath)
      if (this.files.length == 0 || this.files == null) {
        this.$Message.warning('请选择上传文件')
      } else {
        for (const ele of this.files) {
          formData.append('fileName', ele.name)
          formData.append('uri', 'spdb://' + params.serverName + '/' + this.uploadRpc.srcFilePath + '/' + ele.name)
          // formData.append('uri', 'local:///D:/文档/temp/temp' + '/' + ele.name)
          formData.append('file', ele)
        }
        this.loadingTable = true
        this.$ajax.post('/file/uploadFile', formData)
          .then(resp => {
            this.loadingTable = false
            this.uploadRpc.show = false
            // this.$Message.warning(resp.data.msg)
            this.files = []
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
      const ids = Array.from(this.selection, e => e.id)
      ids.forEach(id => {
        this.request.delReq.url = RESOURCE_PATH + '/' + id
        this.request.delReq.method = 'DELETE'
        this.$ajax(this.request.delReq)
          .then(resp => {
            this.search()
          })
      })
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
      let params = {}
      Object.assign(params, this.formBean)
      params.rootDir = this.newPath
      this.searchFile(params)
    },
    /**
		 * 改变分页大小事件
		 **/
    changePageSize (pageSize) {
      this.page.size = pageSize
      let params = {}
      Object.assign(params, this.formBean)
      params.rootDir = this.newPath
      this.searchFile(params)
    }
  },
  /**
	 * 视图挂载
	 **/
  mounted () {
		 this.init()
		 this.loadfile()
  }
}
</script>

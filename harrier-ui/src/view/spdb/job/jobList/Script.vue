<template>
	<div>
		<Row>
			<Col span="20">
				<div style="font-size:14px;font-weight: bold; color: #464c5b;margin-left:5px;">
				作业脚本列表
				&nbsp;&nbsp;平台名:{{this.transData.jobData.platform}}
				&nbsp;&nbsp;应用名:{{this.transData.jobData.systems}}
				&nbsp;&nbsp;作业名:{{this.transData.jobData.job}}
				</div>
			</Col>
			<Col span="4">
				<Button size="small" type="primary" icon="ios-arrow-back" @click="cancel" style="">返回</Button>
			</Col>
		</Row>
		<br/><br/>
		<!-- <div class="spdb-toolbar">
			<Button type="primary" icon="md-cloud-download" @click="downloadAllScriptfile">下载所有脚本</Button>
		</div> -->
		<div class="spdb-table" >
			<Table border :columns="gridColumns" :data="gridData" stripe>
			</Table>
		</div>
		<!-- <div class="spdb-page">
			<Page :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat show-elevator @on-change="changePage" @on-page-size-change="changePageSize"></Page>
		</div> -->

	</div>
</template>

<script>
import utils from '@/common/utils'
const RESOURCE_PATH = '/udsJobStep'

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
      gridData: [],
      gridColumns: [
				 /* {
				 	type: 'index',
				 	title: ' ',
				 	width: 80,
				 	//align: 'center',
				 	fixed: 'left'
				 }, */
        /** {
					type: 'selection',
					width: 60,
					//align: 'center',
					fixed: 'left'
				}, */
        {
          title: '脚本名',
          key: 'scriptName'
        },
        {
          title: '执行参数',
          minWidth: 150,
          key: 'parameter'
        },
        {
          title: '执行步数',
          width: 100,
          key: 'stepNum'
        },
        {
          title: '是否启用',
          key: 'isEnable',
          minWidth: 60,
          render: (h, { column, index, row }) => {
            return h('div', [h('Span', {}, row.isEnable ? '是' : '否')])
          }
        },
        {
          title: '操作',
          width: 150,
          render: (h, { column, index, row }) => {
            return h('div', [
              h(
                'Button',
                {
                  props: {
                    size: 'small',
                    type: 'info'
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      this.$Modal.confirm({
                        title: '提示',
                        onOk: () => {
                          let params = {}
                          params.id = row.id
                          params.isEnable = !row.isEnable
                          let httpConfig = {
                            method: 'POST',
                            url: RESOURCE_PATH + '/update',
                            data: params
                          }
                          this.$ajax(httpConfig)
                            .then(resp => {
                              if (resp.status && resp.status === 200) {
                                this.search()
                              }
                            })
                        },
                        content: row.isEnable ? '是否确定禁用该脚本！' : '是否确定启用该脚本！'
                      })
                    }
                  }
                },
                row.isEnable ? '禁用' : '启用'
              ),
              h('Button', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                  // color: "green"
                },
                href: row.scriptPath,
                on: {
                  click: () => {
                    this.downloadScriptfile(row)
                  }
                }
              }, '下载')
            ])
          }
        }

      ],
      gridData: [],
      selection: [],
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
      this.search()
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
    downloadLogfile () {
      let params = {}
      Object.assign(params, this.formBean)
      utils.download(RESOURCE_PATH + '/downloadLogfile', params)
      // RESOURCE_PATH +
    },
    downloadScriptfile (row) {
      let params = {}
      // Object.assign(params, this.transData.jobData)
      // console.log(row)
      params.filename = row.scriptName
      params.uri = row.scriptPath
      utils.download('/file/loadFile', params)
      // RESOURCE_PATH +
      // let httpConfig = {
      //   method: 'GET',
      //   url: '/file/loadFile',
      //   params: params
      // }

      // this.$ajax(httpConfig)
      //   .then(resp => {
      //     // this.jobScriptOp.path = resp.data.path
      //     utils.blobDownload(resp)
      //     console.log('dddddddddd')
      //   })
    },
    downloadAllScriptfile () {
      let params = {}
      Object.assign(params, this.transData.jobData)
      // params.scriptName = scriptName
      utils.download(RESOURCE_PATH + '/downloadAllScriptfile', params)
    },
    /**
		 * 查询
		 **/
    search () {
      let params = {}
      Object.assign(params, this.transData.jobData)
      // params.currentPage = this.page.current;
      // params.pageSize = this.page.size;
      // params.job = this.transData.id;
      if (!this.transData.formBean) {
        params.currentPage = 1
      }
      let httpConfig = {
        method: 'GET',
        url: '/udsJobStep/selectJobStepList',
        params: params
      }

      this.$ajax(httpConfig)
        .then(resp => {
          resp.data.records.forEach(data => {
            if (data.scriptPath) {
              data.scriptName = data.scriptPath.substring(data.scriptPath.lastIndexOf('/') + 1)
            }
          })
          this.gridData = resp.data.records
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
      this.search()
    },
    /**
		 * 改变分页大小事件
		 **/
    changePageSize (pageSize) {
      this.page.size = pageSize
      this.search()
    },
    /**
		 * 列排序
		 **/
    changeSort (column) {
      let classcon = window.event.currentTarget.className
      let sort, order
      if (classcon.indexOf('ivu-icon-md-arrow-dropup') > -1) {
        order = 'ASC'
      } else if (classcon.indexOf('ivu-icon-md-arrow-dropdown') > -1) {
        order = 'DESC'
      }
      // console.log("column,key,order"+ column.key );
      this.formBean.order = order
      this.formBean.sort = column.key
      this.search()
    },
    /**
		 * 返回到数据视图
		 **/
    cancel () {
      let prevTab = ''
      if (this.transData.tabObj) {
        prevTab = this.transData.tabObj.prevTab
        prevTab = 'joblist'
      }
      let queryCache = { formBean: this.transData.formBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize }
      let backParam = { statusName: 'table', prevTab: prevTab, type: 'back' }
      if (this.transData.jobStatus) {
        queryCache.jobStatus = this.transData.jobStatus
      }
      this.$emit('switch', Object.assign({}, queryCache, backParam))
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

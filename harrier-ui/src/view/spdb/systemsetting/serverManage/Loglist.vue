<template>
	<div>
		<div style="margin-bottom:5px;">
			<Row>
				<Col span="20">
					<div style="font-size:14px;font-weight: bold; color: #464c5b;margin-left:5px;">
					日志列表
					&nbsp;&nbsp;节点名称:{{this.transData.info.serverName}}
					&nbsp;&nbsp;节点IP:{{this.transData.info.ip}}
					</div>
				</Col>
				<Col span="4">
					<Button size="small" type="primary" icon="ios-arrow-back" @click="backserverList" style="">返回</Button>
				</Col>
			</Row>
		</div>

		<div class="spdb-table" >
			<Table border width="800" :columns="gridColumns" :data="gridData" stripe @on-selection-change="select">
			</Table>
		</div>

		<div class="spdb-page">
			<Page placement="top" :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat @on-change="changePage" @on-page-size-change="changePageSize"></Page>
		</div>
	</div>
</template>

<script>
import utils from '@/common/utils';

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
      nodeList: [
        {
          value: '1',
          label: 'uds-02'
        },
        {
          value: '0',
          label: 'uds-01'
        }
      ],
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
        {
          title: '日志文件名',
          width: 638,
          key: 'logFileName'
        },
        {
          title: '操作',
          width: 160,
          // fixed: 'right',
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
                  click: () => {
                    let params = {}
										this.formBean.logFileName = row.logFileName
										this.formBean.path = row.path
										this.formBean.ip = this.transData.info.ip		
										Object.assign(params, this.formBean)
										let httpConfig = {
                      method: 'GET',
                      url: RESOURCE_PATH + '/logdownload',
                      params: params
                    }
										params.type = 'excel'
										utils.download(RESOURCE_PATH + '/logdownload', params)
										// this.$ajax(httpConfig)
										// .then(resp => {
										// 	if(resp.data==true||resp.data=="true"){
										// 		let param = {};
										// 		this.formBean.logFileName =row.logFileName;
										// 		this.formBean.path =row.path;
										// 		Object.assign(param, this.formBean);
										// 		param.type = 'excel';	
										// 		utils.download(RESOURCE_PATH + '/downLoad', param);
										// 		//this.$emit('switch', {id: id}); //提交form事件，在parent中显示form
										// 	}
										// })
										
									}
                }
              }, '下载')

            ])
          }
        }
      ],
      // gridData: [{logFileName:"node-1.log"},{logFileName:"node-2.log"},{logFileName:"node-3.log"}],
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
      ruleValidate: {
      }
    }
  },
  methods: {
    // paramChange(){
    // 	if(this.formBean.param==0){
    // 		this.$emit('switch',{ntable:"true"});
    // 	}
    // },
    /**
		 * 初始化
		 **/
    init () {
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
		 * 查询
		 **/
    search () {
      this.formBean.ip = this.transData.info.ip
			let params = {}
			Object.assign(params, this.formBean)
			params.currentPage = this.page.current
			params.pageSize = this.page.size
			//params={host:host};
			let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/log',
        params: params
      }
			this.$ajax(httpConfig)
        .then(resp => {
          this.gridData = resp.data.rows
				this.page.total = this.gridData.length
				this.page.num = Math.ceil(this.gridData.length / this.page.size) || 1
				//alert(this.gridData);
				for (let i = 0; i < this.page.num; i++) {
            this.totalPage[i] = this.gridData.slice(this.page.size * i, this.page.size * (i + 1))
          }
          this.gridData = this.totalPage[this.page.current - 1]
			})
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
      const ids = Array.from(this.selection, e => e.serverName)
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
			this.search()
		},
    /**
		 * 改变分页大小事件
		 **/
    changePageSize (pageSize) {
      this.page.size = pageSize
			this.search()
		},
    backserverList () {
      let queryCache = {formBean: this.transData.formBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize}
      this.$emit('switch', Object.assign({statusName: 'table'},queryCache)) //提交form事件，在parent中显示form
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

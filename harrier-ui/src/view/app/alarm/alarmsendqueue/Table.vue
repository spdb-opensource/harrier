<template>
  <div>
    <div class="spdb-form">
      <Form ref="alarmsettingForm" :model="formBean" :label-width="80" :rules='ruleValidate'>
        <Row>
        
          <!--<Col span="3">
            <Form-Item prop="code" label="报警码" >
              <Select v-model="formBean.code" style="width:100px" filterable  clearable>
                <Option v-for="item in alarmIdList" :value="item.value" :key="item.value" :label="item.value">{{ item.label }}</Option>
              </Select>
            </Form-Item>
          </Col>-->
          <!-- <Col span="3">
            <Form-Item prop="code" label="报警码">
              <Input  v-model="formBean.code"/>
            </Form-Item>
          </Col>  -->
        </Row>
      </Form>
    </div>

    <div class="spdb-toolbar">
      <Button type="primary" icon="ios-search" @click="search">查询</Button>
      <!-- <Button type="primary" icon="md-close" @click="clear">清除查询</Button>
      <Button type="primary" icon="md-add" @click="add" :requestConfig=request.addReq>新增</Button> -->
      <Button type="primary" :loading="loadingdel" :disabled="ctrlDisable" icon="ios-trash-outline" @click="del()">删除</Button>
      <!--<Button type="primary" :loading="loadingsync" icon="ios-sync" @click="sync">同步</Button>-->

    </div>

		<div class="spdb-table">
			<Table :columns="gridColumns" :data="gridData" stripe @on-selection-change="select">
			</Table>
		</div>

		<div class="spdb-page">
			<Page placement="top" :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat @on-change="changePage" @on-page-size-change="changePageSize"></Page>
		</div>
    		<Modal
			v-model="cuInfo.show"
			title="联系组详细信息"
			:mask-closable="false"
			>
			<div>
				<div>
					<Form :model="cuInfoFormBean" ref="cuInfoHandleForm" :label-width="90" inline>
						<Row>
							<FormItem style='width:75%' prop="platform" label="平台">
								<Input readonly v-model="cuInfoFormBean.platform"/>
							</FormItem>
						</Row>
						<Row>
							<FormItem style='width:75%' prop="systems" label="应用">
								<Input readonly v-model="cuInfoFormBean.systems"/>
							</FormItem>
						</Row>
						<Row>
							<FormItem  style='width:75%' prop="groupName" label="组名">
								<Input readonly v-model="cuInfoFormBean.groupName"/>
							</FormItem>
						</Row>
            <Row>
							<FormItem style='width:75%' prop="sendType" label="发送类型">
								<Input readonly v-model="cuInfoFormBean.sendType"/>
							</FormItem>
						</Row>
            <Row  v-for="(infor, i) in showSendTypeData" :key="`infor-${i}`">
              <FormItem  style='width:75%'  :label="infor[0]">
                <Input readonly v-model="infor[1]" />
              </FormItem>
            </Row>
					</Form>
				</div>
			</div>
		</Modal>
	</div>
</template>

<script>
import utils from '@/common/utils'

const RESOURCE_PATH = '/alarm/send'

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
      ctrlDisable: true,
      loadingdel: false,
      loadingsync: false,
      formBean: {},
      platformList: [],
      systemList: [],
      alarmIdList: [],
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
        {
          type: 'selection',
          width: 60,
          align: 'center'
          // fixed: 'left'
        },
        {
          title: '联系组',
          width: 90,
          align: 'center',
          key: 'groupName',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('a', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.showCuInfo(row)
                  }
                }
              }, 
              row.groupName)
            ])
          }
        },

        {
          title: '发送状态',
          key: 'sendStatus',
          align: 'center',
          minWidth: 95
        },
        {
          title: '发送类型',
          key: 'sendType',
          align: 'center',
          minWidth: 95
        },
        {
          title: '发送参数',
          key: 'sendParams',
          align: 'center',
          minWidth: 130
        },
        {
          title: '标题',
          key: 'title',
          align: 'center',
          minWidth: 95
        },
        {
          title: '发送内容',
          key: 'content',
          align: 'center',
          minWidth: 100
        },
        {
          title: '附件路径',
          key: 'filePath',
          align: 'center',
          minWidth: 95
        },
        {
          title: '发送异常',
          key: 'expcetion',
          align: 'center',
          minWidth: 95
        },
        {
          title: '发送时间',
          key: 'sendTime',
          align: 'center',
          minWidth: 130
        },
        {
          title: '生成时间',
          key: 'createTime',
          align: 'center',
          minWidth: 130
        },
        {
          title: '备注',
          key: 'remark',
          align: 'center',
          minWidth: 100
        },
        // {
        //   title: '操作',
        //   align: 'center',
        //   minWidth: 80,
        //   // fixed: 'right',
        //   render: (h, { column, index, row }) => {
        //     return h('div', [
        //       h('Button', {
        //         props: {
        //           size: 'small',
        //           type: 'info'
        //         },
        //         style: {
        //           marginRight: '5px'
        //         },
        //         on: {
        //           click: () => {
        //             this.$emit('switch', Object.assign({}, { row: row, formBean: this.formBean }, this.getPageParam()))
        //           }
        //         }
        //       }, '编辑')
        //     ])
        //   }
        // }
      ],
      gridData: [],
      selection: [],
      
      page: {
        current: 1,
        total: 10,
        size: 10,
        opt: [10, 50, 100]
      },
      cuInfo: {
        show: false
      },
      showSendTypeData: [],
      cuInfoFormBean: {},
      sendTypeDataBean: {},
      ruleValidate: {
      }
    }
  },
  watch: {
    selection: function (val) {
      
      if (val.length != 0) {
        this.ctrlDisable = false
      } else {
        this.ctrlDisable = true
      }
    }
  },
  methods: {
    loadplat: function () {
      this.platformList = []
      const loadConfig = {
        method: 'GET',
        url: 'msys/getPlatformList',
        params: { platformType: '1,2' }
      }
      this.$ajax(loadConfig).then(resp => {
        if (resp.data) {
          resp.data.forEach(data1 => {
            let temp = {}
            temp.key = data1
            temp.value = data1
            temp.label = data1
            this.platformList.push(temp)
          })
        }
      })
    },
    // 同步
    sync () {
      // 同步
      let loadConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/sync'
      }
      this.loadingsync = true
      this.$ajax(loadConfig)
        .then(resp => {
          this.loadingsync = false
          if (resp.data == '1') {
            this.$Notice.success({
              title: '信息',
              desc: '同步成功'
            })
          } else {
            this.$Notice.error({
              title: '信息',
              desc: '同步失败'
            })
          }
          this.search()
        })
    },
    /**
		 * 初始化
		 **/
    init () {
      if (this.transData.group_name) {
        this.formBean.group_name = this.transData.group_name
      }
      if (this.transData.currentPage) {
        this.page.current = this.transData.currentPage
        this.page.size = this.transData.pageSize
      }
      this.search()
      this.getsendTypeList()
    },
    getsendTypeList () {
      let httpConfig = {
        method: 'GET',
        url: '/alarm/group/sendType'
      }
      this.$ajax(httpConfig)
        .then(resp => {
          if (resp.data) {
            this.sendTypeList = []
            let keys = Object.keys(resp.data)
            let values = Object.values(resp.data)
            for (let i = 0; i < keys.length; i++) {
              let valueData = values[i]
              let sendType = keys[i].split(';')
              this.sendTypeDataBean[sendType[0]] = valueData
            }
          }
        })
    },
    showCuInfo (row) {
      this.showSendTypeData = []
      this.cuInfoFormBean = {}

      this.cuInfo.show = true
      const config = {
        method: 'GET',
        url: '/alarm/group/getDetailByGroupName',
        params: { groupName: row.groupName }
      }
      this.$ajax(config)
        .then(resp => {
          if (resp.data && resp.data.length > 0) {
            this.cuInfoFormBean = resp.data[0]
            let oldSendType = JSON.parse(this.cuInfoFormBean.sendParams)
            let sendTypeData = this.sendTypeDataBean[this.cuInfoFormBean.sendType]
            // 循环取出自定义的key和系统中的key
            let oldSendTypeKeys = Object.keys(oldSendType)
            for (let i = 0; i < oldSendTypeKeys.length; i++) {
              let key = oldSendTypeKeys[i]
              let showKey = key
              for (let j = 0; j < sendTypeData.length; j++) {
                if (key === sendTypeData[j][1]) {
                  showKey = sendTypeData[j][0]
                  break
                }
              }
              let param = []
              param.push(showKey)
              param.push(oldSendType[key])
              this.showSendTypeData.push(param)
            }
          }
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
    /**
		 * 查询
		 **/
    search () {
      let params = {}
      Object.assign(params, this.formBean)
      params.current = this.page.current
      params.size = this.page.size
      if (this.formBean.group_name) {
        params.currentPage = 1
       
      }
      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH,
        params: params
      }
      this.$ajax(httpConfig)
        .then(resp => {
          this.gridData = resp.data.records
          this.page.total = resp.data.total
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
      const ids = Array.from(this.selection, e => e.id)

      ids.forEach(id => {
        this.request.delReq.url = RESOURCE_PATH + '/delete' 
        this.request.delReq.method = 'DELETE'
         this.request.delReq.params = {}
         this.request.delReq.params.id = id
        this.loadingdel = true
        this.$ajax(this.request.delReq)
          .then(resp => {
            this.loadingdel = false
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

    getPageParam () {
      return { formBean: this.alarmBean,
        currentPage: this.page.current,
        pageSize: this.page.size }
    },
    /**
		 * 改变页码事件
		 **/
    changePage (currentPage) {
      // alert(currentPage);
      
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
    //  this.loadplat()
  }
}
</script>

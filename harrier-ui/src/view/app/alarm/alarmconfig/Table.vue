<template>
  <div>
    <div class="spdb-form">
      <Form ref="alarmsettingForm" :model="formBean" :label-width="80" :rules='ruleValidate'>
        <Row>
          <Col span="3">
            <Form-Item prop="platform" label="平台名" >
              <Select v-model="formBean.platform" style="width:100px" filterable  clearable>
                <Option v-for="item in platformList" :value="item.value" :key="item.value" :label="item.value">{{ item.label }}</Option>
              </Select>
            </Form-Item>
          </Col>
          <Col span="3">
            <Form-Item prop="systems" label="应用名" >
              <Select v-model="formBean.systems" style="width:100px" filterable  clearable>
                <Option v-for="item in systemsList" :value="item.value" :key="item.value" :label="item.value">{{ item.label }}</Option>
              </Select>
            </Form-Item>
          </Col>
          <Col span="3">
            <Form-Item prop="code" label="告警码">
              <Select v-model="formBean.code" style="width:100px" filterable  clearable>
                <Option v-for="item in codeList" :value="item.value" :key="item.value" :label="item.value">{{ item.label }}</Option>
              </Select>
            </Form-Item>
          </Col>
        </Row>
      </Form>
    </div>

    <div class="spdb-toolbar">
      <Button type="primary" icon="ios-search" @click="search">查询</Button>
      <Button type="primary" icon="md-close" @click="clear">清除查询</Button>
      <!-- <S-AuthButton type="primary" icon="md-add" @click="add" :requestConfig=request.addReq>新增</S-AuthButton>
      <S-AuthButton type="primary" :loading="loadingdel" :disabled="ctrlDisable" icon="ios-trash-outline" @click="del()">删除</S-AuthButton> -->
      <Button type="primary" icon="md-add" @click="add" :requestConfig=request.addReq>新增</Button>
      <Button type="primary" :loading="loadingdel" :disabled="ctrlDisable" icon="ios-trash-outline" @click="del()">删除</Button>
      <!-- <Button type="primary" :loading="loadingsync" icon="ios-sync" @click="sync">同步</Button> -->
      <!--<Button type="primary" icon="md-cloud-download" @click="download">下载</Button>-->
    </div>

		<div class="spdb-table">
			<Table :columns="gridColumns" :data="gridData" stripe @on-selection-change="select">
			</Table>
		</div>

		<div class="spdb-page">
			<Page placement="top" :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat @on-change="changePage" @on-page-size-change="changePageSize"></Page>
		</div>
	</div>
</template>

<script>
import utils from '@/common/utils'

const RESOURCE_PATH = '/alarm/config'

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
      systemsList: [],
      codeList: [],
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
          title: '平台名',
          key: 'platform',
          align: 'center',
          width: 80
        },
        {
          title: '应用名',
          key: 'systems',
          align: 'center',
          width: 95
        },
        {
          title: '告警码',
          key: 'code',
          align: 'center',
          width: 95
        },
        {
          title: '作业',
          key: 'job',
          align: 'center',
          width: 95
        },
        {
          title: '告警默认状态',
          key: 'defStatus',
          align: 'center',
          width: 130
        },
        {
          title: '通知次数',
          key: 'noticeTimes',
          align: 'center',
          width: 95
        },
        {
          title: '通知间隔分',
          key: 'noticeCycle',
          align: 'center',
          minWidth: 120
        },
        {
          title: '通知用户组',
          key: 'noticeGroupName',
          align: 'center',
          minWidth: 120
        },
        {
          title: '是否生成告警信息',
          key: 'build',
          align: 'center',
          minWidth: 150,
          render: (h, { column, index, row }) => {
            return h('div', [
              h('Span', {}, row.build ? '是' : '否')
            ])
          }
        },
        {
          title: '是否启用',
          key: 'isEnable',
          align: 'center',
          minWidth: 138,
          render: (h, { column, index, row }) => {
            return h('div', [
              h('Span', {}, row.isEnable ? '是' : '否')
            ])
          }
        },
        {
          title: '更新用户',
          key: 'updateUser',
          align: 'center',
          width: 95
        },
        {
          title: '备注',
          key: 'remark',
          align: 'center',
          minWidth: 138
        },
        {
          title: '操作',
          align: 'center',
          width: 80,
          // fixed: 'right',
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
                    this.$emit('switch', Object.assign({}, { row: row, formBean: this.formBean }, this.getPageParam()))
                  }
                }
              }, '编辑')
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
      if (this.transData.etlPlatform) {
        this.formBean.etlPlatform = this.transData.etlPlatform
      }
      if (this.transData.currentPage) {
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
    /**
		 * 查询
		 **/
    search () {
      let params = {}
      Object.assign(params, this.formBean)
      params.currentPage = this.page.current
      params.pageSize = this.page.size
      if (this.formBean.etlPlatform) {
        params.currentPage = 1
      }
      params.job = '*'
      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH,
        params: params
      }

      this.$ajax(httpConfig)
        .then(resp => {
          console.log(resp)
          // if (resp.data.rows) {
          //   resp.data.rows.forEach(data1 => {
          //     if (data1.failedalarmstatus) {
          //       data1.failedalarmstatus = '是'
          //     } else {
          //       data1.failedalarmstatus = '否'
          //     }
          //     if (data1.fluctualAlarmStatus) {
          //       data1.fluctualAlarmStatus = '是'
          //     } else {
          //       data1.fluctualAlarmStatus = '否'
          //     }
          //     if (data1.baselineAlarmStatus) {
          //       data1.baselineAlarmStatus = '是'
          //     } else {
          //       data1.baselineAlarmStatus = '否'
          //     }

          //     if (data1.timeoutalarmstatus) {
          //       data1.timeoutalarmstatus = '是'
          //     } else {
          //       data1.timeoutalarmstatus = '否'
          //     }
          //     if (data1.notificationemailstatus) {
          //       data1.notificationemailstatus = '是'
          //     } else {
          //       data1.notificationemailstatus = '否'
          //     }
          //     if (data1.notificationlimitstatus) {
          //       data1.notificationlimitstatus = '是'
          //     } else {
          //       data1.notificationlimitstatus = '否'
          //     }
          //     if (data1.pendingalarmstatus) {
          //       data1.pendingalarmstatus = '是'
          //     } else {
          //       data1.pendingalarmstatus = '否'
          //     }
          //     if (data1.dispatcheralarmstatus) {
          //       data1.dispatcheralarmstatus = '是'
          //     } else {
          //       data1.dispatcheralarmstatus = '否'
          //     }
          //     if (data1.complementTimeOutStatus) {
          //       data1.complementTimeOutStatus = '是'
          //     } else {
          //       data1.complementTimeOutStatus = '否'
          //     }
          //   })
          // }
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
      this.selection.forEach(item => {
        this.request.delReq = {
          url: RESOURCE_PATH + '/delete',
          method: 'DELETE',
          params: { id: item.id, platform: item.platform }
        }
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

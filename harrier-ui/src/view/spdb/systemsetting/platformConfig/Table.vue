<template>
	<div>
		<div class="spdb-form">
			<Form ref="systemForm" :model="formBean" :label-width="75" :rules='ruleValidate'>
				<Row>
					<!--
					<Col span="6">
						<Form-Item prop="param" label="配置选择">
							<Select filterable placeholder="请选择执行节点或者调度系统" v-model="formBean.param" style="width:190px" @on-change="paramChange()">
								<Option v-for="item in statusList" :value="item.value" :key="item.value">{{ item.label }}</Option>
							</Select>
						</Form-Item>
					</Col>
					<Col span="3">
						<Form-Item label="调度系统并发配置" label-width="110">
						</Form-Item>
					</Col>-->
					<Col span="4">
						<FormItem prop="platform" label="平台名">
						<Select filterable v-model="formBean.platform" @on-change="getSystemList" clearable>
							<Option v-for="item in platformData" :value="item.value" :key="item.value">{{ item.label }}</Option>
						</Select>
					</FormItem>
					</Col>
					<Col span="4">
						<FormItem label="应用名">
              <Select ref="refsystem" filterable v-model="formBean.systems" placeholder="先选平台" clearable>
							  <Option v-for="item in systemData" :value="item.value" :key="item.value">{{ item.label }}</Option>
						  </Select>
						</FormItem>
					</Col>
          <Col span="4">
            <FormItem prop="dbControl" label="逻辑集群" >
                        <Select
                            :transfer="true"
                            filterable
                            v-model="formBean.dbControl"
                            clearable
                        >
                            <Option
                                v-for="item in dbControlList"
                                :value="item.value"
                                :key="item.value"
                                >{{ item.label }}</Option
                            >
                        </Select>
                    </FormItem>
          </Col>
					<Col span="3">
						<FormItem label="是否启用" >
            	<Select filterable v-model="formBean.usePlatform" clearable>
                <Option value="0">启用</Option>
                <Option value="1">禁用</Option>
						  </Select>
						</FormItem>
					</Col>
				</Row>
			</Form>
		</div>
		<Modal id="allStart"
			v-model="allStart.show"
			title="全平台恢复并发"
			width='50%'
			:mask-closable="false"
			>
			<div>
				<div>
					<Form  ref="allStartForm" :label-width="150"  inline>
					<!--	<Row>
							<FormItem prop="maxRunJob" label="最大运行作业数默认值">
							<input type="number" style="border-radius:5px" v-model="formBean2.maxRunJob"  oninput="if(value<0)value=0" />
						</FormItem>
						</Row>-->
						<Row>
              <Col span="9">
                <FormItem style='width:100%'  label="恢复并发平台">
                  <Select filterable v-model="formBean2.platform" clearable>
                    <Option v-for="item in platformData2" :value="item.value" :key="item.value">{{ item.label }}</Option>
                  </Select>
                </FormItem>
              </Col>
              <Col span="9">
                    <FormItem prop="dbControl" label="逻辑集群" >
                        <Select
                            :transfer="true"
                            filterable
                            v-model="formBean2.dbControl"
                            clearable
                        >
                            <Option
                                v-for="item in dbControlList"
                                :value="item.value"
                                :key="item.value"
                                >{{ item.label }}</Option
                            >
                        </Select>
                    </FormItem>
                </Col>
							<Button style='width:10%' type="primary" @click="allstart" :loading="loadingStatus">
								{{ loadingStatus ? 'Loading' : '恢复' }}
							</Button>
						</Row>
					</Form>
				</div>
			</div>
			<div slot="footer"></div>
		</Modal>
		<Modal id="allStop"
			v-model="allStop.show"
			title="全平台停止并发"
			width='50%'
			:mask-closable="false"
			>
			<div>
				<div>
					<Form  ref="allStopForm" :label-width="150"  inline>
						<Row>
             <Col span="9">
                <FormItem style='width:100%'  label="停止并发平台:">
                  <Select filterable v-model="formBean3.platform" clearable>
                    <Option v-for="item in platformData3" :value="item.value" :key="item.value">{{ item.label }}</Option>
                  </Select>
                </FormItem>
              </Col>
              <Col span="9">
                    <FormItem prop="dbControl" label="逻辑集群" >
                        <Select
                            :transfer="true"
                            filterable
                            v-model="formBean3.dbControl"
                            clearable
                        >
                            <Option
                                v-for="item in dbControlList"
                                :value="item.value"
                                :key="item.value"
                                >{{ item.label }}</Option
                            >
                        </Select>
                    </FormItem>
                </Col>
								<Button style='width:10%' type="primary" @click="allstop" :loading="loadingStatus2">
									{{ loadingStatus2 ? 'Loading' : '停止' }}
								</Button>

						</Row>
					</Form>
				</div>
			</div>
			<div slot="footer"></div>
		</Modal>
		<div class="spdb-toolbar">
			<Button type="primary" icon="ios-search" @click="demand">查询</Button>
			<Button type="primary" icon="md-close" @click="clear">清除查询</Button>
			<Button type="primary" icon="md-cloud-download" @click="download">下载</Button>
			<Button type="primary"  @click="allStartOpen" :requestConfig=request.delReq>全平台恢复并发 </Button>
			<Button type="primary"  @click="allStopOpen" :requestConfig=request.delReq>全平台停止并发</Button>
		</div>
		<div class="spdb-table">
			<Table :columns="gridColumns" :data="gridData" stripe>
			</Table>
		</div>

		<div class="spdb-page">
			<Page placement="top" :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat @on-change="changePage" @on-page-size-change="changePageSize"></Page>
		</div>
	</div>
</template>

<script>
import utils from '@/common/utils'

const RESOURCE_PATH = '/udsSystem'

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
      formBean3: {},
      formBean2: {},
      dbControlList: [],
      dbControlName: {},
      allStop: {
        show: false
      },
      allStart: {
        show: false
      },
      loadingStatus: false,
      loadingStatus2: false,
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
      statusList: [
        {
          value: '1',
          label: '执行节点并发配置'
        },
        {
          value: '0',
          label: '调度系统并发配置'
        }

      ],
      strategyList: [
        {
          value: '0',
          label: '普通策略'
        },
				 {
          value: '1',
          label: '指定机器分发机器'
        },
        {
          value: '2',
          label: '指定机器序号分发'
        },
        {
          value: '3',
          label: '指定机器标签分发'
        }
      ],
      gridColumns: [
        {
          title: '平台名',
          key: 'platform',
          minWidth: 100
        },
        {
          title: '应用名',
          key: 'systems',
          minWidth: 100
        },
        {
          title: '逻辑集群',
          key: 'serverRoleNameGroup',
          minWidth: 100
          // render: (h, { column, index, row }) => {
          //   return h('div', [
          //     h('Span', {}, this.dbControlName[row.serverRoleNameGroup])
          //   ])
          // }
        },
        // {
        // 	title: '脚本信号文件保存天数',
        // 	key: 'dataKeepDay',
        // },
        // {
        // 	title: '脚本日志保存天数',
        // 	key: 'logKeepDay',
        // },
        // {
        // 	title: '记录保存天数',
        // 	key: 'recordKeepDay',
        // },

        // {
        //   title: '该平台最大运行作业数',
        //   key: 'sumMax',
        //   width: 160,
        //   align: 'center'
        // },
        {
          title: '最大运行作业数',
          key: 'maxRunJob',
          align: 'center',
          minWidth: 110
        },
        {
          title: '分发策略',
          key: 'select',
          align: 'center',
          minWidth: 150,
          render: (h, params) => {
            let strategy = params.row.select
            let str = ''
            this.strategyList.forEach(data1 => {
              if (data1.value == strategy) {
                str = data1.label
              }
            })
            return h('div', {
              props: {
              }
            }, str)
          }
        },
        {
          title: '策略参数',
          key: 'selectPro',
          // width: 200,
          minWidth: 200,
          // 超出长度省略符号
          render: (h, params) => {
            let str = params.row.selectPro
            let str1 = ''
            let str0 = ''
            while (str.length > 30) {
              str0 = str.substring(0, 30) + '\n'
              str = str.substring(30, str.length)
              str1 = str1 + str0
            }

            return h('div', [
              h('span', {
                style: {
                  display: 'inline-block',
                  width: '100%',
                  overflow: 'hidden',
                  textOverflow: 'ellipsis',
                  whiteSpace: 'nowrap'// 'nowrap'
                },
                // 鼠标悬停 显示完整信息
                domProps: {
                  title: str1 // params.row.strategyPro,

                },
                on: {
                  click: (e) => {
                    e.stopPropagation()
                  }
                }
              }, params.row.selectPro)
            ])
          }
        },
        {
          title: '是否启用',
          key: 'usePlatform',
          align: 'center',
          minWidth: 110
        },
        {
          title: '描述',
          key: 'des',
          minWidth: 150
        },
        {
          title: '操作',
          align: 'center',
          width: 100,
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
                    let platform = row.platform
                    let sys = row.systems
                    let dbControl = row.serverRoleNameGroup
                    let queryCache = { formBean: this.formBean, currentPage: this.page.current, pageSize: this.page.size }
                    this.$emit('switch', Object.assign({ platform: platform, sys: sys, dbControl: dbControl}, queryCache)) // 提交form事件，在parent中显示form
                  }
                }
              }, '编辑')
            ])
          }
        }
      ],
      strategyList: [
        {
          value: '0',
          label: '普通策略'
        },
        {
          value: '1',
          label: '指定机器分发机器'
        },
        {
          value: '2',
          label: '指定机器序号分发'
        },
        {
          value: '3',
          label: '指定机器标签分发'
        }
      ],
      gridData: [],
      systemData: [],
      platformData: [],
      platformData2: [],
      platformData3: [],
      // selection: [],
      page: {
        current: 1,
        total: 10,
        size: 10,
        opt: [10, 50, 100]
      },
      ruleValidate: {
      }
    }
  },
  methods: {
    paramChange () {
      if (this.formBean.param == 1) {
        this.$emit('switch', { mtable: 'true' })
      }
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
            this.platformData2.push(tmp)
            this.platformData3.push(tmp)
          })
        })
    },
    querySystem () {
      let loadConfig = {
        method: 'GET',
        url: '/msys/getSysList',
        params: { platform: this.formBean.platform }
      }

      // modify by jcjin 20200529 for systemData返回值调整为key-value
      // this.$ajax(loadConfig)
      // .then(resp => {
      //	this.formBean.system = "";
      //	this.systemData = resp.data;
      //
      // })
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
    filterMethod (value, option) {
      return option.toUpperCase().indexOf(value.toUpperCase()) !== -1
    },
    /**
		 * 初始化
		 **/
    init () {
      if (this.transData.currentPage) {
        this.formBean = this.transData.formBean
        this.page.current = this.transData.currentPage
        this.page.size = this.transData.pageSize
      }
      // this.queryDbControlList()
      this.search()
      this.getPlatformList()
      // this.queryPlatform()
    },
    /**
     * 如果传过来的有初始数据则进行数据绑定
     **/
    bindData (fields) {
      try {
        const data = Object.assign({}, this.transData.initFormBean)
        if (fields) { // 进行可选字段初始化
          for (const field of fields) {
            this.formBean[field] = data[field]
          }
        } else { // 进行全量字段初始化（默认是只有主表的主键的）
          this.formBean = Object.assign({}, data)
        }
      } catch (error) {
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
    queryDbControlList () {
      let loadConfig = {
        method: 'GET',
        url: '/dictionary',
        params: { dicCode: 'db_control' }
      }
      this.$ajax(loadConfig).then(resp => {
        this.dbControlList = []
        resp.data.rows.forEach(data => {
          this.dbControlName[parseInt(data.dicValue)] = data.dicName
          let tmp = {}
          tmp.value = parseInt(data.dicValue)
          tmp.label = data.dicName
          this.dbControlList.push(tmp)
        })
      })
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
        url: RESOURCE_PATH + '/selectAll',
        params: params
      }
      this.$ajax(httpConfig)
        .then(resp => {
          // this.gridData = resp.data.rows
          // this.page.total = resp.data.total
          this.gridData = resp.data.rows
          this.page.total = resp.data.rows.length
          resp.data/* .rows */.forEach(data1 => {
            if (data1.usePlatform == 1) {
              data1.usePlatform = '禁用'
            } else {
              data1.usePlatform = '启用'
            }
            // if (data1.strategy == 0) {
            //   data1.strategy = '普通策略'
            // } else {
            //   // alert(data1.strategy);
            //   data1.strategy = '指定机器分发机器'
            // }

            data1.strategy = data1.select + ''
          })
        })
    },
    demand () {
      this.page.current = 1
      this.search()
    },
    allStartOpen () {
      this.allStart.show = true
    },
    allStopOpen () {
      this.allStop.show = true
    },
    allstop () {
      // this.$refs.allStopForm.validate(valid => {
      // if(!valid) return;
      if (!this.formBean3.platform) {
        this.$Message.warning('请选择平台')
        return
      }
      let params = {}
      Object.assign(params, this.formBean3)
      params.authps = this.formBean3.platform + '*'
      let httpConfig = {
        url: RESOURCE_PATH,
        data: params
      }
      if (this.formBean3.platform) {
        httpConfig.method = 'PUT'
        httpConfig.url = RESOURCE_PATH + '/allStop'
      }
      this.loadingStatus2 = true
      this.$ajax(httpConfig)
        .then(resp => {
          this.loadingStatus2 = false
          // let str=resp.data;
          if (resp.status && resp.status == 200) {
            this.allStop.show = false
            this.search()
          }
        })
      // });
    },
    allstart () {
      // if(!this.formBean2.maxRunJob){
      // 	this.$Message.warning("请输入默认值");
      // 	return ;
      // }
      if (!this.formBean2.platform) {
        this.$Message.warning('请选择平台')
        return
      }
      let params = {}
      Object.assign(params, this.formBean2)
      params.authps = this.formBean2.platform + '*'
      let httpConfig = {
        url: RESOURCE_PATH,
        data: params
      }
      if (this.formBean2.platform) {
        httpConfig.method = 'PUT'
        httpConfig.url = RESOURCE_PATH + '/allStart'
      }
      this.loadingStatus = true
      this.$ajax(httpConfig)
        .then(resp => {
          this.loadingStatus = false
          // let str=resp.data;
          if (resp.status && resp.status == 200) {
            this.allStart.show = false
            this.search()
          }
        })
    },

    /**
		 * 清空
		 **/
    clear () {
      this.formBean = {}
      this.selection = []
      this.page = {
        current: 1,
        size: 10
      }
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
    // harrier getPlatformList
    getPlatformList () {
      let loadConfig = {
        method: 'GET',
        url: '/udsSystem/listQuery'
      }
      this.$ajax(loadConfig)
        .then(resp => {
          let platformList = []
          resp.data.forEach(data => {
            platformList.push(data.platform)
            // this.formBean.platform = "UDS";
          })
          platformList = Array.from(new Set(platformList))
          platformList.forEach(data => {
            let tmp = {}
            tmp.value = data
            tmp.label = data
            this.platformData.push(tmp)
            this.platformData2.push(tmp)
            this.platformData3.push(tmp)
            // this.formBean.platform = "UDS";
          })
          this.search()
        })
    },
    // harrier getSystemList
    getSystemList () {
      let loadConfig = {
        method: 'GET',
        url: '/udsSystem/listQuery'
      }
      this.systemData = []
      this.$refs.refsystem.setQuery('')
      if (this.formBean.platform) {
        this.$ajax(loadConfig)
          .then(resp => {
            resp.data.forEach(data => {
              if (this.formBean.platform === data.platform && data.systems !== '*') {
                let tmp = {}
                tmp.value = data.systems
                tmp.label = data.systems
                this.systemData.push(tmp)
              }
            })
          })
      }
    }
    // harrier test end
  },
  /**
	 * 视图挂载
	 **/
  mounted () {
    this.init()
  }
}
</script>

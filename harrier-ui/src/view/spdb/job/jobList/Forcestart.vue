<template>
	<div>
		<div style="margin-bottom:5px;">
			<Row>
				<Col span="20">
					<div style="font-size:14px;font-weight: bold; color: #464c5b;margin-left:5px;">
					补数
					&nbsp;&nbsp;平台名:{{this.formBean.platform}}
					&nbsp;&nbsp;应用名:{{this.formBean.systems}}
					&nbsp;&nbsp;作业名:{{this.formBean.job}}
					</div>
				</Col>
				<Col span="4">
					<Button size="small" type="primary" icon="ios-arrow-back" @click="cancel" style="">返回</Button>
				</Col>
			</Row>
		</div>
		<Form ref="forceStartForm" :model="formBean" :label-width="160" :rules="formRule">
			<Row>
				<Col span="8">
					<FormItem prop="platform" label="平台名">
						<Input readonly v-model="formBean.platform"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="system" label="应用名">
						<Input readonly v-model="formBean.systems"/>
					</FormItem>
				</Col>
				</Row>
			<Row>
				<Col span="8">
					<FormItem prop="job" label="作业名">
						<Input readonly type="textarea" :autosize="{minRows: 1,maxRows: 5}" v-model="formBean.job"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="job" label="状态">
						<Input readonly v-model="formBean.lastStatus"/>
					</FormItem>
				</Col>
			</Row>
			<Row>
				<Col span="8">
					<FormItem prop="jobDateArr" label="补数日期">
						<DatePicker :transfer="true" multiple  type="date"  format="yyyyMMdd" v-model="formBean.jobDateArr"></DatePicker>
					</FormItem>
				</Col>
				</Row>
				<Row >
				<Col span="8">
					<FormItem prop="serverName" label="执行节点">
							<Select :transfer="true" filterable v-model="formBean.serverName" clearable placeholder="请选择">
                <Option v-for="item in serverData" :value="item.value" :key="item.value">{{ item.label }}</Option>
              </Select>
						<!--<Input  v-model="formBean.serverName" />-->
					</FormItem>
				</Col>
        <!-- <Col span="12" >
					<FormItem>
            <Alert type="warning" > 提示:udsslavedep是交换的节点，专门跑Datastage的。perl脚本选udsslave即可</Alert>
					</FormItem>
				</Col> -->
				</Row>
				<Row >
				<Col span="8">
					<FormItem prop="multiBatch" label="批次号">
						<Input  v-model="formBean.multiBatch" :disabled="batchDisabled"/>
					</FormItem>
				</Col>
				</Row>
		</Form>

		<div style="margin-left: 150px;">
			<Button type="primary" icon="md-add" @click="confirmBtn" :disabled="fStartDisable">补数</Button>
      <!-- <Button type="primary" icon="ios-search" @click="showJobComplement">补数详情</Button> -->
			<Button type="primary" icon="ios-trash-outline" @click="cancel">取消</Button>
		</div>

    <Modal id="jobComplement"
			v-model="jobcomplement.show"
      width="90"
			:title="jobComplementTitle">
      <div class="spdb-table" style="min-height:300px">
        <Table :columns="gridColumns" :data="gridData" stripe @on-sort-change="changeSort">
        </Table>
      </div>
      <div class="spdb-page">
        <Page  placement="top" :total="page.total" :current="page.current" :page-size-opts="page.opt" show-total show-sizer show-elevat @on-change="changePage" @on-page-size-change="changePageSize"></Page>
      </div>
      <div slot="footer"></div>
    </Modal>

		<Modal id="confirmM"
			v-model="confirm.show"
			:mask-closable="false"
			title="提示">
			<div>
				<Alert type="warning" class="m-warn-con">
          <span style="font-weight:bold">友情提示:</span><br/>
          <div style="margin-left:4%">
          1.补数不检查依赖<br/>
          2.补数不触发下游作业<br/>
          3.补数不会修改正常批量作业TXDate<br/>
          4.补数不修改正常批量作业状态<br/>
          </div>
          <span style="font-weight:bold">注意事项:</span><br/>
          <div style="margin-left:4%">
          1.补数后作业状态以补数管理查看为准<br/>
					2.请在当日正常批量完成后补数,否则会影响正常批量运行<br/>
					3.补数和正常批量不能同时进行,请确保补数运行时间<br/>
					4.如因补数造成一切问题,执行人承担一切责任<br/>
          </div>
          <!-- 补数不检查依赖,不触发下游作业,不会修改TXDate<br/>
          请在当日批量完成后补数,否则会影响批量执行 -->
        </Alert>
        <Checkbox class="m-warn-con" v-model="confirm.isAgree" > 我承担该操作的一切后果</Checkbox>
			</div>
			<div slot="footer">
				<div style="margin-left: 150px;">
					<Button type="primary" :loading="loading" @click="save">确定</Button>
					<Button type="primary"  @click="confirmCancel">取消</Button>
				</div>
			</div>
		</Modal>

	</div>
</template>

<script>
import utils from '@/common/utils'
const RESOURCE_PATH = '/udsJob'

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
      jobComplementTitle: '',
      prequeryCache: {},
      jobcomplement: {
        show: false
      },
      batchDisabled: true,
      serverData: [],
      loading: false,
      fStartDisable: false,
      confirm: {
        show: false
      },
      formBean: {},
      formRule: {
      },
      gridData: [],
      page: {
        current: 1,
        total: 10,
        size: 10,
        opt: [10, 50, 100]
      },
      gridColumns: [
        // {
        //   title: '平台名',
        //   key: 'platform',
        //   sortable: true,
        //   width: 100
        // },
        // {
        //   title: '应用名',
        //   key: 'system',
        //   sortable: true,
        //   width: 100
        // },
        // {
        //   title: '作业名',
        //   key: 'job',
        //   sortable: true,
        //   minWidth: 300
        // },
        {
          title: '作业运行状态',
          key: 'lastStatus',
          sortable: true,
          align: 'center',
          width: 140
        },
        {
          title: 'jobDate',
          key: 'jobDate',
          align: 'center',
          sortable: true,
          width: 120
        },
        {
          title: '节点名',
          key: 'serverName',
          sortable: true,
          width: 120
        },
        {
          title: '批次号',
          key: 'multiBatch',
          sortable: true,
          align: 'center',
          width: 100
        },
        {
          title: '开始执行时间',
          key: 'startTime',
          align: 'center',
          sortable: true,
          width: 175
        },
        {
          title: '结束时间时间',
          key: 'endTime',
          align: 'center',
          sortable: true,
          width: 175
        },
        {
          title: '日志',
          width: 80,
          align: 'center',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('Button', {
                props: {
                  icon: 'md-search'
                },
                style: {
                  // marginRight: '5px'
                  margin: '0px',
                  fontSize: '20px',
                  border: 0,
                  padding: '4px 8px',
                  color: '#03399b',
                  background: 'transparent'
                },
                on: {
                  click: () => {
                    this.prequeryCache = this.transData
                    if (this.transData.prequeryCache) {
                      this.prequeryCache = this.transData.prequeryCache
                    }
                    let id = row.job
                    this.$emit('switch', Object.assign({}, { id: id, statusName: 'log', jobData: row, prevTab: 'forcestart', curTab: 'forcestart', prequeryCache: this.prequeryCache }, this.getPageParam())) // 提交form事件，在parent中显示form
                  }
                }
              }, '')
            ])
          }
        },
        {
          title: '请求参数',
          key: 'reqParam',
          align: 'center',
          minWidth: 300,
          render: (h, { column, index, row }) => {
            let texts = row.reqParam
            if (texts != null) {
              if (texts.length > 10) {
                texts = texts.slice(0, 10) + '...'
              } else {
                texts = row.reqParam
              }
            }
            let str = row.reqParam
            let str1 = ''
            let str0 = ''
            while (str.length > 30) {
              str0 = str.substring(0, 30) + '\n'
              str = str.substring(30, str.length)
              str1 = str1 + str0
            }
            str1 = str1 + str
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
                  title: str1
                }
              }, [
                h('span', {
                  props: {
                    size: 'small',
                    type: 'info'
                  }
                }, row.reqParam)
              ])
            ])
          }
        }
      ]
    }
  },
  methods: {
    /**
		 * 初始化
		 **/
    init () {
      this.formBean = this.transData.prequeryCache ? this.transData.prequeryCache.jobData : this.transData.jobData
      this.batchDisabled = (this.formBean.multiBatch === 0)
      this.formBean.lastStatus = 'Pending'
      this.formBean.isEnable = '1'
      if (this.transData) { // 如果传过来的存在ID则说明是编辑功能跳转过来
        let job = this.transData.prequeryCache ? this.transData.prequeryCache.id : this.transData.id
        let params = {}
        params.job = job
        params.platform = this.formBean.platform
        params.systems = this.formBean.systems
        let loadConfig = {
          method: 'GET',
          url: RESOURCE_PATH + '/selectJobDetail',
          params: params
        }
        this.$ajax(loadConfig)
          .then(resp => {
            this.formBean = resp.data.rows
            this.formBean.jobDateArr = utils.fmtDate(resp.data.rows.jobDate, 'yyyyMMdd')
            this.findServer()
            if (this.transData.prequeryCache) {
              this.page.size = this.transData.pageSize
              this.page.current = this.transData.currentPage
              // console.log(this.transData.prequeryCache)
              // this.showJobComplement()
            }
          })
      } else {
        // this.bindData()
      }
    },
    showJobComplement () {
      let job = this.transData.prequeryCache ? this.transData.prequeryCache.id : this.transData.id
      this.searchJobComplement()
      this.jobComplementTitle = '补数详情 ' + job
      this.jobcomplement.show = true
    },
    searchJobComplement () {
      let params = {}
      params.job = this.formBean.job
      params.currentPage = this.page.current
      params.pageSize = this.page.size
      let httpConfig = {
        method: 'GET',
        url: '/udsjobcomplement',
        params: params
      }
      this.$ajax(httpConfig)
        .then(resp => {
          this.gridData = resp.data.rows
          this.page.total = resp.data.total
        })
    },
    changeSort (column) {
      let classcon = window.event.currentTarget.className
      let order
      if (classcon.indexOf('ivu-icon-md-arrow-dropup') > -1) {
        order = 'ASC'
      } else if (classcon.indexOf('ivu-icon-md-arrow-dropdown') > -1) {
        order = 'DESC'
      }
      this.formBean.order = order
      this.formBean.sort = column.key
      this.searchJobComplement()
    },
    findServer () {
      let param = {}
      // param.location = this.formBean.dbControl
      let loadConfig = {
        method: 'GET',
        url: '/udsServer/listQuery',
        params: param
      }
      this.$ajax(loadConfig)
        .then(resp => {
          this.serverData = []
          resp.data.forEach(data => {
            let tmp = {}
            tmp.value = data.serverName
            tmp.label = data.serverName
            this.serverData.push(tmp)
          })
        })
    },
    confirmCancel () {
      this.confirm.show = false
    },
    confirmBtn () {
      // let diffdays = utils.dateDiff(this.transData.jobData.jobDate, this.formBean.jobDate, 'date')
      // console.log('diffdays:' + diffdays)
      /* if (this.formBean.batch == '0' && diffdays <= 0) {
				this.$Message.error({
						content:'单批次作业TXDate应大于当前TXDate:' + this.transData.jobData.jobDate,
						duration:0,
						closable: true
					});
				return;
			} else if (this.formBean.batch > 0 && diffdays < 0) {
				this.$Message.error({
						content:'多批次作业TXDate应大于等于当前TXDate:' + this.transData.jobData.jobDate,
						duration: 15,
						closable: true
					});
				return;
			} */
      if (!(this.formBean.lastStatus === 'SUCCESS' || this.formBean.lastStatus === 'READY')) {
        this.$Message.error({
          content: '当前作业状态不为SUCCESS或READY',
          duration: 15,
          closable: true
        })
        return
      }
      let date = this.formBean.jobDateArr
      this.formBean.jobDate = ''
      // if(typeof date !== 'string'){
      // if(Array.isArray(date)){
      //   alert(2)
      let arr = []
      const dateArr = Array.from(this.formBean.jobDateArr, e => e)
      for (let i = 0; i < dateArr.length; i++) {
        arr[i] = utils.fmtDate(dateArr[i], 'yyyyMMdd')
      }
      arr = arr.sort()
      this.formBean.jobDate = arr.join()
      // }else{
      //   alert(1)
      //   //  this.formBean.jobDate = utils.fmtDate(this.formBean.jobDate,'yyyyMMdd')
      // }
      this.confirm.show = true
      this.fStartDisable = true
      setTimeout(() => {
        this.fStartDisable = false
      }, 2000)
    },
    save () {
      if (!this.confirm.isAgree) {
        this.$Message.warning({ content: '请同意 我承担该操作的一切后果!' })
        return
      }
      let params = {}
      params.platform = this.formBean.platform
      params.systems = this.formBean.systems
      params.job = this.formBean.job
      params.localDate = this.formBean.jobDate
      params.multibatch = this.formBean.multiBatch

      // params.datalist = [this.formBean]
      // params.reqparams = {}
      // params.reqparams.invokeType = '1'
      // params.requesttype = 'lo'
      // params.authps = this.formBean.platform + this.formBean.system
      let httpConfig = {
        method: 'POST',
        url: RESOURCE_PATH + '/forceStartJob',
        data: params
      }
      // httpConfig.method = 'POST'
      // httpConfig.async = false;
      this.loading = true
      this.$ajax(httpConfig)
        .then(resp => {
          setTimeout(() => {
            this.loading = false
          }, 2000)
          //   if (resp.data.returnCode != null &&
          // 	resp.data.returnCode !== 'undefined' &&
          // 	resp.data.returnCode === 'fail') {
          //     return
          //   }
          //   if (resp.data === 'success') {
          //     this.confirm.show = false
          //     this.formBean.lastStatus = 'Running'
          //   }
          //   // this.cancel();
          // })
          if (resp.data === 1) {
            this.confirm.show = false
            this.$Message.success({
              content: '补数成功！',
              duration: 15,
              closable: true
            })
          } else {
            this.$Message.error({
              content: '补数失败！',
              duration: 15,
              closable: true
            })
            return
          }
          this.cancel()
        })
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
    },
    /**
		 * 改变页码事件
		 **/
    changePage (currentPage) {
      this.page.current = currentPage
      this.searchJobComplement()
    },
    /**
		 * 改变分页大小事件
		 **/
    changePageSize (pageSize) {
      this.page.size = pageSize
      this.searchJobComplement()
    },
    getPageParam () {
      return { formBean: this.formBean,
        currentPage: this.page.current,
        pageSize: this.page.size }
    }
  },
  /**
	 * 视图挂载
	 **/
  mounted () {
    this.init()
    // this.findServer()
  }
}

</script>
<style>
#confirmM .ivu-modal-body{
	padding:16px;font-size:12px;line-height:1.5;width: 100%;
}
</style>

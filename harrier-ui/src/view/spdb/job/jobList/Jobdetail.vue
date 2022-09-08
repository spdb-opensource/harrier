/* eslint-disable no-tabs */
<template>
    <div>
        <div>
            <!-- <Button type="primary" icon="md-add" @click="save">保存</Button> -->
            <Row>
                <Col span="20">
                    <div style="font-size:14px;font-weight: bold; color: #464c5b;margin-left:50px;">作业详细信息</div>
                </Col>
                <Col span="4">
                    <Button size="small" type="primary" icon="ios-arrow-back" @click="cancel" style="">返回</Button>
                </Col>
            </Row>
        </div>
        <Form ref="udsjobForm" :model="formBean" :label-width="220" >
            <Row>
                <Col span="8">
                    <FormItem prop="platform" label="平台名">
                        <Input readonly v-model="formBean.platform"/>
                    </FormItem>
                </Col>
                <Col span="8">
                    <FormItem prop="systems" label="应用名">
                        <Input readonly v-model="formBean.systems"/>
                    </FormItem>
                </Col>
                </Row>
                <Row>
                <Col span="8">
                    <FormItem prop="job" label="作业名">
                        <Input readonly type="textarea" :autosize="{minRows: 1,maxRows: 5}"  v-model="formBean.job"/>
                    </FormItem>
                </Col>
                <Col span="8">
                    <FormItem prop="jobName" label="作业中文名">
                        <Input readonly type="textarea" :autosize="{minRows: 1,maxRows: 5}"  v-model="formBean.jobName"/>
                    </FormItem>
                </Col>
                </Row>
                <Row>
                <Col span="8">
                    <FormItem prop="serverName" label="执行节点">
                        <Input readonly v-model="formBean.serverName"/>
                    </FormItem>
                </Col>
                <Col span="8">
                    <FormItem prop="jobType" label="作业类型">
                        <Input readonly v-model="formBean.jobType"/>
                    </FormItem>
                </Col>
                </Row>
                <Row>
                    <Col span="8">
                        <FormItem prop="jobDate" label="最后执行作业日期">
                            <DatePicker :transfer="true" readonly type="date" parse="yyyyMMdd" format="yyyyMMdd" v-model="formBean.jobDate"></DatePicker>
                        </FormItem>
                    </Col>
                    <Col span="8">
                        <FormItem prop="lastStatus" label="作业最后执行状态">
                            <Input readonly v-model="formBean.lastStatus"/>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                <Col span="8">
                    <FormItem prop="pendingTime" label="进入pending状态的时间">
                        <Input readonly v-model="formBean.pendingTime"/>
                    </FormItem>
                </Col>
                <Col span="8">
                    <FormItem prop="dispatcherTime" label="进入dispatcherTime状态的时间">
                        <Input readonly v-model="formBean.dispatcherTime"/>
                    </FormItem>
                </Col>
                </Row>
                <Row>
                <Col span="8">
                    <FormItem prop="startTime" label="开始执行时间">
                        <Input readonly v-model="formBean.startTime"/>
                    </FormItem>
                </Col>
                <Col span="8">
                    <FormItem prop="endTime" label="结束时间">
                        <Input readonly v-model="formBean.endTime"/>
                    </FormItem>
                </Col>
                </Row>
                <Row>
                <Col span="8">
                    <FormItem prop="multiBatch" label="批次号">
                        <Input readonly v-model="formBean.multiBatch"/>
                    </FormItem>
                </Col>
                <Col span="8">
                    <FormItem prop="numTimes" label="执行次数">
                        <Input readonly v-model="formBean.numTimes"/>
                    </FormItem>
                </Col>
                </Row>
                <Row>
                <Col span="8">
                    <FormItem prop="priority" label="作业执行优先级 ">
                        <Input readonly v-model="formBean.priority"/>
                    </FormItem>
                </Col>
                <Col span="8">
                    <FormItem prop="timeWindow" label="窗口执行时间">
                        <Input readonly v-model="formBean.timeWindow"/>
                    </FormItem>
                </Col>
                </Row>
                <Row>
                <Col span="8">
                    <FormItem prop="checkFrequency" label="是否检测时间">
                        <Input readonly v-model="formBean.checkFrequency"/>
                    </FormItem>
                </Col>
                <Col span="8">
                    <FormItem prop="checkTimeTrigger" label="检测是否采用时间触发">
                        <Input readonly v-model="formBean.checkTimeTrigger"/>
                    </FormItem>
                </Col>
                </Row>
                <Row>
                <Col span="8">
                    <FormItem prop="isEnable" label="是否启用">
                        <Input readonly v-model="formBean.isEnable"/>
                    </FormItem>
                </Col>
                <Col span="8">
                    <FormItem prop="callAgainMaxNum" label="最大重调次数">
                        <Input readonly v-model="formBean.callAgainMaxNum"/>
                    </FormItem>
                </Col>
                </Row>
                <Row>
                <Col span="8">
                    <FormItem prop="callAgainNum" label="当前重调次数">
                        <Input readonly v-model="formBean.callAgainNum"/>
                    </FormItem>
                </Col>
                <Col span="8">
                    <FormItem prop="virtualEnable" label="虚作业">
                        <Input readonly v-model="formBean.virtualEnable"/>
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <!-- <Col span="8">
                    <FormItem prop="lastScriptName" label="最后执行脚本">
                        <Input readonly type="textarea" :autosize="{minRows: 1,maxRows: 5}" v-model="formBean.lastScriptName"/>
                    </FormItem>
                </Col> -->
                <Col span="8">
                    <FormItem prop="dbControl" label="逻辑集群">
                        <Select  v-model="formBean.dbControl" :disabled="true">
                            <Option  v-for="item in locationList" :value="item.value" :key="item.value" >{{ item.label }}</Option>
                        </Select>
                    </FormItem>
                </Col>
            </Row>
        </Form>
    </div>
</template>

<script>
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
      formBean: {},
      locationList: [],
      formRule: {

      }
    }
  },
  methods: {
    /**
     * 初始化
     **/
    init () {
      if (this.transData.id) { // 如果传过来的存在ID则说明是编辑功能跳转过来
        let params = {}
        params.platform = this.transData.jobData.platform
        params.systems = this.transData.jobData.systems
        params.job = this.transData.jobData.job
        let loadConfig = {
          method: 'GET',
          url: RESOURCE_PATH + '/selectJobDetail', // + this.transData.id
          params: params
        }
        this.$ajax(loadConfig)
          .then(resp => {
            if (resp.status && resp.status === 200) {
              let data = resp.data.rows
              data.isEnable == 0 ? data.isEnable = '禁用' : data.isEnable = '启用'
              data.virtualEnable == 0 ? data.virtualEnable = '否' : data.virtualEnable = '是'
              data.checkFrequency == 0 ? data.checkFrequency = '否' : data.checkFrequency = '是'
              data.checkTimeTrigger == 0 ? data.checkTimeTrigger = '否' : data.checkTimeTrigger = '是'
              data.lastStatus == 'Running' ? data.endTime = '' : data.endTime = data.endTime
              this.formBean = data
            }
          })
      } else {
        this.bindData()
      }
    //   this.querylocationList()
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
    /**
         * 保存表单
         **/
    save () {
      this.$refs.udsjobForm.validate(valid => {
        if (!valid) return
        let params = {}
        Object.assign(params, this.formBean)
        let httpConfig = {
          url: RESOURCE_PATH,
          data: params
        }
        if (this.transData.id) {
          httpConfig.method = 'PUT'
          httpConfig.url = RESOURCE_PATH + '/' + this.transData.id
        } else {
          httpConfig.method = 'POST'
        }
        this.$ajax(httpConfig)
          .then(resp => {
            this.cancel()
          })
      })
    },
    /**
       * 返回到数据视图
      **/
    cancel () {
      let prevTab = ''
      if (this.transData.prevTab) {
        prevTab = this.transData.prevTab
      } else if (this.transData.tabObj) {
        prevTab = this.transData.tabObj.prevTab
      }
      let queryCache = { formBean: this.transData.formBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize }
      let backParam = { id: this.transData.previd, statusName: 'table', prevTab: prevTab, type: 'back', curTab: 'jobdetail' }
      if (this.transData.jobStatus) {
        queryCache.jobStatus = this.transData.jobStatus
      }
      if (this.transData.tablePage) {
        queryCache.tablePage = this.transData.tablePage
          }
      if (this.transData.jobData) {
        this.$emit('switch', Object.assign({}, queryCache, backParam, { prequeryCache: this.transData }, { jobData: this.transData.jobData }))
      } else {
        this.$emit('switch', Object.assign({}, queryCache, backParam))
      }
    },
    querylocationList () {
      let loadConfig = {
        method: 'GET',
        url: '/dictionary',
        params: { dicCode: 'location' }
      }
      this.$ajax(loadConfig)
        .then(resp => {
          this.locationList = []
          resp.data.rows.forEach(data => {
            let tmp = {}
            tmp.value = parseInt(data.dicValue)
            tmp.label = data.dicName
            this.locationList.push(tmp)
          })
        })
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

<template>
    <div>
        <div style="margin-bottom:5px;">
            <Row>
                <Col span="20">
                    <div
                        style="font-size:14px;font-weight: bold; color: #464c5b;margin-left:5px;"
                    >
                        修改属性 &nbsp;&nbsp;平台名:{{
                            this.formBean.platform
                        }}
                        &nbsp;&nbsp;应用名:{{
                            this.formBean.systems
                        }}
                        &nbsp;&nbsp;作业名:{{ this.formBean.job }}
                    </div>
                </Col>
                <Col span="4">
                    <Button
                        size="small"
                        type="primary"
                        icon="ios-arrow-back"
                        @click="cancel"
                        style=""
                        >返回</Button
                    >
                </Col>
            </Row>
        </div>
        <Form
            ref="attrForm"
            :model="formBean"
            :label-width="180"
            :rules="formRule"
        >
            <Row>
                <Col span="8">
                    <FormItem prop="platform" label="平台名">
                        <Input readonly v-model="formBean.platform" />
                    </FormItem>
                </Col>
                <Col span="8">
                    <FormItem prop="systems" label="应用名">
                        <Input readonly v-model="formBean.systems" />
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="8">
                    <FormItem prop="job" label="作业名">
                        <Input
                            readonly
                            type="textarea"
                            :autosize="{ minRows: 1, maxRows: 5 }"
                            v-model="formBean.job"
                        />
                    </FormItem>
                </Col>
                <Col span="8">
                    <FormItem prop="priority" label="作业优先级" v-show="udsjobConWR.priority">
                        <Input
                            :readonly="false"
                            placeholder="0-1000"
                            v-model="formBean.priority"
                        />
                         <!-- <Select :transfer="true" filterable v-model="formBean.priority" clearable>
                            <Option v-for="item in priorityList" :value="item.value" :key="item.value" >{{ item.label }}</Option>
                        </Select> -->
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="8">
                    <FormItem prop="callAgainMaxNum" label="最大重调次数" v-show="udsjobConWR.callAgainMaxNum">
                        <Input
                            placeholder="请输入数字0-10"
                            v-model="formBean.callAgainMaxNum"
                        />
                    </FormItem>
                </Col>
                <Col span="8">
                    <FormItem prop="jobDate" label="TXDate" >
                        <DatePicker
                            :disabled="!udsjobConWR.jobDate"
                            :transfer="true"
                            type="date"
                            parse="yyyy-MM-dd"
                            format="yyyy-MM-dd"
                            v-model="formBean.jobDate"
                        ></DatePicker>
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="8">
                    <FormItem prop="lastStatus" label="作业状态" v-show="udsjobConWR.lastStatus">
                        <RadioGroup v-model="formBean.lastStatus" @on-change="switchLastStatus">
                            <Radio label="Pending"><span>Pending</span></Radio>
                            <Radio label="Ready"><span>Ready</span></Radio>
                            <Radio label="Done"><span>Done</span></Radio>
                        </RadioGroup>
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="8">
                    <FormItem prop="checkFrequency" label="是否检测时间" v-show="udsjobConWR.checkFrequency">
                        <Checkbox v-model="formBean.checkFrequency"
                            >启用</Checkbox
                        >
                        <!-- <Input  placeholder='1或0' v-model="formBean.checkFrequency"/> -->
                    </FormItem>
                </Col>
                <Col span="8">
                    <FormItem
                        prop="checkTimeTrigger"
                        label="检测是否采用时间触发"
                        v-show="udsjobConWR.checkTimeTrigger"
                    >
                        <Checkbox v-model="formBean.checkTimeTrigger"
                            >启用</Checkbox
                        >
                        <!-- <Input  placeholder='1或0' v-model="formBean.checkTimeTrigger"/> -->
                    </FormItem>
                </Col>
            </Row>
            <Row> </Row>

            <Row>
                <Col span="12">
                    <FormItem prop="timeWindow" label="作业时间窗口" v-show="udsjobConWR.timeWindow">
                        开始:
                        <TimePicker
                            :transfer="true"
                            v-model="formBean.timeWindowS"
                            format="HH:mm"
                            placement="bottom-end"
                            placeholder="选择时间范围"
                        ></TimePicker>
                        结束:
                        <TimePicker
                            :transfer="true"
                            v-model="formBean.timeWindowE"
                            format="HH:mm"
                            placement="bottom-end"
                            placeholder="选择时间范围"
                        ></TimePicker>
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="8">
                    <FormItem prop="dbControl" label="逻辑集群" v-show="udsjobConWR.dbControl">
                        <Select
                            :transfer="true"
                            filterable
                            v-model="formBean.dbControl"
                            clearable
                        >
                            <Option
                                v-for="item in locationList"
                                :value="item.value"
                                :key="item.value"
                                >{{ item.label }}</Option
                            >
                        </Select>
                    </FormItem>
                </Col>
            </Row>
            <Row>
          <Col span="8">
            <FormItem prop="checkStreamSelf" label="是否启用stream文件检测" v-show="udsjobConWR.checkStreamSelf">
              <!--<Input v-model="formBean.resStatus"/>-->
              <Select filterable  v-model="formBean.checkStreamSelf" clearable>
                  <Option v-for="item in cfsData" :value="item.value" :key="item.value">{{ item.label }}</Option>
                </Select>
            </FormItem>
          </Col>
        <Col span="8">
          <FormItem prop="ignoreError" label="是否忽视错误" v-show="udsjobConWR.ignoreError">
              <!--<Input v-model="formBean.resStatus"/>-->
              <Select filterable  v-model="formBean.ignoreError" clearable>
                  <Option v-for="item in igData" :value="item.value" :key="item.value">{{ item.label }}</Option>
                </Select>
            </FormItem>
        </Col>
        </Row>
        <Row>
          <Col span="8">
            <FormItem prop="batch" label="批次号" v-show="udsjobConWR.multiBatch">
              <InputNumber :max="10000" :min="0" v-model="formBean.multiBatch"></InputNumber>
            </FormItem>
          </Col>
      </Row>
        </Form>

        <div>
            <Button
                type="primary"
                :loading="loading"
                icon="md-add"
                @click="save"
                >保存</Button
            >
            <Button type="primary" icon="ios-trash-outline" @click="cancel"
                >取消</Button
            >
        </div>
    </div>
</template>

<script>
import store from '@/store/index'
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
    const timeWindowValid = (rule, value, callback, source, options) => {
      if (!this.formBean.platform || !this.formBean.systems) {
        callback()
        return
      }
      let timeWindowS = this.formBean.timeWindowS
      let timeWindowE = this.formBean.timeWindowE
      if (!timeWindowS) {
        callback(new Error('开始不能为空'))
      }
      if (!timeWindowE) {
        callback(new Error('结束不能为空'))
      }
      // console.log(utils.dateDiff('20190901 '+timeWindowS,'20190901 '+timeWindowE ,'datetime'));
      if (
        utils.dateDiff(
          '20190901 ' + timeWindowS,
          '20190901 ' + timeWindowE,
          'datetime'
        ) <= 0
      ) {
        callback(new Error('请确定开始时间小于结束时间'))
      } else {
        callback()
      }
    }

    const callAgainMaxNumValid = (
      rule,
      value,
      callback,
      source,
      options
    ) => {
      if (!this.formBean.platform || !this.formBean.systems) {
        callback()
        return
      }
      // alert(1)
      // console.log(new Number(this.formBean.callAgainMaxNum) > -1 && new Number(this.formBean.callAgainMaxNum) <= 10);
      if (
        new Number(this.formBean.callAgainMaxNum) > -1 &&
                new Number(this.formBean.callAgainMaxNum) <= 10
      ) {
        callback()
      } else {
        callback(new Error('最大重调次数范围0-10'))
      }
    }
    return {
      // 作业属性读写权限控制
      udsjobConWR: {
        priority: true,
        callAgainMaxNum: true,
        jobDate: true,
        timeWindow: true,
        lastStatus: true,
        checkFrequency: true,
        checkTimeTrigger: true,
        dbControl: true,
        checkFileStream: true,
        ignoreError: true,
        multiBatch: true
      },
      original: '',
      formBean: {},
      loading: false,
      locationList: [],
      priorityList: [],
      formRule: {
        priority: [
          {
            required: true,
            message: '作业优先级不能为空，只能为数字'
          }
        ],
        callAgainMaxNum: [
          {
            required: true,
            message: '最大重调次数不能为空，只能为数字'
          },
          { validator: callAgainMaxNumValid, trigger: 'blur' }
        ],
        timeWindow: [
          { required: true, message: '不能为空' },
          { validator: timeWindowValid, trigger: 'blur' }
        ],
        checkFrequency: [{ required: true, message: '不能为空' }],
        checkTimeTrigger: [{ required: true, message: '不能为空' }],
        jobDate: [{ required: true, message: '不能为空' }],
        jobType: [{ required: true, message: '不能为空' }]
      },
      cfsData: [{ value: 1, label: '是' }, { value: 0, label: '否' }],
      igData: [{ value: 1, label: '是' }, { value: 0, label: '否' }]
    }
  },
  methods: {
    /**
         * 初始化
         **/
    init () {
      if (this.transData.id) {
        // 如果传过来的存在ID则说明是编辑功能跳转过来
        let params = {}
        params.platform = this.transData.jobData.platform
        params.systems = this.transData.jobData.systems
        params.job = this.transData.jobData.job
        let loadConfig = {
          method: 'GET',
          url: RESOURCE_PATH + '/selectJobDetail',
          params: params
        }
        this.$ajax(loadConfig).then(resp => {
          let data = resp.data.rows
          if (data.timeWindow) {
            data.timeWindowS = data.timeWindow.split('-')[0]
            data.timeWindowE = data.timeWindow.split('-')[1]
          }
          if (data.ignoreError) {
            data.ignoreError = 1
          } else {
            data.ignoreError = 0
          }
          // if (data.checkFrequency === 1) {
          //   data.checkFrequency = true
          // } else {
          //   data.checkFrequency = false
          // }
          // if (data.checkTimeTrigger === 1) {
          //   data.checkTimeTrigger = true
          // } else {
          //   data.checkTimeTrigger = false
          // }

          this.formBean = data
          this.original = data.priority
        })
      } else {
        this.bindData()
      }
      // this.querylocationList()
      // this.querypriorityList()
    },
    /**
         * 如果传过来的有初始数据则进行数据绑定
         **/
    bindData () {
      try {
        this.formBean = Object.assign({}, this.transData.initFormBean)
      } catch (error) {
        console.error(error)
      }
    },
    /**
         * 保存表单
         **/
    save () {
      this.$refs.attrForm.validate(valid => {
        if (!valid) return
        let params = {}
        Object.assign(params, this.formBean)
        params.jobDate = utils.fmtDate(this.formBean.jobDate, 'yyyy-MM-dd')
        params.jobType = this.formBean.jobType
        // if (this.formBean.checkFrequency === true) {
        //   params.checkFrequency = 1
        // } else {
        //   params.checkFrequency = 0
        // }
        // if (this.formBean.checkTimeTrigger === true) {
        //   params.checkTimeTrigger = 1
        // } else {
        //   params.checkTimeTrigger = 0
        // }
        let param = {}
        param.platform = this.transData.jobData.platform
        param.systems = this.transData.jobData.systems
        param.job = this.transData.jobData.job
        let loadConfig = {
          method: 'GET',
          url: RESOURCE_PATH + '/selectJobDetail',
          params: param
        }
        this.$ajax(loadConfig).then(loadresp => {
          if (!(loadresp.data.rows.lastStatus === 'Done' ||
              loadresp.data.rows.lastStatus === 'Failed' ||
              loadresp.data.rows.lastStatus === 'Ready')
          ) {
            this.$Message.error({
              content: '当前作业状态不为Done或Failed或Ready,不能修改作业属性',
              duration: 15,
              closable: true
            })
            this.formBean.lastStatus = loadresp.data.rows.lastStatus
            return
          }
          params.lastStatus = this.formBean.lastStatus
          params.timeWindow = this.formBean.timeWindowS + '-' + this.formBean.timeWindowE
          params.priority = this.formBean.priority
          let httpConfig = {
            method: 'POST',
            url: RESOURCE_PATH + '/updateJobDetail',
            data: params
          }
          // if (this.transData.id) {
          // 置pending
          //   if (params.lastStatus === 'Pending') {
          //     // if (!this.isOwnPlatform(params)) {
          //     //   return
          //     // }
          //     httpConfig.method = 'POST'
          //     httpConfig.url =
          //                   RESOURCE_PATH +
          //                   '/pending/' +
          //                   this.transData.id
          //   } else {
          //     if (this.original !== params.priority) {
          //       if (!this.isOwnPlatform(params)) {
          //         return
          //       }
          //       httpConfig.method = 'POST'
          //       httpConfig.url =
          //                     RESOURCE_PATH +
          //                     '/priority/' +
          //                     this.transData.id
          //     } else {
          //       httpConfig.method = 'PUT'
          //       httpConfig.url =
          //                   RESOURCE_PATH + '/' + this.transData.id
          //     }
          //   }
          // } else {
          //   httpConfig.method = 'POST'
          // }
          // const nparams = {}
          // for (let key in this.udsjobConWR) {
          //   if (this.udsjobConWR[key] === true) {
          //     nparams[key] = params[key]
          //   }
          // }
          // nparams.authps = this.formBean.platform + this.formBean.systems
          // httpConfig.data = nparams
          this.loading = true
          this.$ajax(httpConfig).then(resp => {
            this.loading = false
            if (resp.status && resp.status === 200) {
              this.cancel()
            }
          })
        })
      })
    },
    /**
         * 平台管理员操作权限特殊处理增加判断
         * 置Pending、修改作业优先级，仅当角色为平台管理员、平台是该用户拥有的才可以操作
         *
         **/
    isOwnPlatform (job) {
      let authPlatform = store.getters.getPlatforms
      if (authPlatform.indexOf('ROLE_ADMIN') === -1) {
        if (authPlatform.length > 0) {
          if (authPlatform.includes(job.platform)) {
            return true
          } else {
            // 修改的平台不是所属平台
            this.$Message.error({
              content: '操作权限不足，请联系管理员确认！',
              duration: 15,
              closable: true
            })
            return false
          }
        }
      }
      return true
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
      let queryCache = {
        formBean: this.transData.formBean,
        currentPage: this.transData.currentPage,
        pageSize: this.transData.pageSize
      }
      let backParam = {
        statusName: 'table',
        prevTab: prevTab,
        type: 'back'
      }
      if (this.transData.jobStatus) {
        queryCache.jobStatus = this.transData.jobStatus
      }
      this.$emit('switch', Object.assign({}, queryCache, backParam))
      // this.$emit('switch', {statusName: "table",prevTab: prevTab, type:"back"});
      // this.$emit('switch',{statusName: "table"});
    },
    switchLastStatus (val) {
      if (val === 'Done') {
        this.$Message.warning({
          content: '置Done后作业不会自动触发下游',
          duration: 5,
          closable: true
        })
      }
    },
    querypriorityList () {
       let loadConfig = {
        method: 'GET',
        url: '/dictionary',
        params: { dicCode: 'priority' }
      }
      this.$ajax(loadConfig).then(resp => {
        this.priorityList = []
        resp.data.rows.forEach(data => {
          let tmp = {}
          tmp.value = parseInt(data.dicValue)
          tmp.label = data.dicName
          this.priorityList.push(tmp)
        })
      }) 
    },
    querylocationList () {
      let loadConfig = {
        method: 'GET',
        url: '/dictionary',
        params: { dicCode: 'location' }
      }
      this.$ajax(loadConfig).then(resp => {
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

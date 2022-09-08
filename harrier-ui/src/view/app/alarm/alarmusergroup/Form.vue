<template>
  <div>
    <Form ref="alarmUserGroupForm" :model="formBean" :label-width="100" :rules="formRule">
      <Row>
        <Col span="6">
          <FormItem prop="platform" label="平台名">
            <Input v-model="formBean.platform"/>
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="6">
          <FormItem prop="systems" label="应用名">
            <Input v-model="formBean.systems"/>
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="6">
          <FormItem prop="groupName" label="组名">
            <Input v-model="formBean.groupName"/>
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="6">
          <FormItem  style='width:75%' prop="sendType" label="发送类型">
            <Select clearable filterable   v-model="formBean.sendType" @on-change="sendTypeChange">
              <Option v-for="item in sendTypeList" :value="item[0]" :key="item[0]">{{ item[1] }}</Option>
            </Select>
          </FormItem>
        </Col>
      </Row>
      <Form v-if="showSendType" ref="sendTypeForm" :model="sendTypeBean" :label-width="100" :rules="sendTypeformRule">
        <Row  v-for="(infor, i) in showSendTypeData" :key="`infor-${i}`">
          <Col span="6">
            <FormItem   :prop="infor[1]" :label="infor[0]">
              <Input v-model="sendTypeBean[infor[1]]" />
            </FormItem>
          </Col>
          <Col v-if="i === showSendTypeData.length-1" span="6" :offset="1" >
            <Button type="primary" icon="md-add" @click="addParams">添加参数</Button>
          </Col>    
        </Row>
        <Row  v-for="(infor, i) in customParams" :key="`param-${i}`">
          <Col span="4">
            <FormItem    :label="'参数名'+(i+1)">
              <Input v-model="infor.key" />
            </FormItem>
          </Col>
          <Col span="4">
            <FormItem    :label="'参数值'+(i+1)">
              <Input v-model="infor.value" />
            </FormItem>
          </Col>
          <Col span="4" :offset="1" >
            <Button type="primary" @click="delParams(i)">删除</Button>
          </Col>  
        </Row>
      </Form>
    </Form>

    <div>
      <Button type="primary" :loading="loading" icon="md-add" @click="save">保存</Button>
      <Button type="primary" icon="ios-trash-outline" @click="cancel">取消</Button>
    </div>
  </div>
</template>

<script>
const RESOURCE_PATH = '/alarm/group'

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
    const checkEmail = (rule, value, callback) => {
      if (value) {
        let reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})(,([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4}))*$/
        if (!reg.test(value)) {
          callback(new Error('电子邮件格式不正确'))
        } else {
          callback()
        }

      } else {
        callback(new Error('请输入数据'))
      }
    }
    return {
      formBean: {},
      loading: false,
      sendTypeList: [],
      sendTypeDataBean: {},
      sendTypeBean: {},
      customParams: [],
      showSendTypeData: [],
      showSendType: false,
      platformData: [],
      alarmTypeData: [],
      codeTypeData: [],
      serverityTypeData: [],
      jobTypeData: [],
      noticeObjData: [],
      model1: [],
      isEdit: false,
      noticeTypeData: [{ label: '邮件', value: 'email' }],

      sendTypeformRule: {
        receivers: [{
          required: true,
          validator: checkEmail,
          trigger: 'blur'
        }],
        receiverCcs: [{
          required: true,
          validator: checkEmail,
          trigger: 'blur'
        }],
        sender: [{
          required: true,
          validator: checkEmail,
          trigger: 'blur'
        }],
        user: [{
          required: true,
          message: '请输入数据！'
        }],
        passwd: [{
          required: true,
          message: '请输入数据！'
        }],
        serverHost: [{
          required: true,
          message: '请输入数据！'
        }],
        serverPort: [{
          required: true,
          message: '请输入数据！'
        }],
        url: [{
          required: true,
          message: '请输入数据！'
        }],
        headerParams: [{
          required: true,
          message: '请输入数据！'
        }],
        bodyParams: [{
          required: true,
          message: '请输入数据！'
        }],
        requestType: [{
          required: true,
          message: '请输入数据！'
        }],
        contentField: [{
          required: true,
          message: '请输入数据！'
        }]
      },
      formRule: {
        platform: [{
          required: true,
          message: '请输入数据！'
        }],
        systems: [{
          required: true,
          message: '请输入数据！'
        }],
        groupName: [{
          required: true,
          message: '请输入数据！'
        }],
        sendType: [{
          required: true,
          message: '请选择'
        }],
        sendParams: [{
          required: true,
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
      if (this.transData.row) { // 如果传过来的存在ID则说明是编辑功能跳转过来
        this.formBean = Object.assign({}, this.transData.row)
        console.log(this.formBean)
        this.getsendTypeList(this.formBean.id)
      } else {
        this.bindData()
        this.getsendTypeList()
      }
      // this.queryCodeType()
      this.queryPlatform()
      // this.queryNoticeObj()
      // this.queryJobType()
      // this.queryServerityType()
      // this.queryAlarmType()
    },
    addParams () {
      let param = { key: '', value: '' }
      this.customParams.push(param)

    },
    delParams (i) {
      this.customParams.splice(i, 1)
    },
    getsendTypeList (id) {
      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/sendType'
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
              this.sendTypeList.push(sendType)
              this.sendTypeDataBean[sendType[0]] = valueData
            }
            if (id) {
              // 编辑页面进来
              let oldSendType = JSON.parse(this.formBean.sendParams)
              this.showSendTypeData = this.sendTypeDataBean[this.formBean.sendType]
              console.log(this.showSendTypeData)
              // 循环取出自定义的key和系统中的key
              let oldSendTypeKeys = Object.keys(oldSendType)
              for (let i = 0; i < oldSendTypeKeys.length; i++) {
                let key = oldSendTypeKeys[i]
                let haveData = false
                for (let j = 0; j < this.showSendTypeData.length; j++) {
                  if (key === this.showSendTypeData[j][1]) {
                    haveData = true
                    break
                  }
                }
                if (haveData) {
                  this.sendTypeBean[key] = oldSendType[key]
                } else {
                  let param = { key: key, value: oldSendType[key] }
                  this.customParams.push(param)
                }
              }
              this.showSendType = true
            }
          }
        })
    },
    sendTypeChange (sendType) {
      this.showSendType = false
      this.sendTypeBean = {}
      this.showSendTypeData = this.sendTypeDataBean[sendType]
      this.showSendType = true
      this.customParams = []
      this.$forceUpdate()
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
      this.$refs.alarmUserGroupForm.validate(valid => {
        if (!valid) return
        this.$refs.sendTypeForm.validate(typeValid => {
          if (!typeValid) return
          let str = this.model1
          // if(''==this.model1){
          // this.$Message.error('未填写通知对象');
          // return;
          // }
          this.formBean.noticeObj = str.join()
          let params = {}
          params.authps = this.formBean.platform + '*'
          Object.assign(params, this.formBean)
          for (let i = 0; i < this.customParams.length; i++) {
            let customParam = this.customParams[i]
            this.sendTypeBean[customParam.key + ' '] = customParam.value
          }
          params.sendParams = JSON.stringify(this.sendTypeBean)
          let httpConfig = {
            url: RESOURCE_PATH,
            data: params
          }
          if (this.transData.row) {
            httpConfig.method = 'POST'
            httpConfig.url = RESOURCE_PATH + '/update'
          } else {
            httpConfig.url = RESOURCE_PATH + '/add'
            httpConfig.method = 'PUT'
          }
          this.loading = true
          this.$ajax(httpConfig)
            .then(resp => {
              this.cancel()
            })
        })   
      })
    },
    filterMethod (value, option) {
      return option.toUpperCase().indexOf(value.toUpperCase()) !== -1
    },
    queryPlatform () {
      let loadConfig = {
        method: 'GET',
        url: '/udsSystem/listQuery'
        // params: { systems: '*' }
      }
      this.$ajax(loadConfig)
        .then(resp => {
          // resp.data.forEach(data => {
          //   let tmp = {}
          //   tmp.value = data
          //   tmp.label = data
          //   this.platformData.push(tmp)
          // })
          console.log(resp)
          // this.platformData = resp.data
        })
    },
    queryAlarmType () {
      this.alarmTypeData = []
      let params = { dictcode: 'm_alarm_type' }
      const loadConfig = {
        method: 'GET',
        url: 'file/loadList',
        params: params
      }
      this.$ajax(loadConfig)
        .then(resp => {
          if (resp.data) {
            resp.data.forEach(data1 => {
              let temp = {}
              temp.key = data1.dicName
              temp.value = data1.dicValue
              temp.label = data1.dicName
              this.alarmTypeData.push(temp)
            })
          }
        })
    },
    queryServerityType () {
      this.serverityTypeData = []
      let params = { dictcode: 'm_alarm_level' }
      const loadConfig = {
        method: 'GET',
        url: 'file/loadList',
        params: params
      }
      this.$ajax(loadConfig)
        .then(resp => {
          if (resp.data) {
            resp.data.forEach(data1 => {
              let temp = {}
              temp.key = data1.dicName
              temp.value = data1.dicValue
              temp.label = data1.dicName
              this.serverityTypeData.push(temp)
            })
          }
        })
    },
    queryJobType () {
      this.jobTypeData = []
      let params = { dictcode: 'm_alarm_bus_type' }
      const loadConfig = {
        method: 'GET',
        url: 'file/loadList',
        params: params
      }
      this.$ajax(loadConfig)
        .then(resp => {
          if (resp.data) {
            resp.data.forEach(data1 => {
              let temp = {}
              temp.key = data1.dicName
              temp.value = data1.dicValue
              temp.label = data1.dicName
              this.jobTypeData.push(temp)
            })
          }
        })
    },
    queryNoticeObj () {
      this.noticeObjData = []
      let params = { dictcode: 'm_alarm_not' }
      const loadConfig = {
        method: 'GET',
        url: 'file/loadList',
        params: params
      }
      this.$ajax(loadConfig)
        .then(resp => {
          if (resp.data) {
            resp.data.forEach(data1 => {
              let temp = {}
              temp.key = data1.dicName
              temp.value = data1.dicValue
              temp.label = data1.dicName
              this.noticeObjData.push(temp)
            })
          }
        })
    },
    queryCodeType () {
      this.codeTypeData = []
      let params = { dictcode: 'm_alarm_code' }
      const loadConfig = {
        method: 'GET',
        url: 'file/loadList',
        params: params
      }
      this.$ajax(loadConfig)
        .then(resp => {
          if (resp.data) {
            resp.data.forEach(data1 => {
              let temp = {}
              temp.key = data1.dicName
              temp.value = data1.dicValue
              temp.label = data1.dicName
              this.codeTypeData.push(temp)
            })
          }
        })
    },
    /**
     * 返回到数据视图
     **/
    cancel () {
      let prevTab = ''
      if (this.transData.tabObj) {
        prevTab = this.transData.tabObj.prevTab
      } else if (this.transData.prevTab) {
        prevTab = this.transData.prevTab
      }

      let backParam = { id: this.transData.id, statusName: 'emailTable', prevTab: prevTab, curTab: 'emailForm' }

      let queryCache = { formBean: this.transData.formBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize }
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

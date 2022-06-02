<template>
  <div>
    <Form ref="alarmUserGroupForm" :model="formBean" :label-width="100" :rules="formRule">
      <!--<Row>
        <Col span="6">
          <FormItem prop="platform" label="平台名">
            <Select :disabled="isEdit" filterable v-model="formBean.platform" clearable>
							<Option v-for="item in platformData" :value="item.value" :key="item.value">{{ item.label }}</Option>
						</Select>
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="6">
          <FormItem prop="systems" label="应用名">
            <Select v-model="formBean.systems" filterable clearable>
              <Option v-for="item in codeTypeData" :value="item.value" :key="item.key">{{ item.label }}</Option>
            </Select>
          </FormItem>
        </Col>
      </Row>-->
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
      <!--<Row>
        <Col span="6">
          <FormItem prop="sendType" label="发送类型">
            <Select v-model="formBean.sendType" filterable clearable>
              <Option v-for="item in alarmTypeData" :value="item.value" :key="item.key">{{ item.label }}</Option>
            </Select>
          </FormItem>
        </Col>
      </Row>-->
      <Row>
        <Col span="6">
          <FormItem prop="sendType" label="发送类型">
            <Input v-model="formBean.sendType"/>
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="6">
          <FormItem prop="sendParams" label="发送参数">
            <Input v-model="formBean.sendParams"/>
          </FormItem>
        </Col>
      </Row>


  

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
    return {
      formBean: {},
      loading: false,
      platformData: [],
      alarmTypeData: [],
      codeTypeData: [],
      serverityTypeData: [],
      jobTypeData: [],
      noticeObjData: [],
      model1: [],
      isEdit: false,
      noticeTypeData: [{ label: '邮件', value: 'email' }],

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
          message: '请输入数据！'
        }],
        sendParams: [{
          required: true,
          message: '请输入数据！'
        }],
    
      }
    }
  },
  methods: {
    /**
     * 初始化
     **/
    init () {
      console.log('11',this.transData.row)
      if (this.transData.row) { // 如果传过来的存在ID则说明是编辑功能跳转过来
        this.formBean = Object.assign({}, this.transData.row)
        // this.isEdit = true
        // let loadConfig = {
        //   method: 'GET',
        //   url: RESOURCE_PATH + '/' + this.transData.id
        // }
        // this.$ajax(loadConfig)
        //   .then(resp => {
        //     this.formBean = resp.data
        //     this.model1 = this.formBean.noticeObj.split(',')
        //   })
      } else {
        this.bindData()
      }
      // this.queryCodeType()
      this.queryPlatform()
      // this.queryNoticeObj()
      // this.queryJobType()
      // this.queryServerityType()
      // this.queryAlarmType()
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
        let str = this.model1
        // if(''==this.model1){
        // this.$Message.error('未填写通知对象');
        // return;
        // }
        this.formBean.noticeObj = str.join()
        let params = {}
        params.authps = this.formBean.platform + '*'
        Object.assign(params, this.formBean)
        let httpConfig = {
          url: RESOURCE_PATH,
          data: params
        }
          console.log('httpConfig',httpConfig)
        if (this.transData.row) {
          httpConfig.method = 'POST'
          httpConfig.url = RESOURCE_PATH + '/update' 
        } else {
          httpConfig.url = RESOURCE_PATH + '/add' 
          httpConfig.method = 'PUT'
        }
        console.log('httpConfig',httpConfig)
        this.loading = true
        this.$ajax(httpConfig)
          .then(resp => {
            this.cancel()
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

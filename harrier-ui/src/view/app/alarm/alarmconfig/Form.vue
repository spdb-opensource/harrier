<template>
  <div>
    <Form ref="alarmConfigForm" :model="formBean" :label-width="150" :rules="formRule">
      <Row>
        <Col span="8">
          <FormItem prop="platform" label="平台名">
            <Input v-model="formBean.platform"/>
            <!-- <Select :disabled="isEdit" filterable v-model="formBean.platform" clearable>
							<Option v-for="item in platformData" :value="item.value" :key="item.value">{{ item.label }}</Option>
						</Select> -->
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="8">
          <FormItem prop="systems" label="应用名">
            <Input v-model="formBean.systems"/>
            <!-- <Select v-model="formBean.systems" filterable clearable>
              <Option v-for="item in systemData" :value="item.value" :key="item.key">{{ item.label }}</Option>
            </Select> -->
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="8">
          <FormItem prop="code" label="告警码">
            <Input v-model="formBean.code"/>
            <!-- <Select v-model="formBean.code" filterable clearable>
              <Option v-for="item in codeData" :value="item.value" :key="item.key">{{ item.label }}</Option>
            </Select> -->
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="8">
          <FormItem prop="job" label="作业">
            <Input  v-model="formBean.job" clearable></Input>
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="8">
          <FormItem prop="defStatus" label="告警默认状态">
            <Input v-model="formBean.defStatus"/>
            <!--<Input v-model="formBean.noticeType"/>-->
            <!-- <Select filterable v-model="formBean.defStatus" clearable>
              <Option v-for="item in defStatusData" :value="item.value" :key="item.key">{{ item.label }}</Option>
            </Select> -->
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="8">
          <FormItem prop="noticeTimes" label="通知次数">
            <InputNumber style="width:100%" :max="999" :min="0" :step="1" v-model="formBean.noticeTimes"></InputNumber>
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="8">
          <FormItem prop="noticeCycle" label="通知间隔分">
            <InputNumber style="width:100%" :max="99999" :min="0" :step="1" v-model="formBean.noticeCycle"></InputNumber>
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="8">
          <FormItem prop="noticeGroupName" label="通知用户组">
            <Input v-model="formBean.noticeGroupName"/>
          </FormItem>
        </Col>
      </Row>
       <Row>
        <Col span="8">
          <FormItem prop="build" label="是否生成告警信息">
            <Select v-model="formBean.build" filterable clearable>
              <Option v-for="item in buildData" :value="item.value" :key="item.key">{{ item.label }}</Option>
            </Select>
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="8">
          <FormItem prop="remark" label="备注">
            <Input v-model="formBean.remark"/>
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="8">
          <FormItem prop="isEnable" label="是否启用" >
            <Checkbox v-model="formBean.isEnable">启用</Checkbox>
          </FormItem>
        </Col>
      </Row>
    </Form>

    <div style="margin-left:15%">
      <Button type="primary" :loading="loading" icon="md-add" @click="save">保存</Button>
      <Button type="primary" icon="ios-trash-outline" @click="cancel">取消</Button>
    </div>
  </div>
</template>

<script>
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
  data () {
    return {
      formBean: {},
      loading: false,
      platformData: [],
      codeData: [],
      systemData: [],
      serverityTypeData: [],
      jobTypeData: [],
      noticeObjData: [],
      model1: [],
      isEdit: false,
      defStatusData: [{ label: '邮件', value: 'email' }],
      buildData: [
        { label: '是', value: 'true' },
        { label: '否', value: 'false' }
      ],
      formRule: {
        platform: [{
          required: true,
          message: '请输入数据！'
        }],
        code: [{
          required: true,
          message: '请输入数据！'
        }],
        alarmType: [{
          required: true,
          message: '请输入数据！'
        }],
        serverity: [{
          required: true,
          message: '请输入数据！'
        }],
        noticeType: [{
          required: true,
          message: '请输入数据！'
        }],
        jobType: [{
          required: true,
          message: '请输入数据！'
        }],
        model1: [{
          required: true,
          message: '请输入数据！'
        }],
        noticeCon: [{
          required: true,
          message: '请输入数据！'
        }]
        // updateUser: [{
        // required: true,
        // message: '请输入数据！'
        // }],
      }
    }
  },
  methods: {
    /**
     * 初始化
     **/
    init () {
      console.log(this.transData)
      if (this.transData.row) { // 如果传过来的存在ID则说明是编辑功能跳转过来
        this.formBean = Object.assign({}, this.transData.row)
        if (this.formBean.build === false) {
          this.formBean.build = 'false'
        } else if (this.formBean.build === true) {
          this.formBean.build = 'true'
        }
        this.isEdit = true
      } else {
        this.bindData()
      }
      this.queryPlatform()
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
      this.$refs.alarmConfigForm.validate(valid => {
        if (!valid) return
        let params = {}
        Object.assign(params, this.formBean)
        params.build = this.formBean.build === 0
        let httpConfig = {
          url: RESOURCE_PATH,
          data: params
        }
        if (this.transData) {
          // httpConfig.method = 'PUT'
          httpConfig.method = 'POST'
          httpConfig.url = RESOURCE_PATH + '/update'
        } else {
          // httpConfig.method = 'POST'
          httpConfig.method = 'PUT'
          httpConfig.url = RESOURCE_PATH + '/add'
        }
        delete params.updateTime
        console.log(params)
        // return
        this.loading = true
        this.$ajax(httpConfig)
          .then(resp => {
            console.log(resp)
            this.cancel()
          })
      })
    },
    filterMethod (value, option) {
      return option.toUpperCase().indexOf(value.toUpperCase()) !== -1
    },
    queryPlatform () {
      // let loadConfig = {
      //   method: 'GET',
      //   url: '/udsSystem/listQuery'
      //   // params: { systems: '*' }
      // }
      // this.$ajax(loadConfig)
      //   .then(resp => {
      // resp.data.forEach(data => {
      //   let tmp = {}
      //   tmp.value = data
      //   tmp.label = data
      //   this.platformData.push(tmp)
      // })
      // console.log(resp)
      // this.platformData = resp.data
      // })
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

<template>
  <div>
    <Form ref="alarmConfigForm" :model="formBean" class="work-flow" :label-width="200" :rules="formRule">
      <Row type="flex" justify="center" align="middle">
        <Col span="20">
          <Row>
            <p>名称属性定义</p>
          </Row>
          <Row style="margin-top:10px">
            <Card>
              <Row>
                <Col span="10">
                  <FormItem prop="platform" label="平台名">
                    <Select :disabled="isEdit" filterable v-model="formBean.platform" clearable  @on-change="querySystem">
                      <Option v-for="item in platformData" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="10">
                  <FormItem prop="systems" label="应用名">
                    <Select ref="refsystem" :disabled="isEdit" v-model="formBean.systems" filterable clearable>
                      <Option v-for="item in systemsData" :value="item.value" :key="item.key">{{ item.label }}</Option>
                    </Select>
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="10">
                  <FormItem prop="job" label="作业名">
                    <Input :disabled="isEdit" v-model="formBean.job"/>
                  </FormItem>
                </Col>
                <Col span="10">
                  <FormItem prop="jobName" label="作业名中文">
                    <Input v-model="formBean.jobName"/>
                  </FormItem>
                </Col>
              </Row>
            </Card>
          </Row>
          </br>
          <Row>
            <p>任务执行频度设置</p>
          </Row>
          <Row style="margin-top:10px">
            <Card>
              <Row>
                <Col span="10">
                  <FormItem prop="jobType" label="作业类型">
                    <Select v-model="formBean.jobType" filterable clearable>
                      <Option v-for="item in jobTypeData" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="10">
                  <FormItem prop="jobFrequency" label="作业频度" >
                    <Input v-model="formBean.jobFrequency" :disabled="disJobFrequency"></Input>
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="10">
                  <FormItem prop="offsetDay" label="偏移天数" >
                    <InputNumber style="width: 100%" :step="1" :editable="false" v-model="formBean.offsetDay"></InputNumber>
                  </FormItem>
                </Col>
                <Col span="10">
                  <FormItem prop="timeWindow" label="窗口执行时间">
                    <TimePicker format="HH:mm" type="timerange" v-model="formBean.timeWindow"></TimePicker>
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="10">
                  <FormItem prop="multiBatch" label="批次号">
                    <Input v-model="formBean.multiBatch"/>
                  </FormItem>
                </Col>
              </Row>
            </Card>
          </Row>
          </br>
          <Row>
            <p>任务触发设置</p>
          </Row>
          <Row style="margin-top:10px">
            <Card>
              <Row>
                <Col span="10">
                  <FormItem prop="streamType" label="作业触发类型">
                    <Input :disabled="isEdit" v-model="formBean.streamType"/>
                  </FormItem>
                </Col>
                <Col span="10">
                  <FormItem prop="checkStreamSelf" label="是否启用stream file">
                    <Select v-model="formBean.checkStreamSelf" filterable clearable>
                      <Option :value="0" :key="0">不采用</Option>
                      <Option :value="1" :key="1">采用</Option>
                    </Select>
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="10">
                  <FormItem prop="checkTimeTrigger" label="检测是否采用时间触发">
                    <Select v-model="formBean.checkTimeTrigger" filterable clearable>
                      <Option :value="0" :key="0">不检测</Option>
                      <Option :value="1" :key="1">检测</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="10">
                  <FormItem prop="checkFrequency" label="是否检测时间">
                    <Select v-model="formBean.checkFrequency" filterable clearable>
                      <Option :value="0" :key="0">不检测</Option>
                      <Option :value="1" :key="1">检测</Option>
                    </Select>
                  </FormItem>
                </Col>
              </Row>
            </Card>
          </Row>
          </br>
          <Row>
            <p>异常重调设置</p>
          </Row>
          <Row style="margin-top:10px">
            <Card>
              <Row>
                <Col span="10">
                  <FormItem prop="callAgainMaxNum" label="自动重调最大次数">
                    <Select v-model="formBean.callAgainMaxNum" filterable clearable>
                      <Option v-for="item in callAgainMaxNumData" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="10">
                  <FormItem prop="callAgainWaitSec" label="自动重调等待时间(秒)">
                    <InputNumber style="width: 100%" :min="1" :max="2592000" :step="1" v-model="formBean.callAgainWaitSec"/>
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="10">
                  <FormItem prop="ignoreError" label="是否忽视错误现场" >
                    <Select v-model="formBean.ignoreError" filterable clearable>
                      <Option :value="0" :key="0">不忽视</Option>
                      <Option :value="1" :key="1">忽视</Option>
                    </Select>
                  </FormItem>
                </Col>
              </Row>
            </Card>
          </Row>
          </br>
          <Row>
            <p>任务状态属性</p>
          </Row>
          <Row style="margin-top:10px">
            <Card>
              <Row>
                <Col span="10">
                  <FormItem prop="lastStatus" label="作业运行状态">
                    <Select filterable v-model="formBean.lastStatus" disabled clearable>
                      <Option v-for="item in lastStatusData" :value="item.value" :key="item.key">{{ item.label }}</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="10">
                  <FormItem prop="taskStatus" label="任务状态">
                    <Select filterable v-model="formBean.taskStatus" disabled clearable>
                      <Option v-for="item in taskStatusData" :value="item.value" :key="item.key">{{ item.label }}</Option>
                    </Select>
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="10">
                  <FormItem prop="jobDate" label="批量日期">
                    <DatePicker :transfer="true" type="date" parse="yyyy-MM-dd" format="yyyy-MM-dd" v-model="formBean.jobDate"></DatePicker>
                  </FormItem>
                </Col>
                <Col span="10">
                  <FormItem prop="virtualEnable" label="作业是否执虚">
                    <Select v-model="formBean.virtualEnable" filterable clearable>
                      <Option :value="1" :key="1">虚作业</Option>
                      <Option :value="0" :key="0">非虚作业</Option>
                    </Select>
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="10">
                  <FormItem prop="priority" label="作业执行优先级">
                    <Input v-model="formBean.priority"/>
                  </FormItem>
                </Col>
                <Col span="10">
                  <FormItem prop="des" label="描述" >
                    <Input v-model="formBean.des"></Input>
                  </FormItem>
                </Col>
              </Row>
            </Card>
          </Row>
          </br>
          <Row>
            <p>任务依赖配置</p>
          </Row>
          <Row style="margin-top:10px">
            <Card>
              <Row>
                <Col span="10">
                  <Button type="primary" @click="depJobConfig">依赖配置</Button>
                </Col>
              </Row>
            </Card>
          </Row>
          </br>
          <Row>
            <p>任务脚本配置</p>
          </Row>
          <Row style="margin-top:10px">
            <Card>
              <Row>
                <Col span="10">
                  <Button type="primary" @click="jobStepConfig">脚本配置</Button>
                </Col>
              </Row>
            </Card>
          </Row>
        </Col>
      </Row>
    </Form>

    <div style="text-align:center">
      <Button type="primary" :loading="loading" icon="md-add" @click="save">保存</Button>
      <Button type="primary" icon="ios-trash-outline" @click="cancel">取消</Button>
    </div>

    <Drawer
      title="依赖配置"
      v-model="depJobDrawer.show"
      width="720"
      :mask-closable="false"
      >
      <Form ref="depJobDrawerForm" :model="depJobDrawer.data" :label-width="100" :rules="depJobRuler">
        <Row :gutter="32">
            <Col span="12">
                <FormItem prop="depPlatform" label="依赖平台" label-position="top">
                    <Input v-model="depJobDrawer.data.depPlatform"/>
                </FormItem>
            </Col>
            <Col span="12">
                <FormItem prop="depSystem" label="依赖应用" label-position="top">
                    <Input v-model="depJobDrawer.data.depSystem">
                    </Input>
                </FormItem>
            </Col>
        </Row>
        <Row :gutter="32">
            <Col span="12">
                <FormItem prop="depJob" label="依赖作业" label-position="top">
                  <Input v-model="depJobDrawer.data.depJob">
                    </Input>
                </FormItem>
            </Col>
            <Col span="12">
                <FormItem prop="depBatch" label="批次" label-position="top">
                  <!-- <Input v-model="depJobDrawer.data.depBatch"></Input> -->
                    <InputNumber style="width: 100%" :step="1" :min="0" v-model="depJobDrawer.data.depBatch"></InputNumber>
                </FormItem>
            </Col>
        </Row>
        <Row>
            <Col :offset="11" span="2">
              <Button type="primary" icon="md-add" @click="addDepJob">添加</Button>
            </Col>
        </Row>
        <Row style="margin-top:6%" :gutter="32">
            <Col span="24">
              <Table :columns="depColumns" :data="depJobList" stripe></Table>
            </Col>
        </Row>
      </Form>
      <div class="drawer-footer">
        <Button style="margin-right: 8px" @click="depJobDrawer.show = false">取消</Button>
        <Button type="primary" @click="saveDepJob">保存</Button>
      </div>
    </Drawer>

    <Drawer
      title="脚本配置"
      v-model="jobStepDrawer.show"
      width="80%"
      :mask-closable="false"
      >
      <Row style="background:#d9dee9;padding:5px">
        <Col span="4">
          <span class="jobsetp-title">脚本模块</span>
        </Col>
        <!-- <Col span="20">
      序号 脚本 脚本参数 环境参数
        </Col> -->
        <Col span="1" :offset="1">
          <span class="jobsetp-title">序号</span>
        </Col>
        <Col span="2" :offset="1">
          <span class="jobsetp-title">类型</span>
        </Col>
        <Col span="2" :offset="1">
          <span class="jobsetp-title">脚本上传</span>
        </Col>
        <Col span="4" :offset="1">
          <span class="jobsetp-title">脚本参数</span>
        </Col>
        <Col span="5" :offset="1">
          <span class="jobsetp-title">环境参数</span>
        </Col>
      </Row>
      <Row>
        <Col span="4">
          <!-- <div style="padding: 10px;text-align: center">
            <Tag v-for="item in stepTypeData" style="margin-right:3%" :key="item" color="primary" :name="item" @click.native="handleAdd(item)" size="large">{{ item }}</Tag>
          </div> -->
          <div v-for="item in stepTypeData" :key="item" class="jobsetp-tag" >
            <Tag color="primary" :name="item" @click.native="handleAdd(item)" size="large">{{ item }}</Tag>
          </div>
        </Col>
        <Col span="20">
          <!-- 脚本执行顺序 执行命令 脚本类型 脚本执行路径 脚本执行所需参数
          stepNum operCmd stepType scriptPath parameter environments-->
          <Form ref="jobStepDrawerForm" :model="jobStepDrawer.data" :label-width="100" style="margin-top:2%">
            <FormItem
              v-for="(item,index) in jobStepDrawer.data.list"
              v-if="item.status"
              :key="index"
              :label="'序号'+(index+1)"
              :prop="'list.'+index+'.value'"
              :rules="{required: true,message:'数据不能为空',trigger: 'blur'}"
              >
              <Row>
                <Col span="3" :offset="1">
                  <span>{{item.operCmd}}</span>
                </Col>
                <Col span="2" :offset="1">
                  <Button icon="ios-cloud-upload-outline" @click="uploadStepShow(item,index)">上传</Button>
                </Col>
                <Col span="5" :offset="1">
                  <Input v-model="item.parameter" readonly @click.native="parameterShow(item,index)">
                  </Input>
                </Col>
                <Col span="5" :offset="1">
                  <Input v-model="item.environments" readonly @click.native="envShow(item,index)">
                  </Input>
                </Col>
                <Col span="2" :offset="1">
                  <Button @click="handleRemove(index)">删除</Button>
                </Col>
              </Row>
            </FormItem>
          </Form>
        </Col>
      </Row>
      <div class="drawer-footer">
        <Button style="margin-right: 8px" @click="jobStepDrawer.show = false">取消</Button>
        <Button type="primary" @click="saveJobStep">保存</Button>
      </div>
    </Drawer>

    <Modal id="uploadOp" v-model="jobStepDrawer.uploadShow" title="脚本上传" width="50%" :mask-closable="false" :closable="false" >
      <div>
        <div>
          <Form ref="importOpForm" :label-width="100" >
            <Row>
              <Col span="16">
                <FormItem label="请选择协议:" >
                    <RadioGroup v-model='radioData' @on-change="checkRadio">
                      <Radio label='aws'>对象存储</Radio>
                      <Radio label='scp'>rpc</Radio>
                      <Radio label='local'>本地</Radio>
                    </RadioGroup>
                </FormItem>
              </Col>
            </Row>
            <Row>
              <Col span="16">
                <FormItem label="导入文件名:" style="cursor:pointer;" >
                  <Upload v-if="isShow" ref="importExcel" name="files" :before-upload="handleUpload" action="" >
                    <Input style="width:400px;" type="textarea" :format="['xlsx','xls']" :autosize="{minRows: 1,maxRows: 5}" v-model="files.length === 0? '' : files[0].name" placeholder="请选择文件"/>
                  </Upload>
                  <Input style="width:400px;" v-if="!isShow" v-model='filePath' ></Input>
                </FormItem>
              </Col>
              <Col span="8">
                <div class="spdb-toolbar">
                  <Button icon="md-add" type="primary" @click="importExcel" :loading="loadingStatus" :disabled="loadingStatus" >{{ loadingStatus ? "Uploading" : "导入" }}</Button>
                  <Button icon="md-close" type="primary" @click="jobStepDrawer.uploadShow=false" > 取消 </Button>
                </div>
              </Col>
            </Row>
            <Row>
              <FormItem >
                <div><font style='color: red;'>注：协议选择本地(local)时，请自行上传脚本文件，文件名请填写上传路径</font></div>
              </FormItem>
            </Row>
          </Form>
        </div>
      </div>
      <div slot="footer"></div>
    </Modal>

    <Drawer
      title="脚本参数配置"
      v-model="jobStepDrawer.parameterShow"
      width="40%"
      :closable="false"
      >
        <Form ref="formDynamic" :model="jobStepDrawer.parameter" :label-width="80" style="width: 300px">
          <!-- :rules="{required: true, message: '参数数据不要为空', trigger: 'blur'}"> -->
          <FormItem
                  v-for="(item, index) in jobStepDrawer.parameter.list"
                  v-if="item.status"
                  :key="index"
                  :label="index+1+''"
                  :prop="'list.' + index + '.parameter'"
                  >
              <Row>
                  <Col span="18">
                      <Input type="text" v-model="item.parameter" :placeholder="item.placeholder"></Input>
                  </Col>
                  <Col span="4" offset="1">
                      <Button @click="parameterRemove(index)">删除</Button>
                  </Col>
              </Row>
          </FormItem>
          <FormItem>
              <Row>
                  <Col span="12">
                      <Button type="dashed" long @click="parameterAdd" icon="md-add">添加</Button>
                  </Col>
              </Row>
          </FormItem>
          <FormItem>
              <Button type="primary" @click="parameterOk">确定</Button>
              <Button @click="jobStepDrawer.parameterShow = false" style="margin-left: 8px">取消</Button>
          </FormItem>
      </Form>
    </Drawer>

    <Drawer
      title="环境参数配置"
      v-model="jobStepDrawer.envShow"
      width="40%"
      :closable="false"
      >
        <Form ref="formDynamic" :model="jobStepDrawer.env" :label-width="80" style="width: 300px">
          <FormItem
                  v-for="(item, index) in jobStepDrawer.env.list"
                  v-if="item.status"
                  :key="index"
                  :label="index+1+''"
                  :prop="'list.' + index + '.environments'"
                  :rules="{required: true, message: '参数数据不要为空', trigger: 'blur'}">
              <Row>
                  <Col span="20">
                      <Input type="text" v-model="item.environments" :placeholder="item.placeholder"></Input>
                  </Col>
                  <Col span="2" offset="1">
                      <Button @click="envRemove(index)">删除</Button>
                  </Col>
              </Row>
          </FormItem>
          <FormItem>
              <Row>
                  <Col span="12">
                      <Button type="dashed" long @click="envAdd" icon="md-add">添加</Button>
                  </Col>
              </Row>
          </FormItem>
          <FormItem>
              <Button type="primary" @click="envOk">确定</Button>
              <Button @click="jobStepDrawer.envShow = false" style="margin-left: 8px">取消</Button>
          </FormItem>
      </Form>
    </Drawer>
  </div>
</template>

<script>
import utils from '@/common/utils'
const RESOURCE_PATH = '/jobattributes'

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
    const checkCron = (rule, value, callback) => {
      if (this.disJobFrequency) return callback()
      if (value) {
        let arr = value.trim().split(' ')
        if (!arr || arr.length < 6) {
          callback(new Error('cron表达至少填写到星期（秒 分钟 小时 日 月 星期 年），请添加！'))
        } else {
          let parser = require('cron-parser')
          try {
            parser.parseExpression(value)
            callback()
          } catch (e) {
            callback(new Error('非cron表达式格式，请检查！' + e.message))
          }
        }
      } else {
        callback(new Error('请输入数据'))
      }
    }
    const checkMultiBatch = (rule, value, callback) => {
      if (value) {
        if (!this.formBean.job) {
          callback(new Error('请先输入作业名'))
        } else {
          callback()
        }
      } else {
        callback(new Error('请输入数据'))
      }
    }
    return {
      initFlag: 0,
      isShow: false,
      radioData: 'local',
      depColumns: [
        {
          title: '依赖平台',
          key: 'depPlatform',
          align: 'center',
          minWidth: 80
        },
        {
          title: '依赖应用',
          key: 'depSystem',
          align: 'center',
          minWidth: 80
        },
        {
          title: '依赖作业',
          key: 'depJob',
          align: 'center',
          minWidth: 180
        },
        {
          title: '批次',
          key: 'depBatch',
          align: 'center',
          minWidth: 80
        },
        {
          title: '操作',
          fixed: 'right',
          width: 80,
          align: 'center',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('Button', {
                props: {
                  size: 'small',
                  // icon: 'md-close',
                  // shape: 'circle',
                  type: 'primary'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.removeDepJob(index)
                  }
                }
              }, '删除')
            ])
          }
        }
      ],
      disJobFrequency: false,
      loadingStatus: false,
      files: [],
      fileError: '',
      file: null,
      depJobDrawer: {
        show: false,
        data: {}
      },
      depJobList: [],
      currentIndex: null,
      jobStepDrawer: {
        show: false,
        uploadShow: false, // 上传脚本
        env: {
          list: [
            {
              environments: 'Password=?;name=?',
              status: 1
            }
          ]
        },
        envShow: false,
        parameterShow: false, // 脚本参数
        parameter: {
          list: [
            {
              parameter: '${platform}',
              status: 1
            },
            {
              parameter: '${systems}',
              status: 1
            },
            {
              parameter: '${job}',
              status: 1
            },
            {
              parameter: '${job_date}',
              status: 1
            },
            {
              parameter: '${batch}',
              status: 1
            }
          ]
        }, // 脚本参数
        data: {
          list: [
            // {
            //   parameter: '',
            //   environments: '',
            //   index: 1,
            //   status: 1
            // }
          ]
        }
      },
      jobStepList: [],
      fileType: '',
      formBean: {},
      loading: false,
      platformData: [],
      systemsData: [],
      jobTypeData: [],
      isEdit: false,
      callAgainMaxNumData: [],
      stepTypeData: [
        // 'shell', 'python', 'python3', 'perl', 'http_get', 'http_post', 'java', 'cmd'
        'SQL', 'JAVA', 'HTTP_POST', 'HTTP_GET', 'HTTP_DELETE', 'HTTP_PUT', 'HTTP_HEAD', 'CMD', 'SHELL', 'PYTHON', 'PYTHON3', 'PERL'
      ],
      lastStatusData: [
        // Ready Done Runing Failed Pending Dispatcher
        { label: 'READY', value: 'READY' },
        { label: 'SUCCESS', value: 'SUCCESS' },
        { label: 'RUNING', value: 'RUNING' },
        { label: 'FAILURE', value: 'FAILURE' },
        { label: 'PENDING', value: 'PENDING' },
        { label: 'DISPATCHER', value: 'DISPATCHER' }
      ],
      taskStatusData: [
        // 1->新增；2->变更；3->下线；4->上线完成',
        { label: '新增', value: 1 },
        { label: '变更', value: 2 },
        { label: '下线', value: 3 },
        { label: '上线完成', value: 4 }
      ],
      depJobRuler: {
        depPlatform: [{
          required: true,
          message: '请输入数据！'
        }],
        depSystem: [{
          required: true,
          message: '请输入数据！'
        }],
        depJob: [{
          required: true,
          message: '请输入数据！'
        }],
        depBatch: [{
          required: true,
          message: '请输入数据！'
        }]
      },
      formRule: {
        streamType: [{
          required: true,
          message: '请输入数据！'
        }],
        platform: [{
          required: true,
          message: '请输入数据！'
        }],
        systems: [{
          required: true,
          message: '请输入数据！'
        }],
        job: [{
          required: true,
          message: '请输入数据！'
        }],
        // jobName: [{
        //   required: true,
        //   message: '请输入数据！'
        // }],
        jobType: [{
          required: true,
          message: '请输入数据！'
        }],
        jobDate: [{
          required: true,
          message: '请输入数据！'
        }],
        lastStatus: [{
          required: true,
          message: '请输入数据！'
        }],
        taskStatus: [{
          required: true,
          message: '请输入数据！'
        }],
        multiBatch: [{
          validator: checkMultiBatch,
          trigger: 'blur'
        }],
        timeWindow: [{
          required: true,
          message: '请输入数据！'
        }],
        virtualEnable: [{
          required: true,
          message: '请输入数据！'
        }],
        priority: [{
          required: true,
          message: '请输入数据！'
        }],
        callAgainMaxNum: [{
          required: true,
          message: '请输入数据！'
        }],
        callAgainWaitSec: [{
          required: true,
          message: '请输入数据！'
        }],
        checkFrequency: [{
          required: true,
          message: '请输入数据！'
        }],
        checkTimeTrigger: [{
          required: true,
          message: '请输入数据！'
        }],
        checkStreamSelf: [{
          required: true,
          message: '请输入数据！'
        }],
        depJob: [{
          required: true,
          message: '请输入数据！'
        }],
        jobStep: [{
          required: true,
          message: '请输入数据！'
        }],
        jobFrequency: [{
          validator: checkCron,
          trigger: 'blur'
        }],
        offsetDay: [{
          required: true,
          message: '请输入数据！'
        }],
        ignoreError: [{
          required: true,
          message: '请输入数据！'
        }]
      },
      jobStepDrawerRecord: {},
      filePath: ''
    }
  },
  methods: {
    queryPlatform () {
      let platformList = this.$store.getters.getUserPlatform()
      platformList.forEach(data => {
        let tmp = {}
        tmp.value = data
        tmp.label = data
        this.platformData.push(tmp)
      })
    },
    querySystem () {
      this.systemsData = []
      this.$refs.refsystem.setQuery('')
      if (this.formBean.platform) {
        let userSystemList = this.$store.getters.getUserSystem()
        let userSystem = userSystemList[this.formBean.platform]
        userSystem.forEach(data => {
          let tmp = {}
          tmp.value = data
          tmp.label = data
          this.systemsData.push(tmp)
        })
      }
    },
    /**
    /**
     * 初始化
     **/
    init () {
      if (this.transData.row) { // 如果传过来的存在ID则说明是编辑功能跳转过来
        this.initFlag = 1
        this.isEdit = true
        let params = { platform: this.transData.row.platform, systems: this.transData.row.systems, job: this.transData.row.job }
        let loadConfig = {
          method: 'GET',
          url: RESOURCE_PATH + '/loadByJobName/',
          params: params
        }
        this.$ajax(loadConfig)
          .then(resp => {
            this.formBean = Object.assign({}, resp.data)
            let timeWindow = this.formBean.timeWindow
            if (timeWindow) {
              this.formBean.timeWindow = timeWindow.split('-')
              this.formBean.checkFrequency = this.formBean.checkFrequency ? 1 : 0
              this.formBean.checkStreamSelf = this.formBean.checkStreamSelf ? 1 : 0
              this.formBean.checkTimeTrigger = this.formBean.checkTimeTrigger ? 1 : 0
              this.formBean.ignoreError = this.formBean.ignoreError ? 1 : 0
              this.formBean.virtualEnable = this.formBean.virtualEnable ? 1 : 0
            }
            if (this.formBean.depJob) {
              let arr = this.formBean.depJob.split(',')
              arr.forEach(e => {
                let tmp = {
                  depPlatform: e.split('@')[0],
                  depSystem: e.split('@')[1],
                  depJob: e.split('@')[2],
                  depBatch: e.split('@')[3]
                }
                this.depJobList.push(tmp)
              })
            }
            if (this.formBean.jobStep) {
              let arr = this.formBean.jobStep.split(',')
              arr.forEach(e => {
                // 4@python3@null@$AUTO_HOME/APP/BDP/ADM/BDP_ADM_DTR_DAILY_RPT/bin/bdp_adm_dtr_daily_rpt0400.py@1 2
                // stepNum@operCmd@stepType@scriptPath@parameter@environments
                let tmp = {
                  stepNum: e.split('@')[0],
                  operCmd: e.split('@')[1],
                  stepType: e.split('@')[2],
                  scriptPath: e.split('@')[3],
                  parameter: e.split('@')[4],
                  environments: e.split('@')[5]
                }
                this.jobStepList.push(tmp)
              })
            }
          })
      } else {
        this.bindData()
      }
      // this.queryCodeType()
      this.queryPlatform()
      // this.queryNoticeObj()
      this.queryJobType()
      this.genCallAgainMaxNumData()
      // this.queryServerityType()
      // this.queryAlarmType()
    },
    /**
     * 如果传过来的有初始数据则进行数据绑定
     **/
    bindData () {
      try {
        this.formBean = Object.assign({}, this.transData.initFormBean)
        this.formBean.lastStatus = 'READY'
        this.formBean.taskStatus = 1
        this.formBean.callAgainMaxNum = 0
        this.formBean.timeWindow = ['00:00', '23:59']
        this.formBean.offsetDay = 0
        this.formBean.callAgainWaitSec = 120
      } catch (error) {
        console.error(error)
      }
    },
    uploadStepShow (item, index) {
      this.jobStepDrawerRecord = item
      if (this.jobStepDrawerRecord.scriptPath) {
        this.filePath = this.jobStepDrawerRecord.scriptPath
      } else {
        this.filePath = ''
      }
      this.jobStepDrawer.uploadShow = true
    },
    jobStepConfig () {
      if (this.transData.id) {
        this.depJobDrawer.data.depBatch = 0
        this.jobStepDrawer.data.list = []
        this.jobStepList.sort(utils.compare('index')).map(item => {
          this.jobStepDrawer.data.list.push(Object.assign(item, { status: 1 }))
        })
      }
      this.jobStepDrawer.show = true
    },
    handleAdd (fileType) {
      if (this.jobStepDrawer.data.list && this.jobStepDrawer.data.list.length > 8) {
        return this.$Message.warning('当前作业脚本配置数量已达最大值')
      }
      this.jobStepDrawer.data.list.push({
        operCmd: fileType,
        parameter: '',
        environments: '',
        status: 1
      })
      this.fileType = fileType
    },
    saveJobStep () {
      let list = this.jobStepDrawer.data.list
      this.jobStepList = []
      if (list && list.length > 0) {
        list.forEach((e, index) => {
          let tmp = {
            operCmd: e.operCmd,
            stepNum: index + 1,
            stepType: e.stepType,
            environments: e.environments,
            parameter: e.parameter,
            scriptPath: e.scriptPath
          }
          this.jobStepList.push(tmp)
        })
      }
      this.jobStepDrawer.show = false
      console.log(this.jobStepList)
    },
    handleRemove (index) {
      this.jobStepDrawer.data.list[index].status = 0
      this.jobStepDrawer.data.list.splice(index, 1)
    },
    parameterShow (item, index) {
      console.log(item.parameter.length)
      if (item.parameter && item.parameter.length > 0) {
        this.jobStepDrawer.parameter.list = []
        item.parameter.split(' ').forEach(e => {
          let tmp = {
            parameter: e,
            status: 1,
            placeholder: '${参数}'
          }
          this.jobStepDrawer.parameter.list.push(tmp)
        })
      }
      this.currentIndex = index
      this.jobStepDrawer.parameterShow = true
    },
    parameterOk () {
      let list = this.jobStepDrawer.parameter.list
      let str = ''
      if (list && list.length > 0) {
        list.forEach(e => {
          str = str + ' ' + e.parameter
        })
      }
      this.jobStepDrawer.data.list[this.currentIndex].parameter = str.trim()
      this.jobStepDrawer.parameterShow = false
    },
    parameterAdd () {
      this.jobStepDrawer.parameter.list.push({
        placeholder: '${参数}',
        parameter: '',
        status: 1
      })
    },
    parameterRemove (index) {
      this.jobStepDrawer.parameter.list[index].status = 0
      this.jobStepDrawer.parameter.list.splice(index, 1)
    },
    envShow (item, index) {
      console.log(item)
      item.environments = 'Password=?;name=?'
      if (item.environments) {
        this.jobStepDrawer.env.list = []
        console.log(item.environments.split(';'))
        item.environments.split(';').forEach(e => {
          let tmp = {
            environments: e,
            status: 1,
            placeholder: '${参数}'
          }
          this.jobStepDrawer.env.list.push(tmp)
        })
      }
      this.currentIndex = index
      this.jobStepDrawer.envShow = true
    },
    envOk () {
      let list = this.jobStepDrawer.env.list
      let str = ''
      if (list && list.length > 0) {
        list.forEach(e => {
          str = str + ';' + e.environments
        })
      }
      this.jobStepDrawer.data.list[this.currentIndex].environments = str.trim().replace(/^;/, '')
      this.jobStepDrawer.envShow = false
    },
    envAdd () {
      this.jobStepDrawer.env.list.push({
        environments: '',
        placeholder: 'key=value;key=value',
        status: 1
      })
    },
    envRemove (index) {
      this.jobStepDrawer.env.list[index].status = 0
      this.jobStepDrawer.env.list.splice(index, 1)
    },
    depJobConfig () {
      if (this.transData.id) {
        this.depJobDrawer.data.depBatch = 0
      }
      this.depJobDrawer.show = true
    },
    addDepJob () {
      this.$refs.depJobDrawerForm.validate(valid => {
        if (!valid) return
        let tmp = Object.assign({}, this.depJobDrawer.data)
        let params = { platform: tmp.depPlatform, systems: tmp.depSystem, job: tmp.depJob }
        if (this.depJobList && this.depJobList.length > 0) {
          try {
            this.depJobList.forEach(e => {
              if (JSON.stringify(e) === JSON.stringify(tmp)) {
                this.$Message.warning('请勿重复添加！')
                throw new Error('repeatAddError')
              }
            })
            this.jobSearch(params, tmp)
          } catch (e) {
            if (e.message !== 'repeatAddError') throw e
          }
        } else {
          this.jobSearch(params, tmp)
        }
      })
    },
    jobSearch (params, tmp) {
      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/jobSearch',
        params: params
      }
      this.$ajax(httpConfig)
        .then(resp => {
          if (resp.status && resp.status === 200) {
            if (resp.data && resp.data.length > 0) {
              this.depJobList.push(tmp)
            } else {
              this.$Message.warning('当前作业不存！')
            }
          }
        })
    },
    removeDepJob (index) {
      this.depJobList.splice(index, 1)
    },
    saveDepJob () {
      // this.depJobList = []
      let tmparr = []
      if (this.depJobList && this.depJobList.length > 0) {
        this.depJobList.forEach(e => {
        // 平台@应用@作业@批次
          tmparr.push(e.depPlatform + '@' + e.depSystem + '@' + e.depJob + '@' + e.depBatch)
        })
        this.formBean.depJob = tmparr.join(',')
      }
      this.depJobDrawer.show = false
    },
    addjobDep () {

    },
    handleUpload: function (file) {
      if (!this.multiple) {
        if (this.files.length > 0) {
          this.fileError = `不能选择多个的文件`
          return false
        }
      }
      if (this.files.find(e => e.name == file.name)) {
        this.fileError = `${file.name}已存在上传列表，已自动过滤掉！`
        return false
      }
      this.files.push(file)
      return false
    },
    importExcel () {
      if (this.radioData === 'local') {
        this.jobStepDrawerRecord.scriptPath = this.filePath
        this.jobStepDrawer.uploadShow = false
      } else {
        var formData = new FormData()
        formData.append('fileType', this.fileType)
        formData.append('platform', this.formBean.platform)
        formData.append('systems', this.formBean.systems)
        formData.append('job', this.formBean.job)
        formData.append('version', 1)
        if (this.files.length == 0 || this.files == null) {
          this.$Message.warning('请选择导入文件')
        } else {
          for (const ele of this.files) {
            formData.append('file', ele)
          }
          // formData.authps = systems
          // String fileType, String platform, String systems, String job
          // return
          this.loadingStatus = true
          this.$ajax.post('/jobattributes/upload', formData)
            .then(resp => {
              this.loadingStatus = false
              this.files = []
              this.jobStepDrawer.uploadShow = false
            })
        }
      }
      // })
    },
    /**
     * 保存表单
     **/
    save () {
      this.$refs.alarmConfigForm.validate(valid => {
        if (!valid) return
        let params = {}
        let tmpObj = Object.assign(this.formBean)
        if (tmpObj.timeWindow) {
          tmpObj.timeWindow = tmpObj.timeWindow.join('-')
        }
        if (tmpObj.jobDate) {
          tmpObj.jobDate = utils.fmtDate(this.formBean.jobDate, 'yyyy-MM-dd')
        }
        params.dyJobAttributes = JSON.stringify(tmpObj)
        debugger
        params.jobStepList = JSON.stringify(this.jobStepList)
        params.depJobList = JSON.stringify(this.depJobList)
        // let dyJobAttributes = {
        //   callAgainMaxNum: 3,
        //   callAgainWaitSec: 2,
        //   checkFrequency: 0,
        //   checkStreamSelf: 1,
        //   checkTimeTrigger: 1,
        //   ignoreError: 1,
        //   job: 'BDP_ADM_DTR_DAILY_RPT',
        //   jobDate: '2022-04-28',
        //   jobFrequency: '1',
        //   jobName: 'test',
        //   jobType: 'D',
        //   offsetDay: 1,
        //   platform: 'BDP',
        //   priority: '1',
        //   sysytems: 'ADM',
        //   stakStatus: 1,
        //   timeWindow: '00:00-23:59',
        //   virtualEnable: 1

        // }
        // let depJobList = [
        //   { depPlatform: 'A', depSystems: 'A', depJob: 'A_A_A', depBatch: 2 },
        //   { depPlatform: 'A', depSystems: 'A', depJob: 'A_A_A', depBatch: 1 }
        // ]
        // let jobStepList = [
        //   {
        //     environments: 'Password=?;name=?',
        //     parameter: ' ${platform} ${systems} ${job} ${job_date} ${batch}',
        //     stepNum: '1',
        //     stepType: 'shell'
        //   }
        // ]
        // params.dyJobAttributes = JSON.stringify(dyJobAttributes)
        // params.jobStepList = JSON.stringify(jobStepList)
        // params.depJobList = JSON.stringify(depJobList)
        let httpConfig = {
          url: RESOURCE_PATH,
          data: params
        }
        httpConfig.contentType = 'json'
        if (this.transData.id) {
          httpConfig.method = 'PUT'
          httpConfig.data.id = tmpObj.id
          httpConfig.url = RESOURCE_PATH + '/update'
        } else {
          httpConfig.method = 'POST'
          httpConfig.url = RESOURCE_PATH + '/add'
        }
        console.log(httpConfig)
        // return
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
    genCallAgainMaxNumData () {
      this.callAgainMaxNumData = []
      for (let i = 0; i < 11; i++) {
        let tmp = { label: i, value: i }
        this.callAgainMaxNumData.push(tmp)
      }
    },
    queryJobType () {
      this.jobTypeData = [
        { value: 'D', label: '每天执行' },
        { value: 'W', label: '每周执行' },
        { value: 'M', label: '每月执行' },
        { value: 'C', label: '定时执行' }
      ]
    },
    /**
     * 返回到数据视图
     **/
    cancel () {
      // let queryCache = { formBean: this.transData.formBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize }
      // this.$emit('switch', Object.assign({}, queryCache))
      this.$router.push({
        name: 'workflowmanage'
      })
    },
    checkRadio () {
      if (this.radioData === 'local') {
        this.isShow = false
      } else {
        this.isShow = true
      }
    }
  },
  /**
   * 视图挂载
   **/
  mounted () {
    this.init()
  },
  watch: {
    'formBean.jobType': function (e) {
      this.disJobFrequency = false
      if (this.formBean.jobType === 'D') {
        this.disJobFrequency = true
      }
    }
  }
}

</script>
<style>
  .drawer-footer{
    width: 100%;
    position: absolute;
    bottom: 0;
    left: 0;
    border-top: 1px solid #e8e8e8;
    padding: 10px 16px;
    text-align: right;
    background: #fff;
  }
  .jobsetp-title{
    font-size: 14px;
    font-weight: 700;
    color: #495060;
    text-align: center;
  }
  .jobsetp-tag .ivu-tag{
    font-size: 14px;
    margin-top: 10%;
  }
  .work-flow .ivu-form-item-error-tip {
    white-space: nowrap;
  }
</style>

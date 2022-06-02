<template>
  <div>
    <div class="spdb-form" style="margin-top:20px;">
      <Form ref="udsjobForm" :model="formBean" :label-width="80" :rules="ruleValidate">
        <Row>
          <Col span="3">
            <FormItem prop="platform" label="平台名">
              <Select filterable v-model="formBean.platform" @on-change="getSystemList" clearable>
                <Option v-for="item in platformData" :value="item.value+''" :key="item.value">{{ item.label }}</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="3">
            <FormItem label="应用名">
              <Select ref="refsystem" filterable v-model="formBean.systems" placeholder="先选平台" clearable>
                <Option v-for="item in systemData" :value="item.value" :key="item.value">{{ item.label }}</Option>
              </Select>

            </FormItem>
          </Col>
          <Col span="3">
            <FormItem label="作业状态">
              <Select filterable v-model="formBean.lastStatus" clearable>
                <Option v-for="item in jobstatusData" :value="item.value" :key="item.value">{{ item.label }}</Option>
              </Select>
            </FormItem>
          </Col>
          <Col span="6">
            <FormItem label="作业名">
              <Input type="textarea" :autosize="{minRows: 1,maxRows: 5}" v-model="formBean.job" placeholder="支持模糊查询"/>
            </FormItem>
          </Col>
          <Col span="1">&nbsp;</Col>
          <Col span="8">
            <div class="spdb-toolbar">
              <Button type="primary" icon="ios-search" @click="search">查询</Button>
              <Button type="primary" icon="md-close" @click="clear">清除查询</Button>
              <!-- <S-AuthButton type="primary" icon="md-add" @click="add" :requestConfig=request.addReq>新增</S-AuthButton>
              <S-AuthButton type="primary" icon="ios-trash-outline" @click="del" :requestConfig=request.delReq>删除</S-AuthButton> -->
              <Button type="primary" icon="md-cloud-download" @click="download">下载</Button>
              <!--  icon="md-add"  -->
              <s-auth-button :loading="loadingBatchA" :disabled="ctrlloadingBatchADisable" type="primary" @click="batchActiveConfirm" :requestConfig=request.batchActive>批量重调</s-auth-button>
              <!--
                <S-AuthButton :loading="loadingBatchFC" :disabled="ctrlloadingBatchFCDisable" type="primary" @click="batchForceStartConfirm" :requestConfig=request.batchForceStart>批量补数</S-AuthButton>
              -->
            </div>
          </Col>
        </Row>
      </Form>
    </div>

    <div v-if="transData.showTable" class="spdb-table" id="joblistTable">
      <Table  size="small"  :columns="gridColumns" :data="gridData" stripe @on-selection-change="select" @on-sort-change="changeSort">
      </Table>
    </div>

    <div v-if="transData.showTable" class="spdb-page">
      <Page placement="top" :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt"  show-total show-sizer show-elevator @on-change="changePage" @on-page-size-change="changePageSize" ></Page>
    </div>
    
    <Modal id="confirmM"
      v-model="confirm.show"
      :mask-closable="false"
      title="提示">
      <div>
        <Alert type="warning" >确认kill作业:{{confirm.job}}</Alert>
      </div>
      <div slot="footer">
        <div style="margin-left: 150px;">
          <Button type="primary" @click="killJob">确定</Button>
          <Button type="primary" @click="confirmCancel">取消</Button>
        </div>
      </div>
    </Modal>
   
    <Modal id="confirmD"
      v-model="confirmDisable.show"
      :mask-closable="false"
      title="提示">
      <div>
        <Form  ref="confirmDisableForm" :label-width="100"  inline>
          <Alert class="m-warn-con" type="warning" >禁用作业后，该作业不再运行</Alert>
          <Alert class="m-warn-con" type="warning" >禁用作业影响该作业的触发作业运行</Alert>
          <Checkbox class="m-warn-con" v-model="res.isAgree" > 我承担该操作的一切后果</Checkbox>
        </Form>
      </div>
      <div slot="footer">
        <div style="margin-left: 150px;">
          <Button type="primary" :loading="loadingdisable" @click="disableJob">确定</Button>
          <Button type="primary" @click="disableCancel">取消</Button>
        </div>
      </div>
    </Modal>
    <Modal id="confirmBatchActive"
      v-model="confirmBatchActive.show"
      :mask-closable="false"
      title="提示">
      <div>
        <Alert class="m-warn-con" type="warning" >批量重调会检查依赖，触发下游作业执行，确认是否进行批量重调?</Alert>
        <Checkbox class="m-warn-con" v-model="confirmBatchActive.isAgree" > 我承担该操作的一切后果</Checkbox>
      </div>
      <div slot="footer">
        <div style="margin-left: 150px;">
          <Button type="primary" @click="batchActive">确定</Button>
          <Button type="primary" @click="confirmBatchActiveCancel">取消</Button>
        </div>
      </div>
    </Modal>

  <Modal id="confirmBatchForceStart"
      v-model="confirmBatchForceStart.show"
      :mask-closable="false"
      width='50%'
      title="提示">
      <div>
        <div>
          <Form ref="confirmBatchForceStartForm" :model="forceStartFormBean" :rules="forceStartRuleValidate" :label-width="100"   inline>
            <Row>
              <Col span="10">
                <FormItem prop="jobDate" label="补数日期">
                  <DatePicker style="width:96%" type="date" multiple  format="yyyyMMdd" v-model="forceStartFormBean.jobDate" ></DatePicker>
                </FormItem>
              </Col>
            </Row>
			      <Row>
              <Col span="10">
                <FormItem prop="multiBatch" label="批次号">
                  <Input style="width:110%" v-model="forceStartFormBean.multiBatch" />
                </FormItem>
              </Col>
            </Row>
            <Row >
              <Col span="10">
                <FormItem prop="serverName" label="执行节点">
                    <Select style="width:100%" filterable v-model="forceStartFormBean.serverName" clearable placeholder="请选择">
                      <Option v-for="item in serverData" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                </FormItem>
              </Col>

              <Col span="14" >
                <FormItem>
                  <Alert type="warning" > 提示:udsslavedep是交换的节点，专门跑Datastage的。perl脚本选udsslave即可</Alert>
                </FormItem>
              </Col>
            </Row>
          </Form>
        </div>
        <Alert class="m-warn-con" type="warning" >确认是否进行批量补数?</Alert>
        <Checkbox class="m-warn-con" v-model="confirmBatchForceStart.isAgree" > 我承担该操作的一切后果</Checkbox>
      </div>
      <div slot="footer">
        <div style="margin-left: 150px;">
          <Button type="primary" @click="batchForceStart">确定</Button>
          <Button type="primary" @click="confirmBatchForceStartCancel">取消</Button>
        </div>
      </div>
    </Modal>
    <Modal id="confirmV"
      v-model="confirmVi.show"
      :mask-closable="false"
      title="提示">
      <div>
        <Alert class="m-warn-con" type="warning" >
            <span style="font-weight:bold">友情提示:</span><br/>
            <div style="margin-left:4%">
              1.置虚作业后，不再执行该作业的脚本<br/>
              2.使用该作业数据的下游也无法获取该作业最新数据<br/>
              3.请应用关注下游作业影响，建议同步通知下游负责人<br/>
           </div>
        </Alert>
        <Checkbox class="m-warn-con" v-model="res.isAgree" >我承担该操作的一切后果</Checkbox>
      </div>
      <div slot="footer">
        <div style="margin-left: 150px;">
          <Button type="primary" :loading="loadingvitual" @click="vitualJob">确定</Button>
          <Button type="primary" @click="vitualCancel">取消</Button>
        </div>
      </div>
    </Modal>
    <Modal id="upAndDownstream"
			v-model="upAndDownstream.show"
			title="上下游信息"
			width='75%'
			>
      <div style="max-height:60vh;overflow-y:scroll;overflow-x:hidden">
        <div style="background:#eee;padding: 10px;">
          <Card>
            <p slot="title">上游作业信息</p>
            <Tree :data="upstreamData" :load-data="loadUpData" :render="renderContent" expand-node></Tree>
          </Card>
        </div>
        <div style="background:#eee;padding: 10px">
          <Card>
            <p slot="title">下游作业信息</p>
            <Tree :data="downstreamData" :load-data="loadDownData" :render="renderContent" expand-node></Tree>
          </Card>
        </div>
      </div>
      <div slot="footer"></div>
    </Modal>

    <!-- 作业详情信息预览抽屉 -->
    <Drawer width="50" v-model="jobDrawer.show" title="作业详细信息" id="drawerClass">
      <div>
        <div class="jobdetailclass">
          <Card>
          <Row>
            <Col span="12">
              平台名:<span class="span">{{jobDetailBean.platform}} </span>
            </Col>
            <Col span="12">
              应用名:<span class="span"> {{jobDetailBean.systems}}</span>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              作业名:<span class="span">{{jobDetailBean.job}}</span>
            </Col>
            <Col span="12">
              作业最后执行状态:<span class="span">{{jobDetailBean.lastStatus}}</span>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              最后执行作业日期:<span class="span">{{jobDetailBean.jobDate}}</span>
            </Col>
            <Col span="12">
              执行节点:<span class="span">{{jobDetailBean.serverName}}</span>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              进入pending状态的时间:<span class="span">{{jobDetailBean.pendingTime}}</span>
            </Col>
            <Col span="12">
              进入dispatcherTime状态的时间:<span class="span"> {{jobDetailBean.dispatcherTime}}</span>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              开始执行时间:<span class="span">{{jobDetailBean.startTime}}</span>
            </Col>
            <Col span="12">
              结束时间:<span class="span">{{jobDetailBean.endTime}}</span>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              批次号:<span class="span">{{jobDetailBean.multiBatch}}</span>
            </Col>
            <Col span="12">
              触发方式:<span class="span">{{streamTypeList[jobDetailBean.streamType]}}</span>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              执行次数:<span class="span">{{jobDetailBean.numTimes}}</span>
            </Col>
            <Col span="12">
              当前重调次数:<span class="span">{{jobDetailBean.callAgainNum}}</span>
            </Col>
          </Row>
          </Card>
        </div>

        <!-- <Divider/> -->
        <div class="jobdetailclass">
          <Card>
          <Row>
            <Col span="12">
              作业中文名:<span class="span">{{jobDetailBean.jobName}}</span>
            </Col>
            <Col span="12">
              作业类型:<span class="span" v-text="jobDetailBean.jobType == 'D'? '日':jobDetailBean.jobType== 'C'? '定时':jobDetailBean.jobType== 'W'? '周': '月'">{{jobDetailBean.jobType}}</span>
            </Col>
          </Row>
           <Row>
             <Col span="12">
              最大重调次数:<span class="span">{{jobDetailBean.callAgainMaxNum}}</span>
            </Col>
            <!-- <Col span="12">
              偏移天数:<span class="span">{{jobDetailBean.offsetDay}}</span>
            </Col> -->
            <Col span="12">
              窗口执行时间:<span class="span">{{jobDetailBean.timewindow}}</span>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              虚作业:<span v-text="jobDetailBean.virtualEnable == 1?'是':'否'" class="span">{{jobDetailBean.virtualEnable}}</span>
            </Col>
            <Col span="12">
              优先级:<span class="span">{{jobDetailBean.priority}}</span>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              是否可用:<span v-text="jobDetailBean.enable ?'是':'否'" class="span">{{jobDetailBean.enable}}</span>
            </Col>
            <Col span="12">
              逻辑集群:<span class="span">{{dbControlList[jobDetailBean.dbControl]}}</span>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              是否检测时间:<span v-text="jobDetailBean.checkFrequency == 1?'是':'否'" class="span">{{jobDetailBean.checkFrequency}}</span>
            </Col>
            <Col span="12">
              检测是否采用时间触发:<span v-text="jobDetailBean.checkTimeTrigger == 1?'是':'否'" class="span">{{jobDetailBean.checkTimeTrigger}}</span>
            </Col>
          </Row>
          <!-- <Row>
            <Col span="12">
              是否启用streamFile检测触发:<span class="span">{{jobDetailBean.checkStreamSelf}}</span>
            </Col>
            <Col span="12">
              是否检测权重:<span class="span">{{jobDetailBean.checkWeight}}</span>
            </Col>
          </Row> -->
        </Card>
        </div>
      </div>
    </Drawer>
  </div>
</template>

<script>
import utils from '@/common/utils'
import store from '@/store/index'
import common from '@/mixins/common'
import { Circle } from 'iview'
import SAuthButton from '_c/s-auth-button'
const platforms = store.getters.getPlatforms
const RESOURCE_PATH = '/udsJob'

export default {
  components: {
    SAuthButton
  },
  mixins: [ common ],
  props: {
    transData: {
      type: Object,
      default: function () {
        return {}
      }
    }
  },
  data: function () {
    const validatePlatform = (rule, value, callback) => {
      if (!this.formBean.platform) {
        callback(new Error('请输入数据！'))
      } else {
        callback()
      }
    }
    return {
      jobDetailBean: {},
      jobDrawer: {
        show: false
      },
      upAndDownstream: {
        show: false
      },
      upstreamData: [],
      downstreamData: [],
      serverData: [],
      forceStartFormBean: {},
      loadingBatchFC: false,
      ctrlloadingBatchFCDisable: true,
      loadingBatchA: false,
      ctrlloadingBatchADisable: true,
      jobtypeList: { 'D': '日', 'C': '定时', 'W': '周', 'M': '月' },
      streamTypeList: {},
      dbControlList: {},
      loadingvitual: false,
      loadingdisable: false,
      res: { isAgree: true },
      confirm: {
        show: false,
        job: '',
        row: null
      },
      confirmDisable: {
        show: false,
        row: {}
      },
      confirmVi: {
        show: false,
        row: {}
      },
      confirmBatchActive: {
        show: false,
        isAgree: false
      },
      confirmBatchForceStart: {
        show: false,
        isAgree: false
      },
      platformData: [],
      dropdownItems: [{ val: 'invoke' }, { val: '补数' }, { val: '置虚或实' }, { val: '启用禁用' }, { val: 'kill作业' }], //, {val: '查看脚本'}
      systemData: [],
      jobstatusData: [
        { label: 'Done', value: 'Done' },
        { label: 'Pending', value: 'Pending' },
        { label: 'Running', value: 'Running' },
        { label: 'Failed', value: 'Failed' },
        { label: 'Ready', value: 'Ready' }
      ],
      formBean: {},
      udsJobStyle: {
        status: 'black'
      },
      request: {
        batchActive: {
          method: 'POST',
          url: '/udsjob/batchActive'
        },
        batchForceStart: {
          method: 'POST',
          url: '/udsjob/batchForceStart'
        },
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
          width: 36,
          align: 'center'
          // fixed: 'left'
        },
        {
          title: '平台名',
          ellipsis: true,
          width: 90,
          sortable: true,
          key: 'platform'
        },
        {
          title: '应用名',
          ellipsis: true,
          align: 'left',
          width: 90,
          sortable: true,
          className: 'uds-job-column',
          key: 'systems'
        },
        {
          title: '作业名',
          key: 'job',
          ellipsis: false,
          // tooltip: true,
          sortable: true,
          minWidth: 150,
          className: 'uds-job-column',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('a', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                  color: row.enable ? '' : 'red'
                },
                on: {
                  click: () => {
                    let id = row.job
                    if (this.transData.jobStatus) {
                      this.$emit('switch', Object.assign({}, { id: id, jobStatus: this.transData.jobStatus, statusName: 'jobdetail', jobData: row }, this.getPageParam())) // 提交form事件，在parent中显示form
                    } else {
                      this.$emit('switch', Object.assign({}, { id: id, statusName: 'jobdetail', jobData: row }, this.getPageParam())) // 提交form事件，在parent中显示form
                    }
                  }
                }
              }, row.job)
            ])
          }
        },
        {
          title: '执行节点',
          ellipsis: false,
          tooltip: true,
          minWidth: 110,
          maxWidth: 130,
          sortable: true,
          key: 'serverName',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('span', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                }
              }, row.serverName ? row.serverName : '')
            ])
          }
        },
        {
          title: '类型',
          ellipsis: true,
          tooltip: true,
          minWidth: 70,
          maxWidth: 80,
          // align: 'center',
          sortable: true,
          key: 'jobType',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('a', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  marginLeft: '5px',
                  color: '#3399ff'
                },
                on: {
                  click: () => {
                    let id = row.job
                    if (row.jobType == 'M' || row.jobType == 'W') {
                      this.$emit('switch', Object.assign({}, { id: id, statusName: 'frequency', jobData: row }, this.getPageParam()))
                    } else if (row.jobType == 'C') {
                      this.$emit('switch', Object.assign({}, { id: id, statusName: 'trigger', jobData: row }, this.getPageParam()))
                    } else {
                      this.$Message.warning('日作业不存在定时或频度信息。')
                    }
                  }
                }
              }, this.jobtypeList[row.jobType])
            ])
          }
        },
        {
          title: '状态',
          ellipsis: true,
          tooltip: true,
          minWidth: 80,
          maxWidth: 100,
          sortable: true,
          key: 'lastStatus'
        },
        {
          title: '开始时间',
          ellipsis: false,
          tooltip: false,
          width: 170,
          sortable: true,
          key: 'startTime'
          /* render: (h, {column, index, row}) => {
            let startArr = ['', '']
            if (row.startTime) {
              startArr = row.startTime.split(' ')
            }
            return h('div', [
              h('div', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                }
              }, startArr[0]),
              h('div', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                }
              }, startArr[1])])
          } */
        },
        {
          title: '结束时间',
          ellipsis: false,
          tooltip: false,
          width: 170,
          sortable: true,
          key: 'endTime',
          render: (h, { column, index, row }) => {
            let endArr = ['', '']
            if (row.endTime) {
              endArr = row.endTime.split(' ')
            }
            return h('div', [
              h('div', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                }
              }, row.lastStatus === 'Running' ? '' : row.endTime)])
          }
        },
        /* render: (h, {column, index, row}) => {
            let endArr = ['', '']
            if (row.endTime) {
              endArr = row.endTime.split(' ')
            }
            return h('div', [
              h('div', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                }
              }, row.lastStatus === 'Running' ? '' : endArr[0]),
              h('div', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                }
              }, row.lastStatus === 'Running' ? '' : endArr[1])])
          }
        }, */
        {
          title: 'TXDate',
          ellipsis: false,
          tooltip: true,
          width: 100,
          sortable: true,
          key: 'jobDate',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('span', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                }
              }, row.jobDate.substring(0, 10))
            ])
          }
        },
        {
          title: '虚作业',
          ellipsis: false,
          tooltip: false,
          align: 'center',
          width: 70,
          key: 'virtualEnable',
          render: (h, { column, index, row }) => {
            let enableStatus = row.virtualEnable == 1 ? '是' : '否'
            let colors = row.virtualEnable == 1 ? 'red' : 'green'
            return h('div', [
              h('span', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  marginRight: '5px',
                  color: colors
                }
              }, enableStatus)
            ])
          }
        },
        {
          title: '上下游',
          width: 60,
          align: 'center',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('Button', {
                props: {
                  // icon: 'shuffle'
                  icon: 'md-swap'
                },
                style: {
                  margin: '0px',
                  fontSize: '20px',
                  border: 0,
                  padding: '4px 8px',
                  color: '#03399b',
                  backgroundColor: 'transparent'
                },
                on: {
                  click: () => {
                    this.upAndDownstream.show = true
                    this.getUpstreamData(row.job, null, row)
                    this.getDownstreamData(row.job, null, row)
                  }
                }
              }, '')
            ])
          }
        },
        {
          title: '依赖',
          width: 50,
          align: 'center',
          // fixed: 'right',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('Button', {
                props: {
                  icon: 'md-arrow-back'
                },
                style: {
                  margin: '0px',
                  fontSize: '20px',
                  border: 0,
                  padding: '4px 8px',
                  color: '#03399b',
                  backgroundColor: 'transparent'
                },
                on: {
                  click: () => {
                    let id = row.job
                    if (this.transData.jobStatus) {
                      this.$emit('switch', Object.assign({}, { id: id, jobStatus: this.transData.jobStatus, jobData: row, statusName: 'dep', curTab: 'Table' }, this.getPageParam())) // 提交form事件，在parent中显示form
                    } else {
                      this.$emit('switch', Object.assign({}, { id: id, jobData: row, statusName: 'dep', curTab: 'Table' }, this.getPageParam())) // 提交form事件，在parent中显示form
                    }
                  }
                }
              }, '')
            ])
          }
        },
        // {
        //   title: '作业画像',
        //   width: 70,
        //   align: 'center',
        //   render: (h, { column, index, row }) => {
        //     return h('div', [
        //       h('Button', {
        //         props: {
        //           icon: 'ios-image'
        //         },
        //         style: {
        //           // marginRight: '5px'
        //           margin: '0px',
        //           fontSize: '20px',
        //           border: 0,
        //           padding: '4px 8px',
        //           color: '#03399b',
        //           background: 'transparent'
        //         },
        //         on: {
        //           click: () => {
        //             let id = row.job
        //             if (this.transData.jobStatus) {
        //               this.$emit('switch', Object.assign({}, { id: id, jobStatus: this.transData.jobStatus, statusName: 'img', jobData: row }, this.getPageParam())) // 提交form事件，在parent中显示form
        //             } else {
        //               this.$emit('switch', Object.assign({}, { id: id, statusName: 'img', jobData: row }, this.getPageParam())) // 提交form事件，在parent中显示form
        //             }
        //           }
        //         }
        //       }, '')
        //     ])
        //   }
        // },
        {
          title: '运行记录',
          width: 80,
          align: 'center',
          // fixed: 'right',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('Button', {
                props: {
                  icon: 'md-reorder'
                },
                style: {
                  margin: '0px',
                  fontSize: '20px',
                  border: 0,
                  padding: '4px 8px',
                  color: '#03399b',
                  backgroundColor: 'transparent'
                },
                on: {
                  click: () => {
                    let id = row.job
                    if (this.transData.jobStatus) {
                      this.$emit('switch', Object.assign({}, { id: id, jobStatus: this.transData.jobStatus, jobData: row, statusName: 'jobRecord', curTab: 'Table' }, this.getPageParam())) // 提交form事件，在parent中显示form
                    } else {
                      this.$emit('switch', Object.assign({}, { id: id, jobData: row, statusName: 'jobRecord', curTab: 'Table' }, this.getPageParam())) // 提交form事件，在parent中显示form
                    }
                  }
                }
              }, '')
            ])
          }
        },
        {
          title: '日志',
          width: 50,
          align: 'center',
          // fixed: 'right',
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
                    let id = row.job
                    if (this.transData.jobStatus) {
                      this.$emit('switch', Object.assign({}, { id: id, jobStatus: this.transData.jobStatus, statusName: 'log', jobData: row }, this.getPageParam())) // 提交form事件，在parent中显示form
                    } else {
                      this.$emit('switch', Object.assign({}, { id: id, statusName: 'log', jobData: row }, this.getPageParam())) // 提交form事件，在parent中显示form
                    }
                  }
                }
              }, '')
            ])
          }
        },
        /* {
          title: '上游',
          width: 36,
          // fixed: 'right',
          render: (h, {column, index, row}) => {
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
                    let id = row.job
                    if (this.transData.jobStatus) {
                      this.$emit('switch', Object.assign({}, {id: id, jobStatus: this.transData.jobStatus, statusName: 'upjobs', jobData: row}, this.getPageParam())) // 提交form事件，在parent中显示form
                    } else {
                      this.$emit('switch', Object.assign({}, {id: id, statusName: 'upjobs', jobData: row}, this.getPageParam())) // 提交form事件，在parent中显示form
                    }
                  }
                }
              }, '查看')
            ])
          }
        }, */
        /* {
          title: '下游',
          width: 50,
          // fixed: 'right',
          render: (h, {column, index, row}) => {
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
                    let id = row.job
                    if (this.transData.jobStatus) {
                      this.$emit('switch', Object.assign({}, {id: id, jobStatus: this.transData.jobStatus, statusName: 'downjobs', jobData: row}, this.getPageParam())) // 提交form事件，在parent中显示form
                    } else {
                      this.$emit('switch', Object.assign({}, {id: id, statusName: 'downjobs', jobData: row}, this.getPageParam())) // 提交form事件，在parent中显示form
                    }
                  }
                }
              }, '查看')
            ])
          }
        }, */
        /* {
          title: '状态',
          width: 36,
          //fixed: 'right',
          render: (h, {column,index, row}) => {
            return h('div', [
              h('a', {
                props: {
                  size: 'small',
                  type: 'info',
                },
                style: {
                  //marginRight: '5px'
                },
                on: {
                  click :() => {
                    let id = row.job;
                    this.$emit('switch', Object.assign({},{id: id,statusName: "status",jobData: row},this.getPageParam())); //提交form事件，在parent中显示form
                  }
                }
              }, '修改')
            ])
          }
        }, */
        {
          title: '属性',
          width: 50,
          align: 'center',
          // fixed: 'right',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('Button', {
                props: {
                  icon: 'md-create'
                },
                style: {
                  // marginRight: '5px'
                  margin: '0px',
                  fontSize: '16px',
                  border: 0,
                  padding: '4px 8px',
                  color: '#03399b',
                  background: 'transparent'
                },
                on: {
                  click: () => {
                    if (!row.enable) {
                      this.$Message.error({
                        content: '当前作业状态为禁用,不能修改作业属性',
                        duration: 10,
                        closable: true
                      })
                      return
                    }
                    let id = row.job
                    if (!(row.lastStatus === 'Done' ||
                        row.lastStatus === 'Failed' ||
                        row.lastStatus === 'Ready')
                    ) {
                      this.$Message.error({
                        content: '当前作业状态不为Done或Failed或Ready,不能修改作业属性',
                        duration: 15,
                        closable: true
                      })
                      return
                    }
                    if (this.transData.jobStatus) {
                      this.$emit('switch', Object.assign({}, { id: id, jobStatus: this.transData.jobStatus, statusName: 'attr', jobData: row }, this.getPageParam())) // 提交form事件，在parent中显示form
                    } else {
                      this.$emit('switch', Object.assign({}, { id: id, statusName: 'attr', jobData: row }, this.getPageParam())) // 提交form事件，在parent中显示form
                    }
                  }
                }
              }, '')
            ])
          }
        },
        {
          title: '操作',
          width: 60,
          align: 'center',
          // fixed: 'right',
          render: (h, { column, index, row }) => {
            return h('Dropdown', {
              props: {
                trigger: 'click',
                placement: 'bottom-end',
                transfer: true
              },
              style: {
                paddingRight: '3px'
              },
              on: {
                'on-click': (value) => {
                  let flag = value
                  this.execOpt(flag, row, index, column)
                }
              }
            }, [
              h('a', [
                /* h('span', '更多'), */
                h('Button', {
                  props: {
                    icon: 'ios-more'
                  },
                  style: {
                  // marginRight: '5px'
                    margin: '0px',
                    fontSize: '20px',
                    border: 0,
                    padding: '4px 8px',
                    color: '#03399b',
                    background: 'transparent'
                  }
                })
              ]),
              h('DropdownMenu', {
                slot: 'list'
              },
              this.dropdownItems.map(function (data) {
                return h('DropdownItem', {
                  props: {
                    name: data.val

                  },
                  style: {
                    color: '#3399ff'
                    // paddingLeft: '2px',
                    // paddingRight: '3px'
                  }
                }, data.val)
              })
              )
            ])
          }
        }
      ],
      // gridData: [{platform: "BDP", system: "DLA", job: "BDP_DLA_TEST",serverName: "SERVER1", jobType:"D", lastStatus:"Pending",
      // startTime: "2019-06-31 8:00:00", endTime:  "2019-06-31 8:00:00",jobDate: "2019-06-31"}],
      gridData: [],
      selection: [],
      page: {
        current: 1,
        total: 10,
        size: 10,
        opt: [10, 50, 100]
      },
      isRequirePlatform: true,
      ruleValidate: {
        // platform: [
        //   { required: true, message: '请输入数据！', trigger: 'change' }
        // ]
        platform: [
          { validator: validatePlatform, trigger: 'change' }
        ]
      },
      forceStartRuleValidate: {
        multiBatch: [{
          required: true,
          message: '请输入数据！'
        }],
        jobDate: [{
          required: true,
          message: '请输入数据！'
        }],
        serverName: [{
          required: true,
          message: '请输入数据！'
        }]
      },
      fromto: '1'
    }
  },
  methods: {
    /**
     * 初始化
     **/
    init () {
      if (this.transData.currentPage) {
        this.formBean = this.transData.formBean
        this.page.current = this.transData.currentPage
        this.page.size = this.transData.pageSize
      }

      if (window.sessionStorage.getItem('errorlist_udsjob') || this.transData.jobStatus) {
        this.fromto = '0'
        // this.transData.jobStatus
        if (window.sessionStorage.getItem('errorlist_udsjob') == '0') {
          this.formBean.enable = 0
        } else {
          this.formBean.enable = 1
          if (this.transData.formBean && (this.transData.formBean.lastStatus ||
          this.transData.formBean.lastStatus.length == 0)) { this.formBean.lastStatus = this.transData.formBean.lastStatus } else { this.formBean.lastStatus = window.sessionStorage.getItem('errorlist_udsjob') }
          if (this.transData.jobData) {
            this.formBean.platform = this.transData.jobData.platform
          }
          // if (this.transData.jobData.platform) {
          //  this.formBean.platform = this.transData.jobData.platform;
          // }
        }
      }
      /* if (!this.formBean.platform) {
        console.log(this.formBean.platform)
        if (!platforms.includes('ROLE_ADMIN')) {
          this.formBean.platform = platforms[0]
          this.querySystem()
        }
      } */
      // console.log(this.transData.jobStatus);
      // this.queryPlatform();
      // this.search();
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
      if (
        window.sessionStorage.getItem('errorlist_udsjob') ||
                this.transData.jobStatus
      ) {
        let prevTab = ''
        let backParam = {}
        if (this.transData.statusName === 'table') {
          backParam.statusName = this.transData.prevTab
        } else {
          backParam.statusName = this.transData.statusName
        }
        backParam.udsjobsearch = '1'
        this.$emit('switch', Object.assign({}, backParam))
      } else {
        this.$emit(
          'switch',
          Object.assign({}, { statusName: 'table' })
        )
      }
      this.$refs.udsjobForm.validate(valid => {
        if (!valid && this.fromto == '1') return
        let params = {}
        Object.assign(params, this.formBean)
        params.currentPage = this.page.current
        params.pageSize = this.page.size
        for (let key in params) {
          if (key === 'job') {
            if (params[key] && params[key] !== '') {
              params[key] = '%' + params[key].trim().toUpperCase() + '%'
            } else {
              delete params[key]
            }
          }
          if (key === 'systems' && params[key]) {
            params[key] = params[key].trim().toUpperCase()
          }
        }

        let httpConfig = {
          method: 'GET',
          url: RESOURCE_PATH + '/selectAll',
          params: params
        }
        this.$ajax(httpConfig)
          .then(resp => {
            if (resp.data.total && resp.data.total > 0) {
              if (resp.data.rows && resp.data.rows.length === 0) {
                this.page.current = 1
                this.search()
              }
            }
            this.gridData = resp.data.rows
            this.page.total = resp.data.total
          })
      })
    },
    findServer () {
      let param = {}
      param.location = 'all'
      let loadConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/getServer',
        params: param
      }
      this.serverData = []
      this.$ajax(loadConfig)
        .then(resp => {
          // let sData = []
          resp.data.forEach(data => {
            let tmp = {}
            tmp.value = data.serverName
            tmp.label = data.serverName
            // sData.push(tmp)
            this.serverData.push(tmp)
          })
        })
    },
    execOpt (flag, row, index, column) {
      if (flag == 'invoke') {
        let id = row.job
        if (!row.enable) {
          this.$Message.warning({
            content: '该作业已被禁用，无法invoke',
            duration: 5,
            closable: true
          })
          return
        }
        if (this.transData.jobStatus) {
          this.$emit('switch', Object.assign({}, { id: id, jobStatus: this.transData.jobStatus, statusName: 'invoke', jobData: row }, this.getPageParam()))
        } else {
          this.$emit('switch', Object.assign({}, { id: id, statusName: 'invoke', jobData: row }, this.getPageParam()))
        }
      } else if (flag == '补数') {
        let id = row.job
        if (!row.enable) {
          this.$Message.warning({
            content: '该作业已被禁用，无法补数',
            duration: 5,
            closable: true
          })
          return
        }
        if (this.transData.jobStatus) {
          this.$emit('switch', Object.assign({}, { id: id, jobStatus: this.transData.jobStatus, statusName: 'forcestart', jobData: row }, this.getPageParam()))
        } else {
          this.$emit('switch', Object.assign({}, { id: id, statusName: 'forcestart', jobData: row }, this.getPageParam()))
        }
      } else if (flag == '置虚或实') {
        if (row.virtualEnable == 1) {
          let id = row.job
          this.changeJobVEnable({ virtualEnable: row.virtualEnable, job: id }, index, row)
        } else {
          this.confirmV(row, index)
        }
      } else if (flag == '启用禁用') {
        if (row.lastStatus == 'Done' || row.lastStatus == 'Ready' || row.lastStatus == 'Failed') {
          if (row.enable) {
            this.confirmD(row, index)
          } else {
            let id = row.job
            let enable = row.enable ? 0 : 1
            this.changeJobEnable({ enable: enable, job: id }, index, row)
          }
        } else {
          this.$Message.warning({
            content: '当前该作业状态:' + row.lastStatus + '无法禁用',
            duration: 3,
            closable: true
          })
        }
      } else if (flag == '查看脚本') {
        let id = row.job
        if (this.transData.jobStatus) {
          this.$emit('switch', Object.assign({}, { id: id, jobStatus: this.transData.jobStatus, statusName: 'script', jobData: row }, this.getPageParam()))
        } else {
          this.$emit('switch', Object.assign({}, { id: id, statusName: 'script', jobData: row }, this.getPageParam()))
        }
      } else if (flag == 'kill作业') {
        this.confirmBtn(row)
      }
    },
    handleTreeData (data) {
      data.forEach((data, index) => {
        data.expand = false
        let udsjob = data.udsJob // lastStatus jobDate jobType
        let depStatus = udsjob.depIsEnable === 1 ? '启用' : '禁用'
        data.title = data.title + ' | 状态:' + udsjob.lastStatus + ' | TXDATE:' + udsjob.jobDate + ' | 类型:' + udsjob.jobType + ' | 依赖:' + depStatus
        if (data.children.length == 0) {
          data.expand = false
          data.children = []
        } else {
          this.handleTreeData(data.children)
        }
      })
    },
    renderContent (h, { root, node, data }) {
      let col = '#515a6e'
      if (data.udsJob) {
        if (!data.udsJob.enable) { // 作业禁用
          col = 'red'
        }
      }
      return h('span', {
        style: {
          display: 'inline-block',
          width: '100%'
        }
      }, [
        h('span', [
          h('a', {
            style: {
              color: col
            },
            class: {
              treeTitle: true
            },
            on: {
              click: () => {
                let job = data.id ? data.id : data.title
                this.jobDetailBean = {}
                let params = {}
                params.job = job
                params.platform = data.platform
                params.systems = data.systems
                let httpConfig = {
                  method: 'GET',
                  url: RESOURCE_PATH + '/selectJobDetail',
                  params: params
                }
                this.$ajax(httpConfig)
                  .then(resp => {
                    this.jobDetailBean = resp.data.rows
                    this.jobDrawer.show = true
                  })
              }
            }
          }, data.title)
        ])
      ])
    },
    // 上游
    getUpstreamData (job, level, row) {
      if (!level) {
        this.upstreamData = []
        let obj = {
          title: job,
          loading: false,
          expand: false,
          children: [],
          platform: row.platform,
          systems: row.systems
        }
        this.upstreamData.push(obj)
      }
      let params = {}
      params.job = job
      params.level = level || '1'
      params.platform = row.platform
      params.systems = row.systems
      let loadConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/allUpjobList',
        params: params
      }
      this.$ajax(loadConfig)
        .then(resp => {
          // console.log('上游')
          // console.log(resp.data)
          if (resp.status && resp.status === 200) {
            let tmpUpobsTree = this.upstreamData
            tmpUpobsTree[0].expand = true
            // let treeData = []
            if (resp.data.length > 0) {
              resp.data.forEach((data, index) => {
                resp.data[index].loading = false
                let udsjob = data.udsJob // lastStatus jobDate jobType
                let depStatus = udsjob.depIsEnable === 1 ? '启用' : '禁用'
                resp.data[index].title = data.title + ' | 状态:' + udsjob.lastStatus + ' | TXDATE:' + udsjob.jobDate + ' | 类型:' + udsjob.jobType + ' | 依赖:' + depStatus
                if (data.children.length == 0) {
                  data.children = []
                  data.expand = false
                } else {
                  data.expand = false
                  this.handleTreeData(data.children)
                }
              })
              tmpUpobsTree[0].children = resp.data
              this.upstreamData = tmpUpobsTree
            }
          }
        })
    },
    loadUpData (item, callback) {
      let params = {}
      params.job = item.id ? item.id : item.title
      params.level = '1'
      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/allUpjobList',
        params: params
      }
      this.$ajax(httpConfig)
        .then(resp => {
          let data = []
          let tmp = {}
          if (resp.data.length == 0) {
            this.$Message.info('该作业已经没有上游了')
          }
          resp.data.forEach(node => {
            let udsjob = node.udsJob // lastStatus jobDate jobType
            tmp = {}
            let depStatus = udsjob.depIsEnable === 1 ? '启用' : '禁用'
            tmp.title = node.title + ' | 状态:' + udsjob.lastStatus + ' | TXDATE:' + udsjob.jobDate + ' | 类型:' + udsjob.jobType + ' | 依赖:' + depStatus
            // tmp.title = node.id
            tmp.id = node.id
            tmp.loading = false
            tmp.children = []
            data.push(tmp)
          })
          callback(data)
        })
    },
    // 下游
    getDownstreamData (job, level, row) {
      if (!level) {
        this.downstreamData = []
        let obj = {
          title: job,
          loading: false,
          expand: false,
          children: [],
          platform: row.platform,
          systems: row.systems
        }
        this.downstreamData.push(obj)
      }

      let params = {}
      params.job = job
      params.level = level || '1'
      params.platform = row.platform
      params.systems = row.systems
      let loadConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/allDownjobList',
        params: params
      }
      this.$ajax(loadConfig)
        .then(resp => {
          // console.log('下游')
          // console.log(resp.data)
          if (resp.status && resp.status === 200) {
            let tmpDownobsTree = this.downstreamData
            tmpDownobsTree[0].expand = true
            // let treeData = []
            if (resp.data.length > 0) {
              resp.data.forEach((data, index) => {
                resp.data[index].loading = false
                let udsjob = data.udsJob // lastStatus jobDate jobType
                let depStatus = udsjob.depIsEnable === 1 ? '启用' : '禁用'
                resp.data[index].title = data.title + ' | 状态:' + udsjob.lastStatus + ' | TXDATE:' + udsjob.jobDate + ' | 类型:' + udsjob.jobType + ' | 依赖:' + depStatus
                if (data.children.length == 0) {
                  data.children = []
                  data.expand = false
                } else {
                  data.expand = false
                  this.handleTreeData(data.children)
                }
              })
              tmpDownobsTree[0].children = resp.data
              this.downstreamData = tmpDownobsTree
            }
          }
        })
    },
    // renderDownContent
    loadDownData (item, callback) {
      let params = {}
      params.job = item.id ? item.id : item.title
      params.level = '1'
      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/allDownjobList',
        params: params
      }
      this.$ajax(httpConfig)
        .then(resp => {
          let data = []
          let tmp = {}
          if (resp.data.length == 0) {
            this.$Message.info('该作业已经没有下游了')
          }
          resp.data.forEach(node => {
            let udsjob = node.udsJob // lastStatus jobDate jobType
            let depStatus = udsjob.depIsEnable === 1 ? '启用' : '禁用'
            tmp = {}
            tmp.title = node.title + ' | 状态:' + udsjob.lastStatus + ' | TXDATE:' + udsjob.jobDate + ' | 类型:' + udsjob.jobType + ' | 依赖:' + depStatus
            // tmp.title = node.id
            tmp.id = node.id
            tmp.loading = false
            tmp.children = []
            data.push(tmp)
          })
          callback(data)
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
      const ids = Array.from(this.selection, e => e.platform)
      ids.forEach(id => {
        this.request.delReq.url = RESOURCE_PATH + '/' + id
        this.request.delReq.method = 'DELETE'
        this.$ajax(this.request.delReq)
          .then(resp => {
            this.search()
          })
      })
    },
    /**
    *改变作业是否enable状态
    **/
    changeJobEnable (params, index, row) {
      // console.log(this.$store.getters.getBreadcrumb);
      // params.authps = row.platform + row.system
      params.platform = row.platform
      params.systems = row.systems
      params.job = row.job
      let httpConfig = {
        method: 'POST',
        url: '/udsJobConfig/setEnable',
        data: params
      }
      this.$ajax(httpConfig)
        .then(resp => {
          this.loadingdisable = false
          if (resp.status && resp.status == 200) {
            this.gridData[index].enable = params.enable
            this.confirmDisable.show = false
          }
        })
    },
    /**
    *改变作业是否虚作业状态
    **/
    changeJobVEnable (params, index, row) {
      if (params.virtualEnable == 1) {
        params.virtualEnable = 0
      } else if (params.virtualEnable == 0) {
        params.virtualEnable = 1
      }
      // params.authps = row.platform + row.system
      params.platform = row.platform
      params.systems = row.systems
      params.job = row.job
      params.virtual = row.virtualEnable
      let httpConfig = {
        method: 'POST',
        url: '/udsJobConfig/setVirtual',
        data: params
      }
      // httpConfig.method = 'PUT'
      // httpConfig.url = '/udsjobconfig/' + params.job
      this.$ajax(httpConfig)
        .then(resp => {
          this.loadingvitual = false
          if (resp.status && resp.status == 200) {
            this.gridData[index].virtualEnable = params.virtualEnable
            this.confirmVi.show = false
          }
        })
    },
    /**
     * 数据复选事件
     **/
    select (selection) {
      this.selection = selection
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
    /**
     * 列排序
     **/
    changeSort (column) {
      let classcon = window.event.currentTarget.className
      let sort, order
      if (classcon.indexOf('ivu-icon-md-arrow-dropup') > -1) {
        order = 'ASC'
      } else if (classcon.indexOf('ivu-icon-md-arrow-dropdown') > -1) {
        order = 'DESC'
      }
      // console.log("column,key,order"+ column.key );
      this.formBean.order = order
      this.formBean.sort = column.key
      this.search()
    },
    // harrier getPlatformList
    getPlatformList () {
      // let loadConfig = {
      //   method: 'GET',
      //   url: '/udsSystem/listQuery'
      // }
      // this.$ajax(loadConfig)
      //   .then(resp => {
      //     let platformList = []
      //     resp.data.forEach(data => {
      //       platformList.push(data.platform)
      //       // this.formBean.platform = "UDS";
      //     })
      //     platformList = Array.from(new Set(platformList))
      //     platformList.forEach(data => {
      //       let tmp = {}
      //       tmp.value = data
      //       tmp.label = data
      //       this.platformData.push(tmp)
      //       // this.formBean.platform = "UDS";
      //     })
      //     this.search()
      //   })
      let platformList = this.$store.getters.getUserPlatform
      platformList.forEach(data => {
        let tmp = {}
        tmp.value = data
        tmp.label = data
        this.platformData.push(tmp)
      })
      this.search()
    },
    // harrier getSystemList
    getSystemList () {
      // let loadConfig = {
      //   method: 'GET',
      //   url: '/udsSystem/listQuery'
      // }
      // this.systemData = []
      // this.$refs.refsystem.setQuery('')
      // if (this.formBean.platform) {
      //   this.$ajax(loadConfig)
      //     .then(resp => {
      //       resp.data.forEach(data => {
      //         if (this.formBean.platform === data.platform && data.systems !== '*') {
      //           let tmp = {}
      //           tmp.value = data.systems
      //           tmp.label = data.systems
      //           this.systemData.push(tmp)
      //         }
      //       })
      //     })
      // }
      this.systemData = []
      this.$refs.refsystem.setQuery('')
      if (this.formBean.platform) {
        let userSystemList = this.$store.getters.getUserSystem
        let userSystem = userSystemList[this.formBean.platform]
        userSystem.forEach(data => {
          let tmp = {}
          tmp.value = data
          tmp.label = data
          this.systemData.push(tmp)
        })
      }
    },
    // harrier test end
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
            // this.formBean.platform = "UDS";
          })
          this.search()
          this.queryJobstatus()
          this.queryjobtypeList()
          this.queryDBControList()
          this.queryStreamTypeList()
        })
    },
    querySystem () {
      let loadConfig = {
        method: 'GET',
        url: '/msys/getSysList',
        params: { platform: this.formBean.platform }
      }
      // modify by jcjin 20200528 for systemData返回值由对象调整为集合
      //    this.$ajax(loadConfig)
      //      .then(resp => {
      //        if (this.transData.formBean && this.transData.formBean.system) {
      //        this.formBean.system = this.transData.formBean.system;
      //      } else {
      //        this.formBean.system = "";
      //      }
      //      this.systemData = resp.data;
      //
      //    })
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
    queryJobstatus () {
      let loadConfig = {
        method: 'GET',
        url: '/dictionary',
        params: { dicCode: 'm_job_status' }
      }
      this.$ajax(loadConfig)
        .then(resp => {
          this.jobstatusData = []
          resp.data.rows.forEach(data => {
            let tmp = {}
            tmp.value = data.dicName
            tmp.label = data.dicName
            this.jobstatusData.push(tmp)
            // this.formBean.platform = "UDS";
          })
        })
    },
    filterMethod (value, option) {
      return option.toUpperCase().indexOf(value.toUpperCase()) !== -1
    },
    getPageParam () {
      return { formBean: this.formBean,
        currentPage: this.page.current,
        pageSize: this.page.size }
    },
    confirmBtn (row) {
      if (!(row.lastStatus == 'Running')) {
        this.$Message.error({
          content: '当前作业状态不为Running',
          duration: 15,
          closable: true
        })
        return
      }
      this.confirm.job = row.job
      this.confirm.row = row
      this.confirm.show = true
      // let id = row.job
      // this.$emit('switch', Object.assign({}, { id: id, statusName: 'killjob', jobData: row }, this.getPageParam()))
    },
    confirmV (row, index) {
      this.confirmVi.show = true
      this.confirmVi.row = row
      this.confirmVi.index = index
    },
    vitualJob () {
      if (!this.res.isAgree == true) {
        this.$Message.warning({ content: '请同意 我承担该操作的一切后果!' })
        return
      }
      let id = this.confirmVi.row.job
      this.loadingvitual = true
      this.changeJobVEnable({ virtualEnable: this.confirmVi.row.virtualEnable, job: id }, this.confirmVi.index, this.confirmVi.row)
    },
    vitualCancel () {
      this.confirmVi.show = false
    },
    confirmD (row, index) {
      this.confirmDisable.show = true
      this.confirmDisable.row = row
      this.confirmDisable.index = index
    },
    disableJob () {
      if (!this.res.isAgree == true) {
        this.$Message.warning({ content: '请同意 我承担该操作的一切后果!' })
        return
      }
      let id = this.confirmDisable.row.job
      let enable = this.confirmDisable.row.enable ? 0 : 1
      this.loadingdisable = true
      this.changeJobEnable({ isAgree: this.res.isAgree, enable: enable, job: id }, this.confirmDisable.index, this.confirmDisable.row)
    },
    disableCancel () {
      this.confirmDisable.show = false
    },
    confirmCancel () {
      this.confirm.job = ''
      this.confirm.row = null
      this.confirm.show = false
    },
    confirmBatchForceStartCancel () {
      this.confirmBatchForceStart.show = false
    },
    batchForceStartConfirm () {
      // this.findServer()
      this.confirmBatchForceStart.show = true
    },
    batchForceStart () {
      this.$refs.confirmBatchForceStartForm.validate(valid => {
        if (!valid) return
        if (!this.confirmBatchForceStart.isAgree) {
          this.$Message.warning({ content: '请同意 我承担该操作的一切后果!' })
          return
        }
        const selectSystems = Array.from(this.selection, e => e.platform + e.system)
        if (selectSystems.length < 1) {
          this.$Message.warning('请至少选择一行')
          return
        }
        let authSystems = store.getters.getSystems
        let isExist = true
        if (authSystems.indexOf('ROLE_ADMIN') == -1) {
          // 非系统管理员
          for (let i = 0; i < selectSystems.length; i++) {
            for (let j = 0; j < authSystems.length; j++) {
              if (selectSystems[i] == authSystems[j]) {
                isExist = false
              }
            }
            if (isExist) {
              this.$Message.warning('该用户没有' + selectSystems[i] + '权限')
              return true
            }
          }
        }
        const ids = Array.from(this.selection, e => e.job)
        let jobArr = ''
        ids.forEach(id => {
          if (jobArr == '') {
            jobArr = id
          } else {
            jobArr = jobArr + '/' + id
          }
        })
        let params = {}
        Object.assign(params, this.forceStartFormBean)
        params.datalist = this.selection
        params.reqparams = {}
        params.requesttype = 'lo'
        params.udsjob = this.forceStartFormBean
        params.reqparams = this.forceStartFormBean
        params.reqparams.jobDate = utils.fmtDate(this.forceStartFormBean.jobDate, 'yyyyMMdd')
        params.reqparams.serverName = this.forceStartFormBean.serverName
        params.reqparams.jobArr = jobArr
        params.authps = selectSystems[0]// this.formBean.platform + this.formBean.system;
        // alert(params.authps);
        this.loadingBatchFC = true
        let httpConfig = {
          method: 'POST',
          url: RESOURCE_PATH + '/batchForcestart',
          data: params
        }
        this.$ajax(httpConfig)
          .then(resp => {
            setTimeout(() => {
              this.loading = false
            }, 2000)
            if (resp.data.returnCode != null &&
              resp.data.returnCode !== 'undefined' &&
              resp.data.returnCode === 'fail') {
              this.loadingBatchFC = false
              // this.ctrlloadingBatchFCDisable = true
              return
            }
            if (resp.data === 'success') {
              this.ctrlloadingBatchFCDisable = true
              this.ctrlloadingBatchADisable = true
              this.loadingBatchFC = false
              this.ctrlloadingBatchFCDisable = true
              this.confirmBatchForceStart.show = false
              this.search()
            }
          })
      })
    },

    confirmBatchActiveCancel () {
      this.confirmBatchActive.show = false
    },
    batchActiveConfirm () {
      this.confirmBatchActive.show = true
    },
    batchActive () {
      if (!this.confirmBatchActive.isAgree) {
        this.$Message.warning({ content: '请同意 我承担该操作的一切后果!' })
        return
      }
      const selectSystems = Array.from(this.selection, e => e.platform + e.system)
      if (selectSystems.length < 1) {
        this.$Message.warning('请至少选择一行')
        return
      }
      let authSystems = store.getters.getSystems
      let isExist = true
      if (authSystems.indexOf('ROLE_ADMIN') == -1) {
        // 非系统管理员
        for (let i = 0; i < selectSystems.length; i++) {
          for (let j = 0; j < authSystems.length; j++) {
            if (selectSystems[i] == authSystems[j]) {
              isExist = false
            }
          }
          if (isExist) {
            this.$Message.warning('该用户没有' + selectSystems[i] + '权限')
            return true
          }
        }
      }
      const ids = Array.from(this.selection, e => e.job)
      let jobArr = ''
      ids.forEach(id => {
        if (jobArr == '') {
          jobArr = id
        } else {
          jobArr = jobArr + '/' + id
        }
      })
      let params = {}
      Object.assign(params, this.formBean)
      params.jobArr = jobArr
      params.authps = selectSystems[0]// this.formBean.platform + this.formBean.system;
      this.loadingBatchA = true
      // alert(params.authps);
      let httpConfig = {
        method: 'POST',
        url: RESOURCE_PATH + '/batchActive',
        data: params
      }
      this.$ajax(httpConfig)
        .then(resp => {
          this.ctrlloadingBatchFCDisable = true
          this.ctrlloadingBatchADisable = true
          this.confirmBatchActive.show = false
          this.loadingBatchA = false
          this.search()
        })
    },
    killJob () {
      let row = this.confirm.row
      let params = {}
      params.platform = row.platform
      params.systems = row.systems
      params.job = row.job
      params.jobdate = row.jobDate
      params.multibatch = row.multiBatch

      // let row = this.confirm.row
      // let params = this.confirm.row
      // params.authps = row.platform + row.system
      // console.log(this.$store.getters.getBreadcrumb);
      let httpConfig = {
        method: 'POST',
        url: RESOURCE_PATH + '/killJob',
        data: params
      }
      this.$ajax(httpConfig)
        .then(resp => {
          if (resp.status && resp.status == 200) {
            if (resp.data === 1) {
              this.confirm.show = false
              this.$Message.success({
                content: 'kill作业：' + row.job + '成功！',
                duration: 15,
                closable: true
              })
              this.search()
            } else {
              this.$Message.error({
                content: 'kill作业：' + row.job + '失败！',
                duration: 15,
                closable: true
              })
            }
            this.confirm.show = false
          }
        })
    },
    queryjobtypeList () {
      let loadConfig = {
        method: 'GET',
        url: '/dictionary',
        params: { dicCode: 'jobtype' }
      }
      this.$ajax(loadConfig)
        .then(resp => {
          this.jobtypeList = {}
          resp.data.rows.forEach(data => {
            this.jobtypeList[data.dicValue] = data.dicName
          })
        })
    },
    queryDBControList () {
      let loadConfig = {
        method: 'GET',
        url: '/dictionary',
        params: { dicCode: 'location' }
      }
      this.$ajax(loadConfig)
        .then(resp => {
          this.dbControlList = {}
          resp.data.rows.forEach(data => {
            this.dbControlList[data.dicValue] = data.dicName
          })
        })
    },
    queryStreamTypeList () {
      let loadConfig = {
        method: 'GET',
        url: '/dictionary',
        params: { dicCode: 'stream_type' }
      }
      this.$ajax(loadConfig)
        .then(resp => {
          this.streamTypeList = {}
          resp.data.rows.forEach(data => {
            this.streamTypeList[data.dicValue] = data.dicName
          })
        })
    }

  },
  mounted () {
    this.getPlatformList()
    // this.queryPlatform()
    // this.findServer()
    // 作业列表菜单权限控制
    // const authSystems = this.$store.getters.getSystems
    /* const authSystems = sessionStorage.systems.split(',')
    if (authSystems.includes('ROLE_ADMIN') ||
      authSystems.includes('ROLE_PLAT')) {

    } else {
      this.dropdownItems = [{ val: '补数' }, { val: '置虚或实' }]
    } */

    // console.log(this.$refs.uptree.getSelectedNodes())
  },
  watch: {
    selection: function (val) {
      if (val.length != 0) {
        this.ctrlloadingBatchFCDisable = false
        this.ctrlloadingBatchADisable = false
      } else {
        this.ctrlloadingBatchFCDisable = true
        this.ctrlloadingBatchADisable = true
      }
    },
    /*  作业列表修改属性后重新查询数据库  */
    transData: function (val, oldVal) {
      if (this.transData.showTable) {
        this.$refs.udsjobForm.validate(valid => {
          if (!valid && this.fromto == '1') return
          let params = {}
          Object.assign(params, this.formBean)
          params.currentPage = this.page.current
          params.pageSize = this.page.size
          for (let key in params) {
            if (key === 'job') {
              if (params[key] && params[key] !== '') {
                params[key] = '%' + params[key].trim().toUpperCase() + '%'
              } else {
                delete params[key]
              }
            }
            if (key == 'system' && params[key]) {
              params[key] = params[key].trim().toUpperCase()
            }
          }

          let httpConfig = {
            method: 'GET',
            url: RESOURCE_PATH + '/selectAll',
            params: params
          }
          this.$ajax(httpConfig)
            .then(resp => {
            // console.log(resp);
              if (resp.data.total && resp.data.total > 0) {
                if (resp.data.rows && resp.data.rows.length === 0) {
                  this.page.current = 1
                  this.search()
                }
              }
              this.gridData = resp.data.rows
              this.page.total = resp.data.total
            })
        })
      }
    }
  }

}
</script>
<style>
#joblistTable table td div{
  padding-left: 2px;
  padding-right: 2px;
}
#joblistTable table th div{
  padding-left: 5px;
  padding-right: 5px;
}
#confirmM .ivu-modal-body{
  padding:16px;font-size:14px;line-height:1.5;width: 100%;
}
#confirmD .ivu-modal-body{
  padding:16px;font-size:14px;line-height:1.5;width: 100%;
}
#confirmV .ivu-modal-body{
  padding:16px;font-size:14px;line-height:1.5;width: 100%;
}
#confirmBatchActive .ivu-modal-body{
  padding:16px;font-size:14px;line-height:1.5;width: 100%;
}

#confirmBatchForceStart .ivu-modal-body{
  padding:16px;font-size:14px;line-height:1.5;width: 100%;
}

#upAndDownstream .ivu-modal-body{
  width:100%;
}
#upAndDownstream .ivu-modal-wrap{
  z-index: 1500 !important;
}
#upAndDownstream .ivu-modal-mask{
  z-index: 1500 !important;
}
#drawerClass .ivu-drawer-wrap{
  z-index: 3000 !important;
}
#drawerClass .ivu-drawer-mask{
  z-index: 3000 !important;
}
.jobdetailclass {
  background: #eee;
  padding: 10px;
  font-size: 14px;
  line-height: 340%;
}
.jobdetailclass .ivu-col .span{
  font-size: 14px;
  margin-left: 2%;
}

.treeTitle:hover {
  background-color: #daedf9;
}
.treeTitle:visited {
  background-color: #daedf9;
}
</style>

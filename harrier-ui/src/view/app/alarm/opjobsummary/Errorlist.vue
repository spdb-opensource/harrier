<template>
	<div>
		<div v-if="tabObj.alarmTab">
			<Form ref="alarmForm" :label-width="80" :rules="alarmFormRule" :model="alarmBean">
				<Row>
					<Col span="4">
						<FormItem prop="localDate" label="告警日期">
							<DatePicker :transfer="true" type="date" parse="yyyy-MM-dd" format="yyyy-MM-dd" v-model="alarmBean.localDate"></DatePicker>
						</FormItem>
					</Col>
					<Col span="3">
						<FormItem prop="platform" label="平台名">
							<Select filterable v-model="alarmBean.platform" @on-change="querySystem" clearable>
								<Option v-for="item in platformData" :value="item.value" :key="item.value">{{ item.label }}</Option>
							</Select>
						</FormItem>
					</Col>
					<Col span="3">
						<FormItem label="应用名">
							<Select ref="refsystem" filterable v-model="alarmBean.system" placeholder="先选平台" clearable>
								<Option v-for="item in systemData" :value="item.value" :key="item.value">{{ item.label }}</Option>
							</Select>

						</FormItem>
					</Col>
					<Col span="5">
						<FormItem label="作业名">
								<Input v-model="alarmBean.job" placeholder="支持模糊查询" type="textarea" :autosize="{minRows: 1,maxRows: 5}"/>
						</FormItem>
					</Col>
					<!-- <Col span="3">
						<FormItem label="告警状态码">
							<Select filterable  v-model="alarmBean.alarmType" clearable>
								<Option v-for="item in alarmTypelist" :value="item.value" :key="item.value">{{ item.label }}</Option>
							</Select>
						</FormItem>
					</Col> -->
					<Col span="3">
						<FormItem label="告警状态">
							<Select filterable  v-model="alarmBean.alarmStatus" clearable>
								<Option v-for="item in alarmstatusList" :value="item.value" :key="item.value">{{ item.label }}</Option>
							</Select>
						</FormItem>
					</Col>
					<Col span="1" >
					&nbsp;
					</Col>
					<Col span="4" >
						<div class="spdb-toolbar">
							<Button type="primary" icon="ios-search" @click="search" style="">查询</Button>
							<Button type="primary" icon="md-close" @click="clear">清除</Button>
						</div>
					</Col>
					<!-- <Col span="2" >
						<FormItem label=" " :label-width="50">
							<Button type="primary" icon="ios-search" @click="search" style="">查询</Button>
						</FormItem>
					</Col>
					<Col span="4" >
						<FormItem label=" " :label-width="40">
							<Button type="primary" icon="android-close" @click="clear">清除</Button>
						</FormItem>
					</Col> -->
				</Row>
			</Form>
			<div id="alarmList" class="spdb-table" style="margin-top:5px;" v-if="transData.tabObj.showTable">
				<Table :loading="loading" :columns="gridColumns" :data="alarmGridData" @on-sort-change="changeSort" stripe>
				</Table>
			</div>
			<div class="spdb-page" v-if="transData.tabObj.showTable">
			<Page placement="top" :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat show-elevator @on-change="changePage" @on-page-size-change="changePageSize"></Page>
			</div>

		</div>
		<Modal
			v-model="cuInfo.show"
			title="联系组详细信息"
			:mask-closable="false"
			>
			<div>
				<div>
					<Form :model="cuInfoFormBean" ref="cuInfoHandleForm" :label-width="90" inline>
						<Row>
							<FormItem style='width:75%' prop="platform" label="平台">
								<Input readonly v-model="cuInfoFormBean.platform"/>
							</FormItem>
						</Row>
						<Row>
							<FormItem style='width:75%' prop="systems" label="应用">
								<Input readonly v-model="cuInfoFormBean.systems"/>
							</FormItem>
						</Row>
						<Row>
							<FormItem  style='width:75%' prop="groupName" label="组名">
								<Input readonly v-model="cuInfoFormBean.groupName"/>
							</FormItem>
						</Row>
            <Row>
							<FormItem style='width:75%' prop="sendType" label="发送类型">
								<Input readonly v-model="cuInfoFormBean.sendType"/>
							</FormItem>
						</Row>
            <Row  v-for="(infor, i) in showSendTypeData" :key="`infor-${i}`">
              <FormItem  style='width:75%'  :label="infor[0]">
                <Input readonly v-model="infor[1]" />
              </FormItem>
            </Row>
					</Form>
				</div>
			</div>
		</Modal>
		<Modal
			v-model="errorHandle.show"
			title="处理告警"
			:mask-closable="false"
			>
			<div>
				<div>
					<Form :model="formBean" ref="alarmHandleForm" :label-width="80" :rules="formRule" inline>
            <Row>
							<FormItem  style='width:75%' ref="operationTypeSelect" prop="operationType" label="处理类型">
								<Select filterable  v-model="formBean.operationType" >
									<Option v-for="item in operationTypeList" :value="item[0]" :key="item[0]">{{ item[1] }}</Option>
								</Select>
							</FormItem>
						</Row>
						<Row>
							<Button type="primary" icon="md-add" @click="errorHandleOk">保存</Button>
							<Button type="primary" icon="ios-trash-outline" @click="errorHandleBack">取消</Button>
						</Row>
					</Form>
				</div>
			</div>
			<div slot="footer"></div>
		</Modal>
    <Modal
      id="errorDiagnosisOp"
      v-model="errorDiagnosis.show"
			:title="'报错诊断>>'+baseBean.job"
			:mask-closable="false"
      width="90%"
      scrollable
      @on-cancel="errorDiagnosis.show = false">
      <!-- <div style="max-height:60vh;overflow-y:auto;overflow-x:hidden"> -->
      <div>
        <Row>
          <Col span="24" class="baseInfo">
            <Tabs value="baseInfo">
              <TabPane label="基本信息" icon="md-menu" name="baseInfo">
                <Row>
                  <Col style="margin-left:2%;line-height: 350%">
                    <!-- <Row v-if="isShowJob">
                      <Col span="24">作业名:<span class="base-message">{{baseBean.job}}</span></Col>
                    </Row> -->
                    <Row v-if="isShowJob">
                      <Col span="8">
                        <span class="base-title">当前数据日期:</span>
                        <span class="base-message">{{baseBean.jobDate}}</span>
                      </Col>
                      <Col span="8">
                        <span class="base-title">当前作业状态:</span>
                        <span class="base-message">{{baseBean.lastStatus}}</span>
                        </Col>
                      <Col span="8">
                        <span class="base-title">等待时间:</span>
                        <span class="base-message">{{baseBean.pendingTime}}</span></Col>
                    </Row>
                    <Row v-if="isShowJob">
                      <Col span="8">
                        <span class="base-title">分发时间:</span>
                        <span class="base-message">{{baseBean.dispatcherTime}}</span></Col>
                      <Col span="8">
                        <span class="base-title">开始时间:</span>
                        <span class="base-message">{{baseBean.startTime}}</span></Col>
                      <Col span="8">
                        <span class="base-title">结束时间:</span>
                        <span class="base-message">{{baseBean.endTime}}</span></Col>
                    </Row>
                    <Row>
                      <Col span="8">
                        <span class="base-title">联系人:</span>
                        <span class="base-message">{{baseBean.mcontactuser}}</span></Col>
                      <Col span="8">
                        <span class="base-title">手机号:</span>
                        <span class="base-message">{{baseBean.phone}}</span></Col>
                      <Col span="8">
                        <span class="base-title">邮箱:</span>
                        <span class="base-message">{{baseBean.email}}</span></Col>
                    </Row>
                    <Row>
                      <Col span="24">
                        <span class="base-title">告警内容:</span>
                        <span class="base-message">{{baseBean.alarmContent}}</span></Col>
                    </Row>
                  </Col>
                </Row>
              </TabPane>
              <TabPane label="历史运行情况" icon="md-menu" name="recordInfo">
                <Row span="24">
                  <Table border size="small" :columns="recordColumns" :data="recordData" stripe ></Table>
                </Row>
              </TabPane>
            </Tabs>
          </Col>
        </Row>
        <Row style="margin-top:1%">
          <Col span="24">
            <Row type="flex" align="middle">
              <Col style="margin-left:1%" span="2">
                <!-- <Icon type="android-menu" style="color:#2d8cf0;font-size:16px"></Icon>
                <span class="diag-title">作业诊断</span> -->
              </Col>
              <!-- <Col span="22">
                <hr style="background-color:#eceaea;height:1px;border:none;"/>
              </Col> -->
            </Row>
            <Row>
              <Tabs value="checkDepend" id="tabs" ref="tabs"  @on-click="changeJobTest" type="card">
                <TabPane v-if="jobCheck.isShowCheckDepend" label="依赖检测" name="checkDepend" icon="checkmark-circled">
                  <Row>
                    <Col span="14">
                      <div style="background:#eee;padding: 3px">
                        <Card :bordered="false" style="height:15%">
                          <p slot="title">上游信息</p>
                          <div id="upstreamJob" style="height:15vh"></div>
                          <div id="upstream" style="text-align:left;margin-top:25px">
                            <p>{{upJobInfo.message}} <span :class="upJobMsgStyle">{{upJobInfo.messageSp}}</span></p>
                            <Row>
                              <Table size="small" height="180" :show-header="upstreamData && upstreamData.length>0 ? true : false" :columns="upstreamColumns" :data="upstreamData"></Table>
                            </Row>
                          </div>
                        </Card>
                      </div>
                    </Col>
                    <Col span="10">
                      <div style="background:#eee;padding: 3px">
                        <Card :bordered="false" style="height:15%">
                          <p slot="title">下游信息</p>
                          <div id="downstreamJob" style="height:15vh"></div>
                          <div style="text-align:left;margin-top:25px" v-if="showDownstreamTable.showFistFloor">
                            <p>下游第一层>></p>
                            <Row>
                              <Table size="small" height="180" :show-header="downStreamData[0] ? true : false" :columns="downStreamColumns" :data="downStreamData[0]"></Table>
                            </Row>
                          </div>
                          <div style="text-align:left;margin-top:25px" v-if="showDownstreamTable.showSecondFloor">
                            <p>下游第二层>></p>
                            <Row>
                              <Table size="small" height="180" :show-header="downStreamData[1] ? true : false" :columns="downStreamColumns" :data="downStreamData[1]"></Table>
                            </Row>
                          </div>
                          <div style="text-align:left;margin-top:25px" v-if="showDownstreamTable.showThirdFloor">
                            <p>下游第三层>></p>
                            <Row>
                              <Table size="small" height="180" :show-header="downStreamData[2] ? true : false" :columns="downStreamColumns" :data="downStreamData[2]"></Table>
                            </Row>
                          </div>
                          <div style="text-align:left;margin-top:25px" v-if="showDownstreamTable.showKeyJob">
                            <p>影响下游关键作业>></p>
                            <Card style="height:180px;overflow-y:auto">
                              <div style="text-align:left">
                                <div v-for="(item,index) in affectDownStringKeyJobList" :key="index">
                                  <span style="margin-left:5%">{{item}}</span>
                                </div>
                              </div>
                              <div v-if="isShowInflunceSys">
                                <p style="text-align:center">暂无数据</p>
                              </div>
                            </Card>
                          </div>
                        </Card>
                      </div>
                    </Col>
                  </Row>
                </TabPane>
                <TabPane v-if="jobCheck.isShowCheckSchedule" label="调度配置检测" name="checkSchedule" icon="checkmark-circled">
                  <div style="background:#eee;padding: 3px;line-height: 300%">
                    <Card :bordered="false">
                      <p slot="title">作业配置信息</p>
                      <Row>
                        <Col span="5">
                          <span class="base-title">所属平台名:</span>
                          <span class="base-message">{{configData.platform}}</span>
                        </Col>
                        <Col span="5">
                          <span class="base-title">所属应用名:</span>
                          <span class="base-message">{{ configData.system }}</span>
                        </Col>
                        <Col span="8">
                          <span class="base-title">作业名:</span>
                          <span  class="base-message">{{ configData.job }}</span>
                        </Col>
                        <Col span="6">
                          <span class="base-title">作业中文名:</span>
                          <span class="base-message">{{ configData.jobName }}</span>
                        </Col>
                      </Row>
                      <Row>
                        <Col span="5">
                          <span class="base-title">作业类型:</span>
                          <span class="base-message">
                            <a @click="showDetail('jobType')">{{ jobtypeList[configData.jobType] }}</a>
                          </span>
                        </Col>
                        <Col span="5">
                          <span class="base-title">偏移天数:</span>
                          <span class="base-message">{{configData.offsetDay}}</span>
                        </Col>
                        <Col span="8">
                          <span class="base-title">单多批次:</span>
                          <span class="base-message">{{ configData.multiBatch }}</span>
                        </Col>
                        <Col span="6">
                          <span class="base-title">时间窗口:</span>
                          <span class="base-message">{{ configData.timewindow }}</span>
                        </Col>
                      </Row>
                      <Row>
                        <Col span="5">
                          <span class="base-title">是否启用:</span>
                          <span class="base-message">{{ formatYesOrNo(configData.isEnable) }}</span>
                        </Col>
                        <Col span="5">
                          <span class="base-title">是否置虚:</span>
                          <span class="base-message">{{ formatYesOrNo(configData.virtualEnable) }}</span>
                        </Col>
                        <Col span="8">
                          <span class="base-title">是否启用资源规则:</span>
                          <span class="base-message">
                            <a @click="showDetail('checkWeight')">{{ formatYesOrNo(configData.checkWeight) }}</a>
                          </span>
                        </Col>
                        <Col span="6">
                          <span class="base-title">是否外部信号文件触发:</span>
                          <span class="base-message">{{ configData.sourceByJob }}</span>
                        </Col>
                      </Row>
                      <Row>
                        <Col span="5">
                          <span class="base-title">优先级(1-1000):</span>
                          <span class="base-message">{{ configData.priority }}</span>
                        </Col>
                        <Col span="5">
                          <span class="base-title">分发策略:</span>
                          <span class="base-message">
                            <a @click="showDetail('strategy')">{{ strategyList[configData.strategy] }}</a>
                          </span>
                        </Col>
                        <Col span="8">
                          <span class="base-title">是否采用平台并发:</span>
                          <span class="base-message">{{ configData.isPlatformServer }}</span>
                        </Col>
                        <Col span="6">
                          <span class="base-title">并发上限:</span>
                          <span class="base-message">{{ configData.maxRunJob }}</span>
                        </Col>
                      </Row>
                      <Row>
                        <Col span="5">
                          <span class="base-title">逻辑集群:</span>
                          <span class="base-message">{{ locationList[configData.dbControl] }}</span>
                        </Col>
                      </Row>
                    </Card>
                  </div>
                  <div style="background:#eee;padding: 3px" v-if="isShowFreOrTime">
                    <Card :bordered="false">
                        <p slot="title">调度频率信息</p>
                        <div v-if="isShowTimeTriggerData" style="line-height: 300%">
                          <Row>
                            <Col span="6">
                              <span class="base-title">偏移天数:</span>
                                <span class="base-message">{{timeTriggerData.offsetDay}}</span>
                            </Col>
                            <Col span="6">
                              <span class="base-title">开始时间:</span>
                              <span class="base-message">{{ timeTriggerData.startTime }}</span>
                            </Col>
                            <Col span="6">
                              <span class="base-title">结束时间:</span>
                              <span class="base-message">{{ timeTriggerData.endTime }}</span>
                            </Col>
                          </Row>
                          <Row>
                            <Col span="3">
                              <span class="base-title">秒:</span>
                              <span class="base-message">{{ timeTriggerData.second }}</span>
                            </Col>
                            <Col span="3">
                              <span class="base-title">分:</span>
                              <span class="base-message">{{ timeTriggerData.minute }}</span>
                            </Col>
                            <Col span="3">
                              <span class="base-title">天:</span>
                              <span class="base-message">{{ timeTriggerData.day }}</span>
                            </Col>
                            <Col span="3">
                              <span class="base-title">月:</span>
                              <span class="base-message">{{ timeTriggerData.month }}</span>
                            </Col>
                            <Col span="3">
                              <span class="base-title">周:</span>
                              <span class="base-message">{{ timeTriggerData.week }}</span>
                            </Col>
                            <Col span="3">
                              <span class="base-title">年:</span>
                              <span class="base-message">{{ timeTriggerData.year }}</span>
                            </Col>
                          </Row>
                        </div>
                        <div v-if="isShowFrequencyData" style="line-height: 300%">
                          <Row>
                            <Col span="3">
                              <span class="base-title">天:</span>
                              <span class="base-message">{{ frequencyData.day }}</span>
                            </Col>
                            <Col span="3">
                              <span class="base-title">月:</span>
                              <span class="base-message">{{ frequencyData.month }}</span>
                            </Col>
                            <Col span="3">
                              <span class="base-title">周:</span>
                              <span class="base-message">{{ frequencyData.week }}</span>
                            </Col>
                            <Col span="3">
                              <span class="base-title">年:</span>
                              <span class="base-message">{{ frequencyData.year }}</span>
                            </Col>
                          </Row>
                        </div>
                    </Card>
                  </div>
                  <div style="background:#eee;padding: 3px;line-height: 300%" v-if="isShowWeight">
                    <Card :bordered="false">
                      <p slot="title">资源规则信息</p>
                        <div v-for="(item,index) in weightList" :key="index">
                          <Row>
                            <Col span="6">
                              <span class="base-title">资源类型:</span>
                              <span class="base-message">{{item.limit_type}}</span>
                            </Col>
                            <Col span="6">
                              <span class="base-title">资源上限:</span>
                              <span class="base-message">{{ item.conf_value }}</span>
                            </Col>
                            <Col span="6">
                              <span class="base-title">占用资源数:</span>
                              <span class="base-message">{{ item.limit_value }}</span>
                            </Col>
                          </Row>
                        </div>
                    </Card>
                  </div>
                  <div style="background:#eee;padding: 3px;line-height: 300%" v-if="isShowStrategy">
                    <Card :bordered="false">
                      <p slot="title">策略参数</p>
                        <div>
                          <Row>
                            <Col span="20">
                              <span class="base-title">{{strategyList[configData.strategy]}}:</span>
                              <span class="base-message"> {{ configData.strategyPro }} </span>
                            </Col>
                          </Row>
                        </div>
                    </Card>
                  </div>
                </TabPane>
                <TabPane v-if="jobCheck.isShowAnalyPlatPer" label="平台性能分析" name="analysisPlatPerformance" icon="checkmark-circled">
                  <Row>
                    <Form ref="lineSearchForm" :model="lineSearchBean" :label-width="80">
                      <Row>
                        <Col span="5">
                          <FormItem prop="start" label="开始时间">
                            <DatePicker v-model="lineSearchBean.start" type="datetime" format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间"></DatePicker>
                          </FormItem>
                        </Col>
                        <Col span="5">
                          <FormItem prop="end" label="结束时间">
                            <DatePicker v-model="lineSearchBean.end" type="datetime" format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间"></DatePicker>
                          </FormItem>
                        </Col>
                        <Col offset="1" span="2">
                          <div class="spdb-toolbar">
                            <Button type="primary" icon="ios-search" @click="searchLineGraph">查询</Button>
                          </div>
                        </Col>
                      </Row>
                    </Form>
                  </Row>
                  <Row>
                    <Col span="12">
                      <div style="background:#eee;padding: 5px">
                        <Card :bordered="false">
                            <p slot="title">平台作业状态折线图</p>
                            <div id="platformconc" class="line_box"></div>
                        </Card>
                      </div>
                    </Col>
                    <Col span="12">
                      <div style="background:#eee;padding: 5px">
                        <Card :bordered="false">
                            <p slot="title">节点作业状态折线图 {{baseBean.serverName}}</p>
                            <div id="nodeconc" class="line_box"></div>
                        </Card>
                      </div>
                    </Col>
                  </Row>
                </TabPane>
                <TabPane v-if="jobCheck.isShowAnalyLog" label="日志分析" name="analysisLog" icon="checkmark-circled">
                  <div style="background:#eee;padding: 3px">
                    <Card :bordered="false">
                      <p slot="title">日志</p>
                      <div>
                        <p>{{logContent}}</p>
                      </div>
                    </Card>
                  </div>
                </TabPane>
                <TabPane v-if="jobCheck.isShowAnalyKeyLink" label="关键链路分析" name="analysisKeyLink" icon="checkmark-circled">
                  <Row>
                    <Col span="6">
                      <div style="background:#eee;padding: 3px">
                        <Card :bordered="false">
                            <p slot="title">关键作业</p>
                            <div style="height:37vh;overflow-y:auto">
                              <div v-for="(item,index) in affectDownStringKeyJobList" :key="index">
                                <a @click="searchBaseLine(item)">{{item}}</a>
                              </div>
                            </div>
                        </Card>
                      </div>
                    </Col>
                    <Col span="18">
                      <div style="background:#eee;padding: 3px">
                        <Card :bordered="false">
                          <p slot="title">
                            关键作业链路
                            <span v-if="!isInitialize" style="float:right;margin-right:8px;color:#676767">
                              批量数据日期: {{keyJobBean.jobDate}}&#8195
                              基线承诺时间: {{keyJobBean.baselineTime}}
                            </span>
                          </p>
                          <span v-if="isInitialize">暂无数据，请稍后重试...</span>
                          <div style="height:37vh;overflow-y:auto" v-if="!isInitialize">
                            <!-- <p>
                              批量数据日期: {{keyJobBean.jobDate}}&#8195
                              基线承诺时间: {{keyJobBean.baselineTime}}
                            </p> -->
                            <Collapse v-model="defaultPanel">
                              <Panel v-for="(panel,index) in collapseData" :name="index + ''" :key="index">
                                    <span style="font-weight: bold">关键链路</span>&#8195
                                    根源作业:
                                    {{panel.baselineBean.rootJob}} &#8195
                                    余量时间:
                                    {{panel.baselineBean.restTime}}&#8195
                                    基线状态:
                                    <span :style="{'color': panel.baselineBean.status == '安全' ? 'green':'red'}">{{panel.baselineBean.status}}&#8195</span>
                                    预计完成时间:
                                    {{panel.baselineBean.estimatedCompTime}}
                                <div slot="content">
                                <Timeline>
                                  <TimelineItem v-for="(item,index) in panel.timelineItemData" :key="index" :color="item.color">
                                    <Icon type="ios-radio-button-on" slot="dot"></Icon>
                                    <p style="font-size:12px;font-weight: 600">{{item.job}}</p>
                                    <p style="color:#ff9900" class="content" v-if="item.isShowWaitSignalFile">等待信号文件触发...</p>
                                    <p class="content" v-if="item.isShowEstimatedTime">
                                      预计分发时间：{{item.estimatedDispatcherTime}} |
                                      预计完成时间：{{item.estimatedEndTime}}
                                    </p>
                                    <p class="content">
                                      作业状态：{{item.lastStatus}} |
                                      分发时间：{{item.dispatcherTime}} |
                                      开始时间：{{item.startTime}} |
                                      结束时间：{{item.endTime}} |
                                      作业日期：{{item.jobDate}} |
                                      作业类型：{{item.jobType}}
                                    </p>
                                  </TimelineItem>
                                </Timeline>
                                </div>
                              </Panel>
                            </Collapse>
                          </div>
                        </Card>
                      </div>
                    </Col>
                  </Row>
                </TabPane>
              </Tabs>
            </Row>
          </Col>
        </Row>
      </div>
      <div slot="footer"></div>
    </Modal>
    <Modal
      id="timeTriggerOp"
      v-model="timeTriggerOp.show"
      title="定时信息"
      width="25%"
      >
      <div style="margin-left:7%">
        <Row>
          <Col span="24">
            <span>下次执行时间:   {{triggerBean.startTime}}</span>
          </Col>
        </Row>
        <Row>
          <Col span="12">
            <span>秒:    {{triggerBean.second}}</span>
          </Col>
          <Col span="10">
            <span>分:   {{triggerBean.minute}}</span>
          </Col>
        </Row>
        <Row>
          <Col span="12">
            <span>时:   {{triggerBean.hour}}</span>
          </Col>
          <Col span="8">
            <span>日:   {{triggerBean.day}}</span>
          </Col>
        </Row>
        <Row>
          <Col span="12">
            <span>月:   {{triggerBean.month}}</span>
          </Col>
          <Col span="8">
            <span>周:   {{triggerBean.week}}</span>
          </Col>
        </Row>
        <Row>
          <Col span="12">
            <span>年:   {{triggerBean.year}}</span>
          </Col>
          <Col span="8">
            <span>偏移天数:   {{triggerBean.offsetDay}}</span>
          </Col>
        </Row>
      </div>
      <div slot="footer"></div>
    </Modal>
    <Modal
      id="frequencyOp"
      v-model="frequencyOp.show"
      title="频率信息"
      width="25%"
      >
      <div style="margin-left:7%">
        <Row>
          <Col span="12">
            <span>日:   {{frequencyBean.day}}</span>
          </Col>
          <Col span="10">
            <span>月:   {{frequencyBean.month}}</span>
          </Col>
        </Row>
        <Row>
          <Col span="12">
            <span>周:   {{frequencyBean.week}}</span>
          </Col>
          <Col span="10">
            <span>年:   {{frequencyBean.year}}</span>
          </Col>
        </Row>
      </div>
      <div slot="footer"></div>
    </Modal>
	</div>
</template>

<script>
// import * as echarts from "echarts";
import utils from '@/common/utils'
import store from '@/store/index'
// const platforms = store.getters.getPlatforms
const RESOURCE_PATH = '/alarm'

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
    const validAlarmStatus = (rule, value, callback) => {
      if (value === '0') {
        return callback(new Error('告警状态不能为未处理'))
      } else {
        callback()
      }
    }

    return {
      noJobCheckList: [100, 101, 300, 301, 302, 303, 304, 400, 409, 420, 430, 450],
      upJobMsgStyle: 'upJobMsgGreen',
      upJobInfo: {
        message: '',
        messageSp: ''
      },
      strategyList: {
        '0': '普通策略',
        '1': '指定机器分发机器',
        '2': '指定机器序号分发',
        '3': '指定机器标签分发'
      },
      jobtypeList: {},
      locationList: {},
      showDownstreamTable: {
        showFistFloor: true,
        showSecondFloor: false,
        showThirdFloor: false,
        showKeyJob: false
      },
      jobCheck: {
        isShowCheckDepend: false,
        isShowCheckSchedule: false,
        isShowAnalyPlatPer: false,
        isShowAnalyLog: false,
        isShowAnalyKeyLink: false
      },
      menuData: [
        { '100': 'isShowCheckDepend' },
        { '101': 'isShowCheckSchedule' },
        { '102': 'isShowAnalyPlatPer' },
        { '103': 'isShowAnalyLog' },
        { '104': 'isShowAnalyKeyLink' }
      ],
      waitSignalFile: false,
      isInitialize: false,
      defaultPanel: [],
      collapseData: [],
      baselineBean: {},
      keyJobBean: {},
      showSendTypeData: [],
      sendTypeBean: {},
      sendTypeList: [],
      sendTypeDataBean: {},
      timelineItemData: [],
      lineSearchBean: {
        start: '',
        end: ''
      },
      frequencyList: [],
      triggerList: [],
      triggerBean: {},
      frequencyBean: {},
      timeTriggerOp: {
        show: false
      },
      frequencyOp: {
        show: false
      },
      logContent: '',
      configData: {},
      timeTriggerData: {},
      frequencyData: {},
      weightList: [],
      isShowFreOrTime: false,
      isShowTimeTriggerData: false,
      isShowFrequencyData: false,
      isShowWeight: false,
      isShowStrategy: false,
      row: {},
      platformData: [],
      systemData: [],
      loading: false,
      errorHandle: {
        show: false,
        title: '',
        id: ''
      },
      isShowJob: true,
      isShowInflunceSys: false,
      isShowDoneStream: false,
      affectDownStringKeyJobList: [],
      downStreamData: [],
      downStreamColumns: [
        {
          title: '平台名',
          key: 'platform',
          align: 'center'
        },
        {
          title: '应用名',
          key: 'system',
          align: 'center'
        },
        {
          title: '影响数量',
          key: 'affectNum',
          align: 'center'
        }
      ],
      upstreamColumns: [
        {
          title: '作业名',
          key: 'job',
          minWidth: 200
        },
        {
          title: 'job_date',
          key: 'jobDate',
          minWidth: 150
        },
        {
          title: '作业类型',
          key: 'jobType',
          minWidth: 120,
          render: (h, { column, index, row }) => {
            return h('div', [
              h('a', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  marginRight: '5px',
                  color: '#3399ff'
                },
                on: {
                  click: () => {
                    let id = row.job
                    if (row.jobType == 'M' || row.jobType == 'W') {
                      this.matchedData(row.job, 'frequency')
                      this.frequencyOp.show = true
                    } else if (row.jobType == 'C') {
                      this.matchedData(row.job, 'trigger')
                      this.timeTriggerOp.show = true
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
          key: 'lastStatus',
          minWidth: 120
        }
      ],
      upstreamData: [],
      rootFailData: [],
      depSuccData: [],
      depFailData: [],
      baseBean: {
        job: '',
        jobDate: '',
        pendingTime: '',
        dispatcherTime: '',
        startTime: '',
        endTime: '',
        alarmContent: '',
        mcontactuser: '',
        lastStatus: '',
        alarmCnt: ''
      },
      recordColumns: [
        {
          title: '执行次数',
          width: 100,
          key: 'numTimes'
        },
        {
          title: 'log',
          key: 'logName',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('a', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                  // color: "green"
                },
                href: row.logDir,
                on: {
                  click: () => {
                    this.downloadLogfile(row)
                  }
                }
              }, row.logName)
            ])
          }
        },
        {
          title: 'script',
          key: 'scriptName',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('a', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // marginRight: '5px'
                  // color: "green"
                },
                href: row.scriptPath,
                on: {
                  click: () => {
                    this.downloadScriptfile(row)
                  }
                }
              }, row.scriptName)
            ])
          }
        },
        {
          title: '执行节点',
          ellipsis: false,
          tooltip: true,
          width: 120,
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
          title: '开始时间',
          width: 180,
          key: 'startTime'
        },
        {
          title: '结束时间',
          width: 180,
          key: 'endTime',
          render: (h, params) => {
            let endTimeStr = ''
            endTimeStr = utils.fmtDate(params.row.endTime, 'yyyy')
            if (endTimeStr == '2099') {
              endTimeStr = ''
            } else {
              endTimeStr = params.row.endTime
            }
            return h('div', {
              props: {
              }
            }, endTimeStr)
          }
        },
        {
          title: '耗时',
          width: 100,
          key: 'endTime',
          render: (h, params) => {
            let etime = null
            let startTime = null
            let elapsed = ''
            let str = utils.fmtDate(params.row.endTime, 'yyyy')
            if (str == '2099') {
              elapsed = ''
            } else {
              etime = utils.fmtDate(params.row.endTime, 'yyyyMMdd hh:mm:ss')
              startTime = utils.fmtDate(params.row.startTime, 'yyyyMMdd hh:mm:ss')
              let e = new Date(etime.substring(0, 4) + '/' + etime.substring(4, 6) + '/' + etime.substring(6))
		        	let s = new Date(startTime.substring(0, 4) + '/' + startTime.substring(4, 6) + '/' + startTime.substring(6))
              // let ss=
              let second = parseInt((e.getTime() -  s.getTime())) / 1000
              let hourTime = 0
              let minuteTime = 0
              let secondTime = 0
              if (second > 60) {
                minuteTime = Math.floor(second / 60)
                secondTime = Math.floor(second % 60)
                if (minuteTime >= 60) {
                  hourTime = Math.floor(minuteTime / 60)
                  minuteTime = Math.floor(minuteTime % 60)
                } else {
                  hourTime = 0
                }
              } else {
                hourTime = 0
                minuteTime = 0
                if (second == 60) {
                  minuteTime = 1
                  secondTime = 0
                } else {
                  secondTime = second
                }
              }
              elapsed = this.addZero(hourTime) + ':' + this.addZero(minuteTime) + ':' + this.addZero(secondTime)
            }
            return h('div', {
              props: {
              }
            }, elapsed)
          }
        },
        {
          title: 'TXDate',
          width: 110,
          key: 'jobDate'
        },
        {
          title: '运行状态',
          width: 110,
          key: 'returnCode',
          render: (h, params) => {
            let str = ''
            let endTimeStr = ''
            endTimeStr = utils.fmtDate(params.row.endTime, 'yyyy')
            if (params.row.returnCode == 0) {
              str = 'Done'
            } else {
              str = 'Failed'
            }
            if (endTimeStr == '2099') {
              str = 'Running'
            }
            return h('div', {
              props: {
              }
            }, str)
          }
        }
      ],
      recordData: [],
      formRule: {
        operationType: [{
          required: true,
          message: '请输入数据！'
        }],
        operationTime: [{
          required: true,
          message: '请输入数据！'
        }],
        operationUser: [{
          required: true,
          message: '请输入数据！'
        }],
        alarmStatus: [{
          required: true,
          message: '请输入数据！'
        }, { validator: validAlarmStatus, trigger: 'blur' }]
      },
      alarmFormRule: {
        localDate: [{
          required: true,
          message: '请输入数据！'
        }]
      },
      alarmTypelist: [],
      userlist: {},
      userMap: {},
      userIds: {},
      cuInfo: {
        show: false
      },
      errorDiagnosis: {
        show: false
      },
      cuInfoFormBean: {},
      operations: [
        {
          label: '置虚跳过',
          value: '0'
        },
        {
          label: '再次执行',
          value: '1'
        },
        {
          label: '手工触发',
          value: '2'
        },
        {
          label: '置Done',
          value: '3'
        },
        {
          label: '中止执行',
          value: '4'
        },
        {
          label: '自动完成',
          value: '5'
        }
      ],
      operationTypeList: [],
      alarmstatusList: [
        {
          label: '未处理',
          value: '0'
        },
        {
          label: '已处理',
          value: '1'
        },
        {
          label: '暂停告警',
          value: '2'
        }],
      alarmBean: { alarmTime: utils.fmtDate(new Date(), 'yyyy-MM-dd') },
      formBean: {},
      tabObj: { alarmTab: true, jobdetail: false, showTable: true },
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
      // alarmGridData:[{platform:"BDP",system:"DLA",job:"BDP_DLA_TEST_1",errorType:"JobFailed",alarmTime:"2019-06-30 12:01:23",alarmCount:1,affectKeySys:0,handled:'否',contactUser:""}],
      gridColumns: [
        // {
        //   width: 90,
        //   title: '序号',
        //   align: 'center',
        //   sortable: true,
        //   key: 'id',
        //   render: (h, { column, index, row }) => {
        //     return h('div', [
        //       h('a', {
        //         props: {
        //           size: 'small',
        //           type: 'info'
        //         },
        //         style: {
        //           // fontSize: '32px',
        //           // fontWeight: 'normal',
        //           // color: '#7EC0EE'
        //         },
        //         on: {
        //           click: () => {
        //             // Object.assign({},{id: id, statusName: "jobdetail",alarmTab: false},this.getPageParam())
        //             this.$emit('switch', Object.assign({}, { row: row, statusName: 'alarmdetailTab' }, this.getPageParam()))
        //           }
        //         }
        //       }, row.id)
        //     ])
        //   }
        // },
        {
          title: '平台名',
          width: 64,
          sortable: true,
          align: 'center',
          key: 'platform'
        },
        {
          title: '应用名',
          sortable: true,
          width: 64,
          align: 'center',
          key: 'systems'
        },
        {
          title: '作业名',
          key: 'job',
          // align: 'center',
          minWidth: 250,
          // maxWidth: 400,
          sortable: true,
          render: (h, { column, index, row }) => {
            return h('div', [
              h('a', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  // fontSize: '32px',
                  // fontWeight: 'normal',
                  // color: '#7EC0EE'
                },
                on: {
                  click: () => {
                    let id = row.job
                    if (this.transData.jobStatus) {
                      this.$emit('switch', Object.assign({}, { id: id, jobStatus: this.transData.jobStatus, statusName: 'jobdetail', jobData: row }, this.getPageParam())) // 提交form事件，在parent中显示form
                    } else {
                      this.$emit('switch', Object.assign({}, { id: id, statusName: 'jobdetail', jobData: row }, this.getPageParam())) // 提交form事件，在parent中显示form
                    }

                    // let id = row.job
                    // this.$emit('switch', Object.assign({}, { id: id, statusName: 'jobdetail', jobData: row }, this.getPageParam())) // 提交form事件，在parent中显示form

                    // let id = row.job
                    // this.transData.jobName = row.job
                    // this.switchTab('jobdetail'); //提交form事件，在parent中显示form
                    // this.tabObj.alarmTab = false
                    // // Object.assign({}, {id: id,statusName: "alarmlog",jobData: row},this.getPageParam())
                    // this.$emit('switch', Object.assign({}, { id: id, statusName: 'jobdetail' }, this.getPageParam()))
                  }
                }
              }, row.job)
            ])
          }
        },
        {
          title: '告警码',
          sortable: true,
          align: 'center',
          width: 80,
          key: 'alarmType'
        },
        {
          title: '告警时间',
          sortable: true,
          align: 'center',
          width: 160,
          key: 'times'
        },
        {
          title: '告警内容',
          sortable: true,
          // align: 'center',
          minWidth: 150,
          // maxWidth: 450,
          ellipsis: true,
          key: 'content',
          render: (h, { column, index, row }) => {
            let color = 'green'
            let texts = ''
            if (row.alarmStatus === 'WARN') {
              color = 'red'
            } else if (row.alarmStatus === 'DEAL') {
              color = '#FF8E16'
            } else if (row.alarmStatus === 'END') {
              color = 'black'
            }
            texts = row.content
            if (texts != null) {
              if (texts.length > 10) {
                texts = texts.slice(0, 10) + '...'
              } else {
                texts = row.content
              }
            } else {
              return
            }
            let str = row.content
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
                  },
                  style: {
                    color: color
                  }
                }, row.content)
              ])
            ])
          }
        },
        {
          title: '处理时间',
          width: 120,
          align: 'center',
          key: 'operationTime'
          // render: (h, {column, index, row}) => {

          // }
        },
        // {
        //   title: '影响关键应用',
        //   minWidth: 90,
        //   maxWidth: 100,
        //   key: 'influnceSys',
        //   render: (h, { column, index, row }) => {
        //     return h('div', [
        //       h('span', {
        //         props: {
        //           size: 'small',
        //           type: 'info'
        //         },
        //         style: {
        //           // marginRight: '5px'
        //         },
        //         on: {
        //           click: () => {
        //             this.showCuInfo(row)
        //           }
        //         }
        //       }, row.influnceSys)
        //     ])
        //   }
        // },
        {
          title: '联系组',
          width: 90,
          align: 'center',
          key: 'noticeGroupName',
          render: (h, { column, index, row }) => {
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
                    this.showCuInfo(row)
                  }
                }
              }, this.userMap[row.noticeGroupName] ? this.userMap[row.noticeGroupName] : row.noticeGroupName)
            ])
          }
        },
        // {
        //   title: '报错诊断',
        //   width: 80,
        //   align: 'center',
        //   render: (h, { column, index, row }) => {
        //     let dis = false
        //     let col = '#2d8cf0'
        //     if (row.alarmType) {
        //       if (this.noJobCheckList.includes(parseInt(row.alarmType))) {
        //         dis = true
        //         col = '#bbbec4'
        //       }
        //     }
        //     // this.noJobCheckList
        //     return h('div', [
        //       h('Button', {
        //         props: {
        //           size: 'small',
        //           type: 'text',
        //           disabled: dis
        //         },
        //         style: {
        //           fontSize: '14px',
        //           color: col,
        //           // background: 0 0 ,
        //           backgroundColor: 'rgb(0, 0, 0, 0)',
        //           textDecoration: 'none',
        //           outline: '0',
        //           cursor: 'pointer'
        //           // transition: color .2s ease
        //           // marginRight: '5px'
        //         },
        //         on: {
        //           click: () => {
        //             this.showErrorDiagnosis(row)
        //           }
        //         }
        //       }, '详情')
        //     ])
        //   }
        // },
        {
          title: '详情',
          width: 50,
          // fixed: 'right',
          align: 'center',
          render: (h, { column, index, row }) => {
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
                    this.$emit('switch', Object.assign({}, { row: row, statusName: 'alarmdetailTab' }, this.getPageParam()))
                  }
                }
              }, '查看')
            ])
          }
        },
        {
          title: '日志',
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
                      this.$emit('switch', Object.assign({}, { id: id, jobStatus: this.transData.jobStatus, jobData: row, statusName: 'jobRecord' }, this.getPageParam())) // 提交form事件，在parent中显示form
                    } else {
                      this.$emit('switch', Object.assign({}, { id: id, jobData: row, statusName: 'jobRecord' }, this.getPageParam())) // 提交form事件，在parent中显示form
                    }
                  }
                }
              }, '')
            ])
          }
        },
        {
          title: '处理登记',
          align: 'center',
          width: 120,
          render: (h, { column, index, row }) => {
            return h('div', [
              h('a', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  marginRight: '10px'
                },
                on: {
                  click: () => {
                    this.showErrorHandle(row, index)
                  }
                }
              }, '登记')

            ])
          }
        }
      ],
      alarmGridData: [],
      // alarmGridData:[{platform:"BDP",system:"DLA",job:"BDP_DLA_TEST_1",errorType:"JobFailed",alarmTime:"2019-06-30 12:01:23",alarmCount:1,affectKeySys:0,handled:'否',contactUser:""}],
      selection: [],
      page: {
        current: 1,
        total: 100,
        size: 10,
        opt: [10, 50, 100]
      },
      listData: [],
      showData: [],
      timer: null,
      // graph: null,
      platformconcGraph: null,
      nodeGraph: null
    }
  },
  methods: {
    /**
		 * 初始化
		 **/
    init () {
      this.search()
      this.getsendTypeList()
      // if (this.transData.currentPage) {
      //   this.alarmBean = this.transData.formBean
      //   this.page.current = this.transData.currentPage
      //   this.page.size = this.transData.pageSize
      // }
      // if (!this.alarmBean.platform) {
      //   if (!platforms.includes('ROLE_ADMIN')) {
      //     this.alarmBean.platform = platforms[0]
      //     this.querySystem()
      //   }
      // }
      // this.loaduser()
      // this.queryPlatform()
      // this.queryjobtypeList()
      // this.querylocationList()
    },
    showCuInfo (row) {
      this.showSendTypeData = []
      this.cuInfoFormBean = {}
      this.cuInfo.show = true
      const config = {
        method: 'GET',
        url: '/alarm/group/getDetailByGroupName',
        params: { groupName: row.noticeGroupName }
      }
      this.$ajax(config)
        .then(resp => {
          if (resp.data && resp.data.length > 0) {
            this.cuInfoFormBean = resp.data[0]
            console.log(this.cuInfoFormBean.sendParams)
            let oldSendType = JSON.parse(this.cuInfoFormBean.sendParams)
            let sendTypeData = this.sendTypeDataBean[this.cuInfoFormBean.sendType]
            // 循环取出自定义的key和系统中的key
            let oldSendTypeKeys = Object.keys(oldSendType)
            for (let i = 0; i < oldSendTypeKeys.length; i++) {
              let key = oldSendTypeKeys[i]
              let showKey = key
              for (let j = 0; j < sendTypeData.length; j++) {
                if (key === sendTypeData[j][1]) {
                  showKey = sendTypeData[j][0]
                  break
                }
              }
              let param = []
              param.push(showKey)
              param.push(oldSendType[key])
              this.showSendTypeData.push(param)
            }
          }
        })
    },
    showErrorDiagnosis (row) {
      // 初始化数据
      this.$refs.tabs.activeKey = 'checkDepend' // 打开对话框 默认选择作业依赖
      for (let key in this.jobCheck) {
        this.jobCheck[key] = false
      }
      this.keyJobBean = {}
      this.collapseData = []
      this.showDetail(false)
      // 查找后台配置菜单
      let loadConfig = {
        method: 'GET',
        url: '/merrordiagnosis/getMenuInfo',
        params: {
          platform: row.platform,
          system: row.system,
          job: row.job,
          errorCode: row.alarmType
        }
      }
      this.$ajax(loadConfig).then(resp => {
        if (resp.status && resp.status === 200) {
          if (resp.data && resp.data.length > 0) {
            this.menuData.forEach((menu, index) => {
              resp.data.forEach(d => {
                if (Object.keys(menu)[0] == d) {
                  this.jobCheck[menu[d]] = true
                }
              })
            })
            if (row.alarmLogkeyContent) { // 日志分析
              this.jobCheck.isShowAnalyLog = true
              this.logContent = row.alarmLogkeyContent
            }
            //
            this.row = row
            // let sTime = utils.fmtDate(new Date(new Date().getTime() -1*60*60*1000),'yyyy-MM-dd hh:mm:ss')
            // let eTime = utils.fmtDate(new Date(),'yyyy-MM-dd hh:mm:ss')
            this.searchBaseInfo(row) // 基本信息
            this.searchRecordInfoList(row) // 历史运行情况
            this.searchDepend(row) // 作业依赖
            this.errorDiagnosis.show = true0
          } else {
            this.$Message.warning('当前告警码无报错诊断。')
          }
        }
      })
    },
    formatYesOrNo (data) {
      return data == 1 ? '是' : '否'
    },
    showDetail (item) {
      // jobType strategy checkWeight
      this.isShowFreOrTime = false
      this.isShowTimeTriggerData = false
      this.isShowFrequencyData = false
      this.isShowWeight = false
      this.isShowStrategy = false
      if (item === 'jobType') {
        if (this.configData.jobType == 'M' || this.configData.jobType == 'W') {
          this.isShowFreOrTime = true
          this.isShowFrequencyData = true
        } else if (this.configData.jobType == 'C') {
          this.isShowFreOrTime = true
          this.isShowTimeTriggerData = true
        } else {
          this.$Message.warning('日作业不存在定时或频度信息。')
        }
      } else if (item === 'strategy') {
        if (this.configData.strategy == '0') {
          this.$Message.warning('该分发策略无策略参数。')
        } else if (this.configData.strategy == '1') {
          this.isShowStrategy = true
        } else {
          this.$Message.warning('该分发策略暂不支持查看。')
        }
      } else if (item === 'checkWeight') {
        if (this.configData.checkWeight == 1) {
          this.isShowWeight = true
        } else {
          this.$Message.warning('未启用资源规则。')
        }
      }
    },
    getsendTypeList () {
      let httpConfig = {
        method: 'GET',
        url: '/alarm/group/sendType'
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
          }
        })
    },
    getPlatformconcData (data) { // 平台并发
      // let graph = this.$echarts.init(document.getElementById('platformconc'))
      let option = {
        title: {
          // text: '平台作业状态折线图'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['runningNum', 'dispatcherNum', 'pendingNum']
        },
        xAxis: {
          type: 'category',
          data: data['timeList']
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          name: 'runningNum',
          data: data['running'],
          type: 'line',
          areaStyle: {
            color: {
              type: 'linear',
              x: 0,
              y: 0,
              x2: 0,
              y2: 1,
              colorStops: [
                { offset: 0, color: 'red' },
                { offset: 1, color: 'white' }
              ]
            }
          }
        },
        {
          name: 'dispatcherNum',
          data: data['dispatcher'],
          type: 'line',
          areaStyle: {
            color: {
              type: 'linear',
              x: 0,
              y: 0,
              x2: 0,
              y2: 1,
              colorStops: [
                { offset: 0, color: '#005eaa' },
                { offset: 1, color: 'white' }
              ]
            }
          }
        },
        {
          name: 'pendingNum',
          data: data['pending'],
          type: 'line',
          areaStyle: {
            color: {
              type: 'linear',
              x: 0,
              y: 0,
              x2: 0,
              y2: 1,
              colorStops: [
                { offset: 0, color: '#61a0a8' },
                { offset: 1, color: 'white' }
              ]
            }
          }
        }]
      }
      this.platformconcGraph.hideLoading()
      this.platformconcGraph.setOption(option)
    },
    getNodeData (data) { // 节点并发
      // this.nodeGraph = this.$echarts.init(document.getElementById('nodeconc'))
      let option = {
        title: {
          // text: '节点作业状态折线图'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['runningNum', 'dispatcherNum', 'pendingNum']
        },
        xAxis: {
          type: 'category',
          data: data['timeList']
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          name: 'runningNum',
          data: data['running'],
          type: 'line',
          areaStyle: {
            color: {
              type: 'linear',
              x: 0,
              y: 0,
              x2: 0,
              y2: 1,
              colorStops: [
                { offset: 0, color: 'red' },
                { offset: 1, color: 'white' }
              ]
            }
          }
        },
        {
          name: 'dispatcherNum',
          data: data['dispatcher'],
          type: 'line',
          areaStyle: {
            color: {
              type: 'linear',
              x: 0,
              y: 0,
              x2: 0,
              y2: 1,
              colorStops: [
                { offset: 0, color: '#005eaa' },
                { offset: 1, color: 'white' }
              ]
            }
          }
        },
        {
          name: 'pendingNum',
          data: data['pending'],
          type: 'line',
          areaStyle: {
            color: {
              type: 'linear',
              x: 0,
              y: 0,
              x2: 0,
              y2: 1,
              colorStops: [
                { offset: 0, color: '#61a0a8' },
                { offset: 1, color: 'white' }
              ]
            }
          }
        }]
      }
      this.nodeGraph.hideLoading()
      this.nodeGraph.setOption(option)
    },
    searchBaseInfo (row) {
      if (!row.job) {
        this.isShowJob = false
      }
      let loadConfig = {
        method: 'GET',
        url: '/merrordiagnosis/getBaseInfo',
        params: {
          job: row.job,
          alarmId: row.alarmId
        }
      }
      this.baseBean = {}
      this.$ajax(loadConfig).then(resp => {
        if (resp.status && resp.status === 200) {
          for (let key in resp.data.udsJob) {
            this.baseBean[key] = resp.data.udsJob[key]
          }
          this.baseBean.alarmContent = resp.data.alarmContent
          this.baseBean.mcontactuser = resp.data.mcontactuser
          // 联系人信息
          const config = {
            method: 'GET',
            url: '/alarm/contactUserInfo',
            params: { platform: row.platform, system: row.system, userName: row.mcontactuser }
          }
          this.$ajax(config)
            .then(res => {
              this.baseBean.email = res.data.email
              this.baseBean.phone = res.data.phone
              this.$forceUpdate()
            })
        // alarmCnt: '' // 本月告警次数
        }
      })
    },
    searchRecordInfoList (row) {
      if (!row.job) {
        return
      }
      let params = {}
      params.job = row.job
      params.currentPage = 1
      params.pageSize = 3
      let httpConfig = {
        method: 'GET',
        url: '/merrordiagnosis/getRecordInfoList',
        params: params
      }
      this.$ajax(httpConfig)
        .then(resp => {
          this.recordData = resp.data.rows
        })
    },
    searchDepend (row) {
      if (!row.job) {
        return
      }
      let params = { job: row.job, errorCode: row.alarmType }
      let httpConfig = {
        method: 'GET',
        url: '/merrordiagnosis/getDepInfo',
        params: params
      }
      this.frequencyList = []
      this.triggerList = []
      this.downStreamData = []
      let cNumCount = []
      this.depSuccData = []
      this.depFailData = []
      this.rootFailData = []
      this.$ajax(httpConfig)
        .then(resp => {
          if (resp.status && resp.status === 200) {
            if (JSON.stringify(resp.data) == '{}') {
              return
            }
            let upChartData = []
            if (resp.data) {
              // 上游第一层依赖>>依赖满足:
              if (resp.data.depSucc && resp.data.depSucc.length > 0) {
                this.depSuccData = resp.data.depSucc
                if (resp.data.depSuccFre) {
                  for (let key in resp.data.depSuccFre) {
                    this.frequencyList.push(resp.data.depSuccFre[key])
                  }
                }
                if (resp.data.depSuccTime) {
                  for (let key in resp.data.depSuccTime) {
                    this.triggerList.push(resp.data.depSuccTime[key])
                  }
                }
                upChartData.push(resp.data.depSucc.length)
              } else {
                upChartData.push(0)
              }
              // 上游第一层依赖>>依赖不满足:
              if (resp.data.depFail && resp.data.depFail.length > 0) {
                this.depFailData = resp.data.depFail
                if (resp.data.depFailFre) {
                  for (let key in resp.data.depFailFre) {
                    this.frequencyList.push(resp.data.depFailFre[key])
                  }
                }
                if (resp.data.depFailTime) {
                  for (let key in resp.data.depFailTime) {
                    this.triggerList.push(resp.data.depFailTime[key])
                  }
                }
                upChartData.push(resp.data.depFail.length)
              } else {
                upChartData.push(0)
              }
              // 依赖不满足的根源作业>>
              if (resp.data.rootFail && resp.data.rootFail.length > 0) {
                this.rootFailData = resp.data.rootFail
                if (resp.data.rootFailFre) {
                  for (let key in resp.data.rootFailFre) {
                    this.frequencyList.push(resp.data.rootFailFre[key])
                  }
                }
                if (resp.data.rootFailTime) {
                  for (let key in resp.data.rootFailTime) {
                    this.triggerList.push(resp.data.rootFailTime[key])
                  }
                }
                upChartData.push(resp.data.rootFail.length)
              } else {
                upChartData.push(0)
              }
              this.getUpstreamJob(upChartData, this.depSuccData, this.depFailData, this.rootFailData)
              // 下游影响情况>>
              this.isShowDoneStream = false
              if (resp.data.downList) {
                let downList = resp.data.downList
                if (JSON.stringify(downList[0]) !== '{}') {
                  downList.forEach(d => {
                    let temp = []
                    let cNum = 0
                    for (let key in d) {
                      let obj = {}
                      obj.platform = key.split('_')[0]
                      obj.system = key.split('_')[1]
                      obj.affectNum = d[key]
                      temp.push(obj)
                      cNum += d[key]
                    }
                    cNumCount.push(cNum)
                    this.downStreamData.push(temp)
                  })
                } else {
                  this.isShowDoneStream = true
                }
              } else {
                this.isShowDoneStream = true
              }
              setTimeout(() => {
                this.getDownstreamJob(cNumCount)
              }, 200)
            }
          }
        })
      // 影响下游关键作业>>
      this.affectDownStringKeyJobList = []
      this.isShowInflunceSys = false
      if (row.influnceSys === '无') {
        this.isShowInflunceSys = true
        this.jobCheck.isShowAnalyKeyLink = false
      } else {
        let params2 = { job: row.job }
        let httpConfig2 = {
          method: 'GET',
          url: '/merrordiagnosis/getBaseLineInfo',
          params: params2
        }
        this.$ajax(httpConfig2)
          .then(resp => {
            if (resp.status && resp.status === 200) {
              if (resp.data.length === 0) {
                this.isShowInflunceSys = true
                this.jobCheck.isShowAnalyKeyLink = false
              } else {
                this.affectDownStringKeyJobList = resp.data
                this.jobCheck.isShowAnalyKeyLink = true
                this.jobCheck.isShowCheckDepend = true // 下游信息->影响下游关键作业>>
              }
            }
          })
      }
    },
    getUpstreamJob (data, depSuccData, depFailData, rootFailData) {
      let upstreamGraph = this.$echarts.init(document.getElementById('upstreamJob'))
      let option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        xAxis: {
          type: 'category',
          data: ['上游第一层满足', '上游第一层不满足', '根源不满足']
        },
        yAxis: {
          type: 'value',
          minInterval: 1
        },
        grid: {
          x: 40,
          y: 18,
          x2: 10,
          y2: 20
        },
        series: [{
          data: data,
          type: 'bar',
          barWidth: 30,
          showBackground: true,
          backgroundStyle: {
            color: 'rgba(180,180,180,0.2)'
          },
          label: {
            show: true,
            position: 'top',
            color: '#3f3b3a'
          },
          itemStyle: {
            normal: {
              color: function (params) {
                let colorList = ['#afd582', '#528e95', '#cd4f55']
                return colorList[params.dataIndex]
              }
            }
          }
        }]
      }
      option && upstreamGraph.setOption(option)
      // 默认显示上游第一层依赖不满足
      this.$set(this.upJobInfo, 'message', '上游第一层依赖>>')
      this.$set(this.upJobInfo, 'messageSp', '依赖不满足:')
      this.upJobMsgStyle = 'upJobMsgRed'
      this.upstreamData = depFailData
      // 点击事件
      upstreamGraph.getZr().off('click') // 避免事件重复执行
      upstreamGraph.getZr().on('click', params => {
        const pointlnPixel = [params.offsetX, params.offsetY]
        if (upstreamGraph.containPixel('grid', pointlnPixel)) {
          let xIndex = upstreamGraph.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[0]
          let xData = upstreamGraph.getOption().xAxis[0].data[xIndex]
          this.upstreamData = []
          if (xData === '上游第一层满足') {
            this.upJobMsgStyle = 'upJobMsgGreen'
            this.$set(this.upJobInfo, 'message', '上游第一层依赖>>')
            this.$set(this.upJobInfo, 'messageSp', '依赖满足:')
            this.upstreamData = depSuccData
          } else if (xData === '上游第一层不满足') {
            this.upJobMsgStyle = 'upJobMsgRed'
            this.$set(this.upJobInfo, 'message', '上游第一层依赖>>')
            this.$set(this.upJobInfo, 'messageSp', '依赖不满足:')
            this.upstreamData = depFailData
          } else if (xData === '根源不满足') {
            this.$set(this.upJobInfo, 'message', '依赖不满足的根源作业>>')
            this.$set(this.upJobInfo, 'messageSp', '')
            this.upstreamData = rootFailData
          }
        }
      })
    },
    getDownstreamJob (cNumCount) {
      cNumCount[3] = 0
      if (this.affectDownStringKeyJobList) {
        cNumCount[3] = this.affectDownStringKeyJobList.length
      }
      let downstreamGraph = this.$echarts.init(document.getElementById('downstreamJob'))
      let option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        xAxis: {
          type: 'category',
          data: ['下游第一层', '下游第二层', '下游第三层', '下游关键作业']
        },
        yAxis: {
          type: 'value',
          minInterval: 1
        },
        grid: {
          x: 40,
          y: 18,
          x2: 10,
          y2: 20
        },
        series: [{
          data: cNumCount,
          type: 'bar',
          barWidth: 30,
          showBackground: true,
          backgroundStyle: {
            color: 'rgba(180,180,180,0.2)'
          },
          label: {
            show: true,
            position: 'top',
            color: '#3f3b3a'
          },
          itemStyle: {
            normal: {
              color: function (params) {
                let colorList = ['#7bc1f2', '#7bc1f2', '#7bc1f2', '#e09599']
                return colorList[params.dataIndex]
              }
            }
          }
        }]
      }
      option && downstreamGraph.setOption(option)
      // 点击事件
      downstreamGraph.getZr().off('click') // 避免事件重复执行
      downstreamGraph.getZr().on('click', params => {
        const pointlnPixel = [params.offsetX, params.offsetY]
        if (downstreamGraph.containPixel('grid', pointlnPixel)) {
          let xIndex = downstreamGraph.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[0]
          let xData = downstreamGraph.getOption().xAxis[0].data[xIndex]
          this.$set(this.showDownstreamTable, 'showFistFloor', false)
          this.$set(this.showDownstreamTable, 'showSecondFloor', false)
          this.$set(this.showDownstreamTable, 'showThirdFloor', false)
          this.$set(this.showDownstreamTable, 'showKeyJob', false)
          if (xData === '下游第一层') {
            this.$set(this.showDownstreamTable, 'showFistFloor', true)
          } else if (xData === '下游第二层') {
            this.$set(this.showDownstreamTable, 'showSecondFloor', true)
          } else if (xData === '下游第三层') {
            this.$set(this.showDownstreamTable, 'showThirdFloor', true)
          } else if (xData === '下游关键作业') {
            this.$set(this.showDownstreamTable, 'showKeyJob', true)
          }
        }
      })
    },
    // 匹配定时/频率信息
    matchedData (job, type) {
      this.triggerBean = {}
      this.frequencyBean = {}
      if (type === 'frequency') {
        this.frequencyList.forEach(d => {
          if (d.job === job) {
            this.frequencyBean = d
          }
        })
      }
      if (type === 'trigger') {
        this.triggerList.forEach(d => {
          if (d.job === job) {
            this.triggerBean = d
          }
        })
      }
    },
    // 调度配置检测
    searchSchedule (row) {
      let params = { job: row.job, errorCode: row.alarmType }
      let httpConfig = {
        method: 'GET',
        url: '/merrordiagnosis/getUdsConfigInfo',
        params: params
      }
      this.configData = {}
      this.timeTriggerData = {}
      this.frequencyData = {}
      this.weightList = []
      this.isShowFreOrTime = false
      // this.isShowTimeTriggerData = false
      // this.isShowFrequencyData = false
      // this.isShowWeight = false
      this.$ajax(httpConfig)
        .then(resp => {
          if (resp.status && resp.status === 200 && JSON.stringify(resp.data) !== '{}') {
            // 作业配置信息
            for (let key in resp.data.config) {
              this.configData[key] = resp.data.config[key]
            }
            this.configData.strategy = resp.data.system.strategy // 分发策略
            this.configData.strategyPro = resp.data.system.strategyPro
            this.configData.isPlatformServer = resp.data.system.system === '*' ? '是' : '否'
            this.configData.maxRunJob = resp.data.system.maxRunJob
            this.configData.sourceByJob = resp.data.sourceByJob === 1 ? '是' : '否'
            // 调度频率信息
            if (resp.data.timeTrigger) {
              // this.isShowFreOrTime = true
              // this.isShowTimeTriggerData = true
              for (let key in resp.data.timeTrigger) {
                this.timeTriggerData[key] = resp.data.timeTrigger[key]
              }
            }
            if (resp.data.frequency) {
              // this.isShowFreOrTime = true
              // this.isShowFrequencyData = true
              for (let key in resp.data.frequency) {
                this.frequencyData[key] = resp.data.frequency[key]
              }
            }
            // 资源规则信息
            if (resp.data.weight) {
              // this.isShowWeight = true
              this.weightList = resp.data.weight
            }
            this.$forceUpdate()
          }
        })
    },
    searchLineGraph () {
      let starttime = this.lineSearchBean.start
      let endtime = this.lineSearchBean.end
      let t = (endtime - starttime) / (1000 * 3600)
      if (starttime === '') {
        this.$Message.warning('请输入开始时间')
      } else if (endtime === '') {
        this.$Message.warning('请输入结束时间')
      } else if (starttime > endtime) {
        this.$Message.warning('开始时间大于结束时间')
      } else if (t > 3) {
        this.$Message.warning('时间差值超过3小时')
      } else {
        starttime = utils.fmtDate(starttime, 'yyyy-MM-dd hh:mm:ss')
        endtime = utils.fmtDate(endtime, 'yyyy-MM-dd hh:mm:ss')
        this.searchPlatformC(this.row, starttime, endtime)
      }
      // this.searchPlatformC(this.row,)
    },
    searchPlatformC (row, start, end) {
      this.platformconcGraph = this.$echarts.init(document.getElementById('platformconc'))
      this.platformconcGraph.showLoading({
        text: '数据正在加载...',
        textStyle: { fontSize: 30, color: '#444' }
      })
      this.nodeGraph = this.$echarts.init(document.getElementById('nodeconc'))
      this.nodeGraph.showLoading({
        text: '数据正在加载...',
        textStyle: { fontSize: 30, color: '#444' }
      })
      let params = { platform: row.platform, system: row.system, start: start, end: end, node: row.serverName, job: row.job, errorCode: row.alarmType }
      let httpConfig = {
        method: 'GET',
        url: '/merrordiagnosis/getConcurrentInfo',
        params: params
      }
      this.$ajax(httpConfig)
        .then(resp => {
          if (resp.status && resp.status === 200) {
            if (resp.data.platformStatus) {
              this.getPlatformconcData(resp.data.platformStatus)
            }
            if (resp.data.nodeStatus) {
              this.getNodeData(resp.data.nodeStatus)
            }
          }
        })
    },
    searchLog (row) {
      // if(row.alarmLogkeyContent) {
      //   this.jobCheck.isShowAnalyLog = true
      //   this.logContent = row.alarmLogkeyContent
      // }
    },
    searchKeyLink () {

    },
    searchBaseLine (keyJob) {
      const loadConfig = {
        method: 'GET',
        url: '/mbaselinelines/getKeyLine/' + keyJob
      }
      this.$ajax(loadConfig).then(resp => {
        if (resp.status && resp.status === 200) {
          this.isInitialize = false
          if (resp.data.length === 0) {
            this.isInitialize = true
            return
          }
          this.collapseData = resp.data
          this.collapseData.forEach((cData, j) => {
            this.collapseData[j].timelineItemData = []
            this.collapseData[j].baselineBean = {}
            let arr = cData.baselines
            // arr[1].todayRunningStatus = 2 // 测试
            // arr[3].estimatedEndTime = '2021-07-08 16:00' // 测试
            // console.log(arr)
            arr.forEach((bData, i) => {
              let temp = {}
              Object.assign(temp, bData)
              temp.lastStatus = cData.jobs[i].lastStatus
              temp.dispatcherTime = cData.jobs[i].dispatcherTime
              temp.startTime = cData.jobs[i].startTime
              temp.endTime = cData.jobs[i].endTime
              temp.jobDate = cData.jobs[i].jobDate
              temp.jobType = cData.jobs[i].jobType
              // cData.jobs[2].lastStatus = 'Dispatcher' // 测试
              //  temp.color = bData.todayRunningStatus === 1 ? 'green' : (cData.jobs[i].lastStatus === 'Done' ? 'gray' : (cData.jobs[i].lastStatus === 'Pending' ? 'gray' : '#ff9900'))
              // temp.color = bData.todayRunningStatus === 1 ? 'green' : (cData.jobs[i].lastStatus === 'Done' ? 'gray' : '#ff9900')
              temp.color = bData.todayRunningStatus === 1 ? 'green' : (cData.jobs[i].lastStatus === 'Done' ? 'gray' : (cData.jobs[i].lastStatus === 'Failed' ? 'red' : '#ff9900'))
              if (temp.color === '#ff9900') {
                this.waitSignalFile = false
                let size = this.collapseData[j].timelineItemData.length
                for (let a = 0; a < size; a++) {
                  this.collapseData[j].timelineItemData[a].todayRunningStatus = 1
                  this.collapseData[j].timelineItemData[a].isShowEstimatedTime = false
                  this.collapseData[j].timelineItemData[a].isShowWaitSignalFile = false
                  this.collapseData[j].timelineItemData[a].color = 'green'
                }
              }
              temp.isShowEstimatedTime = bData.todayRunningStatus !== 1
              temp.isShowWaitSignalFile = false
              if (bData.todayRunningStatus == 2 && cData.jobs[i].lastStatus == 'Done') { // 是否等待信号文件
                temp.isShowWaitSignalFile = true
                this.waitSignalFile = true
              }
              if (cData.jobs[i].lastStatus == 'Failed') {
                this.waitSignalFile = true //
              }
              this.collapseData[j].timelineItemData.push(temp)
            })
            this.keyJobBean.jobDate = cData.jobdate
            this.collapseData[j].baselineBean.rootJob = cData.originJob
          })
          if (this.waitSignalFile) { // 不显示预计完成时间 等待信号文件/Failed
            for (let i = 0; i < this.collapseData.length; i++) {
              this.collapseData[i].timelineItemData.map(t => {
                t.isShowEstimatedTime = false
              })
            }
          }
          this.keyJobBean.baselineTime = utils.fmtDate(new Date(resp.data[0].alarmTime), 'yyyy-MM-dd hh:mm:ss')
          this.formatBaselineBean(resp.data[0].alarmTime)
          setTimeout(() => {
            this.defaultPanel = ['0']
          }, 200)
        }
      })
      // this.loadOneKeyJob(keyJob)
    },
    formatBaselineBean (alarmTime) {
      this.collapseData.forEach((d, i) => {
        let index = d.baselines.length - 1
        if (d.baselines[index].estimatedEndTime !== null) {
          let stime = new Date(d.baselines[index].estimatedEndTime)
          let etime = new Date(alarmTime)
          let str1 = this.subtracTime(stime, etime)
          if (etime > stime) {
            this.collapseData[i].baselineBean.restTime = str1
            this.collapseData[i].baselineBean.status = '安全'
          } else {
            this.collapseData[i].baselineBean.restTime = '-' + str1
            this.collapseData[i].baselineBean.status = '破线'
          }
          if (!this.waitSignalFile) {
            this.collapseData[i].baselineBean.estimatedCompTime = d.baselines[index].estimatedEndTime
          }
        }
      })
    },
    changeJobTest (name) {
      if (name === 'checkDepend') {
        this.searchDepend(this.row)
      } else if (name === 'checkSchedule') {
        this.searchSchedule(this.row)
      } else if (name === 'analysisPlatPerformance') {
        let start = utils.fmtDate(new Date(new Date(this.row.alarmTime).getTime() - 2 * 60 * 60 * 1000), 'yyyy-MM-dd hh:mm:ss')
        let end = utils.fmtDate(new Date(new Date(this.row.alarmTime).getTime()), 'yyyy-MM-dd hh:mm:ss')
        this.lineSearchBean = { start: start, end: end }
        this.searchPlatformC(this.row, start, end)
      } else if (name === 'analysisLog') {
        this.searchLog(this.row)
      } else if (name === 'analysisKeyLink') {
        // this.searchKeyLink(this.row)
        if (this.affectDownStringKeyJobList) {
          this.searchBaseLine(this.affectDownStringKeyJobList[0])
        }
      }
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
    getPageParam () {
      return { formBean: this.alarmBean,
        currentPage: this.page.current,
        pageSize: this.page.size }
    },
    /**
		 * 查询
		 **/
    search () {
      let params = {}
      params = Object.assign({}, this.alarmBean)
      if (this.alarmBean.localDate) {
        params.localDate = utils.fmtDate(this.alarmBean.localDate, 'yyyy-MM-dd')
      }
      params.current = this.page.current
      params.size = this.page.size
      for (let key in params) {
        if (key === 'job' && params[key]) {
          params[key] = params[key].trim().toUpperCase()
        }
      }
      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH,
        params: params
      }
      this.loading = true
      this.$ajax(httpConfig)
        .then(resp => {
          if (resp.status && resp.status === 200) {
            this.alarmGridData = resp.data.records
            this.page.total = resp.data.total
          }
          this.loading = false
        })
    },
    subtracTime (stime, etime) {
      let result
      if (etime > stime) {
        result = Math.floor((etime - stime) / 1000)
      } else {
        result = Math.floor((stime - etime) / 1000)
      }
      let h = Math.floor(result / 3600) < 10 ? '0' + Math.floor(result / 3600) : Math.floor(result / 3600)
      let m = Math.floor(result % 3600 / 60) < 10 ? '0' + Math.floor(result % 3600 / 60) : Math.floor(result % 3600 / 60)
      let s = result % 3600 % 60 < 10 ? '0' + result % 3600 % 60 : result % 3600 % 60
      let str = h + ':' + m + ':' + s
      return str
    },
    formatTime (minute) {
      let day = 0
      let hour = 0
      if (minute > 60) {
        hour = parseInt(minute / 60)
        minute = parseInt(minute % 60)
        if (hour > 24) {
          day = parseInt(hour / 24)
          hour = parseInt(hour % 24)
        }
      }
      let result = ''
      if (minute > 0) {
        result = '' + parseInt(minute) + '分钟'
      }
      if (hour > 0) {
        result = '' + parseInt(hour) + '小时' + result
      }
      if (day > 0) {
        result = '' + parseInt(day) + '天' + result
      }
      return result
    },
    /**
		 * 清空
		 **/
    clear () {
      this.alarmBean = {}
      this.page = {
        current: 1,
        size: 10
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
      const ids = Array.from(this.selection, e => e.databaseId)
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
    cancel () {

    },
    switchTab (tabName) {
      for (let key in this.tabObj) {
        this.tabObj[key] = false
      }
      this.tabObj[tabName] = true
    },
    haltAlarm (alarmId, alarmStatus, index, row) {
      if (alarmStatus == '1') {
        this.$Message.info('请选择未处理的告警操作')
        return
      }
      if (alarmStatus == '2') {
        alarmStatus = '0'
      } else if (alarmStatus == '0') {
        alarmStatus = '2'
      }
      let httpConfig = {
        url: RESOURCE_PATH,
        data: {	alarmStatus: alarmStatus, job: row.job, authps: row.platform + row.system }
      }
      httpConfig.method = 'PUT'
      httpConfig.url = RESOURCE_PATH + '/' + alarmId
      this.$ajax(httpConfig)
        .then(resp => {
          if (resp.status && resp.status === 200) {
            this.alarmGridData[index].alarmStatus = alarmStatus
          }
        }).catch(error => {
          console.error('login exception:' + error)
        })
    },
    errorHandleOk () {
      this.$refs.alarmHandleForm.validate(valid => {
        if (!valid) return
        let httpConfig = {
          method: 'GET',
          url: RESOURCE_PATH + '/operater',
          params: { key: this.formBean.operationType, alarmId: this.formBean.id }
        }
        this.$ajax(httpConfig)
          .then(resp => {
            console.log(resp)
            if (resp.data === true) {
              // this.alarmGridData[this.formBean.index].alarmStatus = this.formBean.alarmStatus
              this.errorHandle.show = false
              this.search()
            }
          })
      })
    },
    errorHandleBack () {
      this.$refs.operationTypeSelect.classes[1]['ivu-form-item-error'] = false
      if (this.$refs.operationTypeSelect.$el.children[1].children[1]) {
        this.$refs.operationTypeSelect.$el.children[1].children[1].remove()
      }
      this.errorHandle.show = false
    },
    showErrorHandle (errorObj, index) {
      const operaterConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/getOperater',
        params: {}
      }
      this.$ajax(operaterConfig).then(resp => {
        this.formBean = {}
        this.errorHandle.show = true
        this.formBean.id = errorObj.id
        this.formBean.operationUser = sessionStorage.username
        this.formBean.operationTime = utils.fmtDate(new Date(), 'yyyy-MM-dd hh24:mm:ss')
        this.formBean = Object.assign({}, this.formBean)
        if (resp.data) {
          this.operationTypeList = resp.data
        }
      })
    },
    /**
		 * 列排序
		 **/
    changeSort (column) {
      let classcon = window.event.currentTarget.className
      // let sort, order
      let order
      if (classcon.indexOf('ivu-icon-md-arrow-dropup') > -1) {
        order = 'ASC'
      } else if (classcon.indexOf('ivu-icon-md-arrow-dropdown') > -1) {
        order = 'DESC'
      }
      this.alarmBean.order = order
      this.alarmBean.sort = column.key
      this.search()
    },
    queryAlarmList () {
      this.search()
    },
    loadErrorCodeList () {
      const loadConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/errorCodeList',
        params: {}
      }
      this.$ajax(loadConfig).then(resp => {
        if (resp.data.rows) {
          resp.data.rows.forEach(data => {
            let tmp = {}
            tmp.value = data.code
            tmp.label = data.code
            this.alarmTypelist.push(tmp)
          })
        }
      })
    },
    loaduser: function () {
      const loadConfig = {
        method: 'GET',
        url: 'user/getUserList',
        params: {}
      }
      this.$ajax(loadConfig).then(resp => {
        if (resp.data) {
          resp.data.forEach(data => {
            this.userlist[data.user_id] = data.userchname
            this.userMap[data.username] = data.userchname
          })
        }
        this.search()
      })
    },
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
      this.systemData = []
      this.$refs.refsystem.setQuery('')
      if (this.alarmBean.platform) {
        let userSystemList = this.$store.getters.getUserSystem()
        let userSystem = userSystemList[this.alarmBean.platform]
        userSystem.forEach(data => {
          let tmp = {}
          tmp.value = data
          tmp.label = data
          this.systemData.push(tmp)
        })
      }
    },
    tomain () {
      for (let key in this.transData.tabObj) {
        this.transData.tabObj[key] = false
      }
      this.transData.tabObj['alarmTab'] = true
    },
    scrollInter () {
      this.showData.splice(0, 1)
      setTimeout(() => {
        this.listData.push(this.listData[0])
        // this.showData = [];
        // this.showData.splice(0,1);
        this.showData.splice(0, 1, this.listData[0])
        this.listData.shift()
      }, 0)
    },
    enter () {
      clearInterval(this.timer)
    },
    leave () {
      this.timer = setInterval(this.scrollInter, 10000)
    },
    downloadLogfile (row) {
      let params = {}
      params.job = row.job
      params.logDir = row.logDir
      params.logName = row.logName
      params.check = '1'
      let config = {
        url: '/merrordiagnosis/downloadLogfile',
        method: 'GET',
        params: params
      }
      this.$ajax(config)
        .then(resp => {
          if (resp.data.returnCode && resp.data.returnCode == 'fail') {
            this.$Message.warning({
              content: '日志不存在,请检查作业是否为虚作业!',
              duration: 5,
              closable: true
            })
          } else {
            params.check = '0'
            utils.download('/merrordiagnosis/downloadLogfile', params)
          }
        })
    },
    downloadScriptfile (row) {
      let params = {}
      params.scriptName = row.scriptName
      params.job = row.job
      params.platform = row.platform
      params.system = row.system
      params.check = '1'
      let config = {
        url: '/merrordiagnosis/downloadScriptfile',
        method: 'GET',
        params: params
      }
      this.$ajax(config)
        .then(resp => {
          if (resp.data.returnCode && resp.data.returnCode == 'fail') {
            this.$Message.warning({
              content: '脚本不存在,请检查脚本是否存在!',
              duration: 5,
              closable: true
            })
          } else {
            params.check = '0'
            utils.download('/udsjobsteprecord/downloadScriptfile', params)
          }
        })
    },
    addZero (time) {
      let str = time
      if (time < 10) {
        str = '0' + time
      }
      return str
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
    querylocationList () {
      let loadConfig = {
        method: 'GET',
        url: '/dictionary',
        params: { dicCode: 'location' }
      }
      this.$ajax(loadConfig).then(resp => {
        this.locationList = {}
        resp.data.rows.forEach(data => {
          this.locationList[data.dicValue] = data.dicName
        })
      })
    }
  },
  computed: {

  },

  /**
	 * 视图挂载
	 **/
  mounted () {
    // this.loaduser();
    this.alarmBean.localDate = utils.fmtDate(new Date(), 'yyyy-MM-dd')
    // this.loadErrorCodeList()
    this.init()
    // this.listMUdsDutyByChDay()
    this.queryPlatform()
    this.timer = setInterval(this.scrollInter, 10000)
  },
  destroyed () {
    clearInterval(this.timer)
  }
}
</script>
<style>
#alarmList table td div{
	padding-left: 0;
	padding-right: 3px;
}
#alarmList table th div{
	padding-left: 0;
	padding-right: 3px;
}

.slide-enter-active,.slide-leave-active {
	transition: all 0.5s linear;
}
.slide-leave-to {
	transform: translateY(-20px);
}
.slide-enter {
	transform: translateY(20px);

}
.marquee-wrap {
	width: 80%;
	height: 20px;
	border-radius:0px;
	background: rgba($color: #000000, $alpha: 0.6);
	margin: 0 0;
	overflow: hidden;
	.marquee-list {
		li {
			width: 100%;
			height: 100%;
			text-overflow: ellipsis;
			overflow: hidden;
			white-space: nowrap;
			padding: 0 20px;
			list-style: none;
			line-height: 40px;
			text-align: center;
			color: #fff;
			font-size: 40px;
			font-weight: 400;
			font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
		}
	}
	.animate-up {
		transition: all 0.5s linear;
		transform: translateY(-20px);
	}
}
.duty{
	font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
	color: #2d8cf0;
	font-size: 14px;
	cursor:pointer;
	font-weight: 540;
}
.dutys {
font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
	color: #ff9900;
	font-size: 16px;
	cursor:pointer;
	font-weight: 540;
}
.list-item {
	display: block;
	margin-right: 10px;
}
.list-enter-active, .list-leave-active {
	transition: all 0.5s;
}
.list-leave-to {
	opacity: 0;
	transform:translateY(-20px);
}
.list-enter {
	opacity: 0;
	transform:translateY(20px);
}
.diag-title {
  /* color#495060;font-size:14px */
  font-size: 14px;
  color: #495060;
  /* font-weight: bold; */
}
#errorDiagnosisOp .ivu-modal .ivu-modal-content .ivu-modal-body {
  width: 100%;
}
.base-title {
  font-size: 15px;
}
.base-message {
  /* color: #464c5b; */
  /* color: #5d6b8b; */
  color: #3e3f4b;
  margin-left: 10px;
  font-size: 13px;
  font-weight: bold;
  /* white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden; */
  /* width: 50%; */
}
.line_box {
  height: 31vh;
  /* width: 700% !important; */
  width: 100%;
  padding-left: 0px;
}
.bar_box {
  height: 100px;
  width: 90%;
  padding-left: 0px;
}
.baseInfo .ivu-tabs {
  /* max-height: 50px; */
  height: 5%;
  min-height: 50px !important;
}
#timeTriggerOp span {
  font-size: 15px;
}
#frequencyOp span {
  font-size: 15px;
}
#tabs .ivu-tabs-nav .ivu-tabs-tab {
  display: inline-block;
  height: 100%;
  width: 33vmin;
  text-align: center;
  padding: 8px 16px;
  margin-right: 16px;
  box-sizing: border-box;
  cursor: pointer;
  text-decoration: none;
  position: relative;
  transition: color .3s ease-in-out;
}
.upJobMsgGreen {
  margin-left:1%;
  color:#afd582;
}
.upJobMsgRed {
  margin-left:1%;
  color:#528e95;
}
#frequencyOp .ivu-modal-body{
  width: 100% !important;
}
</style>

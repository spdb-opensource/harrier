<template>
  <div>
    <Row :gutter="20">
      <Col :xs="12" :md="8" :lg="4" v-for="(infor, i) in inforCardData" :key="`infor-${i}`" style="height: 144px;padding-bottom: 10px">
        <infor-card style="border-radius: 10px" shadow :color="infor.color" :iconUrl="infor.iconUrl" :backImgUrl="infor.backImgUrl">
          <count-to :end="infor.count"  :count-class=" i>2? 'count-style-black':'count-style' "/>
          <p :style="i>2?'color:#0B1131':'color: #FFFFFF'  ">{{ infor.title }}</p>
        </infor-card>
      </Col>
    </Row>
    <Row :gutter="20" style="margin-top: 10px;">
      <Col :md="24" :lg="12" style="margin-bottom: 10px;">
        <Card shadow style="border-radius: 10px">
          <Row :gutter="20" style="margin-top: 10px;height:52px">
            <Col :md="24" :lg="18" style="margin-bottom: 20px;">
            <p class="carousel-title">集群资源利用率统计</p>
            </Col>
            <Col :md="24" :lg="6" style="margin-bottom: 20px;">
            </Col>
          </Row>
          <div style="width: 100%;height:296px">
            <Row :gutter="20" style="margin-left:3% ;">
              <Col :md="24" :lg="6" >
                <p class="circle-title" >
                  <span class="circlr-dot" style="background-color:#FF8E16"></span>集群就绪节点</p>
                <Row class="circle-row" >
                  <div style="display:flex;text-align:center;padding:10px;">
                    <i-circle style="margin:auto;color:#FF8E16" stroke-color="#FF8E16" :percent="clusterstats.serverPercent" :size="80">
                      <span class="demo-Circle-inner" style="font-size:28px">{{clusterstats.serverPercent}}<span style="font-size:16px">%</span>
                      </span> 
                    </i-circle>
                    <div style="margin:auto">
                      <p><span style="font-size:18px">{{clusterstats.runservernum}}</span><span style="font-size:12px">/{{clusterstats.allservernum}}</span></p>
                    </div>
                  </div>
                </Row>
              </Col>
              <Col :md="24" :lg="18">
                <p class="circle-title" >
                  <span class="circlr-dot" style="background-color:#2589FF"></span>主节点资源利用率
                </p>
                <Row class="circle-row" >
                  <Col :xs="12"  :lg="24" >
                    <Carousel
                      v-if="showCarousel"
                      :autoplay="majorNodeSetting.autoplay"
                      :autoplay-speed="majorNodeSetting.autoplaySpeed"
                      v-model="value3"
                      :loop="majorNodeSetting.loop"
                      :dots="majorNodeSetting.dots"
                      :arrow="majorNodeSetting.arrow"
                      >
                      <div>
                        <div v-for="(item,index) in majorNodeList" :key="index" style="width:100%">         
                          <CarouselItem>
                            <div style="display: inline-flex; text-align: center;width:49%;">
                              <info-circle color="#2589FF" :name="item[0].name" :cpuPercent="item[0].cpuPercent"
                              :usedcpu="item[0].usedcpu" :allcpu="item[0].allcpu" :ip="item[0].ip" :memoryPercent = item[0].memoryPercent :usedmem="item[0].usedmem" :allmem="item[0].allmem">
                              </info-circle>
                            </div>
                            <div style="display: inline-flex; text-align: center;width:49%" >
                              <info-circle color="#2589FF" :name="item[1].name" :cpuPercent="item[1].cpuPercent" :usedcpu="item[1].usedcpu" :allcpu="item[1].allcpu" :ip="item[1].ip" :memoryPercent = item[1].memoryPercent :usedmem="item[1].usedmem" :allmem="item[1].allmem" v-if="item.length == 2">
                              </info-circle>
                            </div>
                          </CarouselItem>
                        </div>
                      </div>
                    </Carousel>
                  </Col>
                </Row>  
              </Col>
            </Row>
            <Row :gutter="20" style="margin-top: 10px;margin-left:2%;">
              <Col :md="24" :lg="6" style="margin-bottom: 20px;">
              <p class="circle-title show-one ">
                <span class="circlr-dot" style="background-color:#15C75B"></span>集群计算资源总利用率</p>
              <Row class="circle-row" >
                <Col :md="24" :lg="12" style="text-align:center;padding:5px">
                  <i-circle :percent="clusterstats.cpuPercent" :size="50" stroke-color="#15C75B" > 
                    <span class="demo-Circle-inner" style="color:#15C75B">{{clusterstats.cpuPercent}}%</span> 
                  </i-circle>
                  <p style="font-size:14px;font-weight: 400">cpu</p>
                  <p style="font-size: 12px;color: #B5BBCD;font-weight: 400;">{{clusterstats.usedclustercpu}}/{{clusterstats.allclustercpu}}</p>

                </Col>
                <Col :md="24" :lg="12" style="text-align:center;padding:5px">
                    <i-circle :percent="clusterstats.memoryPercent" :size="50" stroke-color="#15C75B"> 
                      <span class="demo-Circle-inner" style="color:#15C75B">{{clusterstats.memoryPercent}}%</span> 
                    </i-circle>
                    <p style="font-size:14px;font-weight: 400">内存</p>
                    <p style="font-size: 12px;color: #B5BBCD;font-weight: 400;">{{clusterstats.usedclustermem}}/{{clusterstats.allclustermem}}</p>
                </Col>
              </Row>
              </Col>
              <Col :md="24" :lg="18" style="margin-bottom: 20px;">
              <p class="circle-title" >
                <span class="circlr-dot" style="background-color:#7B4CFE;"></span>子节点资源利用率</p>
              <Row class="circle-row" >
                <Col :xs="12"  :lg="24" >
                  <Carousel
                    v-if="showCarousel"
                    :autoplay="childNodeSetting.autoplay"
                    :autoplay-speed="childNodeSetting.autoplaySpeed"
                    v-model="value4"
                    :loop="childNodeSetting.loop"
                    :dots="childNodeSetting.dots"
                    :arrow="childNodeSetting.arrow"
                  >
                    <div>
                      <div v-for="(item,index) in childNodeList" :key="index" style="width:100%">         
                        <CarouselItem  >
                          <div style="display: inline-flex; text-align: center;width:49%">
                            <info-circle color="#7B4CFE" :name="item[0].name" :cpuPercent="item[0].cpuPercent"
                              :usedcpu="item[0].usedcpu" :allcpu="item[0].allcpu" :ip="item[0].ip" :memoryPercent = item[0].memoryPercent :usedmem="item[0].usedmem" :allmem="item[0].allmem">
                            </info-circle>
                          </div>
                          <div style="display: inline-flex; text-align: center;width:49%" >
                            <info-circle color="#7B4CFE" :name="item[1].name" :cpuPercent="item[1].cpuPercent"
                              :usedcpu="item[1].usedcpu" :allcpu="item[1].allcpu" :ip="item[1].ip" :memoryPercent = item[1].memoryPercent :usedmem="item[1].usedmem" :allmem="item[1].allmem" v-if="item.length == 2">
                            </info-circle>
                          </div>
                        </CarouselItem>
                      </div>
                    </div>
                  </Carousel>
                </Col>
              </Row>
              </Col>
            </Row>
        </div>
        </Card>
      </Col>
      <Col :md="24" :lg="12" style="margin-bottom: 10px;">
        <Card shadow style="border-radius: 10px">
          <Row :gutter="20" style="margin-top: 10px;">
            <Col :md="24" :lg="18" style="margin-bottom: 20px;">
            <p class="carousel-title">各平台应用作业量</p>
            </Col>
          </Row>
          <div id="main6" ref="main6" style="width: 100%;height:300px"></div>
        </Card>
      </Col>
    </Row>
    <Row :gutter="20" style="margin-top: 10px;">
      <Col :md="24" :lg="12" style="margin-bottom: 10px;">
        <Card shadow style="border-radius: 10px">
          <Row :gutter="20" style="margin-top: 10px;">
            <Col :md="24" :lg="18" style="margin-bottom: 20px;">
              <Carousel
                :autoplay="setting.autoplay"
                :autoplay-speed="setting.autoplaySpeed"
                v-model="value1"
                :loop="setting.loop"
                :dots="setting.dots"
                :arrow="setting.arrow"
                @on-change="changePlatform"
                >
                  <div v-for="(item,index) in platformList" :key="index">
                    <CarouselItem>
                      <div class="carousel-title">
                        <span>{{item.value}}</span>
                        <span>—平台作业状态折线图</span>
                      </div>
                    </CarouselItem>
                  </div>
              </Carousel>
            </Col>
            <Col :md="24" :lg="6" style="margin-bottom: 20px;">
              <Button type="primary"  @click="platformSearch('platform')">时间筛选</Button>
            </Col>
          </Row>
          <div id="main7" ref="main7" style="width:100%;height:320px"></div>
        </Card>
      </Col>
      <Col :md="24" :lg="12" style="margin-bottom: 10px;">
         <Card shadow style="border-radius: 10px">
          <Row :gutter="20" style="margin-top: 10px;">
            <Col :md="24" :lg="18" style="margin-bottom: 20px;">
              <Carousel
              :autoplay="setting.autoplay"
              :autoplay-speed="setting.autoplaySpeed"
              v-model="value2"
              :loop="setting.loop"
              :dots="setting.dots"
              :arrow="setting.arrow"
              @on-change="changeNode"
              >
                <div v-for="(item,index) in nodeList" :key="index">
                  <CarouselItem>
                    <div >
                      <Row class="carousel-title">{{item}}—节点作业状态折线图</Row>
                    </div>
                  </CarouselItem>
                </div>
              </Carousel>
            </Col>
            <Col :md="24" :lg="6" style="margin-bottom: 20px;">
              <Button type="primary"  @click="platformSearch('node')">时间筛选</Button>
            </Col>
          </Row>
           <!-- 节点作业状态折线图 -->
          <div id="main8" ref="main8"  style="width: 100%;height:320px"></div>
        </Card>
      </Col>
    </Row>
    <Modal
    v-model="timeFormBean.show"
    :title="timeFormBean.title"
    :mask-closable="false"
    >
    <div>
      <div>
        <Form :label-width="80"  inline>
          <Row>
            <Col span="24">
              <FormItem  label="开始时间">
                <DatePicker :transfer="true" type="datetime" parse="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss" v-model="timeFormBean.startTime"></DatePicker>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <FormItem  label="结束时间">
                <DatePicker :transfer="true" type="datetime" parse="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss" v-model="timeFormBean.endTime"></DatePicker>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Button type="primary"  @click="timeSave">确定</Button>
            <Button type="primary"  @click="timeFormBean.show=false">取消</Button>
          </Row>
        </Form>
      </div>
    </div>
    <div slot="footer"></div>
  </Modal>
  </div>
</template>

<script>
import utils from '@/common/utils'
import echarts from 'echarts'
import InforCard from '_c/info-card-new'
import InfoCircle from '_c/info-circle'
import CountTo from '_c/count-to'
import { ChartPie, ChartBar } from '_c/charts'
import Example from './example.vue'
const RESOURCE_PATH = '/firstPage'
export default {
  name: 'home',
  components: {
    InfoCircle,
    InforCard,
    CountTo,
    ChartPie,
    ChartBar,
    Example
  },
  created () {
    this.$nextTick(() => {
      setTimeout(() => {
        this.getBar()
        this.queryNodeList()
        this.queryPlatformList()
      }, 20)
    })
  },
  data () {
    return {
      platformStartTime: utils.fmtDate(new Date(new Date().getTime() - 3 * 60 * 60 * 1000), 'yyyy-MM-dd hh:mm:ss'),
      platformEndTime: utils.fmtDate(new Date(), 'yyyy-MM-dd hh:mm:ss'),
      nodeStartTime: utils.fmtDate(new Date(new Date().getTime() - 3 * 60 * 60 * 1000), 'yyyy-MM-dd hh:mm:ss'),
      nodeEndTime: utils.fmtDate(new Date(), 'yyyy-MM-dd hh:mm:ss'),
      nodeFormBean: {},
      timeFormBean: { show: false },
      intervalDataspace: '',
      timeList: [ { value: '10', label: '近六个月' }, { value: '20', label: '近一年' } ],
      jobBean: {},
      haveNode: false,
      inforCardData: [
        { title: '平台总数', name: 'sumPlatforms', iconUrl: 'platformC', count: 0, color: '#2d8cf0' },
        { title: '应用总数', name: 'sumSystems', iconUrl: 'systemC', count: 0, color: '#19be6b' },
        { title: '作业总数', name: 'sumJobs', iconUrl: 'jobC', count: 0, color: '#ed3f14' },
        { title: '未完成平台', name: 'sumUndonePlatforms', iconUrl: 'unPlatformC', count: 0, color: '#E0E7FA' },
        { title: '未完成应用', name: 'sumUndoneSystems', iconUrl: 'unSystemC', count: 0, color: '#D9F3E7' },
        { title: '未完成作业', name: 'sumUndoneJobs', iconUrl: 'unJobC', count: 0, color: '#FCEBE3' }
      ],
      pieData: [
        { value: 335, name: '直接访问' },
        { value: 310, name: '邮件营销' },
        { value: 234, name: '联盟广告' },
        { value: 135, name: '视频广告' },
        { value: 1548, name: '搜索引擎' }
      ],
      barData: {
        Mon: 13253,
        Tue: 34235,
        Wed: 26321,
        Thu: 12340,
        Fri: 24643,
        Sat: 1322,
        Sun: 1324
      },
      showCarousel: false,
      jobData: [],
      dataMap: {},
      myBarGraphOption: {},
      majorNodeSetting: {
        loop: false,
        autoplay: false,
        autoplaySpeed: 20000,
        dots: 'none',
        radiusDot: false,
        trigger: 'click',
        arrow: 'hover'
      },
      childNodeSetting: {
        loop: false,
        autoplay: false,
        autoplaySpeed: 20000,
        dots: 'none',
        radiusDot: false,
        trigger: 'click',
        arrow: 'hover'
      },
      setting: {
        loop: false,
        autoplay: false,
        autoplaySpeed: 20000,
        dots: 'none',
        radiusDot: false,
        trigger: 'click',
        arrow: 'hover'
      },
      clusterstats: {},
      majorNodeList: [],
      childNodeList: [],
      platformList: [],
      announcementList: [],
      nodeList: [],
      value1: 0,
      value2: 0,
      value3: 0,
      value4: 0,
      graph7: null,
      graph8: null
    }
  },
  methods: {
    init () {
      this.setting = {
        loop: true,
        autoplay: true,
        autoplaySpeed: 20000,
        dots: 'none',
        radiusDot: false,
        trigger: 'click',
        arrow: 'hover'
      }
      this.getPsjStats()
      this.getClusterStats()
      this.graph8 = this.$echarts.init(document.getElementById('main8'))
      this.graph7 = this.$echarts.init(document.getElementById('main7'))
    },
    getBar () { // 各平台应用作业量
      const sumAllPatformJobConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/sumAllPatformJob ',
        params: {}
      }
      this.$ajax(sumAllPatformJobConfig).then(resp => {
        if (resp.data) {
          this.jobData = resp.data
          let obj = {}
          let xAxisArr = {}
          for (let i = 1; i <= Math.ceil(this.jobData.length / 10 % 10); i++) {
            let tmp = {}
            tmp[i] = []
            let xAxis = {}
            xAxis[i] = []
            let k = this.jobData.length <= 10 * i ? this.jobData.length : 10 * i
            for (let j = (i - 1) * 10; j < k; j++) {
              tmp[i][j] = {
                name: this.jobData[j].platform,
                value: this.jobData[j].num
              }
              xAxis[i][j] = this.jobData[j].platform
            }
            tmp[i] = tmp[i].filter(d => d)
            Object.assign(obj, tmp)
            xAxis[i] = xAxis[i].filter(d => d)
            Object.assign(xAxisArr, xAxis)
          }
          this.dataMap.jobCount = obj
          this.dataMap.count = Math.ceil(this.jobData.length / 10 % 10)
          this.dataMap.xAxisArr = xAxisArr
          let myBar = this.$echarts.init(document.getElementById('main6'))
          myBar.setOption(this.myBarGraph(this.dataMap))
        }
      })
    },
    myBarGraph (dataMap) {
      let yArr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
      this.myBarGraphOption = {
        baseOption: {
          timeline: {
            axisType: 'category',
            autoPlay: true,
            playInterval: 3000,
            data: yArr.splice(0, dataMap.count),
            label: {
              formatter: function (s) {
                return ''
              }
            },
            lineStyle: { // 时间轴
              show: true,
              color: 'rgba(225,225,225,0)'
            },
            checkpointStyle: { // 时间轴上的点
              symbol: 'circle',
              color: '#316bf3',
              borderColor: 'rgba(49,107,243,0.5)'
            },
            controlStyle: { // 控制
              show: false,
              color: '#343434',
              borderColor: '#343434',
              showPlayBtn: false // 播放按钮
            },
            emphasis: {
              controlStyle: { // hover样式
                color: 'rgba(225,225,225,0.8)',
                borderColor: 'rgba(225,225,225,0.8)'
              }
            }
          },
          title: {
            subtext: '' // 小标题,
          },
          tooltip: {

          },
          legend: {
            left: 'right',
            data: []
          },
          calculable: true,
          grid: {
            top: 50,
            bottom: 80,
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'shadow',
                label: {
                  show: true,
                  formatter: function (params) {
                    return params.value.replace('\n', '')
                  }
                }
              }
            }
          },
          xAxis: [
            {
              'type': 'category',
              'axisLabel': { 'interval': 0 },
              splitLine: { show: false }
            }
          ],
          yAxis: [
            {
              type: 'value',
              name: '作业数'
            }
          ],
          series: [
            {
              name: '作业数',
              type: 'bar',
              color: '#3858F9',
              barWidth: 30,
              label: {
                show: true,
                position: 'top',
                textStyle: {
                  color: '#3858F9',
                  fontSize: 12
                }
              }
            }
          ]
        },
        options: [
          {
            xAxis: [
              { data: dataMap.xAxisArr[1] }
            ],
            series: [
              { data: dataMap.jobCount[1] }
            ]
          },
          {
            xAxis: [
              { data: dataMap.xAxisArr[2] }
            ],
            series: [
              { data: dataMap.jobCount[2] }
            ]
          },
          {
            xAxis: [
              { data: dataMap.xAxisArr[3] }
            ],
            series: [
              { data: dataMap.jobCount[3] }
            ]
          },
          {
            xAxis: [
              { data: dataMap.xAxisArr[4] }
            ],
            series: [
              { data: dataMap.jobCount[4] }
            ]
          },
          {
            xAxis: [
              { data: dataMap.xAxisArr[5] }
            ],
            series: [
              { data: dataMap.jobCount[5] }
            ]
          }
        ]
      }
      return this.myBarGraphOption
    },
    changePlatform (param0, param1) {
      let platform = this.platformList[param1].value
      this.getPlatformconcData(platform) // 平台作业状态折线图
    },
    changeNode (param0, param1) {
      let node = this.nodeList[param1]
      this.getNodeData(node) // 节点作业状态折线图
    },
    // 查询节点名称数据
    queryNodeList () {
      let loadConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/ListAllWorker'
      }
      this.nodeList = []
      this.haveNode = false
      this.$ajax(loadConfig).then(resp => {
        resp.data.forEach(data => {
          this.nodeList.push(data)
        })
        if (this.nodeList.length == 0) {
          this.haveNode = true
        }
        this.getNodeData(this.nodeList[0])
      })
    },
    queryPlatformList () {
      this.platformList = []
      const loadConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/ListAllPlatform',
        params: {}
      }
      this.$ajax(loadConfig).then(resp => {
        if (resp.status && resp.status === 200 && resp.data) {
          resp.data.forEach(data1 => {
            let temp = {}
            temp.key = data1
            temp.value = data1
            temp.label = data1
            this.platformList.push(temp)
          })
          this.getPlatformconcData(this.platformList[0].value)
        }
      })
    },
    getNodeData (node) {
      let loadConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/serverJobStatus',
        params: {
          start: this.nodeStartTime,
          node: node,
          end: this.nodeEndTime
        }
      }
      this.$ajax(loadConfig).then(resp => {
        let option = {
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['runningNum'],
            top: '7%'
          },
          xAxis: {
            type: 'category',
            data: resp.data.timeList
          },
          yAxis: {
            type: 'value'
          },
          grid: {
            x: 60,
            y: 60,
            x2: 60,
            y2: 60
          },
          series: [{
            data: resp.data.running,
            type: 'line',
            symbol: 'none',
            lineStyle: {
              color: '#3858F9'
            },
            areaStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [
                  { offset: 0, color: '#3858F9' },
                  { offset: 1, color: 'white' }
                ]
              }
            }
          }
          ]
        }
        this.graph8.setOption(option, true)
        this.graph8.resize()
      })
    },
    getPlatformconcData (platform) {
      let loadConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/platformJobStatus',
        params: {
          start: this.platformStartTime,
          platform: platform,
          end: this.platformEndTime
        }
      }
      this.$ajax(loadConfig).then(resp => {
        let option = {
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['runningNum', 'dispatcherNum', 'pendingNum'],
            top: '7%',
            x: '5%'
          },
          xAxis: {
            type: 'category',
            data: resp.data['timeList']
          },
          yAxis: {
            type: 'value',
            axisLabel: {
              formatter: '{value}'
            }
          },
          grid: {
            x: 60,
            y: 60,
            x2: 60,
            y2: 60
          },

          series: [{
            name: 'runningNum',
            data: resp.data['running'],
            type: 'line',
            markPoint: {
              data: [
                { type: 'max', name: '峰值:{value}' }
              ]
            }
          },
          {
            name: 'dispatcherNum',
            data: resp.data['dispatcher'],
            type: 'line',
            markPoint: {
              data: [
                { type: 'max', name: '峰值:{value}' }
              ]
            }
          },
          {
            name: 'pendingNum',
            data: resp.data['pending'],
            type: 'line',
            markPoint: {
              data: [
                { type: 'max', name: '峰值:{value}' }
              ]
            }
          }]
        }
        this.graph7.setOption(option, true)
        this.graph7.resize()
      })
    },
    getPsjStats () {
      const psjStatsConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/psjStats',
        params: {}
      }
      this.$ajax(psjStatsConfig).then(resp => {
        if (resp.data) {
          let psjStatsKeys = Object.keys(resp.data)
          let psjStatsValues = Object.values(resp.data)
          for (let i = 0; i < this.inforCardData.length; i++) {
            for (let j = 0; j < psjStatsKeys.length; j++) {
              if (psjStatsKeys[j] === this.inforCardData[i].name) {
                this.inforCardData[i].count = psjStatsValues[j]
              }
            }
          }
        }
      })
    },
    getPercent (num, total) {
      num = parseFloat(num)
      total = parseFloat(total)
      if (isNaN(num) || isNaN(total)) {
        return '-'
      }
      return total <= 0 ? 0 : Math.round((num / total) * 10000) / 100.0
    },
    getClusterStats () {
      this.clusterstats = {}
      let workerDataList = []
      let masterDataList = []
      this.majorNodeList = []
      this.childNodeList = []
      const clusterStatsConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/clusterStats',
        params: {}
      }
      this.$ajax(clusterStatsConfig).then(resp => {
        if (resp.data) {
          let data = resp.data
          for (let i = 0; i < data.length; i++) {
            if (i === data.length - 1) {
              this.clusterstats = JSON.parse(data[i].clusterstats)
              this.clusterstats.serverPercent = this.getPercent(this.clusterstats.runservernum, this.clusterstats.allservernum)
              this.clusterstats.cpuPercent = this.getPercent(this.clusterstats.usedclustercpu, this.clusterstats.allclustercpu)
              this.clusterstats.memoryPercent = this.getPercent(this.clusterstats.usedclustermem, this.clusterstats.allclustermem)
              this.clusterstats.usedclustermem = this.getTwoNumber(this.clusterstats.usedclustermem)
              this.clusterstats.allservernum = this.getTwoNumber(this.clusterstats.allservernum)
              this.clusterstats.usedclustercpu = this.getTwoNumber(this.clusterstats.usedclustercpu)
              this.clusterstats.allclustercpu = this.getTwoNumber(this.clusterstats.allclustercpu)
              this.clusterstats.usedclustermem = this.getTwoNumber(this.clusterstats.usedclustermem)
              this.clusterstats.allclustermem = this.getTwoNumber(this.clusterstats.allclustermem)
              break
            }
            if (data[i].type === 'Worker-Server') {
              let worderData = data[i]
              worderData.cpuPercent = this.getPercent(worderData.usedcpu, worderData.allcpu)
              worderData.memoryPercent = this.getPercent(worderData.usedmem, worderData.allmem)

              worderData.usedcpu = this.getTwoNumber(worderData.usedcpu)
              worderData.allcpu = this.getTwoNumber(worderData.allcpu)

              worderData.usedmem = this.getTwoNumber(worderData.usedmem)
              worderData.allmem = this.getTwoNumber(worderData.allmem)
              workerDataList.push(worderData)
            }
            if (data[i].type === 'Master-Server') {
              let masterData = data[i]
              masterData.cpuPercent = this.getPercent(masterData.usedcpu, masterData.allcpu)
              masterData.memoryPercent = this.getPercent(masterData.usedmem, masterData.allmem)

              masterData.usedcpu = this.getTwoNumber(masterData.usedcpu)
              masterData.allcpu = this.getTwoNumber(masterData.allcpu)

              masterData.usedmem = this.getTwoNumber(masterData.usedmem)
              masterData.allmem = this.getTwoNumber(masterData.allmem)
              masterDataList.push(masterData)
            }
          }
          this.getMajorNodeList(masterDataList)
          this.getChildNodeList(workerDataList)
          this.showCarousel = true
        }
      })
    },
    getTwoNumber (num) {
      var s = num + ''
      var str = s.substring(0, s.indexOf('.') + 3)
      return str
    },
    getMajorNodeList (majorNodeData) {
      for (let i = 0; i < majorNodeData.length; i = i + 2) {
        let majorNode = []
        majorNode.push(majorNodeData[i])
        if (majorNodeData.length > i + 1) {
          majorNode.push(majorNodeData[i + 1])
        }
        this.majorNodeList.push(majorNode)
      }
      console.log(this.majorNodeList)
    },
    getChildNodeList (childNodeData) {
      for (let i = 0; i < childNodeData.length; i = i + 2) {
        let childNode = []
        childNode.push(childNodeData[i])
        if (childNodeData.length > i + 1) {
          childNode.push(childNodeData[i + 1])
        }
        this.childNodeList.push(childNode)
      }
    },
    platformSearch (type) {
      this.timeFormBean.show = true
      this.timeFormBean.type = type
      if (type === 'platform') {
        this.timeFormBean.title = '平台作业时间筛选'
        this.timeFormBean.startTime = this.platformStartTime
        this.timeFormBean.endTime = this.platformEndTime
      } else {
        this.timeFormBean.title = '节点作业时间筛选'
        this.timeFormBean.startTime = this.nodeStartTime
        this.timeFormBean.endTime = this.nodeEndTime
      }
    },
    timeSave () {
      let starttime = this.timeFormBean.startTime
      let endtime = this.timeFormBean.endTime
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
        this.timeFormBean.show = false
        if (this.timeFormBean.type === 'platform') {
          this.platformStartTime = utils.fmtDate(this.timeFormBean.startTime, 'yyyy-MM-dd hh:mm:ss')
          this.platformEndTime = utils.fmtDate(this.timeFormBean.endTime, 'yyyy-MM-dd hh:mm:ss')
          this.getPlatformconcData(this.platformList[this.value1].value) // 平台作业状态折线图
        } else {
          this.nodeStartTime = utils.fmtDate(this.timeFormBean.startTime, 'yyyy-MM-dd hh:mm:ss')
          this.nodeEndTime = utils.fmtDate(this.timeFormBean.endTime, 'yyyy-MM-dd hh:mm:ss')
          this.getNodeData(this.nodeList[this.value2])
        }
      }
    } 
  },
  mounted () {
    this.init()
  }

}
</script>

<style lang="less">
.count-style{
  font-size: 32px;
  color: #FFFFFF;
}
.count-style-black{
  font-size: 32px;
  color: #0B1131;
}
.carousel-title {
  margin-left: 5%;
  color:#516b91;
  font-size:18px;
  font-weight:600
}
.show-one {
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden
}
.circlr-dot {
    width: 5px;
    height: 5px;
    border-radius: 50%;
    vertical-align: middle;
    margin-right: 4px;
    display: inline-block
}
.circle-title {
  font-size: 14px;
  color: #000000;
  font-weight: 400;
}
.circle-row {
  background:#F8FAFD;
  height:100px;
  margin-top:10px
}
</style>

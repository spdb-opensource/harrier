<template>
  <div class="rightmenu">
    <div >
      <Button type="primary" icon="ios-arrow-back" @click="cancel" >返回</Button>
      <Tag class="imgTitle">{{ jobinfo.job }}作业画像</Tag>

    </div>
    <Row class="row">
      <Col class="float-e-margins" span="8">
        <span class="title">
          <img class="imagestyle" src="@/assets/jobimg/basic.png" />基础属性
        </span>
        <div id="basis">
          <Tag type="dot" class="tagstyle">作业：{{baseAttr.jobname}}</Tag>
          <br/>
          <Tag type="dot" class="tagstyle">所属平台：{{baseAttr.platform}}</Tag>
          <br/>
          <Tag type="dot" class="tagstyle">所属应用：{{baseAttr.system}}</Tag>
          <br/>
          <Tag type="dot" class="tagstyle">{{baseAttr.type}}</Tag>
          <Tag type="dot" class="tagstyle">所属批次：{{baseAttr.batchNum}}</Tag>
          <br/>
          <Tag type="dot" class="tagstyle">{{baseAttr.virtual_enable}}</Tag>
          <Tag type="dot" class="tagstyle">{{baseAttr.jobType}}</Tag>
          <Tag type="dot" class="tagstyle" v-if="baseAttr.contactuser!=''">作业联系人: {{baseAttr.contactuser}}</Tag>
        </div>
      </Col>

      <Col class="float-e-margins" span="8">
        <span class="title">
          <img class="imagestyle" src="@/assets/jobimg/life.png" />生命周期
        </span>
        <div id="life">
          <Tag type="dot" class="tagstyle">首次上线：{{lifeCycle.createTime}}</Tag>
          <br/>
          <Tag type="dot" class="tagstyle">最近变更：{{lifeCycle.updateTime}}</Tag>
          <br/>
          <Tag type="dot" class="tagstyle">当前版本号：{{lifeCycle.version}}</Tag>
        </div>
      </Col>

      <Col class="float-e-margins" span="8">
        <span class="title">
          <img class="imagestyle" src="@/assets/jobimg/influ.png" />影响概览
        </span>
        <div id="influ">
          <Tag type="dot" class="tagstyle">影响类型：{{influence.influjob}}</Tag>
          <Tag type="dot" class="tagstyle" v-if="jobinfo.platform === 'EDW'">{{influence.keyReportJob}}</Tag>
          <Tag type="dot" class="tagstyle" >{{influence.influenceSys}}</Tag>
          <Tag type="dot" class="tagstyle">{{influence.influenceJobType}}</Tag>
          <Tag type="dot" class="tagstyle">影响下游{{influence.sysnum}}个应用系统</Tag>
          <Tag type="dot" class="tagstyle">影响下游{{influence.jobnum}}个作业</Tag>
          <br/>
          <Tag type="dot" class="tagstyle" style="float: left;">影响TOP3应用(作业数):
          </Tag >
            <li style="margin-left: 15em;margin-top:3px;list-style-type:none;"  v-for="item in influence.top5sys" :key="item">
              <Tag class="tagstyle" style="font-size: 12px;" >{{item}}</Tag>
            </li>

          <!-- <Tag type="dot" class="tagstyle">{{ influence.top5sys }}</Tag> -->
          <!-- <Card>
              <p slot="title">影响TOP3应用(作业数):</p>
              <li v-for="item in influence.top5sys">
                {{ item }}
              </li>

          </Card> -->

        </div>
      </Col>
    </Row>

    <div class="container">
      <Row class="row">
        <Col class="jobscore">
          <div class="titlestyle">
            <h5 class="h5style">
              <img class="imagestyle" src="@/assets/jobimg/time.png" />作业评分
            </h5>
          </div>
          <JobRate :transData="jobinfo"></JobRate>
          <!--<div id="main"></div>-->
        </Col>

        <Col class="lineage">
          <div class="titlestyle">
            <h5 class="h5style">
              <img class="imagestyle" src="@/assets/jobimg/xueyuan.png" />血缘分析
            </h5>

            <div id="xueyuanMap">
              <Tag type="dot" class="tagstyle">{{lineageMsg}}</Tag>
            </div>
          </div>
          <div id="maind">
            <Lineage id="maind" :transData="transData" ref="lineage"></Lineage>
          </div>

          <div class="analysis">
            <Button type="primary" id="btnLink" v-if="jobinfo.platform === 'EDW'" @click="linktodataworks">深入分析</Button>
          </div>
        </Col>
      </Row>
      <!-- 时效分析 -->
      <Row class="row">
        <Col class="performance">
          <div class="titlestyle">
            <h5 class="h5style">
              <img class="imagestyle" src="@/assets/jobimg/radar.png" />时效分析
            </h5>
            <div id="timeLabel">
              <Tag type="dot" class="tagstyle">近一个月最高耗时：{{jobConsume.maxTime}}</Tag>
              <Tag type="dot" class="tagstyle">近一个月平均耗时：{{jobConsume.avgTime}}</Tag>
              <Tag type="dot" class="tagstyle">时效级别：{{jobinfo.effectiveClass}}</Tag>
              <Tag type="dot" class="tagstyle">整体链路层级：{{jobinfo.jobLev}}</Tag>
              <Tag type="dot" class="tagstyle">内部链路层级：{{jobinfo.sysJobLev}}</Tag>
            </div>
          </div>
          <!-- <div id="TimeLevel"> -->
          <TimeLevel :transData="jobinfo"></TimeLevel>
          <!-- </div> -->
          <div class="btnstyle">
            <Button type="primary" id="btnLink" @click="ProcessDisplayLink()">作业最长链路</Button>
            <Button type="primary" id="btnLink" @click="ProcessFenxi()">时效提升分析</Button>
            <Modal
              :title="linkTitle"
              v-model="jobinfo.link"
              class-name="vertical-center-modal"
              width="1310"
            >
              <Table
                :columns="jobinfo.columnsLink"
                :data="jobinfo.longestLinkData"
                size="small"
                ref="linktable"
              ></Table>
              <br />
              <Button type="primary" size="large" @click="exportLinkData()">
                <Icon type="ios-download-outline"></Icon>导出数据
              </Button>
            </Modal>

            <Modal
              :title="performanceTitle"
              v-model="jobinfo.performance"
              class-name="vertical-center-modal"
              width="1000"
            >

              <div>
                <Button type="primary" size="large" @click="exportSourceData(1)">
                  <Icon type="ios-download-outline"></Icon>导出数据
                </Button>
                <Tag class="tableTitle" >源系统供数时间</Tag>
              </div>
              <Table
                :columns="jobinfo.columnsSourceJob"
                :data="jobinfo.sourceJobData"
                size="small"
                ref="sourcetable"
              ></Table>

              <br />

              <div>
                <Button type="primary" size="large" @click="exportDirDepData()">
                  <Icon type="ios-download-outline"></Icon>导出数据
                </Button>
                <Tag class="tableTitle">依赖作业最晚完成时间TOP10</Tag>
              </div>
              <Table
                :columns="jobinfo.columnsDireJobTop"
                :data="jobinfo.direJobTopData"
                size="small"
                ref="dirdeptable"
              ></Table>

              <br />

              <div>
                <Button type="primary" size="large" @click="exportDepData(1)">
                  <Icon type="ios-download-outline"></Icon>导出数据
                </Button>
                <Tag class="tableTitle">上游作业运行耗时TOP10</Tag>
              </div>
              <Table
                :columns="jobinfo.columnsUpJobTop"
                :data="jobinfo.upJobTopData"
                size="small"
                ref="deptable"
              ></Table>
            </Modal>
          </div>
        </Col>
        <!-- 资源消耗 -->
        <Col class="resource">
          <div class="titlestyle">
            <h5 class="h5style">
              <img class="imagestyle" src="@/assets/jobimg/emergy.png" />资源消耗
            </h5>
            <div id="energyLabel">
              <!-- <Button type="text" class="btnBabel btn-light">作业权重：40</Button>
              <Button type="text" class="btnBabel btn-light">作业优先级：600</Button>
              <Button type="text" class="btnBabel btn-light">近1月CPU最高占用：34567</Button>
              <Button type="text" class="btnBabel btn-light">近1月CPU平均占用：30000</Button>
              <Button type="text" class="btnBabel btn-light">近1月最高耗时：00:05:36</Button>
              <Button type="text" class="btnBabel btn-light">近1月平均耗时：00:05:36</Button> -->
            </div>
          </div>
          <!-- <div id="energyMap"></div> -->
          <Resource :transData="jobinfo" class="resourceinner"></Resource>
        </Col>
      </Row>
    </div>
  </div>
</template>

<script>
import TimeLevel from './jobimgs/timeLevel'
import Resource from './jobimgs/resource'
import Lineage from './jobimgs/lineage'
import JobRate from './jobimgs/jobRate'
export default {
  components: {
    TimeLevel,
    Resource,
    Lineage,
    JobRate
  },
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
      jobinfo: {
        job: '',
        platform: '',
        system: '',
        link: false,
        performance: false,
        columnsLink: [
          {
            title: '链路层级',
            align: 'center',
            width: 100,
            // sortable: true,
            key: 'lev'
          },
          {
            title: '平台名',
            ellipsis: true,
            width: 80,
            // sortable: true,
            key: 'platform'
          },
          {
            title: '应用名',
            ellipsis: true,
            align: 'left',
            width: 90,
            // sortable: true,
            className: 'uds-job-column',
            key: 'system'
          },
          {
            title: '作业名',
            // ellipsis: true,
            align: 'left',
            width: 335,
            className: 'uds-job-column',
            key: 'job'
          },
          {
            title: '批量日期',
            ellipsis: true,
            align: 'left',
            width: 120,
            className: 'uds-job-column',
            key: 'job_date'
          },
          {
            title: '开始时间',
            // ellipsis: true,
            align: 'left',
            width: 175,
            className: 'uds-job-column',
            key: 'start_time'
          },
          {
            title: '完成时间',
            // ellipsis: true,
            align: 'left',
            width: 175,
            className: 'uds-job-column',
            key: 'end_time'
          },
          {
            title: '等待时间',
            // ellipsis: true,
            align: 'center',
            width: 100,
            className: 'uds-job-column',
            key: 'consume'
          },
          {
            title: '作业属性',
            ellipsis: true,
            align: 'center',
            width: 100,
            className: 'uds-job-column',
            key: 'virtual_enable'
          }
        ],
        longestLinkData: [],

        columnsDireJobTop: [
          {
            title: '应用名',
            ellipsis: true,
            align: 'left',
            width: 90,
            // sortable: true,
            className: 'uds-job-column',
            key: 'system'
          },
          {
            title: '作业名',
            // ellipsis: true,
            align: 'left',
            width: 350,
            className: 'uds-job-column',
            key: 'dep_job'
          },
          {
            title: '开始时间',
            // ellipsis: true,
            align: 'left',
            width: 175,
            className: 'uds-job-column',
            key: 'startTime'
          },
          {
            title: '完成时间',
            // ellipsis: true,
            align: 'left',
            width: 175,
            className: 'uds-job-column',
            key: 'endTime'
          },
          {
            title: '平均完成时间',
            // ellipsis: true,
            align: 'center',
            width: 130,
            className: 'uds-job-column',
            key: 'avgTime'
          }
        ],
        direJobTopData: [],
        columnsUpJobTop: [
          {
            title: '应用名',
            ellipsis: true,
            align: 'left',
            width: 90,
            // sortable: true,
            className: 'uds-job-column',
            key: 'system'
          },
          {
            title: '作业名',
            // ellipsis: true,
            align: 'left',
            width: 350,
            className: 'uds-job-column',
            key: 'job'
          },
          {
            title: '本次耗时',
            // ellipsis: true,
            align: 'left',
            width: 175,
            className: 'uds-job-column',
            key: 'consume'
          },
          {
            title: '最高耗时',
            // ellipsis: true,
            align: 'left',
            width: 175,
            className: 'uds-job-column',
            key: 'maxTime'
          },
          {
            title: '平均耗时',
            // ellipsis: true,
            align: 'center',
            width: 130,
            className: 'uds-job-column',
            key: 'avgTime'
          }
        ],
        upJobTopData: [],
        columnsSourceJob: [
          {
            title: '应用名',
            ellipsis: true,
            align: 'left',
            width: 90,
            // sortable: true,
            className: 'uds-job-column',
            key: 'system'
          },
          {
            title: '作业名',
            // ellipsis: true,
            align: 'left',
            width: 350,
            className: 'uds-job-column',
            key: 'job'
          },
          {
            title: '开始时间',
            // ellipsis: true,
            align: 'left',
            width: 175,
            className: 'uds-job-column',
            key: 'start_time'
          },
          {
            title: '结束时间',
            // ellipsis: true,
            align: 'left',
            width: 175,
            className: 'uds-job-column',
            key: 'end_time'
          },
          {
            title: '平均供数时间',
            // ellipsis: true,
            align: 'center',
            width: 130,
            className: 'uds-job-column',
            key: 'avgTime'
          }
        ],
        sourceJobData: [],
        jobLev: 1,
        sysJobLev: 1,
        effectiveClass: ''
      },
      jobConsume: {
        maxTime: '',
        avgTime: ''
      },
      baseAttr: {
        jobname: '',
        platform: '',
        system: '',
        type: '',
        jobType: '',
        virtual_enable: '',
        batchNum: '',
        contactuser: ''
      },
      influence: {
        sysnum: '',
        jobnum: '',
        influjob: '',
        influenceJobType: '',
        influenceSys: '',
        keyReportJob: '',
        top5sys: ''
      },
      lineageMsg: '',
      lifeCycle: {
        createTime: '',
        updateTime: '',
        version: ''
      },
      bloodUrl: ''

    }
  },
  computed: {
    // eslint-disable-next-line vue/return-in-computed-property
    linkTitle () {
      return this.jobinfo.job + ' 作业最长链路'
    },

    performanceTitle () {
      return this.jobinfo.job + ' 作业时效提升分析'
    }
  },

  methods: {
    init () {
      // console.log(this.transData)
      this.getlineageData()
    },
    getlineageData () { // 循环获得子组件数据
      let msg
      setTimeout(() => {
        msg = this.$refs.lineage.getSumData()
        if (msg.jobCnt !== -1 && msg.systemCnt !== -1) {
          this.lineageMsg = '总共' + msg.systemCnt + '个应用' + msg.jobCnt + '个作业'
        } else {
          this.getlineageData()
        }
      }, 30)
    },
    linktodataworks () {
      window.open(this.bloodUrl)
    },
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
      let backParam = { statusName: 'table', prevTab: prevTab, type: 'back' }
      if (this.transData.jobStatus) {
        queryCache.jobStatus = this.transData.jobStatus
      }
      this.$emit('switch', Object.assign({}, queryCache, backParam))
    },

    ProcessDisplayLink () {
      this.jobinfo.link = true
    },

    ProcessFenxi () {
      this.jobinfo.performance = true
    },

    getLongestLink () {
      let loadConfig = {
        method: 'GET',
        url: '/jobimg/longestLink',
        params: {
          platform: this.jobinfo.platform,
          job: this.jobinfo.job
        }
      }
      this.$ajax(loadConfig).then(resp => {
        this.jobinfo.longestLinkData = resp.data
      })
    },

    getDireJobtop () {
      let loadConfig = {
        method: 'GET',
        url: '/jobimg/getDireJobtop',
        params: {
          job: this.jobinfo.job
        }
      }
      this.$ajax(loadConfig).then(resp => {
        this.jobinfo.direJobTopData = resp.data
      })
    },

    getUpJobtop () {
      let loadConfig = {
        method: 'GET',
        url: '/jobimg/getUpJobtop',
        params: {
          platform: this.jobinfo.platform,
          job: this.jobinfo.job
        }
      }
      this.$ajax(loadConfig).then(resp => {
        this.jobinfo.upJobTopData = resp.data
      })
    },

    getSourceJob () {
      let loadConfig = {
        method: 'GET',
        url: '/jobimg/getSourceJob',
        params: {
          platform: this.jobinfo.platform,
          job: this.jobinfo.job
        }
      }
      this.$ajax(loadConfig).then(resp => {
        this.jobinfo.sourceJobData = resp.data
      })
    },

    getJobLev () {
      let loadConfig = {
        method: 'GET',
        url: '/jobimg/getJobLev',
        params: {
          platform: this.jobinfo.platform,
          system: this.jobinfo.system,
          job: this.jobinfo.job
        }
      }
      this.$ajax(loadConfig).then(resp => {
        this.jobinfo.jobLev = resp.data.joblev
        this.jobinfo.sysJobLev = resp.data.sysJobLev
      })
    },

    getJobConsume () {
      let loadConfig = {
        method: 'GET',
        url: '/jobimg/getJobConsume',
        params: {
          job: this.jobinfo.job
        }
      }
      this.$ajax(loadConfig).then(resp => {
        this.jobConsume = resp.data
      })
    },

    getBaseAttr () {
      let loadConfig = {
        method: 'GET',
        url: '/jobimg/getBaseAttr',
        params: {
          platform: this.jobinfo.platform,
          system: this.jobinfo.system,
          job: this.jobinfo.job
        }
      }
      this.$ajax(loadConfig).then(resp => {
        // console.log(resp.data)
        if (resp.data.virtual_enable == 1) {
          this.baseAttr.virtual_enable = '虚作业'
        } else {
          this.baseAttr.virtual_enable = '实作业'
        }
        // eslint-disable-next-line no-unused-expressions
        this.baseAttr.jobname = resp.data.job_name,
        this.baseAttr.platform = resp.data.platform,
        this.baseAttr.system = resp.data.system,
        this.baseAttr.type = resp.data.type,
        this.baseAttr.jobType = resp.data.jobType
        this.baseAttr.batchNum = resp.data.multi_batch
        if (resp.data.contactuser) {
          if (resp.data.contactuser.endsWith(',')) {
            this.baseAttr.contactuser = resp.data.contactuser.slice(0, resp.data.contactuser.length - 1)
          } else {
            this.baseAttr.contactuser = resp.data.contactuser
          }
        }

        if (resp.data.priority > 100) {
          this.jobinfo.effectiveClass = '高'
        } else {
          this.jobinfo.effectiveClass = '普通'
        }
      })
    },

    getInfluence () {
      let loadConfig = {
        method: 'GET',
        url: '/jobimg/getInfluence',
        params: {
          platform: this.jobinfo.platform,
          system: this.jobinfo.system,
          job: this.jobinfo.job
        }
      }
      this.$ajax(loadConfig).then(resp => {
        if (resp.data.jobnum != 0) {
          this.influence.influjob = '影响下游作业'
        } else {
          this.influence.influjob = '不影响下游作业'
        }
        this.influence.sysnum = resp.data.sysnum
        this.influence.jobnum = resp.data.jobnum
      })
    },

    getUpJobStat () {
      let loadConfig = {
        method: 'GET',
        url: '/jobimg/getUpJobStat',
        params: {
          platform: this.jobinfo.platform,
          system: this.jobinfo.system,
          job: this.jobinfo.job
        }
      }
      this.$ajax(loadConfig).then(resp => {
        console.log(resp.data)
      })
    },

    getDownJobStat () {
      let loadConfig = {
        method: 'GET',
        url: '/jobimg/getDownJobStat',
        params: {
          platform: this.jobinfo.platform,
          job: this.jobinfo.job
        }
      }
      this.$ajax(loadConfig).then(resp => {
        // console.log(resp)
        if (resp.data.keyReportJob == 1) {
          this.influence.keyReportJob = '影响关键日报'
        } else {
          this.influence.keyReportJob = '不影响关键日报'
        }
        if (resp.data.influKeyJobType == 0) {
          this.influence.influenceJobType = '不影响关键作业'
          this.influence.influenceSys = '不影响关键应用'
        } else if (resp.data.influKeyJobType == 1) {
          this.influence.influenceJobType = '影响关键作业'
          this.influence.influenceSys = '影响关键应用'
        } else {
          this.influence.influenceJobType = '此作业为关键作业'
          this.influence.influenceSys = '影响关键应用'
        }

        // resp.data.top5sys.forEach(element => {
        //   this.influence.top5sys = this.influence.top5sys + ',' + element
        // });
        // this.influence.top5sys = this.influence.top5sys.slice(1)
        this.influence.top5sys = resp.data.top5sys
      })
    },

    getBloodUrl () {
      if (this.jobinfo.platform !== 'EDW') {
        return
      }
      let loadConfig = {
        method: 'GET',
        url: '/jobimg/getBloodUrl'
      }
      this.$ajax(loadConfig).then(resp => {
        if (resp.data.bloodurl) {
          this.bloodUrl = resp.data.bloodurl
        }
      })
    },

    getLifeCycle () {
      let loadConfig = {
        method: 'GET',
        url: '/jobimg/getLifeCycle',
        params: {
          job: this.jobinfo.job
        }
      }
      this.$ajax(loadConfig).then(resp => {
        // console.log(resp)
        this.lifeCycle.createTime = resp.data.create_time
        this.lifeCycle.updateTime = resp.data.update_time
        this.lifeCycle.version = resp.data.version
      })
    },

    exportLinkData () {
      this.$refs.linktable.exportCsv({
        filename: this.jobinfo.job + '最长链路'
      })
    },
    exportSourceData () {
      this.$refs.sourcetable.exportCsv({
        filename: this.jobinfo.job + '源系统供数时间'
      })
    },
    exportDirDepData () {
      this.$refs.dirdeptable.exportCsv({
        filename: this.jobinfo.job + '依赖作业最晚完成时间TOP10'
      })
    },
    exportDepData () {
      this.$refs.deptable.exportCsv({
        filename: this.jobinfo.job + '上游作业运行耗时TOP10'
      })
    }
  },

  mounted () {
    this.init()
    this.getLongestLink()
    this.getDireJobtop()
    this.getUpJobtop()
    this.getSourceJob()
    this.getJobLev()
    this.getJobConsume()
    this.getBaseAttr()
    this.getInfluence()
    // this.getUpJobStat()
    this.getDownJobStat()
    this.getLifeCycle()
  },
  created () {
    // console.log(this.transData.jobData)
    this.jobinfo.job = this.transData.jobData.job
    this.jobinfo.platform = this.transData.jobData.platform
    this.jobinfo.system = this.transData.jobData.system
    this.getBloodUrl()
  }
}
</script>

<style  scoped>
.rightmenu {
  margin: 0 auto;
  padding: 0px;
  background-image: url("~@/assets/jobimg/bj.png");
}
/* .backtopre {
  float: right;
} */
.btnBabel {
  margin-bottom: 8px;
  margin-right: 8px;
}
.wrapper {
  /*background-color: #F5F5F5;*/
  width: 96%;
}
.container {
  width: 96%;
  margin: 0px auto;
}
.float-e-margins {
  width: 32%;
  height: 250px;
  margin-left: 1%;
  background-color: rgba(
    250,
    250,
    250,
    0.5
  ); /*background-image: linear-gradient(#F5F5F5,#D2D2D2);*/
  box-shadow: 0px 0px 20px 0px #d5dee7;
  border-radius: 20px;
  /* height: 300px; background:url('../../img/background11.png')  */
}
.title {
  font-family: Microsoft YaHei;
  font-size: 20px;
  color: #2d343a;
  padding-left: 20px;
  font-weight: bold;
}
.modal-body {
  padding: 0px;
  background: url("~@/assets/jobimg/bj.png");
}
.imagestyle {
  margin-bottom: 3px;
  margin-right: 5px;
}
.row {
  width: 100%;
  /* margin: 0px auto; */
  margin-right: 0px;
  margin-left: 5px;
  margin-top: 20px;
  margin-bottom: 10px;
}
#base {
  padding-top: 10px;
  padding-left: 20px; /*height:210px*/
}
#influ {
  padding-top: 10px;
  padding-left: 20px; /*height:210px*/
}
#life {
  padding-top: 10px;
  padding-left: 20px; /*height:210px*/
}
#maind {
  width: 750px; /*margin: 0 auto;*/
  height: 410px; /*overflow: hidden;*/
}

#main {
  width: 420px;
  margin: 0px auto;
  height: 467px;
  overflow: hidden;
}
#energyMap {
  width: 750px; /*margin: 0 auto;*/
  height: 330px;
  margin-top: 20px;
  overflow: hidden;
}
#btnLink {
  margin-bottom: 5px;
  margin-right: 5px;
  height: 34px;
  background-color: #2780e3;
  border-color: #2780e3;
}
.titlestyle {
  width: 90%;
  margin: 0px auto; /*background: #FFFFFF;*/
  border-radius: 20px;
}
.resource {
  min-height: 350px;
  float: left;
  width: 58%;
  height: 455px;
  background-color: rgba(250, 250, 250, 0.5);
  box-shadow: 0px 0px 20px 0px rgba(18, 42, 99, 0.2);
  border-radius: 20px; /*background-image: linear-gradient(#F5F5F5,#D2D2D2);*/ /*background:url('../../img/background22.png')*/
}
.h5style {
  font-family: Microsoft YaHei;
  font-size: 20px;
  color: #2d343a;
  letter-spacing: 0;
  line-height: 22px;
  padding-top: 20px; /*padding-left: 7%;*/
  font-weight: bold;
}
#TimeLevel {
  width: 520px;
  margin: 0 auto;
  height: 250px;
  overflow: hidden;
}
.resourceinner {
  position: relative;
  width: 100%;
  height: 300px;
  padding: 0px;
  margin-top: 60px;
  border-width: 0px;
  cursor: default;
}
#JobRate {
  width: 520px;
  margin: 0 auto;
  height: 600px;
  overflow: hidden;
}
.jobscore {
  min-height: 350px;
  float: left;
  width: 40%;
  height: 570px;
  background-color: rgba(250, 250, 250, 0.5);
  box-shadow: 0px 0px 20px 0px rgba(18, 42, 99, 0.3);
  border-radius: 20px;
  margin-right: 20px; /*background-image: linear-gradient(#F5F5F5,#D2D2D2);*/ /*background:url('../../img/background21.png')*/
}
.lineage {
  min-height: 350px;
  float: left;
  width: 58%;
  height: 570px;
  background-color: rgba(250, 250, 250, 0.5);
  box-shadow: 0px 0px 20px 0px rgba(18, 42, 99, 0.3);
  border-radius: 20px; /*background-image: linear-gradient(#F5F5F5,#D2D2D2);*/ /*background:url('../../img/background22.png')*/
}
.performance {
  min-height: 350px;
  float: left;
  width: 40%;
  height: 455px;
  background-color: rgba(250, 250, 250, 0.5);
  box-shadow: 0px 0px 20px 0px rgba(18, 42, 99, 0.3);
  border-radius: 20px;
  margin-right: 20px; /*background-image: linear-gradient(#F5F5F5,#D2D2D2);*/ /*background:url('@/assets/jobimg/background21.png')*/
}
.analysis {
  margin: 0 auto;
  overflow: hidden;
  margin-top: 5px;
  margin-left: 50px;
  text-align: center;
}
.btnstyle {
  /* margin: 0 auto;
  overflow: hidden;
  margin-top: 5px;
  margin-left: 50px;
  text-align: center; */

  margin: 0 auto;
  overflow: hidden;
  margin-top: 5px;
  text-align: center;
}

.tableTitle{
  margin-left: 280px;
  font-size: 18px;
}
.imgTitle{
  margin-top: 5px;
  margin-left: 25%;
  padding-top:5px;
  height: 35px;
  font-size: 25px;
  font-weight: bold;
  background-color: transparent
}
.tagstyle {
  background-color: transparent ;
  font-size: 14px;
  margin-left: 0.5em;
}
</style>

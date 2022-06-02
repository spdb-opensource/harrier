<template>
  <div>
    <!-- 数据视图 -->
    <!-- <MTable v-if="showtable" @switch="trans($event)" :transData="transData"/> -->
    <!-- 表单视图 -->
    <MForm v-if="false" @switch="trans($event)" :transData="transData">
    </MForm>
    <br/>
    <MErrorlist v-if="transData.tabObj.alarmTab" @switch="trans($event)" :transData="transData"></MErrorlist>
    <MAlarmdetail v-if="transData.tabObj.alarmdetailTab" @switch="trans($event)" :transData="transData"></MAlarmdetail>
    <!-- <MPlatform v-if="transData.tabObj.platform" @switch="trans($event)" :transData="transData"></MPlatform>
    <MPlatformuf v-if="transData.tabObj.platformuf" @switch="trans($event)" :transData="transData"></MPlatformuf>
    <MSystem v-if="transData.tabObj.system" @switch="trans($event)" :transData="transData"></MSystem>
    <MSystemuf v-if="transData.tabObj.systemuf" @switch="trans($event)" :transData="transData"></MSystemuf>
    <MJobappdetail v-if="transData.tabObj.jobappdetail" @switch="trans($event)" :transData="transData"></MJobappdetail> -->
    <!-- <MJoblist v-if="transData.tabObj.joblist" @switch="trans($event)" :transData="transData"></MJoblist> -->
    <!-- <MJoblistpending v-if="transData.tabObj.joblistpending" @switch="trans($event)" :transData="transData"></MJoblistpending>
    <MJoblistready v-if="transData.tabObj.joblistready" @switch="trans($event)" :transData="transData"></MJoblistready>
    <MJoblistrunning v-if="transData.tabObj.joblistrunning" @switch="trans($event)" :transData="transData"></MJoblistrunning>
    <MJoblistfailed v-if="transData.tabObj.joblistfailed" @switch="trans($event)" :transData="transData"></MJoblistfailed>
    <MJoblisthalt v-if="transData.tabObj.joblisthalt" @switch="trans($event)" :transData="transData"></MJoblisthalt> -->
    <!-- <MJobdetail v-if="transData.tabObj.jobdetail" @switch="trans($event)" :transData="transData"></MJobdetail> -->
    <!-- 触发 -->
    <!-- <MStream v-if="transData.tabObj.stream" @switch="trans($event)" :transData="transData"></MStream> -->
    <!-- 依赖 -->
    <!-- <MDep v-if="transData.tabObj.dep" @switch="trans($event)" :transData="transData"></MDep> -->
    <!-- 日志 -->
    <!-- <MLog v-if="transData.tabObj.log" @switch="trans($event)" :transData="transData"></MLog> -->
    <!-- 日志 -->
    <!-- <MAlarmlog v-if="transData.tabObj.alarmlog" @switch="trans($event)" :transData="transData"></MAlarmlog> -->
    <!-- 上游作业 -->
    <!-- <MUpjobs v-if="transData.tabObj.upjobs" @switch="trans($event)" :transData="transData"></MUpjobs> -->
    <!-- 下游作业 -->
    <!-- <MDownjobs v-if="transData.tabObj.downjobs" @switch="trans($event)" :transData="transData"></MDownjobs> -->
    <!-- 修改状态 -->
    <!-- <MStatus v-if="transData.tabObj.status" @switch="trans($event)" :transData="transData"></MStatus> -->
    <!-- 修改属性 -->
    <!-- <MAttr v-if="transData.tabObj.attr" @switch="trans($event)" :transData="transData"></MAttr> -->
    <!-- invoke -->
    <!-- <MInvoke v-if="transData.tabObj.invoke" @switch="trans($event)" :transData="transData"></MInvoke> -->
    <!-- forcestart -->
    <!-- <MForcestart v-if="transData.tabObj.forcestart" @switch="trans($event)" :transData="transData"></MForcestart> -->
    <!-- 脚本列表 -->
    <!-- <MScript v-if="transData.tabObj.script" @switch="trans($event)" :transData="transData"></MScript> -->

    <!-- MFrequency 频度from页面 -->
    <!-- <MFrequency v-if="transData.tabObj.frequency" @switch="trans($event)" :transData="transData"></MFrequency> -->
    <!-- MTrigger 定时from页面 -->
    <!-- <MTrigger v-if="transData.tabObj.trigger" @switch="trans($event)" :transData="transData"></MTrigger> -->
    <!-- MKilljob 定时from页面 -->
    <!-- <MKilljob v-if="transData.tabObj.killjob" @switch="trans($event)" :transData="transData"></MKilljob> -->
    <!-- 作业画像 -->
    <!-- <MImg v-if="transData.tabObj.img" @switch="trans($event)" :transData="transData"></MImg>
    <Row style="margin: 10px auto;">
        <Col span="19">
        <div class='marquee-wrap' @mouseenter="enter" @mouseout="leave">
          <transition-group name='list' tag='p'>
            <span v-for="(item, index) in showData" :key="index+''" class="list-item duty">
              <span class='dutys'>值班信息 [</span>{{item}}<span class='dutys'>]</span>
            </span>
          </transition-group>
        </div>
      </Col>
      <Col span="5">
        <div style="text-align:right;margin-right:10px;" class='dutys'>
            作业名红色表示作业禁用
        </div>
      </Col>
    </Row> -->
  </div>
</template>

<script>

// import MJobdetail from '@/view/app/jobmanager/udsjob/Jobdetail'
// import MJoblist from '@/view/app/jobmanager/udsjob/Table'
// import MJoblistpending from '@/view/app/jobmanager/udsjob/Table'
// import MJoblistrunning from '@/view/app/jobmanager/udsjob/Table'
// import MJoblistready from '@/view/app/jobmanager/udsjob/Table'
// import MJoblistfailed from '@/view/app/jobmanager/udsjob/Table'
// import MJoblisthalt from '@/view/app/jobmanager/udsjob/Table'
import MErrorlist from '@/view/app/alarm/opjobsummary/Errorlist'
// import MAlarmlog from '@/view/app/alarm/opjobsummary/Alarmlog'
import MAlarmdetail from '@/view/app/alarm/opjobsummary/Alarmdetail'
// import MTable from '@/view/app/alarm/opjobsummary/Table'
import MForm from '@/view/app/alarm/opjobsummary/Form'
// import MPlatformuf from '@/view/app/alarm/opjobsummary/Platform'
// import MPlatform from '@/view/app/alarm/opjobsummary/Platform'
// import MSystemuf from '@/view/app/alarm/opjobsummary/System'
// import MSystem from '@/view/app/alarm/opjobsummary/System'
// import MJobappdetail from '@/view/app/alarm/opjobsummary/Jobappdetail'

// import MScript from '@/view/app/jobmanager/udsjob/Script'
// import MStream from '@/view/app/jobmanager/udsjob/Stream'
// import MDep from '@/view/app/jobmanager/udsjob/Dep'
// import MLog from '@/view/app/jobmanager/udsjob/Log'
// import MUpjobs from '@/view/app/jobmanager/udsjob/Upjobs'
// import MDownjobs from '@/view/app/jobmanager/udsjob/Downjobs'
// import MStatus from '@/view/app/jobmanager/udsjob/Status'
// import MAttr from '@/view/app/jobmanager/udsjob/Attr'
// import MInvoke from '@/view/app/jobmanager/udsjob/Invoke'
// import MForcestart from '@/view/app/jobmanager/udsjob/Forcestart'

// import MFrequency from '@/view/app/jobmanager/udsjob/Frequency'
// import MTrigger from '@/view/app/jobmanager/udsjob/Trigger'
// import MKilljob from '@/view/app/jobmanager/udsjob/Killjob'
// import MImg from '@/view/app/jobmanager/udsjob/Jobimg'
export default {
  components: {
    // MTable,
    MForm,
    // MJobdetail,
    MErrorlist,
    MAlarmdetail
    // MJoblist,
    // MStream,
    // MDep,
    // MLog,
    // MUpjobs,
    // MDownjobs,
    // MStatus,
    // MAttr,
    // MInvoke,
    // MForcestart,
    // MPlatform,
    // MSystem,
    // MPlatformuf,
    // MSystemuf,
    // MJobappdetail,
    // MJoblistpending,
    // MJoblistrunning,
    // MJoblistfailed,
    // MJoblisthalt,
    // MJoblistready,
    // MScript,
    // MAlarmlog,

    // MFrequency,
    // MTrigger,
    // MKilljob,
    // MImg
  },
  props: {
    parentData: {
      type: Object,
      default: function () {
        return {
          tabObj: {
            prevTab: null,
            alarmTab: true,
            alarmdetailTab: false,
            jobdetail: false,
            joblist: false,
            stream: false,
            dep: false,
            log: false,
            upjobs: false,
            downjobs: false,
            status: false,
            attr: false,
            invoke: false,
            forcestart: false,
            platform: false,
            system: false,
            platformuf: false,
            systemuf: false,
            jobappdetail: false,
            joblistpending: false,
            joblistrunning: false,
            joblistfailed: false,
            joblisthalt: false,
            joblistready: false,
            script: false,
            alarmlog: false,
            frequency: false,
            trigger: false,
            killjob: false,
            img: false
          }
        }
      }
    }
  },
  data () {
    return {
      transData: this.parentData,
      showtable: true,
      number: 0,
      animateUp: false,
      listData: [],
      showData: [],
      timer: null,
      jobTable: [
        'joblistpending',
        'joblistrunning',
        'joblistfailed',
        'joblistready',
        'joblisthalt',
        'joblist'
      ],
      jobSubTab: [
        'log',
        'killjob',
        'trigger',
        'frequency',
        'jobdetail',
        'forcestart',
        'invoke',
        'attr',
        'downjobs',
        'upjobs',
        'dep',
        'stream',
        'img'
      ]

    }
  },
  methods: {
    trans (data) {
      this.transData = Object.assign({}, this.parentData, data)
      // this.showtable = !this.showtable;  //视图切换
      // console.log(data);
      for (let key in this.transData.tabObj) {
        if (this.transData.tabObj[key] === true) {
          this.transData.tabObj.prevTab = key
        }
        this.transData.tabObj[key] = false
      }
      if (data.type && data.type === 'back') {
        if (this.jobTable.includes(data.prevTab)) {
          this.transData.showTable = true
        } else {
          this.transData.showTable = false
        }
        if (window.sessionStorage.getItem('opjob_summary')) {
          if (data.statusName === 'joblist') {
          } else {
            if (data.prevTab === 'dep' || data.prevTab === 'stream') {
              // eslint-disable-next-line standard/computed-property-even-spacing
              this.transData.tabObj[window.sessionStorage.getItem('opjob_summary')] = true
            }
          }
        }
        this.transData.tabObj[data.prevTab] = true
      } else {
        if (this.jobTable.includes(data.statusName)) {
          this.transData.showTable = true
          // this.transData.tabObj["joblist"] = true
        } else {
          this.transData.showTable = false
        }
        if (this.jobTable.includes(this.transData.tabObj.prevTab)) {
          window.sessionStorage.setItem(
            'opjob_summary',
            this.transData.tabObj.prevTab
          )
          // this.transData.tabObj[this.transData.tabObj.prevTab] = true
        }
        if (window.sessionStorage.getItem('opjob_summary')) {
          if (data.statusName === 'joblist') {
            this.transData.tabObj['joblist'] = true
          } else {
            if (this.jobSubTab.includes(data.statusName)) {
              // eslint-disable-next-line standard/computed-property-even-spacing
              this.transData.tabObj[
                window.sessionStorage.getItem('opjob_summary')
              ] = true
            }
          }
        }
        if (data.udsjobsearch === '1') {
          if (this.jobSubTab.includes(data.statusName)) {
            this.transData.showTable = true
          } else {
            this.transData.tabObj[data.statusName] = true
          }
        } else {
          this.transData.tabObj[data.statusName] = true
        }
      }
    },
    tomain () {
      for (let key in this.transData.tabObj) {
        this.transData.tabObj[key] = false
      }
      this.transData.tabObj['alarmTab'] = true
    },
    listMUdsDutyByChDay () {
      this.$ajax.get('/duty/listDutyUserList')
        .then(resp => {
          this.listData = []
          if (resp.data) {
            for (let i = 0; i < resp.data.length; i++) {
              let info = '平台:' + resp.data[i].platform +
            ', 数据日期:' + resp.data[i].txDate +
            ',   值班人:' + resp.data[i].dutyUser +
            ',手机:' + resp.data[i].mobilePhone +
            ',   支持人员:' + resp.data[i].supportStaff +
            ',手机:' + resp.data[i].supMobilePhone
              this.listData.push(info)
            }
            this.scrollInter()
          }
        })
    },
    scrollInter () {
      this.showData.splice(0, 1)
      setTimeout(() => {
        this.listData.push(this.listData[0])
        this.showData.splice(0, 1, this.listData[0])
        this.listData.shift()
      }, 0)
    },
    enter () {
      clearInterval(this.timer)
    },
    leave () {
      this.timer = setInterval(this.scrollInter, 10000)
    }
  },
  mounted () {
    // this.listMUdsDutyByChDay()
    this.timer = setInterval(this.scrollInter, 10000)
    window.sessionStorage.removeItem('opjob_summary')
  },
  computed: {
    /* text() {
      return {
        id: this.number,
        val: this.arr[this.number]
      }
    } */
  },
  destroyed () {
    clearInterval(this.timer)
  }
}
</script>
<style>
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
    li{
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
</style>

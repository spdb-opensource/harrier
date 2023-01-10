<template>
  <div>
    <!-- 表单视图 -->
    <MForm v-if="false" @switch="trans($event)" :transData="transData">
    </MForm>
    <br/>
  <div style="background:#fff;padding:10px;border-radius: 10px">
    <MErrorlist v-if="true" @switch="trans($event)" :transData="transData"></MErrorlist>
    <MAlarmdetail v-if="transData.tabObj.alarmdetailTab" @switch="trans($event)" :transData="transData"></MAlarmdetail>
    <MJobdetail v-if="transData.tabObj.jobdetail" @switch="trans($event)" :transData="transData"></MJobdetail>
		<MJobRecord v-if="transData.tabObj.jobRecord" @switch="trans($event)" :transData="transData"></MJobRecord>
    </div>
  </div>
</template>

<script>

import MJobdetail from '@/view/spdb/job/jobList/Jobdetail'
import MJobRecord from '@/view/spdb/job/jobList/JobRecord'
import MErrorlist from '@/view/app/alarm/opjobsummary/Errorlist'
import MAlarmdetail from '@/view/app/alarm/opjobsummary/Alarmdetail'
import MForm from '@/view/app/alarm/opjobsummary/Form'
export default {
  components: {
    // MTable,
    MForm,
    MJobdetail,
    MJobRecord,
    MErrorlist,
    MAlarmdetail
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
            jobRecord: false,
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
            img: false,
            showTable: true
          }
        }
      }
    }
  },
  data () {
    return {
      transData: this.parentData,
      showTable: true,
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
        'jobRecord',
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
      if (data.statusName === 'table') {
        this.transData.tabObj.showTable = true
      } else {
        this.transData.tabObj.showTable = false
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

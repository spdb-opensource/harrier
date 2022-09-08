/* eslint-disable no-irregular-whitespace */
<template>
    <div>
		<!-- 数据视图 -->
    <MTable v-if="true" @switch="trans($event)" :transData="transData"/>
    <!-- 表单视图 -->
		<MForm v-if="statusObject.form" @switch="trans($event)" :transData="transData"></MForm>
		<!-- 触发 -->
		<MStream v-if="statusObject.stream" @switch="trans($event)" :transData="transData"></MStream>
		<!-- 依赖 -->
		<MDep v-if="statusObject.dep" @switch="trans($event)" :transData="transData"></MDep>
		<!-- 日志 -->
		<MLog v-if="statusObject.log" @switch="trans($event)" :transData="transData"></MLog>
		<!-- 上游作业 -->
		<MUpjobs v-if="statusObject.upjobs" @switch="trans($event)" :transData="transData"></MUpjobs>
		<!-- 下游作业 -->
		<MDownjobs v-if="statusObject.downjobs" @switch="trans($event)" :transData="transData"></MDownjobs>
		<!-- 修改状态 -->
		<MStatus v-if="statusObject.status" @switch="trans($event)" :transData="transData"></MStatus>
		<!-- 修改属性 -->
		<MAttr v-if="statusObject.attr" @switch="trans($event)" :transData="transData"></MAttr>
		<!-- invoke -->
		<MInvoke v-if="statusObject.invoke" @switch="trans($event)" :transData="transData"></MInvoke>
		<!-- forcestart -->
		<MForcestart v-if="statusObject.forcestart" @switch="trans($event)" :transData="transData"></MForcestart>
		<!-- MJobdetail -->
		<MJobdetail v-if="statusObject.jobdetail" @switch="trans($event)" :transData="transData"></MJobdetail>
		<!-- MScript 作业脚本列表 -->
		<MScript v-if="statusObject.script" @switch="trans($event)" :transData="transData"></MScript>
		<!-- MFrequency 频度from页面 -->
		<MFrequency v-if="statusObject.frequency" @switch="trans($event)" :transData="transData"></MFrequency>
		<!-- MTrigger 定时from页面 -->
		<MTrigger v-if="statusObject.trigger" @switch="trans($event)" :transData="transData"></MTrigger>

    <!-- MKilljob kill作业页面 -->
		<MKilljob v-if="statusObject.killjob" @switch="trans($event)" :transData="transData"></MKilljob>

    <!-- 作业画像 -->
    <MImg v-if="statusObject.img" @switch="trans($event)" :transData="transData"></MImg>

    <!-- 作业列表 -->
		<MJobRecord v-if="statusObject.jobRecord" @switch="trans($event)" :transData="transData"></MJobRecord>

    </div>
</template>

<script>
import MTable from '@/view/spdb/job/jobList/Table'
import MForm from '@/view/spdb/job/jobList/Form'
import MStream from '@/view/spdb/job/jobList/Stream'
import MDep from '@/view/spdb/job/jobList/Dep'
import MLog from '@/view/spdb/job/jobList/Log'
import MUpjobs from '@/view/spdb/job/jobList/Upjobs'
import MDownjobs from '@/view/spdb/job/jobList/Downjobs'
import MStatus from '@/view/spdb/job/jobList/Status'
import MAttr from '@/view/spdb/job/jobList/Attr'
import MInvoke from '@/view/spdb/job/jobList/Invoke'
import MForcestart from '@/view/spdb/job/jobList/Forcestart'
import MJobdetail from '@/view/spdb/job/jobList/Jobdetail'
import MScript from '@/view/spdb/job/jobList/Script'
import MFrequency from '@/view/spdb/job/jobList/Frequency'
import MTrigger from '@/view/spdb/job/jobList/Trigger'

import MKilljob from '@/view/spdb/job/jobList/Killjob'
import MImg from '@/view/spdb/job/jobList/Jobimg'

import MJobRecord from '@/view/spdb/job/jobList/JobRecord'

export default {
  components: {
    MTable,
    MForm,
    MStream,
    MDep,
    MLog,
    MUpjobs,
    MDownjobs,
    MStatus,
    MAttr,
    MInvoke,
    MForcestart,
    MJobdetail,
    MScript,
    MFrequency,
    MTrigger,
    MKilljob,
    MImg,
    MJobRecord
  	},
  props: {
    	parentData: {
      type: Object,
      default: function () {
        return {
          joblistcon: {}
        }
      }
    }
  },
  data () {
    return {
      transData: this.parentData,
      showtable: true,
      statusObject: {
        table: true,
        form: false,
        stream: false,
        dep: false,
        log: false,
        upjobs: false,
        downjobs: false,
        status: false,
        attr: false,
        invoke: false,
        forcestart: false,
        jobdetail: false,
        script: false,
        frequency: false,
        trigger: false,
        killjob: false,
        img: false,
        jobRecord: false
      }

    }
  },
  methods: {
    trans (data) {
      this.transData = Object.assign({}, this.parentData, data)
      for (let key in this.statusObject) {
        this.statusObject[key] = false
      }
      if (this.transData.prevTab && this.transData.prevTab != this.transData.curTab) {
        this.statusObject[this.transData.prevTab] = true
      } else {
        if (data.statusName === 'table') {
          this.transData.showTable = true
        } else {
          this.transData.showTable = false
        }
        this.statusObject[data.statusName] = true
      }
      // this.showtable = !this.showtable;  //视图切换
    }
  }
}
</script>

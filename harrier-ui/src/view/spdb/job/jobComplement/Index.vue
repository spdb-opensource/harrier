/* eslint-disable no-irregular-whitespace */
<template>
    <div>
		<!-- 数据视图 -->
    <MTable v-if="true" @switch="trans($event)" :transData="transData"/>
    <!-- 补数列表 -->
		<MJobComplement v-if="statusObject.jobComplement" @switch="trans($event)" :transData="transData"></MJobComplement>
    <!-- MJobdetail -->
		<MJobdetail v-if="statusObject.jobdetail" @switch="trans($event)" :transData="transData"></MJobdetail>
    </div>
</template>

<script>
import MTable from '@/view/spdb/job/jobComplement/Table'
import MJobComplement from '@/view/spdb/job/jobComplement/JobComplement'
import MJobdetail from '@/view/spdb/job/jobList/Jobdetail'

export default {
  components: {
    MTable,
    MJobComplement,
    MJobdetail
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
        complement: false,
        jobdetail: false
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

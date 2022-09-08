<template>
    <div>
		<!-- 数据视图 -->
        <MTable v-if="nodeObject.table" @switch="trans($event)" :transData="transData"/>
        <!-- 表单视图 -->
		<MForm v-if="nodeObject.form" @switch="trans($event)" :transData="transData">
		</MForm>
		<MLoglist v-if="nodeObject.loglist" @switch="trans($event)" :transData="transData">
		</MLoglist>

    </div>
</template>

<script>
import MTable from '@/view/spdb/systemsetting/serverManage/Table'
import MForm from '@/view/spdb/systemsetting/serverManage/Form'
import MLoglist from '@/view/spdb/systemsetting/serverManage/Loglist'

export default {
  components: {
    MTable,
    MForm,
    MLoglist
  	},
  props: {
    	parentData: {
      type: Object,
      default: function () {
        return {}
      }
    }
  },
  data () {
    return {
      transData: this.parentData,
      showable: true,
      nodeObject: {
        form: false,
        table: true,
        loglist: false
      }
    }
  },
  methods: {
    trans (data) {
      this.transData = Object.assign({}, this.parentData, data)
      // console.log(data);
      for (let key in this.nodeObject) {
        this.nodeObject[key] = false
      }
      this.nodeObject[data.statusName] = true
    }
  }
}
</script>

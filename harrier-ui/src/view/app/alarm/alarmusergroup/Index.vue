<template>
    <div>
		<!-- 数据视图 -->
        <MTable v-if="showtable" @switch="trans($event)" :transData="transData"/>
        <!-- 表单视图 -->
		<MForm v-if="!showtable" @switch="trans($event)" :transData="transData">
		</MForm>
    </div>
</template>

<script>
import MTable from '@/view/app/alarm/alarmusergroup/Table'
import MForm from '@/view/app/alarm/alarmusergroup/Form'

export default {
  components: {
    MTable,
    MForm
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
      showtable: true
    }
  },
  methods: {
    trans (data) {
      this.transData = Object.assign({}, this.parentData, data)
      this.showtable = !this.showtable // 视图切换
    }
  }
}
</script>

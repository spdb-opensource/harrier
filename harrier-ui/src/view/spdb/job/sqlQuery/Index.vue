<template>
    <div>
      <!-- 数据视图 -->
      <CUSTOMSQLTable v-if="showtable" @switch="trans($event)" :transData="transData"/>
      <!-- 表单视图 -->
      <CUSTOMSQLForm v-if="!showtable" @switch="trans($event)" :transData="transData">
      </CUSTOMSQLForm>
    </div>
</template>

<script>
import CUSTOMSQLTable from '@/view/spdb/job/sqlQuery/Table'
import CUSTOMSQLForm from '@/view/spdb/job/sqlQuery/Form'

export default {
  components: {
    CUSTOMSQLTable,
    CUSTOMSQLForm
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
      this.transData = Object.assign({}, this.parentData, data)
      this.showtable = !this.showtable // 视图切换
    }
  }
}
</script>

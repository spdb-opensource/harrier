<template>
    <div>
      <!-- 数据视图 -->
      <MTable v-if="showtable" @switch="trans($event)" :transData="transData"/>
    </div>
</template>

<script>
import MTable from '@/view/app/workflow/newworkflow/Table'

export default {
  components: {
    MTable
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

<template>
    <div>
        <CusTable v-if='showtable' @exit='switchShow($event)' :transData="transData" />
        <CusForm  v-if='!showtable' @exit='switchShow($event)' :transData="transData" />
    </div>
</template>

<script>
import CusTable from '@/view/spdb/authority/role/Table.vue'
import CusForm from '@/view/spdb/authority/role/Form.vue'

export default {
  components: { CusTable, CusForm },
  data: function () {
    return {
      transData: {
        type: Object,
        default: function () {
          return {}
        }
      },
      showtable: true
    }
  },
  methods: {
    switchShow: function (data) {
      this.transData = Object.assign({}, data)
      this.showtable = !this.showtable
      window.scrollTo(0, 0)
    }
  }
}
</script>

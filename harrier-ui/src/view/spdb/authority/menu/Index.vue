<template>
    <div>
        <MenuTable v-if="showtable" @switch='trans($event)'/>
        <MenuForm  v-if="!showtable" :transData="transData" @switch='trans($event)'/>
    </div>
</template>

<script>
import MenuTable from '@/view/spdb/authority/menu/Table.vue'
import MenuForm from '@/view/spdb/authority/menu/Form.vue'

export default {
  components: { MenuTable, MenuForm },
  data: function () {
    return {
      transData: {},
      showtable: true
    }
  },
  methods: {
    trans: function (data) {
      this.transData = data
      this.showtable = !this.showtable
      window.scrollTo(0, 0)
    }
  }
}
</script>

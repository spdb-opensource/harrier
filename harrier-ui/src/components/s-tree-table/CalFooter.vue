<template slot="footer">
  <div>
    <table cellspacing="0" cellpadding="0" border="0" class="ivu-table">
      <tr>
        <td v-for="(title, index) in tableTitles" :key="index">
          <div class="ivu-table-cell" :style="'height:48px;width:'+title._width+'px'">
            <span v-if="title.type == 'index'">{{label}}</span>
            <span v-if="columns.includes(title.key)">{{cal(title.key)}}</span>
          </div>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  props: {
    type: {
      type: String,
      validator(value) {
        return ["total", "avg"].includes(value);
      }
    },
    columns: {
      default() {
        return [];
      },
    },
  },
  methods: {
    cal(key) {
      const data = this.$parent.cloneData;
      let result = 0;
      data.forEach(ele => {
        if(!isNaN(ele[key])) {
          result += Number.parseFloat(ele[key]);
        };
      });
      if(this.type === 'avg'&&data.length != 0) {
        result = result/data.length;
      }
      return result;
    }
  },
  computed: {
    tableTitles() {
      return this.$parent.cloneColumns;
    },
    label() {
      switch(this.type) {
        case 'avg':
          return '平均';
        case 'total':
        default:
          return '总计';
      }
    }
  }
}
</script>


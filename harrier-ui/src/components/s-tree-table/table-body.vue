<template>
    <table cellspacing="0" cellpadding="0" border="0" :style="styleObject">
        <colgroup>
            <col v-for="(column, index) in columns" :width="setCellWidth(column, index, false)">
        </colgroup>
        <tbody :class="[prefixCls + '-tbody']">
            <template v-for="(row, index) in data">
                <table-tr v-if="childrenShow(row._index)"
                    :row="row"
                    :key="row._rowKey"
                    :prefix-cls="prefixCls"
                    @mouseenter.native.stop="handleMouseIn(row._index)"
                    @mouseleave.native.stop="handleMouseOut(row._index)"
                    @click.native="clickCurrentRow(row._index)"
                    @dblclick.native.stop="dblclickCurrentRow(row._index)">
                    <td v-for="column in columns" :class="alignCls(column, row)">
                        <Cell
                            :fixed="fixed"
                            :prefix-cls="prefixCls"
                            :row="row"
                            :key="column._columnKey"
                            :column="column"
                            :natural-index="index"
                            :index="row._index"
                            :checked="rowChecked(row._index)"
                            :disabled="rowDisabled(row._index)"
                            :expanded="rowExpanded(row._index)"
                            :folded="rowFolded(row._index)"
                            :parentlevel="rowParentLevel(row._index)"
                            :add-show="addShow(row._index)"
                        ></Cell>
                    </td>
                </table-tr>
                <tr v-if="rowExpanded(row._index)" :class="{[prefixCls + '-expanded-hidden']: fixed}">
                    <td :colspan="columns.length" :class="prefixCls + '-expanded-cell'">
                        <Expand :key="row._rowKey" :row="row" :render="expandRender" :index="row._index"></Expand>
                    </td>
                </tr>
            </template>
        </tbody>
    </table>
</template>
<script>
// todo :key="row"
import TableTr from "./table-tr.vue";
import Cell from "./cell.vue";
import Expand from "./expand.js";
import Mixin from "./mixin";

export default {
  name: "TableBody",
  mixins: [Mixin],
  components: { Cell, Expand, TableTr },
  props: {
    prefixCls: String,
    styleObject: Object,
    columns: Array,
    data: Array, // rebuildData
    objData: Object,
    columnsWidth: Object,
    fixed: {
      type: [Boolean, String],
      default: false
    }
  },
  computed: {
    expandRender() {
      let render = function() {
        return "";
      };
      for (let i = 0; i < this.columns.length; i++) {
        const column = this.columns[i];
        if (column.type && column.type === "expand") {
          if (column.render) render = column.render;
        }
      }
      return render;
    },
  },
  methods: {
    rowParentLevel(_index) {
      let i = 0;
      let row = this.objData[_index];
      while (row.hasOwnProperty("parent")) {
        i++;
        row = row.parent;
      }
      return i;
    },
    rowChecked(_index) {
      return this.objData[_index] && this.objData[_index]._isChecked;
    },
    rowDisabled(_index) {
      return this.objData[_index] && this.objData[_index]._isDisabled;
    },
    rowExpanded(_index) {
      return this.objData[_index] && this.objData[_index]._isExpanded;
    },
    rowFolded(_index) {
      return this.objData[_index] && this.objData[_index]._isFolded;
    },
    handleMouseIn(_index) {
      this.$parent.handleMouseIn(_index);
    },
    handleMouseOut(_index) {
      this.$parent.handleMouseOut(_index);
    },
    clickCurrentRow(_index) {
      this.$parent.clickCurrentRow(_index);
    },
    dblclickCurrentRow(_index) {
      this.$parent.dblclickCurrentRow(_index);
    },
    childrenShow(_index) {
      // debugger
      this.data;
      let row = this.objData[_index];
      if (row.hasOwnProperty("parent")) {
        return !row.parent._isFolded;
      } else {
        return true;
      }
    },
    // 判断显示子级
    addShow(_index){
      let row = this.objData[_index];
      // debugger
      if(row.hasOwnProperty('children')){
        return row.children.length == 0?false:true;
      }else{
        return false;
      }      
    }
  }
};
</script>

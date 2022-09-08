<template>
    <div :class="classes" ref="cell">
        <template v-if="renderType === 'index'">{{naturalIndex + 1}}</template>
        <template v-if="renderType === 'selection'">
            <Checkbox :value="checked" @click.native.stop="handleClick" @on-change="toggleSelect" :disabled="disabled"></Checkbox>
        </template>
        <template v-if="renderType === 'html'"><span v-html="row[column.key]"></span></template>
        <template v-if="renderType === 'normal'"><span>{{row[column.key]}}</span></template>
        <template v-if="renderType === 'expand' && !row._disableExpand">
            <div :class="expandCls" @click="toggleExpand">
                <Icon type="ios-arrow-right"></Icon>
            </div>
        </template>
        <!--增加列的Tree节点属性-->
        <template v-if="renderType === 'tree'">
            <div :class="expandCls" @click="toggleTree" :style="parentLevelIndent" v-show="addShow">
                <Icon v-if="this.folded" type="android-add"></Icon>
                <Icon v-else type="ios-minus-empty"></Icon>
            </div>
        </template>
        <Cell
            v-if="renderType === 'render'"
            :row="row"
            :column="column"
            :index="index"
            :render="column.render"></Cell>
    </div>
</template>
<script>
import Cell from "./expand";
import Icon from "iview/src/components/icon/icon.vue";
import Checkbox from "iview/src/components/checkbox/checkbox.vue";

export default {
  name: "TableCell",
  components: { Icon, Checkbox, Cell },
  props: {
    prefixCls: String,
    row: Object,
    column: Object,
    naturalIndex: Number, // index of rebuildData
    index: Number, // _index of data
    checked: Boolean,
    disabled: Boolean,
    expanded: Boolean,
    folded: Boolean,
    parentlevel: {
      type: Number,
      default: 0
    },
    fixed: {
      type: [Boolean, String],
      default: false
    },
    addShow:Boolean
  },
  data() {
    return {
      renderType: "",
      uid: -1,
      context: this.$parent.$parent.$parent.currentContext
    };
  },
  computed: {
    parentLevelIndent() {
      return { "text-indent": this.parentlevel*0.5+"em" };
    },
    classes() {
      return [
        `${this.prefixCls}-cell`,
        {
          [`${this.prefixCls}-hidden`]:
            !this.fixed &&
            this.column.fixed &&
            (this.column.fixed === "left" || this.column.fixed === "right"),
          [`${this.prefixCls}-cell-ellipsis`]: this.column.ellipsis || false,
          [`${this.prefixCls}-cell-with-expand`]: this.renderType === "expand"
        }
      ];
    },
    expandCls() {
      return [
        `${this.prefixCls}-cell-expand`,
        {
          [`${this.prefixCls}-cell-expand-expanded`]: this.expanded
        }
      ];
    }
  },
  methods: {
    toggleSelect() {
      this.$parent.$parent.$parent.toggleSelect(this.index);
    },
    toggleExpand() {
      this.$parent.$parent.$parent.toggleExpand(this.index);
    },
    toggleTree() {
      this.$parent.$parent.$parent.toggleTree(this.index);
    },
    handleClick() {
      // 放置 Checkbox 冒泡
    }
  },
  created() {
    if (this.column.type === "index") {
      this.renderType = "index";
    } else if (this.column.type === "selection") {
      this.renderType = "selection";
    } else if (this.column.type === "html") {
      this.renderType = "html";
    } else if (this.column.type === "expand") {
      this.renderType = "expand";
    } else if (this.column.render) {
      this.renderType = "render";
    } else if (this.column.type === "tree") {
      this.renderType = "tree";
    } else {
      this.renderType = "normal";
    }
  }
};
</script>


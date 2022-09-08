<template>
  <div>
    <Form ref="menuForm" :label-width="100" :model="formBean" :rules="formRule">
      <Row>
        <Col span="10">
          <FormItem prop="menuName" label="菜单名">
            <Input v-model="formBean.menuName"/>
          </FormItem>
        </Col>
        <Col span="10">
          <FormItem prop="menuUrl" label="菜单URL">
            <Input :disabled="isNoPath" v-model="formBean.menuUrl"/>
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="10">
          <FormItem label="菜单描述">
            <Input v-model="formBean.remark"/>
          </FormItem>
        </Col>
        <Col span="10">
          <FormItem prop="menuNo" label="菜单排序值">
            <Input v-model="formBean.menuNo"/>
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="10">
          <FormItem label="菜单图标">
            <Icon :style="iconStyles" size="20" :type="formBean.menuIconUrl"></Icon>
            <Button type="success" @click="showIconDlg()">选择</Button>
          </FormItem>
        </Col>
      </Row>
    </Form>

    <div>
      <Button type="primary" :loading="loading" icon="md-add" @click="save()">保存</Button>
      <Button type="primary" icon="ios-redo-outline" @click="cancel()">取消</Button>
    </div>

    <Modal
    v-model="iconDlgShow"
    ref="iconDlgShow"
    width="700"
    :footerHide="true"
    title="选择图标">
      <div @click="selectIcon" style="text-align:center">
        <Icon :style="iconStyles" size="24" v-for="icon in icons" :class="icon.value" :key="icon.value" :type="icon.value"></Icon>
      </div>
    </Modal>
  </div>
</template>

<script>
export default {
  props: {
    transData: Object
  },
  data: function () {
    return {
      formBean: {},
      loading: false,
      iconStyles: {
        width: '20px',
        height: '20px',
        margin: '10px',
        padding: '0px',
        cursor: 'pointer',
        verticalAlign: 'middle'
      },
      formRule: {
        menuName: [
          {
            required: true,
            message: '请输入菜单名！'
          }
        ],
        menuUrl: [
          {
            required: false,
            message: '非根菜单请输入菜单URL！'
          }
        ],
        menuNo: [
          {
            required: true,
            message: '请输入菜单排序值！'
          }
        ]
      },
      iconDlgShow: false,
      currentIcon: '',
      icons: [],
      isNoPath: this.transData.isNoPath
    }
  },
  methods: {
    init: function () {
      if (this.transData.id) {
        const getMenu = {
          method: 'GET',
          url: `menu/${this.transData.id}`
        }
        this.$ajax(getMenu)
          .then(resp => {
            this.formBean = resp.data
          })
      }
      if (this.transData.parentId) { // 子菜单赋值parentid
        this.formBean.parentMenuId = this.transData.parentId
      }
    },
    save: function () {
      this.$refs.menuForm.validate(valid => {
        if (!valid) return
        let config = {}
        let data = Object.assign({}, this.formBean)
        if (data.menuUrl) {
          data.menuUrl = data.menuUrl.replace(/\/+/g, '/')
        }
        config.data = data
        delete config.data.createTime
        delete config.data.updateTime
        if (this.formBean.menuId) {
          config.method = 'PUT'
          config.url = `/menu/${this.formBean.menuId}/update`
        } else {
          if (config.data.menuName) {
            // config.data.createUser = 'test'
            // config.data.updateUser = 'test'
            if (!config.data.parentMenuId) {
              config.data.parentMenuId = 0
              config.data.menuFlag = 0
            } else {
              config.data.menuFlag = 1
            }
            config.data.isEnable = true
          }
          config.method = 'POST'
          config.url = '/menu/add'
        }
        this.loading = true
        this.$ajax(config)
          .then(resp => {
            this.cancel()
          })
      })
    },
    cancel: function () {
      this.$emit('switch')
    },
    showIconDlg: function () {
      this.iconDlgShow = true
      let icons = []
      for (const sheet of document.styleSheets) {
        let rules
        if (sheet.cssRules) {
          rules = sheet.cssRules
        } else {
          rules = sheet.rules
        }
        for (const rule of rules) {
          const cssName = rule.selectorText // .icon-biaoji::before
          if (cssName && cssName.startsWith('.icon-')) {
            // console.log(cssName)
            // const icon = cssName.replace('.icon', 'spdb').replace('::before', '')
            const icon = cssName.replace('.icon', 'iconfont icon').replace('::before', '')
            // console.log(icon)
            icons.push({ value: icon, label: icon })
          }
        }
      }
      this.icons = icons
    },
    selectIcon: function (e) {
      if (e.target.nodeName === 'I') {
        this.currentIcon = e.target.className.replace('iconfont icon', 'spdb')
        this.formBean.menuIconUrl = this.currentIcon
        this.$refs.iconDlgShow.close()
      }
    }
  },
  mounted: function () {
    this.init()
  }
}
</script>
<style>
/* .ivu-modal-body
{
    width:700px;
    height:auto;
    word-wrap: break-word;
    padding: 16px;
    font-size: 10px;
    line-height: 1.5;
} */
</style>

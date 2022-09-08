<template>
  <div>
    <a class="sys-bar-user-a" @click="help">
      <Icon class="sys-bar-user-a" type="md-help-circle" style="font-size:22px"></Icon>
      <span class="sys-bar-user-a">帮助</span>
    </a>
    <Modal v-model="helpShow" title="帮助文档下载" width="50%" @on-cancel="closeHelp" :footer-hide="true">
        <div style="height:50px" v-if="ifNoHelp">
          <span center style="color:#8b8b8b;font-style:italic;font-size:14px">暂无帮助文档</span>
        </div>
        <div>
          <ul style="font-size:14px;margin-left:5%">
            <li  v-for="item in helpDocData" :key="item.platform">
              <a @click="downloadHelpDoc(item)">
                {{item.filename}}
                <span style="color:#8b8b8b;font-style:italic">[{{item.platform}},{{item.updateTime}}]</span>
              </a>
            </li>
          </ul>
        </div>
      </Modal>
  </div>
</template>

<script>
import './help.less'
import utils from '@/common/utils'

export default {
  data () {
    return {
      helpShow: false,
      ifNoHelp: false,
      helpDocData: []
    }
  },
  name: 'Help',
  props: {
  },
  computed: {

  },
  methods: {
    help () {
      this.helpShow = true
      this.search()
    },
    search () {
      let params = {}
      Object.assign(params, this.formBean)
      let httpConfig = {
        method: 'GET',
        url: '/moperatemanual',
        params: params
      }
      this.$ajax(httpConfig).then(resp => {
        this.helpDocData = []
        resp.data.rows.forEach(data => {
          if (data.filename !== '请上传该平台运维手册') {
            this.helpDocData.push(data)
          }
        })
        if (this.helpDocData.length === 0) {
          this.ifNoHelp = true
        }
      })
    },
    closeHelp () {
      this.helpShow = false
    },
    downloadHelpDoc (row) {
      let params = {}
      params.platform = row.platform
      utils.download('/moperatemanual/downloadfile', params)
    }
  },
  mounted () {

  }
}
</script>

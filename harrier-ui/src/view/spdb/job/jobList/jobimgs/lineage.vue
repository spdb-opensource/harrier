<template>
  <div id="maind" ref="lineage" ></div>
</template>
<script>

import elementResizeDetectorMaker from 'element-resize-detector'

export default {
  props: {
    transData: {
      type: Object,
      default: function () {
        return {}
      }
    }
  },
  data () {
    return {
      // platformCnt: 0,
      systemCnt: -1,
      jobCnt: -1,
      nodeColors: [ // { color: '#187a2f' }, { color: '#5e9a80' },
        { color: '#0aa3b5' }, { color: '#76c0cb' }, { color: '#9db2b7' }, { color: '#caa465' }, { color: '#be8663' }, { color: '#dd4c51' }, { color: '#d45a59' }, { color: '#d0545f' }, { color: '#e75b68' }, { color: '#f37674' }, { color: '#f7a128' }, { color: '#ebb40f' }, { color: '#e1c315' }, { color: '#b09733' }, { color: '#a2b029' }, { color: '#3aa255' }
      ],
      graph: null,
      option: {
        tooltip: {
          formatter: function (value) {
            return value.name
          }
        },
        series: {
          type: 'sunburst',
          center: ['50%', '50%'],
          radius: [0, '95%'], // 半径
          // sort: null,
          emphasis: {
            itemStyle: {
              focus: 'ancestor' // descendant ancestor
            }
          },
          levels: [{}, {
            r0: '15%',
            r: '55%',
            itemStyle: {
              borderWidth: 4
            }
          }, {
            r0: '55%',
            r: '60%',
            itemStyle: {
              borderWidth: 4
            },
            label: {
              color: '#2f4554',
              position: 'outside',
              padding: 3
              // silent: false
            }
          }],
          data: this.jobLineageData,
          label: {
            rotate: 'radial',
            formatter: function (param) {
              let depth = param.treePathInfo.length
              if (depth === 3) {
                let jobNameEn = param.name
                // return jobNameEn
                // let jobNameEn = ''
                let half = jobNameEn.length / 2
                return jobNameEn.substring(0, half) + '\n' + jobNameEn.substring(half)
              }
            }
          }
        }
      }

    }
  },
  methods: {
    init () {
      this.graph = this.$echarts.init(document.getElementById('maind'))
      this.getData(this.transData.jobData)
    },
    getData (jobData) {
      let params = {}
      params.platform = jobData.platform
      // params.system = jobData.system
      params.job = jobData.job
      let loadConfig = {
        method: 'GET',
        url: '/jobimg/upjobs',
        params: params
      }
      this.$ajax(loadConfig)
        .then(resp => {
          this.formatData(resp.data)
        })
    },
    formatData (upJobData) {
      this.jobCnt = upJobData.length
      let node = []
      let index = 0
      let systemNameArr = []
      if (upJobData) {
        for (let i = 0; i < upJobData.length; i++) {
          let platindex = upJobData[i].indexOf('_')
          let sysindex = upJobData[i].indexOf('_', platindex + 1)
          let systemName = upJobData[i].substring(platindex + 1, sysindex)
          index = i
          while (index > 17) {
            index = index - 18
          }
          if (systemNameArr.indexOf(systemName) === -1) {
            let nodeTemp = { name: systemName, itemStyle: this.nodeColors[index] }
            systemNameArr.push(systemName)
            node.push(nodeTemp)
          }
        }
        this.systemCnt = systemNameArr.length
        this.formatChild(upJobData, node)
      }
    },
    formatChild (upJobData, node) {
      let maxNum = node.length
      if (node.length > 10) { // 平台数
        maxNum = 10
      }
      for (let i = 0; i < maxNum; i++) {
        let childrenArr = []
        let index = 0
        for (let j = 0; j < upJobData.length; j++) {
          let systemName = upJobData[j].substring(upJobData[j].indexOf('_') + 1, upJobData[j].indexOf('_', upJobData[j].indexOf('_') + 1))
          index = j
          while (index > 17) {
            index = index - 18
          }
          if (node[i].name === systemName) {
            let obj = {
              name: upJobData[j],
              value: 1,
              itemStyle: this.nodeColors[index]
            }
            childrenArr.push(obj)
            if (childrenArr.length > 2) { // 应用数
              break
            }
          }
        }
        node[i].children = childrenArr
      }
      this.option['series']['data'] = node// node
      this.graph.setOption(this.option)
      window.addEventListener('resize', () => {
        this.graph.resize()
      })
      const _this = this
      let erd = elementResizeDetectorMaker()
      erd.listenTo(this.$refs.lineage, () => {
        _this.$nextTick(() => {
          _this.graph.resize()
        })
      })
    },
    getSumData () {
      let tmp = {}
      tmp.systemCnt = this.systemCnt
      tmp.jobCnt = this.jobCnt
      return tmp
      // let str = '总共' + this.systemCnt + '个系统' + this.jobCnt + '个作业'
      // return str
    }
  },
  mounted () {
    this.init()
  }
}
</script>

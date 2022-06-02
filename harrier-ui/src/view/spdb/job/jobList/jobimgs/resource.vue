<template>
  <div id="Resource"></div>
</template>

<script>
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
      jobResusgList: [],
      graph: '',
      option: {},
      jobstat: []
    }
  },
  methods: {
    init () {
      this.getResUsgInfo() // 获取作业对应的资源消耗情况，包括内存和CPU
      this.graph = this.$echarts.init(document.getElementById('Resource'))
    },
    // 获取作业对应的资源消耗情况，包括内存和CPU
    getResUsgInfo () {
      let job = this.transData.job
      let httpConfig = {
        method: 'GET',
        url: '/mjobresusginfo/getResUsgInfo',
        params: {
          job: job
        }
      }
      this.jobResusgList = []
      this.$ajax(httpConfig)
        .then(resp => {
          if (resp.status && resp.status === 200) {
            this.jobResusgList = resp.data
            let valueList1 = this.jobResusgList.map(function (item) {
              return item['CpuTime']
            })
            var avg = 0
            var sum = 0
            for (var i = 0; i < valueList1.length; i++) {
              sum += valueList1[i]
            }
            avg = sum / valueList1.length
            if (avg > 0) {
              this.getResUsgView()
            } else {
              this.getTimeUsgView()
            }
          }
        })
    },
    getResUsgView () {
      let data = this.jobResusgList
      let dateList = data.map(function (item) {
        return item['LogDate']
      })
      let valueList1 = data.map(function (item) {
        return item['CpuTime']
      })
      let valueList2 = data.map(function (item) {
        return item['SpaceTime']
      })
      let option = {
        title: {
          text: '近一个月资源消耗',
          x: 'center',
          align: 'right'
        },
        grid: {
          bottom: 20
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            animation: false,
            label: {
              backgroundColor: '#505765'
            }
          }
        },
        legend: {
          data: ['CPU消耗', 'Spool空间消耗'],
          x: 'right'
        },
        lineStyle: {
          width: 4
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            axisLine: { onZero: false },
            data: dateList
          }
        ],
        yAxis: [
          {
            name: 'CPU消耗(秒)',
            type: 'value'
          },
          {
            name: 'Spool空间消耗(GB)',
            type: 'value'
          }
        ],
        series: [
          {
            name: 'CPU消耗',
            type: 'line',
            smooth: true,
            animation: true,
            areaStyle: {
              color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: '#1370D8'
                },
                {
                  offset: 0.5,
                  color: '#FFFFFF'
                }
              ])
            },
            lineStyle: {
              width: 3
            },
            color: '#1370D8',
            data: valueList1,
            markPoint: {
              data: [
                { type: 'max', name: 'Max' },
                { type: 'min', name: 'Min' }
              ]
            },
            markLine: {
              data: [
                { type: 'average', name: 'Avg' }
              ]
            }
          },
          {
            name: 'Spool空间消耗',
            type: 'line',
            yAxisIndex: 1,
            smooth: true,
            animation: true,
            areaStyle: {
              color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: '#3AA255'
                },
                {
                  offset: 0.5,
                  color: '#FFFFFF'
                }
              ])
            },
            lineStyle: {
              width: 3
            },
            color: '#3AA255',
            data: valueList2,
            markPoint: {
              data: [
                { type: 'max', name: 'Max' },
                { type: 'min', name: 'Min' }
              ]
            },
            markLine: {
              data: [
                { type: 'average', name: 'Avg' }
              ]
            }
          }
        ]
      }
      this.graph.setOption(option)
      window.addEventListener('resize', () => {
        this.graph.resize()
      })
    },
    getTimeUsgView () {
      let data = this.jobResusgList
      let dateList = data.map(function (item) {
        return item['LogDate']
      })
      let valueList1 = data.map(function (item) {
        return item['RunningTime']
      })
      let option = {
        title: {
          text: '近一个月作业耗时情况',
          x: 'center',
          align: 'right'
        },
        grid: {
          bottom: 20
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            animation: false,
            label: {
              backgroundColor: '#505765'
            }
          }
        },
        legend: {
          data: ['作业运行耗时'],
          x: 'right'
        },
        lineStyle: {
          width: 4
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            axisLine: { onZero: false },
            data: dateList
          }
        ],
        yAxis: [
          {
            name: '运行耗时(分钟)',
            type: 'value'
          }
        ],
        series: [
          {
            name: '运行耗时',
            type: 'line',
            smooth: true,
            animation: true,
            areaStyle: {
              color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: '#1370D8'
                },
                {
                  offset: 0.5,
                  color: '#FFFFFF'
                }
              ])
            },
            lineStyle: {
              width: 3
            },
            color: '#1370D8',
            data: valueList1,
            markPoint: {
              data: [
                { type: 'max', name: 'Max' },
                { type: 'min', name: 'Min' }
              ]
            },
            markLine: {
              data: [
                { type: 'average', name: 'Avg' }
              ]
            }
          }
        ]
      }
      this.graph.setOption(option)
    }
  },
  mounted () {
    this.init()
  }
}
</script>
<style scoped>
  .container {
    color : rgb(3, 58, 240)
  }
</style>

<template>
  <div id="TimeLevel"></div>
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
      graph: '',
      option: {
        title: {
          text: '最近1月完成时间分布（次数）',
          textStyle: {
            fontSize: 18
          },
          x: 'center',
          y: 'top',
          textAlign: 'left'
        },
        grid: {
          bottom: 20
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        lineStyle: {
          width: 4
        },
        xAxis: {
          data: ['0-6时', '6-8时', '8-10时', '10-12时', '12时后']
          // data:MonthCompleteTimeArray
        },
        yAxis: {
          splitLine: { show: false }
        },
        series: [
          {
            name: '完成次数',
            type: 'bar',
            barWidth: 20,
            itemStyle: {
              normal: {
                barBorderRadius: 5,
                color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: '#00b4ff' },
                  { offset: 1, color: '#000073' }
                  // { offset: 0, color: 'rgba(1, 10, 255, 0.4)' },
                  // { offset: 1, color: 'rgba(1, 10, 255, 0.7)' }
                ])
              }
            },
            data: [4, 5, 6, 7, 8, 9]
            // data: MonthCompleteTimeValue,
          }
        ]
      },
      jobstat: []
    }
  },
  methods: {
    init () {
      this.graph = this.$echarts.init(document.getElementById('TimeLevel'))
      this.graph.setOption(this.option)
    },

    getData () {
      let job = this.transData.job
      let loadConfig = {
        method: 'GET',
        url: '/jobimg/timelev',
        params: {
          job: job
        }
      }
      this.$ajax(loadConfig).then(resp => {
        this.jobstat.push(resp.data['0-6时'])
        this.jobstat.push(resp.data['6-8时'])
        this.jobstat.push(resp.data['8-10时'])
        this.jobstat.push(resp.data['10-12时'])
        this.jobstat.push(resp.data['12时后'])
        this.option.series[0].data = this.jobstat
        // console.log(this.option)
        this.graph.setOption(this.option)
      })
    }
  },
  mounted () {
    this.init()
    this.getData()
  }
}
</script>
<style scoped>
  .container {
    color : rgb(3, 58, 240)
  }
</style>

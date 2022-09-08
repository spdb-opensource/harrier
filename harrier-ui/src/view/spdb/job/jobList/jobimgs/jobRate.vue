<template>
  <div id="JobRate"></div>
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
      // option: {
      //   title: {
      //     text: '最近1月完成时间分布（次数）',
      //     textStyle: {
      //       fontSize: 18
      //     },
      //     x: 'center',
      //     y: 'top',
      //     textAlign: 'left'
      //   },
      //   grid: {
      //     bottom: 20
      //   },
      //   tooltip: {
      //     trigger: 'axis',
      //     axisPointer: {
      //       type: 'shadow'
      //     }
      //   },
      //   lineStyle: {
      //     width: 4
      //   },
      //   xAxis: {
      //     data: ['0-6时', '6-8时', '8-10时', '10-12时', '12时后']
      //     // data:MonthCompleteTimeArray
      //   },
      //   yAxis: {
      //     splitLine: { show: false }
      //   },
      //   series: [
      //     {
      //       name: '完成次数',
      //       type: 'bar',
      //       barWidth: 20,
      //       itemStyle: {
      //         normal: {
      //           barBorderRadius: 5,
      //           color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
      //             { offset: 0, color: '#00b4ff' },
      //             { offset: 1, color: '#000073' }
      //             // { offset: 0, color: 'rgba(1, 10, 255, 0.4)' },
      //             // { offset: 1, color: 'rgba(1, 10, 255, 0.7)' }
      //           ])
      //         }
      //       },
      //       data: [4, 5, 6, 7, 8, 9]
      //       // data: MonthCompleteTimeValue,
      //     }
      //   ]
      // },
      jobstat: []
    }
  },
  methods: {
    init () {
      // this.graph = this.$echarts.init(document.getElementById('JobRate'))
      // this.graph.setOption(this.option)
    },

    getData () {
      let params = {}
      let job = this.transData.job
      let system = this.transData.system
      let platform = this.transData.platform
      params.job=job
      params.platform=platform
      params.system=system
      let httpConfig = {
        method: 'GET',
        url: '/jobimg/getRate',
        params: params
      }
      let rateList = {}
      this.$ajax(httpConfig).then(resp => {
        if (resp.status && resp.status === 200) {
          rateList=resp.data
          this.getRada(rateList)
        }
      })
    },
    getRada (rateList) {
      this.$nextTick(function () {
        // let distvalue = this.alarmDist
        let arr=rateList
        let distvalue = []//[7,6,8,10,1]
        let indicator = [
          { name: '血缘', max: 10 ,value: rateList.relation},
          { name: '时效', max: 10 ,value: rateList.timeliness},
          { name: '稳运', max: 10 ,value: rateList.steady},
          { name: '效能', max: 10 ,value: rateList.efficiency},
          { name: '影响', max: 10 ,value: rateList.influence},
        ]
        indicator.forEach(element => {
          if(element.value){
            distvalue.push(element.value)
          }else{
            distvalue.push(0)
          }
        })  
        let i = 0
        indicator.forEach(element => {
          if (distvalue[i] > 10) {
            indicator[i].max = distvalue[i]
          }
          i = i + 1
        })
        const right1 = {
          tooltip: {
            show: true,
            trigger: 'item'
          },
          title: {
            // text: '作业评分统计',
            textAlign: 'auto',
            x: 'center',
            textStyle: {
              color: '#516b91'
              // fontSize:'20',
            }
          },
          radar: {
            center: ['50%', '50%'], // 偏移位置
            radius: '65%',
            startAngle: 90, // 起始角度
            splitNumber: 4,
            // shape: 'circle',
            splitArea: {
              areaStyle: {
                // color: "transparent"
              }
            },
            axisLabel: {
              show: false,
              fontSize: 10,
              color: '#000',
              fontStyle: 'normal',
              fontWeight: 'normal'
            },
            axisLine: {
              show: true,
              lineStyle: {
                color: '#516b91'// #516b91 "rgba(255, 255, 255, 1)"
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                // color: 'rgba(84, 112, 200, 0.5)'515a6e
                // color: 'rgba(255, 255, 255, 0.5)'
              }
            },
            indicator: indicator
          },
          series: [
            {
              type: 'radar',
              areaStyle: {
                normal: {
                  // color: 'rgba(245,166,35,0.4)'
                  // color: 'rgba(84, 112, 200, 0.9)'
                  color: 'rgba(129, 170, 232, 0.9)'
                }
              },
              data: [
                {
                  value: distvalue,
                  itemStyle: {
                    normal: {
                      color: '#5470c8',
                      lineStyle: {
                        // color: '#fff'
                      }
                    }
                  },
                  name: '作业评分分布'
                }
              ]
            }
          ]
        }

        let graph = this.$echarts.init(document.getElementById('JobRate'))
        graph.setOption(right1)
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

<template>
	<div id="graph"></div>
</template>
<script>
export default {
  data () {
    return {
      option: {
        title: {
          text: ''
        },
        tooltip: {

          formatter: function (params) {
            var paraNmae = ''

            if (params.name.length > 25) {
              return params.name.substring(0, 20) + '<br/>' + params.name.substring(20) + '<br/>' +
                         '血缘 : ' + params.value[0] + '<br/>' +
                         '影响 : ' + params.value[1] + '<br/>' +
                         '效能 : ' + params.value[2] + '<br/>' +
                         '稳运 : ' + params.value[3] + '<br/>' +
                         '时效 : ' + params.value[4] + '<br/>'
            } else {
              return params.name + '<br/>' +
                           '血缘 : ' + params.value[0] + '<br/>' +
                           '影响 : ' + params.value[1] + '<br/>' +
                           '效能 : ' + params.value[2] + '<br/>' +
                           '稳运 : ' + params.value[3] + '<br/>' +
                           '时效 : ' + params.value[4] + '<br/>'
            }
          }

        },

        legend: {
          data: ['作业雷达图']
        },
        radar: {
          // shape: 'circle',
          center: ['50%', '50%'],
          radius: '70%',

          name: {
            textStyle: {
              color: '#fff',
              backgroundColor: '#999',
              borderRadius: 3,
              padding: [3, 5]
            },
            fontSize: 16,
            color: 'rgba(10, 134, 255, 1)'
          },
          axisLine: {
            lineStyle: {
              color: '#00b4ff' // 坐标轴颜色
            }
          },
          indicator: [
            { name: '血缘', max: 10 },
            { name: '影响', max: 10 },
            { name: '效能', max: 10 },
            { name: '稳运', max: 10 },
            { name: '时效', max: 10 }
          ]
        },
        series: [{
          name: etlJob,
          type: 'radar',
          lineStyle: {
            width: 4,
            color: 'rgb(158, 158, 68)' // 线条颜色
          },
          label: {
            fontSize: 25
          },

          areaStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
              offset: 0,
              color: '#00b4ff'
            }, {
              offset: 1,
              color: '#000073'
            }])

          }, // 阴影区域颜色
          data: [
            {
              value: [jsDataxueyuanScore, jsDatayingxiangScore, jsDatanenghaoScore, jsDatawenyunScore, jsDatashixiaoScore],
              name: etlJob.trim()
            }
          ]
        }]
      }

    }
  }
}
</script>

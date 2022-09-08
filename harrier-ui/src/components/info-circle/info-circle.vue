<template>
  <Row style="width:100%">
    <i-Col :md="24" :lg="12" style="text-align:center;">
      <p style="white-space: nowrap;text-overflow: ellipsis;overflow: hidden">{{name}}</p>
      <div style="display:flex;text-align:center;padding:10px;">
        <i-circle :percent="cpuPercent" :size="40" :stroke-color="color">
          <span class="demo-Circle-inner" >{{cpuPercent}}</span>
          <span style="font-size:10px">%</span >
        </i-circle>
        <div >
          <p class="detail-font-first">cpu</p>
          <p class="detail-font-second">{{usedcpu}}/{{allcpu}}</p>
        </div>
      </div>
    </i-Col>
    <i-Col :md="24" :lg="12" style="text-align:center;">
      <p style="color:#B5BBCD;white-space: nowrap;text-overflow: ellipsis;overflow: hidden">{{ip}}</p>
      <div style="display:flex;text-align:center;padding:10px;">
        <i-circle :percent="memoryPercent" :size="40" :stroke-color="color">
          <span class="demo-Circle-inner" >{{memoryPercent}}</span>
          <span style="font-size:10px">%</span >
        </i-circle>
        <div >
          <p class="detail-font-first">内存</p>
          <p class="detail-font-second">{{usedmem}}/{{allmem}}</p>
        </div>
      </div>
    </i-Col>
  </Row>
</template>
<script>
export default {
  name: 'InfoCircle',
  components: {
  },
  props: {
    left: {
      type: Number,
      default: 36
    },
    color: {
      type: String,
      default: '#2d8cf0'
    },
    name: {
      type: String,
      default: ''
    },
    cpuPercent: {
      type: Number,
      default: 10
    },
    usedcpu: {
      type: String,
      default: ''
    },
    allcpu: {
      type: String,
      default: ''
    },
    ip: {
      type: String,
      default: ''
    },
    memoryPercent: {
      type: Number,
      default: 0
    },
    usedmem: {
      type: String,
      default: ''
    },
    allmem: {
      type: String,
      default: ''
    }
  },
  computed: {
  }
}
</script>

<style lang="less">

.detail-font-first{
  font-size:14px;
  font-weight: 400
}
.detail-font-second{
  font-size: 12px;
  color: #B5BBCD;
  font-weight: 400;
}
</style>

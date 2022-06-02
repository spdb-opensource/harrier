<template>
  <div @keydown.enter="submit" id="login-header" class="note">
    <div>
    <!-- <div class="sys-bar-logo">
      <Row class="sys-bar-logo-row">
        <Col span="8" class="sys-bar-logo-row">
         <img src="../../../static/img/logobg-white.png" />
        </Col>
        <!-- <Col offset="4" span="12">
          <Row>
            <div class="logoTitle_m1">浦发银行</div>
            <div class="logoTitle_m2">SPD BANK</div>
          </Row>
        </Col>
      </Row>
    </div> -->
    <!-- <div class="sys-bar-logoline">
      <img src="../../../static/img/logoline.png" />
    </div> -->
    <div class="sys-bar-title">
      <div>{{ homeTitle }}</div>
    </div>
      <Row style="height:100%;">
     <Col span="1">
        &nbsp;
      </Col>
       <Col span="14">
            <div >
              <img src="../../../static/img/login_bg_right.png" class="flip-horizontal">
            </div>
        </Col>
      <Col span="9" >
              <Card icon="log-in" title="欢迎登录" :bordered="false" class="login_old" style="margin-top:30%;height: 250px;">
                <div class="form-con">
                  <login-form @on-success-valid="handleSubmit"></login-form>
                </div>
                <Button type="text" size="small" style="float:left" @click="register()">注册</Button>
              </Card>
        </Col>

        </Row>
    </div>
  </div>
</template>

<script>
import LoginForm from '_c/login-form'
import { mapActions } from 'vuex'
// import axios from 'axios'
export default {
  data () {
    return {
      homeTitle: 'Harrier',
      note: {
        backgroundImage: "url('static/img/header.jpg')",
        backgroundRepeat: 'no-repeat'
      },
      formBean: {},
      login: '登录',
      userPlatform: new Set(),
      userSystem: {}
    }
  },
  components: {
    LoginForm
  },
  methods: {
    ...mapActions([
      'handleLogin',
      'getUserInfo'
    ]),
    handleSubmit ({ userName, password }) {
      let params = {}
      params.username = userName
      params.password = password
      let httpConfig = {
        method: 'GET',
        url: '/login',
        params: params
      }
      this.$ajax(httpConfig).then(resp => {
        let req = resp.data
        if (!req.success) {
          this.$Modal.error({
            title: '提示',
            content: req.msg
          })
          return
        }
        this.getUser(userName)
        this.$store.dispatch('setUserToken', req.data)
        this.$router.push({
          name: this.$config.homeName
        })
      })
    },
    getUser (usercode) {
      let params = {}
      params.usercode = usercode

      let httpConfig = {
        method: 'GET',
        url: '/user/loadByUserCode',
        params: params
      }
      this.$ajax(httpConfig).then(resp => {
        let req = resp.data
        if (!req) {
          return
        }
        this.getUserPlatform(req.userId)
        this.$store.dispatch('setUserInfo', req)
      })
    },
    getUserPlatform (userId) {
      const httpConfig = {
        method: 'GET',
        url: `/user/${userId}/platform/permiss`
      }
      this.$ajax(httpConfig)
        .then(resp => {
          if (resp.data) {
            resp.data.forEach(async permiss => {
              this.userPlatform.add(permiss.platform)
              let temp = new Set()
              if (this.userSystem[permiss.platform]) {
                temp = this.userSystem[permiss.platform]
              }
              if (permiss.systems === '*') {
                const systemConfig = {
                  method: 'GET',
                  url: '/udsSystem/selectSystem',
                  params: { platform: permiss.platform }
                }
                await this.$ajax(systemConfig)
                  .then(resp => {
                    resp.data.forEach(data => {
                      if (permiss.platform === data.platform && data.systems !== '*') {
                        temp.add(data.systems)
                      }
                    })
                  })
              } else {
                temp.add(permiss.systems)
              }
              this.userSystem[permiss.platform] = temp
            })
          }
          this.userPlatform.forEach(data => {
            let temp = this.userSystem[data]
            this.userSystem[data] = Array.from(temp)
          })
          const data = {
            userPlatform: Array.from(this.userPlatform),
            userSystem: this.userSystem
          }
          this.$store.dispatch('setUserSystem', data)
        })
    },
    register () {
      this.$router.push({ path: '/register' })
    }
  }
}
</script>

<style lang="less" scoped>
@import url("./login.less");
.sys-bar-title {
  color: #fff;
  width: 600px;
  position: absolute;
  // top: 8px;
  // left: 200px;
  left: 60px;
  z-index: 999;
  height: 60px;
  text-align: left;
  line-height: 54px;
  margin: 0 0px;
  font-size: 24px;
  letter-spacing: 0.1em;

  background-repeat: no-repeat;
}
.ivu-btn-primary{
  background-color: #376eff;
  border-color: #376eff;
  margin-top: 3%;
}
.flip-horizontal {
  width: 90%;
}
</style>

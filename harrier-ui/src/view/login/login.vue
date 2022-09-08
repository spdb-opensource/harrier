<template>
  <div id="login-header" class="note">
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
              <Card icon="log-in" title="欢迎登录" :bordered="false" class="login_old" :style="cardStyle" >
                <div class="form-con">
                  <login-form ref="loginForm" @on-success-valid="handleSubmit" :captchaSwitch="captchaSwitch"></login-form>
                </div>
                <Button type="text" size="large" style="float:left" @click="register()">注册</Button>
                <Button type="text" size="large" style="float:right" @click="resetPasswords()">忘记密码</Button>
              </Card>
        </Col>

        </Row>
    </div>
  </div>
</template>

<script>
import LoginForm from '_c/login-form'
import { mapActions } from 'vuex'
import Main from '@/components/main'
import utils from '@/common/utils'
// import axios from 'axios'
export default {
  data () {
    return {
      cardStyle: 'height:280px;margin-top:30%',
      captchaSwitch: false,
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
  created () {
    this.getCaptchaSwitch()
  },
  methods: {
    ...mapActions([
      'handleLogin',
      'getUserInfo'
    ]),
    getCaptchaSwitch () {
      let loadConfig = {
        method: 'GET',
        url: '/login/selectCaptchaOnOff'
      }
      this.$ajax(loadConfig)
        .then(resp => {
          this.captchaSwitch = resp.data.data
          if (this.captchaSwitch) {
            this.cardStyle = 'height:320px;margin-top:25%;'
          }
        })
    },
    handleSubmit ({ userName, password, code, uuid }) {
      let params = {}
      params.username = userName
      params.password = password
      params.code = code
      params.uuid = uuid
      let httpConfig = {
        method: 'POST',
        url: '/login',
        params: params
      }
      this.$ajax(httpConfig).then(async resp => {
        let req = resp.data
        if (!req.success) {
          this.$Modal.error({
            title: '提示',
            content: req.msg
          })
          if (this.captchaSwitch) {
            this.$refs.loginForm.getCode()
            this.captchaSwitch = true
          }
          return
        }
        this.$store.dispatch('setUserToken', req.data)
        await this.getUser(userName)
        this.$router.push({
          name: this.$config.homeName
        })
      })
    },
    async getUser (usercode) {
      let params = {}
      params.usercode = usercode

      let httpConfig = {
        method: 'GET',
        url: '/user/loadByUserCode',
        params: params
      }
      await this.$ajax(httpConfig).then(resp => {
        let req = resp.data
        if (!req) {
          return
        }
        this.$store.dispatch('setUserInfo', req)
        utils.getUserPlatform(req.userId)
      })
    },
    async getUserPlatform (userId) {
      const httpConfig = {
        method: 'GET',
        url: `/user/${userId}/platform/permiss`
      }
      await this.$ajax(httpConfig)
        .then(async resp => {
          if (resp.data) {
            for (const permiss of resp.data) {
            // resp.data.forEach(async permiss => {
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
            }
          }
          this.userPlatform.forEach(data => {
            let temp = this.userSystem[data]
            if (temp) {
              this.userSystem[data] = Array.from(temp)
            }
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
    },
    resetPasswords () {
      this.$router.push({ path: '/resetPasswords' })
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

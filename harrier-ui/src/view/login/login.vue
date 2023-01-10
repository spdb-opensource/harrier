<template>
<div id="login-header" class="note">
  <div class="sys-bar-logo">
      <Row class="sys-bar-logo-row">
        <Col offset="4" span="12">
        </Col>
      </Row>
    </div>

    <Row style="height:100%;">
      <Col span="15">
          &nbsp;
      </Col>
      <Col span="9" style="height:100%;opacity: 0.76;background: #FFFFFF;">
        <div style="text-align:center;padding-top:25%">
            <img src="../../../static/img/logo_harrier.png" width = "137px" height = "157px"/>
            <div class="homeTitle">
              {{ homeTitle }}
            </div>
        </div>
        <Card :bordered="false" :shadow="false" :dis-hover="true" class="login">
           <div class="form-con">
              <login-form ref="loginForm" @on-success-valid="handleSubmit" :captchaSwitch="captchaSwitch"></login-form>
           </div>
           <div style="margin-top:3%;margin-bottom:10%">
              <Button type="text" size="small" style="float:left;color: #0B1131;font-size:16px" @click="register()">注册</Button>
              <Button type="text" size="small" style="float:right;color: #0B1131;font-size:16px" @click="resetPasswords()">忘记密码</Button>
           </div> 
              <!-- <Form ref="loginForm" :model="formBean" :rules="formRule">
                <FormItem>
                </FormItem>
                 <FormItem prop="username">
                    <Input type="text" size="large" v-model="formBean.username" icon="ios-person" placeholder="请输入用户名" ></Input>
                </FormItem>
                <FormItem prop="password">
                  <Input type="password" size="large" v-model="formBean.password" icon="ios-lock" placeholder="请输入密码" >
                  </Input>
                </FormItem>
              </Form>
              <Button id="loginBtn" class="loginButten" :disabled="isClick" :long="true" @click="submit()" type="primary">{{login}}</Button>
              <div style="margin-top:3%;margin-bottom:10%">
                <Button type="text" size="small" style="float:left;color: #0B1131;font-size:16px" @click="register()">注册</Button>
                <Button type="text" size="small" style="float:right;color: #0B1131;font-size:16px" @click="resetPasswords()">忘记密码</Button>
              </div> -->
            </Card>
      </Col>
    </Row>
</div>
</template>

<script>
import LoginForm from '_c/login-form'
import { mapActions } from 'vuex'
import Main from '@/components/main'
import utils from '@/common/utils'
import { localRemove } from '@/libs/util'
import router, { createRouter } from '@/router'
import routers from '@/router/routers'
// import axios from 'axios'
export default {
  data () {
    return {
      formRule: {
        username: [{ required: true, message: '请输入用户名！' }],
        password: [{ required: true, message: '请输入密码！' }]
      },
      note: {
        backgroundImage: "url('static/img/header.jpg')",
        backgroundRepeat: 'no-repeat'
        // backgroundSize:"25px auto",
        // marginTop: "5px",
      },
      cardStyle: 'height:280px;margin-top:30%',
      captchaSwitch: false,
      homeTitle: '大数据中心批量监控系统',
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
        localRemove('route')
        router.matcher = createRouter(routers).matcher
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
.loginTitle {
  font-family: FZLTZHUNHJW--GB1-0;
  font-size: 35px;
  color: #0B1131;
  letter-spacing: 0;
  font-weight: 500;
}
.homeTitle {
  font-family: FZLTCHJW--GB1-0;
  font-size: 45px;
  color: #0B1131;
  letter-spacing: 0;
  text-align: center;
  font-weight: 550;
}
.loginButten {
  background-image: linear-gradient(270deg, #6132fe  0%, #3b54f9 100%);
}
</style>

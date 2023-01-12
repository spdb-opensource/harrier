<template>
<div id="login-header" class="background-image">
  <div>
    <div>
			<Row type="flex" justify="center" align="middle" class="code-row-bg" style="margin-top:3%">
				<Col span="12" >
					<Card icon="log-in" title="用户注册" :bordered="false" class="login_old" :style="[{height: (captchaSwitch ? '390px': '280px')},{marginTop: (captchaSwitch ? '5%': '10%')},{background: 'rgba(255, 255, 255, 0.9)'}]">
          <Form ref="userForm" :label-width="10" :model="formBean" :rules="formRule">
            <Col span="22">
              <Form-Item prop="userCode">
                <Input v-model="formBean.userCode" :disabled="isEdit">
                  <span slot="prepend" style="display: inline-block;width:60px">账户：</span>
                </Input>
              </Form-Item>
						</Col>
            <Col span="22">
              <Form-Item prop="userPwd">
                <Input type='password' v-model="formBean.userPwd">
                  <span slot="prepend" style="display: inline-block;width:60px">密码：</span>
                </Input>
              </Form-Item>
						</Col>
            <Col span="22">
              <Form-Item prop="password">
                <Input type="password" v-model="formBean.password">
                  <span slot="prepend" style="display: inline-block;width:60px">再次输入：</span>
                </Input>
              </Form-Item>
						</Col>
            <Col span="22" v-if="captchaSwitch">
              <Form-Item prop="userEmail">
                <Input type="userEmail" v-model="formBean.userEmail">
                  <span slot="prepend" style="display: inline-block;width:60px">邮箱:</span>
                </Input>
              </Form-Item>
            </Col>
            <Col span="16" v-if="captchaSwitch">
              <FormItem prop="code">
                <Input v-model="formBean.code">
                  <span slot="prepend" style="display: inline-block;width:60px">验证码：</span>
                </Input>
              </FormItem>
            </Col>
						<Col span="6" v-if="captchaSwitch">
              <FormItem>
                <Button  type="primary" @click="getCode">{{codeMessage}}</Button>
              </FormItem>
            </Col>
            <div>
              <Button type="primary" :loading="loading" icon="md-add" @click="save()" style="float: right">保存</Button>
              <Button type="primary" icon="ios-redo-outline" @click="cancel()" style=" margin-right: 15px;float: right;">取消</Button>
            </div>
          </Form>
        </Card>
				</Col>
			</Row>
		</div>

<!-- 
    <div class="sys-bar-title">
      <div>Harrier</div>
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
        <Card icon="log-in" title="用户注册" :bordered="false" class="login_old" :style="[{height: (captchaSwitch ? '380px': '280px')},{marginTop: (captchaSwitch ? '25%': '30%')}]">
          <Form ref="userForm" :label-width="10" :model="formBean" :rules="formRule">
            <Col span="22">
              <Form-Item prop="userCode">
                <Input v-model="formBean.userCode" :disabled="isEdit">
                  <span slot="prepend" style="display: inline-block;width:60px">账户：</span>
                </Input>
              </Form-Item>
						</Col>
            <Col span="22">
              <Form-Item prop="userPwd">
                <Input type='password' v-model="formBean.userPwd">
                  <span slot="prepend" style="display: inline-block;width:60px">密码：</span>
                </Input>
              </Form-Item>
						</Col>
            <Col span="22">
              <Form-Item prop="password">
                <Input type="password" v-model="formBean.password">
                  <span slot="prepend" style="display: inline-block;width:60px">再次输入：</span>
                </Input>
              </Form-Item>
						</Col>
            <Col span="22" v-if="captchaSwitch">
              <Form-Item prop="userEmail">
                <Input type="userEmail" v-model="formBean.userEmail">
                  <span slot="prepend" style="display: inline-block;width:60px">邮箱:</span>
                </Input>
              </Form-Item>
            </Col>
            <Col span="16" v-if="captchaSwitch">
              <FormItem prop="code">
                <Input v-model="formBean.code">
                  <span slot="prepend" style="display: inline-block;width:60px">验证码：</span>
                </Input>
              </FormItem>
            </Col>
						<Col span="1" v-if="captchaSwitch">
              <FormItem>
                <Button  type="primary" @click="getCode">{{codeMessage}}</Button>
              </FormItem>
            </Col>
            <div>
              <Button type="primary" :loading="loading" icon="md-add" @click="save()" style="margin-left: 80px;">保存</Button>
              <Button type="primary" icon="ios-redo-outline" @click="cancel()" style="margin-left: 30px;">取消</Button>
            </div>
          </Form>
        </Card>
      </Col>
    </Row> -->
  </div>
</div>

</template>

<script>
import { mapActions } from 'vuex'
export default {
  data: function () {
    return {
      captchaSwitch: false,
      codeMessage: '获取验证码',
      homeTitle: 'Harrier',
      formBean: {},
      isShow: true,
      isEdit: false,
      loading: false,
      formRule: {
        userCode: [ { required: true, message: '该项为必输项！' }, {
          validator: (rule, value, callback, source, options) => {
            let v = this.formBean.userCode
            if (this.isEdit) {
              callback()
              return
            }
            this.$ajax(`user/${v}/codeExists`)
              .then(resp => {
                if (resp.data === true) {
                  callback(new Error('该用户名已存在！'))
                } else {
                  callback()
                }
              })
          }}
        ],
        code: [{ required: true, message: '请输入验证码！' }],
        userName: [ { required: true, message: '该项为必输项！' } ],
        password: [ { required: true, message: '该项为必输项！' } ],
        userPwd: [ { required: true, message: '该项为必输项！' } ]
      }
    }
  },
  methods: {
    ...mapActions([
      'handleLogOut'
    ]),
    getCaptchaSwitch () {
      let loadConfig = {
        method: 'GET',
        url: '/register/selectCaptchaOnOff'
      }
      this.$ajax(loadConfig)
        .then(resp => {
          this.captchaSwitch = resp.data.data
        })
    },
    getCode () {
      if (!this.formBean.userEmail) {
        this.$Message.warning({
          content: '请输入邮箱',
          duration: 8,
          closable: true
        })
        return
      }
      let cookieV = this.getCookieValue('registerEmail') ? this.getCookieValue('registerEmail') : 0
      if (cookieV > 0) {
        this.settime()
        this.$Message.info({
          content: '请检查您的邮箱，查看是否收到验证码',
          duration: 8,
          closable: true
        })
      } else {
        let param = {}
        Object.assign(param, this.formBean)
        let loadConfig = {
          method: 'POST',
          url: '/user/sendRegistervaricode',
          data: param
        }
        this.$ajax(loadConfig)
          .then(resp => {
            if (resp.status && resp.status === 200) {
              if (resp.data) {
                this.getCode_btn = true
                this.addCookie('registerEmail', 60, 60)
                this.settime()
              } else {
                this.$Message.error({
                  content: '验证码发送失败！',
                  duration: 8,
                  closable: true
                })
              }
            }
          })
      }
    },
    getCookieValue (name) {
      let strCookie = document.cookie
      let arrCokie = strCookie.split(';')
      let result = ''
      arrCokie.forEach((d, i) => {
        let arr = d.split('=')
        if (arr[0].replace(/\s*/g, '') === name) {
          result = unescape(arr[1])
        }
      })
      return result
    },
    settime () {
      let countdown = this.getCookieValue('registerEmail')
      let that = this
      let tim = setInterval(function () {
        countdown--
        that.codeMessage = '重新发送(' + countdown + ')'
        that.getCode_btn = true
        if (countdown <= 0) {
          clearInterval(tim)
          that.getCode_btn = false
          that.codeMessage = '重新发送'
        }
        that.editCookie('registerEmail', countdown, countdown + 1)
      }, 1000)
    },
    editCookie (name, value, expiresHours) {
      let cookieString = name + '=' + escape(value)
      if (expiresHours > 0) {
        let date = new Date()
        date.setTime(date.getTime() + expiresHours * 1000)
        cookieString = cookieString + ';expires=' + date.toGMTString()
      }
      document.cookie = cookieString
    },
    addCookie (name, value, expiresHours) {
      let cookieString = name + '=' + escape(value)
      if (expiresHours > 0) {
        let date = new Date()
        date.setTime(date.getTime() + expiresHours * 1000)
        cookieString = cookieString + ';expires=' + date.toUTCString()
      }
      document.cookie = cookieString
    },
    save: function () {
      if (this.formBean.userId) { // 编辑
      } else {
        if (this.formBean.userPwd != this.formBean.password) {
          this.$Message.error('两次密码不同')
          return
        }
      }
      this.$refs.userForm.validate((valid) => {
        if (!valid) return
        let config = {}
        config.data = this.formBean
        config.data.username = this.formBean.userCode
        config.method = 'POST'
        config.url = '/register'
        config.contentType = 'json'
        this.loading = true
        this.$ajax(config)
          .then(resp => {
            console.log(resp)
            debugger
            if (resp.data.success) {
              var msg = resp.data.data.msg
              var status = resp.data.data.status
              if (status === 'true' || status === true) {
                this.$Modal.success({
                  title: '提示',
                  content: '注册成功！'
                })
                setTimeout(() => {
                  this.cancel()
                }, 1000)
              } else {
                this.$Modal.error({
                  title: '提示',
                  content: msg
                })
                this.loading = false
              }
            }
          })
      })
    },
    cancel: function () {
      this.handleLogOut().then(() => {
        this.$router.push({
          name: 'login'
        })
      })
    }
  },
  mounted: function () {
    this.getCaptchaSwitch()
    //
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
<style>
#userRegisterForm .ivu-form-item-error-tip {
  width: 150px;
}
body {
    background-image:url(~@/assets/images/login_background.png);
    background-repeat: no-repeat;
    background-size: cover;
    -webkit-background-size: cover;
    background-position: center 0;
}
</style>

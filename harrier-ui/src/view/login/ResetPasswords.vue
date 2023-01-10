<template>
<div>
  <Row type="flex" justify="center" align="middle" class="code-row-bg" style="margin-top:5%">
    <Col span="12" >
      <Card :shadow="false" :padding="40" class="login" style="background: rgb(255, 255, 255,0.9);">
        <Form ref="formBean" :model="formBean" :labelWidth="50" :rules="formRule">
          <Row>
            <!-- <p style="height: 32px;font-size:14px;text-align:left;font-weight: bold;">您好{{userName}}：</p> -->
            <p style="height: 32px;font-size:14px;text-align:left;font-weight: bold;">密码重置</p>
          </Row>
          <Row>
            <!-- <Col span="15">
              <FormItem prop="empId">
                <Input v-model="formBean.empId" style="width:100%">
                  <span slot="prepend" style="display: inline-block;width:70px">工号：</span>
                </Input>
              </FormItem>
            </Col> -->
            <Col span="15">
              <FormItem prop="userCode">
                <Input v-model="formBean.userCode" style="width:100%">
                  <span slot="prepend" style="display: inline-block;width:70px">账号：</span>
                </Input>
              </FormItem>
            </Col>
            <Col span="1">
              <FormItem prop="userCode">
                <Button type="primary" style="margin-left:0" @click="searchMessage">查询</Button>
              </FormItem>
            </Col>
          </Row>
          <div v-if="isSearch">
          <Row>
          </Row>
          <Row>
            <Col span="15">
              <FormItem prop="userName">
                <Input v-model="formBean.userName" readonly>
                  <span slot="prepend" style="display: inline-block;width:70px">中文名：</span>
                </Input>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="15">
              <FormItem prop="userEmail">
                <Input v-model="formBean.userEmail" readonly>
                  <span slot="prepend" style="display: inline-block;width:70px">邮箱：</span>
                </Input>
              </FormItem>
            </Col>
            <Col span="1">
              <FormItem prop="userEmail">
                <Button id="getCode_btn" :disabled="getCode_btn" type="primary" @click="getCode">{{codeMessage}}</Button>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="15">
              <FormItem prop="code">
                <Input v-model="formBean.code">
                  <span slot="prepend" style="display: inline-block;width:70px">验证码：</span>
                </Input>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="15">
              <FormItem prop="newPassword">
                <Input type="password" v-model="formBean.newPassword">
                  <span slot="prepend" style="display: inline-block;width:70px">新密码：</span>
                </Input>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="15">
              <FormItem prop="newPassword2">
                <Input type="password" v-model="formBean.newPassword2">
                  <span slot="prepend" style="display: inline-block;width:70px">确认密码：</span>
                </Input>
              </FormItem>
            </Col>
          </Row>
          </div>
          <Row>
            <Col span="12">
              <FormItem>
                  <Button @click="Cancel" style="margin-left: 20px;float: right;">取消</Button>
                  <Button :disabled="isview" type="primary" :loading="loading" @click="Submit" style="float: right;">确定</Button>
              </FormItem>
            </Col>
          </Row>
        </Form>
      </Card>
    </Col>
  </Row>
</div>
</template>

<script>
import utils from '@/common/utils'
import { mapActions } from 'vuex'

export default {
  data () {
    return {
      countdown: '',
      codeMessage: '获取验证码',
      getCode_btn: false,
      isSearch: false,
      formBean: {},
      modulus: '',
      exponent: '',
      key: '',
      loading: false,
      isview: true,
      confirm: {
        show: false
      },
      formRule: {
        code: [{ required: true, message: '请输入验证码！' }],
        newPassword: [{ required: true, message: '请输入密码！' }],
        newPassword2: [{ required: true, message: '请确认密码！' }]
      }
    }
  },
  methods: {
    ...mapActions([
      'handleLogOut'
    ]),
    Submit () {
      this.$refs.formBean.validate(valid => {
        if (!valid) return
        if (this.formBean.newPassword === this.formBean.newPassword2) {
          var pwreg = /^(?![S|s]pdb.123)(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[`~!@#$%^&*()_+<>?:"{},.;'\/[\]\|\\=-]).*$/
          if (pwreg.test(this.formBean.newPassword)) {
            let params = {}
            Object.assign(params, this.formBean)
            // 更新是否需要修改密码字段 if_modify_pass=0
            params.isModifyPwd = 0
            let httpConfig = {
              method: 'POST',
              url: '/user/forgotPwd',
              data: params
            }
            this.loading = true
            this.isview = true
            this.$ajax(httpConfig).then(resp => {
              if (resp.data === 'success') {
                this.$Message.success('修改成功！将自动跳转到登陆页面...')
                setTimeout(function () { utils.logout(this.$router) }, 1000)
              } else if (resp.data === 'codefail') {
                this.$Message.error({
                  content: '验证码错误或失效，修改失败！',
                  duration: 8,
                  closable: true
                })
                this.loading = false
                this.isview = false
              } else {
                this.$Message.error('修改失败！')
                this.loading = false
                this.isview = false
              }
            })
          } else {
            this.$Message.error('密码不符合规则,必须是字母数组和特殊字符组合！')
          }
        } else {
          this.$Message.error('两次输入密码不同！')
        }
      })
    },
    searchMessage () {
      if (!this.formBean.userCode) {
        this.$Message.error('请输入账号后查询')
      }
      let param = {}
      param.userCode = this.formBean.userCode
      let loadConfig = {
        method: 'GET',
        url: '/user/selectUserToCode',
        params: param
      }
      this.isSearch = false
      this.isview = true
      this.$ajax(loadConfig)
        .then(resp => {
          if (resp.status && resp.status === 200) {
            // console.log(resp)
            if (resp.data.length === 0) {
              this.$Message.warning({
                content: '当前账号不存在或停用,请确认账号或联系管理员',
                duration: 8,
                closable: true
              })
              // this.$Message.warning('当前工号不存在或停用,请确认工号或联系管理员')
            } else {
              this.isSearch = true
              this.isview = false
              this.formBean = resp.data
              // this.formBean.empId = resp.data.emp_id
            }
          }
        })
    },
    getCode () {
      if (!this.formBean.userEmail) {
        this.$Message.warning({
          content: '当前账号未配置邮箱，请联系管理员',
          duration: 8,
          closable: true
        })
        return
      }
      let cookieV = this.getCookieValue('secondsremained') ? this.getCookieValue('secondsremained') : 0
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
          url: '/user/sendvaricode',
          data: param
        }
        this.$ajax(loadConfig)
          .then(resp => {
            if (resp.status && resp.status === 200) {
              if (resp.data) {
                this.getCode_btn = true
                this.addCookie('secondsremained', 60, 60)
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
    addCookie (name, value, expiresHours) {
      let cookieString = name + '=' + escape(value)
      if (expiresHours > 0) {
        let date = new Date()
        date.setTime(date.getTime() + expiresHours * 1000)
        cookieString = cookieString + ';expires=' + date.toUTCString()
      }
      document.cookie = cookieString
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
      let countdown = this.getCookieValue('secondsremained')
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
        that.editCookie('secondsremained', countdown, countdown + 1)
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
    Cancel () {
      this.$router.push({ name: 'login' })
    }
  },
  mounted () {
	  // this.confirm.show = true
    // this.userName = this.$route.query.userName
    // this.userId = this.$route.query.userId
    // if (env.enableVerifyPass) {
    //   this.getKey()
    // }
  }
}
</script>
<style>
body {
    background-image:url(~@/assets/images/login_background.png);
    background-repeat: no-repeat;
    background-size: cover;
    -webkit-background-size: cover;
    background-position: center 0;
}
</style>
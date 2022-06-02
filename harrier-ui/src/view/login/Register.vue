<template>
<div @keydown.enter="submit" id="login-header" class="note">
  <div>
    <div class="sys-bar-logo">
      <Row class="sys-bar-logo-row">
        <Col span="8" class="sys-bar-logo-row">
        <img src="../../../static/img/logobg-white.png" />
        </Col>
        <Col offset="4" span="12">
          <Row>
            <div class="logoTitle_m1">浦发银行</div>
            <div class="logoTitle_m2">SPD BANK</div>
          </Row>
        </Col>
      </Row>
    </div>
    <div class="sys-bar-logoline">
      <img src="../../../static/img/logoline.png" />
    </div>
    <div class="sys-bar-title">
      <div>Harrier</div>
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
        <div class='registerForm'>
          <div class="ivu-card-head">
            <p><span>用户注册</span></p>
          </div>
          <div>
            <Form ref="userForm" :label-width="120" :model="formBean" :rules="formRule" id="userRegisterForm">
              <Row>
                <Col span="6">
                  <Form-Item prop="userCode" label="用户名">
                    <Input v-model="formBean.userCode" :disabled="isEdit" style="width: 150px;"/>
                  </Form-Item>
                </Col>
              </Row>
              <!-- <Row>
                <Col span="6">
                  <Form-Item prop="userName" label="中文名称">
                    <Input v-model="formBean.userName"/>
                  </Form-Item>
                </Col>
              </Row> -->
              <Row  v-if="isShow">
                <Col span="6">
                  <Form-Item prop="userPwd" label="密码" >
                    <!--<Input type="userPwd" v-model="formBean.userPwd"/>-->
                    <Input type='password' v-model="formBean.userPwd" placeholder="密    码" style="width: 150px;">
                      <Icon type="md-lock" slot="prepend"></Icon>
                    </Input>
                  </Form-Item>
                </Col>
              </Row>
              <Row  v-if="isShow">
                <Col span="6">
                  <Form-Item prop="password" label="再次输入">
                    <Input type="password" v-model="formBean.password" style="width: 150px;">
                      <Icon type="md-lock" slot="prepend"></Icon>
                    </Input>
                  </Form-Item>
                </Col>
              </Row>
            </Form>
            <div>
              <Button type="primary" :loading="loading" icon="md-add" @click="save()" style="margin-left: 80px;">保存</Button>
              <Button type="primary" icon="ios-redo-outline" @click="cancel()" style="margin-left: 30px;">取消</Button>
            </div>
          </div>
        </div>
      </Col>
    </Row>
  </div>
</div>

</template>

<script>
import { mapActions } from 'vuex'
export default {
  data: function () {
    return {
      formBean: {},
      isShow: true,
      isEdit: false,
      loading: false,
      formRule: {
        userCode: [ { required: true, message: '该项为必输项！' } //, {
          // validator: (rule, value, callback, source, options) => {
          //   if (this.isEdit) {
          //     callback()
          //     return
          //   }
          //   this.$ajax(`user/${value}/codeExists`)
          //     .then(resp => {
          //       if (resp.data === true) {
          //         callback(new Error('该用户名已存在！'))
          //       } else {
          //         callback()
          //       }
          //     })
          // }}
        ],
        userName: [ { required: true, message: '该项为必输项！' } ],
        userPwd: [ { required: true, message: '该项为必输项！' } ]
      }
    }
  },
  methods: {
    ...mapActions([
      'handleLogOut'
    ]),
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
            if (resp.data.success) {
              this.$Modal.success({
                title: '提示',
                content: '注册成功！'
              })
            }
            this.cancel()
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
  left: 200px;
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
</style>

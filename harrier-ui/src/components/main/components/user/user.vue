<template>
  <div class="sys-bar-user">
    <Dropdown trigger="click" style="font-size:14px;" @on-click="handleClick">
      <a class="sys-bar-user-a" href="javascript:void(0)">
        <Icon type="md-person" class="sys-bar-user-a" style="font-size:22px"></Icon>
        <!-- &nbsp; -->
        <span class="sys-bar-user-a">您好, {{ userChName ? userChName : userName }}</span>
        <!-- &nbsp; -->
        <Icon :size="18" class="sys-bar-user-a" type="md-arrow-dropdown"></Icon>
      </a>
      <DropdownMenu slot="list" class="select-menu">
        <DropdownItem name="修改密码">修改密码</DropdownItem>
        <DropdownItem name="退出">退出</DropdownItem>
      </DropdownMenu>
    </Dropdown>
    <!-- 用户弹窗 -->
    <Modal
			v-model="userMsg"
			title="修改密码"
			:mask-closable="false"
			>
        <Form>
          <Row type="flex" justify="center">
            <Col span="20">
              <Input type="password" v-model="oldPassword">
                <span slot="prepend" style="display: inline-block;width:70px">旧密码：</span>
              </Input>
            </Col>
          </Row>
          <Row style="margin-top:6px" type="flex" justify="center">
            <Col span="20">
              <Input type="password" v-model="newPassword">
                <span slot="prepend" style="display: inline-block;width:70px">新密码：</span>
              </Input>
            </Col>
          </Row>
          <Row style="margin-top:6px" type="flex" justify="center">
            <Col span="20">
              <Input type="password" v-model="newPassword2">
                <span slot="prepend" style="display: inline-block;width:70px">再次输入：</span>
              </Input>
            </Col>
          </Row>
        </Form>
        <div slot="footer">
          <Button type="primary" :loading="loading" @click="userOk">确定</Button>
          <Button type="text" :disabled="isview" @click="userCancel">取消</Button>
        </div>
    </Modal>
  </div>
</template>

<script>
import './user.less'
import utils from '@/common/utils'
import { mapActions } from 'vuex'
// import { RSAKeyPair, encryptedString } from 'speed4j-front-commonjs'
import env from '@/config'
export default {
  data () {
    return {
      userChName: '',
      userMsg: false,
      oldPassword: '',
      newPassword: '',
      newPassword2: '',
      loading: false,
      isview: false,
      key: ''
    }
  },
  name: 'User',
  props: {
  },
  computed: {
    userName () {
      return this.$store.getters.getUserName
    }
  },
  methods: {
    ...mapActions([
      'handleLogOut'
    ]),
    logout () {
      // utils.logout(this.$router)
      this.handleLogOut().then(() => {
        this.$router.push({
          name: 'login'
        })
      })
    },
    message () {
      this.$router.push({
        name: 'message_page'
      })
    },
    handleClick (name) {
      switch (name) {
        case '退出': this.logout()
          break
        case '修改密码':
          this.userMsg = true
          // if (env.enableVerifyPass) {
          //   this.getKey()
          // }
          break
      }
    },
    getUserName () {
      // const userconfig = {
      //   method: 'GET',
      //   url: '/user',
      //   params: { username: this.$store.getters.getUser.username }
      // }
      // this.$ajax(userconfig).then(resp => {
      //   if (resp.data.rows) {
      //     this.userChName = resp.data.rows[0].userchname
      //   }
      // })
      this.userChName = this.$store.getters.getUserChName()
    },
    userOk () {
      if (this.oldPassword) {
        if (
          this.newPassword === this.newPassword2 &&
        !(this.newPassword === this.oldPassword)
        ) {
          var pwreg = /^(?![S|s]pdb.123)(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[`~!@#$%^&*()_+<>?:"{},.;'\/[\]\|\\=-]).*$/
          if (pwreg.test(this.newPassword)) {
            let params = {}
            // params.oldPassword = encryptedString(this.key, this.oldPassword)
            // params.newPassword = encryptedString(this.key, this.newPassword)
            params.oldPassword = this.oldPassword
            params.newPassword = this.newPassword
            let httpConfig = {
              method: 'PUT',
              url: '/user/updatePwd',
              params: params
            }
            this.loading = true
            this.isview = true
            this.$ajax(httpConfig).then(resp => {
              if (resp.data === 'success') {
                this.$Message.success('修改成功！')
                this.userCancel()
              } else if (resp.data === 'pwdfail') {
                this.$Message.error('密码不匹配，修改失败！')
              } else if (resp.data === 'userfail') {
                this.$Message.error('登录超时，请重新登录！')
                this.logout()
              } else {
                this.$Message.error('修改失败！')
              }
              this.loading = false
              this.isview = false
            })
          } else {
            this.$Message.error('密码不符合规则,必须是字母数组和特殊字符组合！')
          }
        } else {
          this.$Message.error('两次输入密码不同或新旧密码相同！')
        }
      } else {
        this.$Message.error('请输入旧密码！')
      }
    },
    // getKey: function () {
    //   // load rsa key 加密传输密码
    //   this.$ajax({
    //     url: '/login/key',
    //     method: 'GET'
    //   })
    //     .then(response => {
    //       if (response.status === 200) {
    //         // 成功
    //         this.exponent = response.data.exponent
    //         this.modulus = response.data.modulus
    //         this.key = new RSAKeyPair(this.exponent, '', this.modulus)
    //         // this.isClick = false;
    //       } else {
    //         this.$Message.error('请求登录信息异常,等待后台恢复！')
    //         // this.isClick = true;
    //         let promise = new Promise(resolve => setTimeout(resolve, 10000))
    //         promise.then(() => this.getKey())
    //       }
    //     })
    //     // eslint-disable-next-line handle-callback-err
    //     .catch(error => {
    //       // console.log(error);
    //       this.$Message.error('请求登录信息异常,等待后台恢复！')
    //       // this.isClick = true;
    //       let promise = new Promise(resolve => setTimeout(resolve, 10000))
    //       promise.then(() => this.getKey())
    //     })
    // },
    userCancel () {
      this.userMsg = false
      this.oldPassword = ''
      this.newPassword = ''
      this.newPassword2 = ''
    }
  },
  mounted () {
    this.getUserName()
  }
}
</script>

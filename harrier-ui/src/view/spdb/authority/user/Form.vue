<template>
  <div>
    <div>
      <Form ref="userForm" :label-width="120" :model="formBean" :rules="formRule">
        <Row>
            <Col span="6">
               <Form-Item prop="userCode" label="用户名">
              <Input v-model="formBean.userCode" :disabled="isEdit"/>
            </Form-Item>
          </Col>

          </Row>
        <Row>
          <Col span="6">
            <Form-Item prop="userName" label="中文名称">
              <Input v-model="formBean.userName"/>
            </Form-Item>
           </Col>
        </Row>
        <Row  v-if="isShow">
            <Col span="6">
            <Form-Item prop="userPwd" label="密码" >
              <!--<Input type="userPwd" v-model="formBean.userPwd"/>-->
              <Input type='password' v-model="formBean.userPwd" placeholder="密    码">
                <Icon type="md-lock" slot="prepend"></Icon>
              </Input>
            </Form-Item>
          </Col>
          </Row>
        <Row  v-if="isShow">
          <Col span="6">
            <Form-Item prop="password" label="再次输入">
              <Input type="password" v-model="formBean.password">
                <Icon type="md-lock" slot="prepend"></Icon>
              </Input>
            </Form-Item>
          </Col>
        </Row>
        <Row>
          <Col span="6">
            <Form-Item prop="userPhone" label="手机号码">
              <Input  v-model="formBean.userPhone"/>
            </Form-Item>
          </Col>
        </Row>
        <Row>
          <Col span="6">
            <Form-Item prop="userEmail" label="邮箱">
              <Input type="email" v-model="formBean.userEmail"/>
            </Form-Item>
          </Col>
        </Row>
        <Row>
          <Col span="6">
            <Form-Item prop="empId" label="工号">
              <Input v-model="formBean.empId"/>
            </Form-Item>
          </Col>
        </Row>
        <!--
        <Row v-if="isShow">
          <Col span="6">
            <Form-Item prop="role" label="所属角色"  >
              <Select filterable v-model="formBean.role_id"   >
                <Option  v-for="item in roleList" :value="item.value" :key="item.key" :label="item.label">
                  {{ item.label }}
                </Option>
              </Select>
            </Form-Item>
          </Col>
        </Row>
        <Row>
          <Col span="6">
            <Form-Item prop="platform_name" label="所属平台" >
              <Select filterable v-model="formBean.platform_name" @on-change="loadsys()"  clearable>
                <Option  v-for="item in platformList" :value="item.value" :key="item.value" :label="item.value">
                  {{ item.label }}
                </Option>
              </Select>
            </Form-Item>
          </Col>

          </Row>
        <Row>
          <Col span="6">
            <Form-Item prop="system_name" label="所属应用">
              <Input  v-model="formBean.system_name"/>
              <Select filterable v-model="formBean.system_name"   clearable>
                <Option v-for="item in sysList" :value="item.value" :key="item.value" :label="item.value">
                  {{ item.label }}
                </Option>
              </Select>
            </Form-Item>
          </Col>

        </Row>
        -->
      </Form>

      <div>
        <Button type="primary" :loading="loading" icon="md-add" @click="save()">保存</Button>
        <Button type="primary" icon="ios-redo-outline" @click="cancel()">取消</Button>
      </div>
    </div>
  </div>
</template>

<script>
// import managerServer  from '@/api/manage';
import env from '@/config'
import store from '@/store/index'
export default {
  props: {
    transData: {
      type: Object,
      default: function () {
        return {}
      }
    }
  },
  data: function () {
    const checkEmpId = (rule, value, callback) => {
      if (this.transData.id) {
        return callback()
      }
      let reg = /^\d{8}$/
      if (!reg.test(value)) {
        callback(new Error('请输入八位数字的工号'))
        return
      }
      let param = {}
      param.empId = value
      let httpConfig = {
        method: 'GET',
        url: '/user/loadByEmpId',
        params: param
      }
      this.$ajax(httpConfig)
        .then(resp => {
          if (resp.data !== '') {
            callback(new Error('该工号已存在，请重新输入'))
          } else {
            callback()
          }
        })
    }
    const checkuserEmail = (rule, value, callback) => {
      let reg = /^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/
      if (!reg.test(value)) {
        callback(new Error('请输入正确的邮箱号'))
      } else {
        callback()
      }
    }
    const checkPhoneNum = (rule, value, callback) => {
      if (!value) {
        return callback()
      }
      let reg = /^1(3[0-9]|4[01456789]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\d{8}$/
      if (!reg.test(value)) {
        callback(new Error('请输入正确的手机号'))
      } else {
        callback()
      }
    }
    return {
      tableData: [],
      sysList: [],
      roleList: [],
      platformList: [],
      formBean: {},
      isShow: true,
      loading: false,
      formRule: {
        userCode: [ { required: true, message: '该项为必输项！' }, {
          validator: (rule, value, callback, source, options) => {
            if (this.isEdit) {
              callback()
              return
            }
            this.$ajax(`user/${value}/codeExists`)
              .then(resp => {
                if (resp.data === true) {
                  callback(new Error('该用户名已存在！'))
                } else {
                  callback()
                }
              })
          }
        } ],
        userName: [ { required: true, message: '该项为必输项！' } ],
        userPwd: [ { required: true, message: '该项为必输项！' } ],
        // empId: [ { required: true, message: '该项为必输项！' }, { validator: checkEmpId, trigger: '' } ],
        userEmail: [ { required: true, message: '该项为必输项！' }, { validator: checkuserEmail, trigger: '' } ],
        userPhone: [ { validator: checkPhoneNum, trigger: '' } ]
      },
      modulus: '',
      exponent: '',
      key: '',
      errorMsg: '',
      request: {
        getKey: {
          url: '/login/key',
          method: 'GET'
        }
      },
      isEdit: false
    }
  },
  methods: {
    loadsys: function () {
      this.sysList = []
      const loadConfig = {
        method: 'GET',
        url: '/msys/getSysList',
        params: { platform: this.formBean.platform_name }
      }
      this.$ajax(loadConfig).then(resp => {
        if (resp.data) {
          resp.data.forEach(data1 => {
            let temp = {}
            temp.key = data1
            temp.value = data1
            temp.label = data1
            this.sysList.push(temp)
          })
        }
      })
    },
    // loadplat: function () {
    //   const loadConfig = {
    //     method: 'GET',
    //     url: 'user/getPlatformList',
    //     params: {}
    //   }
    //   this.$ajax(loadConfig).then(resp => {
    //     if (resp.data) {
    //       resp.data.forEach(data1 => {
    //         let temp = {}
    //         temp.key = data1
    //         temp.value = data1
    //         temp.label = data1

    //         this.platformList.push(temp)
    //       })
    //     }
    //   })
    // },
    loadRole: function () {
      this.roleList = []
      const loadConfig = {
        method: 'GET', url: `/role/${this.formBean.userCode}/list`
      }
      this.$ajax(loadConfig).then(resp => {
        if (resp.data) {
          resp.data.forEach(data1 => {
            let temp = {}
            temp.key = data1.role_chname
            temp.value = data1.role_id
            temp.label = data1.role_chname
            this.roleList.push(temp)
          })
        }
      })
    },
    init: function () {
      let id = this.transData.id
      if (id) {
        this.isShow = false
        this.isEdit = true
        this.$ajax(`user/${id}`)
          .then(resp => {
            this.formBean = resp.data
            // this.formBean.userPwd = ''
          })
      } else {

      }
    },
    // getDep:function(){
    //   let data={};
    //   this.$axios.post('msys/getPlatformList',data).then(res=> {
    //     if(res.data.code===200){
    //       this.platformList=res.data.data;
    //       console.log(this.depss);
    //     }else{
    //       console.log(1);
    //     }
    //   }).catch(function(error){
    //     this.$message.error('系统错误');
    //   })
    // },
    save: function () {
      if (this.formBean.userId) { // 编辑
      } else {
        if (this.formBean.userPwd != this.formBean.password) {
          this.$Message.error('两次新密码不同')
          return
        }
      }
      this.$refs.userForm.validate((valid) => {
        if (!valid) return
        let config = {}
        // if (env.enableVerifyPass) {
        //   let encrpytedpass = this.key
        //   this.formBean.userPwd = encrpytedpass
        //   this.formBean.password = encrpytedpass
        // }
        config.data = this.formBean
        delete config.data.createTime
        delete config.data.updateTime
        if (this.formBean.userId) {
          config.method = 'PUT'
          config.url = `/user/${this.formBean.userId}/update`
        } else {
          config.method = 'POST'
          config.url = '/user/add'
        }
        // let authSystems = store.getters.getSystems
        // var param = authSystems.find(item => { if (item.indexOf('*') > -1) { return item } })
        // config.data.authps = param
        this.loading = true
        this.$ajax(config)
          .then(resp => {
            this.cancel()
          })
      })
    },
    cancel: function () {
      let queryCache = { searchBean: this.transData.searchBean, currentPage: this.transData.currentPage, pageSize: this.transData.pageSize }
      this.$emit('exit', Object.assign({}, queryCache))
    },
    getKey: function () {
      // load rsa key 加密传输密码
      this.$ajax(this.request.getKey)
        .then(response => {
          if (response.status == '200') {
            // 成功
            this.exponent = response.data.exponent
            this.modulus = response.data.modulus
            this.key = this.exponent
            this.isClick = false
          } else {
            this.$Message.error('请求登录信息异常,等待后台恢复！')
            this.isClick = true
            let promise = new Promise(resolve => setTimeout(resolve, 10000))
            promise.then(() => (this.getKey()))
          }
        })
        .catch(error => {
          console.log(error)
          this.$Message.error('请求登录信息异常,等待后台恢复！')
          this.isClick = true
          let promise = new Promise(resolve => setTimeout(resolve, 10000))
          promise.then(() => (this.getKey()))
        })
    }
  },
  mounted: function () {
    if (env.enableVerifyPass) {
      this.getKey()
    }
    this.init()
    // this.loadsys();
    // this.loadplat()
    this.loadRole()
  }
}
</script>

<template>
  <div>
    <Form ref="serverForm" :model="formBean" :label-width="120" :rules="formRule">
      <Row>
        <Col span="6">
          <FormItem prop="serverName" label="执行节点名称">
            <Input v-model="formBean.serverName" readonly />
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="6">
          <FormItem prop="ip" label="执行节点iP">
            <Input v-model="formBean.ip" readonly />
          </FormItem>
        </Col>
      </Row>
      <Row>
        <!-- <Col span="6">
          <FormItem prop="performanceRatio" label="性能比值(千分比)">
            <input
              type="number"
              style="border-radius:5px; width: 70px"
              v-model="formBean.performanceRatio"
              oninput="if(value>1000)value=100;if(value<0)value=0"
            />
          </FormItem>
        </Col> -->
      </Row>
      <Row>
        <Col span="8">
          <!--
					<FormItem prop="maxJobNum" label="最大并发数">
						<Input v-model="formBean.maxJobNum"/>
          </FormItem>-->
          <FormItem prop="maxJobNum" label="最大并发数" :label-width="120">
            <input
              type="number"
              style="border-radius:5px; width: 70px"
              v-model="formBean.maxJobNum"
              oninput="if(value>300)value=300;if(value<0)value=0"
            />
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span>
          <FormItem prop="nodeEnable" label="是否启用">
            <Checkbox v-model="formBean.nodeEnable">启用</Checkbox>
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="4">
          <Form-Item prop="tags" label="节点标签">
            <Select filterable placeholder="请选择" v-model="formBean.tags" clearable>
              <Option v-for="item in dirList" :value="item.value" :key="item.key">{{ item.value }}</Option>
            </Select>
          </Form-Item>
        </Col>
      </Row>
      <Row>
        <Col span="6">
          <FormItem prop="location" label="逻辑集群">
            <Select v-model="formBean.dbControl" :disabled="true">
              <Option
                v-for="item in dbControlList"
                :value="item.value"
                :key="item.value"
              >{{ item.label }}</Option>
            </Select>
          </FormItem>
        </Col>
      </Row>
      <Row>
        <Col span="6">
          <FormItem prop="location" label="节点归属地">
            <Select v-model="formBean.location" :disabled="true">
              <Option
                v-for="item in locationList"
                :value="item.value"
                :key="item.value"
              >{{ item.label }}</Option>
            </Select>
          </FormItem>
        </Col>
      </Row>
    </Form>

    <div>
      <Button type="primary" :loading="loading" icon="md-add" @click="save">保存</Button>
      <Button type="primary" icon="ios-trash-outline" @click="cancel">取消</Button>
    </div>
  </div>
</template>

<script>
const RESOURCE_PATH = "/server";

export default {
  props: {
    transData: {
      type: Object,
      default: function() {
        return {};
      }
    }
  },
  data() {
    return {
      formBean: {},
      loading: false,
      locationList: [],
      dbControlList:[],
      formRule: {},
      dirList: []
    };
  },
  methods: {
    /**
     * 初始化
     **/
    init() {
      if (this.transData.id) {
        // 如果传过来的存在ID则说明是编辑功能跳转过来
        let loadConfig = {
          method: "GET",
          url: RESOURCE_PATH + "/" + this.transData.id
        };
        this.$ajax(loadConfig).then(resp => {
          this.formBean = resp.data;
          if (resp.data.isEnable == 1) {
            this.formBean.nodeEnable = true;
          } else {
            this.formBean.nodeEnable = false;
          }
        });
      } else {
        this.bindData();
      }
      this.querylocationList();
      this.queryDbControlList();
    },
    /**
     * 如果传过来的有初始数据则进行数据绑定
     **/
    bindData() {
      try {
        this.formBean = Object.assign({}, this.transData.initFormBean);
      } catch (error) {
        console.error(error);
      }
    },
    /**
     * 保存表单
     **/
    save() {
      this.$refs.serverForm.validate(valid => {
        if (!valid) return;
        let params = {};
        this.formBean.isEnable = this.formBean.nodeEnable == true ? 1 : 0;
        Object.assign(params, this.formBean);
        let httpConfig = {
          url: RESOURCE_PATH,
          data: params
        };
        if (this.transData.id) {
          httpConfig.method = "PUT";
          httpConfig.url = RESOURCE_PATH + "/" + this.transData.id;
        } else {
          httpConfig.method = "POST";
        }
        this.loading = true;
        this.$ajax(httpConfig).then(resp => {
          this.loading = false;
          let str = resp.data;
          //alert(str);
          // this.$dialog.confirm(" xxx").then(function(){

          // }).catch(function(){

          // });
          //console.log(str);
          this.cancel();
          this.init();
        });
      });
    },
    /**
     * 返回到数据视图
     **/
    cancel() {
      let queryCache = {
        formBean: this.transData.formBean,
        currentPage: this.transData.currentPage,
        pageSize: this.transData.pageSize
      };
      this.$emit("switch", Object.assign({ statusName: "table" }, queryCache));
    },
    
    queryDbControlList() {
      let loadConfig = {
        method: "GET",
        url: "/dictionary",
        params: { dicCode: "db_control" }
      };
      this.$ajax(loadConfig).then(resp => {
        this.dbControlList = [];
        resp.data.rows.forEach(data => {
          let tmp = {};
          tmp.value = parseInt(data.dicValue);
          tmp.label = data.dicName;
          this.dbControlList.push(tmp);
        });
      });
    },
    querylocationList() {
      let loadConfig = {
        method: "GET",
        url: "/dictionary",
        params: { dicCode: "location" }
      };
      this.$ajax(loadConfig).then(resp => {
        this.locationList = [];
        resp.data.rows.forEach(data => {
          let tmp = {};
          tmp.value = parseInt(data.dicValue);
          tmp.label = data.dicName;
          this.locationList.push(tmp);
        });
      });
    },
    loadTags() {
      let loadConfig = {
        method: "GET",
        url: "/dictionary",
        params: { dicCode: "nodeTag" }
      };
      this.$ajax(loadConfig).then(resp => {
        resp.data.rows.forEach(data => {
          let tmp = {};
          tmp.value = data.dicValue;
          tmp.label = data.dicName;
          this.dirList.push(tmp);
        });
      });
    }
  },
  /**
   * 视图挂载
   **/
  mounted() {
    this.init();
    this.loadTags();
  }
};
</script>

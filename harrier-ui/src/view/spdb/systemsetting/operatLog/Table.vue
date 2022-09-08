<template>
	<div>
    <div class="spdb-form">
      <Form :label-width="80">
        <Row>
          <Col span="4">
            <FormItem label="操作用户">
              <Input v-model="formBean.userCode"/>
            </FormItem>
          </Col>
          <Col span="4">
            <FormItem label="操作类型">
              <Input v-model="formBean.operatType"/>
              <!-- <Select filterable v-model="searchBean.isEnable">
                <Option v-for="item in enums.state" :key="item.key" :value="item.key">{{item.val}}</Option>
              </Select> -->
            </FormItem>
          </Col>
          <Col span="4">
            <FormItem label="相关作业">
              <Input v-model="formBean.job"/>
            </FormItem>
          </Col>
        </Row>
      </Form>
    </div>

		<div class="spdb-toolbar">
			<Button type="primary" icon="ios-search" @click="demand">查询</Button>
			<Button type="primary" icon="md-close" @click="clear">清除查询</Button>
		</div>

		<div class="spdb-table">
			<Table :loading="loading" :columns="gridColumns" :data="gridData" stripe >
			</Table>
		</div>

		<div class="spdb-page">
			<Page :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat @on-change="changePage" @on-page-size-change="changePageSize"></Page>
		</div>
	</div>
</template>

<script>
import utils from '@/common/utils'
import store from '@/store/index'
import SAuthButton from '_c/s-auth-button'
const RESOURCE_PATH = '/mOperatLog'

export default {
  components: {
    SAuthButton
  },
  props: {
    transData: {
      type: Object,
      default: function () {
    			return {}
      }
    }
  },
  data: function () {
    return {
      formBean: {},
      gridColumns: [
        {
          title: '操作用户',
          width: 120,
          key: 'userCode'
        },
        {
          title: '操作类型',
          width: 200,
          key: 'operatType'
        },
        {
          title: '相关作业',
          width: 120,
          key: 'job'
        },
        {
          title: 'IP',
          width: 150,
          key: 'ip'
        },
        {
          title: '操作时间',
          key: 'operatDate',
          width: 200
        },
        {
          title: '请求',
          key: 'operat',
          width: 200
        },
        {
          title: '响应',
          minWidth: 200,
          key: 'operatContent'
        }
      ],
      gridData: [],
      loading: false,
      page: {
        current: 1,
        total: 10,
        size: 10,
        opt: [10, 50, 100]
      }
    }
  },
  methods: {
    /**
		 * 初始化
		 **/
    init () {
      // this.queryLimitType()
      // this.queryPlatform()
      // if (this.transData.statusName==="weightTable") {
      if (this.transData.currentPage) {
        this.formBean = this.transData.formBean
        this.page.current = this.transData.currentPage
        this.page.size = this.transData.pageSize
      }
      this.search()
    },
    demand () {
      this.page.current = 1
      this.search()
    },
    /**
		 * 查询
		 **/
    search () {
      let params = {}
      Object.assign(params, this.formBean)
      params.current = this.page.current
      params.size = this.page.size
      // this.$set(params, 'orders', [{ column: 'operatDate', asc: false }])
      // this.$set(params, 'asc', false)
      // params.orders.column = 'operatDate'
      // params.orders.asc = false

      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH + '/selectAll',
        params: params
      }
      this.loading = true
      this.$ajax(httpConfig)
        .then(resp => {
          this.loading = false
          this.gridData = resp.data.records
          this.page.total = resp.data.total
        })
    },
    /**
		 * 清空
		 **/
    clear () {
      this.formBean = {}
      this.page = {
        current: 1,
        size: 10
      }
      this.search()
    },
    /**
		 * 改变页码事件
		 **/
    changePage (currentPage) {
      this.page.current = currentPage
      this.search()
    },
    /**
		 * 改变分页大小事件
		 **/
    changePageSize (pageSize) {
      this.page.size = pageSize
      this.search()
    }
  },
  /**
	 * 视图挂载
	 **/
  mounted () {
		 this.init()
  }
}
</script>

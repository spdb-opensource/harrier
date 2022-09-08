<template>
  <div>
    <div class="spdb-form">
      <Form :label-width="80">
        <Row>
          <Col span="4">
            <FormItem label="字典类型">
              <Input v-model="formBean.dicCode"/>
            </FormItem>
          </Col>
          <Col span="4">
            <FormItem label="字典键">
              <Input v-model="formBean.dicKey"/>
              <!-- <Select filterable v-model="searchBean.isEnable">
                <Option v-for="item in enums.state" :key="item.key" :value="item.key">{{item.val}}</Option>
              </Select> -->
            </FormItem>
          </Col>
        </Row>
      </Form>
    </div>
    <div class="spdb-toolbar">
      <Button type="primary" icon="ios-search" @click="demand">查询</Button>
      <Button type="primary" icon="md-close" @click="clear">清除查询</Button>
      <Button type="primary" icon="md-add" @click="add">新增</Button>
      <Button type="primary" :loading="loadingdel" :disabled="ctrlDisable" icon="ios-trash-outline" @click="del()">删除</Button>
    </div>
    <div class="spdb-table">
      <Table :loading="loading" :columns="gridColumns" :data="gridData" @on-selection-change="select"></Table>
    </div>

    <div class="spdb-page">
      <Page placement="top" :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat @on-change="changePage" @on-page-size-change="changePageSize"></Page>
    </div>
  </div>
</template>

<script>
import utils from '@/common/utils'
import common from '@/mixins/common'

const RESOURCE_PATH = '/mDictionary'

export default {
  props: {
    transData: {
      type: Object,
      default: function () {
        return {}
      }
    }
  },
  mixins: [ common ],
  data: function () {
    return {
      formBean: {},
      loadingdel: false,
      gridColumns: [
        {
          type: 'selection',
          width: 60,
          align: 'center'
        },
        {
          title: '字典类型',
          key: 'dicCode',
          minWidth: 100
        },
        {
          title: '字典键',
          key: 'dicKey',
          minWidth: 100
        },
        {
          title: '字典名称',
          key: 'dicName',
          minWidth: 100
        },
        {
          title: '字典值',
          key: 'dicValue',
          align: 'center',
          minWidth: 110
        },
        {
          title: '描述',
          key: 'dicDesc',
          minWidth: 150
        },
        {
          title: '操作',
          align: 'center',
          width: 100,
          // fixed: 'right',
          render: (h, { column, index, row }) => {
            return h('div', [
              h('Button', {
                props: {
                  size: 'small',
                  type: 'info'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    let dicCode = row.dicCode
                    let dicKey = row.dicKey
                    let queryCache = { formBean: this.formBean, currentPage: this.page.current, pageSize: this.page.size }
                    this.$emit('switch', Object.assign({ dicCode: dicCode, dicKey: dicKey, data: row }, queryCache)) // 提交form事件，在parent中显示form
                  }
                }
              }, '编辑')
            ])
          }
        }
      ],
      gridData: [],
      selection: [],
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
      if (this.transData.currentPage) {
        this.formBean = this.transData.formBean
        this.page.current = this.transData.currentPage
        this.page.size = this.transData.pageSize
      }
      this.search()
    },
    /**
     * 查询
     **/
    search () {
      this.selection = []
      let params = {}
      Object.assign(params, this.formBean)
      if (!params.dicCode || params.dicCode === '') {
        delete params.dicCode
      }
      if (!params.dicKey || params.dicKey === '') {
        delete params.dicKey
      }
      params.current = this.page.current
      params.size = this.page.size

      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH,
        params: params
      }
      this.$ajax(httpConfig)
        .then(resp => {
          this.loading = false
          this.gridData = resp.data.records
          this.page.total = resp.data.total
        })
    },
    demand () {
      this.page.current = 1
      this.search()
    },

    /**
     * 清空
     **/
    clear () {
      this.formBean = {}
      this.selection = []
      this.page = {
        current: 1,
        size: 10
      }
    },

    /**
     * 新增
     **/
    add () {
      this.$emit('switch', null)
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
    },

    /**
     * 删除
     **/
    del: function () {
      const ids = Array.from(this.selection, e => e.id)
      let deleteConfig = {
        method: 'DELETE',
        url: `/mDictionary/${ids}/delete`,
        data: {}
      }
      this.loadingdel = true
      this.$ajax(deleteConfig)
        .then(resp => {
          this.loadingdel = false
          this.search()
        })
    }
  },
  /**
   * 视图挂载
   **/
  mounted () {
    // this.init()
  }
}
</script>

<template>

	<div>
		<Alert v-if="errorshow" type="error">
			<span slot="desc" class="warningtext" :style="msgerrorstyle" ref="error">{{errormsg}}</span>
		</Alert>
		<Alert  v-if="warnshow" type="warning">
			<span slot="desc" class="warningtext" :style="msgwarnstyle" ref="warn">警告:执行SQL一切后果由操作者承担!</span>
		</Alert>
		<div class="spdb-form">
			<Form ref="systemconfigForm" :model="formBean" :label-width="80" label-position="top" :rules="formRule">
        <Row>
					<Span style="fontSize:16px;color:#ff9900;">请输入SQL,注意: 仅支持select,update,delete</Span>
				</Row>
        <!-- <Row>
			   <span style="fontSize:16px;color:#ff9900">(常用表：依赖表:uds_job_dependency; 作业信息表:uds_job_config; 定时和触发作业表:uds_job_time_trigger; 作业频度表:uds_job_date_frequency)</span>
				</Row> -->
				<Row>
					<!--<FormItem prop="selectSql" label="请输入SQL,注意: 仅支持Explain,Show,Help:">
					</FormItem>-->
					<Input v-model="formBean.selectSql" type="textarea" :autosize="{minRows: 3,maxRows: 100}" placeholder="请输入SQL语句！ SQL结束不需要标点符号"></Input>
				</Row>
				<Row>
					<Checkbox v-model="formBean.isAgree" > 我承担执行SQL的后果</Checkbox>
					<FormItem prop="" label="">
					</FormItem>
				</Row>
			</Form>
		</div>
		<div class="spdb-toolbar">
			<Button type="primary" :loading="loading" icon="md-checkmark" @click="search">提交</Button>
			<Button type="primary" icon="md-close" @click="clear">清除查询</Button>
			<!-- <Button type="primary" icon="md-cloud-download" @click="download">下载</Button> -->
		</div>

		<div class="spdb-table">
			<Table border :columns="gridColumns" :data="gridData" stripe >
			</Table>
		</div>

		<div class="spdb-page">
			<Page placement="top" :total="page.total" :current="page.current" :page-size="page.size" :page-size-opts="page.opt" show-total show-sizer show-elevat @on-change="changePage" @on-page-size-change="changePageSize"></Page>
		</div>
	</div>
</template>

<script>
// import common from '@/mixins/common'
// import Alert from 'iview'
import utils from '@/common/utils'
const RESOURCE_PATH = '/jobmanage/customsql'

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
    return {
      msgwarnstyle: {
        fontSize: '14px',
        color: 'red',
        fontWeight: 'bold'
      },
      msgerrorstyle: {
        fontSize: '14px',
        color: 'red',
        fontWeight: 'bold'
      },
      errormsgtmp: '',
      errorshow: false,
      warnshow: true,
      formBean: { isAgree: true },
      loading: false,
      request: {
        delReq: {
          method: 'DELETE',
          url: RESOURCE_PATH
        },
        addReq: {
          method: 'POST',
          url: RESOURCE_PATH
        }
      },
      formRule: {
        selectSql: [{
          required: true,
          message: '请输入自定义SQL！'
        }]
      },
      gridColumns: [],
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
      // this.search();
    },
    /**
     * 如果传过来的有初始数据则进行数据绑定
    **/
    bindData (fields) {
      try {
        const data = Object.assign({}, this.transData.initFormBean)
        if (fields) { // 进行可选字段初始化
          for (const field of fields) {
            this.formBean[field] = data[field]
          }
        } else { // 进行全量字段初始化（默认是只有主表的主键的）
          this.formBean = Object.assign({}, data)
        }
      } catch (error) {
        console.error(error)
      }
    },
    /**
		 * 下载
		 **/
    download () {
      let params = {}
      Object.assign(params, this.formBean)
      params.type = 'excel'
      if (!this.formBean.selectSql) {
        this.$Message.warning({ content: 'sql语句不能为空！' })
        return
      } else if (!this.formBean.isAgree === true) {
        this.$Message.warning({ content: '请同意 我承担执行SQL的后果!' })
        return
      }
      utils.download(RESOURCE_PATH + '/downLoad', params)
    },
    /**
		 * 查询
		 **/
    search (pageChange) {
      let params = {}
      if (typeof this.formBean.selectSql === 'undefined' || this.formBean.selectSql.trim().length === 0) {
        this.$Message.warning({ content: 'sql语句不能为空！' })
        return
      } else if (!this.formBean.isAgree === true) {
        this.$Message.warning({ content: '请同意 我承担执行SQL的后果!' })
        return
      }
      Object.assign(params, this.formBean)
      if (pageChange === 'change') {
        params.pageCurrent = this.page.current
        params.pageSize = this.page.size
      } else {
        this.page.current = 1
        // this.page.size = 10
        params.pageCurrent = 1
        params.pageSize = this.page.size
      }

      let httpConfig = {
        method: 'GET',
        url: RESOURCE_PATH,
        params: params
      }
      if (this.formBean.selectSql.trim().toUpperCase().startsWith('INSERT') ||
            this.formBean.selectSql.trim().toUpperCase().startsWith('UPDATE') ||
            this.formBean.selectSql.trim().toUpperCase().startsWith('DELETE')
      ) {
        // if (this.$store.getters.getSystems.indexOf('ROLE_ADMIN') === -1) {
        //   this.$Message.error('您非系统管理员，无增删改的权限，如需请联系系统管理员')
        //   return
        // }
        httpConfig = {
          method: 'POST',
          url: RESOURCE_PATH,
          data: params
        }
      } else if (this.formBean.selectSql.trim().toUpperCase().startsWith('SELECT')) {
        // for (let key in params) {
        //   params[key] = encodeURIComponent(params[key])// encodeURIComponent(params[key])
        // }
        let httpConfig = {
          method: 'GET',
          url: RESOURCE_PATH,
          params: params
        }
      }
      this.loading = true
      this.$ajax(httpConfig)
        .then(resp => {
          this.loading = false
          // console.log(resp)
          if (resp.data.returnCode && resp.data.returnCode === 'fail') {
            // this.$Message.error(resp.data.returnDetailMsg.substring(12));
            this.errorshow = true
            this.errormsgtmp = resp.data.returnDetailMsg.substring(12)
            this.gridColumns = []
            this.gridData = []
            this.page.total = 0
            return
          } else {
            this.errorshow = false
          }
          if (resp.status === 200 && !this.formBean.selectSql.trim().toUpperCase().startsWith('SELECT')) {
            if (this.formBean.selectSql.trim().toUpperCase().startsWith('INSERT')) {

            } else {
              this.$Message.success('操作成功')
            }
            return
          } else if (resp.status !== 200) {
            this.$Message.success('操作失败')
            return
          }
          // console.log(resp);
          let datas = resp.data.rows
          if (resp.data.returnCode != null && resp.data.returnCode !== 'undefined' && resp.data.returnCode === 'fail') {
            this.errorshow = true
            this.errormsgtmp = resp.data.returnDetailMsg

            return
          } else {
            this.errorshow = false
          }
          var columnNames = datas.shift(0).columnNames
          // console.log(columnNames.length);
          let headerw = new Array(columnNames.length).fill(0)
          // console.log(headerw);
          let tmpGridColumn = [{
            type: 'index',
            title: ' ',
            width: 60,
            // align: 'center',
            fixed: 'left'

          }]
          for (let i = 0; i < datas.length; i++) {
            let data = datas[i]
            for (let j = 0; j < columnNames.length; j++) {
              let tmp = data[columnNames[j]] != null ? data[columnNames[j]].length : 0
              if (headerw[j] < tmp) {
                headerw[j] = tmp
              }
            }
          }
          for (let i = 0; i < columnNames.length; i++) {
            let tmpColumn = {}
            tmpColumn.title = columnNames[i]
            tmpColumn.key = columnNames[i]
            headerw.push(tmpColumn.title.length)
            let len = tmpColumn.title.length
            if (len < headerw[i]) {
              len = headerw[i]
            }
            tmpColumn.width = len * 15
            if (tmpColumn.width < 100) {
              tmpColumn.width = 100
            }
            tmpGridColumn.push(tmpColumn)
          }
          if (this.formBean.selectSql.trim().toUpperCase().startsWith('SELECT')) {
            this.gridColumns = tmpGridColumn
            this.gridData = datas
            this.page.total = resp.data.total
          }
        })
        .catch(error => {
          console.log(error)
        })
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
		 * 改变页码事件
		 **/
    changePage (currentPage) {
      let pageChange = 'change'
      this.page.current = currentPage
      this.search(pageChange)
    },
    /**
		 * 改变分页大小事件
		 **/
    changePageSize (pageSize) {
      let pageChange = 'change'
      this.page.size = pageSize
      this.search(pageChange)
    }
  },
  computed: {
    errormsg: function () {
      return this.errormsgtmp
    }
  },
  watch: {
    selection: function (val) {
      if (val.length !== 0) {
        this.ctrlDisable = false
      } else {
        this.ctrlDisable = true
      }
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
<style scoped>
	.warningtext {
		color: "red";
		weight: 300;
	}
</style>

export default {
  data: function () {
    return {
      selection: [],
      page: {
        current: 1,
        total: 10,
        size: 10,
        opt: [10, 50, 100]
      },
      ctrlDisable: true,
      loading: true
    }
  },
  methods: {
    select: function (selection) {
      this.selection = selection
    },
    changePage: function (currentPage) {
      this.page.current = currentPage
      this.search()
    },
    changePageSize: function (pageSize) {
      this.page.size = pageSize
      this.search()
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
  mounted: function () {
    this.init()
  }
}

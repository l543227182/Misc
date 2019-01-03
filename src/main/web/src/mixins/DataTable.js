export default {
  data () {
    return {
      search: '',
      condition: {
        sort: '',
        order: ''
      }
    }
  },
  computed: {
    sortMap () {
      return {}
    }
  },
  methods: {
    getQueryParams () {
      const params = {}
      const pagination = this.$refs.page
      if (pagination) {
        params.offset = pagination.cursor
        params.limit = pagination.pageSize
      }
      const condition = this.condition
      if (condition) {
        // sort
        if (condition.sort) {
          const sort = this.sortMap[condition.sort] || condition.sort
          const order = condition.order === 'descending' ? 'desc' : 'asc'
          params['order_by'] = `${sort} ${order}`
        }
        // filter
        const filters = []
        _.forEach(condition.filters, (value, key) => {
          if (value) {
            filters.push({property: key, value})
          }
        })
        params.filters = filters
      }
      // search
      if (this.search) {
        params['search'] = this.search
      }
      return params
    },
    sortChange ({ column, prop, order }) {
      this.condition.sort = prop
      this.condition.order = order
    },
    filterChange (filters) {
      const condition = this.condition
      if (condition && condition.filters) {
        _.forEach(filters, (value, key) => {
          condition.filters[key] = value.join(',')
        })
      }
    }
  }
}

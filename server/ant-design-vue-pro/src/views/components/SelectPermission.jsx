import Vue from 'vue'
import { Select } from 'ant-design-vue'
import { listPermission, axios } from '@/api/entity'

const CancelToken = axios.CancelToken
export default {
  extends: Select,
  props: {
    showSearch: {
      type: Boolean,
      default: true
    },
    showArrow: {
      type: Boolean,
      default: false
    },
    filterOption: {
      type: Boolean,
      default: false
    },
    defaultActiveFirstOption: {
      type: Boolean,
      default: false
    }
  },
  mounted () {
    this.$children[0].$on('search', this.search)
  },
  data () {
    return {
      domain: {
        data: [],
        wait: null,
        cancel: () => {}
      }
    }
  },
  methods: {
    changeLoading (v) {
      const f = Vue.config.silent // 错误日志 !!
      Vue.config.silent = true
      this.loading = v
      Vue.config.silent = f
    },
    search (v) {
      if (!v) return
      this.domain.cancel('')
      clearTimeout(this.domain.wait)
      this.changeLoading(false)
      this.domain.wait = setTimeout(() => {
        this.changeLoading(true)
        this.webappSearch(v).finally(() => {
          this.changeLoading(false)
        })
      }, 1000)
    },
    webappSearch (v) {
      const param = {}
      param.name = {
        key: 'name',
        val: '%' + v + '%',
        opt: 'like'
      }
      const the = this
      return listPermission({ query: param }, {
        cancelToken: new CancelToken(cancel => {
          the.domain.cancel = cancel
        })
      }).then((res) => {
        const data = []
        const vm = this
        res.content.forEach((e) => {
          data.push(e)
        })
        vm.$emit('searched', data, v)
      }).catch((e) => {})
    }
  }
}

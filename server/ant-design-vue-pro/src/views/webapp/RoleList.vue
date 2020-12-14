<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="名称">
                <a-input v-model="queryParam.name" placeholder=""/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="应用名">
                <a-input v-model="queryParam.webappName" placeholder=""/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
              <a-button style="margin-left: 8px" @click="() => this.queryParam = {}">重置</a-button>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <div class="table-operator">
        <a-button type="primary" icon="plus" @click="handleAdd">新建</a-button>
        <a-dropdown v-action:edit v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="1"><a-icon type="delete" />删除</a-menu-item>
            <!-- lock | unlock -->
            <a-menu-item key="2"><a-icon type="lock" />锁定</a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px">
            批量操作 <a-icon type="down" />
          </a-button>
        </a-dropdown>
      </div>

      <s-table
        ref="table"
        size="default"
        rowKey="id"
        :columns="columns"
        :data="loadData"
        showPagination="auto"
      >
        <span slot="action" slot-scope="text, record">
          <template>
            <a @click="handleEdit(record)">配置</a>
            <a-divider type="vertical" />
            <a @click="handleSub(record)">订阅报警</a>
          </template>
        </span>
      </s-table>
    </a-card>

    <div>
      <a-modal v-model="domain.visible" title="edit" :footer="null">
        <a-form :form="editForm" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }" @submit="editSubmit">
          <a-form-item label="名称">
            <a-input
              v-decorator="['name', { rules: [{ required: true, message: 'Please input your note!' }] }]"
            />
          </a-form-item>
          <a-form-item label="应用">
            <SelectWebapp
              v-decorator="['webapp.id', { rules: [{ required: true, message: 'Please input your note!' }] }]"
              placeholder="input search text"
              @searched="searched"
              @select="selectWebapp"
            >
            <a-select-option v-for="d in domain.searchData" :key="d.id">{{ d.name }}</a-select-option>
            </SelectWebapp>
          </a-form-item>
          <a-form-item label="描述">
            <a-input
              v-decorator="['description', { rules: [{ message: 'Please input your note!' }] }]"
            />
          </a-form-item>
          <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
            <a-button type="primary" html-type="submit">Submit</a-button>
          </a-form-item>
        </a-form>
      </a-modal>
    </div>
  </page-header-wrapper>
</template>

<script>
import moment from 'moment'
import { STable, Ellipsis } from '@/components'
import { listRole, saveRole, trimObject } from '@/api/entity'
import SelectWebapp from '@/views/components/SelectWebapp'

const columns = [
  {
    title: 'ID',
    dataIndex: 'id'
  },
  {
    title: '名称',
    dataIndex: 'name'
  },
  {
    title: '描述',
    dataIndex: 'description'
  },
  {
    title: '应用',
    dataIndex: 'webapp.name'
  },
  {
    title: '创建时间',
    dataIndex: 'createTime'
  },
  {
    title: '操作',
    dataIndex: 'action',
    width: '150px',
    scopedSlots: { customRender: 'action' }
  }
]

export default {
  name: 'TableList',
  components: {
    SelectWebapp,
    STable,
    Ellipsis
  },
  data () {
    this.columns = columns
    return {
      domain: {
        obj: {},
        wait: null,
        cancel: () => {},
        searchData: [],
        loading: false,
        visible: false
      },
      editForm: this.$form.createForm(this),
      confirmLoading: false,
      mdl: null,
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: { },
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        const param = { ...this.queryParam }
        trimObject(param)
        if (param.name) {
          param.name = {
            val: '%' + param.name + '%',
            opt: 'like'
          }
        }
        if (param.webappName) {
          param.webappName = {
            key: 'webapp.name',
            val: '%' + param.webappName + '%',
            opt: 'like'
          }
        }
        const query = Object.assign({}, parameter, { query: param })
        return listRole(query)
          .then(res => {
            return res
          })
      },
      selectedRowKeys: [],
      selectedRows: []
    }
  },
  created () {
    // getRoleList({ t: new Date() })
  },
  computed: {
    rowSelection () {
      return {
        selectedRowKeys: this.selectedRowKeys,
        onChange: this.onSelectChange
      }
    }
  },
  methods: {
    selectWebapp (v, opt) {
      this.domain.obj.webapp = { id: v }
    },
    searched (data) {
      this.domain.searchData = data
    },
    handleEdit (record) {
      const cp = Object.assign({}, record)
      this.domain.visible = true
      this.domain.searchData = [ cp.webapp ]
      this.$nextTick(() => {
        this.domain.obj = cp
        this.showEdit(cp)
      })
    },
    showEdit (record) {
      const rc = Object.assign({}, record, { webapp: { id: record.webapp.id, name: record.webapp.name } })
      delete rc.id
      delete rc.createTime
      this.editForm.setFieldsValue(rc)
    },
    handleAdd () {
      this.domain.visible = true
      this.domain.obj = {}
      this.domain.searchData = []
      this.editForm.resetFields()
    },
    editSubmit (e) {
      e.preventDefault()
      this.editForm.validateFields((err, values) => {
        if (!err) {
          // console.log('Received values of form: ', values)
          const entity = Object.assign({}, this.domain.obj, values)
          saveRole(entity).then((res) => {
            this.$refs.table.refresh()
            this.domain.visible = false
            this.$message.info('提交成功')
          }).catch((e) => {
            this.$message.error('出错了 : ' + e.response.data.message)
          })
        }
      })
    },
    handleOk () {
      const form = this.$refs.createModal.form
      this.confirmLoading = true
      form.validateFields((errors, values) => {
        if (!errors) {
          console.log('values', values)
          if (values.id > 0) {
            // 修改 e.g.
            new Promise((resolve, reject) => {
              setTimeout(() => {
                resolve()
              }, 1000)
            }).then(res => {
              this.visible = false
              this.confirmLoading = false
              // 重置表单数据
              form.resetFields()
              // 刷新表格
              this.$refs.table.refresh()

              this.$message.info('修改成功')
            })
          } else {
            // 新增
            new Promise((resolve, reject) => {
              setTimeout(() => {
                resolve()
              }, 1000)
            }).then(res => {
              this.visible = false
              this.confirmLoading = false
              // 重置表单数据
              form.resetFields()
              // 刷新表格
              this.$refs.table.refresh()

              this.$message.info('新增成功')
            })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleCancel () {
      this.visible = false

      const form = this.$refs.createModal.form
      form.resetFields() // 清理表单数据（可不做）
    },
    handleSub (record) {
      if (record.status !== 0) {
        this.$message.info(`${record.no} 订阅成功`)
      } else {
        this.$message.error(`${record.no} 订阅失败，规则已关闭`)
      }
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    resetSearchForm () {
      this.queryParam = {
        date: moment(new Date())
      }
    }
  }
}
</script>

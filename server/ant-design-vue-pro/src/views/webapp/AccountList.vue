<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="姓名">
                <a-input v-model="queryParam.name" placeholder=""/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="账号">
                <a-input v-model="queryParam.account" placeholder=""/>
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
        <span slot="disableOpt" slot-scope="text, record">
          <a-switch default-checked :checked="!record.disable" :loading="record.disableLoading" @change="(c, e) => { disableAccount(c, e, record) }" />
        </span>
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">配置</a>
        </span>
      </s-table>
    </a-card>

    <div>
      <a-modal v-model="domain.visible" title="edit" :footer="null">
        <a-form :form="editForm" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }" @submit="editSubmit">
          <a-form-item label="姓名">
            <a-input
              v-decorator="['name', { rules: [{ required: true, message: 'Please input your note!' }] }]"
            />
          </a-form-item>
          <a-form-item label="账号">
            <a-input
              v-decorator="['account', { rules: [{ required: true, message: 'Please input your note!' }] }]"
            />
          </a-form-item>
          <a-form-item label="状态">
            <a-radio-group
              v-decorator="['disable', { initialValue: false, rules: [{ required: true, message: 'Please input your note!' }] }]"
            >
              <a-radio :value="false">
                启用
              </a-radio>
              <a-radio :value="true">
                禁用
              </a-radio>
            </a-radio-group>
          </a-form-item>
          <a-form-item label="电话">
            <a-input
              v-decorator="['phone', { rules: [{ message: 'Please input your note!' }] }]"
            />
          </a-form-item>
          <a-form-item label="邮箱">
            <a-input
              v-decorator="['email', { type: 'email', rules: [{ message: 'Please input your note!' }] }]"
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
import { listAccount, saveAccount, trimObject } from '@/api/entity'

const columns = [
  {
    title: 'ID',
    dataIndex: 'id'
  },
  {
    title: '姓名',
    dataIndex: 'name'
  },
  {
    title: '账号',
    dataIndex: 'account'
  },
  {
    title: '状态',
    scopedSlots: { customRender: 'disableOpt' },
    dataIndex: 'disable'
  },
  {
    title: '电话',
    dataIndex: 'phone'
  },
  {
    title: '邮箱',
    dataIndex: 'email'
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
    STable,
    Ellipsis
  },
  data () {
    this.columns = columns
    return {
      domain: {
        obj: {},
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
        if (param.account) {
          param.account = {
            key: 'account',
            val: '%' + param.account + '%',
            opt: 'like'
          }
        }
        const query = Object.assign({}, parameter, { query: param })
        return listAccount(query)
          .then(res => {
            res.content && res.content.forEach((e) => { e.disableLoading = false })
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
    disableAccount (checked, e, record) {
      record.disableLoading = true
      saveAccount({ id: record.id, disable: !checked })
        .then((res) => {
          record.disable = !checked
          this.$message.info('修改成功')
        }).catch((er) => {
          this.$message.error('出错了 : ' + er.response.data.message)
        }).finally(() => {
          record.disableLoading = false
        })
    },
    handleEdit (record) {
      this.domain.visible = true
      this.$nextTick(() => {
        this.domain.obj = record
        this.showEdit(record)
      })
    },
    showEdit (record) {
      const rc = Object.assign({}, record)
      delete rc.id
      delete rc.createTime
      delete rc.disableLoading
      this.editForm.setFieldsValue(rc)
    },
    handleAdd () {
      this.domain.visible = true
      this.domain.obj = {}
      this.editForm.resetFields()
    },
    editSubmit (e) {
      e.preventDefault()
      this.editForm.validateFields((err, values) => {
        if (!err) {
          // console.log('Received values of form: ', values)
          const entity = Object.assign({}, this.domain.obj, values)
          saveAccount(entity).then((res) => {
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

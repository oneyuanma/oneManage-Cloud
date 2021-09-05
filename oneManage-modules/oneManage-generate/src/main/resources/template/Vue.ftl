<template>
  <div class="app-container">
    <div class="filter-container">
      <#list columns as c>
        <#if c.ifQuery == "true">
          <el-input v-model="listQuery.${c.doColumnName}" placeholder="${c.columnComment}" style="width: 200px;margin-right: 10px;" class="filter-item"@keyup.enter.native="handleFilter"/>
        </#if>
      </#list>
      <el-button v-waves plain class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button plain  class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-plus" @click="handleCreate">
        新增${functionComment}
      </el-button>
    </div>

    <el-table
            :key="tableKey"
            v-loading="listLoading"
            :data="list"
            border
            fit
            highlight-current-row
            style="width: 100%;"
    >
      <#list columns as c>
        <#if c.ifList == "true">
          <el-table-column label="${c.columnComment}" prop="${c.doColumnName}" min-width="100px" align="center"/>
        </#if>
      </#list>
      <el-table-column label="操作" align="center" min-width="120" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>

          <el-button size="mini"  type="danger" @click="handleDelete(row,$index)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.nowPageIndex" :limit.sync="listQuery.pageSize" @pagination="getList"/>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="35%">

      <el-form :model="form" :rules="rules" ref="form" label-width="80px" class="demo-ruleForm">

        <#list columns as c>
          <#if c.ifEdit == "true">
            <#if c.viewType == "text_input">
              <el-form-item label="${c.columnComment}" prop="${c.doColumnName}">
                <el-input v-model="form.${c.doColumnName}"></el-input>
              </el-form-item>
            </#if>
            <#if c.viewType == "text_area">
              <el-form-item label="${c.columnComment}" prop="${c.doColumnName}">
                <el-input type="textarea" v-model="form.${c.doColumnName}"></el-input>
              </el-form-item>
            </#if>
            <#if c.viewType == "date">
            <el-form-item label="${c.columnComment}" prop="${c.doColumnName}">
                <el-date-picker
                        v-model="form.${c.doColumnName}"
                        type="datetime"
                        placeholder="选择日期时间">
                </el-date-picker>
            </el-form-item>
            </#if>
          </#if>
        </#list>

        <el-form-item>
          <el-button @click="resetForm()">重置</el-button>
          <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
            保存${functionComment}
          </el-button>
        </el-form-item>
      </el-form>

    </el-dialog>

  </div>
</template>

<script>
import { pageList, ${classNameParam}Save, ${classNameParam}Delete } from '@/api/${classNameParam}'
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },

  data() {
    return {
      form: {
        id: '',
    <#list columns as c>
    <#if c.ifEdit == "true">
      ${c.doColumnName}: '',
    </#if>
    </#list>
      },
      rules: {
        <#list columns as c>
        <#if c.ifEdit == "true">
        ${c.doColumnName}: [
          <#if c.ifNull == "true">
          { required: true, message: '请输入${c.columnComment}', trigger: 'blur' },
          </#if>
            <#if c.columnType == "String">
          <#if (c.length)??>
          { min: 1, max: ${c.length?c}, message: '长度在 1 到  ${c.length} 个', trigger: 'blur' }
          </#if>
            </#if>
        ],
        </#if>
        </#list>
      },
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
    <#list columns as c>
    <#if c.ifQuery == "true">
        ${c.doColumnName}: '',
    </#if>
    </#list>
        nowPageIndex: 1,
        pageSize: 10
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改${functionComment}',
        create: '新增${functionComment}'
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      pageList(this.listQuery).then(response => {
        console.log(response)
        this.list = response.data.list
        this.total = response.data.total
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    // 表单重置
    resetForm() {
      this.form = {
        <#list columns as c>
        <#if c.ifEdit == "true">
        ${c.doColumnName}: '',
        </#if>
        </#list>
      }
    },
    // 显示新增${functionComment}表单
    handleCreate() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    },
    // 保存${functionComment}
    createData() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          ${classNameParam}Save(this.form).then(() => {
            this.getList()
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '保存${functionComment}成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // 显示修改${functionComment}表单
    handleUpdate(row) {
      this.form = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    },
    // 修改${functionComment}
    updateData() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          // const tempData = Object.assign({}, this.form)
          ${classNameParam}Save(this.form).then(() => {
            const index = this.list.findIndex(v => v.id === this.form.id)
            this.list.splice(index, 1, this.form)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '更新${functionComment}成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // ${functionComment}删除
    handleDelete(row, index) {
      this.$confirm('确定删除${functionComment}吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        ${classNameParam}Delete({ 'id': row.id }).then(() => {
          this.getList()
          this.$notify({
            title: 'Success',
            message: '删除${functionComment}成功',
            type: 'success',
            duration: 2000
          })
        })
      })
    }
  }
}
</script>

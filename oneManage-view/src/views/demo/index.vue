<template>
  <div class="app-container">
    <div class="filter-container">
                <el-input v-model="listQuery.description" placeholder="文本域例子" style="width: 200px;margin-right: 10px;" class="filter-item"@keyup.enter.native="handleFilter"/>
          <el-input v-model="listQuery.time" placeholder="时间选择器例子" style="width: 200px;margin-right: 10px;" class="filter-item"@keyup.enter.native="handleFilter"/>
          <el-input v-model="listQuery.title" placeholder="input例子" style="width: 200px;margin-right: 10px;" class="filter-item"@keyup.enter.native="handleFilter"/>
      <el-button v-waves plain class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button plain  class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-plus" @click="handleCreate">
        新增代码生成工具演示demo
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
          <el-table-column label="文本域例子" prop="description" min-width="100px" align="center"/>
          <el-table-column label="时间选择器例子" prop="time" min-width="100px" align="center"/>
          <el-table-column label="input例子" prop="title" min-width="100px" align="center"/>
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

              <el-form-item label="文本域例子" prop="description">
                <el-input type="textarea" v-model="form.description"></el-input>
              </el-form-item>
            <el-form-item label="时间选择器例子" prop="time">
                <el-date-picker
                        v-model="form.time"
                        type="datetime"
                        placeholder="选择日期时间">
                </el-date-picker>
            </el-form-item>
              <el-form-item label="input例子" prop="title">
                <el-input v-model="form.title"></el-input>
              </el-form-item>

        <el-form-item>
          <el-button @click="resetForm()">重置</el-button>
          <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
            保存代码生成工具演示demo
          </el-button>
        </el-form-item>
      </el-form>

    </el-dialog>

  </div>
</template>

<script>
import { pageList, generateDemoSave, generateDemoDelete } from '@/api/generateDemo'
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
      description: '',
      time: '',
      title: '',
      },
      rules: {
        description: [
          { min: 1, max: 1000, message: '长度在 1 到  1,000 个', trigger: 'blur' }
        ],
        time: [
        ],
        title: [
          { min: 1, max: 100, message: '长度在 1 到  100 个', trigger: 'blur' }
        ],
      },
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        description: '',
        time: '',
        title: '',
        nowPageIndex: 1,
        pageSize: 10
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改代码生成工具演示demo',
        create: '新增代码生成工具演示demo'
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
        description: '',
        time: '',
        title: '',
      }
    },
    // 显示新增代码生成工具演示demo表单
    handleCreate() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    },
    // 保存代码生成工具演示demo
    createData() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          generateDemoSave(this.form).then(() => {
            this.getList()
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '保存代码生成工具演示demo成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // 显示修改代码生成工具演示demo表单
    handleUpdate(row) {
      this.form = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    },
    // 修改代码生成工具演示demo
    updateData() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          // const tempData = Object.assign({}, this.form)
          generateDemoSave(this.form).then(() => {
            const index = this.list.findIndex(v => v.id === this.form.id)
            this.list.splice(index, 1, this.form)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '更新代码生成工具演示demo成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // 代码生成工具演示demo删除
    handleDelete(row, index) {
      this.$confirm('确定删除代码生成工具演示demo吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        generateDemoDelete({ 'id': row.id }).then(() => {
          this.getList()
          this.$notify({
            title: 'Success',
            message: '删除代码生成工具演示demo成功',
            type: 'success',
            duration: 2000
          })
        })
      })
    }
  }
}
</script>

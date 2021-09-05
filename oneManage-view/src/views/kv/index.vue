<template>
  <div class="app-container">
    <div class="filter-container">

      <el-input v-model="listQuery.k" placeholder="键" style="width: 200px;" class="filter-item"@keyup.enter.native="handleFilter"/>
      <el-input v-model="listQuery.v" placeholder="值" style="width: 200px;margin-left: 10px;" class="filter-item"@keyup.enter.native="handleFilter"/>
      <el-button v-waves plain v-if="checkPermission(['system-kv-query'])" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button plain v-if="checkPermission(['system-kv-edit'])" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-plus" @click="handleCreate">
        新增键值对
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
      <el-table-column label="键" prop="k" min-width="100px" align="center"/>
      <el-table-column label="值" prop="v" min-width="100px" align="center"/>
      <el-table-column label="备注" prop="remarks" min-width="100px" align="center"/>

      <el-table-column label="操作" align="center" min-width="120" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" v-if="checkPermission(['system-kv-edit'])" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>

          <el-button size="mini" v-if="checkPermission(['system-kv-del'])" type="danger" @click="handleDelete(row,$index)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.nowPageIndex" :limit.sync="listQuery.pageSize" @pagination="getList"/>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="35%">

      <el-form :model="kvForm" :rules="rules" ref="kvForm" label-width="80px" class="demo-ruleForm">
        <el-form-item label="键" prop="k">
          <el-input v-model="kvForm.k"></el-input>
        </el-form-item>
        <el-form-item label="值" prop="v">
          <el-input v-model="kvForm.v"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input type="textarea" v-model="kvForm.remarks"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="resetForm()">重置</el-button>
          <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
            保存键值对
          </el-button>
        </el-form-item>
      </el-form>

    </el-dialog>

  </div>
</template>

<script>
import { pageList, kvSave, kvDelete } from '@/api/kv'
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import checkPermission from '@/utils/permission' // 权限判断函数

export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },

  data() {
    return {
      kvForm: {
        id: '',
        k: '',
        v: '',
        remarks: ''
      },
      rules: {
        k: [
          { required: true, message: '请输入键', trigger: 'blur' },
          { min: 1, max: 40, message: '长度在 1 到 40 个字符', trigger: 'blur' }
        ],
        v: [
          { required: true, message: '请输入值', trigger: 'blur' }
        ]
      },
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        nowPageIndex: 1,
        pageSize: 10,
        k: '',
        v: ''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改键值对',
        create: '新增键值对'
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    checkPermission,
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
      this.kvForm = {
        kvname: '',
        nickName: '',
        password: '',
        phone: '',
        email: '',
        sex: ''
      }
    },
    // 显示新增键值对表单
    handleCreate() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['kvForm'].clearValidate()
      })
    },
    // 保存键值对
    createData() {
      this.$refs['kvForm'].validate((valid) => {
        if (valid) {
          kvSave(this.kvForm).then(() => {
            this.getList()
            // this.list.unshift(this.kvForm)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '保存键值对成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // 显示修改键值对表单
    handleUpdate(row) {
      this.kvForm = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['kvForm'].clearValidate()
      })
    },
    // 修改键值对
    updateData() {
      this.$refs['kvForm'].validate((valid) => {
        if (valid) {
          // const tempData = Object.assign({}, this.kvForm)
          kvSave(this.kvForm).then(() => {
            const index = this.list.findIndex(v => v.id === this.kvForm.id)
            this.list.splice(index, 1, this.kvForm)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '更新键值对成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // 键值对删除
    handleDelete(row, index) {
      this.$confirm('确定删除键值对吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        kvDelete({ 'id': row.id }).then(() => {
          this.getList()
          this.$notify({
            title: 'Success',
            message: '删除键值对成功',
            type: 'success',
            duration: 2000
          })
        })
      })
    }
  }
}
</script>

<template>
  <div class="app-container">
    <div class="filter-container">

      <el-input v-model="listQuery.username" placeholder="用户名" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.nickName" placeholder="昵称" style="width: 200px;margin-left: 10px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button v-waves plain v-if="checkPermission(['system-user-query'])" class="filter-item"  style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button plain v-if="checkPermission(['system-user-edit'])" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-plus" @click="handleCreate">
        新增用户
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
<!--      <el-table-column label="ID" prop="id"  align="center"  width="80"/>-->
      <el-table-column label="用户名" prop="username" min-width="100px" align="center" />
      <el-table-column label="昵称" prop="nickName" min-width="100px" align="center"/>
      <el-table-column label="邮箱" prop="email" min-width="100px" align="center"/>
      <el-table-column label="电话" prop="phone" min-width="100px" align="center"/>
      <el-table-column label="状态" prop="" min-width="100px" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.status == 0"><el-tag type="success">正常</el-tag></span>
          <span v-else><el-tag type="danger">禁用</el-tag></span>
        </template>
      </el-table-column>
      <el-table-column label="性别" min-width="50px" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.sex == 0">男</span>
          <span v-else>女</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" min-width="120" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" v-if="checkPermission(['system-user-edit'])" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button v-if="row.status=='1' && checkPermission(['system-user-edit'])" size="mini" type="success" @click="handleModifyStatus(row,'published')">
            启用
          </el-button>
          <el-button v-if="row.status=='0' && checkPermission(['system-user-edit'])" size="mini" type="warning" @click="handleModifyStatus(row,'draft')">
            禁用
          </el-button>
          <el-button size="mini" v-if="checkPermission(['system-user-del'])" type="danger" @click="handleDelete(row,$index)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.nowPageIndex" :limit.sync="listQuery.pageSize" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="35%" >

      <el-form :model="userForm" :rules="rules" ref="userForm" label-width="80px" class="demo-ruleForm">
        <el-form-item label="登录名" prop="username">
          <el-input v-model="userForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="userForm.password"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="userForm.nickName"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userForm.email"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="userForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="userForm.sex" placeholder="请选择性别">
            <el-option label="男" value="0"></el-option>
            <el-option label="女" value="1"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="角色" prop="role">
          <el-select v-model="userForm.roles" multiple placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button @click="resetForm()">重置</el-button>
          <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          保存用户
          </el-button>
        </el-form-item>
      </el-form>

    </el-dialog>

  </div>
</template>

<script>
import { fetchList, userSave, userDelete, userSet } from '@/api/user'
import { selectOptions } from '@/api/role'
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import checkPermission from '@/utils/permission' // 权限判断函数

export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },

  data() {
    var checkPhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('手机号不能为空'))
      } else {
        const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
        console.log(reg.test(value))
        if (reg.test(value)) {
          callback()
        } else {
          return callback(new Error('请输入正确的手机号'))
        }
      }
    }
    return {
      userForm: {
        id: '',
        username: '',
        nickName: '',
        password: '',
        phone: '',
        email: '',
        sex: '',
        status: '',
        roles: []
      },
      rules: {
        username: [
          { required: true, message: '请输入登录名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        nickName: [
          { required: true, message: '请输入昵称', trigger: 'change' },
          { min: 3, max: 30, message: '长度在 3 到 30 个字符', trigger: 'blur' }
        ],
        phone: [
          { validator: checkPhone, trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        sex: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ]
      },
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        nowPageIndex: 1,
        pageSize: 10,
        username: '',
        nickName: ''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改用户',
        create: '新增用户'
      },
      options: []
    }
  },
  created() {
    this.getList()
  },
  methods: {
    checkPermission,
    getList() {
      this.listLoading = true
      fetchList(this.listQuery).then(response => {
        console.log(response)
        this.list = response.data.list
        this.total = response.data.total
        this.listLoading = false
        // Just to simulate the time of the request
        // setTimeout(() => {
        //   this.listLoading = false
        // }, 1.5 * 1000)
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    // 修改用户状态
    handleModifyStatus(row, status) {
      userSet({ 'id': row.id }).then(() => {
        this.getList()
        this.$notify({
          title: 'Success',
          message: '设置用户状态成功',
          type: 'success',
          duration: 2000
        })
      })
    },
    // 表单重置
    resetForm() {
      this.userForm = {
        username: '',
        nickName: '',
        password: '',
        phone: '',
        email: '',
        sex: ''
      }
    },
    // 显示新增用户表单
    handleCreate() {
      this.resetForm()
      // 获取角色列表
      selectOptions().then(response => {
        console.log(response)
        this.options = response.data
      })
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['userForm'].clearValidate()
      })
    },
    // 保存用户
    createData() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          userSave(this.userForm).then(() => {
            this.getList()
            // this.list.unshift(this.userForm)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '保存用户成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // 显示修改用户表单
    handleUpdate(row) {
      // 获取角色列表
      selectOptions().then(response => {
        console.log(response)
        this.options = response.data
      })
      this.userForm = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['userForm'].clearValidate()
      })
    },
    // 修改用户
    updateData() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          // const tempData = Object.assign({}, this.userForm)
          userSave(this.userForm).then(() => {
            const index = this.list.findIndex(v => v.id === this.userForm.id)
            this.list.splice(index, 1, this.userForm)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '更新用户成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // 用户删除
    handleDelete(row, index) {
      this.$confirm('确定删除用户吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        userDelete({ 'id': row.id }).then(() => {
          this.getList()
          this.$notify({
            title: 'Success',
            message: '删除用户成功',
            type: 'success',
            duration: 2000
          })
        })
      })
      // this.list.splice(index, 1)
    }
  }
}
</script>

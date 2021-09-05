<template>
  <div class="app-container">
    <div class="filter-container">

      <el-input v-model="listQuery.code" placeholder="角色编码" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.name" placeholder="角色名称" style="width: 200px;margin-left: 10px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button v-waves plain class="filter-item" v-if="checkPermission(['system-role-query'])" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button class="filter-item" plain v-if="checkPermission(['system-role-edit'])" style="margin-left: 10px;" type="primary" icon="el-icon-plus" @click="handleCreate">
        新增角色
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
      <el-table-column label="角色编码" prop="code" min-width="100px" align="center" />
      <el-table-column label="角色名称" prop="name" min-width="100px" align="center"/>
      <el-table-column label="角色描述" prop="description" min-width="100px" align="center"/>

      <el-table-column label="操作" align="center" min-width="120" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="success" v-if="checkPermission(['system-role-edit'])" size="mini" @click="showResourceDialog(row)">
            权限赋值
          </el-button>
          <el-button type="primary" v-if="checkPermission(['system-role-edit'])" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button size="mini" v-if="checkPermission(['system-role-del'])" type="danger" @click="handleDelete(row,$index)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.nowPageIndex" :limit.sync="listQuery.pageSize" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="35%" >

      <el-form :model="roleForm" :rules="rules" ref="roleForm" label-width="80px" class="demo-ruleForm">
        <el-form-item label="角色编码" prop="code">
          <el-input v-model="roleForm.code"></el-input>
        </el-form-item>
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="roleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="角色描述" prop="description">
          <el-input v-model="roleForm.description"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="resetForm()">重置</el-button>
          <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          保存角色
          </el-button>
        </el-form-item>
      </el-form>

    </el-dialog>

    <el-dialog title="权限赋值" :visible.sync="dialogFormVisible2" width="25%" >

      <el-form :model="roleForm" :rules="rules" ref="roleForm" label-width="80px" class="demo-ruleForm">
        <el-form-item label="" prop="">
          <el-tree
            ref="resourceTree"
            :data="data"
            show-checkbox
            node-key="id"
            :default-checked-keys="defaultCheckedKeysResource"
            :props="defaultProps">
          </el-tree>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveRoleResource()" >
            保存
          </el-button>
        </el-form-item>
      </el-form>

    </el-dialog>

  </div>
</template>

<script>
import { fetchList, roleSave, roleDelete, getRoleResource, saveRoleResource } from '@/api/role'
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination'
import { treeAssignment } from '@/api/resource' // secondary package based on el-pagination
import checkPermission from '@/utils/permission' // 权限判断函数

export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },

  data() {
    return {
      // 权限资源树形列表数据
      data: [],
      // 已选中资源
      defaultCheckedKeysResource: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      roleForm: {
        id: '',
        code: '',
        name: '',
        description: ''
      },
      rules: {
        code: [
          { required: true, message: '请输入角色编码', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 50 个字符', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 50 个字符', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入角色描述', trigger: 'change' },
          { min: 3, max: 30, message: '长度在 3 到 255 个字符', trigger: 'blur' }
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
      dialogFormVisible2: false,
      dialogStatus: '',
      textMap: {
        update: '修改角色',
        create: '新增角色'
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
    // 表单重置
    resetForm() {
      this.roleForm = {
        code: '',
        name: '',
        description: ''
      }
    },
    // 显示新增角色表单
    handleCreate() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['roleForm'].clearValidate()
      })
    },
    // 保存角色
    createData() {
      this.$refs['roleForm'].validate((valid) => {
        if (valid) {
          roleSave(this.roleForm).then(() => {
            this.getList()
            // this.list.unshift(this.roleForm)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '保存角色成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // 显示修改角色表单
    handleUpdate(row) {
      this.roleForm = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['roleForm'].clearValidate()
      })
    },
    // 修改角色
    updateData() {
      this.$refs['roleForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.roleForm)
          tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          roleSave(tempData).then(() => {
            const index = this.list.findIndex(v => v.id === this.roleForm.id)
            this.list.splice(index, 1, this.roleForm)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '更新角色成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // 角色删除
    handleDelete(row, index) {
      this.$confirm('确定删除角色吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        roleDelete({ 'id': row.id }).then(() => {
          this.getList()
          this.$notify({
            title: 'Success',
            message: '删除角色成功',
            type: 'success',
            duration: 2000
          })
        })
      })
      // this.list.splice(index, 1)
    },
    // 显示权限赋值表单
    showResourceDialog(row) {
      this.roleForm = Object.assign({}, row) // copy obj
      this.dialogFormVisible2 = true
      // 获取权限列表列表
      treeAssignment().then(response => {
        this.data = response.data
      })
      // 获取已关联关系
      getRoleResource({ 'roleId': this.roleForm.id }).then(response => {
        this.defaultCheckedKeysResource = response.data.resourceIds
      })
      this.$nextTick(() => {
        this.$refs['roleForm'].clearValidate()
      })
    },
    // 保存角色权限支援对应关系
    saveRoleResource() {
      // 全选中节点
      const checkedKeys = this.$refs.resourceTree.getCheckedKeys()
      // 半选中节点
      // const halfCheckedKeys = this.$refs.resourceTree.getHalfCheckedKeys()
      // 所有选中节点
      // const keys = checkedKeys.concat(halfCheckedKeys)
      saveRoleResource({
        'roleId': this.roleForm.id,
        'resourceIds': checkedKeys.join(',')
      }).then(() => {
        this.getList()
        this.defaultCheckedKeysResource = []
        this.dialogFormVisible2 = false
        this.$notify({
          title: 'Success',
          message: '资源权限赋值成功成功',
          type: 'success',
          duration: 2000
        })
      })
    }
  }
}
</script>

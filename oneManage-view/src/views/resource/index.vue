<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button v-waves plain v-if="checkPermission(['system-resource-query'])" class="filter-item"  style="margin-left: 10px;" type="primary" icon="el-icon-refresh" @click="handleFilter">
        刷新
      </el-button>
      <el-button class="filter-item" plain v-if="checkPermission(['system-resource-edit'])" style="margin-left: 10px;" type="primary  " icon="el-icon-plus" @click="handleCreate">
        新增资源
      </el-button>
    </div>

    <el-table
      :data="tableData"
      style="width: 100%;margin-bottom: 20px;"
      row-key="id"
      border
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column
        prop="title"
        label="资源名称"
        width="180">
      </el-table-column>
      <el-table-column
        prop="path"
        align="center"
        label="路由地址">
      </el-table-column>
      <el-table-column
        prop="url"
        align="center"
        label="后台请求路径"
        :show-overflow-tooltip='true'>
      </el-table-column>
      <el-table-column
        prop="component"
        align="center"
        label="前端组件路径">
      </el-table-column>
      <el-table-column
        prop="code"
        label="权限编码"
        align="center"
        width="180">
      </el-table-column>
      <el-table-column
        prop="level"
        label="资源层级"
        align="center"
        width="80">
      </el-table-column>
      <el-table-column
        prop="sortNo"
        label="排序"
        align="center"
        width="80">
      </el-table-column>
      <el-table-column
        prop="icon"
        label="图标"
        sortable
        align="center"
        width="160">
      </el-table-column>
      <el-table-column
        prop="type"
        label="资源类型"
        align="center"
        width="120">
        <template slot-scope="scope">
          <span v-if="scope.row.type == 'menu'">目录</span>
          <span v-else>按钮</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" min-width="140" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" v-if="checkPermission(['system-resource-edit'])" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button type="primary" v-if="checkPermission(['system-resource-edit'])" size="mini" @click="handleCreateNext(row)">
            新增下一级
          </el-button>
          <el-button size="mini" v-if="checkPermission(['system-resource-del'])" type="danger" @click="handleDelete(row,$index)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="45%" >

      <el-form :model="resourceForm" :rules="rules" ref="resourceForm" label-width="100px" class="demo-ruleForm">

        <el-form-item label="上级菜单" prop="parentId" >
          <el-select
            v-model="resourceForm.parentId"
            clearable
            placeholder="请选择"
            @clear="handleClear"
            ref="selectUpResId"
          >
            <el-option hidden key="parentId" :value="resourceForm.parentId" :label="resourceForm.parentTitle">
            </el-option>
            <el-tree
              :data="data"
              :props="defaultProps"
              :expand-on-click-node="false"
              :check-on-click-node="true"
              @node-click="handleNodeClick"
            >
            </el-tree>
          </el-select>
        </el-form-item>
        <el-form-item label="资源名称" prop="title">
          <el-input v-model="resourceForm.title"></el-input>
        </el-form-item>
        <el-form-item label="权限编码" prop="code">
          <el-input v-model="resourceForm.code"></el-input>
        </el-form-item>
        <el-form-item label="路由地址" prop="path">
          <el-input v-model="resourceForm.path"></el-input>
        </el-form-item>
        <el-form-item label="后台请求路径" prop="url">
          <el-input v-model="resourceForm.url"></el-input>
        </el-form-item>
        <el-form-item label="前端组件路径" prop="component">
          <el-input v-model="resourceForm.component"></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="sortNo">
          <el-input v-model="resourceForm.sortNo"></el-input>
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-input v-model="resourceForm.icon"></el-input>
        </el-form-item>
        <el-form-item label="资源类型" prop="type">
          <el-select v-model="resourceForm.type" placeholder="请选择资源类型">
            <el-option label="菜单" value="menu"></el-option>
            <el-option label="按钮" value="button"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="resetForm()">重置</el-button>
          <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          保存资源
          </el-button>
        </el-form-item>
      </el-form>

    </el-dialog>

  </div>
</template>

<script>

import { treeList, treeSelect, resourceSave, resourceDelete } from '@/api/resource'
import waves from '@/directive/waves'
import checkPermission from '@/utils/permission' // 权限判断函数

export default {

  name: 'ComplexTable',
  directives: { waves },

  data() {
    // 验证是否整数,非必填
    var checkInt = (rule, value, callback) => {
      if (!value) {
        callback()
      }
      setTimeout(() => {
        if (!Number(value)) {
          callback(new Error('请输入正整数'))
        } else {
          const re = /^[0-9]*[1-9][0-9]*$/
          const rsCheck = re.test(value)
          if (!rsCheck) {
            callback(new Error('请输入正整数'))
          } else {
            callback()
          }
        }
      }, 1000)
    }
    return {
      data: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      saveForm: {
        upResId: ''
      },
      upResName: '',
      tableData: [],
      resourceForm: {
        id: '',
        parentId: '',
        parentTitle: '',
        title: '',
        code: '',
        path: '',
        url: '',
        component: '',
        sortNo: '',
        icon: '',
        type: ''
      },
      rules: {
        title: [
          { required: true, message: '请输入资源名称', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 50 个字符', trigger: 'blur' }
        ],
        code: [
          { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
        ],
        path: [
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ],
        component: [
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ],
        sortNo: [
          { required: true, message: '请输入排序', trigger: 'blur' },
          { validator: checkInt, trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择资源类型', trigger: 'change' }
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
        update: '修改资源',
        create: '新增资源'
      },
      options: []
    }
  },
  created() {
    this.getList()
  },
  methods: {
    checkPermission,
    // 节点点击事件
    handleNodeClick(data) {
      // 这里主要配置树形组件点击节点后，设置选择器的值；自己配置的数据，仅供参考
      this.resourceForm.parentTitle = data.name
      this.resourceForm.parentId = data.resId
      // 选择器执行完成后，使其失去焦点隐藏下拉框的效果
      this.$refs.selectUpResId.blur()
    },
    // 选择器配置可以清空选项，用户点击清空按钮时触发
    handleClear() {
      // 将选择器的值置空
      this.resourceForm.parentTitle = ''
      this.resourceForm.parentId = ''
    },
    // 资源列表初始化
    getList() {
      this.listLoading = true
      treeList(this.listQuery).then(response => {
        console.log(response)
        this.tableData = response.data
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
      this.resourceForm = {
        id: '',
        parentId: '',
        parentTitle: '',
        title: '',
        code: '',
        url: '',
        sortNo: '',
        icon: '',
        type: ''
      }
    },
    // 显示新增资源表单
    handleCreate() {
      this.resetForm()
      // 获取角色列表
      treeSelect().then(response => {
        this.data = response.data
      })
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['resourceForm'].clearValidate()
      })
    },
    // 保存资源
    createData() {
      this.$refs['resourceForm'].validate((valid) => {
        if (valid) {
          resourceSave(this.resourceForm).then(() => {
            this.getList()
            // this.list.unshift(this.resourceForm)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '保存资源成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // 显示修改资源表单
    handleUpdate(row) {
      // 获取角色列表
      treeSelect().then(response => {
        this.data = response.data
        this.resourceForm.parentTitle = row.parentTitle
        this.resourceForm.parentId = row.parentId
      })

      this.resourceForm = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['resourceForm'].clearValidate()
      })
    },
    // 修改资源
    updateData() {
      this.$refs['resourceForm'].validate((valid) => {
        if (valid) {
          resourceSave(this.resourceForm).then(() => {
            this.getList()
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '更新资源成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // 显示新增资源表单
    handleCreateNext(row) {
      this.resetForm()
      // 获取角色列表
      treeSelect().then(response => {
        this.data = response.data
        this.resourceForm.parentTitle = row.title
        this.resourceForm.parentId = row.id
        this.resourceForm.sortNo = row.sortNo + 5
      })
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['resourceForm'].clearValidate()
      })
    },
    // 资源删除
    handleDelete(row, index) {
      this.$confirm('确定删除资源吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        resourceDelete({ 'id': row.id }).then(() => {
          this.getList()
          this.$notify({
            title: 'Success',
            message: '删除资源成功',
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

<template>
  <div class="app-container">
    <div class="filter-container">

      <el-input v-model="listQuery.tableName" placeholder="表名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-input v-model="listQuery.tableDescription" placeholder="表描述" style="width: 200px;margin-left: 10px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-input v-model="listQuery.author" placeholder="作者" style="width: 200px;margin-left: 10px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-button v-waves plain class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button plain class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-refresh" @click="handleSync">
        同步表
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
      <el-table-column label="表名称" prop="tableName" min-width="100px" align="center"/>
      <el-table-column label="表描述" prop="tableDescription" min-width="140px" align="center"/>
      <el-table-column label="作者" prop="author" min-width="70px" align="center"/>
      <el-table-column label="类名" prop="className" min-width="70px" align="center"/>
      <el-table-column label="模块名" prop="moduleName" min-width="70px" align="center"/>
<!--      <el-table-column label="包路径" prop="packagePath" min-width="100px" align="center"/>-->
      <el-table-column label="功能名" prop="functionName" min-width="90px" align="center"/>
      <el-table-column label="生成路径" prop="path" min-width="90px" align="center"/>

      <el-table-column label="操作" align="center" min-width="220" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button type="primary" size="mini" @click="handleOpen(row)">
            字段配置
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(row,$index)">
            删除
          </el-button>
          <el-button type="success" size="mini" @click="sync(row)">
            表同步
          </el-button>
          <el-button type="primary" size="mini" @click="generate(row)">
            代码生成
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.nowPageIndex" :limit.sync="listQuery.pageSize" @pagination="getList"/>

    <el-drawer
      title="编辑"
      :visible.sync="drawer"
      :direction="direction"
      :size="size"
      :before-close="handleClose">
      <div style="margin-left: 30px;margin-right: 30px;">
        <template>
          <el-form  :model="fieldForm" ref="fieldForm" label-width="80px" class="demo-ruleForm">
            <el-table :data="fieldList" style="width: 100%" max-height="720">
              <el-table-column
                prop="fieldName"
                label="字段名"
                width="180">
              </el-table-column>
              <el-table-column
                prop=""
                label="字段描述"
                width="180">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.fieldDescription"></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="dataType"
                label="数据类型"
                width="180">
              </el-table-column>
              <el-table-column
                prop=""
                label="字段类型"
                width="180">
                <template slot-scope="scope">
                    <el-select v-model="scope.row.fieldType" placeholder="请选择">
                      <el-option
                        v-for="item in fieldTypeOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                </template>
              </el-table-column>
              <el-table-column
                prop=""
                label="字段属性"
                width="180">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.fieldProperty"></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop=""
                label="编辑"
                width="50">
                <template slot-scope="scope">
                  <el-checkbox v-model="scope.row.ifEdit"/>
                </template>
              </el-table-column>
              <el-table-column
                prop=""
                label="列表"
                width="50">
                <template slot-scope="scope">
                  <el-checkbox v-model="scope.row.ifList"/>
                </template>
              </el-table-column>
              <el-table-column
                prop=""
                label="必填"
                width="50">
                <template slot-scope="scope">
                  <el-checkbox v-model="scope.row.ifNull"/>
                </template>
              </el-table-column>
              <el-table-column
                prop=""
                label="查询"
                width="50">
                <template slot-scope="scope">
                  <el-checkbox v-model="scope.row.ifQuery"/>
                </template>/
              </el-table-column>
              <el-table-column
                prop=""
                label="查询方式"
                width="180">
                <template slot-scope="scope">
                    <el-select v-model="scope.row.queryType" placeholder="请选择">
                      <el-option
                        v-for="item in queryTypeOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                </template>
              </el-table-column>
              <el-table-column
                prop=""
                label="显示类型"
                width="180">
                <template slot-scope="scope">
                    <el-select v-model="scope.row.viewType" placeholder="请选择">
                      <el-option
                        v-for="item in viewTypeOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                </template>
              </el-table-column>
            </el-table>
            <el-form-item >
              <div style="text-align:center;margin-top: 20px;">
                <el-button type="primary" @click="updateFieldData()">
                  保存字段信息
                </el-button>
              </div>

<!--              <el-button @click="handleClose()">返回</el-button>-->
            </el-form-item>
          </el-form>
        </template>

      </div>

    </el-drawer>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="35%">
      <el-form  :model="tableForm" :rules="rules" ref="tableForm" label-width="80px" class="demo-ruleForm">
        <el-form-item label="表名称" prop="tableName">
          <el-input v-model="tableForm.tableName" style="width: 460px"></el-input>
        </el-form-item>
        <el-form-item label="表描述" prop="tableDescription">
          <el-input v-model="tableForm.tableDescription" style="width: 460px"></el-input>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="tableForm.author" style="width: 460px"></el-input>
        </el-form-item>
        <el-form-item label="类名" prop="className">
          <el-input v-model="tableForm.className" style="width: 460px"></el-input>
        </el-form-item>
        <el-form-item label="模块名" prop="moduleName">
          <el-input v-model="tableForm.moduleName" style="width: 460px"></el-input>
        </el-form-item>
        <el-form-item label="功能名" prop="functionName">
          <el-input v-model="tableForm.functionName" style="width: 460px"></el-input>
        </el-form-item>
<!--        <el-form-item label="包路径" prop="packagePath">-->
<!--          <el-input v-model="tableForm.packagePath" style="width: 460px"></el-input>-->
<!--        </el-form-item>-->
        <el-form-item label="生成路径" prop="path">
          <el-input v-model="tableForm.path" style="width: 460px"></el-input>
        </el-form-item>
        <br>
        <el-form-item>
          <el-button @click="resetForm()">重置</el-button>
          <el-button type="primary" @click="updateData()">
            保存表信息
          </el-button>
        </el-form-item>
      </el-form>

    </el-dialog>

    <el-dialog title="表同步" :visible.sync="dialogFormVisible2" width="30%">
      <div style="text-align: center;">
        <template>
          <el-select v-model="tableName" placeholder="同步所有表" style="margin-right: 10px;margin-left: 10px;">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </template>
        <el-button  class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-refresh" @click="syncTable">
          同步表
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { pageList, tableSave, tableDelete, tableSync, fieldDetail, fieldSave, generate, selectOptions} from '@/api/generate'
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import checkPermission from '@/utils/permission' // 权限判断函数

export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },

  data() {
    return {
      // 下拉表选项
      options: [],
      // 下拉选中的表
      tableName: '',
      // 字段类型下拉选项
      fieldTypeOptions: [{
        value: 'Integer',
        label: 'Integer'
      }, {
        value: 'String',
        label: 'String'
      }, {
        value: 'Long',
        label: 'Long'
      }, {
        value: 'Double',
        label: 'Double'
      }, {
        value: 'Date',
        label: 'Date'
      }],
      // 字段类型下拉选项
      queryTypeOptions: [{
        value: 'equal',
        label: '='
      }, {
        value: 'no_equal',
        label: '!='
      }, {
        value: 'more',
        label: '>'
      }, {
        value: 'more_equal',
        label: '>='
      }, {
        value: 'less',
        label: '<'
      }, {
        value: 'less_equal',
        label: '<='
      }, {
        value: 'like',
        label: 'LIKE'
      }],
      // 字段类型下拉选项
      viewTypeOptions: [{
        value: 'text_input',
        label: '文本框'
      }, {
        value: 'text_area',
        label: '文本域'
      }, {
        value: 'down_box',
        label: '下拉框'
      }, {
        value: 'single_box',
        label: '单选框'
      }, {
        value: 'check_box',
        label: '复选框'
      }, {
        value: 'date',
        label: '日期控件'
      }],
      // 标签默认选中
      activeName: 'second',
      drawer: false,
      direction: 'rtl',
      size: '88%',
      tableForm: {
        id: '',
        tableName: '',
        tableDescription: '',
        author: '',
        className: '',
        moduleName: '',
        packagePath: '',
        functionName: '',
        path: ''
      },
      fieldForm: null,
      rules: {
        tableName: [
          { required: true, message: '请输入表名', trigger: 'blur' },
          { min: 1, max: 40, message: '长度在 1 到 40 个字符', trigger: 'blur' }
        ],
        tableDescription: [
          { required: true, message: '请输入表描述', trigger: 'blur' }
        ],
        author: [
          { required: true, message: '请输入作者名', trigger: 'blur' },
          { min: 1, max: 40, message: '长度在 1 到 40 个字符', trigger: 'blur' }
        ],
        className: [
          { required: true, message: '请输入类名', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
        ],
        moduleName: [
          { required: true, message: '请输入模块名', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
        ],
        packagePath: [
          { required: true, message: '请输入包路径', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
        ],
        functionName: [
          { required: true, message: '请输入功能名', trigger: 'blur' },
          { min: 1, max: 40, message: '长度在 1 到 40 个字符', trigger: 'blur' }
        ],
        path: [
          { required: true, message: '请输入文件生成路径', trigger: 'blur' },
          { min: 1, max: 400, message: '长度在 1 到 400 个字符', trigger: 'blur' }
        ]
      },
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        nowPageIndex: 1,
        pageSize: 10,
        tableName: '',
        tableDescription: '',
        author: ''
      },
      dialogFormVisible: false,
      dialogFormVisible2: false,
      dialogStatus: '',
      textMap: {
        update: '修改表信息',
        create: '新增表信息'
      },
      fieldList: []
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
      this.tableForm = {
        kvname: '',
        nickName: '',
        password: '',
        phone: '',
        email: '',
        sex: ''
      }
    },
    // 显示表同步操作界面
    handleSync() {
      // 获取角色列表
      selectOptions().then(response => {
        console.log(response)
        this.options = response.data
      })
      this.dialogFormVisible2 = true
    },
    // 显示修改表信息表单
    handleUpdate(row) {
      this.tableForm = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['tableForm'].clearValidate()
      })
    },
    // 修改表信息
    updateData() {
      this.$refs['tableForm'].validate((valid) => {
        if (valid) {
          // const tempData = Object.assign({}, this.tableForm)
          tableSave(this.tableForm).then(() => {
            const index = this.list.findIndex(v => v.id === this.tableForm.id)
            this.list.splice(index, 1, this.tableForm)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '更新表信息成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // 表信息删除
    handleDelete(row, index) {
      this.$confirm('确定删除表信息吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        tableDelete({ 'id': row.id }).then(() => {
          this.getList()
          this.$notify({
            title: 'Success',
            message: '删除表信息成功',
            type: 'success',
            duration: 2000
          })
        })
      })
    },
    // 同步表数据-所有表
    syncAll() {
      this.$confirm('同步会覆盖之前的数据，确定同步吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.listLoading = true
        tableSync({ 'tableName': '' }).then(() => {
          this.dialogFormVisible2 = false
          this.getList()
          this.listLoading = false
          this.$notify({
            title: 'Success',
            message: '同步表信息成功',
            type: 'success',
            duration: 2000
          })
        })
      })
    },
    // 同步表数据-根据表名
    sync(row) {
      this.$confirm('同步会覆盖之前的数据，确定同步吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.listLoading = true
        tableSync({ 'tableName': row.tableName }).then(() => {
          this.getList()
          this.listLoading = false
          this.$notify({
            title: 'Success',
            message: '同步表信息成功',
            type: 'success',
            duration: 2000
          })
        })
      })
    },
    // 同步表数据-根据表名
    syncTable() {
      this.$confirm('同步会覆盖之前的数据，确定同步吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.listLoading = true
        tableSync({ 'tableName': this.tableName }).then(() => {
          this.dialogFormVisible2 = false
          this.getList()
          this.listLoading = false
          this.$notify({
            title: 'Success',
            message: '同步表信息成功',
            type: 'success',
            duration: 2000
          })
        })
      })
    },
    // 抽屉打开
    handleOpen(row) {
      fieldDetail({ 'tableId': row.id }).then(response => {
        console.log(response)
        this.fieldList = response.data
        console.log(this.fieldList)
      })
      this.drawer = true
    },
    // 抽屉关闭
    handleClose(done) {
      done()
    },
    // 修改字段信息
    updateFieldData() {
      fieldSave({ 'fieldDTOList': this.fieldList }).then(() => {
        this.$notify({
          title: 'Success',
          message: '保存成功',
          type: 'success',
          duration: 2000
        })
        this.handleClose()
      })
    },
    // 表信息删除
    generate(row) {
      this.$confirm('确定生成代码吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        generate({ 'tableId': row.id }).then(() => {
          this.getList()
          this.$notify({
            title: 'Success',
            message: '生成代码成功',
            type: 'success',
            duration: 2000
          })
        })
      })
    }
  }
}
</script>

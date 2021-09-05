<template>
  <div class="app-container">
    <div class="filter-container">

      <el-input v-model="listQuery.jobName" placeholder="任务名称" style="width: 200px;" class="filter-item"@keyup.enter.native="handleFilter"/>
      <el-input v-model="listQuery.jobDescription" placeholder="任务描述" style="width: 200px;margin-left: 10px;" class="filter-item"@keyup.enter.native="handleFilter"/>
      <el-input v-model="listQuery.jobGroupName" placeholder="任务组名" style="width: 200px;margin-left: 10px;" class="filter-item"@keyup.enter.native="handleFilter"/>
      <el-button v-waves plain v-if="checkPermission(['system-kv-query'])" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button plain v-if="checkPermission(['system-kv-edit'])" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-plus" @click="handleCreate">
        新增定时任务
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
      <el-table-column label="任务名称" prop="jobName" min-width="100px" align="center"/>
      <el-table-column label="任务描述" prop="jobDescription" min-width="100px" align="center"/>
      <el-table-column label="任务组名" prop="jobGroupName" min-width="80px" align="center"/>
      <el-table-column label="类名" prop="jobClassName" min-width="120px" align="center"/>
      <el-table-column label="触发类" prop="triggerName" min-width="120px" align="center"/>
      <el-table-column label="触发组" prop="triggerGroupName" min-width="80px" align="center"/>
      <el-table-column label="cron表达式" prop="cronExpression" min-width="100px" align="center"/>
      <el-table-column label="状态" prop="" min-width="60px" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.triggerState == 0"><el-tag type="success">正常</el-tag></span>
          <span v-else><el-tag type="danger">暂停</el-tag></span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="200" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" v-if="checkPermission(['quartz-job-edit'])" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button type="primary" size="mini" @click="run(row)">
            立即执行
          </el-button>
          <el-button v-if="row.triggerState=='1' && checkPermission(['quartz-job-set'])" size="mini" type="success" @click="handleModifyStatus(row)">
            启用
          </el-button>
          <el-button v-if="row.triggerState=='0' && checkPermission(['quartz-job-set'])" size="mini" type="warning" @click="handleModifyStatus(row)">
            暂停
          </el-button>
          <el-button size="mini" v-if="checkPermission(['quartz-job-del'])" type="danger" @click="handleDelete(row,$index)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.nowPageIndex" :limit.sync="listQuery.pageSize" @pagination="getList"/>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="35%">

      <el-form :model="jobForm" :rules="rules" ref="jobForm" label-width="110px" class="demo-ruleForm">
        <el-form-item label="任务名称" prop="jobName">
          <el-input v-model="jobForm.jobName"></el-input>
        </el-form-item>
        <el-form-item label="任务描述" prop="jobDescription">
          <el-input v-model="jobForm.jobDescription"></el-input>
        </el-form-item>
        <el-form-item label="任务组名" prop="jobGroupName">
          <el-input v-model="jobForm.jobGroupName"></el-input>
        </el-form-item>
        <el-form-item label="类名" prop="jobClassName">
          <el-input v-model="jobForm.jobClassName"></el-input>
        </el-form-item>
        <el-form-item label="触发类" prop="triggerName">
          <el-input v-model="jobForm.triggerName"></el-input>
        </el-form-item>
        <el-form-item label="触发组" prop="triggerGroupName">
          <el-input v-model="jobForm.triggerGroupName"></el-input>
        </el-form-item>
        <el-form-item label="cron表达式" prop="cronExpression">
          <el-input v-model="jobForm.cronExpression"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="triggerState">
<!--          <el-input v-model="jobForm.triggerState"></el-input>-->
          <el-switch v-model="jobForm.triggerState" active-value="0" inactive-value="1" active-text ="暂停" inactive-text="禁用"></el-switch>
        </el-form-item>
        <el-form-item>
          <el-button @click="resetForm()">重置</el-button>
          <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
            保存定时任务
          </el-button>
        </el-form-item>
      </el-form>

    </el-dialog>

  </div>
</template>

<script>
import { pageList, jobSave, jobDelete, statusSet, run } from '@/api/quartz'
import { selectOptions } from '@/api/role'
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import checkPermission from '@/utils/permission'

export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },

  data() {
    return {
      jobForm: {
        id: '',
        jobName: '',
        jobDescription: '',
        jobGroupName: '',
        jobClassName: '',
        triggerName: '',
        triggerGroupName: '',
        cronExpression: '',
        triggerState: ''
      },
      rules: {
        jobName: [
          { required: true, message: '请输入任务名称', trigger: 'blur' },
          { min: 1, max: 40, message: '长度在 1 到 40 个字符', trigger: 'blur' }
        ],
        jobGroupName: [
          { required: true, message: '请输入任务组名', trigger: 'blur' },
          { min: 1, max: 40, message: '长度在 1 到 40 个字符', trigger: 'blur' }
        ],
        triggerGroupName: [
          { required: true, message: '请输入触发类组', trigger: 'blur' },
          { min: 1, max: 40, message: '长度在 1 到 40 个字符', trigger: 'blur' }
        ],
        jobClassName: [
          { required: true, message: '请输入类名', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
        ],
        triggerName: [
          { required: true, message: '请输入触发类名', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
        ],
        cronExpression: [
          { required: true, message: '请输入表达式', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
        ]
      },
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        nowPageIndex: 1,
        pageSize: 10,
        jobName: '',
        jobGroupName: '',
        jobDescription: ''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改定时任务',
        create: '新增定时任务'
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
      this.jobForm = {
        kvname: '',
        nickName: '',
        password: '',
        phone: '',
        email: '',
        sex: ''
      }
    },
    // 显示新增定时任务表单
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
        this.$refs['jobForm'].clearValidate()
      })
    },
    // 保存定时任务
    createData() {
      this.$refs['jobForm'].validate((valid) => {
        if (valid) {
          jobSave(this.jobForm).then(() => {
            this.getList()
            // this.list.unshift(this.jobForm)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '保存定时任务成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // 显示修改定时任务表单
    handleUpdate(row) {
      // 获取角色列表
      selectOptions().then(response => {
        console.log(response)
        this.options = response.data
      })
      this.jobForm = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['jobForm'].clearValidate()
      })
    },
    // 修改定时任务
    updateData() {
      this.$refs['jobForm'].validate((valid) => {
        if (valid) {
          // const tempData = Object.assign({}, this.jobForm)
          jobSave(this.jobForm).then(() => {
            const index = this.list.findIndex(v => v.id === this.jobForm.id)
            this.list.splice(index, 1, this.jobForm)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '更新定时任务成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // 定时任务删除
    handleDelete(row, index) {
      this.$confirm('确定删除定时任务吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        jobDelete({ 'id': row.id }).then(() => {
          this.getList()
          this.$notify({
            title: 'Success',
            message: '删除定时任务成功',
            type: 'success',
            duration: 2000
          })
        })
      })
    },
    // 修改定时任务状态
    handleModifyStatus(row) {
      statusSet({ 'id': row.id }).then(() => {
        this.getList()
        this.$notify({
          title: 'Success',
          message: '设置定时任务状态成功',
          type: 'success',
          duration: 2000
        })
      })
    },
    // 定时任务执行一次
    run(row, status) {
      run({ 'id': row.id }).then(() => {
        this.$notify({
          title: 'Success',
          message: '定时任务执行成功',
          type: 'success',
          duration: 2000
        })
      })
    }
  }
}
</script>

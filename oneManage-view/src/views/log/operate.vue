<template>
  <div class="app-container">
    <div class="filter-container">

      <el-input v-model="listQuery.module" placeholder="模块名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.operatePath" placeholder="操作位置" style="width: 200px;margin-left: 10px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button v-waves v-if="checkPermission(['log-operate-query'])" class="filter-item"  style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
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
      <el-table-column label="模块名称" prop="module" min-width="100px" align="center" />
      <el-table-column label="操作位置" prop="operatePath" min-width="160px" align="center"/>
      <el-table-column label="操作类型" prop="type" min-width="80px" align="center">
        <template slot-scope="scope" >
          <span v-if="scope.row.type == 'query'" ><el-tag type="success" effect="plain">查询</el-tag></span>
          <span v-if="scope.row.type == 'delete'" ><el-tag type="danger" effect="plain">删除</el-tag></span>
          <span v-if="scope.row.type == 'set'" ><el-tag effect="plain">设置</el-tag></span>
          <span v-if="scope.row.type == 'save'" ><el-tag type="success" effect="plain">保存</el-tag></span>
          <span v-if="scope.row.type == 'operate'" ><el-tag type="warning" effect="plain">操作</el-tag></span>

<!--          <span v-else><el-tag type="danger" effect="plain">操作</el-tag></span>-->
        </template>

<!--        <template slot-scope="scope" >-->
<!--          <el-tag>{{scope.row.type}}</el-tag>-->
<!--        </template>-->
      </el-table-column>
      <el-table-column label="操作人名称" prop="userName" min-width="100px" align="center"/>
      <el-table-column label="请求ip" prop="ip" min-width="80px" align="center"/>
      <el-table-column label="执行时间(ms)" prop="executeTime" min-width="80px" align="center"/>
      <el-table-column label="操作日期" prop="time" min-width="120px" align="center"/>
      <el-table-column label="备注" prop="remark" min-width="100px" align="center"/>

      <el-table-column label="操作" align="center" min-width="120" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="showDetail(row)">
            详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.nowPageIndex" :limit.sync="listQuery.pageSize" @pagination="getList" />

    <el-dialog title="详情" :visible.sync="dialogFormVisible" width="40%" >

      <el-form :model="logForm" :inline="false"  ref="logForm" label-width="80px" class="demo-ruleForm">

        <el-row>
          <el-form-item label="模块名称" >
<!--          {{logForm.module}}-->
          <el-input v-model="logForm.module"></el-input>
        </el-form-item>
        <el-form-item label="操作位置" >
          <el-input v-model="logForm.operatePath"></el-input>
<!--          {{logForm.operatePath}}-->
        </el-form-item>
        </el-row>
        <el-row>
        <el-form-item label="操作用户" >
          <el-input v-model="logForm.userName"></el-input>
<!--          {{logForm.userName}}-->
        </el-form-item>
        <el-form-item label="操作类型">
          <el-tag>{{logForm.type}}</el-tag>
<!--          <el-input v-model="logForm.type"></el-input>-->
<!--          {{logForm.type}}-->
        </el-form-item>
        </el-row>
        <el-row>
        <el-form-item label="请求ip" >
          <el-input v-model="logForm.ip"></el-input>
        </el-form-item>
        <el-form-item label="执行时长" >
          <el-input v-model="logForm.executeTime"></el-input>
        </el-form-item>
        </el-row>
        <el-row>
        <el-form-item label="操作时间" >
          <el-input v-model="logForm.time"></el-input>
        </el-form-item>
        <el-form-item label="备注" >
          <el-input v-model="logForm.remark"></el-input>
        </el-form-item>
        </el-row>
        <el-row>
        <el-form-item label="请求参数">
<!--          {{logForm.parameter}}-->
          <el-input type="textarea" v-model="logForm.parameter"></el-input>
        </el-form-item>
        </el-row>
        <el-row>
        <el-form-item label="返回参数">
<!--          {{logForm.result}}-->
          <el-input type="textarea" v-model="logForm.result"></el-input>
        </el-form-item>
        </el-row>
      </el-form>

    </el-dialog>

  </div>
</template>

<script>
import { operatePageList } from '@/api/log'
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import checkPermission from '@/utils/permission' // 权限判断函数

export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },

  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        nowPageIndex: 1,
        pageSize: 10,
        module: '',
        operatePath: ''
      },
      dialogFormVisible: false,
      logForm: {
        id: '',
        module: '',
        operatePath: '',
        userName: '',
        type: '',
        ip: '',
        executeTime: '',
        time: '',
        remark: '',
        parameter: '',
        result: ''
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
      operatePageList(this.listQuery).then(response => {
        console.log(response)
        this.list = response.data.list
        this.total = response.data.total
        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 0.3 * 1000)
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    // 显示修改角色表单
    showDetail(row) {
      this.logForm = Object.assign({}, row) // copy obj
      this.dialogFormVisible = true
      // this.$nextTick(() => {
      //   this.$refs['logForm'].clearValidate()
      // })
    }
  }
}
</script>

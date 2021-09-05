<template>
  <div class="app-container">
    <div class="filter-container">

      <el-input v-model="listQuery.title" placeholder="日志标题" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.userName" placeholder="登录账号名" style="width: 200px;margin-left: 10px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button v-waves v-if="checkPermission(['log-login-query'])" class="filter-item"  style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleFilter">
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
      <el-table-column label="日志标题" prop="title" min-width="100px" align="center" />
      <el-table-column label="登录账号名" prop="userName" min-width="100px" align="center"/>
      <el-table-column label="登录ip" prop="ip" min-width="100px" align="center"/>
      <el-table-column label="操作系统" prop="operateSystem" min-width="100px" align="center"/>
      <el-table-column label="浏览器" prop="browser" min-width="100px" align="center"/>
      <el-table-column label="操作类型" prop="" min-width="60px" align="center">
        <template slot-scope="scope" >
          <span v-if="scope.row.type == 'login'" ><el-tag type="success" effect="plain">登录</el-tag></span>
          <span v-else><el-tag type="danger" effect="plain">登出</el-tag></span>
        </template>
      </el-table-column>
      <el-table-column label="状态" min-width="60px" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.status == 'success'"><el-tag type="success">成功</el-tag></span>
          <span v-else><el-tag type="danger">失败</el-tag></span>
        </template>
      </el-table-column>

      <el-table-column label="操作日期" prop="time" min-width="120px" align="center"/>
      <el-table-column label="备注" prop="remark" min-width="100px" align="center"/>

    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.nowPageIndex" :limit.sync="listQuery.pageSize" @pagination="getList" />

  </div>
</template>

<script>
import { pageList } from '@/api/log'
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
        userName: '',
        title: ''
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
        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 0.3 * 1000)
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    }
  }
}
</script>

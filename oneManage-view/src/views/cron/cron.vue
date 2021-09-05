<template>
  <div id="cron">
    <el-form :model="form" label-width="80px">
      <el-form-item style="margin-top: -10px; margin-bottom:0px;">
       <cron v-if="showCronBox" v-model="form.cronExpression"></cron>
       <span style="color: #E6A23C; font-size: 12px;">corn从左到右（用空格隔开）：秒 分 小时 月份中的日期 月份 星期中的日期 年份</span>
     </el-form-item>
     <el-form-item label="Cron">
       <el-input v-model="form.cronExpression" auto-complete="off">
          <el-button slot="append" v-if="!showCronBox" icon="el-icon-arrow-up" @click="showCronBox = true" title="打开图形配置"></el-button>
          <el-button slot="append" v-else icon="el-icon-arrow-down" @click="showCronBox = false" title="关闭图形配置"></el-button>
       </el-input>
     </el-form-item>
    </el-form>
  </div>
</template>

<script>
import cron from '@/components/Cron/cron'

export default {
  name: 'Cron',
  components: {
    cron
  },
  data () {
    return {
      showCronBox: false,
      form: {
        cronExpression: ''
      }
    }
  }
}
</script>

<style>
#cron {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  width: 80%;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

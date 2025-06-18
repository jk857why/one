<template>
  <div class="page-container">
    <!-- 查询条件表单区域 -->
    <el-form :inline="true" label-width="120px" class="filter-form">
      <el-form-item label="载具号">
        <el-input  placeholder="请输入载具号" clearable />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="fetchCarriers">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格区域 -->
    <div class="table-wrapper">
      <el-table
        :data="carriers"
        border
        stripe
        height="750px"
        style="width: 100%;"
        empty-text="暂无数据"
      >
        <el-table-column prop="carrierID" label="载具号" min-width="100" />
        <el-table-column prop="carrierType" label="载具类型" min-width="100" />
        <el-table-column prop="carrierDetailType" label="载具详细类型" min-width="150" />
        <el-table-column prop="durableSpecID" label="耐用品规格号" min-width="150" />
        <el-table-column prop="carrierStatus" label="载具状态" min-width="100" />
        <el-table-column prop="eqpID" label="设备号" min-width="100" />
        <el-table-column prop="portID" label="端口号" min-width="100" />
        <el-table-column prop="locationID" label="位置号" min-width="100" />
      </el-table>
    </div>

    <!-- 底部操作栏 -->
    <el-row class="footer-row" :gutter="10">
      <el-col :span="20">
        <el-form-item label="备注">
          <el-input v-model="remark" placeholder="请输入备注..." clearable />
        </el-form-item>
      </el-col>
      <el-col :span="4">
        <el-row justify="end" :gutter="10">
          <el-col :span="12">
            <el-button type="primary" plain style="width: 100%;">确定</el-button>
          </el-col>
          <el-col :span="12">
            <el-button type="danger" plain style="width: 100%;">退出</el-button>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>
<script setup>
import { ref } from 'vue'

// 表格数据
const carriers = ref([])

// 查询时间范围
const dateRange = ref([])

// 备注输入框
const remark = ref('')

// 模拟查询方法（后续可对接 Java 后端接口）
const fetchCarriers = () => {
  console.log('载具号:', filter.value.carrierID)
  console.log('时间范围:', filter.value.dateRange)

  // TODO: 通过 filter.value 传参调用后端接口
  // axios.get('/api/releaseCarriers', { params: filter.value }).then(...)
}
</script>

<style scoped>
.page-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  padding: 20px;
  box-sizing: border-box;
}

.filter-form {
  margin-bottom: 10px;
}

.footer-row {
  margin-top: 15px;
}
</style>



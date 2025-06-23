<template>
  <div class="page-container">
    <!-- 查询条件表单区域 -->
    <el-form :inline="true" label-width="120px" class="filter-form">
      <el-form-item label="载具号">
        <el-input v-model="carrierID" placeholder="请输入载具号" clearable />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSearch">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格区域 -->
    <div class="table-wrapper">
      <el-table
        :data="tableData"
        border
        stripe
        height="750px"
        style="width: 100%"
        empty-text="暂无数据"
        :highlight-current-row="true"
        :current-row="selectedRow"
        @row-click="onRowClick"
        :row-key="rowKey"
      >
      <el-table-column prop="carrierID" label="载具编号" />
      <el-table-column prop="carrierType" label="载具类型" />
      <el-table-column prop="carrierDetailType" label="载具细分类型" />
      <el-table-column prop="durableSpecID" label="耐用品规格号" />
      <el-table-column prop="carrierStatus" label="载具状态" />
      <el-table-column prop="cleaningStatus" label="清洁状态" />
      <el-table-column prop="lockStatus" label="锁定状态" />
      <el-table-column prop="capacityStatus" label="容量状态" />
      <el-table-column prop="batchNumber" label="批次号" />
      <el-table-column prop="batchQuantity" label="批次数量" />
      <el-table-column prop="eqp_id" label="设备号" />
      <el-table-column prop="port_id" label="端口号" />
      <el-table-column prop="locationID" label="位置号" />
      <el-table-column prop="edit_time" label="编辑时间" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column prop="maxCleaningCount" label="最大清洗次数" />
      <el-table-column prop="cleaningCount" label="当前清洗次数" />
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
            <el-button type="primary" plain style="width: 100%;" :disabled="!selectedRow" @click="onConfirm">
              确定
            </el-button>
          </el-col>
          <el-col :span="12">
            <el-button type="danger" plain style="width: 100%;" @click="onCancel">
              退出
            </el-button>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getAllCarriers, searchCarriers, releaseCarrier } from '@/api/carrier'
const carrierID = ref('')
const dateRange = ref([])
const tableData = ref([])
const selectedRow = ref(null)
const remark = ref('')

// 查询载具数据
const onSearch = async () => {
  try {
    const trimmedID = carrierID.value.trim()
    const hasDateRange = dateRange.value.length === 2

    let res
    if (!trimmedID && !hasDateRange) {
      res = await getAllCarriers()
    } else {
      const params = {}
      if (trimmedID) params.carrierID = trimmedID
      if (hasDateRange) {
        params.start = dateRange.value[0].toISOString()
        params.end = dateRange.value[1].toISOString()
      }
      res = await searchCarriers(params)
    }

    console.log('返回数据：', res)
    tableData.value = Array.isArray(res) ? res : []
    selectedRow.value = null
  } catch (error) {
    ElMessage.error('查询失败')
  }
}




// 点击行
const onRowClick = (row) => {
  selectedRow.value = row
}

// 释放载具
const onConfirm = async () => {
  if (!selectedRow.value) {
    ElMessage.warning('请先选择一条载具记录')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要释放载具【${selectedRow.value.carrierID}】吗？`,
      '确认释放',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
    )

    await releaseCarrier(selectedRow.value.carrierID, 'Idle')  // 只传 carrierID 和状态
    ElMessage.success('载具释放成功')
    onSearch()  // 刷新表格
  } catch (error) {
    if (error !== 'cancel') {
      console.error('释放失败:', error)
      ElMessage.error('释放失败')
    }
  }
}

// 清空选中项
const onCancel = () => {
  selectedRow.value = null
}

// 唯一标识行
const rowKey = (row) => row.carrierID


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

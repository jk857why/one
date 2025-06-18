<template>
  <div>
    <el-form :inline="true" label-width="120px">
      <el-form-item label="载具细分类型">
        <el-select v-model="search.carrierDetailType" placeholder="请选择">
          <el-option v-for="item in detailTypes" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item label="清洁状态">
        <el-select v-model="search.cleaningStatus" placeholder="请选择">
          <el-option v-for="item in cleaningStatuses" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item label="耐用品规格号">
        <el-select v-model="search.durableSpecID" placeholder="请选择">
          <el-option v-for="item in durableSpecIDs" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item label="载具状态">
        <el-select v-model="search.carrierStatus" placeholder="请选择">
          <el-option v-for="item in carrierStatuses" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item label="容量状态">
        <el-select v-model="search.capacityStatus" placeholder="请选择">
          <el-option v-for="item in capacityStatuses" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="loadTableData">查询</el-button>
      </el-form-item>
    </el-form>
<el-table :data="tableData" border style="width: 100%; margin-top: 20px">
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
  <el-table-column prop="create_time" label="创建时间" />
  <el-table-column prop="maxCleaningCount" label="最大清洗次数" />
  <el-table-column prop="cleaningCount" label="当前清洗次数" />
</el-table>

    <div style="margin-top: 20px; display: flex; flex-wrap: wrap; gap: 10px;">
      <el-button type="primary" @click="onCreate">新建</el-button>
      <el-button @click="onCancelCreate">取消新建</el-button>
      <el-button type="danger" @click="onScrap">报废</el-button>
      <el-button @click="onCancelScrap">取消报废</el-button>
      <el-button type="success" @click="onRelease">释放载具</el-button>
      <el-button @click="onCancelRelease">取消释放</el-button>
      <el-button type="warning" @click="onLock">锁定</el-button>
      <el-button @click="onUnlock">解锁</el-button>
      <el-button type="info" @click="onClean">清洗</el-button>
      <el-button @click="onExit">退出</el-button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

import { 
  getDistinctValuesByField, 
  findCarriersByConditions, 
  updateLockStatus, 
  cleanCarrier,
  updateCarrierStatus
} from '@/api/carrier'

const router = useRouter()

const onCreate = () => {
  router.push('/durable')
}

const search = ref({
  carrierDetailType: null,   // 这里用 carrierDetailType，和接口字段保持一致
  cleaningStatus: null,
  durableSpecID: null,
  carrierStatus: null,
  capacityStatus: null
})

const detailTypes = ref([])
const cleaningStatuses = ref([])
const durableSpecIDs = ref([])
const carrierStatuses = ref([])
const capacityStatuses = ref([])

const tableData = ref([])

// 页面加载时调用，初始化下拉框数据
const loadSelects = async () => {
  const res1 = await getDistinctValuesByField('carrierDetailType')
  console.log('carrierDetailType res:', res1)

  const res2 = await getDistinctValuesByField('cleaningStatus')
  console.log('cleaningStatus res:', res2)

  const res3 = await getDistinctValuesByField('durableSpecID')
  console.log('durableSpecID res:', res3)

  const res4 = await getDistinctValuesByField('carrierStatus')
  console.log('carrierStatus res:', res4)

  const res5 = await getDistinctValuesByField('capacityStatus')
  console.log('capacityStatus res:', res5)

  // 根据实际数据结构再处理赋值
}
 

// 点击查询，调用后端接口请求数据
const loadTableData = async () => {
  const params = {
    carrierDetailType: search.value.carrierDetailType,
    cleaningStatus: search.value.cleaningStatus === 'All' ? null : search.value.cleaningStatus,
    durableSpecID: search.value.durableSpecID === 'All' ? null : search.value.durableSpecID,
    carrierStatus: search.value.carrierStatus,
    capacityStatus: search.value.capacityStatus === 'All' ? null : search.value.capacityStatus,
  }

  const res = await findCarriersByConditions(params)
  tableData.value = res.data
}

onMounted(() => {
  loadSelects()
})
</script>


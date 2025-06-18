<template>
  <div>
    <el-form :inline="true" label-width="120px">
      <el-form-item label="载具细分类型">
        <el-select v-model="search.detailType" placeholder="请选择">
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

const router = useRouter()

const onCreate = () => {
  router.push('/durable')
}

const search = ref({
  detailType: null,
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
  detailTypes.value = await fetchDistinct('CarrierDetailType').then(res => res.data)
  cleaningStatuses.value = await fetchDistinct('CleaningStatus').then(res => res.data)
  
  // durableSpecIDs 需要加 'All' 选项，WinForms 里有
  durableSpecIDs.value = ['All', ...await fetchDistinct('DurableSpecID').then(res => res.data)]

  carrierStatuses.value = await fetchDistinct('CarrierStatus').then(res => res.data)
  
  capacityStatuses.value = ['All', ...await fetchDistinct('CapacityStatus').then(res => res.data)]
}

// 点击查询，调用后端接口请求数据
const loadTableData = async () => {
  // 把 'All' 转换为 null 传给后端
  const params = {
    detailType: search.value.detailType,
    cleaningStatus: search.value.cleaningStatus === 'All' ? null : search.value.cleaningStatus,
    durableSpecID: search.value.durableSpecID === 'All' ? null : search.value.durableSpecID,
    carrierStatus: search.value.carrierStatus,
    capacityStatus: search.value.capacityStatus === 'All' ? null : search.value.capacityStatus,
  }

  const res = await searchCarriers(params)
  tableData.value = res.data
}

onMounted(() => {
  loadSelects()
})
</script>

<template>
  <div class="carrier-view-container">
    <!-- 查询区域 -->
    <el-form :inline="true" class="filter-bar">
      <el-form-item label="载具细分类型">
        <el-select v-model="search.carrierDetailType" placeholder="请选择" class="select-item">
          <el-option v-for="item in detailTypes" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item label="清洁状态">
        <el-select v-model="search.cleaningStatus" placeholder="请选择" class="select-item">
          <el-option v-for="item in cleaningStatuses" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item label="耐用品规格号">
        <el-select v-model="search.durableSpecID" placeholder="请选择" class="select-item">
          <el-option v-for="item in durableSpecIDs" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item label="载具状态">
        <el-select v-model="search.carrierStatus" placeholder="请选择" class="select-item">
          <el-option v-for="item in carrierStatuses" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item label="容量状态">
        <el-select v-model="search.capacityStatus" placeholder="请选择" class="select-item">
          <el-option v-for="item in capacityStatuses" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="loadTableData">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格区域 -->
    <div class="table-wrapper">
      <el-table :data="tableData" border height="100%" style="width: 100%" empty-text="暂无数据" @row-click="handleRowClick" :current-row="currentRow" highlight-current-row>
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
        <el-table-column prop="eqpId" label="设备号" />
        <el-table-column prop="portId" label="端口号" />
        <el-table-column prop="locationID" label="位置号" />
        <el-table-column prop="editTime" label="编辑时间" />
        <el-table-column prop="createTime" label="创建时间" />
        <el-table-column prop="maxCleaningCount" label="最大清洗次数" />
        <el-table-column prop="cleaningCount" label="当前清洗次数" />

      </el-table>
    </div>

    <!-- 操作按钮 -->
    <div class="button-bar">
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

const currentRow = ref(null) //  用于高亮当前行
// 当前选中载具 ID
const selectedCarrierID = ref(null)
// 表格点击行事件：记录当前载具ID
const handleRowClick = (row) => {
  currentRow.value = row         // 设置高亮行
  selectedCarrierID.value = row.carrierID // 设置选中的载具ID
}

// 解锁
const onUnlock = async () => {
  if (!selectedCarrierID.value) return console.warn('未选择载具')
  try {
    await updateLockStatus(selectedCarrierID.value, 'Unlocked')
    console.log('解锁成功')
    await loadTableData()
  } catch (err) {
    console.error('解锁失败', err)
  }
}

// 锁定
const onLock = async () => {
  if (!selectedCarrierID.value) return console.warn('未选择载具')
  try {
    await updateLockStatus(selectedCarrierID.value, 'Locked')
    console.log('锁定成功')
    await loadTableData()
  } catch (err) {
    console.error('锁定失败', err)
  }
}

// 释放
const onRelease =()=>{
  router.push('/quit')
}

// 取消释放（假设变更为 Idle）
const onCancelRelease = () => {
  console.log('取消释放操作')
}


// 报废（假设状态为 Scrapped）
const onScrap = async () => {
  if (!selectedCarrierID.value) return console.warn('未选择载具')
  try {
    await updateCarrierStatus(selectedCarrierID.value, 'Scrapped')
    console.log('报废成功')
    await loadTableData()
  } catch (err) {
    console.error('报废失败', err)
  }
}

// 取消报废（假设变更为 Idle）
const onCancelScrap = async () => {
  if (!selectedCarrierID.value) return console.warn('未选择载具')
  try {
    await updateCarrierStatus(selectedCarrierID.value, 'Idle')
    console.log('取消报废成功')
    await loadTableData()
  } catch (err) {
    console.error('取消报废失败', err)
  }
}

// 清洗
const onClean = async () => {
  if (!selectedCarrierID.value) return console.warn('未选择载具')
  try {
    await cleanCarrier(selectedCarrierID.value)
    console.log('清洗成功')
    await loadTableData()
  } catch (err) {
    console.error('清洗失败', err)
  }
}

// 新建跳转
const onCreate = () => {
  router.push('/create')
}

// 取消新建（无API，保留占位）
const onCancelCreate = () => {
  console.log('取消新建操作')
}

// 退出
const onExit = () => {
  console.log('退出页面')
  router.back()
}
import {
  getDistinctValuesByField,
  findCarriersByConditions,
  updateLockStatus,
  cleanCarrier,
  updateCarrierStatus,
  getAllCarriers
} from '@/api/carrier'

const router = useRouter()



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
  detailTypes.value = ['All', ...res1]

  const res2 = await getDistinctValuesByField('cleaningStatus')
  cleaningStatuses.value = ['All', ...res2]

  const res3 = await getDistinctValuesByField('durableSpecID')
  durableSpecIDs.value = ['All', ...res3]

  const res4 = await getDistinctValuesByField('carrierStatus')
  carrierStatuses.value = ['All', ...res4]

  const res5 = await getDistinctValuesByField('capacityStatus')
  capacityStatuses.value = ['All', ...res5]
}

// 点击查询，调用后端接口请求数据
const cleanParam = (val) => (val === null || val === 'All' ? undefined : val)


const loadTableData = async () => {
  const params = {
    carrierDetailType: cleanParam(search.value.carrierDetailType),
    cleaningStatus: cleanParam(search.value.cleaningStatus),
    durableSpecID: cleanParam(search.value.durableSpecID),
    carrierStatus: cleanParam(search.value.carrierStatus),
    capacityStatus: cleanParam(search.value.capacityStatus),
  }

  try {
    // ✅ ✅ ✅ 【新增】：定义 isAllSelected
    const isAllSelected =
      !params.carrierDetailType &&
      !params.cleaningStatus &&
      !params.durableSpecID &&
      !params.carrierStatus &&
      !params.capacityStatus

    let res // ✅ ✅ ✅ 【新增】：提前声明 res

    if (isAllSelected) {
      // 所有条件为空时，获取全部载具
      res = await getAllCarriers()
    } else {
      res = await findCarriersByConditions(params)
    }

    // ✅ 判断返回格式并赋值
    if (Array.isArray(res)) {
      tableData.value = res
    } else if (res && Array.isArray(res.data)) {
      tableData.value = res.data
    } else {
      console.warn('接口返回数据格式不对:', res)
      tableData.value = []
    }

    console.log('表格数据:', tableData.value)
  } catch (err) {
    console.error('载具查询失败：', err)
    tableData.value = []
  }
  if (tableData.value.length > 0) {
  currentRow.value = tableData.value[0] // 设置高亮
  selectedCarrierID.value = tableData.value[0].carrierID // 设置选中载具 ID
}
console.log('表格数据:', tableData.value)
}


onMounted(() => {
  loadSelects()
  loadTableData()  // 加上这一行，页面加载自动查询一次表格
})

</script>
<style scoped>
.carrier-view-container {
  height: 90vh;
  /* ① 固定页面高度为视口高度 */
  padding: 10px;
  display: flex;
  /* ② flex 垂直布局 */
  flex-direction: column;
  box-sizing: border-box;
}


.filter-bar {
  display: flex;
  /* ① 水平一行排列 */
  gap: 10px;
  /* ② 固定间距 */
  align-items: center;
  flex-wrap: nowrap;
  /* ③ 不换行，撑开一行 */
  height: 50px;
  /* ④ 固定查询栏高度 */
}

.select-item {
  width: 160px;
  /* ⑤ select 固定宽度 */
}

.table-wrapper {
  flex: 1;
  overflow: hidden;
}

.el-table {
  height: 100%;
}

.button-bar {
  margin-top: 10px;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}
</style>

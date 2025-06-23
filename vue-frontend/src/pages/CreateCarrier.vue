<template>
    <div class="container">
        <!-- 查询区域 -->
        <div class="flex gap-4 items-center flex-row">
            <span>类型</span>
            <el-select v-model="selectedType" style="width: 240px; margin-right: 16px;">
                <el-option v-for="item in typeOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>

            <span>耐用品型号</span>
            <el-select v-model="selectedModel" style="width: 240px; margin-right: 16px;">
                <el-option v-for="item in modelOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>

            <el-button type="primary" style="width: 75px;" @click="onQuery">查询</el-button>
        </div>

        <!-- 表格和表单布局 -->
        <el-row :gutter="20">
            <el-col :span="10">
                <!-- 耐用品清单 -->
                <el-table :data="tableData" height="450px" style="width: 95%" @row-click="onSelectDurable">
                    <el-table-column prop="durableSpecID" label="耐用品规格号" />
                    <el-table-column prop="durableName" label="耐用品规格说明" />
                    <el-table-column prop="durableType" label="耐用品类型" />
                    <el-table-column prop="durableDetailType" label="耐用品详细类型" />
                    <el-table-column prop="durableColor" label="耐用品颜色" />
                    <el-table-column prop="durableCapacity" label="耐用品容量" />
                    <el-table-column prop="maxUseCount" label="最大使用次数" />
                    <el-table-column prop="maxUseDays" label="最大使用天数" />
                    <el-table-column prop="maxUseCountAfterClean" label="清洗后最大使用次数" />
                    <el-table-column prop="maxUseDaysAfterClean" label="清洗后最长使用天数" />
                    <el-table-column prop="alarmWhenExceedCount" label="超出使用次数时发出报警" />
                    <el-table-column prop="alarmWhenExceedDays" label="超出使用天数时报警" />
                    <el-table-column prop="alarmWhenExceedCountAfterClean" label="清洗后超出使用次数时报警" />
                    <el-table-column prop="alarmWhenExceedCountAfterInspection" label="检查后超出使用次数时报警" />
                    <el-table-column prop="alarmWhenExceedDaysAfterInspection" label="检查后超出使用时长报警" />
                    <el-table-column prop="factoryId" label="工厂" />
                    <el-table-column prop="editStatus" label="编辑状态" />
                    <el-table-column prop="editTime" label="编辑发生时间" />
                    <el-table-column prop="editUser" label="编辑用户" />
                    <el-table-column prop="event" label="事件" />
                    <el-table-column prop="eventCode" label="事件码" />
                    <el-table-column prop="createTime" label="创建时间" />
                </el-table>

                <!-- 创建信息表单 -->
                <div class="create">
                    <el-row :gutter="3">
                        <el-col :span="10">
                            <el-form :inline="true" :model="formInline" class="demo-form-inline">
                                <el-form-item label="耐用品规格号">
                                    <el-input v-model="formInline.durableSpecID" clearable />
                                </el-form-item>
                                <el-form-item label="载具类型">
                                    <el-input v-model="formInline.carrierType" clearable />
                                </el-form-item>
                                <el-form-item label="最大使用次数">
                                    <el-input v-model="formInline.maxUseCount" clearable />
                                </el-form-item>
                                <el-form-item label="最大清洗次数">
                                    <el-input v-model="formInline.maxCleaningCount" clearable />
                                </el-form-item>
                                <el-form-item label="容量">
                                    <el-input v-model="formInline.capacity" clearable />
                                </el-form-item>
                            </el-form>
                        </el-col>

                        <el-col :span="10">
                            <el-form-item label="位置号">
                                <el-select v-model="formInline.locationID" clearable>
                                    <el-option v-for="item in approverOptions" :key="item" :label="item"
                                        :value="item" />
                                </el-select>
                            </el-form-item>

                            <el-form-item label="创建数量">
                                <el-checkbox v-model="isCheckedCreateQuantity" style="margin-right: 8px;" />
                                <el-input-number v-model="formInline.createQuantity" :min="0" :max="10000"
                                    style="width: 150px;" />
                            </el-form-item>

                            <el-form-item label="载具号">
                                <el-checkbox v-model="isCheckedCarrierNumber" style="margin-right: 8px;" />
                                <el-input v-model="formInline.carrierNumber" style="width: 150px;" />
                            </el-form-item>

                            <el-row :gutter="10">
                                <el-col :span="11">
                                    <el-button type="primary" plain style="width: 75px;" @click="onClear">清空</el-button>
                                </el-col>
                                <el-col :span="11">
                                    <el-button type="primary" plain style="width: 75px;"
                                        @click="onGenerate">生成</el-button>
                                </el-col>
                            </el-row>
                        </el-col>
                    </el-row>
                </div>
            </el-col>

            <!-- 载具表格展示 -->
            <el-col :span="14">
                <el-table :data="carrierTableData" height="700px" style="width: 95%">
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
            </el-col>
        </el-row>

        <!-- 备注及操作 -->
        <el-row :gutter="3" class="create">
            <el-col :span="20">
                <el-form-item label="备注">
                    <el-input placeholder="备注" clearable />
                </el-form-item>
            </el-col>
            <el-col :span="4">
                <el-row>
                    <el-col :span="11">
                        <el-button type="primary" plain style="width: 75px;">确定</el-button>
                    </el-col>
                    <el-col :span="11">
                        <el-button type="primary" plain style="width: 75px;">退出</el-button>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import {
    getAllDurables,
    getDurablesBySpecID,
    getDistinctDurableSpecIDs
} from '@/api/durable'
import { createCarrier } from '@/api/vehicle' // 载具创建接口
import { getAllCarriers } from '@/api/carrier' // 载具查询接口
import type { DurableGoods } from '@/types/durableGoods'

// 选中的类型和型号
const selectedType = ref('')
const selectedModel = ref('')

// 下拉选项数据
const typeOptions = ref<{ label: string; value: string }[]>([])
const modelOptions = ref<{ label: string; value: string }[]>([])

// 耐用品数据表格
const tableData = ref<DurableGoods[]>([])

// 载具数据表格（初始空）
const carrierTableData = ref<any[]>([])

// 表单绑定数据
const formInline = ref({
    durableSpecID: '',
    carrierType: '',
    maxUseCount: '',
    maxCleaningCount: '',
    capacity: '',
    locationID: '',
    approver: '',
    createQuantity: 0,
    carrierNumber: ''
})

// 审批人选项
const approverOptions = ref<string[]>([])

// 复选框状态
const isCheckedCreateQuantity = ref(false)
const isCheckedCarrierNumber = ref(false)

// 加载“类型”下拉选项
const fetchTypeOptions = async () => {
    try {
        const res = await getDistinctDurableSpecIDs()
        if (res && Array.isArray(res.data)) {
            typeOptions.value = res.data.map((id: string) => ({ label: id, value: id }))
            modelOptions.value = [...typeOptions.value]
        }
    } catch (error) {
        console.error('加载类型失败', error)
    }
}

// 加载耐用品数据
const fetchTableData = async (specId = 'All') => {
    try {
        let res
        if (specId === 'All' || specId === '') {
            res = await getAllDurables()
        } else {
            res = await getDurablesBySpecID(specId)
        }
        if (Array.isArray(res)) {
            tableData.value = res
        } else if (Array.isArray(res.data)) {
            tableData.value = res.data
        } else {
            console.warn('耐用品数据格式不符合预期', res)
            tableData.value = []
        }
    } catch (error) {
        console.error('获取耐用品数据失败', error)
        tableData.value = []
    }
}
// 加载载具数据
const fetchCarrierTableData = async () => {
  try {
    const res = await getAllCarriers()
    console.log('载具列表接口返回:', res)

    if (res && Array.isArray(res)) {
      carrierTableData.value = res
    } else {
      carrierTableData.value = []
      console.warn('载具数据格式不正确，赋值为空数组')
    }
  } catch (error) {
    console.error('获取载具数据失败', error)
    carrierTableData.value = []
  }
}

// 点击查询按钮
const onQuery = async () => {
    await fetchTableData(selectedType.value)
}

// 选中耐用品行赋值表单
const onSelectDurable = (row: DurableGoods) => {
    formInline.value.durableSpecID = row.durableSpecID
    formInline.value.carrierType = row.durableType
    formInline.value.maxUseCount = row.maxUseCount?.toString() ?? ''
    formInline.value.capacity = row.durableCapacity ?? ''
    formInline.value.maxCleaningCount = row.maxUseCountAfterClean?.toString() ?? ''
}

// 清空表单
const onClear = () => {
    formInline.value = {
        durableSpecID: '',
        carrierType: '',
        maxUseCount: '',
        maxCleaningCount: '',
        capacity: '',
        locationID: '',
        approver: '',
        createQuantity: 0,
        carrierNumber: ''
    }
    isCheckedCreateQuantity.value = false
    isCheckedCarrierNumber.value = false
}

const onGenerate = async () => {
  if (!formInline.value.durableSpecID) {
    alert('请选择耐用品规格号');
    return;
  }
  try {
    const payload = {
      durableSpecID: formInline.value.durableSpecID,
      locationID: formInline.value.locationID || '',
    };
    console.log('发送请求数据:', payload);

    const res = await createCarrier(payload);
    console.log('接口返回数据:', res);

    // createCarrier 返回的就是后端的对象，不用 .data
    const carrier = res;

    if (carrier && carrier.carrierID) {
      alert('载具创建成功，ID：' + carrier.carrierID);
      // 重新加载耐用品和载具列表数据
      await fetchTableData(selectedType.value);
      await fetchCarrierTableData();
    } else {
      alert('载具创建成功，但未返回载具ID');
    }
  } catch (error: any) {
    console.error('生成载具失败', error);
    if (error.response) {
      alert('生成失败：' + JSON.stringify(error.response.data));
    } else {
      alert('生成失败，请检查网络或日志');
    }
  }
};


// 组件挂载初始化数据
onMounted(() => {
    fetchTypeOptions()
    fetchTableData()
    fetchApproverOptions()
    // 载具表初始不加载，保持空
})

// 审批人加载示例（接口不存在时可留空）
async function fetchApproverOptions() {
    approverOptions.value = [] // 如果有接口，替换这里
}
</script>

<style scoped>
.container {
    margin: 25px;
}

.create {
    margin-top: 30px;
}

.space_right {
    margin-right: 10px;
}
</style>
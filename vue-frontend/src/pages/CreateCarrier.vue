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
                                    <el-button type="primary" plain style="width: 75px;">清空</el-button>
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
                <el-table :data="tableData" height="700px" style="width: 95%">
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
    getDistinctDurableSpecIDs,
    getDurablesWithCarrier
} from '@/api/durable'
import { createCarrier } from '@/api/vehicle' // 载具创建接口
import type { DurableGoods } from '@/types/durableGoods'

// 选中的类型和型号
const selectedType = ref('')
const selectedModel = ref('')

// 下拉选项数据
const typeOptions = ref<{ label: string; value: string }[]>([])
const modelOptions = ref<{ label: string; value: string }[]>([])

// 表格数据，耐用品清单
const tableData = ref<DurableGoods[]>([])

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

// 是否勾选复选框
const isCheckedCreateQuantity = ref(false)
const isCheckedCarrierNumber = ref(false)

// 加载“类型”下拉选项（假设由后端提供）
const fetchTypeOptions = async () => {
    try {
        const res = await getDistinctDurableSpecIDs()
        // 接口返回的规格ID数组，转成 {label,value} 形式用于下拉选
        if (res && Array.isArray(res.data)) {
            typeOptions.value = res.data.map((id: string) => ({ label: id, value: id }))
            modelOptions.value = [...typeOptions.value] // 简单示例用相同列表
        }
    } catch (error) {
        console.error('加载类型失败', error)
    }
}

// 加载耐用品数据列表，带条件过滤
const fetchTableData = async (specId = 'All') => {
    try {
        let res
        if (specId === 'All' || specId === '') {
            res = await getAllDurables()
        } else {
            res = await getDurablesBySpecID(specId)
        }

        // ✅ 判断 res 本身是数组
        if (Array.isArray(res)) {
            tableData.value = res
        } else if (Array.isArray(res.data)) {
            tableData.value = res.data
        } else {
            console.warn('数据格式不符合预期:', res)
            tableData.value = []
        }
    } catch (error) {
        console.error('获取数据失败:', error)
        tableData.value = []
    }
}

// 加载审批人选项（示例，假设接口存在）
const fetchApproverOptions = async () => {
}

// 点击“查询”按钮触发
const onQuery = async () => {
    await fetchTableData(selectedType.value)
}

// 选中耐用品表格行时，赋值表单相关字段
const onSelectDurable = (row: DurableGoods) => {
    formInline.value.durableSpecID = row.durableSpecID
    formInline.value.carrierType = row.durableType
    formInline.value.maxUseCount = row.maxUseCount?.toString() ?? ''
    formInline.value.capacity = row.durableCapacity ?? ''
    // 这里假设最大清洗次数存在，字段名自行修改
    formInline.value.maxCleaningCount = row.maxUseCountAfterClean?.toString() ?? ''
}

// 点击“生成”按钮，调用接口创建载具
const onGenerate = async () => {
    if (!formInline.value.durableSpecID) {
        alert('请选择耐用品规格号')
        return
    }
    try {
        await createCarrier(formInline.value.durableSpecID, formInline.value.locationID)
        console.log('durableSpecID:', formInline.value.durableSpecID)
        console.log('locationID:', formInline.value.locationID)

        alert('载具创建成功')
        // 生成后刷新数据或其他逻辑
        await fetchTableData(selectedType.value)
    } catch (error) {
        console.error('生成载具失败', error)
        alert('生成失败，请检查日志')
    }
}

// 组件加载时调用
onMounted(() => {
    fetchTypeOptions()
    fetchTableData()
    fetchApproverOptions()
})

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
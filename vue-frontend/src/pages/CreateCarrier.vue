<template>
    <div class="container">
        <div class="flex gap-4 items-center flex-row">
            <span>类型</span>
            <el-select v-model="value" style="width: 240px; margin-right: 16px;">

                <el-option v-for="item in options" :key="item.value" :label="item.label"
                    :value="item.value"></el-option>
            </el-select>
            <span>耐用品型号</span>
            <el-select v-model="value" style="width: 240px; margin-right: 16px;">

                <el-option v-for="item in options" :key="item.value" :label="item.label"
                    :value="item.value"></el-option>
            </el-select>
        </div>
        <el-row :gutter="20">
            <el-col :span="10">
                <!-- 耐用品清单 -->
                <div>
                    <el-table :data="tableData" height="450px" style="width: 95%">
                        <el-table-column prop="date" label="Date" width="180" />
                        <el-table-column prop="name" label="Name" width="180" />
                        <el-table-column prop="address" label="Address" />
                    </el-table>
                </div>
                <!-- 创建信息 -->
                <div class="create">
                    <el-row :gutter="3">
                        <el-col :span="10">
                            <el-form :inline="true" :model="formInline" class="demo-form-inline">
                                <el-form-item label="耐用品规格号">
                                    <el-input placeholder="Approved by" clearable />
                                </el-form-item>
                                <el-form-item label="载具类型">
                                    <el-input placeholder="Approved by" clearable />
                                </el-form-item>
                                <el-form-item label="最大使用次数">
                                    <el-input placeholder="Approved by" clearable />
                                </el-form-item>
                                <el-form-item label="最大清洗次数">
                                    <el-input placeholder="Approved by" clearable />
                                </el-form-item>
                                <el-form-item label="容量">
                                    <el-input placeholder="Approved by" clearable />
                                </el-form-item>
                            </el-form>
                        </el-col>
                        <el-col :span="10">
                            <el-form-item label="Approved by">
                                <el-input placeholder="Approved by" clearable />
                            </el-form-item>
                            <el-form-item label="位置号">
                                <el-select v-model="formInline.approver" placeholder="Approved by" clearable>
                                    <el-option v-for="item in approverOptions" :key="item" :label="item"
                                        :value="item"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="创建数量">
                                <el-checkbox v-model="isChecked" style="margin-right: 8px;" />
                                <el-input-number :min="0" :max="10000" placeholder="Approved by"
                                    style="width: 150px;" />
                            </el-form-item>
                            <el-form-item label="载具号">
                                <el-checkbox v-model="isChecked" style="margin-right: 8px;" />                          
                                <el-input placeholder="Approved by" style="width: 150px;" />
                            </el-form-item>
                            <el-row :gutter="10">
                                <el-col :span="11">
                                    <el-button type="primary" plain style="width: 75px;">清空</el-button>
                                </el-col>
                                <el-col :span="11">
                                    <el-button type="primary" plain style="width: 75px;">生成</el-button>
                                </el-col>
                            </el-row>
                        </el-col>

                    </el-row>
                </div>
            </el-col>
            <el-col :span="14">
                <div>
                    <el-table :data="tableData" height="700px" style="width: 95%">
                        <el-table-column prop="date" label="Date" width="180" />
                        <el-table-column prop="name" label="Name" width="180" />
                        <el-table-column prop="address" label="Address" />
                    </el-table>
                </div>
            </el-col>
        </el-row>
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
import { ref } from 'vue'

const selectedType = ref('')
const selectedModel = ref('')

// 下拉选项数组
const typeOptions = ref<{ label: string; value: string }[]>([])
const modelOptions = ref<{ label: string; value: string }[]>([])

// 加载“类型”选项
const fetchTypeOptions = async () => {
    try {
        // TODO: 替换为真实接口
        const res = await axios.get('/api/types/list')
        // 假设接口返回格式 [{ label: '类型1', value: 'type1' }, ...]
        typeOptions.value = res.data
    } catch (error) {
        console.error('加载类型失败', error)
    }
}

// 加载“耐用品型号”选项
const fetchModelOptions = async () => {
    try {
        // TODO: 替换为真实接口
        const res = await axios.get('/api/models/list')
        modelOptions.value = res.data
    } catch (error) {
        console.error('加载型号失败', error)
    }
}

import axios from 'axios'
import { onMounted } from 'vue'

// 表格数据
const tableData = ref([])

// 模拟接口调用逻辑：从后端获取数据
const fetchTableData = async () => {
    try {
        const response = await axios.get('/api/durable-items/list')

        // 如果返回结构是 { code: 200, data: [...] }
        const raw = response.data

        if (Array.isArray(raw)) {
            tableData.value = raw
        } else if (raw && Array.isArray(raw.data)) {
            tableData.value = raw.data
        } else if (raw && raw.data && Array.isArray(raw.data.list)) {
            tableData.value = raw.data.list
        } else {
            console.warn('数据格式不符合预期:', raw)
            tableData.value = []
        }
    } catch (error) {
        console.error('获取数据失败:', error)
        tableData.value = []
    }
}

// 页面加载时调用
onMounted(() => {
    fetchTableData(),
        fetchApproverOptions(),
        selectedModel,
        selectedType
})
// 表单数据
const formInline = ref({
    approver: ''
})

// 下拉选项数组，初始为空
const approverOptions = ref<string[]>([])

// 页面加载时调用接口加载选项
const fetchApproverOptions = async () => {
    try {
        // TODO: 替换成你的接口地址
        const response = await axios.get('/api/approvers/list')

        // 假设接口返回数据格式是数组，比如 ['Alice', 'Bob', 'Charlie']
        approverOptions.value = response.data
    } catch (error) {
        console.error('加载审批人选项失败', error)
    }
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
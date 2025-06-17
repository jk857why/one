<template>
    <div class="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center">
      <div class="bg-white p-6 rounded shadow w-96">
        <h2 class="text-lg mb-4">{{ editingId ? '编辑学生' : '新增学生' }}</h2>
        <form @submit.prevent="submit">
          <input v-model="form.studentName" placeholder="姓名" class="input" />
          <input v-model="form.studentClass" placeholder="班级" class="input" />
          <div class="mt-4 flex justify-end space-x-2">
            <button type="submit" class="bg-green-500 text-white px-3 py-1 rounded">保存</button>
            <button @click="$emit('close')" type="button" class="bg-gray-300 px-3 py-1 rounded">取消</button>
          </div>
        </form>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted, watch } from 'vue';
  import { addStudent, getStudent, updateStudent } from '@/api/students';
  import { Student } from '@/types/Student';
  
  const props = defineProps<{ editingId: number | null }>();
  const emit = defineEmits(['close']);
  
  const form = ref<Student>({
    studentName: '', studentClass: ''
  });
  
  onMounted(async () => {
    if (props.editingId) {
      const res = await getStudent(props.editingId);
      form.value = res.data;
    }
  });
  
  watch(() => props.editingId, async (newVal) => {
    if (newVal) {
      const res = await getStudent(newVal);
      form.value = res.data;
    } else {
      form.value = { studentName: '', studentClass: '' };
    }
  });
  
  const submit = async () => {
    if (props.editingId) {
      await updateStudent(props.editingId,form.value);
    } else {
      await addStudent(form.value);
    }
    emit('close');
  };
  </script>
  
  <style scoped>
  .input {
    display: block;
    width: 100%;
    margin-bottom: 0.5rem;
    padding: 0.5rem;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  </style>
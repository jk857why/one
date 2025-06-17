<template>
    <div class="p-4">
      <h1 class="text-xl mb-4">学生信息管理</h1>
      <button @click="openAdd" class="bg-blue-500 text-white px-3 py-1 rounded">新增学生</button>
      <table class="w-full mt-4 border">
        <thead>
          <tr class="bg-gray-100">
            <th class="p-2">学号</th>
            <th class="p-2">姓名</th>
            <th class="p-2">班级</th>
            <th class="p-2">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="stu in students" :key="stu.studentId" class="border-t">
            <td class="p-2">{{ stu.studentId }}</td>
            <td class="p-2">{{ stu.studentName }}</td>
            <td class="p-2">{{ stu.studentClass }}</td>
            <td class="p-2 space-x-2">
              <button @click="editStudent(stu.studentId!)" class="text-blue-500">编辑</button>
              <button @click="removeStudent(stu.studentId!)" class="text-red-500">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
  
      <StudentFormModal
        v-if="showModal"
        :editingId="editingId"
        @close="handleClose"
      />
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted } from 'vue';
  import { getStudents, deleteStudent } from '@/api/students';
  import { Student } from '@/types/Student';
  import StudentFormModal from '@/components/StudentFormModal.vue';
  
  const students = ref<Student[]>([]);
  const showModal = ref(false);
  const editingId = ref<number | null>(null);
  
  const loadStudents = async () => {
    const res = await getStudents();
    students.value = res.data;
  };
  
  const openAdd = () => {
    editingId.value = null;
    showModal.value = true;
  };
  
  const editStudent = (id: number) => {
    editingId.value = id;
    showModal.value = true;
  };
  
  const removeStudent = async (id: number) => {
    await deleteStudent(id);
    await loadStudents();
  };
  
  const handleClose = () => {
    showModal.value = false;
    loadStudents();
  };
  
  onMounted(loadStudents);
  </script>
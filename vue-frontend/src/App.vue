<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <div class="app-container">
    <!-- 顶部导航栏 -->
    <header class="header">
      <nav class="nav">
        <el-menu mode="horizontal" router :default-active="$route.path">
          <el-sub-menu index="2">
            <template #title>载具</template>
            <el-menu-item index="/main">载具主界面</el-menu-item>
            <el-menu-item index="/create">创建载具</el-menu-item>
            <el-menu-item index="/quit">释放载具</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </nav>
    </header>
    <main>
      <RouterView />
    </main>
  </div>
</template>

<style scoped></style>
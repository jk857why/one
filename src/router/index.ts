import { createRouter, createWebHistory } from 'vue-router'
import CarrierView from '../views/CarrierView.vue'

const routes = [
  { path: '/', redirect: '/carrier' },
  { path: '/carrier', component: CarrierView }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router

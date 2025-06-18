import { createRouter, createWebHistory } from 'vue-router';
import CreateCarrier from '@/pages/CreateCarrier.vue'
import ReleaseCarrier from '@/pages/ReleaseCarrier.vue'
import CarrierView from '@/pages/CarrierView.vue'

const routes = [
  { path: '/create', component: CreateCarrier },
  { path: '/quit', component: ReleaseCarrier},
  { path: '/main', component: CarrierView},
 
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
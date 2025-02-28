import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Home.vue';
import Analysis from '@/views/Analysis.vue';

const routes = [
    { path: '/', component: Home },
    { path: '/analysis', component: Analysis }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;

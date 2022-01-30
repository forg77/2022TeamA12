import { createRouter, createWebHashHistory } from 'vue-router'
import Home from '../views/Home.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/examManage',
    name: 'ExamManage',
    component: () => import('../views/ExamManage.vue')
  },
  {
    path: '/addExam',
    name: 'AddExam',
    component: () => import('../views/AddExam.vue')
  },
  {
    path: '/invigilationManage',
    name: 'InvigilationManage',
    component: () => import('../views/InvigilationManage.vue')
  },
  {
    path: '/questionsManage',
    name: 'QuestionsManage',
    component: () => import('../views/QuestionsManage.vue')
  },
  {
    path: '/questionsInput',
    name: 'QuestionsInput',
    component: () => import('../views/QuestionsInput.vue')
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router

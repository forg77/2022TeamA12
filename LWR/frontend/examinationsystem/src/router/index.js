import { createRouter, createWebHashHistory } from 'vue-router'
import { siteTitle } from '../config'
import Home from '../views/Home.vue'

const postTitle = " - " + siteTitle;

const routes = [{
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
        component: () =>
            import ( /* webpackChunkName: "about" */ '../views/About.vue')
    },
    {
        path: '/examManage',
        name: '考试管理',
        component: () =>
            import ('../views/ExamManage.vue'),
        meta: {
            title: "考试管理" + postTitle
        }
    },
    {
        path: '/addExam',
        name: '添加考试',
        component: () =>
            import ('../views/AddExam.vue'),
        meta: {
            title: "添加考试" + postTitle
        }
    },
    {
        path: '/invigilationManage',
        name: '监考管理',
        component: () =>
            import ('../views/InvigilationManage.vue'),
        meta: {
            title: "监考管理" + postTitle
        }
    },
    {
        path: '/questionsManage',
        name: '题库查询',
        component: () =>
            import ('../views/QuestionsManage.vue'),
        meta: {
            title: "题库查询" + postTitle
        }
    },
    {
        path: '/questionsInput',
        name: '题库录入',
        component: () =>
            import ('../views/QuestionsInput.vue'),
        meta: {
            title: "题库录入" + postTitle
        }
    },
    {
        path: '/tableTest',
        name: '表格测试',
        component: () =>
            import ('../testViews/TableTest.vue'),
        meta: {
            title: "表格测试" + postTitle
        }
    },
    {
        path: '/dialogTest',
        name: '对话框测试',
        component: () =>
            import ('../testViews/DialogTest.vue'),
        meta: {
            title: "对话框测试" + postTitle
        }
    },
    {
        path: '/examTest',
        name: '考试测试',
        component: () =>
            import ('../testViews/ExamTest.vue'),
        meta: {
            title: "考试测试" + postTitle
        }
    },
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
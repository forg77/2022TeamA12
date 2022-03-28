import {createRouter, createWebHistory, Router} from 'vue-router'
import {config} from '@/config'
import Home from '../views/Home.vue'

const postTitle = " - " + config.siteTitle;

const routes = [
    {
        path: '/login',
        name: "Login",
        component: () =>
            import ('../views/Login.vue'),
        meta: {
            title: "登录"
        }
    },
    {
        path: '/register',
        name: "Register",
        component: () =>
            import ('../views/Register.vue'),
        meta: {
            title: "注册"
        }
    },
    {
        path: '/admin',
        name: "Admin",
        component: () =>
            import ('../views/MainView.vue'),
        props: {
            navs: [{
                title: "考试管理",
                content: [
                    {title: "考试管理", link: "/admin/examManage"},
                    {title: "添加考试", link: "/admin/addExam"},
                    {title: "监考管理", link: "/admin/invigilationManage"},
                ],
            },
                {
                    title: "题库管理",
                    content: [
                        {title: "题库查询", link: "/admin/questionsManage"},
                        {title: "题库录入", link: "/admin/questionsInput"},
                    ],
                },
                {
                    title: "测试",
                    content: [
                        {title: "表格测试", link: "/admin/tableTest"},
                        {title: "对话框测试", link: "/admin/dialogTest"},
                        {title: "考试测试", link: "/admin/examTest"},
                        {title: "考试表格测试", link: "/admin/examTableTest"},
                    ],
                },
            ],
        },
        children: [{
            path: 'home',
            name: 'Home',
            component: Home
        },
            {
                path: 'about',
                name: 'About',
                // route level code-splitting
                // this generates a separate chunk (about.[hash].js) for this route
                // which is lazy-loaded when the route is visited.
                component: () =>
                    import ( /* webpackChunkName: "about" */ '../views/About.vue')
            },
            {
                path: 'examManage',
                name: 'ExamManage',
                component: () =>
                    import ('../views/admin/ExamManage.vue'),
                meta: {
                    title: "考试管理" + postTitle,
                    pageTitle: "考试管理"
                }
            },
            {
                path: 'addExam',
                name: 'AddExam',
                component: () =>
                    import ('../views/admin/AddExam.vue'),
                meta: {
                    title: "添加考试" + postTitle,
                    pageTitle: "添加考试"
                }
            },
            {
                path: 'invigilationManage',
                name: 'InvigilationManage',
                component: () =>
                    import ('../views/admin/InvigilationManage.vue'),
                meta: {
                    title: "监考管理" + postTitle,
                    pageTitle: "监考管理"
                }
            },
            {
                path: 'questionsManage',
                name: 'QuestionsManage',
                component: () =>
                    import ('../views/admin/QuestionsManage.vue'),
                meta: {
                    title: "题库查询" + postTitle,
                    pageTitle: "题库查询"
                }
            },
            {
                path: 'questionsInput',
                name: 'QuestionsInput',
                component: () =>
                    import ('../views/admin/QuestionsInput.vue'),
                meta: {
                    title: "题库录入" + postTitle,
                    pageTitle: "题库录入"
                }
            },
            {
                path: 'tableTest',
                name: 'TableTest',
                component: () =>
                    import ('../testViews/TableTest.vue'),
                meta: {
                    title: "表格测试" + postTitle,
                    pageTitle: "表格测试"
                }
            },
            {
                path: 'dialogTest',
                name: 'DialogTest',
                component: () =>
                    import ('../testViews/DialogTest.vue'),
                meta: {
                    title: "对话框测试" + postTitle,
                    pageTitle: "对话框测试"
                }
            },
            {
                path: 'examTest',
                name: 'ExamTest',
                component: () =>
                    import ('../testViews/ExamTest.vue'),
                meta: {
                    title: "考试测试" + postTitle,
                    pageTitle: "考试测试"
                }
            },
            {
                path: 'examTableTest',
                name: 'ExamTableTest',
                component: () =>
                    import ('../testViews/ExamTableTest.vue'),
                meta: {
                    title: "考试表格测试" + postTitle,
                    pageTitle: "考试表格测试"
                }
            },
        ]
    },
    {
        path: '/teacher',
        name: "Teacher",
        component: () =>
            import ('../views/MainView.vue'),
        props: {
            navs: [{
                title: "考试管理",
                content: [
                    {title: "考试管理", link: "/teacher/examManage"},
                    {title: "考试监考", link: "/teacher/invigilating"},
                ],
            }, {
                title: "题库管理",
                content: [
                    {title: "题库查询", link: "/teacher/questionsManage"},
                    {title: "题库录入", link: "/teacher/questionsInput"},
                ],
            }],
        },
        children: [{
            path: 'questionsManage',
            name: 'TeacherQuestionsManage',
            component: () =>
                import ('../views/teacher/QuestionsManage/index.vue'),
            meta: {
                title: "题库管理" + postTitle,
                pageTitle: "题库管理"
            }
        },
            {
                path: 'questionManage/:bankId',
                name: 'TeacherQuestionManage',
                component: () =>
                    import ('../views/teacher/QuestionsManage/QuestionTable.vue'),
                meta: {
                    title: "题目管理" + postTitle,
                    pageTitle: "题目管理"
                }
            },
            {
                path: 'examManage',
                name: 'TeacherExamManage',
                component: () =>
                    import ('../views/teacher/ExamManage.vue'),
                meta: {
                    title: "考试管理" + postTitle,
                    pageTitle: "考试管理"
                }
            },
            {
                path: 'examEdit/:examId',
                name: 'TeacherExamEdit',
                component: () =>
                    import ('../views/teacher/ExamEdit.vue'),
                meta: {
                    title: "考试编辑" + postTitle,
                    pageTitle: "考试编辑"
                }
            },
            {
                path: 'examCorrect/:examId',
                name: 'ExamCorrect',
                component: () =>
                    import ('../views/teacher/ExamCorrect.vue'),
                meta: {
                    title: "考试批改" + postTitle,
                    pageTitle: "考试批改"
                }
            },
            {
                path: 'addExam',
                name: 'AddExam',
                component: () =>
                    import ('../views/teacher/AddExam/index.vue'),
                meta: {
                    title: "添加考试" + postTitle,
                    pageTitle: "添加考试"
                },
                props: {
                    mode: 'add'
                }
            },
            {
                path: 'editExamConfig/:examId',
                name: 'EditExamConfig',
                component: () =>
                    import ('../views/teacher/AddExam/index.vue'),
                meta: {
                    title: "考试设置" + postTitle,
                    pageTitle: "考试设置"
                },
                props: {
                    mode: 'edit'
                }
            },
            {
                path: 'invigilating',
                name: 'Invigilating',
                component: () =>
                    import ('../views/teacher/Invigilating.vue'),
                meta: {
                    title: "考试监考" + postTitle,
                    pageTitle: "考试监考"
                }
            },
        ]
    },
    {
        path: '/student',
        name: "Student",
        component: () =>
            import ('../views/MainView.vue'),
        props: {
            navs: [{
                title: "我的考试",
                link: "/student/exams",
            },
                {
                    title: "我的成绩",
                    content: [
                        {title: "成绩查询", link: "/student/gradeQuery"},
                        {title: "知识分析", link: "/student/message"},
                    ],
                },
                {
                    title: "我的消息",
                    link: "/student/message"
                }
            ],
        },
        children: [{
            path: '',
            name: 'Home1',
            component: Home
        },
            {
                path: 'exams',
                name: 'Exams',
                component: () =>
                    import ('../views/student/Exams.vue'),
                meta: {
                    title: "我的考试" + postTitle,
                    pageTitle: "我的考试"
                }
            },
            {
                path: 'message',
                name: 'Massage',
                component: () =>
                    import ('../views/student/Message.vue'),
                meta: {
                    title: "我的消息" + postTitle,
                    pageTitle: "我的消息"
                },
            },
            {
                path: 'studentExam/:examId',
                name: 'StudentExam',
                component: () =>
                    import ('../views/student/StudentExam.vue'),
                meta: {
                    title: "考试" + postTitle,
                    pageTitle: "考试"
                }
            },
            {
                path: 'gradeQuery',
                name: 'GradeQuery',
                component: () =>
                    import ('../views/student/GradeQuery.vue'),
                meta: {
                    title: "成绩查询" + postTitle,
                    pageTitle: "成绩查询"
                },
            },
        ]
    },
    {
        path: '/config',
        name: "Config",
        component: () =>
            import ('../views/MainView.vue'),
        props: {
            navs: [{
                title: "个人中心",
                link: "/config/personal"
                // content: [
                //     { title: "考试管理", link: "/teacher/examManage" },
                // ],
            }],
        },
        children: [{
            path: 'personal',
            name: 'Personal',
            component: () =>
                import ('../views/Personal/index.vue'),
            meta: {
                title: '个人中心' + postTitle,
                pageTitle: '个人中心'
            }
        },
        ]
    },
    //重定向
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/admin/',
        redirect: '/admin/examManage'
    },
    {
        path: '/student/',
        redirect: '/student/exams'
    },
    {
        path: '/teacher/',
        redirect: '/teacher/examManage'
    },
]

const router: Router = createRouter({
    history: createWebHistory('/web/'),
    routes,
})

//配置标题
router.beforeEach(to => {
    if (to.meta.title) {
        document.title = <string>to.meta.title;
    }
});

// //配置用户未登录的跳转
// router.beforeEach((to, from) => {
//     if (to.name != "Login") {
//         if (config.user == null) {
//             return { path: '/login', replace: true };
//         }
//     }
//     return true;
// });

export default router;

import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)


//如果在这里出错，那么不会出404，而是空白页。404只有在访问后台的时候会出现
const routes = [
  {
    path:"/",
    name:"login",
    component: ()=>import('../views/login')
  },
  {
    path:'/layout',
    redirect:'/homestay/add', //重定向
    component:()=>import('../views/layout'),
    children:[  //设置子路由
      {
        path:'/homestay/add',
        component:()=>import('../views/layout/homestay/add.vue')
      },
      {
        path:'/homestay/list',
        component:()=>import('../views/layout/homestay/list.vue')
      },
    ]
  },
  {
    path:'/home',
    redirect:'/check/list', //重定向
    component:()=>import('../views/home'),
    children:[  //设置子路由
      {
        path:'/check/list',
        component:()=>import('../views/home/check/list.vue')
      },
    ]
  },

  {
    path:'/manager',
    redirect:'/classify/addStu', //重定向
    component:()=>import('../views/manager'),
    children:[  //设置子路由
      {
        path:'/classify/addStu',
        component:()=>import('../views/manager/classify/addStu.vue')
      },
      {
        path:'/classify/list',
        component:()=>import('../views/manager/classify/list.vue')
      },
      {
        path:'/classify/addTeach',
        component:()=>import('../views/manager/classify/addTeach.vue')
      },
      {
        path:'/classify/listTeacher',
        component:()=>import('../views/manager/classify/listTeacher.vue')
      },
    ]
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

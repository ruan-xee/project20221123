import Vue from 'vue'
import VueRouter from 'vue-router'
import Manage from '../views/Manage.vue'
import User from "@/views/User";
import Home from "@/views/Home";
import store from "@/store";
import Login from "@/views/Login";
import Register from "@/views/Register";
import Person from "@/views/Person";
import File from "@/views/File";
import Role from "@/views/Role";
import Menu from "@/views/Menu";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '框架',
    component: Manage,
    redirect:"/home",
    children:[
      {path:'home', name:'首页', component: Home},
      {path:'user', name:'用户管理', component: User},
      {path:'role', name:'角色管理', component: Role},
      {path:'menu', name:'菜单管理', component: Menu},
      {path:'person', name:'个人信息', component: Person},
      {path:'file', name:'文件管理', component: File},
    ],
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
  },
  {
    path: '/register',
    name: 'Register',
    component: Register,
  },

  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next)=>{
  localStorage.setItem("currentPathName", to.name);
  store.commit("setPath");
  next();
})
export default router

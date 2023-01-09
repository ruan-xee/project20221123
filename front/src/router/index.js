import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";
import Login from "@/views/Login";
import Register from "@/views/Register";

Vue.use(VueRouter)

const routes = [
  // {
  //   path: '/',
  //   name: '框架',
  //   component: Manage,
  //   redirect:"/home",
  //   children:[
  //     {path:'home', name:'首页', component: Home},
  //     {path:'user', name:'用户管理', component: User},
  //     {path:'role', name:'角色管理', component: Role},
  //     {path:'menu', name:'菜单管理', component: Menu},
  //     {path:'person', name:'个人信息', component: Person},
  //     {path:'file', name:'文件管理', component: File},
  //   ],
  // },
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
  },
  {
    path: '/404',
    name: '404',
    component: ()=>import('../views/404.vue')
  },
]
const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

//提供一个重置路由的方法
export const resetRouter = ()=>{
  router.matcher = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
}

export const setRoutes = ()=>{
  const storeMenus = localStorage.getItem("menus");
  if (storeMenus){
    const manageRoute = {path:'/', name: 'Manage', component: ()=>import('../views/Manage'), redirect:"/home", children:[
        {path:'person', name:'个人信息', component: ()=>import('../views/Person')}
      ]}
    const menus = JSON.parse(storeMenus);
    menus.forEach(item=>{
      if (item.path) {
        let itemMenu = {
          path: item.path.replace("/", ""),
          name: item.name,
          component: () => import('../views/' + item.pagePath + '.vue')
        }
        manageRoute.children.push(itemMenu);
      } else if(item.children.length){
        item.children.forEach(child=>{
          if (child.path){
            let itemMenu = {
              path: child.path.replace("/", ""),
              name: child.name,
              component: () => import('../views/' + child.pagePath + '.vue')
            }
            manageRoute.children.push(itemMenu);
          }
        })
      }
    })

    const currentRouteNames = router.getRoutes().map(v=>v.name);
    if (!currentRouteNames.includes('Manage')) {
      router.addRoute(manageRoute);
    }
  }
}

setRoutes();



router.beforeEach((to, from, next)=>{
  localStorage.setItem("currentPathName", to.name);
  store.commit("setPath");


  //未找到路由的情况
  if (!to.matched.length){
    const storeMenus = localStorage.getItem("menus");
    if (storeMenus){
      next("/404");
    } else {
      next("/login");
    }
  }
  next();

})
export default router

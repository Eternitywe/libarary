import { createRouter, createWebHistory } from "vue-router";
import { staticRoutes } from "./routers";
import Cookies from "js-cookie";
import {ElMessage} from "element-plus";



const router = createRouter({
  history: createWebHistory(),
  routes: staticRoutes,
});

//路由守卫
router.beforeEach((to, from, next) => {
  if (to.path === "/login") next();
  const admin = Cookies.get('admin')
  if (!admin && to.path !== '/login'){
    ElMessage({
      message: '您还未登录,请前往登录！',
      type: 'warning',
      plain: true,
    })
    return next('/login')  //未登录强制退回登录页面
  }
  next();
});



// 导出路由
export default router;

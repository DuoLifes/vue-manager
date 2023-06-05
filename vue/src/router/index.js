import Vue from 'vue'
import Router from 'vue-router'
//1. 将vue-router模块，挂载到Vue
Vue.use(Router)

/* Layout */
import Layout from '@/layout'//引入一些组件和混入（大家都会用的东西）

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
// 2.拆分路由
  //2.1.常量路由，无论何种角色，都可以访问的路由
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{//这里的内容会在<router-view :key="key" />标签渲染
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },
]
  //2.2 异步路由，根据不同角色，需要过滤的路由
export const asyncRoutes = [
  {
    path: '/security',
    component: Layout,
    redirect: '/security/user',
    meta: { title: '权限管理', icon: 'el-icon-lock' },
    children: [
      {
        path: 'user',
        name: 'User',
        component: () => import('@/views/security/user/index'),
        meta: { title: '用户管理'}
      },
      {
        path: 'role',
        name: 'Role',
        component: () => import('@/views/security/role/index'),
        meta: { title: '角色管理'}
      },
      {
        path: 'menu',
        name: 'menu',
        component: () => import('@/views/security/menu/index'),
        meta: { title: '菜单管理'}
      },
    ]
  },
]
  //2.3 任意路由,路径错误重定向到的路由
export const anyRoutes = { path: '*', redirect: '/404', hidden: true }// 404 page must be placed at the end !!!

// 3. 回调函数，封装了路由，routes封装的是上面的constantRoutes常量
const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),//滚动行为，y为0
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
// 4. 对外暴露，重置路由函数
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}
// 5. 对外暴露，router这个对象
export default router

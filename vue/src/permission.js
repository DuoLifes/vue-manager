import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist

//当发生路由跳转进行异步调用，to表示要去的路由，from表示要离开的路由，next用来执行钩子，有多种重载形式，比如next(),next({})
router.beforeEach(async(to, from, next) => {
  // 开始进度条
  NProgress.start()

  //设置页面标题
  document.title = getPageTitle(to.meta.title)

  // 通过token确定用户是否已登录
  const hasToken = getToken()
  //如果有token，则已登录
  if (hasToken) {
    /**动态路由需要解决，刷新路由丢失问题**/
    //判断是否为刷新页面，vuex中无数据，则为刷新页面
    if(store.state.user.routesResult.length === 0 ){
      // 获取用户信息(关于异步路由的东西我放这里了)
      store.dispatch('user/getInfo').then(()=>{
        next({path:to.path})
      })
    }else{
      next()
    }
    //如果用户要去的是登录页，直接从定向到主页，因为已经登录了，不用重复登录
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()//进度条转态为完成
    } else {//如果要前往的路由不是/login
      //获取用户名
      const hasGetUserInfo = store.getters.name
      //如果用户名存在
      if (hasGetUserInfo) {
        next()//进行管道中的下一个钩子。如果全部钩子执行完了，则导航的状态就是 confirmed (确认的)。
      } else {//不存在
        try {
          // 获取用户信息
          await store.dispatch('user/getInfo')
          next()//进行管道中的下一个钩子。如果全部钩子执行完了，则导航的状态就是 confirmed (确认的)。
        } catch (error) {//如果获取用户信息出错
          // 删除令牌，进入登录页面重新登录
          await store.dispatch('user/resetToken')
          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)//next('/') 或者 next({ path: '/' }): 跳转到一个不同的地址
          NProgress.done()//进度条结束
        }
      }
    }
  } else {
    /* has no token*/

    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})

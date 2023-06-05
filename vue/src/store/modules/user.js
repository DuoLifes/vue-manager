import { login, logout, getInfo } from '@/api/user' //引入了我们的api。因为我们要向后端发请求
import { getMenusByUserId } from '@/api/menu' //引入了我们的api。因为我们要向后端发请求
import {treeData,unTreeData} from '@/utils/treeutil'//处理树形结构的工具类
import { getToken, setToken, removeToken } from '@/utils/auth' //auth权限相关操作
import { resetRouter , asyncRoutes,anyRoutes,constantRoutes} from '@/router'//拆分好的路由
import router from '@/router'//路由对象

//getDefaultState常数封装了token，name，avatar
const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: '',
    routes:[],//服务器返回的菜单path，菜单就是type类型为menu的
    roles:[],//保存当前用户拥有角色
    buttons:[],//按钮权限信息，按钮就是菜单类型为button的
    resultAsyncRoutes:[],//对比后的异步路由
    routesResult:[],//最终要展示的路由
  }
}
//赋值给state
const state = getDefaultState()
//给actions监听调用的mutations方法，例如commit('SET_TOKEN', data.token)
const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROUTES:(state,routes)=>{
    state.routes = routes
  },
  SET_ROLES:(state,roels)=>{
    state.roels = roels
  },
  SET_BUTTONS:(state,buttons)=>{
    state.buttons = buttons
  },
  SET_RESULTASYNCROUTES: (state,resultAsyncRoutes)=>{
    state.resultAsyncRoutes = resultAsyncRoutes
    //合并常量、异步、路由最终展示的路由
    state.routesResult = constantRoutes.concat(state.resultAsyncRoutes,anyRoutes);
    //将最终合并的路由添加到路由中
    router.addRoutes(state.routesResult)
  }
}
//action监听,通过this.$store.dispatch('******/****')调用
//例如调用下面login就是，this.$store.dispatch('user/login',userInfo)
const actions = {
  // user login
  //处理登录业务
  async login({ commit },userInfo) {
    //解构出用户名和密码
    const { username,password,code} = userInfo;
    //异步调用/api/user文件下的login
    let result = await login({username: username.trim(),password:password,code:code});
    if (result.code==20000){
      //我们规定token为tokenHead + " " + token字符串
      const token = result.data.tokenHead+' '+result.data.token;
      //设置到state
      commit("SET_TOKEN",token);
      //通过auth.js文件的函数，设置到cookie
      setToken(token)
    }else{
      //登录失败
      return Promise.reject(new Error(result.message));
    }
  },

  // get user info
  async getInfo({ commit, state }) {
    //异步调用/api/user文件下的login
    let result = await getInfo(state.token);

    if(result.code === 20000){
      const { loginInfo } = result.data
      if (!loginInfo) {
        return reject('验证失败，请重新登录.');
      }
      //处理路由
      let menusResult = await getMenusByUserId(loginInfo.id)
      if(menusResult.code === 20000){
        const { menus } = menusResult.data
        if(menus.length > 0){
          const _menus = unTreeData(menus)
          let routes = []
          let buttons = []
          _menus.forEach(e=>{
            if(e.type === 'menu'){
              routes.push(e.path)
            }else if(e.type === 'button') {
              buttons.push(e.permission)
            }
          })
          commit('SET_BUTTONS',buttons)
          commit('SET_ROUTES',routes)
          const { roles } = loginInfo
          commit('SET_ROLES',roles)
          //对比后设置需要展示的异步路由
          commit('SET_RESULTASYNCROUTES',compareasyncRoutes(asyncRoutes,routes))
        }
      }else{
        commit('SET_RESULTASYNCROUTES',compareasyncRoutes(asyncRoutes,[]))
      }
      const {username} = loginInfo;
      commit('SET_NAME',username);
      commit('SET_AVATAR','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif');
    }else{
      //登录失败
      return Promise.reject(new Error(result.message));
    }
  },

  // user logout
  async logout({ commit, state }) {
    let result = await logout(state.token);
    if(result.code === 20000){
      removeToken() // must remove  token  first
      resetRouter()
      commit('RESET_STATE')
    }else{
      //退出登录失败
      return Promise.reject(new Error(result.message));
    }
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}
//比较路由，返回需要展示的异步路由
const compareasyncRoutes = (asyncRoutes,routes)=>{
  return asyncRoutes.filter(item =>{
    if(routes.indexOf(item.path)!=-1){//如果当前路由，用户拥有权限，就展示
      if(item.children&&item.children.length){//如果有children就递归
        item.children = compareasyncRoutes(item.children,routes)
      }
      return true;
    }
  })
}
export default {
  namespaced: true,
  state,
  mutations,
  actions
}


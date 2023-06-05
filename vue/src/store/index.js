import Vue from 'vue' //引入Vue，我们要挂载
import Vuex from 'vuex' //我们需要引入Vuex，然后挂载到Vue
//下面都是引入自己写的另一些js文件
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'

Vue.use(Vuex)//将vuex挂载到Vue

//创建Vuex的Store对象，并将上面js文件中暴露的内容注入，最后存储到store常量中
const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user
  },
  getters
})
//暴露store常量
export default store

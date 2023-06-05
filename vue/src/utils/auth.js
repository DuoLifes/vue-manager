//使用js-cookie存储token，这里封装了很多针对cookie等浏览器存储功能
import Cookies from 'js-cookie'
//Token的key值，我们需要根据后端需要，进行修改
// const TokenKey = 'vue_admin_template_token'
const TokenKey = 'vue_admin_template_token'
//对外暴露获取token的函数
export function getToken() {
  return Cookies.get(TokenKey)
}
//对外暴露设定token的函数
export function setToken(token) {
  return Cookies.set(TokenKey, token)
}
//对外暴露移除token的函数
export function removeToken() {
  return Cookies.remove(TokenKey)
}

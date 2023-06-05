import request from '@/utils/request'

export function login(data) {
  return request({
    url: 'login',
    method: 'post',
    data
  })
}
export function captcha() {
  return request({
    url: 'captcha',
    method: 'get',
    responseType:'blob'
  })
}
export function getInfo() {
  return request({
    url: '/login/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: 'logout',
    method: 'post'
  })
}

export function userPage(data,current,size){
  return request({
    url:`security/dd-user-role/page/${current}/${size}`,
    method:'post',
    data
  })
}
export function addUser(data){
  return request({
    url:'security/dd-user-role/add',
    method:'post',
    data
  })
}
export function updateUser(data){
  return request({
    url:'/security/dd-user-role/',
    method:'put',
    data
  })
}
export function deleteUserById(id){
  return request({
    url:`/security/dd-user-role/${id}`,
    method:'delete'
  })
}

export function updatePasswordById(id,password){
  return request({
    url:`/security/dd-user-role/updatePasswordById/${id}/${password}`,
    method:'put'
  })
}
export function updateUserAndRole(data){
  return request({
    url:`/security/dd-user-role/updateUserRole`,
    method:'put',
    params:data
  })
}


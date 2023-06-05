import request from '@/utils/request'

export function menuList(){
  return request({
    url:'/security/dd-menu/menus',
    method:'get'
  })
}
export function addMenu(data){
  return request({
    url:'/security/dd-menu/',
    method:'post',
    data
  })
}
export function updateMenu(data){
  return request({
    url:'/security/dd-menu/',
    method:'put',
    data
  })
}
export function deleteMenuById(id){
  return request({
    url:`/security/dd-menu/${id}`,
    method:"delete",
  })
}
export function deleteMenuList(parameter){
  return request({
    url:'/security/dd-menu/ids',
    method:"delete",
    params: parameter
  })
}
export function updateMenuAndRole(parameter){
  return request({
    url:'/security/dd-menu/roleAndMenu',
    method:"put",
    params: parameter
  })
}

export function menuIdByRoleId(rid){
  return request({
    url:`/security/dd-menu/menuIdByRoleId/${rid}`,
    method:"get"
  })
}


export function getMenusByUserId(){
  return request({
    url:`/security/dd-menu/menu`,
    method:"get"
  })
}

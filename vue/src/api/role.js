import request from '@/utils/request'

//分页查询角色列表
export function getAllRolesPage(current,size) {
  return request({
    url:`/security/dd-role/page/${current}/${size}`,
    method:'get',
  })
}
//添加角色
export function addRole(data){
  return request({
    url:'/security/dd-role/',
    method:'post',
    data
  })
}
//修改角色
export function updateRole(data){
  return request({
    url:'/security/dd-role/',
    method:'put',
    data
  })
}
//删除角色
export function deleteRoleById(id){
  return request({
    url:`/security/dd-role/role/${id}`,
    method:'delete'
  })
}

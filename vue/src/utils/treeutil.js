/**
 * 构造树型结构数据
 * @param {*} source 数据源
 * @param {*} id id字段 默认 'id'
 * @param {*} parentId 父节点字段 默认 'parentId'
 * @param {*} children 孩子节点字段 默认 'children'
 * @param {*} rootId 根Id 默认 0
 */
export function treeData (source, id, pid, children, rootId) {
  id = id || 'id'
  pid = pid || 'pid'
  children = children || 'children'
  rootId = rootId || 0
  const cloneData = JSON.parse(JSON.stringify(source))// 对源数据深度克隆
 return cloneData.filter(father => {
    const branchArr = cloneData.filter(child => father[id] == child[pid])// 返回每一项的子级数组
    branchArr.length > 0 ? father[children] = branchArr : delete father[children]// 如果存在子级，则给父级添加一个children属性，并赋值
    return father[pid] == rootId // 返回第一层
  })
}

/**
 * 树形结构解析，构造为普通形式
 */
export function unTreeData(source,children){
  children = children || 'children'
  let cloneData = JSON.parse(JSON.stringify(source))// 对源数据深度克隆
  let arr = []//保存最终结果
  unTreeDatadd(cloneData,children,arr)//递归解析树结构，解析结果放在arr中
  return arr;//将arr返回
}
function unTreeDatadd(source,children,arr){
  //遍历数组
  source.filter(father=>{
    if(father[children]){//如果当前元素有child，将当前元素加入arr，然后递归它的儿子加入arr，儿子都递归完，删除当前元素的children属性
      arr.push(father)//将当前元素加入arr
      unTreeDatadd(father[children],children,arr)//递归儿子
      delete father[children]//删除children
    }else{//如果当前元素没有child
      arr.push(father)//直接添加到arr
    }
  })
}

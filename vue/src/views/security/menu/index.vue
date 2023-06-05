<template>
  <div class="menu-container">
    <!--    添加开始-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-button type="primary" @click="addMenuClick(0)">添加顶级菜单</el-button>
      </el-form-item>
    </el-form>
<!--    添加结束-->
    <el-table :data="tableData" style="width: 100%;margin-bottom: 20px;" row-key="id" border default-expand-all
              :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column prop="name" label="菜单名" sortable width="500vw"></el-table-column>
      <el-table-column prop="type" label="菜单类型" sortable width="180"></el-table-column>
      <el-table-column prop="permission" label="菜单权限(一般给按钮使用)"></el-table-column>
      <el-table-column prop="url" label="菜单url(与后端对应，无此菜单权限的用户，无法访问)"></el-table-column>
      <el-table-column prop="path" label="菜单path(要和前端路由配置的path一致)"></el-table-column>
<!--      <el-table-column prop="component" label="组件(对应路由组件)"></el-table-column>-->
<!--      <el-table-column prop="iconCls" label="组件图标"></el-table-column>-->
      <el-table-column label="操作(添加子菜单/编辑菜单/删除菜单)" width="300vw" align="center">
        <template slot-scope="{row,$index}"><!--注意这个可以让我们使用row和$index两个值，方便我们操作,row是当前列-->
          <el-button type="success" icon="el-icon-plus" @click="addMenuClick(row.id)"></el-button>
          <el-button type="primary" icon="el-icon-edit" @click="updateMenuClick(row)"></el-button>
          <el-button type="danger" icon="el-icon-delete" @click="deleteMenu(row)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--    对话框开始-->
    <el-dialog title="添加菜单" :visible.sync="addVisible" width="80%">
      <el-form :inline="true" label-width="10vw" class="demo-form-inline">
        <el-form-item label="父id" v-show="false" >
          <el-input v-model="addMenuData.pid" placeholder="父id"></el-input>
        </el-form-item>
        <el-form-item label="菜单名">
          <el-input v-model="addMenuData.name" placeholder="菜单名"></el-input>
        </el-form-item>
        <el-form-item label="菜单类型(menu(菜单)/button(按钮))">
          <el-input v-model="addMenuData.type" placeholder="菜单类型"></el-input>
        </el-form-item>
        <el-form-item label="菜单权限">
          <el-input v-model="addMenuData.permission" placeholder="菜单权限"></el-input>
        </el-form-item>
        <el-form-item label="菜单url">
          <el-input v-model="addMenuData.url" placeholder="菜单url"></el-input>
        </el-form-item>
        <el-form-item label="菜单path">
          <el-input v-model="addMenuData.path" placeholder="菜单path"></el-input>
        </el-form-item>
        <el-form-item label="组件">
          <el-input v-model="addMenuData.component" placeholder="组件"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addVisible = false">取 消</el-button>
        <el-button type="primary" @click="addOrUpdateMenu">确 定</el-button>
      </span>
    </el-dialog>
    <!--    对话框结束-->
  </div>
</template>

<script>
  import {treeData,unTreeData} from '@/utils/treeutil'
  import {menuList,addMenu,deleteMenuList,deleteMenuById,updateMenu} from '@/api/menu'
export default {
  name: 'index.vue',
  data(){
    return{
      tableData: [],//树形结构数据
      addMenuData:{},//添加菜单的数据
      updateMenuData:{},//修改菜单数据
      addVisible:false,//添加菜单对话框
      editVisible:false,//编辑菜单对话框
    }
  },
  created() {
    this.init()
  },
  methods:{
    init() {
      this.menuList();
      this.addMenuData = {};
    },
    /**=================================================api请求=============================================**/
    async menuList(){
      var result = await menuList()
      if(result.code === 20000){
        let data = result.data;
        const _treeData = treeData(data.menuAllList)
        this.tableData = _treeData
      }else{
        this.$message.error(result.message);
      }
    },
    async addOrUpdateMenu(){
      if(this.addMenuData.name === undefined || this.addMenuData.permission === undefined){
        this.$message.error("菜单名和菜单权限为必选项");
        return;
      }
      if(this.addMenuData.id){//如果有id说明是修改
        var result = await updateMenu(this.addMenuData);
        if(result.code === 20000){
          this.$message.success("修改菜单成功")
          this.init()
        }else{
          this.$message.error("修改失败")
        }
      }else{
        var result = await addMenu(this.addMenuData);
        if(result.code === 20000){
          this.$message.success("添加菜单成功")
          this.init()
        }else{
          this.$message.error("添加失败")
        }
      }
      this.addVisible = false;
    },
    /**=================================================组件回调=============================================**/
    deleteMenu(menu){
      this.$confirm('此操作将删除当前菜单和当前菜单所有子菜单，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let result = {};
        if(menu.children){
          let arr =[]
          unTreeData([menu]).filter(e=>{
            arr.push(e.id)
          });
          result = await deleteMenuList({ids:arr.join(',')})
        }else{
          console.log(2)
          result = await deleteMenuById(menu.id);
        }

        if(result.code === 20000){
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.init()
        }else{
          this.$message.error(result.message||"删除失败！！！");
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    //添加按钮回调
    addMenuClick(pid){
      this.addVisible = true;
      this.addMenuData = {}
      this.addMenuData.pid = pid
    },
    //修改按钮回调
    updateMenuClick(data){
      this.addVisible = true;
      const _data = JSON.parse(JSON.stringify(data))
      this.addMenuData = _data;
    },
  }
}
</script>

<style lang="scss" scoped>
.menu {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>

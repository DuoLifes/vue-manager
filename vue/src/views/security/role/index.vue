<template>
  <div class="role-container">
    <!--    添加和批量删除开始-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-popover placement="bottom" width="800" v-model="addVisible">
          <p>添加角色</p>
          <el-form :inline="true" :model="addUserData" class="demo-form-inline">
            <el-form-item label="角色名">
              <el-input v-model="addUserData.nameZh" placeholder="角色名"></el-input>
            </el-form-item>
            <el-form-item label="权限名">
              <el-input v-model="addUserData.name" placeholder="权限名"></el-input>
            </el-form-item>
          </el-form>
          <div style="text-align: right; margin: 0">
            <el-button size="mini" type="text" @click="addVisible = false">取消</el-button>
            <el-button type="primary" size="mini" @click="addRole">添加</el-button>
          </div>
          <el-button type="primary" slot="reference">添加</el-button>
        </el-popover>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" @click="" :disabled="deleteAllFlag">批量删除</el-button>
      </el-form-item>
    </el-form>
    <!--    添加和批量删除结束-->
    <!--    表格开始-->
    <el-table v-loading="loading" :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="100vw"></el-table-column>
      <el-table-column label="序号" type="index"  width="100vw"></el-table-column>
      <el-table-column prop="nameZh" label="角色名" width="400vw"></el-table-column>
      <el-table-column prop="name" label="权限字段" width="500vw"></el-table-column>
      <el-table-column label="操作(分配菜单权限/编辑角色/删除角色)" width="400vw" align="center">
        <template slot-scope="{row,$index}"><!--注意这个可以让我们使用row和$index两个值，方便我们操作-->
          <el-button type="success" icon="el-icon-menu" @click="updateMenuAndRoleClick(row)"></el-button>
          <el-button type="primary" icon="el-icon-edit" @click="updateRoleClick(row)"></el-button>
          <el-button type="danger" icon="el-icon-delete" @click="deleteRoleById(tableData[$index].id)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--    表格结束-->
    <!--    分页开始-->
    <div class="block">
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-size="pageSize"
        layout="total, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
    <!--    分页结束-->
    <!--    对话框开始-->
    <el-dialog title="修改角色拥有菜单权限" :visible.sync="menuVisible" width="30%">
      <el-tree
        :data="treeMenuData"
        show-checkbox
        node-key="id"
        :props="defaultProps"
        :default-expanded-keys="currentNodes"
        ref="tree">
      </el-tree>
      <span slot="footer" class="dialog-footer">
        <el-button @click="menuVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateMenuAndRole">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="修改角色信息" :visible.sync="editVisible" width="30%">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="角色名">
          <el-input v-model="updateRoleData.nameZh" placeholder="角色名"></el-input>
        </el-form-item>
        <el-form-item label="权限名">
          <el-input v-model="updateRoleData.name" placeholder="权限字段"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateRole">确 定</el-button>
      </span>
    </el-dialog>
    <!--    对话框结束-->
  </div>
</template>

<script>
import { getAllRolesPage,addRole,deleteRoleById,updateRole} from '@/api/role'
import {menuList,updateMenuAndRole,menuIdByRoleId} from '@/api/menu'
import { treeData, unTreeData } from '@/utils/treeutil'
export default {
  name: 'index.vue',
  data(){
    return{
      addUserData:{
        name: "",
        nameZh: ""
      },//添加角色
      updateRoleData:{},//修改角色
      //角色展示数据
      tableData: [{
        name:"ROLE_admin",
        nameZh:"超级管理员"
      },
        {
          name:"ROLE_user",
          nameZh: "普通用户"
        }
      ],
      loading:false,
      pageNum: 0,//当前页
      pageSize:8,//每页条目个数
      total:1,//数据个数
      addVisible:false,//添加按钮的弹出框
      deleteAllFlag:true,//批量删除按钮是否可用
      menuVisible:false,//修改拥有菜单权限
      editVisible:false,//修改角色信息对话框
      treeMenuData: [],//菜单树结构
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      currentNodes:[],//当前选中的节点
      updateMenuAndRoleData:{rid:'',mids:''}//修改角色拥有菜单权限的数据
    }
  },
  created() {
    this.init()
  },
  methods:{
    init(){
      this.getAllRolesPage(this.pageNum,this.pageSize);
      this.menuList()
      this.addUserData={
        name: "",
        nameZh: ""
      };
    },
    /**=================================================api请求=============================================**/
    async getAllRolesPage(current,size){
      this.loading = true;//查询表格开始，加载动画显示
      var result = await getAllRolesPage(current,size);
      let data = result.data
      if (result.code === 20000){
        this.tableData = data.RoleAllList
        this.pageNum=data.pageNum;//当前页
        this.total=data.total;//数据个数
      }else {
        this.$message('角色信息查询失败');
        this.tableData = []
      }
      this.loading = false;
    },
    async menuList(){
      var result = await menuList()
      if(result.code === 20000){
        let data = result.data;
        const _treeData = treeData(data.menuAllList)
        this.treeMenuData = _treeData
      }else{
        this.$message.error(result.message);
      }
    },
    //角色id获取菜单id
    async menuIdByRoleId(rid){
      var result = await menuIdByRoleId(rid)
      if(result.code === 20000){
        return result.data.menuId
      }else{
        this.$message.info("查询当前角色菜单列表失败！！！")
      }
    },
    //添加角色
    async addRole(){
      if(this.addUserData.name ==="" ||this.addUserData.nameZh===""){
        this.$message('请正确输入权限名和角色名！！！');
        return;
      }
      let result = await addRole(this.addUserData);
      if(result.code===20000){
        this.$message({
          message: '添加角色成功',
          type: 'success'
        });
        this.init()
      }else{
        this.$message.error(result.message);
      }
      this.addVisible = false
    },
    async updateRole(){
      if(this.updateRoleData.name===''||this.updateRoleData.nameZh===''){
        this.$message('请正确输入权限名和角色名！！！');
        return;
      }
      let result = await updateRole(this.updateRoleData)
      if (result.code === 20000){
        this.$message.success("修改完成！！")
        this.init()
      }else{
        this.$message.error(result.message||"修改失败！！！");
      }
      this.editVisible = false
    },
    async updateMenuAndRole(){
      this.updateMenuAndRoleData.mids = this.$refs.tree.getCheckedKeys().join(",")
      var result = await updateMenuAndRole(this.updateMenuAndRoleData);
      if(result.code === 20000){
        this.init()
        this.$message.success("修改成功！！！")
      }else{
        this.$message.error(result.message||"修改失败")
      }
      this.menuVisible = false
    },
    /**=================================================组件回调=============================================**/
    updateMenuAndRoleClick(data){
      this.currentNodes = []
      this.updateMenuAndRoleData.rid = data.id
      // //根据角色id获取菜单id，做数据回显
      const _this = this
      this.menuIdByRoleId(data.id).then(arr=>{
        if(arr.length > 0){
          this.currentNodes = arr
          _this.$refs.tree.setCheckedKeys(arr);
        }else{
          this.currentNodes = []
          _this.$refs.tree.setCheckedKeys([]);
        }
      }).catch(err=>{
        this.currentNodes = []
        _this.$refs.tree.setCheckedKeys([]);
      })
      this.menuVisible = true
    },
    updateRoleClick(data){
      const _data = JSON.parse(JSON.stringify(data))
      this.updateRoleData = _data;
      this.editVisible = true;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      if(val.length > 0 ){
        this.deleteAllFlag = false;
      }else{
        this.deleteAllFlag = true;
      }
    },
    deleteRoleById(id){
      if(id === '1'){
        this.$message.info("超级管理员角色不可删除！！！")
        return ;
      }
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        var result = await deleteRoleById(id);
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
    handleCurrentChange(val) {
      this.getAllRolesPage(val,this.pageSize)
    }
  },
}
</script>

<style lang="scss" scoped>
.role {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>

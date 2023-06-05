<template>
  <div class="user-container">
<!--    条件查询开始-->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="用户名">
        <el-input v-model="formInline.username" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item label="角色名">
        <el-input v-model="formInline.roles[0].nameZh" placeholder="角色名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>
<!--    条件查询结束-->
<!--    添加和批量删除开始-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-popover placement="bottom" width="800" v-model="addVisible">
          <p>添加用户</p>
          <el-form :inline="true" :model="addUserData" class="demo-form-inline">
            <el-form-item label="用户名">
              <el-input v-model="addUserData.username" placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="addUserData.password" placeholder="密码"></el-input>
            </el-form-item>
          </el-form>
          <div style="text-align: right; margin: 0">
            <el-button size="mini" type="text" @click="addVisible = false">取消</el-button>
            <el-button type="primary" size="mini" @click="addUser">添加</el-button>
          </div>
          <el-button type="primary" slot="reference" v-show="$store.state.user.buttons.indexOf('btn:security')!=-1">添加</el-button>
        </el-popover>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" @click="" :disabled="deleteAllFlag">批量删除</el-button>
      </el-form-item>
    </el-form>
<!--    添加和批量删除结束-->
<!--    表格开始-->
    <el-table v-loading="loading" :data="tableData" border :default-sort = "{prop: 'username', order: 'ascending'}" style="width: 100vw" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="100vw"></el-table-column>
      <el-table-column label="序号" type="index" fixed="left" width="100w"></el-table-column>
<!--      <el-table-column prop="id" label="序号" fixed="left" width="0px"></el-table-column>-->
      <el-table-column prop="username" label="username" width="200vw"></el-table-column>
      <el-table-column prop="rolesStr" label="拥有角色列表" width="400vw"></el-table-column>
      <el-table-column prop="authoritiesStr" label="拥有权限列表" width="400vw"></el-table-column>
      <el-table-column fixed="right" label="操作(分配角色/编辑用户/修改密码/删除用户)" width="300vw">
        <template slot-scope="{row,$index}"><!--注意这个可以让我们使用row和$index两个值，方便我们操作-->
          <el-button type="success" icon="el-icon-user-solid" @click="updateUserAndRoleClick(row)"></el-button>
          <el-button type="primary" icon="el-icon-edit" @click="updateUserClick(row)"></el-button>
          <el-button type="primary" icon="el-icon-key" @click="updataPasswordClick(row)"></el-button>
          <el-button type="danger" icon="el-icon-delete" @click="deleteUserById(tableData[$index].id)"></el-button>
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
    <el-dialog title="修改用户拥有角色" :visible.sync="roleVisible" width="30%">
      <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
      <div style="margin: 15px 0;"></div>
      <el-checkbox-group v-model="checkedCities" @change="handleCheckedCitiesChange">
        <el-checkbox v-for="city in cities" :label="city" :key="city.id">{{city.nameZh}}</el-checkbox>
      </el-checkbox-group>
      <span slot="footer" class="dialog-footer">
        <el-button @click="roleVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateUserAndRole">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="修改用户信息" :visible.sync="editVisible" width="30%">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="用户名">
          <el-input v-model="updateUserData.username" placeholder="用户名"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateUser">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="修改用户密码" :visible.sync="passwordVisible" width="30%">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="要修改密码">
          <el-input v-model="passwordData.password" placeholder="密码"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="passwordVisible = false">取 消</el-button>
        <el-button type="primary" @click="updataPassword">确 定</el-button>
      </span>
    </el-dialog>
<!--    对话框结束-->
  </div>
</template>

<script>
import {userPage,addUser,deleteUserById,updateUser,updatePasswordById,updateUserAndRole} from '@/api/user'
import { getAllRolesPage} from '@/api/role'
export default {
  name: "user",
  data() {
    return {
      //查询条件
      formInline: {
        username: '',
        roles:[
          {
            nameZh:''
          }
        ]
      },
      addUserData:{
        password: "",
        username: ""
      },//添加用户
      updateUserData:{},//修改用户
      passwordData:{},//修改用户密码
      updateUserAndRolendRoleData:{},//修改用户拥有角色
      //用户展示数据
      tableData: [{
        username:"admin",
        roles:"超级管理员,普通用户",
        authorities:"ROLE_admin"
      },
        {
          username:"admin",
          roles:"超级管理员,普通用户",
          authorities:"ROLE_admin"
        }
      ],
      loading:false,
      pageNum: 1,//当前页
      pageSize:8,//每页条目个数
      total:1,//数据个数
      multipleSelection: [],//当前选中的用户数据
      deleteAllFlag:true,//批量删除按钮是否可用
      addVisible:false,//添加按钮的弹出框
      roleVisible:false,//修改用户角色的对话框
      editVisible:false,//修改用户信息对话框
      passwordVisible:false,//修改用户密码
      //分配用户角色，的复选框数据
      checkAll: false,//是否全选
      isIndeterminate: true,//表示 checkbox 的不确定状态，一般用于实现全选的效果
      checkedCities: [],//当前选中的
      cities: ['上海', '北京', '广州', '深圳'],//多少个复选项
    }
  },
  created() {
    this.init()
  },
  methods: {
    //初始化方法
    init(){
      this.userPage(this.pageNum,this.pageSize)
      this.getAllRolesPage()
      this.addUserData={
        password: "",
        username: ""
      };
    },
    /**=================================================api请求=============================================**/
    //分页查询用户
    async userPage(current,size){
      this.loading = true;//查询表格开始，加载动画显示
      this.pageSize= size;//每页条目个数
      //深度克隆一份查询条件
      const _formInline = JSON.parse(JSON.stringify(this.formInline))
      if(!_formInline.roles[0].nameZh){//如果没有这个条件，需要移除
        delete _formInline.roles
      }
      //进行查询
      let result = await userPage(_formInline,current,this.pageSize);
      console.log(result)
      //响应正常
      if(result.code === 20000){
        //拿到响应数据
        let data = result.data
        //过来数据，方便我们展示数据
        //将对象roles和authorities封装成字符串，并将字符串作为一个字段添加到数据中
        data.allUserListPage.forEach(function(item){
            if(item.roles.length>0){
              let str = ''
              item.roles.forEach(item2=> str += item2.nameZh+",");
              item.rolesStr = str.substring(0,str.length-1);
            }else{
              item.rolesStr = "当前用户没有所属角色"
            }
            if(item.authorities.length>0){
              let str = ''
              item.authorities.forEach(item2 => str += item2.authority+",")
              item.authoritiesStr = str.substring(0,str.length-1);
            }else{
              item.authoritiesStr = "当前用户没有任何权限"
            }
          }
        );
        this.tableData = data.allUserListPage
        this.pageNum=data.pageNum;//当前页
        this.total=data.total;//数据个数
      }else{
        this.tableData = []
        this.$message('用户信息查询失败');
      }
      this.loading = false;//表格加载结束
    },
    //查询角色
    async getAllRolesPage(){
      var result = await getAllRolesPage(0,1000);
      if (result.code === 20000){
        console.log(result)
        this.cities = result.data.RoleAllList;

      }else{
        this.$message.error("获取角色列表失败！！！")
      }
    },
    //添加用户
    async addUser(){
      if(this.addUserData.username ==="" ||this.addUserData.password===""){
        this.$message('请正确输入用户名和密码！！！');
        return;
      }
      let result = await addUser(this.addUserData);
      if(result.code===20000){
        this.$message({
          message: '添加用户成功',
          type: 'success'
        });
        this.init()
      }else{
        this.$message.error(result.message);
      }
      this.addVisible = false
    },
    async updateUser(){
      var result = await updateUser(this.updateUserData);
      if (result.code === 20000){
        this.$message.success("修改成功")
        this.init();
      }else{
        this.$message.error("修改失败")
      }
      this.editVisible = false;
    },
    async updataPassword(){
      const data = this.passwordData;
      if(data.password === '' || data.password=== undefined){
        this.$message.info("请输入密码！！！");
        return ;
      }
      var result = await updatePasswordById(data.id,data.password)
      if(result.code === 20000){
        this.$message.success("修改成功")
      }else{
        this.$message.error(result.message||"修改失败！！")
      }
      this.passwordVisible = false;
    },
    //更新用户角色
    async updateUserAndRole(){
      let arr = [];

      this.checkedCities.filter(e=>{
        arr.push(e.id)
      })
      this.updateUserAndRolendRoleData.rids = arr.join(",")
      console.log(this.updateUserAndRolendRoleData)
      let result = await updateUserAndRole(this.updateUserAndRolendRoleData)
      if(result.code === 20000){
        this.$message.success("修改成功！！！")
        this.init()
      }else{
        this.$message.error("修改失败！！！")
      }
      this.roleVisible = false
    },
    /**=================================================组件回调=============================================**/
    onSubmit() {
      console.log(this.formInline);
      this.userPage(1,this.pageSize)
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      if(val.length > 0 ){
        this.deleteAllFlag = false;
      }else{
        this.deleteAllFlag = true;
      }
    },
    updateUserClick(data){
      this.editVisible = true;
      const _data = JSON.parse(JSON.stringify(data))
      this.updateUserData = _data;
    },
    //修改用户拥有角色，复选框的回显
    updateUserAndRoleClick(data){
      this.updateUserAndRolendRoleData.uid = data.id //封装后端接口需要的数据
      this.roleVisible = true
      const _data = JSON.parse(JSON.stringify(data))
      //如果已有角色，需要进行数据回显
      if(_data.roles && _data.roles.length>0){
        this.cities.forEach(e=>{
          _data.roles.forEach(r=>{
            if(r.id==e.id){
              this.checkedCities.push(e)
            }
          })
        })
      }else{
        this.checkedCities=[]
      }
    },
    updataPasswordClick(data){
      this.passwordVisible = true;
      const _data = JSON.parse(JSON.stringify(data))
      this.passwordData.id = _data.id;
    },
    deleteUserById(id){
      if(id === '1'){
        this.$message.info("超级管理员用户不可删除！！！")
        return ;
      }
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        var result = await deleteUserById(id);
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
      this.userPage(val,this.pageSize)
    },
    handleCheckAllChange(val) {
      this.checkedCities = val ? this.cities : [];
      this.isIndeterminate = false;
    },
    handleCheckedCitiesChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.cities.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length;
    },
  },


}
</script>

<style lang="scss" scoped>
.user {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>

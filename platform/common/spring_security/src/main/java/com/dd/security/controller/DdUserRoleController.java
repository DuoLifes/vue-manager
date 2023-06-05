package com.dd.security.controller;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.dd.common_utils.Result;
import com.dd.security.entity.DdRole;
import com.dd.security.entity.DdUser;
import com.dd.security.service.DdRoleService;
import com.dd.security.service.DdUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-13
 */
@RestController
@Api("传递查询条件时，不要携带security的字段，只携带DdUser对象本身的字段，JSON")
@RequestMapping("/security/dd-user-role")
public class DdUserRoleController {
    @Autowired
    private DdUserService ddUserService;
    @Autowired
    private DdRoleService ddRoleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @ApiOperation("获取所有操作员以及它拥有的角色和权限,传递查询条件时，不要携带security的字段，只携带DdUser对象本身的字段，JSON")
    @PostMapping("/")
    public Result getAllUser(@RequestBody DdUser ddUser){
        List<DdUser> list = ddUserService.getAllUser(ddUser);
        if(list.isEmpty()){
           return Result.error().message("没有获取的用户信息！！！");
        }
        return Result.ok().data("allUserList",list);
    }
    @ApiOperation("分页获取所有操作员以及它拥有的角色和权限,传递查询条件时，不要携带security的字段，只携带DdUser对象本身的字段，JSON")
    @PostMapping("/page/{current}/{size}")
    public Result getAllUserPage(@RequestBody DdUser ddUser,@PathVariable Integer current,@PathVariable Integer size){

        Page<Object> objects = PageHelper.startPage(current, size);

//        List<DdUser> list = ddUserService.getAllUser(ddUser);
        List<DdUser> list = ddUserService.list();
        for (int i = 0; i < list.size(); i++) {
            DdUser user =  list.get(i);
            List<DdRole> rolesByUserId = ddRoleService.getRolesByUserId(user.getId());
            user.setRoles(rolesByUserId);
            list.set(i,user);
        }

        if(list.isEmpty()){
            return Result.error().message("没有获取的用户信息！！！");
        }
        return Result.ok()
                .data("allUserListPage",list)
                .data("pageNum",objects.getPageNum())
                .data("pages",objects.getPages())
                .data("total",objects.getTotal());
    }
    @ApiOperation("添加用户")
    @PostMapping("/add")
    public Result addUser (@RequestBody DdUser ddUser){
        Snowflake snowflake = IdUtil.getSnowflake(0,0);
        ddUser.setId(snowflake.nextIdStr());//生成一个随机id
        //密码需要加密
        String encode = passwordEncoder.encode(ddUser.getPassword());
        ddUser.setPassword(encode);
        if(ddUserService.save(ddUser)){
            return Result.ok().message("添加成功");
        }
        return Result.error().message("添加失败");
    }

    @ApiOperation("更新用户，此接口无法修改密码")
    @PutMapping("/")
    public Result updateUser(@RequestBody DdUser ddUser){
        ddUser.setPassword(null);//不允许直接改密码
        if(ddUserService.updateById(ddUser)){
          return Result.ok().message("修改用户成功！！！");
        }
        return Result.error().message("修改失败！！！");
    }

    @ApiOperation("更新用户拥有的角色")
    @PutMapping("/updateUserRole")
    public Result updateUserRole(String uid,String[] rids){
        return ddUserService.updateUserRole(uid,rids);
    }

    @ApiOperation("修改密码")
    @PutMapping("/updatePasswordById/{id}/{password}")
    public Result updatePassword(@PathVariable String id, @PathVariable String password){
        String encode = passwordEncoder.encode(password);
        DdUser ddUser = new DdUser();
        ddUser.setId(id);
        ddUser.setPassword(encode);
        if(ddUserService.updateById(ddUser)){
           return Result.ok().message("修改密码成功！！！");
        }
        return Result.error().message("修改密码失败！！！");
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable String id){
        if(id.equals("1")){
            return Result.error().message("超级管理员不允许删除，删除失败！！！");
        }
        if(ddUserService.removeById(id)){
            return Result.ok().message("删除成功！！！");
        }
        return Result.error().message("删除失败！！！");
    }

}


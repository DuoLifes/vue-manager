package com.dd.security.controller;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.dd.common_utils.Result;
import com.dd.security.entity.DdRole;
import com.dd.security.service.DdRoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/security/dd-role")
public class DdRoleController {
    @Autowired
    private DdRoleService ddRoleService;

    @ApiOperation("获取所有角色")
    @GetMapping("/")
    public Result getAllRoles(){
        List<DdRole> list = ddRoleService.list();
        if(list.isEmpty()){
            return Result.error().message("没有获取的任何角色信息！！！");
        }
        return Result.ok().data("RoleAllList",list);
    }
    @ApiOperation("获取所有角色")
    @GetMapping("/page/{current}/{size}")
    public Result getAllRolesPage(@PathVariable Integer current,@PathVariable Integer size){
        Page<Object> page = PageHelper.startPage(current, size);
        List<DdRole> list = ddRoleService.list();
        if(list.isEmpty()){
            return Result.error().message("没有获取的任何角色信息！！！");
        }
        return Result.ok().data("RoleAllList",list)
                          .data("pageNum",page.getPageNum())
                          .data("total",page.getTotal());
    }
    @ApiOperation("修改角色")
    @PutMapping("/")
    public Result updateRole(@RequestBody DdRole ddRole){
        //如果角色名不是ROLE_打头，就补充上
        if(!ddRole.getName().startsWith("ROLE_")){
            ddRole.setName("ROLE_"+ddRole.getName());
        }
        if(ddRoleService.updateById(ddRole)){
            return Result.ok().message("修改成功");
        }
        return Result.error().message("修改失败");
    }
    @ApiOperation("添加角色")
    @PostMapping("/")
    public Result addRole(@RequestBody DdRole ddRole){
        Snowflake snowflake = IdUtil.getSnowflake(0,0);
        ddRole.setId(snowflake.nextIdStr());//生成一个随机id
        //如果角色名不是ROLE_打头，就补充上
        if(!ddRole.getName().startsWith("ROLE_")){
            ddRole.setName("ROLE_"+ddRole.getName());
        }
        if(ddRoleService.save(ddRole)){
            return Result.ok().message("添加成功");
        }
        return Result.error().message("添加失败");
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/role/{id}")
    public Result deleteRole(@PathVariable(value = "id",name = "id") String id){
        if(ddRoleService.removeById(id)){
            return Result.ok().message("删除成功");
        }
        return Result.error().message("删除失败");
    }
}


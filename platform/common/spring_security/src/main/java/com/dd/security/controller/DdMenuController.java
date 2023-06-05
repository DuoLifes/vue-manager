package com.dd.security.controller;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dd.common_utils.Result;
import com.dd.security.entity.DdMenu;
import com.dd.security.entity.DdRoleMenu;
import com.dd.security.service.DdMenuService;
import com.dd.security.service.DdRoleMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-14
 */
@RestController
@RequestMapping("/security/dd-menu")
public class DdMenuController {
    @Autowired
    private DdMenuService ddMenuService;
    @Autowired
    private DdRoleMenuService ddRoleMenuService;

    @ApiOperation(value = "通过用户id查询菜单列表")
    @GetMapping("/menu")
    public Result getMenusByUserId(){
        List<DdMenu> list = ddMenuService.getMenusByUserId();
//        if(list.isEmpty()){
//            return Result.error().message("没有菜单！！");
//        }
        return Result.ok().data("menus",list);
    }

    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/menus")
    public Result getMenus(){
        List<DdMenu> list = ddMenuService.list();
        if(list.isEmpty()){
            return Result.error().message("没有菜单！！");
        }
        return Result.ok().data("menuAllList",list);
    }
    @ApiOperation("添加菜单")
    @PostMapping("/")
    public Result insertMenu(@RequestBody DdMenu ddMenu){
        Snowflake snowflake = IdUtil.getSnowflake(0,0);
        ddMenu.setId(snowflake.nextIdStr());//生成一个随机id
        if(ddMenuService.save(ddMenu)){
            return Result.ok().message("添加成功");
        }
        return Result.error().message("添加失败");
    }
    @ApiOperation("修改菜单")
    @PutMapping("/")
    public Result updateMenu(@RequestBody DdMenu ddMenu){
        if(ddMenuService.updateById(ddMenu)){
            return Result.ok().message("修改成功");
        }
        return Result.error().message("修改失败");
    }
    @ApiOperation("删除菜单")
    @DeleteMapping("/{id}")
    public Result deleteRole(@PathVariable(value = "id",name = "id") String id){
        if(ddMenuService.removeById(id)){
            return Result.ok().message("删除成功");
        }
        return Result.error().message("删除失败");
    }

    @ApiOperation("批量删除菜单")
    @DeleteMapping ("/ids")
    public Result deleteRoleList(String ids){
        System.out.println(ids);
        String[] split = ids.split(",");
        if(ddMenuService.removeByIds(Arrays.asList(split.clone()))){
            return Result.ok().message("删除成功");
        }
        return Result.error().message("删除失败");
    }

    @ApiOperation(value = "根据角色id查询菜单id")
    @GetMapping("/menuIdByRoleId/{rid}")
    public Result getMenuIdByRoleId(@PathVariable String rid){
        List<String> mids = ddRoleMenuService.list(new QueryWrapper<DdRoleMenu>().eq("rid", rid))
                .stream().map(DdRoleMenu::getMid).collect(Collectors.toList());
        if(mids.isEmpty()){
            return Result.error().message("此用户还没有分配菜单");
        }
        return Result.ok().data("menuId",mids);
    }

//    @ApiOperation(value = "根据角色id查询菜单")
//    @GetMapping("/menuByRoleId/{rid}")
//    public Result getMenuByRoleId(@PathVariable String rid){
//        List<DdMenu> list = ddMenuService.getMenuByRoleId(rid);
//        if(list.isEmpty()){
//            return Result.error().message("没有菜单！！");
//        }
//        return Result.ok().data("menuList",list);
//    }

    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/roleAndMenu")
    public Result updateMenuRole(String rid,String[] mids){
        return ddMenuService.updateMenuRole(rid,mids);
    }
}


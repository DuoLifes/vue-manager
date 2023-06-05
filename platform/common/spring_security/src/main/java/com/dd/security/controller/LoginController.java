package com.dd.security.controller;

import com.dd.common_utils.Result;
import com.dd.security.entity.DdRole;
import com.dd.security.entity.DdUser;
import com.dd.security.entity.UserLogin;
import com.dd.security.service.DdRoleService;
import com.dd.security.service.DdUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@Api(tags = "LoginController,登录")
public class LoginController {

    @Autowired
    private DdUserService ddUserService;

    @Autowired
    private DdRoleService ddRoleService;
    @ApiOperation(value = "登录之后返回token,如果想要带着token请求，需要添加Bearer前缀，Bearer token，中间空格分隔")
    @PostMapping("/login")
    public Result login(@RequestBody UserLogin userLogin, HttpServletRequest request){
        if(userLogin == null){
            Result.error().message("请输入用户名和密码");
        }
        if(userLogin.getUsername()==null){
            Result.error().message("请输入用户名");
        }
        if(userLogin.getPassword()==null){
            Result.error().message("请输入密码");
        }
        if(userLogin.getCode()==null){
            Result.error().message("请输入验证码");
        }
        return ddUserService.login(userLogin,request);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/login/info")
    public Result loginInfo(@ApiParam(value ="全局对象，security将信息设置到全局，通过这个就可以获取")
                            Principal principal){
        if(principal == null){
            return Result.error().message("用户信息不存在");
        }
        String username = principal.getName();//从全局中获取username
        //根据用户名获取用户对象
        DdUser ddUser =  ddUserService.getLoginInfoByUsername(username);
        if(ddUser == null){
            return Result.error().message("用户信息不存在");
        }
        List<DdRole> rolesByUserId = ddRoleService.getRolesByUserId(ddUser.getId());
        ddUser.setRoles(rolesByUserId);
        ddUser.setPassword(null);//无论如何都不可以返回密码
        return Result.ok().message("用户信息获取成功").data("loginInfo",ddUser);
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public Result logout(){
        //这里省略了退出登录逻辑，比如销毁token，销毁数据库，redis中缓存等，因为没有引入redis，所以直接返回退出成功
        return Result.ok().message("退出登录");
    }

    /**
     * 用来测试ROLE_admin角色无法通行的url
     * @return
     */
    @GetMapping("/testSecurityUrl/a")
    public Result a(){
        return Result.ok();
    }
}

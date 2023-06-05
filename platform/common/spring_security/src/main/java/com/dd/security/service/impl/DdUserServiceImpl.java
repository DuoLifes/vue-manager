package com.dd.security.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dd.common_utils.Result;
import com.dd.security.entity.*;

import com.dd.security.mapper.DdUserMapper;
import com.dd.security.mapper.DdUserRoleMapper;
import com.dd.security.service.DdUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.security.utils.JwtTokenUtil;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class DdUserServiceImpl extends ServiceImpl<DdUserMapper, DdUser> implements DdUserService {

    @Autowired
    private DdUserMapper ddUserMapper;

    @Autowired
    private UserDetailsService userDetailsService;//spring security UserDetailsService,需要我们自己实现它

    @Autowired
    private PasswordEncoder passwordEncoder;//PasswordEncoder，需要我们自己配置出来

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private DdUserRoleMapper ddUserRoleMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 登录之后返回token
     */
    @Override
    public Result login(UserLogin userLogin, HttpServletRequest request) {
        //从session中获取验证码
//        String captcha = (String) request.getSession().getAttribute("captcha");
        //从redis中获取验证码
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        String remoteAddr = request.getRemoteAddr();
        String captcha = (String)valueOperations.get(remoteAddr + "captcha");
        //如果验证码为空，或者验证码和session中验证码不一致（忽略大小写比较），返回错误
//        if(StringUtils.isEmpty(captcha)||!captcha.equalsIgnoreCase(userLogin.getCode())){
//            return Result.error().message("验证码错误！！！");
//        }
        //获取userDetails对象，这里我们userDetailsServiceImpl实现的是，它通过用户名查询了数据库的密码
        UserDetails userDetails = userDetailsService.loadUserByUsername(userLogin.getUsername());
        //如果用户为空，
        if(userDetails==null){
            return Result.error().message("用户不存在");
        }
        //密码匹配失败
        if(!passwordEncoder.matches(userLogin.getPassword(),userDetails.getPassword())){
            return Result.error().message("密码错误");
        }
        //下面这个因为我们DdUser将isEnabled写死为true，所以就不写这些判断了，其实都应该写成属性，存储到数据库
//        if(!userDetails.isEnabled()){
//            return Result.error().message("用户被禁言，请联系管理员");
//        }

        //更新登录用户对象UserDetails 如果获取对象成功，没有错误，那么使用Spring Security我们需要将对象设置到UserDetails中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);//将其放在Security全局中
        //生成token，返回
        String jwtToken = jwtTokenUtil.getJwtToken(userDetails);
        return Result.ok().message("登录成功，获取token")
                .data("token",jwtToken)//token字符串
                .data("tokenHead",tokenHead);//tokenheader，key
    }

    /**
     * 根据用户名获取登录对象
     */
    @Override
    public DdUser getLoginInfoByUsername(String username) {
        DdUser ddUser = ddUserMapper.selectOne(new QueryWrapper<DdUser>().eq("username", username));
        if(ddUser == null){
            return null;
        }
        return ddUser;
    }

    /**
     * 获取所有操作员
     */
    @Override
    public List<DdUser> getAllUser(DdUser ddUser) {
        return ddUserMapper.getAllUser(ddUser);
    }

    /**
     * 更新用户拥有的角色
     * @param uid 用户id
     * @param rids 用户要修改的角色id
     */
    @Override
    @Transactional//自己写更新的操作，一定要加事务
    public Result updateUserRole(String uid, String[] rids) {
        //先全删了，否则得一次次判断，太费资源
        ddUserRoleMapper.delete(new QueryWrapper<DdUserRole>().eq("uid", uid));
        //要更新的菜单id为空，则直接返回
        if(null == rids||rids.length == 0){
            return Result.ok().message("更新用户角色成功");
        }
        //如果传了菜单id过来，则更新
        ddUserMapper.insertRecord(uid,rids);
        return Result.ok().message("更新菜单成功");
    }


}

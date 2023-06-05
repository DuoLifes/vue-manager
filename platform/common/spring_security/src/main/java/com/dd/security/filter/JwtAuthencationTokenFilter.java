package com.dd.security.filter;

import com.dd.security.entity.DdUser;
import com.dd.security.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * JWT登录授权过滤器，继承OncePerRequestFilter
 */
public class JwtAuthencationTokenFilter extends OncePerRequestFilter {


    @Value("${jwt.tokenHeader}")
    private String tokenHeader;//JWT存储的请求头

    @Value("${jwt.tokenHead}")
    private String tokenHead;//JWT负载中拿到的开头，token字符串，tokenHead是字符串的头，也就是以它开头
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 前置拦截
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Enumeration<String> headerNames = request.getHeaderNames();//debug用的
        //通过tokenHeader获取验证头,也就是tokenHead+token字符串
        String authHeader = request.getHeader(tokenHeader);

        //如果验证头存在，并且token字符串符合我们规定的tokenHead开头
        if(authHeader!=null && authHeader.startsWith(tokenHead)){
            //将token截取出来
            String authToken = authHeader.substring(tokenHead.length());
            //根据token获取用户名
            String username = jwtTokenUtil.getUsernameByToken(authToken);

            //如果token存在，但是Security全局上下文没有用户，表示用户没有登录
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                //登录
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //如果token有效
                if(jwtTokenUtil.checkToken(authToken,userDetails)){
                    //更新登录用户对象UserDetails
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    //重新设置到用户对象中
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //设置到security全局上下文
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(request,response);//放行
    }
}

package com.dd.security.filter;

import com.dd.security.entity.DdMenu;
import com.dd.security.entity.DdRole;
import com.dd.security.service.DdMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * 权限控制
 * 根据请求url分析所需角色
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private DdMenuService ddMenuService;


    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取FilterInvocation对象
        FilterInvocation filterInvocation = (FilterInvocation) o;
        //获取请求url
        String requestUrl = filterInvocation.getRequestUrl();
        //获取到每个菜单的对应角色，就是这个url，哪些角色有权限
        List<DdMenu> menusWithRole = ddMenuService.getMenusWithRole();
        //遍历菜单
        for (DdMenu m : menusWithRole){
            //如果用户请求url和菜单中url匹配
            if(m.getUrl()!=null && antPathMatcher.match(m.getUrl(),requestUrl)){
                //流式编程+lambda表达式：类名::方法名 方法引用，DdRole::getName 表示引用DdRole类的getName方法
                //类名::new 构造方法引用，String[]::new 表示引用String[]的构造方法，构造一个数组
                //stream().map(),可以将遍历到的每个值，做相应转换，stream().map(DdRole::getName),将每个值转换为角色name字符串
                //获取角色列表，也就是说，用户请求的url，需要具备以下角色
                String[] roles = m.getRoles().stream().map(DdRole::getName).toArray(String[]::new);

                //org.springframework.security.access.SecurityConfig;
                //将我们角色放到Security中
                return SecurityConfig.createList(roles);
            }
        }
        //如果用户请求url不是菜单中url，给予默认角色ROLE_LOGIN(登录角色)，也就是必须拥有登录角色才能访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}

package com.dd.security.filter;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * 权限控制
 * 判断用户角色
 */
import java.util.Collection;

@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {
    /**
     *
     * @param authentication 当前访问用户
     * @param o
     * @param collection Collection<ConfigAttribute> 我们在上一个过滤器CustomFilter implements FilterInvocationSecurityMetadataSource中配置了
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {

        //遍历ConfigAttribute
        for(ConfigAttribute configAttribute : collection){
            //获取当前访问url，需要的角色权限,这些值在再CustomFilter中设置进去的
            String needRole = configAttribute.getAttribute();
            //判断url是否登录即可访问，再CustomFilter 中设置
            if("ROLE_LOGIN".equals(needRole)){
                //如果当前用户是匿名用户（未登录用户）,抛异常，让用户登录
                if(authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("尚未登录，请登录！！！");
                }else{
                    return;
                }
            }
            //如果当前url，不是登录就可以访问的
            //判断当前用户的GrantedAuthority里面有没有需要的角色，如果有就放行
            //如果用户没有相应角色，就不放行，同时：没登录的用户因为没有角色，也不会被放行
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for(GrantedAuthority authority:authorities){
                if(authority.getAuthority().equals(needRole)||authority.getAuthority().equals("ROLE_admin")){
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足，请联系管理员!!!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}

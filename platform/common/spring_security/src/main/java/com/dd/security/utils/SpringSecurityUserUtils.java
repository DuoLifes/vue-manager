package com.dd.security.utils;

import com.dd.security.entity.DdUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityUserUtils {
    /**
     * 获取SpringSecurity全局上下文中的user对象
     * @return
     */
    public static DdUser getSpringSecurityUser(){
        return (DdUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

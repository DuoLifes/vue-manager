package com.dd.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DdUser对象,使用Spring Security框架就要继承UserDetails接口，实现方法，将返回值改为true", description="")
public class DdUser implements UserDetails {
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id")
//    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;
    private String username;
    private String password;

    @ApiModelProperty(value = "用户角色")
    @TableField(exist = false)
    private List<DdRole> roles;
    /**
     * 所有权限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(roles!=null){
            List<SimpleGrantedAuthority> authorities =
                    roles.stream()
                            //将每一个角色，遍历成Security指定的权限字符对象，
                            // 比如ROLE_admin要封装成new SimpleGrantedAuthority("ROLE_admin")
                            .map(role -> {
                                if(role.getName()==null)
                                    return null;
                                else
                                    return new SimpleGrantedAuthority(role.getName());
                            })
                            .collect(Collectors.toList());//然后返回为list
            return authorities;//将角色权限返回
        }else{
            return new ArrayList<SimpleGrantedAuthority>();
        }
    }

    /**
     * 账号是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否被锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 凭证（密码）是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否启用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

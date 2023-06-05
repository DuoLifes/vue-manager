package com.dd.security.service;

import com.dd.security.entity.DdRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-12-13
 */
public interface DdRoleService extends IService<DdRole> {

    /**
     * 根据用户id获取用户拥有角色
     */
    List<DdRole> getRolesByUserId(String userId);

}

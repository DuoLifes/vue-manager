package com.dd.security.service.impl;

import com.dd.security.entity.DdRole;
import com.dd.security.mapper.DdRoleMapper;
import com.dd.security.service.DdRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-12-13
 */
@Service
public class DdRoleServiceImpl extends ServiceImpl<DdRoleMapper, DdRole> implements DdRoleService {

    @Autowired
    private DdRoleMapper ddRoleMapper;
    /**
     * 根据用户id获取用户拥有角色
     */
    @Override
    public List<DdRole> getRolesByUserId(String userId) {
        return ddRoleMapper.getRolesByUserId(userId);
    }
}

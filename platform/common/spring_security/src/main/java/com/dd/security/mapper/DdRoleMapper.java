package com.dd.security.mapper;

import com.dd.security.entity.DdRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-12-13
 */
@Mapper
public interface DdRoleMapper extends BaseMapper<DdRole> {

    /**
     * 根据用户id获取用户拥有角色
     */
    List<DdRole> getRolesByUserId(String userId);
}

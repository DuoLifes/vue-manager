package com.dd.security.mapper;

import com.dd.security.entity.DdMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-12-14
 */
public interface DdMenuMapper extends BaseMapper<DdMenu> {

    List<DdMenu> getMenusByUserId(String id);

    /**
     * 根据角色获取菜单列表
     */
    List<DdMenu> getMenusWithRole();

    List<DdMenu> getMenuByRoleId(String rid);
    /**
     * 更新角色菜单
     * 先将当前角色所有菜单都删除，然后再更新
     * @param rid 角色id
     * @param mids 要更新的菜单id
     */
    Integer insertRecord(String rid, String[] mids);
}

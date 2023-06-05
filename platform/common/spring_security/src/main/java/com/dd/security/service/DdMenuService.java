package com.dd.security.service;

import com.dd.common_utils.Result;
import com.dd.security.entity.DdMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-12-14
 */
public interface DdMenuService extends IService<DdMenu> {

    /**
     * 根据用户id获取菜单
     */
    List<DdMenu> getMenusByUserId();
    /**
     * 根据角色获取菜单列表
     */
    List<DdMenu> getMenusWithRole();

    List<DdMenu> getMenuByRoleId(String rid);

    //更新角色菜单
    Result updateMenuRole(String rid, String[] mids);
}

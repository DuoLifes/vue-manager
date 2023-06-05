package com.dd.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dd.common_utils.Result;
import com.dd.security.entity.DdMenu;
import com.dd.security.entity.DdRoleMenu;
import com.dd.security.entity.DdUser;
import com.dd.security.mapper.DdMenuMapper;
import com.dd.security.mapper.DdRoleMenuMapper;
import com.dd.security.service.DdMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.security.service.DdRoleMenuService;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-12-14
 */
@Service
public class DdMenuServiceImpl extends ServiceImpl<DdMenuMapper, DdMenu> implements DdMenuService {

    @Autowired
    private DdMenuMapper ddMenuMapper;
    @Autowired
    private DdRoleMenuMapper ddRoleMenuMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据用户id获取菜单
     */
    @Override
    public List<DdMenu> getMenusByUserId() {
        //Security全局上下文获取UserDetails对象
        DdUser user = (DdUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //获取redis对象
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //先从redis中查询
        List<DdMenu> menus = (List<DdMenu>) valueOperations.get("menu_" + user.getId());
        //如果redis中没有，查询mysql数据库
        if(CollectionUtils.isEmpty(menus)){
            menus = ddMenuMapper.getMenusByUserId(user.getId());
            //将查询出来的内容放在reids中
            valueOperations.set("menu_" + user.getId(),menus,30, TimeUnit.MILLISECONDS);
        }
        //返回结果
        return menus;
    }

    /**
     * 根据角色获取菜单列表
     */
    @Override
    public List<DdMenu> getMenusWithRole() {
        return ddMenuMapper.getMenusWithRole();
    }

    @Override
    public List<DdMenu> getMenuByRoleId(String rid) {
        return ddMenuMapper.getMenuByRoleId(rid);
    }

    /**
     * 更新角色菜单
     * 先将当前角色所有菜单都删除，然后再更新
     * @param rid 角色id
     * @param mids 要更新的菜单id
     */
    @Override
    @Transactional//自己写更新的操作，一定要加事务
    public Result updateMenuRole(String rid, String[] mids) {
        //先全删了，否则得一次次判断，太费资源
        ddRoleMenuMapper.delete(new QueryWrapper<DdRoleMenu>().eq("rid", rid));
        //要更新的菜单id为空，则直接返回
        if(null == mids||mids.length == 0){
            return Result.ok().message("更新菜单成功");
        }
        //如果传了菜单id过来，则更新
        ddMenuMapper.insertRecord(rid,mids);

        return Result.ok().message("更新菜单成功");
    }
}

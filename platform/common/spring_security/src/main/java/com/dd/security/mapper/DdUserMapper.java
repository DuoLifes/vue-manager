package com.dd.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.security.entity.DdUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DdUserMapper extends BaseMapper<DdUser>  {

    public DdUser selectByUsername(String username);

    List<DdUser> getAllUser(DdUser ddUser);

    /**
     * 更新用户拥有的角色
     * @param uid 用户id
     * @param rids 用户要修改的角色id
     */
    Integer insertRecord(String uid, String[] rids);
}

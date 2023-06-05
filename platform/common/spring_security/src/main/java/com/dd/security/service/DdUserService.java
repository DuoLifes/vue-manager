package com.dd.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.common_utils.Result;
import com.dd.security.entity.DdMenu;
import com.dd.security.entity.DdUser;
import com.dd.security.entity.UserLogin;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DdUserService extends IService<DdUser> {

    //登录之后返回token
    Result login(UserLogin userLogin, HttpServletRequest request);

    //根据用户名获取登录对象
    DdUser getLoginInfoByUsername(String username);

    /**
     * 获取所有操作员
     */
    List<DdUser> getAllUser(DdUser ddUser);

    Result updateUserRole(String uid, String[] rids);
}

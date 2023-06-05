package com.dd.service_animation.service;

import com.dd.service_animation.entity.Animation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-12-08
 */
public interface AnimationService extends IService<Animation> {

    List<Animation> selectByTapId(String tId);

    List<Animation> selectByRecommend();
}

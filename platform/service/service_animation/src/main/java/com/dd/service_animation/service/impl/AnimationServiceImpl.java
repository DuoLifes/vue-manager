package com.dd.service_animation.service.impl;

import com.dd.service_animation.entity.Animation;
import com.dd.service_animation.mapper.AnimationMapper;
import com.dd.service_animation.service.AnimationService;
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
 * @since 2021-12-08
 */
@Service
public class AnimationServiceImpl extends ServiceImpl<AnimationMapper, Animation> implements AnimationService {

    @Autowired
    private AnimationMapper animationMapper;
    /**
     * 根据tId 获取 所属标签的 课程列表
     * @param tId 标签id
     * @return
     */
    @Override
    public List<Animation> selectByTapId(String tId) {
        List<Animation> animations = animationMapper.selectByTapId(tId);
        return animations;
    }

    @Override
    public List<Animation> selectByRecommend() {
        List<Animation> animations = animationMapper.selectByRecommend();
        return animations;
    }
}

package com.dd.service_animation.service.impl;

import com.dd.service_animation.entity.Tag;
import com.dd.service_animation.mapper.TagMapper;
import com.dd.service_animation.service.TagService;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;
    @Override
    public List<Tag> selectByAnimationId(String aId) {
        List<Tag> list = tagMapper.selectByAnimationId(aId);
        return list;
    }
}

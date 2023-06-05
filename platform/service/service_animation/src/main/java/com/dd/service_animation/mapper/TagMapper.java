package com.dd.service_animation.mapper;

import com.dd.service_animation.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-12-08
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> selectByAnimationId(String aId);
}

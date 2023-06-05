package com.dd.service_animation.mapper;

import com.dd.service_animation.entity.Animation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

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
public interface AnimationMapper extends BaseMapper<Animation> {

    List<Animation> selectByTapId(String tId);

    List<Animation> selectByRecommend();
}

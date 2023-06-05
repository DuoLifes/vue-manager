package com.dd.service_animation.controller;


import com.dd.common_utils.Result;
import com.dd.service_animation.entity.Animation;
import com.dd.service_animation.service.AnimationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-08
 */
@RestController
@RequestMapping("/service_animation/animation")
public class AnimationController {

    @Autowired
    private AnimationService animationService;

    @GetMapping("selectAll")
    public Result selectAll(){
        List<Animation> list = animationService.list();
        return Result.ok().data("animationList",list);
    }

    @GetMapping("selectById/{id}")
    public Result selectById(@PathVariable(name = "id") String id){
        Animation byId = animationService.getById(id);
        return Result.ok().data("animationById",byId);
    }

    @ApiOperation("根据标签id获取所有属于此标签的动画")
    @GetMapping("selectByTapId/{tId}")
    public Result selectByTapId(@ApiParam(name="tId",value = "标签id")
                                @PathVariable("tId") String tId){
        List<Animation> list = animationService.selectByTapId(tId);
        return Result.ok().data("animationList",list);
    }

    @ApiOperation("根据推荐表查询推荐课程")
    @GetMapping("selectByRecommend")
    public Result selectByRecommend(){
        List<Animation> list = animationService.selectByRecommend();
        return Result.ok().data("animationList",list);
    }
}


package com.dd.service_animation.controller;


import com.dd.common_utils.Result;
import com.dd.service_animation.entity.Tag;
import com.dd.service_animation.service.TagService;
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
@RequestMapping("/service_animation/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("selectAll")
    public Result selectAll(){
        List<Tag> list = tagService.list();
        return Result.ok().data("TapList",list);
    }

    @ApiOperation("根据动画id获取所有动画所属标签")
    @GetMapping("selectByAnimationId/{aId}")
    public Result selectByAnimationId(@ApiParam(name = "aId",value = "动画id")
                                      @PathVariable(name = "aId") String aId){
        List<Tag> list = tagService.selectByAnimationId(aId);
        return Result.ok().data("tagList",list);
    }
}


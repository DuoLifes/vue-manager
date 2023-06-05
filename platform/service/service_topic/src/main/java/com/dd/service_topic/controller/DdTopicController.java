package com.dd.service_topic.controller;


import com.dd.common_utils.Result;
import com.dd.service_base.handler.CustomExceptionHandler;
import com.dd.service_topic.entity.DdTopic;
import com.dd.service_topic.entity.insert.DdTopicInsert;
import com.dd.service_topic.service.DdTopicService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/service_topic/dd-topic")
public class DdTopicController {

    @Autowired
    private DdTopicService ddTopicService;

    @GetMapping("selectALl")
    public Result selectAll(){
        List<DdTopic> list = ddTopicService.list();
        return Result.ok().data("list",list);
    }

    @ApiOperation("根据id获取话题")
    @GetMapping("selectById/{id}")
    public Result selectById(@ApiParam(name="id",value = "话题id")
                                 @PathVariable(name = "id") Long id){
        DdTopic byId = ddTopicService.getById(id);
        return Result.ok().data("byId",byId);
    }

    @ApiOperation("添加话题")
    @PostMapping("insertTopic")
    public Result insertBlog(   @ApiParam(name = "blog",value = "博客信息")
                                @Validated @RequestBody DdTopicInsert topicInsert) {
        DdTopic ddTopic = new DdTopic();

        BeanUtils.copyProperties(topicInsert,ddTopic);//把topicInsert的属性给ddTopic
        //通用mapper
        boolean save = ddTopicService.save(ddTopic);
        if(save){
            return Result.ok().message("保存成功");
        }
        throw new CustomExceptionHandler(20001,"保存失败");
    }
}


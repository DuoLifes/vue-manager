package com.dd.service_animation.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author testjava
 * @since 2021-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Animation对象", description="")
public class Animation implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "动画id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "动画名称")
    private String name;

    @ApiModelProperty(value = "预览图")
    private String picture;

    @ApiModelProperty(value = "开播时间")
    private String publishT;

    @ApiModelProperty(value = "所属国家")
    private String region;

    @ApiModelProperty(value = "播放渠道")
    private String playD;

    @ApiModelProperty(value = "集数")
    private Integer number;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date creationT;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date changeT;


}

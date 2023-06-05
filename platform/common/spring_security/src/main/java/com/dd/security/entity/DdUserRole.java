package com.dd.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DdUserRole对象", description="")
public class DdUserRole implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户id")
    private String uid;

    @ApiModelProperty(value = "角色id")
    private String rid;


}

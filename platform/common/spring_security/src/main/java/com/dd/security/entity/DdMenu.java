package com.dd.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

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
 * @since 2021-12-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DdMenu对象", description="")
public class DdMenu implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "父菜单id")
    private String pid;

    @ApiModelProperty(value = "菜单名")
    private String name;

    @ApiModelProperty(value = "菜单类型（目录，按钮）")
    private String type;

    @ApiModelProperty(value = "菜单权限")
    private String permission;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "path")
    private String path;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "图标")
    @TableField("iconCls")
    private String iconCls;

    @ApiModelProperty(value = "是否保持激活")
    @TableField("keepAlive")
    private Boolean keepAlive;

    @ApiModelProperty(value = "是否要求权限")
    @TableField("requireAuth")
    private Boolean requireAuth;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @ApiModelProperty(value = "子菜单")
    @TableField(exist = false)//告诉Mybatis，表中没有这个字段，否则操作时会去数据库找这个字段
    private List<DdMenu> children;

    @ApiModelProperty(value = "角色列表")
    @TableField(exist = false)
    private List<DdRole> roles;
}

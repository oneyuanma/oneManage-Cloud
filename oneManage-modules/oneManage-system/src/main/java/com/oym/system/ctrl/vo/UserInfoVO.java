package com.oym.system.ctrl.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 用户信息
 *
 * @author oneyuanma
 * @date 2021/06/23
 */
@Data
@ApiModel(description = "系统用户信息VO")
public class UserInfoVO implements Serializable {

    private static final long serialVersionUID = 3908190591048076552L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "角色编码集合")
    private Set<String> roles;

    @ApiModelProperty(value = "角色名称")
    private String roleNames;

    @ApiModelProperty(value = "权限集合")
    private Set<String> permissions;

    @ApiModelProperty(value = "拥有权限的菜单集合")
    private List<ResourceTreeVO> menus;

    @ApiModelProperty(value = "上次登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "上次登录ip")
    private String lastLoginIp;
}

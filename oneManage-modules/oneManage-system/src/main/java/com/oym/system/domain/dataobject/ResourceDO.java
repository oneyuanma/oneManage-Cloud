package com.oym.system.domain.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oym.commons.base.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 系统权限资源实体类
 *
 * @author oneyuanma
 * @date 2021/06/29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_resource")
public class ResourceDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = 6936283373806075036L;

    /**
     * 上级资源ID
     */
    private Long parentId;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 后台请求路径
     */
    private String url;
    /**
     * 前端组件路径
     */
    private String component;
    /**
     * 资源编码,权限标识
     */
    private String code;
    /**
     * 标题
     */
    private String title;
    /**
     * 资源层级
     */
    private Integer level;
    /**
     * 排序
     */
    private Integer sortNo;
    /**
     * 图标
     */
    private String icon;
    /**
     * 类型 menu : 菜单、button ：按钮
     */
    private String type;
}

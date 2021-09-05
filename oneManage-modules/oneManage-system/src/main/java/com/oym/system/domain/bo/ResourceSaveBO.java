package com.oym.system.domain.bo;

import com.oym.commons.base.request.AbstractQuery;
import lombok.Data;

/**
 * 资源权限保存 BO
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
public class ResourceSaveBO extends AbstractQuery {

    private static final long serialVersionUID = 1883605514721169292L;

    private Long id;

    private Long parentId;

    private String path;

    private String url;

    private String component;

    private String code;

    private String title;

    private Integer level;

    private Integer sortNo;

    private String icon;

    private String type;
}

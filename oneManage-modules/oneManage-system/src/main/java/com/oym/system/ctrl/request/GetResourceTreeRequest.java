package com.oym.system.ctrl.request;

import com.oym.commons.base.request.AbstractQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 获取资源权限树形列表 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "获取资源权限树形列表 Request")
public class GetResourceTreeRequest extends AbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

}

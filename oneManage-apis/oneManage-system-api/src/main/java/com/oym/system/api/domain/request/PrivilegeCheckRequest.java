package com.oym.system.api.domain.request;

import com.oym.commons.base.request.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权限校验request
 *
 * @author oneyuanma
 * @date 2021/06/22
 */
@Data
@ApiModel(description = "权限校验request")
public class PrivilegeCheckRequest extends AbstractQuery {

    @ApiModelProperty(value = "用户id")
    private Long uid;

    @ApiModelProperty(value = "请求url")
    private String url;

    public PrivilegeCheckRequest() {
    }

    public PrivilegeCheckRequest(Long uid, String url) {
        this.uid = uid;
        this.url = url;
    }
}

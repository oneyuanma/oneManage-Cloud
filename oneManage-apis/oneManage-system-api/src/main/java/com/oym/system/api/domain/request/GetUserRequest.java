package com.oym.system.api.domain.request;

import com.oym.commons.base.request.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询用户信息request
 *
 * @author oneyuanma
 * @date 2021/06/22
 */
@Data
@ApiModel(description = "查询用户信息request")
public class GetUserRequest extends AbstractQuery {

    @ApiModelProperty(value = "用户登录名")
    private String username;

    public GetUserRequest() {
    }

    public GetUserRequest(String username) {
        this.username = username;
    }
}

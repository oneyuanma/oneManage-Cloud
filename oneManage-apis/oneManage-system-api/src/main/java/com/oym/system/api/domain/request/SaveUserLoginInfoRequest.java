package com.oym.system.api.domain.request;

import com.oym.commons.base.request.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 保存用户登录信息request
 *
 * @author oneyuanma
 * @date 2021/06/22
 */
@Data
@ApiModel(description = "保存用户登录信息request")
public class SaveUserLoginInfoRequest extends AbstractQuery {

    @ApiModelProperty(value = "用户登录名")
    private Long userId;

    @ApiModelProperty(value = "当前登录ip")
    private String ip;

    public SaveUserLoginInfoRequest() {
    }

    public SaveUserLoginInfoRequest(Long userId, String ip) {
        this.userId = userId;
        this.ip = ip;
    }
}

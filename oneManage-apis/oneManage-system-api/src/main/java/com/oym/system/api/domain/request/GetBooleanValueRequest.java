package com.oym.system.api.domain.request;

import com.oym.commons.base.request.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取布尔类型键值对request
 *
 * @author oneyuanma
 * @date 2021/06/22
 */
@Data
@ApiModel(description = "获取布尔类型键值对request")
public class GetBooleanValueRequest extends AbstractQuery {

    @ApiModelProperty(value = "键")
    private String key;

    @ApiModelProperty(value = "默认值")
    private Boolean defaultValue;

    public GetBooleanValueRequest() {
    }

    public GetBooleanValueRequest(String key) {
        this.key = key;
        this.defaultValue = false;
    }

    public GetBooleanValueRequest(String key, Boolean defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }
}

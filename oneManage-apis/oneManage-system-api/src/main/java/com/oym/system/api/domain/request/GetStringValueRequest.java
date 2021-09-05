package com.oym.system.api.domain.request;

import com.oym.commons.base.request.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取字符串类型键值对request
 *
 * @author oneyuanma
 * @date 2021/06/22
 */
@Data
@ApiModel(description = "获取字符串类型键值对request")
public class GetStringValueRequest extends AbstractQuery {

    @ApiModelProperty(value = "键")
    private String key;

    @ApiModelProperty(value = "默认值")
    private String defaultValue;

    public GetStringValueRequest() {
    }

    public GetStringValueRequest(String key) {
        this.key = key;
        this.defaultValue = "";
    }

    public GetStringValueRequest(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }
}

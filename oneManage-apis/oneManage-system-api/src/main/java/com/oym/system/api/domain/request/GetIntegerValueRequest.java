package com.oym.system.api.domain.request;

import com.oym.commons.base.request.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取整型类型键值对request
 *
 * @author oneyuanma
 * @date 2021/06/22
 */
@Data
@ApiModel(description = "获取整型类型键值对request")
public class GetIntegerValueRequest extends AbstractQuery {

    @ApiModelProperty(value = "键")
    private String key;

    @ApiModelProperty(value = "默认值")
    private Integer defaultValue;

    public GetIntegerValueRequest() {
    }

    public GetIntegerValueRequest(String key) {
        this.key = key;
        this.defaultValue = 0;
    }

    public GetIntegerValueRequest(String key, Integer defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }
}

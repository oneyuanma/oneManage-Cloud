package com.oym.system.api.domain.request;

import com.oym.commons.base.request.AbstractQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取长整型类型键值对request
 *
 * @author oneyuanma
 * @date 2021/06/22
 */
@Data
@ApiModel(description = "获取长整型类型键值对request")
public class GetLongValueRequest extends AbstractQuery {

    @ApiModelProperty(value = "键")
    private String key;

    @ApiModelProperty(value = "默认值")
    private Long defaultValue;

    public GetLongValueRequest() {
    }

    public GetLongValueRequest(String key) {
        this.key = key;
        this.defaultValue = 0L;
    }

    public GetLongValueRequest(String key, Long defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }
}

package com.oym.system.api;

import com.oym.system.api.domain.request.GetBooleanValueRequest;
import com.oym.system.api.domain.request.GetIntegerValueRequest;
import com.oym.system.api.domain.request.GetLongValueRequest;
import com.oym.system.api.domain.request.GetStringValueRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * KV键值对相关API
 *
 * @author oneyuanma
 * @date 2021/07/14
 */
@Api(value = "KV键值对API", tags = {"KV键值对API"})
@FeignClient("oneManage-system")
public interface KvApi {

    /**
     * 获取字符串类型Value, 带有默认值
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取字符串类型Value, 带有默认值")
    @PostMapping(value = "/kv/getStringValue")
    String getStringValue(@RequestBody GetStringValueRequest request);

    /**
     * 获取整型类型Value, 带有默认值
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取整型类型Value, 带有默认值")
    @PostMapping(value = "/kv/getIntegerValue")
    Integer getIntegerValue(@RequestBody GetIntegerValueRequest request);

    /**
     * 获取长整型类型Value, 带有默认值
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取长整型类型Value, 带有默认值")
    @PostMapping(value = "/kv/getLongValue")
    Long getLongValue(@RequestBody GetLongValueRequest request);

    /**
     * 获取布尔类型Value, 带有默认值
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取布尔类型Value, 带有默认值")
    @PostMapping(value = "/kv/getBooleanValue")
    Boolean getBooleanValue(@RequestBody GetBooleanValueRequest request);
}

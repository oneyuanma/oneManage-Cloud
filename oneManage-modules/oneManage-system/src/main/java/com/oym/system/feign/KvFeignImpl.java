package com.oym.system.feign;

import com.oym.commons.utils.Argument;
import com.oym.system.api.KvApi;
import com.oym.system.api.domain.request.GetBooleanValueRequest;
import com.oym.system.api.domain.request.GetIntegerValueRequest;
import com.oym.system.api.domain.request.GetLongValueRequest;
import com.oym.system.api.domain.request.GetStringValueRequest;
import com.oym.system.service.KvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * KV键值对api实现
 *
 * @author oneyuanma
 * @date 2021/06/22
 */
@RestController
public class KvFeignImpl implements KvApi {

    @Autowired
    private KvService kvService;

    /**
     * 获取字符串类型Value, 带有默认值
     *
     * @param request
     * @return
     */
    @Override
    public String getStringValue(GetStringValueRequest request) {
        String value = kvService.getValueByKey(request.getKey());
        return Argument.isBlank(value) ? request.getDefaultValue() : value;
    }

    /**
     * 获取整形类型Value, 带有默认值
     *
     * @param request
     * @return
     */
    @Override
    public Integer getIntegerValue(GetIntegerValueRequest request) {
        String value = kvService.getValueByKey(request.getKey());
        if (Argument.isBlank(value)) {
            return request.getDefaultValue();
        }
        Integer v = request.getDefaultValue();
        try {
            v = Integer.parseInt(value);
        } catch (Exception e) {
            return v;
        }
        return v;
    }

    /**
     * 获取长整形类型Value, 带有默认值
     *
     * @param request
     * @return
     */
    @Override
    public Long getLongValue(GetLongValueRequest request) {
        String value = kvService.getValueByKey(request.getKey());
        if (Argument.isBlank(value)) {
            return request.getDefaultValue();
        }
        Long v = request.getDefaultValue();
        try {
            v = Long.parseLong(value);
        } catch (Exception e) {
            return v;
        }
        return v;
    }

    /**
     * 获取布尔类型Value, 带有默认值
     *
     * @param request
     * @return
     */
    @Override
    public Boolean getBooleanValue(GetBooleanValueRequest request) {
        String value = kvService.getValueByKey(request.getKey());
        if (Argument.isBlank(value)) {
            return request.getDefaultValue();
        }
        Boolean v = request.getDefaultValue();
        try {
            v = Boolean.parseBoolean(value);
        } catch (Exception e) {
            return v;
        }
        return v;
    }

}

package com.oym.gateway.commons.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.oym.commons.base.response.OymResponse;
import com.oym.commons.utils.Argument;
import com.oym.system.api.UserApi;
import com.oym.system.api.domain.request.PrivilegeCheckRequest;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * 权限校验相关工具类
 *
 * @author oenyuanma
 * @date 2021/07/17
 */
@Component
public class PrivilegeCheckUtils {

    @Autowired
    private UserApi privilegeCheckApi;

    private static UserApi PRIVILEGE_CHECK_API;

    private static LoadingCache<CacheKey, Boolean> loadingCache;

    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class CacheKey {
        private Long uid;
        private String key;
    }


    @PostConstruct
    public void init() {
        PRIVILEGE_CHECK_API = privilegeCheckApi;

        loadingCache = CacheBuilder
                .newBuilder()
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .maximumSize(20480)
                .build(new CacheLoader<CacheKey, Boolean>() {
                    @Override
                    @SuppressWarnings("NullableProblems")
                    public Boolean load(CacheKey cacheKey) {
                        return checkPrivilege(cacheKey.getUid(), cacheKey.getKey());
                    }
                });
    }

    public static boolean check(Long uid, String key) {
        try {
            return loadingCache.get(new CacheKey(uid, key));
        } catch (Exception e) {
            return checkPrivilege(uid, key);
        }
    }

    /**
     * 调用api校验uid 和 url
     *
     * @param uid
     * @param key
     * @return
     */
    private static boolean checkPrivilege(Long uid, String key) {
        if (StringUtils.isBlank(key) || Argument.isNotPositive(uid)) {
            return false;
        }

        PrivilegeCheckRequest privilegeCheckRequest = new PrivilegeCheckRequest();
        privilegeCheckRequest.setUrl(key);
        privilegeCheckRequest.setUid(uid);
        OymResponse<Boolean> res = PRIVILEGE_CHECK_API.privilegeCheck(privilegeCheckRequest);
        if (res.isFail()) {
            return false;
        }
        return res.getData();
    }

}

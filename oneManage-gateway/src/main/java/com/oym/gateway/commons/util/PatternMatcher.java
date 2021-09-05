package com.oym.gateway.commons.util;

/**
 * @author oneyuanma
 * @date 2021/09/02
 */
public interface PatternMatcher {

    boolean matches(String pattern, String source);
}

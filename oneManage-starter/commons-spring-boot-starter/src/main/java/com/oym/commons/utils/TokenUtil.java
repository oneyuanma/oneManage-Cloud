package com.oym.commons.utils;

import java.util.UUID;

public class TokenUtil {

    /**
     * 生成token
     *
     * @return String
     */
    public static String generateToken() {

        return UUID.randomUUID().toString();
    }

}

package com.oym.auth.request;

import lombok.Data;


/**
 * @author oneyuanma
 * @date 2021/06/22
 */
@Data
public class LoginRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

}

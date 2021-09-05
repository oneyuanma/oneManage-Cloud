package com.oym.commons.user;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.oym.commons.utils.Argument;
import lombok.Data;
import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;

import java.util.Optional;

/**
 * @author oneyuanma
 * @date 2021/06/23
 */
@Data
public class WebUser {

    private static final long serialVersionUID = 1614900642960014349L;

    private static final ThreadLocal<WebUser> userHolder =
            new NamedThreadLocal<>("WebUser");

    private static final ThreadLocal<WebUser> inheritableUserHolder =
            new NamedInheritableThreadLocal<>("WebUser");

    public static WebUser getCurrentUser() {
        return userHolder.get();
    }

    public static void setCurrentUser(WebUser webUser) {
        userHolder.set(webUser);
    }

    public static void resetWebUser() {
        userHolder.remove();
    }

    /**
     * 登陆类型
     */
    private String loginType;
    /**
     * 唯一标示
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 最后访问时间
     */
    private Long lastAccess;

    /**
     * 当前访问用户的ip
     */
    private String ip;
    /**
     * 当前访问用户的UA
     */
    private String ua;

    /**
     * 当前用户的语言版本
     */
    private String language;

    private String timeZone;

    private String operator;

    /**
     * 每次请求生成的token
     */
    private String token;

    /**
     * 员工头像
     */
    private String avatar;

    /**
     * 手机号码
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 性别
     */
    private String sex;

    /**
     * 是否登陆
     *
     * @return boolean
     */
    public boolean isLogin() {
        return Argument.isNotBlank(username);
    }

    public Optional<String> getInfo() {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(this.getUsername())) {
            sb.append("username=").append(this.getUsername());
            sb.append(";");
        }
        if (StringUtils.isNotBlank(this.getPhone())) {
            sb.append("phone=").append(this.getPhone());
            sb.append(";");
        }
        if (StringUtils.isNotBlank(this.getEmail())) {
            sb.append("email=").append(this.getEmail());
            sb.append(";");
        }
        if (StringUtils.isNotBlank(this.getSex())) {
            sb.append("sex=").append(this.getSex());
            sb.append(";");
        }
        if (Argument.isNotNull(this.getUserId())) {
            sb.append("userId=").append(this.getUserId());
            sb.append(";");
        }
        if (StringUtils.isNotBlank(this.getLanguage())) {
            sb.append("language=").append(this.getLanguage());
            sb.append(";");
        }
        if (Argument.isNotNull(this.getLastAccess())) {
            sb.append("lastAccess=").append(this.getLastAccess());
            sb.append(";");
        }
        if (StringUtils.isNotBlank(this.getLoginType())) {
            sb.append("loginType=").append(this.getLoginType());
            sb.append(";");
        }
        if (StringUtils.isNotBlank(this.getToken())) {
            sb.append("token=").append(this.getToken());
            sb.append(";");
        }
        if (StringUtils.isNotBlank(this.getTimeZone())) {
            sb.append("timeZone=").append(this.getTimeZone());
            sb.append(";");
        }
        if (StringUtils.isNotBlank(this.getOperator())) {
            sb.append("operator=").append(this.getOperator());
            sb.append(";");
        }
        if (StringUtils.isNotBlank(this.getAvatar())) {
            sb.append("avatar=").append(this.getAvatar());
            sb.append(";");
        }
        if (StringUtils.isNotBlank(this.getIp())) {
            sb.append("ip=").append(this.getIp());
            sb.append(";");
        }
        if (StringUtils.isNotBlank(this.getUa())) {
            sb.append("ua=").append(this.getUa());
            sb.append(";");
        }
        if (StringUtils.isNotBlank(this.getNickName())) {
            sb.append("nickName=").append(this.getNickName());
            sb.append(";");
        }
        if (sb.length() > 0) {
            sb = sb.deleteCharAt(sb.length() - 1);
            return Optional.of(sb.toString());
        }
        return null;
    }

    @Override
    public String toString() {
        return "WebUser{" +
                "loginType='" + loginType + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", nickName='" + nickName + '\'' +
                ", lastAccess=" + lastAccess +
                ", ip='" + ip + '\'' +
                ", ua='" + ua + '\'' +
                ", language='" + language + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", operator='" + operator + '\'' +
                ", token='" + token + '\'' +
                ", avatar='" + avatar + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}

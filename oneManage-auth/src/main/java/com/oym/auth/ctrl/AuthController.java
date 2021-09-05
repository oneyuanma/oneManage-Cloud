package com.oym.auth.ctrl;

import cn.hutool.core.util.IdUtil;
import com.oym.auth.cons.LoginStatusEnum;
import com.oym.auth.cons.LoginTypeEnum;
import com.oym.auth.domain.message.LoginMessage;
import com.oym.auth.request.LoginRequest;
import com.oym.auth.transform.LogTransform;
import com.oym.auth.util.IpAndAddrUtil;
import com.oym.commons.base.response.OymResponse;
import com.oym.commons.user.UserLoginInfo;
import com.oym.commons.user.WebUser;
import com.oym.commons.utils.Argument;
import com.oym.kafka.client.KafkaClient;
import com.oym.log.api.LogApi;
import com.oym.redis.client.RedisClient;
import com.oym.system.api.KvApi;
import com.oym.system.api.UserApi;
import com.oym.system.api.domain.request.GetStringValueRequest;
import com.oym.system.api.domain.request.GetUserRequest;
import com.oym.system.api.domain.request.SaveUserLoginInfoRequest;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * 鉴权中心
 *
 * @author oneyuanma
 * @date 2021/06/22
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserApi userApi;

    @Autowired
    private LogApi logApi;

    @Autowired
    private KvApi kvApi;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private KafkaClient kafkaClient;

    /**
     * token无刷新有效时间
     */
    protected static final long TOKEN_VALID_MILLIS_SECOND = 600;

    /**
     * 登录token保存key
     */
    private static final String TOKEN_KEY = "user:token:";

    /**
     * 日志保存方式
     */
    private static final String KAFKA = "kafka";

    /**
     * 登录
     *
     * @param request
     * @return
     */
    @PostMapping("/login")
    public OymResponse<String> login(@RequestBody LoginRequest request, HttpServletRequest req) {

        if (Argument.isBlank(request.getUsername()) || Argument.isBlank(request.getPassword())) {
            return OymResponse.error("用户名或者密码不能为空");
        }

        OymResponse<UserLoginInfo> response = userApi.getUserByName(new GetUserRequest(request.getUsername()));

        if (response.isFail()) {
            return OymResponse.error(response.getMsg());
        }

        UserLoginInfo userInfo = response.getData();

        // 校验密码是否正确
        boolean b = DigestUtils.md5DigestAsHex(request.getPassword().getBytes()).equals(userInfo.getPassword());
        if (!b) {
            return OymResponse.error("密码不正确");
        }

        // 生成token,并设置有效期
        String token = getTokenKey(IdUtil.objectId());
        userInfo.setToken(token);
        redisClient.set(token, userInfo, TOKEN_VALID_MILLIS_SECOND);

        // 保存用户登录信息
        userApi.saveLoginInfo(new SaveUserLoginInfoRequest(userInfo.getId(), IpAndAddrUtil.getIp(req)));

        // 保存登录日志
        saveLog(userInfo.getId(), userInfo.getUsername(), LoginTypeEnum.LOGIN.getValue(), "登录系统", "登录成功", req);

        return OymResponse.success(token);
    }

    /**
     * 登出
     *
     * @return
     */
    @PostMapping("/logOut")
    public OymResponse<String> logOut(HttpServletRequest req) {

        // 清除token
        WebUser webUser = WebUser.getCurrentUser();
        redisClient.del(webUser.getToken());

        // 保存登出日志
        saveLog(webUser.getUserId(), webUser.getUsername(), LoginTypeEnum.LOGOUT.getValue(), "退出系统", "退出成功", req);

        return OymResponse.success();
    }

    /**
     * 保存日志
     *
     * @param uid
     * @param userName
     * @param type
     * @param req
     */
    private void saveLog(Long uid, String userName, String type, String title, String remark, HttpServletRequest req) {
        try {
            // 发送kafka消息，记录登录日志
            LoginMessage loginMessage = new LoginMessage();
            loginMessage.setUid(uid);
            loginMessage.setUserName(userName);
            loginMessage.setTime(new Date());
            loginMessage.setTitle(title);
            loginMessage.setRemark(remark);
            loginMessage.setType(type);
            loginMessage.setStatus(LoginStatusEnum.SUCCESS.getValue());
            loginMessage.setIp(IpAndAddrUtil.getIp(req));
            loginMessage.setOperateSystem(IpAndAddrUtil.getOsName(req));
            loginMessage.setBrowser(IpAndAddrUtil.getBrowserName(req));

            /**
             * 登录日志保存，这边提供了两种方式
             * 1.通过kafka消息把登录日志存入ES
             * 2.通过feign调用讲登录日志存入数据库
             * 具体采用哪种由系统KV控制
             */
            String value = kvApi.getStringValue(new GetStringValueRequest("login_log_type", "kafka"));
            if (KAFKA.equals(value)) {
                // 通过kafka消息把登录日志存入ES
                kafkaClient.send("login_log_topic", loginMessage);
            } else {
                // 通过feign调用讲登录日志存入数据库
                logApi.loginLogSave(LogTransform.INS.transfer(loginMessage));
            }
        }catch (Exception e){
            logger.error("AuthController saveLog err, msg:{}", ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * token 拼装
     *
     * @param token
     * @return
     */
    private String getTokenKey(String token) {
        return TOKEN_KEY + token;
    }
}

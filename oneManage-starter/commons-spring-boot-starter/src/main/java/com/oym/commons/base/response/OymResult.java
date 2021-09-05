package com.oym.commons.base.response;

import com.oym.commons.cons.ReturnCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author oneyuanma
 * @Date 2021-06-17
 * @Description 内部方法调用通用包装
 */
@Data
public class OymResult<D> implements Serializable {

    private static final long serialVersionUID = 3248873533036428466L;

    /**
     * 响应状态码,成功200，失败900
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应对象
     */
    private D data;

    public OymResult() {
    }

    public OymResult(Integer code, String msg, D data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public OymResult(ReturnCode returnCode, D data) {
        this.code = returnCode.getCode();
        this.msg = returnCode.getMsg();
        this.data = data;
    }

    public boolean isSuccess() {
        return ReturnCode.SUCCESS.getCode().equals(this.code);
    }

    public boolean isFail() {
        if (ReturnCode.ERROR.getCode().equals(this.code)) {
            return true;
        }
        String regex = "^3\\d*$";
        if (String.valueOf(this.code).matches(regex)) {
            return true;
        }
        return false;
    }

    public static <D> OymResult<D> success() {
        return new OymResult(ReturnCode.SUCCESS, (Object) null);
    }

    public static <D> OymResult<D> success(Integer code, String msg) {
        return new OymResult(code, (String) msg, null);
    }

    public static <D> OymResult<D> success(D data) {
        return new OymResult(ReturnCode.SUCCESS, data);
    }

    public static <D> OymResult<D> noLogin() {
        return new OymResult(ReturnCode.NO_TOKEN_ERROR, (Object) null);
    }

    public static <D> OymResult<D> noPermission() {
        return new OymResult(ReturnCode.USER_NOPERMISSION_LOCKED_ERROR, (Object) null);
    }

    public static <D> OymResult<D> error() {
        return new OymResult(ReturnCode.ERROR, (Object) null);
    }

    public static <D> OymResult<D> error(ReturnCode returnCode) {
        return new OymResult(returnCode, (Object) null);
    }

    public static <D> OymResult<D> error(String msg) {
        return new OymResult(ReturnCode.ERROR.getCode(), msg, (Object) null);
    }

    public static <D> OymResult<D> error(String msg, D data) {
        return new OymResult(ReturnCode.ERROR.getCode(), msg, data);
    }

    public static boolean isSuccessStatus(Integer code) {
        return ReturnCode.SUCCESS.getCode().equals(code);
    }

}

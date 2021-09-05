package com.oym.commons.base.response;

import com.oym.commons.cons.ReturnCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author oneyuanma
 * @Date 2021-06-17
 * @Description 返回给前台的通用包装
 */
@Data
public class OymResponse<D> implements Serializable {

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

    public OymResponse() {
    }

    public OymResponse(Integer code, String msg, D data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public OymResponse(ReturnCode returnCode, D data) {
        this.code = returnCode.getCode();
        this.msg = returnCode.getMsg();
        this.data = data;
    }

    public boolean isSuccess() {
        return ReturnCode.SUCCESS.getCode().equals(this.code);
    }

    public boolean isFail() {
        return ReturnCode.ERROR.getCode().equals(this.code);
    }

    public static <D> OymResponse<D> success() {
        return new OymResponse(ReturnCode.SUCCESS, (Object) null);
    }

    public static <D> OymResponse<D> success(Integer code, String msg) {
        return new OymResponse(code, (String) msg, null);
    }

    public static <D> OymResponse<D> success(D data) {
        return new OymResponse(ReturnCode.SUCCESS, data);
    }

    public static <D> OymResponse<D> noLogin() {
        return new OymResponse(ReturnCode.NO_TOKEN_ERROR, (Object) null);
    }

    public static <D> OymResponse<D> noPermission() {
        return new OymResponse(ReturnCode.USER_NOPERMISSION_LOCKED_ERROR, (Object) null);
    }

    public static <D> OymResponse<D> error() {
        return new OymResponse(ReturnCode.ERROR, (Object) null);
    }

    public static <D> OymResponse<D> error(ReturnCode returnCode) {
        return new OymResponse(returnCode, (Object) null);
    }

    public static <D> OymResponse<D> error(String msg) {
        return new OymResponse(ReturnCode.ERROR.getCode(), msg, (Object) null);
    }

    public static <D> OymResponse<D> error(String msg, D data) {
        return new OymResponse(ReturnCode.ERROR.getCode(), msg, data);
    }

    public static boolean isSuccessStatus(Integer code) {
        return ReturnCode.SUCCESS.getCode().equals(code);
    }

}

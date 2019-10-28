package com.zxb.user.response;

import lombok.Data;

import java.io.Serializable;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-27 16:58
 */
@Data
public class Response<T> implements Serializable {

    public static final Response USERNAME_PASSWORD_INVALID = new Response("10001", "用户密码无效");
    public static final Response MOBILE_OR_EMAIL_REQUIRED = new Response("10002", "手机或邮箱至少有一项");
    public static final Response SEND_VERIFY_CODE_FAILED = new Response("10003", "发送验证码失败");
    public static final Response VERIFY_CODE_INVALID = new Response("10004", "验证码不正确");
    private String code;
    private String message;
    private T data;

    public Response() {
        this.code = "0";
        this.message = "success";
    }

    public Response(T data) {
        this.code = "0";
        this.message = "success";
        this.data = data;
    }

    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Response exception(Exception e) {
        return new Response("9999", e.getMessage());
    }

    public static Response success() {
        return new Response();
    }

    public static <T> Response success(T data) {
        return new Response(data);
    }
}

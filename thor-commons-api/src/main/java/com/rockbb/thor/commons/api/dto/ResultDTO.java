package com.rockbb.thor.commons.api.dto;

import java.io.Serializable;

/**
 * 用于需要带消息的接口返回类型
 *
 * Created by Milton on 2016/2/6 at 13:23.
 */
public class ResultDTO implements Serializable {
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_ERROR = 1;

    private int code;
    private String message;
    private String key;

    public int getCode() { return code; }
    public String getMessage() { return message; }
    public String getKey() { return key; }

    public ResultDTO(int code) {
        this.code = code;
    }
    public ResultDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public ResultDTO(int code, String message, String key) {
        this.code = code;
        this.message = message;
        this.key = key;
    }

    public static ResultDTO success() {
        return new ResultDTO(CODE_SUCCESS);
    }
    public static ResultDTO success(String message) {
        return new ResultDTO(CODE_SUCCESS, message);
    }
    public static ResultDTO success(String message, String key) {
        return new ResultDTO(CODE_SUCCESS, message, key);
    }

    public static ResultDTO error(String message) {
        return new ResultDTO(CODE_ERROR, message);
    }
    public static ResultDTO error(String message, String key) {
        return new ResultDTO(CODE_ERROR, message, key);
    }

    public boolean isFailed() {
        return (code != CODE_SUCCESS);
    }
}

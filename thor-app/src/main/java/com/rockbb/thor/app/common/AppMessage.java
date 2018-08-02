package com.rockbb.thor.app.common;

public class AppMessage {

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_GENERAL_ERROR = 1;

    private int code;
    private String message;
    private String field;

    public AppMessage(int code, String message, String field) {
        this.code = code;
        this.field = field;
        this.message = message;
    }
    public AppMessage(int code, String message) {
        this.code = code;
        this.field = "";
        this.message = message;
    }
    public AppMessage(int code) {
        this.code = code;
        this.field = "";
        this.message = "";
    }

    public int getCode() {
        return code;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
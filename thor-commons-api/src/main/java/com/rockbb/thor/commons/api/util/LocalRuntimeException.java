package com.rockbb.thor.commons.api.util;

public class LocalRuntimeException extends RuntimeException {
    public static final String CODE_GENERIC_ERROR = "0";
    private String code;

    public LocalRuntimeException() {
        super();
        this.code = CODE_GENERIC_ERROR;
    }

    public LocalRuntimeException(String message) {
        super(message);
        this.code = CODE_GENERIC_ERROR;
    }

    public LocalRuntimeException(String message, Throwable cause) {
        super(message, cause);
        this.code = CODE_GENERIC_ERROR;
    }

    public LocalRuntimeException(String message, String code) {
        super(message);
        this.code = code;
    }

    public LocalRuntimeException(String message, String code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() { return code; }
}

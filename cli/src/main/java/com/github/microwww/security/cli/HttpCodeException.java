package com.github.microwww.security.cli;

import java.io.IOException;

public class HttpCodeException extends IOException {
    private int code;
    private String errorMessage;

    public HttpCodeException(String message) {
        super(message);
    }

    public int getCode() {
        return code;
    }

    public HttpCodeException setCode(int code) {
        this.code = code;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpCodeException setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}

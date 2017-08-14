package com.shsxt.crm.exception;

import com.shsxt.crm.constant.Constant;

public class ParamException extends RuntimeException {

    private Integer errorCode;

    public ParamException() {
    }

    public ParamException(String message) {
        this(Constant.ERROR_CODE, message);
    }

    public ParamException(Integer errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}

package me.zeyuan.adapter_nonrestful;

/**
 * This exception represents a business logic error in interaction with server.
 */

public class BusinessException extends RuntimeException {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BusinessException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

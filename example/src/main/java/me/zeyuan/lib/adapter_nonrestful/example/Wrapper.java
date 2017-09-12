package me.zeyuan.lib.adapter_nonrestful.example;


import me.zeyuan.adapter_nonrestful.ResponseWrapper;

public class Wrapper<T> implements ResponseWrapper {

    private String reason;
    private T result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    @Override
    public boolean isOk() {
        return error_code == 0;
    }

    @Override
    public int getCode() {
        return error_code;
    }

    @Override
    public String getMessage() {
        return reason;
    }

    @Override
    public T getData() {
        return result;
    }
}

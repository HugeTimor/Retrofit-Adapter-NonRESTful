package me.zeyuan.adapter_nonrestful;

import com.google.gson.annotations.SerializedName;

public class DefaultResponseWrapper<T> implements ResponseWrapper<T> {
    private int code;
    @SerializedName(value = "msg", alternate = {"message", "errmsg"})
    private String msg;
    @SerializedName(value = "data", alternate = {"result"})
    private T data;

    @Override
    public boolean isOk() {
        return code == 0;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

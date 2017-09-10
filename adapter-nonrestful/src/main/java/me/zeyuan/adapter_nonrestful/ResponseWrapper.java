package me.zeyuan.adapter_nonrestful;


public interface ResponseWrapper<T> {
    boolean isOk();

    int getCode();

    String getMessage();

    T getData();
}

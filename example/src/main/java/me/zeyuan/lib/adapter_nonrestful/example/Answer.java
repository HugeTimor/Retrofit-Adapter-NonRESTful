package me.zeyuan.lib.adapter_nonrestful.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


class Answer {


    /**
     * code : 100000
     * text : 你好rebort
     */

    private int code;
    private String text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}

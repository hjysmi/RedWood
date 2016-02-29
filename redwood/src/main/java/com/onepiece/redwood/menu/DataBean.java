package com.onepiece.redwood.menu;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2015/8/15.
 */
public class DataBean<T> {
    @Expose
    private T data ;
    @Expose
    private Boolean success;
    @Expose
    private Boolean token;

    public DataBean() {
    }

    public DataBean(T data, Boolean success) {
        this.data = data;
        this.success = success;
    }

    public Boolean getToken() {
        return token;
    }

    public void setToken(Boolean token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "data=" + data +
                ", success=" + success +
                ", token=" + token +
                '}';
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}

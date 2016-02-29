package com.onepiece.redwood.listener;

/**
 * Created by Administrator on 2015/9/25.
 */
public interface IErrorView {
    /**
     * 请求失败
     */
    void showRequestError();

    /**
     * 网络连接失败提示
     */
    void showNetError();
}

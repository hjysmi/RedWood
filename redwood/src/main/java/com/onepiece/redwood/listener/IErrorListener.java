package com.onepiece.redwood.listener;

/**
 * Created by Administrator on 2015/9/25.
 */
public interface IErrorListener {
    /**
     * 网络连接失败
     */
    void OnNetError();

    /**
     * 请求失败（success==false）
     */
    void OnRequestFail();
}

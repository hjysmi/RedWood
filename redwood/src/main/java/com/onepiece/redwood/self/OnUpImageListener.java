package com.onepiece.redwood.self;

import com.onepiece.redwood.listener.IErrorListener;
import com.onepiece.redwood.listener.OnReLoginListener;

/**
 * Created by Administrator on 2015/9/9.
 */
public interface OnUpImageListener extends IErrorListener, OnReLoginListener {
    void OnUpImageSuccess();

    @Override
    void OnNetError();

    @Override
    void OnRequestFail();

    @Override
    void onReLogin();
}

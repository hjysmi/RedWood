package com.onepiece.redwood.order.sign;

import com.onepiece.redwood.listener.IErrorListener;
import com.onepiece.redwood.listener.OnReLoginListener;

/**
 * Created by Administrator on 2015/9/18.
 */
public interface OnUpSignImageListener extends IErrorListener,OnReLoginListener {
    void onUpSignImageSuccess();

    @Override
    void OnNetError();

    @Override
    void OnRequestFail();

    @Override
    void onReLogin();
}

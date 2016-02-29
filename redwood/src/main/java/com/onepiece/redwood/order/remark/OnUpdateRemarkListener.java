package com.onepiece.redwood.order.remark;

import com.onepiece.redwood.listener.IErrorListener;
import com.onepiece.redwood.listener.OnReLoginListener;

/**
 * Created by Administrator on 2015/9/21.
 */
public interface OnUpdateRemarkListener extends IErrorListener, OnReLoginListener {

    void OnUpdateRemarkSuccess();

    @Override
    void OnNetError();

    @Override
    void OnRequestFail();

    @Override
    void onReLogin();
}

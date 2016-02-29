package com.onepiece.redwood.order.orderdetail;

import com.onepiece.redwood.listener.IErrorListener;
import com.onepiece.redwood.listener.OnReLoginListener;

/**
 * Created by Administrator on 2015/8/31.
 */
public interface OnOrderDetailListener extends IErrorListener,OnReLoginListener {
    void OnOrderDetailSuccess(OrderDetailBean orderDetailBean);

    @Override
    void OnNetError();

    @Override
    void OnRequestFail();

    @Override
    void onReLogin();
}

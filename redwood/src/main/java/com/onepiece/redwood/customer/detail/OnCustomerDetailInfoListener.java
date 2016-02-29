package com.onepiece.redwood.customer.detail;

import com.onepiece.redwood.customer.CustomerBean;
import com.onepiece.redwood.listener.IErrorListener;
import com.onepiece.redwood.listener.OnReLoginListener;

/**
 * Created by Administrator on 2015/8/26.
 */
public interface OnCustomerDetailInfoListener extends IErrorListener, OnReLoginListener {
    void OnDetailSuccess(CustomerBean customerBean);

    @Override
    void OnNetError();

    @Override
    void OnRequestFail();

    @Override
    void onReLogin();
}

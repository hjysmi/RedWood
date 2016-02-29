package com.onepiece.redwood.customer.create;

import com.onepiece.redwood.customer.CustomerBean;
import com.onepiece.redwood.listener.IErrorListener;
import com.onepiece.redwood.listener.OnReLoginListener;

/**
 * Created by Administrator on 2015/8/24.
 */
public interface OnCreateCustomerListener extends IErrorListener, OnReLoginListener {
    void OnCreateSuccess(CustomerBean customerBean);

    @Override
    void OnNetError();

    @Override
    void OnRequestFail();

    @Override
    void onReLogin();
}

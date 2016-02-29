package com.onepiece.redwood.customer;

import com.onepiece.redwood.listener.IErrorListener;
import com.onepiece.redwood.listener.OnReLoginListener;

import java.util.List;

/**
 * Created by Administrator on 2015/8/23.
 */
public interface OnCustomerFragListener extends IErrorListener, OnReLoginListener {
    void OnSuccess(List<CustomerBean> customerBeanList);

    @Override
    void OnNetError();

    @Override
    void OnRequestFail();

    @Override
    void onReLogin();
}

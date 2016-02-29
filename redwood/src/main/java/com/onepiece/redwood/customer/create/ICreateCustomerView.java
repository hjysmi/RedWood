package com.onepiece.redwood.customer.create;

import com.onepiece.redwood.customer.CustomerBean;
import com.onepiece.redwood.listener.IErrorView;
import com.onepiece.redwood.listener.IReLoginView;

/**
 * Created by Administrator on 2015/8/24.
 */
public interface ICreateCustomerView extends IErrorView,IReLoginView {
    void showBar();

    void hideBar();

    void ShowCreateCustomerSuccess(CustomerBean customerBean);

    void showPendingTradSuccess();

    void showPendingTradFail();

    @Override
    void showNetError();

    @Override
    void showRequestError();

    @Override
    void showReLogin();
}

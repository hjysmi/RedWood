package com.onepiece.redwood.customer;

import com.onepiece.redwood.listener.IErrorListener;
import com.onepiece.redwood.listener.IReLoginView;

import java.util.List;

/**
 * Created by Administrator on 2015/8/23.
 */
public interface ICustomerFragView extends IErrorListener, IReLoginView {
    void showBar();

    void hideBar();

    void showCustomerListSuccess(List<CustomerBean> customerBeanList);


    void showPendingTradSuccess();

    void showPendingTradFail();

    @Override
    void OnNetError();

    @Override
    void OnRequestFail();

    @Override
    void showReLogin();
}

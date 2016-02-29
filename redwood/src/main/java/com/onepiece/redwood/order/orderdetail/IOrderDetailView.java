package com.onepiece.redwood.order.orderdetail;

import com.onepiece.redwood.listener.IErrorView;
import com.onepiece.redwood.listener.IReLoginView;

/**
 * Created by Administrator on 2015/8/31.
 */
public interface IOrderDetailView extends IErrorView,IReLoginView {
    void showBar();
    void hideBar();
    void showOrderDetailSuccess(OrderDetailBean orderDetailBean);

    @Override
    void showNetError();

    @Override
    void showRequestError();

    @Override
    void showReLogin();
}

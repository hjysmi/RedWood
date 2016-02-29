package com.onepiece.redwood.order;

import com.onepiece.redwood.listener.IErrorView;
import com.onepiece.redwood.listener.IReLoginView;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/1.
 */
public interface IOrderView extends IErrorView, IReLoginView {
    void showBar();

    void hideBar();

    void showOrderListSuccess(List<OrderDetailBean> orderDetailBeanList);

    void showCancelOrderSuccess(OrderDetailBean orderDetailBean);

    void showCancelOrderFail();

    @Override
    void showNetError();

    @Override
    void showRequestError();

    @Override
    void showReLogin();
}

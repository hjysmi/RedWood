package com.onepiece.redwood.order.cancel;

import com.onepiece.redwood.listener.IErrorView;
import com.onepiece.redwood.listener.IReLoginView;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/1.
 */
public interface ICancelOrderView extends IErrorView, IReLoginView {
    void showBar();

    void hideBar();

    void ShowOrderListSuccess(List<OrderDetailBean> orderDetailBeanList);

    @Override
    void showNetError();

    @Override
    void showRequestError();

    @Override
    void showReLogin();
}

package com.onepiece.redwood.customer.detail;

import com.onepiece.redwood.customer.CustomerBean;
import com.onepiece.redwood.listener.IErrorView;
import com.onepiece.redwood.listener.IReLoginView;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;

import java.util.List;

/**
 * Created by Administrator on 2015/8/26.
 */
public interface ICustomerDetailView extends IErrorView, IReLoginView {
    void showBar();

    void hideBar();

    void ShowCustomerInfoSuccess(CustomerBean customerBean);

    void showCustomerInfoFail();

    void ShowCustomerOrderInfoSuccess(List<OrderDetailBean> orderDetailBeanList);

    @Override
    void showNetError();

    @Override
    void showRequestError();

    @Override
    void showReLogin();
}

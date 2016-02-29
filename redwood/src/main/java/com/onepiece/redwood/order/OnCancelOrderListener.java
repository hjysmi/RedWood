package com.onepiece.redwood.order;

import com.onepiece.redwood.order.orderdetail.OrderDetailBean;

/**
 * Created by Administrator on 2015/9/1.
 */
public interface OnCancelOrderListener {
    void OnCancelOrderSuccess(OrderDetailBean orderDetailBean);

    void OnCancelOrderFail();
}

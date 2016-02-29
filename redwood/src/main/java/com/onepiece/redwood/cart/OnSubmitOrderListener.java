package com.onepiece.redwood.cart;

import com.onepiece.redwood.listener.IErrorListener;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;

/**
 * Created by Administrator on 2015/8/31.
 */
public interface OnSubmitOrderListener extends IErrorListener {
    void OnSubmitOrderSuccess(OrderDetailBean orderDetailBean);

    @Override
    void OnNetError();

    @Override
    void OnRequestFail();


}

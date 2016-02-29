package com.onepiece.redwood.order;

import com.onepiece.redwood.listener.IErrorListener;
import com.onepiece.redwood.listener.OnReLoginListener;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/1.
 */
public interface OnOrderListListener extends OnReLoginListener,IErrorListener{
    void OnOrderListSuccess(List<OrderDetailBean> orderDetailBeanList);

    @Override
    void OnNetError();

    @Override
    void OnRequestFail();

    @Override
    void onReLogin();
}

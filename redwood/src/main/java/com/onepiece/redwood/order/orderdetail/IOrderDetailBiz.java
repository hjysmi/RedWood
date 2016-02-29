package com.onepiece.redwood.order.orderdetail;

/**
 * Created by Administrator on 2015/8/31.
 */
public interface IOrderDetailBiz {
    void getOrderDetailBtId(String token,int orderId,OnOrderDetailListener onOrderDetailListener);
}

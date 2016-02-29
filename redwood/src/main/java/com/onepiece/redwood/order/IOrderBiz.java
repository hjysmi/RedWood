package com.onepiece.redwood.order;

import java.util.Map;

/**
 * Created by Administrator on 2015/9/1.
 */
public interface IOrderBiz {
    void getOrderListInfo(Map<String, String> map, OnOrderListListener onOrderListListener);

    void cancelOrderById(String token, int orderId, OnCancelOrderListener onCancelOrderListener);
    void pendingTrad(String token,int orderId,int productUserId, OnPendingTradListener onPendingTradListener);
}

package com.onepiece.redwood.order;

import com.onepiece.redwood.customer.ICustomerFragView;
import com.onepiece.redwood.customer.create.ICreateCustomerView;
import com.onepiece.redwood.customer.detail.ICustomerDetailView;
import com.onepiece.redwood.order.cancel.ICancelOrderView;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/1.
 */
public class OrderPrestener {
    private IOrderView iOrderView;
    private IOrderBiz iOrderBiz;
    private ICancelOrderView iCancelOrderView;
    private ICustomerDetailView iCustomerDetailView;
    private ICustomerFragView iCustomerFragView;
    private ICreateCustomerView iCreateCustomerView;

    public OrderPrestener(IOrderView iOrderView) {
        this.iOrderView = iOrderView;
        this.iOrderBiz = new OrderBiz();
    }

    public OrderPrestener(ICancelOrderView iCancelOrderView) {
        this.iCancelOrderView = iCancelOrderView;
        this.iOrderBiz = new OrderBiz();
    }

    public OrderPrestener(ICustomerDetailView iCustomerDetailView) {
        this.iCustomerDetailView = iCustomerDetailView;
        this.iOrderBiz = new OrderBiz();
    }

    public OrderPrestener(ICustomerFragView iCustomerFragView) {
        this.iCustomerFragView = iCustomerFragView;
        this.iOrderBiz = new OrderBiz();
    }

    public OrderPrestener(ICreateCustomerView iCreateCustomerView) {
        this.iCreateCustomerView = iCreateCustomerView;
        this.iOrderBiz = new OrderBiz();
    }

    /**
     * 获取订单列表(未完成，已完成)
     *
     * @param map
     */
    public void getOrderListInfo(Map<String, String> map) {
        iOrderView.showBar();
        iOrderBiz.getOrderListInfo(map, new OnOrderListListener() {
            @Override
            public void OnOrderListSuccess(List<OrderDetailBean> orderDetailBeanList) {
                iOrderView.hideBar();
                iOrderView.showOrderListSuccess(orderDetailBeanList);
            }

            @Override
            public void OnNetError() {
                iOrderView.hideBar();
                iOrderView.showNetError();
            }

            @Override
            public void onReLogin() {
                iOrderView.hideBar();
                iOrderView.showReLogin();
            }

            @Override
            public void OnRequestFail() {
                iOrderView.hideBar();
                iOrderView.showRequestError();
            }
        });
    }

    public void cancelOrderById(String token, Integer orderId) {
        iOrderView.showBar();
        iOrderBiz.cancelOrderById(token, orderId, new OnCancelOrderListener() {
            @Override
            public void OnCancelOrderSuccess(OrderDetailBean orderDetailBean) {
                iOrderView.hideBar();
                iOrderView.showCancelOrderSuccess(orderDetailBean);
            }

            @Override
            public void OnCancelOrderFail() {
                iOrderView.hideBar();
                iOrderView.showCancelOrderFail();
            }
        });
    }

    /**
     * 获取订单列表(已关闭)
     *
     * @param map
     */
    public void getCancelOrderListInfo(Map<String, String> map) {
        iCancelOrderView.showBar();
        iOrderBiz.getOrderListInfo(map, new OnOrderListListener() {
            @Override
            public void OnOrderListSuccess(List<OrderDetailBean> orderDetailBeanList) {
                iCancelOrderView.hideBar();
                iCancelOrderView.ShowOrderListSuccess(orderDetailBeanList);
            }

            @Override
            public void OnNetError() {
                iCancelOrderView.hideBar();
                iCancelOrderView.showNetError();
            }

            @Override
            public void onReLogin() {
                iCancelOrderView.hideBar();
                iCancelOrderView.showReLogin();
            }

            @Override
            public void OnRequestFail() {
                iCancelOrderView.hideBar();
                iCancelOrderView.showRequestError();
            }
        });
    }

    /**
     * 获取订单信息ByproductUserId(客户Id)
     */
    public void getOrderInfoByproductUserId(Map<String, String> map) {
        iCustomerDetailView.showBar();
        iOrderBiz.getOrderListInfo(map, new OnOrderListListener() {
            @Override
            public void OnOrderListSuccess(List<OrderDetailBean> orderDetailBeanList) {
                iCustomerDetailView.hideBar();
                iCustomerDetailView.ShowCustomerOrderInfoSuccess(orderDetailBeanList);
            }

            @Override
            public void OnNetError() {
                iCustomerDetailView.hideBar();
                iCustomerDetailView.showNetError();
            }

            @Override
            public void onReLogin() {
                iCustomerDetailView.hideBar();
                iCustomerDetailView.showReLogin();
            }

            @Override
            public void OnRequestFail() {
                iCustomerDetailView.hideBar();
                iCustomerDetailView.showRequestError();
            }
        });
    }

    /**
     * 待处理订单
     *
     * @param token
     * @param orderId
     * @param id
     */
    public void pendingTrad(String token, Integer orderId, Integer id) {
        iCustomerFragView.showBar();
        iOrderBiz.pendingTrad(token, orderId, id, new OnPendingTradListener() {
            @Override
            public void onPendingTradSuccess() {
                iCustomerFragView.hideBar();
                iCustomerFragView.showPendingTradSuccess();
            }

            @Override
            public void onPendingTradFail() {
                iCustomerFragView.hideBar();
                iCustomerFragView.showPendingTradFail();
            }
        });
    }

    /**
     * 待处理订单
     *
     * @param token
     * @param orderId
     * @param id
     */
    public void pendingTrad_CreateCustomer(String token, Integer orderId, Integer id) {
        iCreateCustomerView.showBar();
        iOrderBiz.pendingTrad(token, orderId, id, new OnPendingTradListener() {
            @Override
            public void onPendingTradSuccess() {
                iCreateCustomerView.hideBar();
                iCreateCustomerView.showPendingTradSuccess();
            }

            @Override
            public void onPendingTradFail() {
                iCreateCustomerView.hideBar();
                iCreateCustomerView.showPendingTradFail();
            }
        });
    }
}

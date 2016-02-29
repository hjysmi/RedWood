package com.onepiece.redwood.order.orderdetail;

/**
 * Created by Administrator on 2015/8/31.
 */
public class OrderDetailPrestener {
    IOrderDetailView iOrderDetailView;
    IOrderDetailBiz iOrderDetailBiz;

    public OrderDetailPrestener(IOrderDetailView iOrderDetailView) {
        this.iOrderDetailView = iOrderDetailView;
        this.iOrderDetailBiz = new OrderDetailBiz();
    }

    public void getOrderDetailById(String token, int orderId) {
        iOrderDetailView.showBar();
        iOrderDetailBiz.getOrderDetailBtId(token, orderId, new OnOrderDetailListener() {
            @Override
            public void OnOrderDetailSuccess(OrderDetailBean orderDetailBean) {
                iOrderDetailView.hideBar();
                iOrderDetailView.showOrderDetailSuccess(orderDetailBean);
            }

            @Override
            public void OnNetError() {
                iOrderDetailView.hideBar();
                iOrderDetailView.showNetError();
            }

            @Override
            public void onReLogin() {
                iOrderDetailView.hideBar();
                iOrderDetailView.showReLogin();
            }

            @Override
            public void OnRequestFail() {
                iOrderDetailView.hideBar();
                iOrderDetailView.showRequestError();
            }
        });
    }
}

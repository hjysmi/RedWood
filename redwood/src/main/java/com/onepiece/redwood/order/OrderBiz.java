package com.onepiece.redwood.order;

import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.menu.DataBean;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;
import com.orhanobut.logger.Logger;

import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/9/1.
 */
public class OrderBiz implements IOrderBiz {
    @Override
    public void getOrderListInfo(Map<String, String> map, final OnOrderListListener onOrderListListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.getOrderInfo(map, new Callback<DataBean<OrderPage>>() {
            @Override
            public void success(DataBean<OrderPage> bean, Response response) {
                Logger.e("url=" + response.getUrl());
                if (null == bean.getToken()) {
                    if (bean.getSuccess()) {
                        onOrderListListener.OnOrderListSuccess(bean.getData().getResult());
                    } else {
                        /**请求失败*/
                        onOrderListListener.OnRequestFail();
                    }
                } else if (!bean.getToken()) {
                    /**重新登录*/
                    onOrderListListener.onReLogin();
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if (retrofitError.isNetworkError()) {
                    onOrderListListener.OnNetError();
                }
            }
        });
    }

    @Override
    public void cancelOrderById(String token, int orderId, final OnCancelOrderListener onCancelOrderListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.cancelOrderById(token, orderId, new Callback<DataBean<OrderDetailBean>>() {
            @Override
            public void success(DataBean<OrderDetailBean> objectDataBean, Response response) {
                Logger.e("url+" + response.getUrl());
                if (objectDataBean.getSuccess()) {
                    onCancelOrderListener.OnCancelOrderSuccess(objectDataBean.getData());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                onCancelOrderListener.OnCancelOrderFail();
            }
        });
    }

    /**
     * 待处理订单
     *
     * @param token
     * @param orderId
     * @param productUserId
     * @param onPendingTradListener
     */
    @Override
    public void pendingTrad(String token, int orderId, int productUserId, final OnPendingTradListener onPendingTradListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.pendingOrder(token, orderId, productUserId, new Callback<DataBean<Object>>() {
            @Override
            public void success(DataBean<Object> bean, Response response) {
                Logger.e("url=" + response.getUrl());
                if (bean.getSuccess()) {
                    Logger.e("url>>success");
                    onPendingTradListener.onPendingTradSuccess();
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Logger.e("url>>fail");
                onPendingTradListener.onPendingTradFail();
            }
        });
    }


}

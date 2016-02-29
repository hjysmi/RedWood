package com.onepiece.redwood.order.orderdetail;

import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.menu.DataBean;
import com.orhanobut.logger.Logger;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/31.
 */
public class OrderDetailBiz implements IOrderDetailBiz {
    @Override
    public void getOrderDetailBtId(String token, int orderId, final OnOrderDetailListener onOrderDetailListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.getOrderInfoById(token, orderId, new Callback<DataBean<OrderDetailBean>>() {
            @Override
            public void success(DataBean<OrderDetailBean> Bean, Response response) {
                Logger.e("url=" + response.getUrl());
                if (Bean.getToken() == null) {
                    if (Bean.getSuccess()) {
                        onOrderDetailListener.OnOrderDetailSuccess(Bean.getData());
                    } else {
                        onOrderDetailListener.OnRequestFail();
                    }
                } else if (!Bean.getToken()) {
                    /**重新登录*/
                    onOrderDetailListener.onReLogin();
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Logger.e("url=" + retrofitError.getUrl());
                if (retrofitError.isNetworkError()) {
                    onOrderDetailListener.OnNetError();
                }
            }
        });
    }
}

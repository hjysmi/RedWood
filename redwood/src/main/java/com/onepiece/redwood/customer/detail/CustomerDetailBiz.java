package com.onepiece.redwood.customer.detail;

import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.customer.CustomerBean;
import com.onepiece.redwood.menu.DataBean;
import com.orhanobut.logger.Logger;

import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/26.
 */
public class CustomerDetailBiz implements ICustomerDetailBiz {
    @Override
    public void getCustomerDetailInfo(Map<String, String> map, final OnCustomerDetailInfoListener onCustomerDetailInfoListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.getCustomerInfoById(map, new Callback<DataBean<CustomerBean>>() {
            @Override
            public void success(DataBean<CustomerBean> bean, Response response) {
                Logger.e("url=" + response.getUrl());
                if (bean.getToken() == null) {
                    if (bean.getSuccess()) {
                        onCustomerDetailInfoListener.OnDetailSuccess(bean.getData());
                    } else {
                        onCustomerDetailInfoListener.OnRequestFail();
                    }
                } else if (!bean.getToken()) {
                    /**重新登录*/
                    onCustomerDetailInfoListener.onReLogin();
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Logger.e("url=" + retrofitError.getUrl());
                if (retrofitError.isNetworkError()) {
                    onCustomerDetailInfoListener.OnNetError();
                }
            }
        });
    }
}

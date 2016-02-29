package com.onepiece.redwood.customer;

import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.menu.DataBean;
import com.orhanobut.logger.Logger;

import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/23.
 */
public class CustomerBiz implements ICustomerFragBiz {
    /**
     * 获取客户信息
     *
     * @param map
     * @param onCustomerFragListener
     */
    @Override
    public void getCustomerFragInfo(Map<String, String> map, final OnCustomerFragListener onCustomerFragListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.getCustomerInfo(map, new Callback<DataBean<PageBean>>() {
            @Override
            public void success(DataBean<PageBean> bean, Response response) {
                Logger.e("url=" + response.getUrl());
                if (null == bean.getToken()) {
                    if (bean.getSuccess()) {
                        onCustomerFragListener.OnSuccess(bean.getData().getResult());
                    } else {
                        onCustomerFragListener.OnRequestFail();
                    }
                } else if (!bean.getToken()) {
                    Logger.e("url>>relogin");
                    onCustomerFragListener.onReLogin();
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if (retrofitError.isNetworkError()) {
                    onCustomerFragListener.OnNetError();
                }
            }
        });
    }
}

package com.onepiece.redwood.customer.create;

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
 * Created by Administrator on 2015/8/24.
 */
public class CreateCustomerBiz implements ICreateCustomerBiz {
    @Override
    public void createCustomerInfo(Map<String, String> map, final OnCreateCustomerListener onCreateCustomerListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.CreateCustomerInfo(map, new Callback<DataBean<CustomerBean>>() {
            @Override
            public void success(DataBean<CustomerBean> bean, Response response) {
                Logger.e("url=" + response.getUrl());
                if (bean.getToken() == null) {
                    if (bean.getSuccess()) {
                        //创建客户成功
                        onCreateCustomerListener.OnCreateSuccess(bean.getData());
                    } else {
                        onCreateCustomerListener.OnRequestFail();
                    }
                } else if (!bean.getToken()) {
                    /**重新登录*/
                    onCreateCustomerListener.onReLogin();
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Logger.e("url=" + retrofitError.getUrl());
                if(retrofitError.isNetworkError()){
                    onCreateCustomerListener.OnNetError();
                }

            }
        });
    }


}

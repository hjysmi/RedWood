package com.onepiece.redwood.customer.update;

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
 * Created by Administrator on 2015/8/27.
 */
public class CustomerUpdateBiz implements ICustomerUpdateBiz{
    @Override
    public void updateCustomerInfoById(Map<String, String> map,final OnUpdateCustomerListener onUpdateCustomerListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.updateCustomerInfoById(map, new Callback<DataBean<CustomerBean>>() {
            @Override
            public void success(DataBean<CustomerBean> customerBeanDataBean, Response response) {
                Logger.e("url="+response.getUrl());
                onUpdateCustomerListener.OnUpCustomerInfoSuccess();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Logger.e("url="+retrofitError.getUrl());
                onUpdateCustomerListener.OnUpCustomerInfoFail();
            }
        });
    }
}

package com.onepiece.redwood.prolist;

import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.menu.DataBean;
import com.orhanobut.logger.Logger;

import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/16.
 */
public class ProListBiz implements IProListBiz {
    /**
     * 获取产品列表信息
     *
     * @param onProListListener
     */
    @Override
    public void getProListInfo(Map<String, String> map, final OnProListListener onProListListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.getProListInfo(map, new Callback<DataBean<ProListBean>>() {
            @Override
            public void success(DataBean<ProListBean> bean, Response response) {
                Logger.e("url=" + response.getUrl());
                onProListListener.OnProListSuccess(bean.getData());
                if(bean.getSuccess()){
                    onProListListener.OnProListSuccess(bean.getData());
                }else{
                    onProListListener.OnRequestFail();
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if(retrofitError.isNetworkError()){
                    onProListListener.OnNetError();
                }
            }
        });
    }


}

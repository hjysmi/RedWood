package com.onepiece.redwood.customer.add;

import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.menu.DataBean;
import com.orhanobut.logger.Logger;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/24.
 */
public class AreasChangeBiz implements IAreasChangeBiz {
    @Override
    public void getAreasInfo(int pcode, final OnAreasListener onAreasListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.getAreasInfo(pcode, new Callback<DataBean<List<AreasBean>>>() {
            @Override
            public void success(DataBean<List<AreasBean>> listDataBean, Response response) {
                Logger.e("url=" + response.getUrl());
                if(listDataBean.getSuccess()){
                    onAreasListener.OnAreasSuccess(listDataBean.getData());
                }else{
                    onAreasListener.OnRequestFail();
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Logger.e("url=" + retrofitError.getUrl());
                if(retrofitError.isNetworkError()){
                    onAreasListener.OnNetError();
                }

            }
        });
    }
}

package com.onepiece.redwood.menu;

import com.onepiece.redwood.UrlAPI;
import com.orhanobut.logger.Logger;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/15.
 */
public class MenuBiz implements IMenuBiz {
    /**
     * 获取菜单信息
     *
     * @param onMenuLinstener
     */
    @Override
    public void getMenuInfo(final OnMenuLinstener onMenuLinstener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.getMenuInfo(new Callback<DataBean<List<MenuBean>>>() {
            @Override
            public void success(DataBean<List<MenuBean>> bean, Response response) {
                Logger.e("url" + response.toString());
                if (bean.getSuccess()) {
                  onMenuLinstener.OnMenuSuccess(bean.getData());
                } else {
                    onMenuLinstener.OnRequestFail();
                }


            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if(retrofitError.isNetworkError()){
                    onMenuLinstener.OnNetError();
                }
                Logger.e("url=" + retrofitError.getCause().toString());
                Logger.e("url=" + retrofitError.getUrl());
            }
        });




    }
}

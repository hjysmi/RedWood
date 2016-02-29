package com.onepiece.redwood.self;

import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.menu.DataBean;
import com.onepiece.redwood.self.update.OnUpdateUserListener;
import com.orhanobut.logger.Logger;

import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by Administrator on 2015/9/9.
 */
public class SelfBiz implements ISelfBiz {
    @Override
    public void upHeadImage(String token, TypedFile file, final OnUpImageListener onUpImageListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.upHeadImage(token, file, new Callback<DataBean<Object>>() {
            @Override
            public void success(DataBean<Object> bean, Response response) {
                if (null == bean.getToken()) {
                    if (bean.getSuccess()) {
                        onUpImageListener.OnUpImageSuccess();
                    } else {
                        onUpImageListener.OnRequestFail();
                    }
                } else if (!bean.getToken()) {
                    /**重新登录*/
                    onUpImageListener.onReLogin();
                }


            }

            @Override
            public void failure(RetrofitError retrofitError) {

                if (retrofitError.isNetworkError()) {
                    onUpImageListener.OnNetError();
                }
            }
        });
    }

    @Override
    public void updateUserInfo(Map<String, String> map, final OnUpdateUserListener onUpdateUserListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.updateUserInfo(map, new Callback<DataBean<UserBean>>() {
            @Override
            public void success(DataBean<UserBean> bean, Response response) {
                Logger.e("url=" + response.getUrl());
                if (null == bean.getToken()) {
                    if (bean.getSuccess()) {
                        onUpdateUserListener.OnUpdateSuccess(bean.getData());
                    } else {
                        onUpdateUserListener.OnRequestFail();
                    }
                } else if (!bean.getToken()) {
                    /**重新登录*/
                    onUpdateUserListener.onReLogin();
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Logger.e("url=" + retrofitError.getUrl());
                if (retrofitError.isNetworkError()) {
                    onUpdateUserListener.OnNetError();
                }

            }
        });
    }


}

package com.onepiece.redwood.login;

import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.menu.DataBean;
import com.onepiece.redwood.self.UserBean;
import com.orhanobut.logger.Logger;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/28.
 */
public class LoginActBiz implements ILoginActBiz {
    /**
     * 获取登录信息
     *
     * @param uname
     * @param upsw
     * @param onLoginListener
     */
    @Override
    public void getUserInfo(String uname, String upsw, final OnLoginListenerI onLoginListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.getUserInfoById(uname, upsw, new Callback<DataBean<UserBean>>() {
            @Override
            public void success(DataBean<UserBean> bean, Response response) {
                Logger.e("url=" + response.getUrl());
                Logger.e("url1=" + bean.toString());
                Boolean flag = bean.getToken();
                if (flag==null) {
                    //token=null token有效
                    if (bean.getSuccess()) {
                        onLoginListener.OnLoginSuccess(bean.getData());
                    } else {
                        onLoginListener.OnRequestFail();
                    }
                } else if (!flag) {
                    //token = false(重新登录)token无效
                    //  onLoginListener.onReLogin();
                }


            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if(retrofitError.isNetworkError()){
                    onLoginListener.OnNetError();
                }
                Logger.e("url=" + retrofitError.getCause().toString());
                Logger.e("url=" + retrofitError.getUrl());

            }
        });
    }
}

package com.onepiece.redwood.order.sign;

import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.menu.DataBean;
import com.orhanobut.logger.Logger;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by Administrator on 2015/9/18.
 */
public class SignBiz implements ISignBiz {
    /**
     * 上传签名图片
     *
     * @param token
     * @param orderId
     * @param file
     * @param onUpSignImageListener
     */
    @Override
    public void upSignImage(String token, int orderId, TypedFile file, final OnUpSignImageListener onUpSignImageListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.upSignImage(token, orderId, file, new Callback<DataBean<Object>>() {
            @Override
            public void success(DataBean<Object> bean, Response response) {
                Logger.e("url=" + bean.toString());
                if (bean.getToken() == null) {
                    if (bean.getSuccess()) {
                        onUpSignImageListener.onUpSignImageSuccess();
                    } else {
                        onUpSignImageListener.OnRequestFail();
                    }
                } else if (!bean.getToken()) {
                    onUpSignImageListener.onReLogin();
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if (retrofitError.isNetworkError()) {
                    onUpSignImageListener.OnNetError();
                }
            }
        });
    }
}

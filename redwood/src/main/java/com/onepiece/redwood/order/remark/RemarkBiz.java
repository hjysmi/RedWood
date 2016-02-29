package com.onepiece.redwood.order.remark;

import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.menu.DataBean;
import com.orhanobut.logger.Logger;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/9/21.
 */
public class RemarkBiz implements IRemarkBiz {
    /**
     * 修改备注
     *
     * @param token
     * @param orderId
     * @param remark
     * @param onUpdateRemarkListener
     */
    @Override
    public void updateRemark(String token, int orderId, String remark, final OnUpdateRemarkListener onUpdateRemarkListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.updateRemarkInfo(token, orderId, remark, new Callback<DataBean<Object>>() {
            @Override
            public void success(DataBean<Object> bean, Response response) {
                Logger.e("url=" + response.getUrl());
                if (bean.getToken() == null) {
                    if (bean.getSuccess()) {
                        onUpdateRemarkListener.OnUpdateRemarkSuccess();
                    } else {
                        onUpdateRemarkListener.OnRequestFail();
                    }
                } else if (!bean.getToken()) {
                    /**重新登录*/
                    onUpdateRemarkListener.onReLogin();
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if (retrofitError.isNetworkError()) {
                    onUpdateRemarkListener.OnNetError();
                }

            }
        });
    }
}

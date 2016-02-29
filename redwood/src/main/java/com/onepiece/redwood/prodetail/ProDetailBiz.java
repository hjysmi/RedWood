package com.onepiece.redwood.prodetail;

import android.content.Context;

import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.coll.CollBean;
import com.onepiece.redwood.coll.CollDao;
import com.onepiece.redwood.menu.DataBean;
import com.orhanobut.logger.Logger;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/21.
 */
public class ProDetailBiz implements IProDetailBiz {
    @Override
    public void getProDetailInfo(int proId,final OnProDetailListener onProDetailListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.getProDetailInfo(proId, new Callback<DataBean<ProDetailBean>>() {
            @Override
            public void success(DataBean<ProDetailBean> bean, Response response) {
                Logger.e("url=" + response.getUrl());
                if(bean.getSuccess()){
                    onProDetailListener.getInfoSuccess(bean.getData());
                }else{
                    onProDetailListener.OnRequestFail();
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Logger.e("url="+retrofitError.getUrl());
                if(retrofitError.isNetworkError()) onProDetailListener.OnNetError();
            }
        });
    }
    /**
     * 加入收藏夹
     *
     * @param context
     * @param collBean
     * @param onCollListener
     */
    @Override
    public void addCollById(Context context, CollBean collBean, OnCollListener onCollListener) {
        CollDao dao = new CollDao(context);
        int i = dao.addCollById(collBean);
        if (i == 0) {
            onCollListener.onCollFail();
        } else {
            onCollListener.onCollSuccess(i);
        }
    }

    /**
     * 查询收藏夹BYId
     *
     * @param context
     * @param proId
     * @param onQueryCollListener
     */
    @Override
    public void queryCollById(Context context, int proId, OnQueryCollListener onQueryCollListener) {
        CollDao dao = new CollDao(context);
        Boolean flag = dao.queryCollById(proId);
        if (flag) {
            onQueryCollListener.onQueryCollSuccess();
        } else {
            onQueryCollListener.onQueryCollFail();
        }
    }
}

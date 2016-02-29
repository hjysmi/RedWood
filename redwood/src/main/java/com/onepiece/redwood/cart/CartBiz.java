package com.onepiece.redwood.cart;

import android.content.Context;

import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.menu.DataBean;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;
import com.onepiece.redwood.prodetail.cartact.OnDelCartListener;
import com.onepiece.redwood.prodetail.cartact.OnQueryCartListener;
import com.onepiece.redwood.prodetail.cartact.OnUpdateCartListener;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/8/31.
 */
public class CartBiz implements ICartBiz {
    @Override
    public void submitOrder(Map<String, String> map, final OnSubmitOrderListener onSubmitOrderListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.submitOrder(map, new Callback<DataBean<OrderDetailBean>>() {
            @Override
            public void success(DataBean<OrderDetailBean> bean, Response response) {
                Logger.e("url=" + response.getUrl());

                    if (bean.getSuccess()) {
                        Logger.e("url" + bean.toString());
                        onSubmitOrderListener.OnSubmitOrderSuccess(bean.getData());
                    }else{
                        onSubmitOrderListener.OnRequestFail();
                    }



            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Logger.e("url=" + retrofitError.toString());
                if(retrofitError.isNetworkError()){
                    onSubmitOrderListener.OnNetError();
                }

            }
        });
    }

    /**
     * 查询购物车
     *
     * @param context
     * @param onQueryCartListener
     */
    @Override
    public void queryCartAll(Context context, OnQueryCartListener onQueryCartListener) {
        CartDao dao = new CartDao(context);
        onQueryCartListener.OnQueryCartSuccess(dao.queryAll());
    }

    @Override
    public void delCartById(Context context, int proId, OnDelCartListener onDelCartListener) {
        CartDao dao = new CartDao(context);
        int i = dao.delCartByproId(proId);
        if (i > 0) {
            onDelCartListener.OnDelCartSuccess(dao.queryAll());
        }
    }

    /**
     * 批量删除购物车
     * @param context
     * @param list_proId
     * @param OnDelCartByProListListener
     */
    @Override
    public void delCartByAllProId(Context context, List<Integer> list_proId, OnDelCartByProListListener OnDelCartByProListListener) {
        CartDao dao = new CartDao(context);
        int i = dao.delCartByproAllId(list_proId);
        if(i>0){
            OnDelCartByProListListener.onDelCartByProListSuccess();
        }else{
            OnDelCartByProListListener.onDelCartByProListFail();
        }
    }

    /**
     * 产品数量
     *
     * @param context
     * @param proId
     * @param count
     * @param onUpdateCartListener
     */
    @Override
    public void updateCountByproId(Context context, int proId, int count, OnUpdateCartListener onUpdateCartListener) {
        CartDao dao = new CartDao(context);
        int i = dao.addCountByproId(count, proId);
        if (i > 0) {
            onUpdateCartListener.OnUpdateCartResult(dao.queryAll());
        }
    }
}

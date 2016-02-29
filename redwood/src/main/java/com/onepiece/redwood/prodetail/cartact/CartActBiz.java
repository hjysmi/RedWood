package com.onepiece.redwood.prodetail.cartact;

import android.content.Context;

import com.onepiece.redwood.UrlAPI;
import com.onepiece.redwood.cart.CartDao;
import com.onepiece.redwood.cart.OnDelCartByProListListener;
import com.onepiece.redwood.cart.OnSubmitOrderListener;
import com.onepiece.redwood.menu.DataBean;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/9/14.
 */
public class CartActBiz implements ICartActBiz {
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

    /**
     * 删除订单
     *
     * @param context
     * @param onDelCartListener
     */
    @Override
    public void delCartById(Context context, int proId, OnDelCartListener onDelCartListener) {
        CartDao dao = new CartDao(context);
        int i = dao.delCartByproId(proId);
        if (i > 0) {
            onDelCartListener.OnDelCartSuccess(dao.queryAll());
        }
    }

    /**
     * 修改数量
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

    /**
     * 提交订单
     *
     * @param map
     * @param onSubmitOrderListener
     */
    @Override
    public void submitOrder(Map<String, String> map, final OnSubmitOrderListener onSubmitOrderListener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(UrlAPI.IPADDRESS).build();
        UrlAPI api = restAdapter.create(UrlAPI.class);
        api.submitOrder(map, new Callback<DataBean<OrderDetailBean>>() {
            @Override
            public void success(DataBean<OrderDetailBean> bean, Response response) {
                Logger.e("url=" + response.getUrl());

                    if (bean.getSuccess()) {
                        Logger.e(bean.toString());
                        onSubmitOrderListener.OnSubmitOrderSuccess(bean.getData());
                    } else {
                        onSubmitOrderListener.OnRequestFail();

                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if (retrofitError.isNetworkError()) {
                    onSubmitOrderListener.OnNetError();
                }
            }
        });
    }

    @Override
    public void delCartByAllProId(Context context, List<Integer> list_proId, OnDelCartByProListListener OnDelCartByProListListener) {
        CartDao dao = new CartDao(context);
        int i = dao.delCartByproAllId(list_proId);
        if (i > 0) {
            OnDelCartByProListListener.onDelCartByProListSuccess();
        } else {
            OnDelCartByProListListener.onDelCartByProListFail();
        }
    }
}

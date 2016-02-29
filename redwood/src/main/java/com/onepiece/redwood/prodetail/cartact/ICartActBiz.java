package com.onepiece.redwood.prodetail.cartact;

import android.content.Context;

import com.onepiece.redwood.cart.OnDelCartByProListListener;
import com.onepiece.redwood.cart.OnSubmitOrderListener;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/14.
 */
public interface ICartActBiz {
    void queryCartAll(Context context, OnQueryCartListener onQueryCartListener);

    void delCartById(Context context, int proId, OnDelCartListener onDelCartListener);

    void updateCountByproId(Context context, int proId, int count, OnUpdateCartListener onUpdateCartListener);

    void submitOrder(Map<String, String> map, OnSubmitOrderListener onSubmitOrderListener);
    void delCartByAllProId(Context context, List<Integer> list_proId, OnDelCartByProListListener OnDelCartByProListListener);
}

package com.onepiece.redwood.cart;

import android.content.Context;

import com.onepiece.redwood.prodetail.cartact.OnDelCartListener;
import com.onepiece.redwood.prodetail.cartact.OnQueryCartListener;
import com.onepiece.redwood.prodetail.cartact.OnUpdateCartListener;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/31.
 */
public interface ICartBiz {
    void submitOrder(Map<String, String> map, OnSubmitOrderListener onSubmitOrderListener);

    void queryCartAll(Context context, OnQueryCartListener onQueryCartListener);

    void delCartById(Context context, int proId, OnDelCartListener onDelCartListener);
    void delCartByAllProId(Context context, List<Integer> list_proId, OnDelCartByProListListener OnDelCartByProListListener);

    void updateCountByproId(Context context, int proId, int count, OnUpdateCartListener onUpdateCartListener);
}

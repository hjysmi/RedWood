package com.onepiece.redwood.prodetail.cartact;

import com.onepiece.redwood.cart.CartBean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/14.
 */
public interface OnUpdateCartListener {
    void OnUpdateCartResult(List<CartBean> cartBeanList);
}

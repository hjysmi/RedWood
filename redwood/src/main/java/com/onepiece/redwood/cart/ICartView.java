package com.onepiece.redwood.cart;

import com.onepiece.redwood.listener.IErrorView;
import com.onepiece.redwood.listener.IReLoginView;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;

import java.util.List;

/**
 * Created by Administrator on 2015/8/31.
 */
public interface ICartView extends IErrorView, IReLoginView {
    void showBar();

    void hideBar();

    void showCart(List<CartBean> cartBeanList);

    void SubmitOrderSuccess(OrderDetailBean orderDetailBean);


    void delCartByproId(List<CartBean> cartBeanList);

    void updateCartByproId(List<CartBean> cartBeanList);

    void delCartByProAllIdSuccess();

    void delCartByProAllIdFail();

    @Override
    void showNetError();

    @Override
    void showRequestError();

    @Override
    void showReLogin();
}

package com.onepiece.redwood.prodetail.cartact;

import com.onepiece.redwood.cart.CartBean;
import com.onepiece.redwood.listener.IErrorView;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/14.
 */
public interface ICartActView extends IErrorView{
    void hideBar();

    void showBar();

    void showCart(List<CartBean> cartBeanList);

    void delCartByproId(List<CartBean> cartBeanList);

    void updateCartByproId(List<CartBean> cartBeanList);

    void submitOrderSuccess(OrderDetailBean orderDetailBean);

    //void submitOrderFail();

    void delCartByProAllIdSuccess();

    void delCartByProAllIdFail();

    @Override
    void showNetError();

    @Override
    void showRequestError();


}

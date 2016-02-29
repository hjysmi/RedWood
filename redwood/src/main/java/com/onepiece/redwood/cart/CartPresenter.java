package com.onepiece.redwood.cart;

import android.content.Context;
import android.os.Handler;

import com.onepiece.redwood.order.orderdetail.OrderDetailBean;
import com.onepiece.redwood.prodetail.cartact.OnDelCartListener;
import com.onepiece.redwood.prodetail.cartact.OnQueryCartListener;
import com.onepiece.redwood.prodetail.cartact.OnUpdateCartListener;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/31.
 */
public class CartPresenter {
    ICartView iCartView;
    ICartBiz iCartBiz;
    Handler handler = new Handler();

    public CartPresenter(ICartView iCartView) {
        this.iCartView = iCartView;
        this.iCartBiz = new CartBiz();
    }


    /**
     * 提交订单
     *
     * @param map
     */
    public void submitOrder(Map<String, String> map) {
        iCartView.showBar();
        iCartBiz.submitOrder(map, new OnSubmitOrderListener() {
            @Override
            public void OnSubmitOrderSuccess(OrderDetailBean orderDetailBean) {
                iCartView.hideBar();
                iCartView.SubmitOrderSuccess(orderDetailBean);
            }

            @Override
            public void OnRequestFail() {
                iCartView.hideBar();
                iCartView.showRequestError();
            }

            @Override
            public void OnNetError() {
                iCartView.hideBar();
                iCartView.showNetError();
            }
        });
    }

    public void queryAll(Context context) {
        iCartView.showBar();
        iCartBiz.queryCartAll(context, new OnQueryCartListener() {
            @Override
            public void OnQueryCartSuccess(final List<CartBean> cartBeanList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCartView.hideBar();
                        iCartView.showCart(cartBeanList);
                    }
                });

            }
        });
    }

    /**
     * 删除购物车ByproId
     *
     * @param context
     * @param proId
     */
    public void delCartById(Context context, int proId) {
        iCartView.showBar();
        iCartBiz.delCartById(context, proId, new OnDelCartListener() {
            @Override
            public void OnDelCartSuccess(final List<CartBean> cartBeanList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCartView.hideBar();
                        iCartView.delCartByproId(cartBeanList);
                    }
                });

            }
        });
    }

    public void updateCountByproId(Context context, Integer count, Integer proId) {
        iCartView.showBar();
        iCartBiz.updateCountByproId(context, proId, count, new OnUpdateCartListener() {
            @Override
            public void OnUpdateCartResult(final List<CartBean> cartBeanList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCartView.hideBar();
                        iCartView.updateCartByproId(cartBeanList);
                    }
                });

            }
        });
    }

    public void delCartByAllId(final Context context, final List<Integer> list_cartProId) {
        iCartView.hideBar();
        handler.post(new Runnable() {
            @Override
            public void run() {
                iCartBiz.delCartByAllProId(context, list_cartProId, new OnDelCartByProListListener() {
                    @Override
                    public void onDelCartByProListSuccess() {
                        iCartView.hideBar();
                        iCartView.delCartByProAllIdSuccess();
                    }

                    @Override
                    public void onDelCartByProListFail() {
                        iCartView.hideBar();
                        iCartView.delCartByProAllIdFail();
                    }

            });
            }
        });
    }
}

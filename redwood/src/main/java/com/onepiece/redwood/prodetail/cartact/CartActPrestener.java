package com.onepiece.redwood.prodetail.cartact;

import android.content.Context;
import android.os.Handler;

import com.onepiece.redwood.cart.CartBean;
import com.onepiece.redwood.cart.OnDelCartByProListListener;
import com.onepiece.redwood.cart.OnSubmitOrderListener;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/14.
 */
public class CartActPrestener {
    Context context;
    ICartActView iCartActView;
    ICartActBiz iCartActBiz;
    Handler handler = new Handler();

    public CartActPrestener(Context context, ICartActView iCartActView) {
        this.context = context;
        this.iCartActView = iCartActView;
        this.iCartActBiz = new CartActBiz();

    }

    public void queryAll() {
        iCartActView.showBar();
        iCartActBiz.queryCartAll(context, new OnQueryCartListener() {
            @Override
            public void OnQueryCartSuccess(final List<CartBean> cartBeanList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCartActView.hideBar();
                        iCartActView.showCart(cartBeanList);
                    }
                });
            }
        });
    }

    public void delCartById(int proId) {
        iCartActView.showBar();
        iCartActBiz.delCartById(context, proId, new OnDelCartListener() {
            @Override
            public void OnDelCartSuccess(List<CartBean> cartBeanList) {
                iCartActView.hideBar();
                iCartActView.delCartByproId(cartBeanList);
            }
        });
    }

    public void updateCountByproId(Integer count, Integer proId) {
        iCartActView.showBar();
        iCartActBiz.updateCountByproId(context, proId, count, new OnUpdateCartListener() {
            @Override
            public void OnUpdateCartResult(List<CartBean> cartBeanList) {
                iCartActView.hideBar();
                iCartActView.updateCartByproId(cartBeanList);
            }
        });
    }

    /**
     * 提交订单
     *
     * @param map
     */
    public void submitOrder(Map<String, String> map) {
        iCartActView.showBar();
        iCartActBiz.submitOrder(map, new OnSubmitOrderListener() {
            @Override
            public void OnSubmitOrderSuccess(OrderDetailBean orderDetailBean) {
                iCartActView.hideBar();
                iCartActView.submitOrderSuccess(orderDetailBean);
            }

            @Override
            public void OnNetError() {
                iCartActView.hideBar();
                iCartActView.showNetError();
            }



            @Override
            public void OnRequestFail() {
                iCartActView.hideBar();
                iCartActView.showRequestError();
            }
        });
    }
    public void delCartByAllId(final Context context, final List<Integer> list_cartProId) {
        iCartActView.hideBar();
        handler.post(new Runnable() {
            @Override
            public void run() {
                iCartActBiz.delCartByAllProId(context, list_cartProId, new OnDelCartByProListListener() {
                    @Override
                    public void onDelCartByProListSuccess() {
                        iCartActView.hideBar();
                        iCartActView.delCartByProAllIdSuccess();
                    }

                    @Override
                    public void onDelCartByProListFail() {
                        iCartActView.hideBar();
                        iCartActView.delCartByProAllIdFail();
                    }

                });
            }
        });
    }
}

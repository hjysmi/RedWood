package com.onepiece.redwood.prodetail.cartact;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onepiece.redwood.R;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.cart.CartAdapter;
import com.onepiece.redwood.cart.CartBean;
import com.onepiece.redwood.cart.CartProBean;
import com.onepiece.redwood.cart.ChangeCustomerAct;
import com.onepiece.redwood.order.orderdetail.OrderDetailAct;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;
import com.onepiece.redwood.prodetail.TextViewUtil;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.SPUtils;
import com.onepiece.redwood.utils.ScreenUtils;
import com.orhanobut.logger.Logger;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车
 */
public class CartAct extends Activity implements ICartActView, OnClickListener, CartAdapter.Callback {
    private Button but_back, but_update;
    private WhorlView id_whorlview;
    private ListView id_lv;
    private PercentLinearLayout rl_empty;
    private Button id_but_changecustomer;
    private Button id_but_submitorder;
    private TextView id_tv_allprice;
    CartAdapter adapter;
    List<CartBean> list_cart = new ArrayList<CartBean>();
    HashMap<CartBean, Boolean> hashMap = new HashMap<CartBean, Boolean>();
    private CartActPrestener prestener = new CartActPrestener(this, this);
    private Map<String, String> map;
    private int customerId = 0;
    private Boolean flag = false;
    int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartact);
        initViews();
        initValues();
        initEvents();
    }

    private void initEvents() {
        but_back.setOnClickListener(this);
        but_update.setOnClickListener(this);
        id_but_changecustomer.setOnClickListener(this);
        id_but_submitorder.setOnClickListener(this);
    }

    private void initValues() {
        prestener.queryAll();
    }

    private void initViews() {
        width = ScreenUtils.getScreenWidth(this);
        but_back = (Button) findViewById(R.id.but_back);
        but_update = (Button) findViewById(R.id.but_update);
        id_whorlview = (WhorlView) findViewById(R.id.id_whorlview);
        id_lv = (ListView) findViewById(R.id.id_lv);
        id_but_submitorder = (Button) findViewById(R.id.id_but_submitorder);
        id_tv_allprice = (TextView) findViewById(R.id.id_tv_allprice);
        id_but_changecustomer = (Button) findViewById(R.id.id_but_changecustomer);
        rl_empty = (PercentLinearLayout) findViewById(R.id.rl_empty);
        id_lv.setEmptyView(rl_empty);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_back:
                finish();
                break;
            case R.id.but_update:
                prestener.queryAll();
                break;
            case R.id.id_but_changecustomer:
                Intent intent = new Intent(this, ChangeCustomerAct.class);
                startActivityForResult(intent, 5);
                break;
            case R.id.id_but_submitorder:
                List<CartProBean> list_cartPro = new ArrayList<CartProBean>();
                List<Integer> list_cartProId = new ArrayList<Integer>();
                Gson gson = new Gson();
                for (CartBean cartBean : list_cart) {
                    CartProBean cartProBean = new CartProBean(cartBean.getCount(), cartBean.getProId());
                    list_cartPro.add(cartProBean);
                    list_cartProId.add(cartBean.getProId());
                }
                if (list_cartPro.size() == 0) {
                    Msg.showShort(this, "请添加产品到购物车");
                    return;
                }
                StringBuffer pro_str = new StringBuffer("{products:");
                pro_str.append(gson.toJson(list_cartPro));
                pro_str.append("}");
                if (customerId == 0) {
                    //没有选择客户
                    Msg.showShort(this, "请选择客户");
                } else {
                    map = new HashMap<String, String>();
                    map.put("token", (String) SPUtils.get(this, "token", ""));
                    map.put("productUserId", String.valueOf(customerId));
                    map.put("p", pro_str.toString());
                    Logger.e("map=" + map.toString());
                    prestener.delCartByAllId(this, list_cartProId);
                    //   prestener.d
                    //  prestener.submitOrder(map);
                }
                break;
        }
    }


    @Override
    public void showCart(List<CartBean> cartBeanList) {
        this.list_cart = cartBeanList;
        Double d = 0d;
        if (flag) {
            for (int i = 0; i < cartBeanList.size(); i++) {
                CartBean cartBean = cartBeanList.get(i);
                d += cartBean.getPrice() * cartBean.getCount();
                hashMap.put(cartBean, true);
            }
        } else {
            for (int i = 0; i < cartBeanList.size(); i++) {
                CartBean cartBean = cartBeanList.get(i);
                d += cartBean.getPrice() * cartBean.getCount();
                hashMap.put(cartBean, false);
            }
        }
        initAllPrice(d);
        adapter = new CartAdapter(this, this);
        adapter.bindData(cartBeanList);
        adapter.setHashMap(hashMap);
        id_lv.setAdapter(adapter);
        if (flag) {
            //删除显示，数量隐藏
            flag = false;
        } else {
            //数量隐藏。删除显示
            flag = true;
        }
    }

    @Override
    public void delCartByproId(List<CartBean> cartBeanList) {
        this.list_cart = cartBeanList;
        Double d = 0d;
        for (int i = 0; i < cartBeanList.size(); i++) {
            CartBean cartBean = cartBeanList.get(i);
            d += cartBean.getPrice() * cartBean.getCount();
            hashMap.put(cartBean, true);
        }
        initAllPrice(d);
        adapter = new CartAdapter(this, this);
        adapter.bindData(cartBeanList);
        adapter.setHashMap(hashMap);
        id_lv.setAdapter(adapter);
    }

    @Override
    public void updateCartByproId(List<CartBean> cartBeanList) {
        this.list_cart = cartBeanList;
        Double d = 0d;
        for (int i = 0; i < cartBeanList.size(); i++) {
            CartBean cartBean = cartBeanList.get(i);
            d += cartBean.getPrice() * cartBean.getCount();
            hashMap.put(cartBean, false);
        }
        initAllPrice(d);
        adapter = new CartAdapter(this, this);
        adapter.bindData(cartBeanList);
        adapter.setHashMap(hashMap);
        id_lv.setAdapter(adapter);
    }

    private void initAllPrice(Double d) {
        ArrayList<String> list_str = new ArrayList<String>();
        ArrayList<Integer> list_color = new ArrayList<Integer>();
        ArrayList<Float> list_tvsize = new ArrayList<Float>();
        list_str.add("  总价:");
        list_str.add(" ￥" + d);
        list_color.add(Color.parseColor("#000000"));
        list_color.add(Color.parseColor("#b40000"));
        list_tvsize.add((float) (0.01 * width));
        list_tvsize.add((float) (0.01 * width));
        TextViewUtil.setText(this, id_tv_allprice, list_str, list_color, list_tvsize);
    }

    @Override
    public void submitOrderSuccess(OrderDetailBean orderDetailBean) {
        Intent intent = new Intent(this, OrderDetailAct.class);
        intent.putExtra("orderId", orderDetailBean.getId());
        startActivity(intent);
    }


    @Override
    public void delCartByProAllIdSuccess() {
        prestener.queryAll();
        prestener.submitOrder(map);
    }

    @Override
    public void delCartByProAllIdFail() {
        Msg.showShort(this, "提交订单失败");
    }

    @Override
    public void showNetError() {
        Msg.showShort(this, "请连接网络");
    }

    @Override
    public void showRequestError() {
        Msg.showShort(this, "提交订单失败");
    }

    /*@Override
    public void showReLogin() {
        Intent intent = new Intent(this, LoginAct.class);
        intent.putExtra(ResultCode.LOGIN_RELOGIN, true);
        startActivity(intent);
    }*/


    @Override
    public void click(View v) {
        switch (v.getId()) {
            case R.id.id_but_del:
                int proId = (int) v.getTag();
                prestener.delCartById(proId);
                break;
            case R.id.id_but_reduce:
                CartBean cartBean_reduce = (CartBean) v.getTag();
                prestener.updateCountByproId(cartBean_reduce.getCount(), cartBean_reduce.getProId());
                break;
            case R.id.id_but_plus:
                CartBean cartBean_plus = (CartBean) v.getTag();
                prestener.updateCountByproId(cartBean_plus.getCount(), cartBean_plus.getProId());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 5) {
            id_but_changecustomer.setText(data.getStringExtra("customerName"));
            customerId = data.getIntExtra("customerId", 0);
        }
    }

    @Override
    public void hideBar() {
        id_whorlview.setVisibility(View.GONE);
        id_whorlview.stop();
    }

    @Override
    public void showBar() {
        id_whorlview.setVisibility(View.VISIBLE);
        id_whorlview.start();
    }
}

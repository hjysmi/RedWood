package com.onepiece.redwood;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.onepiece.redwood.cart.CartFrag;
import com.onepiece.redwood.customer.CustomerFrag;
import com.onepiece.redwood.menu.ProClassFrag;
import com.onepiece.redwood.order.OrderFrag;
import com.onepiece.redwood.self.SelfFrag;
import com.zhy.android.percent.support.PercentRelativeLayout;

/**
 * 红木管家首页
 */
public class HomePageAct extends FragmentActivity implements View.OnClickListener {
    private ImageView id_iv_pro;
    private TextView id_tv_pro;
    private ImageView id_iv_customer;
    private TextView id_tv_customer;
    private PercentRelativeLayout hp_order, hp_pro, hp_customer;
    private ImageView id_iv_order;
    private TextView id_tv_order;
    private PercentRelativeLayout hp_cart;
    private ImageView id_iv_cart;
    private TextView id_tv_cart;
    private PercentRelativeLayout hp_self;
    private ImageView id_iv_self;
    private TextView id_tv_self;
    private FragmentManager fm;
    ProClassFrag proClassFrag;
    CustomerFrag customerFrag;
    CartFrag cartFrag;
    SelfFrag selfFrag;
    OrderFrag orderFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        initViews();
        initValues();
        initEnents();
    }

    /**
     * 事件处理
     */
    private void initEnents() {
        hp_pro.setOnClickListener(this);
        hp_customer.setOnClickListener(this);
        hp_order.setOnClickListener(this);
        hp_cart.setOnClickListener(this);
        hp_self.setOnClickListener(this);

    }

    private void initValues() {
        fm = getSupportFragmentManager();
        setTab(1);
    }

    /**
     * 初始化view
     */
    private void initViews() {
        id_iv_pro = (ImageView) findViewById(R.id.id_iv_pro);
        id_tv_pro = (TextView) findViewById(R.id.id_tv_pro);
        id_iv_customer = (ImageView) findViewById(R.id.id_iv_customer);
        id_tv_customer = (TextView) findViewById(R.id.id_tv_customer);
        hp_order = (PercentRelativeLayout) findViewById(R.id.hp_order);
        hp_pro = (PercentRelativeLayout) findViewById(R.id.hp_pro);
        hp_customer = (PercentRelativeLayout) findViewById(R.id.hp_customer);
        id_iv_order = (ImageView) findViewById(R.id.id_iv_order);
        id_tv_order = (TextView) findViewById(R.id.id_tv_order);
        hp_cart = (PercentRelativeLayout) findViewById(R.id.hp_cart);
        id_iv_cart = (ImageView) findViewById(R.id.id_iv_cart);
        id_tv_cart = (TextView) findViewById(R.id.id_tv_cart);
        hp_self = (PercentRelativeLayout) findViewById(R.id.hp_self);
        id_iv_self = (ImageView) findViewById(R.id.id_iv_self);
        id_tv_self = (TextView) findViewById(R.id.id_tv_self);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hp_pro:
                setTab(1);
                break;
            case R.id.hp_customer:
                setTab(2);
                break;
            case R.id.hp_order:
                setTab(3);
                break;
            case R.id.hp_cart:
                setTab(4);
                break;
            case R.id.hp_self:
                setTab(5);
                break;
        }
    }

    private void setTab(int i) {
        clearColor();
        switch (i) {
            case 1:
                //产品
                hp_pro.setBackgroundColor(Color.parseColor("#b40000"));
                id_iv_pro.setImageResource(R.drawable.hp_pro_white);
                id_tv_pro.setTextColor(Color.parseColor("#ffffff"));
                showFragments(1);
                break;
            case 2:
                //客户
                hp_customer.setBackgroundColor(Color.parseColor("#b40000"));
                id_iv_customer.setImageResource(R.drawable.hp_customer_white);
                id_tv_customer.setTextColor(Color.parseColor("#ffffff"));
                showFragments(2);
                break;
            case 3:
                //订单
                hp_order.setBackgroundColor(Color.parseColor("#b40000"));
                id_iv_order.setImageResource(R.drawable.hp_order_white);
                id_tv_order.setTextColor(Color.parseColor("#ffffff"));
                showFragments(3);
                break;
            case 4:
                //购物车
                hp_cart.setBackgroundColor(Color.parseColor("#b40000"));
                id_iv_cart.setImageResource(R.drawable.hp_cart_white);
                id_tv_cart.setTextColor(Color.parseColor("#ffffff"));
                showFragments(4);
                break;
            case 5:
                //我
                hp_self.setBackgroundColor(Color.parseColor("#b40000"));
                id_iv_self.setImageResource(R.drawable.hp_self_white);
                id_tv_self.setTextColor(Color.parseColor("#ffffff"));
                showFragments(5);
                break;
        }

    }

    private void showFragments(int i) {
        FragmentTransaction ft = fm.beginTransaction();
        hideFragments(ft);
        switch (i) {
            case 1:
                //产品
                if (proClassFrag != null) {
                    ft.show(proClassFrag);
                } else {
                    proClassFrag = new ProClassFrag();
                    ft.add(R.id.ll_main, proClassFrag);
                }

                break;
            case 2:
                //客户
                customerFrag = new CustomerFrag();
                ft.add(R.id.ll_main, customerFrag);
                break;
            case 3:
                //订单
                orderFrag = new OrderFrag();
                ft.add(R.id.ll_main, orderFrag);
                break;
            case 4:
                //购物车
                cartFrag = new CartFrag();
                ft.add(R.id.ll_main, cartFrag);
                break;
            case 5:
                //我
                selfFrag = new SelfFrag();
                ft.add(R.id.ll_main, selfFrag);
                break;

        }
        ft.commit();
    }

    private void hideFragments(FragmentTransaction ft) {
        if (proClassFrag != null) ft.hide(proClassFrag);
        if (customerFrag != null) ft.hide(customerFrag);
        if (cartFrag != null) ft.hide(cartFrag);
        if (selfFrag != null) ft.hide(selfFrag);
        if (orderFrag != null) ft.hide(orderFrag);
    }

    private void clearColor() {
        hp_pro.setBackgroundColor(Color.parseColor("#ffffff"));
        hp_customer.setBackgroundColor(Color.parseColor("#ffffff"));
        hp_order.setBackgroundColor(Color.parseColor("#ffffff"));
        hp_cart.setBackgroundColor(Color.parseColor("#ffffff"));
        hp_self.setBackgroundColor(Color.parseColor("#ffffff"));
        id_iv_pro.setImageResource(R.drawable.hp_pro_gray);
        id_iv_customer.setImageResource(R.drawable.hp_customer_gray);
        id_iv_order.setImageResource(R.drawable.hp_order_gray);
        id_iv_cart.setImageResource(R.drawable.hp_cart_gray);
        id_iv_self.setImageResource(R.drawable.hp_self_gray);
        id_tv_pro.setTextColor(Color.parseColor("#7a7e83"));
        id_tv_customer.setTextColor(Color.parseColor("#7a7e83"));
        id_tv_order.setTextColor(Color.parseColor("#7a7e83"));
        id_tv_cart.setTextColor(Color.parseColor("#7a7e83"));
        id_tv_self.setTextColor(Color.parseColor("#7a7e83"));
    }
}

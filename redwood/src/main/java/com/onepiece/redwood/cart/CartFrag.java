package com.onepiece.redwood.cart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onepiece.redwood.BaseFragment;
import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.login.LoginAct;
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
 * 购物车管理
 */
public class CartFrag extends BaseFragment implements ICartView, View.OnClickListener, CartAdapter.Callback {
    View mView;
    private ImageView id_iv_update;
    private Button id_but_submitorder;
    private WhorlView id_whorlview;
    private TextView id_tv_allprice;
    private Button id_but_changecustomer;
    private PercentLinearLayout rl_empty;
    ListView lv;
    List<String> list = new ArrayList<String>();
    CartAdapter adapter;
    List<CartBean> list_cart = new ArrayList<CartBean>();
    boolean isDel = false;
    private CartDao dao;
    private int width;
    private int customerId = 0;
    BroadCartCart broadCartCart;
    HashMap<CartBean, Boolean> hashMap = new HashMap<CartBean, Boolean>();
    private CartPresenter presenter = new CartPresenter(this);
    private Map<String, String> map;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.cartfrag, container, false);
        initViews();
        initValues();
        initEvents();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initEvents() {
        id_but_submitorder.setOnClickListener(this);
        id_iv_update.setOnClickListener(this);
        id_but_changecustomer.setOnClickListener(this);
    }

    private void initValues() {
        registercast();
        presenter.queryAll(getActivity());

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
        TextViewUtil.setText(getActivity(), id_tv_allprice, list_str, list_color, list_tvsize);
    }

    private void initViews() {

        width = ScreenUtils.getScreenWidth(getActivity());
        lv = (ListView) mView.findViewById(R.id.id_lv);
        id_tv_allprice = (TextView) mView.findViewById(R.id.id_tv_allprice);
        id_whorlview = (WhorlView) mView.findViewById(R.id.id_whorlview);
        id_iv_update = (ImageView) mView.findViewById(R.id.id_iv_update);
        rl_empty = (PercentLinearLayout) mView.findViewById(R.id.rl_empty);
        id_but_changecustomer = (Button) mView.findViewById(R.id.id_but_changecustomer);
        id_but_submitorder = (Button) mView.findViewById(R.id.id_but_submitorder);
        lv.setEmptyView(rl_empty);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_iv_update:
                presenter.queryAll(getActivity());
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
                StringBuffer pro_str = new StringBuffer("{products:");
                pro_str.append(gson.toJson(list_cartPro));
                pro_str.append("}");
                if (list_cartPro.size() == 0) {
                    Msg.showShort(getActivity(), "请添加产品到购物车");
                    return;
                }
                if (customerId == 0) {
                    //没有选择客户
                    Msg.showShort(getActivity(), "请选择客户");
                } else {
                    map = new HashMap<String, String>();
                    map.put("token", (String) SPUtils.get(getActivity(), "token", ""));
                    map.put("productUserId", String.valueOf(customerId));
                    map.put("p", pro_str.toString());
                    Logger.e("map=" + map.toString());
                    Msg.showShort(getActivity(), "proId=" + list_cartProId.toString());
                    presenter.delCartByAllId(getActivity(), list_cartProId);
                }

                break;
            case R.id.id_but_changecustomer:
                Intent intent = new Intent(getActivity(), ChangeCustomerAct.class);
                intent.putExtra(ResultCode.IS_CUSTOMER_CHANGE, true);
                startActivityForResult(intent, 5);
                break;
            default:
                break;
        }
    }

    @Override
    public void showCart(List<CartBean> cartBeanList) {
        this.list_cart = cartBeanList;
        hashMap = new HashMap<CartBean, Boolean>();
        Double d = 0d;
        if (isDel) {
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
        adapter = new CartAdapter(getActivity(), this);
        adapter.bindData(cartBeanList);
        initAllPrice(d);
        adapter.setHashMap(hashMap);
        lv.setAdapter(adapter);
        if (isDel) {
            isDel = false;
        } else {
            isDel = true;
        }
    }

    @Override
    public void SubmitOrderSuccess(OrderDetailBean orderDetailBean) {
        Intent intent = new Intent(getActivity(), OrderDetailAct.class);
        intent.putExtra("orderId", orderDetailBean.getId());
        getActivity().startActivity(intent);
    }

    @Override
    public void delCartByproId(List<CartBean> cartBeanList) {
        this.list_cart = cartBeanList;
        hashMap = new HashMap<CartBean, Boolean>();
        Double d = 0d;
        for (int i = 0; i < cartBeanList.size(); i++) {
            CartBean cartBean = cartBeanList.get(i);
            d += cartBean.getPrice() * cartBean.getCount();
            hashMap.put(cartBean, true);
        }
        adapter = new CartAdapter(getActivity(), this);
        adapter.bindData(cartBeanList);
        initAllPrice(d);
        adapter.setHashMap(hashMap);
        lv.setAdapter(adapter);
    }

    @Override
    public void updateCartByproId(List<CartBean> cartBeanList) {
        this.list_cart = cartBeanList;
        hashMap = new HashMap<CartBean, Boolean>();
        Double d = 0d;
        for (int i = 0; i < cartBeanList.size(); i++) {
            CartBean cartBean = cartBeanList.get(i);
            d += cartBean.getPrice() * cartBean.getCount();
            hashMap.put(cartBean, false);
        }
        adapter = new CartAdapter(getActivity(), this);
        adapter.bindData(cartBeanList);
        initAllPrice(d);
        adapter.setHashMap(hashMap);
        lv.setAdapter(adapter);
    }

    @Override
    public void delCartByProAllIdSuccess() {
        presenter.queryAll(getActivity());
        // Logger.e("map=>>"+map.toString());
        presenter.submitOrder(map);
    }

    @Override
    public void delCartByProAllIdFail() {
        Msg.showShort(getActivity(), "提交订单失败");
    }

    @Override
    public void showNetError() {
        Msg.showShort(getActivity(), "请连接网络");
    }

    @Override
    public void showRequestError() {
        Msg.showShort(getActivity(), "提交订单失败");
    }

    @Override
    public void showReLogin() {
        Intent intent = new Intent(getActivity(), LoginAct.class);
        intent.putExtra(ResultCode.LOGIN_RELOGIN,true);
        getActivity().startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 5) {
            id_but_changecustomer.setText(data.getStringExtra("customerName"));
            customerId = data.getIntExtra("customerId", 0);
        }
    }

    @Override
    public void click(View v) {
        switch (v.getId()) {
            case R.id.id_but_del:
                int proId = (int) v.getTag();
                presenter.delCartById(getActivity(), proId);
                break;
            case R.id.id_but_reduce:
                CartBean cartBean_reduce = (CartBean) v.getTag();
                presenter.updateCountByproId(getActivity(), cartBean_reduce.getCount(), cartBean_reduce.getProId());
                break;
            case R.id.id_but_plus:
                CartBean cartbean_plus = (CartBean) v.getTag();
                presenter.updateCountByproId(getActivity(), cartbean_plus.getCount(), cartbean_plus.getProId());
                break;

        }
    }


    @Override
    public void showBar() {
        id_whorlview.setVisibility(View.VISIBLE);
        id_whorlview.start();
    }

    @Override
    public void hideBar() {
        id_whorlview.setVisibility(View.GONE);
        id_whorlview.stop();
    }

    public void registercast() {
        broadCartCart = new BroadCartCart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ResultCode.ACTION_CUSTOMER_CHANGE);
        getActivity().registerReceiver(broadCartCart, filter);
    }

    public class BroadCartCart extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case ResultCode.ACTION_CUSTOMER_CHANGE:
                    String name = intent.getStringExtra(ResultCode.CUSTOMER_NAME);
                    Integer id = intent.getIntExtra(ResultCode.CUSTOMER_Id, 0);
                    id_but_changecustomer.setText(name);
                    customerId = id;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadCartCart);
    }
}

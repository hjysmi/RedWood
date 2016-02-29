package com.onepiece.redwood.order;

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
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.onepiece.redwood.BaseFragment;
import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.broadcast.BroadCastRelogin;
import com.onepiece.redwood.broadcast.IReLoginReceive;
import com.onepiece.redwood.cart.ChangeCustomerAct;
import com.onepiece.redwood.login.LoginAct;
import com.onepiece.redwood.order.cancel.CancelOrderAct;
import com.onepiece.redwood.order.orderdetail.OrderDetailAct;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;
import com.onepiece.redwood.order.orderdetail.ProductOrderDetailsBean;
import com.onepiece.redwood.prodetail.ProDetailAct;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.SPUtils;
import com.onepiece.redwood.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单查询
 */
public class OrderFrag extends BaseFragment implements IOrderView, View.OnClickListener, AbsListView.OnScrollListener, OrderAdapter.Callback {
    private View mView;
    private TextView id_tv_nofinish, id_tv_finish, id_tv_pending;
    private OrderPrestener prestener = new OrderPrestener(this);
    private WhorlView id_whorlview;
    private Button id_but_close;
    private ListView id_lv;
    private Map<String, String> map;
    private int page = 1;
    private int rows = 12;
    private int status = 0;
    int firstVisibleItem = 0, totalItemCount = 0, visibleItemCount = 0;
    private OrderAdapter adapter;
    List<OrderDetailBean> total = new ArrayList<OrderDetailBean>();
    BroadCastRelogin broadCastRelogin;
    //OrderNewAdapter adapter;
    int width;
    BroadCast broadCast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.orderfrag, null);
        initViews();
        initValues();
        initEvents();
        return mView;
    }

    private void initEvents() {
        id_tv_nofinish.setOnClickListener(this);
        id_tv_finish.setOnClickListener(this);
        id_tv_pending.setOnClickListener(this);
        id_but_close.setOnClickListener(this);
        id_lv.setOnScrollListener(this);
        broadCastRelogin.setiReLoginReceive(new IReLoginReceive() {
            @Override
            public void OnReceiceReLogin() {
                setTab(1);
            }
        });
    }

    private void initValues() {
        registerBroadcastReceiver();
        resigster();
        setTab(1);
    }

    private void initViews() {
        id_tv_nofinish = (TextView) mView.findViewById(R.id.id_tv_nofinish);
        id_tv_finish = (TextView) mView.findViewById(R.id.id_tv_finish);
        id_tv_pending = (TextView) mView.findViewById(R.id.id_tv_pending);
        id_whorlview = (WhorlView) mView.findViewById(R.id.id_whorlview);
        id_but_close = (Button) mView.findViewById(R.id.id_but_close);
        id_lv = (ListView) mView.findViewById(R.id.id_lv);
        width = ScreenUtils.getScreenWidth(getActivity());
        //  id_lv.setDividerHeight((int) (0.012 * width));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_tv_nofinish:
                setTab(1);
                break;
            case R.id.id_tv_finish:
                setTab(2);
                break;
            case R.id.id_tv_pending:
                setTab(3);
                break;
            case R.id.id_but_close:
                Intent intent = new Intent(getActivity(), CancelOrderAct.class);
                getActivity().startActivity(intent);
                break;

        }
    }

    private void clearColor() {
        id_tv_finish.setTextColor(Color.parseColor("#555555"));
        id_tv_nofinish.setTextColor(Color.parseColor("#555555"));
        id_tv_pending.setTextColor(Color.parseColor("#555555"));
    }

    private void setTab(int i) {
        clearColor();
        page = 1;
        switch (i) {
            case 1:
                total = new ArrayList<OrderDetailBean>();
                id_tv_nofinish.setTextColor(Color.parseColor("#b40000"));
                map = new HashMap<String, String>();
                status = 0;
                map.put("token", (String) SPUtils.get(getActivity(), "token", ""));
                map.put("page", String.valueOf(page));
                map.put("rows", String.valueOf(rows));
                map.put("status", String.valueOf(status));
                prestener.getOrderListInfo(map);
                break;
            case 2:
                total = new ArrayList<OrderDetailBean>();
                id_tv_finish.setTextColor(Color.parseColor("#b40000"));
                map = new HashMap<String, String>();
                status = 1;
                map.put("token", (String) SPUtils.get(getActivity(), "token", ""));
                map.put("page", String.valueOf(page));
                map.put("rows", String.valueOf(rows));
                map.put("status", String.valueOf(status));
                prestener.getOrderListInfo(map);
                break;
            case 3:
                total = new ArrayList<OrderDetailBean>();
                id_tv_pending.setTextColor(Color.parseColor("#b40000"));
                map = new HashMap<String, String>();
                status = 3;
                map.put("token", (String) SPUtils.get(getActivity(), "token", ""));
                map.put("page", String.valueOf(page));
                map.put("rows", String.valueOf(rows));
                map.put("status", String.valueOf(status));
                prestener.getOrderListInfo(map);
                break;
        }
    }

    @Override
    public void showOrderListSuccess(List<OrderDetailBean> orderDetailBeanList) {
        total.addAll(orderDetailBeanList);
        List<ProductOrderDetailsBean> list = new ArrayList<ProductOrderDetailsBean>();
        for (OrderDetailBean ob : total) {
            List<ProductOrderDetailsBean> productOrderDetailsList = ob.getProductOrderDetailsList();
            for (ProductOrderDetailsBean pdb : productOrderDetailsList) {
                pdb.setOrderDetailBean(ob);
                list.add(pdb);
            }
        }
        adapter = new OrderAdapter(getActivity(), R.layout.order_item, this);
        adapter.bindDatas(list);
        if (page == 1) {
            id_lv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        page++;
    }


    @Override
    public void showCancelOrderSuccess(OrderDetailBean orderDetailBean) {
        Msg.showShort(getActivity(), "订单已关闭");
        OrderDetailBean rem = null;
        for (OrderDetailBean odb : total) {
            if (odb.getId() == orderDetailBean.getId()) {
                rem = odb;
            }
        }
        total.remove(rem);
        List<ProductOrderDetailsBean> list = new ArrayList<ProductOrderDetailsBean>();
        for (OrderDetailBean ob : total) {
            List<ProductOrderDetailsBean> productOrderDetailsList = ob.getProductOrderDetailsList();
            for (ProductOrderDetailsBean pdb : productOrderDetailsList) {
                pdb.setOrderDetailBean(ob);
                list.add(pdb);
            }
        }
        adapter.bindDatas(list);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void showCancelOrderFail() {

    }

    @Override
    public void showNetError() {
        Msg.showShort(getActivity(), "请连接网络");
    }

    @Override
    public void showRequestError() {
        Msg.showShort(getActivity(), "请求失败");
    }

    @Override
    public void showReLogin() {
        Intent intent = new Intent(getActivity(), LoginAct.class);
        intent.putExtra(ResultCode.LOGIN_RELOGIN, true);
        getActivity().startActivity(intent);
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

    /**
     * 取消订单
     * 继续交易
     * 自定义的点击事件
     *
     * @param v
     */
    @Override
    public void click(View v) {

        switch (v.getId()) {
            case R.id.in_order_item_bottom:
                Integer id3 = (Integer) v.getTag();
                Intent intent_order3 = new Intent(getActivity(), OrderDetailAct.class);
                intent_order3.putExtra("orderId", id3);
                getActivity().startActivity(intent_order3);
                break;
            case R.id.in_order_item_top:
                Integer id2 = (Integer) v.getTag();
                Intent intent_order = new Intent(getActivity(), OrderDetailAct.class);
                intent_order.putExtra("orderId", id2);
                getActivity().startActivity(intent_order);
                break;

            case R.id.order_pro_item:
                int proId = (int) v.getTag();
                Intent intent_pro = new Intent(getActivity(), ProDetailAct.class);
                intent_pro.putExtra(ResultCode.PRO_ID, proId);
                getActivity().startActivity(intent_pro);
                break;
            case R.id.id_but_del:
                Integer id = (Integer) v.getTag();
                String token = (String) SPUtils.get(getActivity(), "token", "");
                prestener.cancelOrderById(token, id);
                break;
            case R.id.id_but_gotrading:
                Integer id1 = (Integer) v.getTag();
                //继续交易
                Intent intent = new Intent(getActivity(), ChangeCustomerAct.class);
                intent.putExtra(ResultCode.ORDER_TARDING_ISTRAD, true);
                intent.putExtra(ResultCode.ORDER_TARDING_ORDERID, id1);
                startActivity(intent);
                break;
            default:
                break;
        }

    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            if (firstVisibleItem >= totalItemCount - 10) {
                map.put("page", String.valueOf(page));
                prestener.getOrderListInfo(map);
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    private void resigster() {
        broadCast = new BroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ResultCode.ORDER_TARDING_ISTRADDATA);
        getActivity().registerReceiver(broadCast, filter);
    }

    public class BroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ResultCode.ORDER_TARDING_ISTRADDATA)) {
                setTab(1);
            }
        }
    }

    private void registerBroadcastReceiver() {
        broadCastRelogin = new BroadCastRelogin();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ResultCode.BROAD_RELOGIN_ACTION_CUSTOMER);
        getActivity().registerReceiver(broadCastRelogin, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadCast);
        getActivity().unregisterReceiver(broadCastRelogin);
    }
}

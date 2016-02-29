package com.onepiece.redwood.order.cancel;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.broadcast.BroadCastRelogin;
import com.onepiece.redwood.broadcast.IReLoginReceive;
import com.onepiece.redwood.login.LoginAct;
import com.onepiece.redwood.order.OrderAdapter;
import com.onepiece.redwood.order.OrderPrestener;
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
 * 查询已取消订单
 */
public class CancelOrderAct extends Activity implements ICancelOrderView, View.OnClickListener, AbsListView.OnScrollListener, OrderAdapter.Callback {
    private Button id_but_back;
    private ListView id_lv;
    private WhorlView id_whorlview;
    int width;
    private OrderPrestener prestener = new OrderPrestener(this);
    private OrderAdapter adapter;
    Map<String, String> map;
    int page = 1;
    int rows = 12;
    int status = 2;
    int firstVisibleItem = 0, totalItemCount = 0, visibleItemCount = 0;
    List<OrderDetailBean> total = new ArrayList<OrderDetailBean>();
    private BroadCastRelogin broadCastRelogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancelorder);
        initViews();
        initValues();
        initEvents();
    }

    private void initEvents() {
        id_but_back.setOnClickListener(this);
        id_lv.setOnScrollListener(this);
        broadCastRelogin.setiReLoginReceive(new IReLoginReceive() {
            @Override
            public void OnReceiceReLogin() {
                page=1;
                loadData();
            }
        });
    }

    private void initValues() {
        registerBroadcastReceiver();
        loadData();
    }
    private void loadData(){
        map = new HashMap<String, String>();
        map.put("token", (String) SPUtils.get(this, "token", ""));
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(rows));
        map.put("status", String.valueOf(status));
        prestener.getCancelOrderListInfo(map);
    }
    private void initViews() {
        width = ScreenUtils.getScreenWidth(this);
        id_but_back = (Button) findViewById(R.id.id_but_back);
        id_lv = (ListView) findViewById(R.id.id_lv);
        id_whorlview = (WhorlView) findViewById(R.id.id_whorlview);
        //   id_lv.setDividerHeight((int) (0.02 * width));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_but_back:
                finish();
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

    @Override
    public void ShowOrderListSuccess(List<OrderDetailBean> orderDetailBeanList) {
        total.addAll(orderDetailBeanList);
        List<ProductOrderDetailsBean> list = new ArrayList<ProductOrderDetailsBean>();
        for (OrderDetailBean ob : orderDetailBeanList) {
            List<ProductOrderDetailsBean> productOrderDetailsList = ob.getProductOrderDetailsList();
            for (ProductOrderDetailsBean pdb : productOrderDetailsList) {
                pdb.setOrderDetailBean(ob);
                list.add(pdb);
            }
        }
        adapter = new OrderAdapter(this, R.layout.order_item, this);
        adapter.bindDatas(list);
        if (page == 1) {
            id_lv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        page++;
    }

    @Override
    public void showNetError() {
        Msg.showShort(this, "请连接网络");
    }

    @Override
    public void showRequestError() {
        Msg.showShort(this,"请求失败");
    }

    @Override
    public void showReLogin() {
        Intent intent = new Intent(this, LoginAct.class);
        intent.putExtra(ResultCode.LOGIN_RELOGIN, true);
        startActivity(intent);
    }


    @Override
    public void click(View v) {

        switch (v.getId()) {
            case R.id.in_order_item_bottom:
                Integer id3 = (Integer) v.getTag();
                Intent intent_order3 = new Intent(this, OrderDetailAct.class);
                intent_order3.putExtra("orderId", id3);
                startActivity(intent_order3);
                break;
            case R.id.in_order_item_top:
                Integer id2 = (Integer) v.getTag();
                Intent intent_order = new Intent(this, OrderDetailAct.class);
                intent_order.putExtra("orderId", id2);
                startActivity(intent_order);
                break;

            case R.id.order_pro_item:
                int proId = (int) v.getTag();
                Intent intent_pro = new Intent(this, ProDetailAct.class);
                intent_pro.putExtra(ResultCode.PRO_ID, proId);
                startActivity(intent_pro);
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
                prestener.getCancelOrderListInfo(map);
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    private void registerBroadcastReceiver() {
        broadCastRelogin = new BroadCastRelogin();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ResultCode.BROAD_RELOGIN_ACTION_CUSTOMER);
       registerReceiver(broadCastRelogin, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadCastRelogin);
    }
}

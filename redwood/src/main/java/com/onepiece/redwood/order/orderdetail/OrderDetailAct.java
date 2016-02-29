package com.onepiece.redwood.order.orderdetail;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.broadcast.BroadCastRelogin;
import com.onepiece.redwood.broadcast.IReLoginReceive;
import com.onepiece.redwood.login.LoginAct;
import com.onepiece.redwood.order.remark.RemarkAct;
import com.onepiece.redwood.order.sign.SignAct;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.SPUtils;

/**
 * 订单完成
 */
public class OrderDetailAct extends Activity implements IOrderDetailView, View.OnClickListener {
    private Button id_but_back;
    private WhorlView id_whorlview;
    private TextView id_tv_name, id_tv_phone, id_tv_address, id_tv_num, id_tv_time;
    private ListView id_lv;
    private Button id_but_sign, id_but_remark;
    int orderId = 0;
    private OrderDetailPrestener prestener = new OrderDetailPrestener(this);
    private OrderDetailAdapter adapter;
    private OrderDetailBean orderDetailBean;
    private BroadCastRelogin broadCastRelogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdetail);
        initViews();
        initValues();
        initEvents();
    }

    private void initEvents() {
        id_but_back.setOnClickListener(this);
        id_but_sign.setOnClickListener(this);
        id_but_remark.setOnClickListener(this);
        /**重新登录*/
        broadCastRelogin.setiReLoginReceive(new IReLoginReceive() {
            @Override
            public void OnReceiceReLogin() {
                prestener.getOrderDetailById((String) SPUtils.get(OrderDetailAct.this, "token", ""), orderId);
            }
        });
    }

    private void initValues() {
        registerBroadcastReceiver();
        orderId = getIntent().getIntExtra("orderId", 0);
        prestener.getOrderDetailById((String) SPUtils.get(this, "token", ""), orderId);
    }

    private void initViews() {
        id_but_back = (Button) findViewById(R.id.id_but_back);
        id_whorlview = (WhorlView) findViewById(R.id.id_whorlview);
        id_tv_name = (TextView) findViewById(R.id.id_tv_name);
        id_tv_phone = (TextView) findViewById(R.id.id_tv_phone);
        id_tv_address = (TextView) findViewById(R.id.id_tv_address);
        id_tv_num = (TextView) findViewById(R.id.id_tv_num);
        id_tv_time = (TextView) findViewById(R.id.id_tv_time);
        id_lv = (ListView) findViewById(R.id.id_lv);
        id_but_sign = (Button) findViewById(R.id.id_but_sign);
        id_but_remark = (Button) findViewById(R.id.id_but_remark);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_but_back:
                finish();
                break;
            case R.id.id_but_sign:
                Intent intent = new Intent(this, SignAct.class);
                intent.putExtra(ResultCode.ORDER_TARDING_ORDERID, orderId);
                startActivity(intent);
                break;
            case R.id.id_but_remark:
                Intent intent1 = new Intent(this, RemarkAct.class);
                intent1.putExtra(ResultCode.REMARK_TAG, orderDetailBean.getRemark());
                intent1.putExtra(ResultCode.ORDER_TARDING_ORDERID, orderId);
                startActivity(intent1);
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
    public void showOrderDetailSuccess(OrderDetailBean orderDetailBean) {
        this.orderDetailBean = orderDetailBean;
        id_tv_name.setText(orderDetailBean.getOrderName());
        id_tv_phone.setText(orderDetailBean.getOrderPhone());
        id_tv_address.setText(orderDetailBean.getOrderAddress());
        id_tv_num.setText("订单: " + orderDetailBean.getOrderNumber());
        id_tv_time.setText("下单时间: " + orderDetailBean.getOrderDateTime());
        adapter = new OrderDetailAdapter(this, R.layout.orderdetail__item);
        adapter.bindDatas(orderDetailBean.getProductOrderDetailsList());
        id_lv.setAdapter(adapter);
    }

    @Override
    public void showNetError() {
        Msg.showShort(this, "请连接网络");
    }

    @Override
    public void showRequestError() {
        Msg.showShort(this, "请求失败");
    }

    @Override
    public void showReLogin() {
        Intent intent = new Intent(this, LoginAct.class);
        intent.putExtra(ResultCode.LOGIN_RELOGIN, true);
        startActivity(intent);
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

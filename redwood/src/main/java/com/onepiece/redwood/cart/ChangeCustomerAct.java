package com.onepiece.redwood.cart;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.broadcast.BroadCastRelogin;
import com.onepiece.redwood.broadcast.IReLoginReceive;
import com.onepiece.redwood.customer.CustomerBean;
import com.onepiece.redwood.customer.CustomerFragPresenter;
import com.onepiece.redwood.customer.ICustomerFragView;
import com.onepiece.redwood.customer.add.AddCustomerAct;
import com.onepiece.redwood.login.LoginAct;
import com.onepiece.redwood.order.OrderPrestener;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.SPUtils;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户选择
 */
public class ChangeCustomerAct extends Activity implements ICustomerFragView, View.OnClickListener {
    private Button but_back;
    private WhorlView id_whorlview;
    private Button but_newcustomer;
    private ListView id_lv;
    private CustomerChangeAdapter adapter;
    private CustomerFragPresenter presenter = new CustomerFragPresenter(this);
    private OrderPrestener orderPrestener = new OrderPrestener(this);
    private BroadCastRelogin broadCastRelogin;
    private Map<String, String> map;
    public static ChangeCustomerAct instance;
    private Integer orderId = 0;
    private Boolean isTrading = false;
    List<CustomerBean> customerList;
    private Boolean isCustomerChange = false;
    String token;
    int page = 1;
    int count = 0;
    int rows = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerchange);
        instance = this;
        initViews();
        initValues();
        initEvents();
    }

    private void initEvents() {
        but_back.setOnClickListener(this);
        but_newcustomer.setOnClickListener(this);
        id_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerBean customerBean = customerList.get(position);

                if (!isTrading) {
                    //返回购物车
                    Intent intent = new Intent();
                    intent.putExtra("customerId", customerBean.getId());
                    intent.putExtra("customerName", customerBean.getName());
                    if (ChangeCustomerAct.instance != null) {
                        ChangeCustomerAct.instance.setResult(5, intent);
                        ChangeCustomerAct.instance.finish();
                    }
                } else {
                    //继续交易
                    token = (String) SPUtils.get(ChangeCustomerAct.this, "token", "");
                    orderPrestener.pendingTrad(token, orderId, customerBean.getId());

                }

            }
        });
        /**重新登录监听事件*/
        broadCastRelogin.setiReLoginReceive(new IReLoginReceive() {
            @Override
            public void OnReceiceReLogin() {
                map = new HashMap<String, String>();
                map.put("token", (String) SPUtils.get(ChangeCustomerAct.this, "token", ""));
                map.put("page", String.valueOf(page));
                map.put("rows", String.valueOf(rows));
                map.put("count", String.valueOf(count));
                presenter.getCustomerInfo(map);
            }
        });
    }

    private void initValues() {
        registerBroadcastReceiver();
        isTrading = getIntent().getBooleanExtra(ResultCode.ORDER_TARDING_ISTRAD, false);
        orderId = getIntent().getIntExtra(ResultCode.ORDER_TARDING_ORDERID, 0);
        isCustomerChange = getIntent().getBooleanExtra(ResultCode.IS_CUSTOMER_CHANGE, false);
        map = new HashMap<String, String>();
        map.put("token", (String) SPUtils.get(this, "token", ""));
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(rows));
        map.put("count", String.valueOf(count));
        presenter.getCustomerInfo(map);
    }

    private void initViews() {
        but_back = (Button) findViewById(R.id.but_back);
        id_whorlview = (WhorlView) findViewById(R.id.id_whorlview);
        id_lv = (ListView) findViewById(R.id.id_lv);
        but_newcustomer = (Button) findViewById(R.id.but_newcustomer);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_back:
                finish();
                break;
            case R.id.but_newcustomer:
                Intent intent = new Intent(this, AddCustomerAct.class);
                intent.putExtra(ResultCode.IS_CUSTOMER_CHANGE, isCustomerChange);
                intent.putExtra(ResultCode.ORDER_TARDING_ISTRAD, isTrading);
                intent.putExtra(ResultCode.ORDER_TARDING_ORDERID, orderId);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void showCustomerListSuccess(List<CustomerBean> customerBeanList) {
        this.customerList = customerBeanList;
        adapter = new CustomerChangeAdapter(this, R.layout.item_customerfrag);
        adapter.bindDatas(customerBeanList);
        id_lv.setAdapter(adapter);
    }

    @Override
    public void showPendingTradSuccess() {

        Intent intent = new Intent();
        intent.setAction(ResultCode.ORDER_TARDING_ISTRADDATA);
        sendBroadcast(intent);
        finish();
    }

    @Override
    public void showPendingTradFail() {

    }

    @Override
    public void OnNetError() {
        Msg.showShort(this, "请连接网络");
    }

    @Override
    public void OnRequestFail() {
        Msg.showShort(this, "请求失败");
    }

    @Override
    public void showReLogin() {
        Logger.e("url>>1");
        Intent intent = new Intent(this, LoginAct.class);
        intent.putExtra(ResultCode.LOGIN_RELOGIN, true);
        startActivity(intent);
        //  startActivityForResult(intent,6);
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

    private void registerBroadcastReceiver() {
        broadCastRelogin = new BroadCastRelogin();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ResultCode.BROAD_RELOGIN_ACTION_CUSTOMER);
        registerReceiver(broadCastRelogin, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadCastRelogin);
    }
}

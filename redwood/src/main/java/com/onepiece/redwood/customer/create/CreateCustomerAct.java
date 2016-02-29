package com.onepiece.redwood.customer.create;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.broadcast.BroadCastRelogin;
import com.onepiece.redwood.broadcast.IReLoginReceive;
import com.onepiece.redwood.cart.ChangeCustomerAct;
import com.onepiece.redwood.customer.CustomerBean;
import com.onepiece.redwood.customer.add.AddCustomerAct;
import com.onepiece.redwood.customer.add.AreasBean;
import com.onepiece.redwood.customer.add.AreasChangeAct;
import com.onepiece.redwood.customer.detail.CustomerDetailAct;
import com.onepiece.redwood.login.LoginAct;
import com.onepiece.redwood.order.OrderPrestener;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.SPUtils;
import com.onepiece.redwood.utils.ScreenUtils;
import com.onepiece.redwood.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建新客户
 */
public class CreateCustomerAct extends Activity implements View.OnClickListener, ICreateCustomerView {
    public final static int KEYCODE_PROVINCE = 1;
    public final static int KEYCODE_CITY = 2;
    public final static String PCODE = "pcode";
    public final static String PCODE_NAME = "pcodename";
    private Button id_but_sure, id_but_cancel;
    private TextView id_tv_province;
    private TextView id_tv_city;
    private EditText id_et_phone, id_et_name;
    private WhorlView id_whorlview;
    private AreasBean areasBean = new AreasBean();
    private CreateCustomerPresenter presenter = new CreateCustomerPresenter(this);
    private OrderPrestener orderPrestener = new OrderPrestener(this);
    private Map<String, String> map = new HashMap<String, String>();
    int width;
    Integer businessId = 0;
    private Boolean isCustomerChange = false;
    private Integer orderId = 0;
    private Boolean isTrading = false;
    private BroadCastRelogin broadCastRelogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createcustomer);
        initViews();
        initValues();
        initEvents();
    }

    private void initEvents() {
        id_but_cancel.setOnClickListener(this);
        id_but_sure.setOnClickListener(this);
        id_tv_province.setOnClickListener(this);
        id_tv_city.setOnClickListener(this);
        /**重新登录监听*/
        broadCastRelogin.setiReLoginReceive(new IReLoginReceive() {
            @Override
            public void OnReceiceReLogin() {
                createCustomer();
                //   Msg.showShort(CreateCustomerAct.this, "请重新创建用户");
            }
        });
    }

    private void initValues() {
        registerBroadcastReceiver();
        businessId = (Integer) SPUtils.get(this, "businessId", 0);
        isCustomerChange = getIntent().getBooleanExtra(ResultCode.IS_CUSTOMER_CHANGE, false);
        isTrading = getIntent().getBooleanExtra(ResultCode.ORDER_TARDING_ISTRAD, false);
        orderId = getIntent().getIntExtra(ResultCode.ORDER_TARDING_ORDERID, 0);
    }

    private void initViews() {
        width = ScreenUtils.getScreenWidth(this);
        id_but_sure = (Button) findViewById(R.id.id_but_sure);
        id_but_cancel = (Button) findViewById(R.id.id_but_cancel);
        id_tv_province = (TextView) findViewById(R.id.id_tv_province);
        id_tv_city = (TextView) findViewById(R.id.id_tv_city);
        id_whorlview = (WhorlView) findViewById(R.id.id_whorlview);
        id_et_phone = (EditText) findViewById(R.id.id_et_phone);
        id_et_name = (EditText) findViewById(R.id.id_et_name);
        id_et_phone.setPadding((int) (0.02 * width), 0, 0, 0);
        id_et_name.setPadding((int) (0.02 * width), 0, 0, 0);
        id_tv_city.setPadding((int) (0.02 * width), 0, 0, 0);
        id_tv_province.setPadding((int) (0.02 * width), 0, 0, 0);
    }
    private void createCustomer(){
        map.put("token", (String) SPUtils.get(this, "token", ""));
        map.put("businessId", String.valueOf(businessId));
        map.put("name", id_et_name.getText().toString().trim());
        map.put("phone", id_et_phone.getText().toString().trim());
        map.put("province", id_tv_province.getText().toString().trim());
        map.put("city", id_tv_city.getText().toString().trim());
        presenter.createCustomerInfo(map);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_but_cancel:
                finish();
                break;
            case R.id.id_but_sure:
                createCustomer();
                break;
            case R.id.id_tv_province:
                Intent intent_province = new Intent(this, AreasChangeAct.class);
                intent_province.putExtra(PCODE, 0);
                intent_province.putExtra(AreasChangeAct.ISUPDATECUSTOMER, false);
                //获取省份信息
                startActivityForResult(intent_province, KEYCODE_PROVINCE);
                break;
            case R.id.id_tv_city:
                String privancename = id_tv_province.getText().toString().trim();
                if (StringUtils.isNotEmpty(privancename)) {
                    //省份
                    if (SPUtils.get(this, PCODE_NAME, "").equals(privancename)) {
                        Intent intent_city = new Intent(this, AreasChangeAct.class);
                        int code = (Integer) SPUtils.get(this, PCODE, 0);
                        intent_city.putExtra(PCODE, code);
                        intent_city.putExtra(AreasChangeAct.ISUPDATECUSTOMER, false);
                        //获取城市信息
                        startActivityForResult(intent_city, KEYCODE_CITY);
                    } else {
                        Msg.showShort(this, "请重新选择省份");
                    }
                } else {
                    //
                    Msg.showShort(this, "请先选择省份");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        areasBean = (AreasBean) data.getSerializableExtra(AreasChangeAct.AREAS_NAME);
        switch (requestCode) {
            case KEYCODE_PROVINCE:
                id_tv_province.setText(areasBean.getName());
                break;
            case KEYCODE_CITY:
                id_tv_city.setText(areasBean.getName());
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
    public void ShowCreateCustomerSuccess(CustomerBean customerBean) {
        Msg.showShort(this, "创建用户成功");
        //创建用户
        if (isCustomerChange) {
            //购物车里新建客户

            Intent intent = new Intent(ResultCode.ACTION_CUSTOMER_CHANGE);
            intent.putExtra(ResultCode.CUSTOMER_NAME, customerBean.getName());
            intent.putExtra(ResultCode.CUSTOMER_Id, customerBean.getId());
            sendBroadcast(intent);
            if (null != ChangeCustomerAct.instance) {
                ChangeCustomerAct.instance.finish();
            }
        } else {
            if (isTrading) {
                //继续交易
                String token = (String) SPUtils.get(this, "token", "");
                orderPrestener.pendingTrad_CreateCustomer(token, orderId, customerBean.getId());

            } else {
                Msg.showShort(this, "创建用户成功");
                Intent intent = new Intent(this, CustomerDetailAct.class);
                intent.putExtra(CustomerDetailAct.CUTOMER_ID, customerBean.getId());
                startActivity(intent);
            }
        }
        if (null != AddCustomerAct.instance) {
            AddCustomerAct.instance.finish();
        }
        finish();
    }

    @Override
    public void showPendingTradSuccess() {
        Intent intent = new Intent();
        intent.setAction(ResultCode.ORDER_TARDING_ISTRADDATA);
        sendBroadcast(intent);
        if (null != AddCustomerAct.instance) {
            AddCustomerAct.instance.finish();
        }
        if (null != ChangeCustomerAct.instance) {
            ChangeCustomerAct.instance.finish();
        }
        finish();
    }

    @Override
    public void showPendingTradFail() {

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

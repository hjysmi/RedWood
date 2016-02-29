package com.onepiece.redwood.customer.detail;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.broadcast.BroadCastRelogin;
import com.onepiece.redwood.broadcast.IReLoginReceive;
import com.onepiece.redwood.customer.CustomerBean;
import com.onepiece.redwood.customer.update.CustomerUpdateAct;
import com.onepiece.redwood.login.LoginAct;
import com.onepiece.redwood.order.OrderPrestener;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;
import com.onepiece.redwood.order.orderdetail.ProductOrderDetailsBean;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.SPUtils;
import com.onepiece.redwood.utils.ScreenUtils;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户详情
 */
public class CustomerDetailAct extends Activity implements ICustomerDetailView, View.OnClickListener, AbsListView.OnScrollListener {
    public static final String CUTOMER_ID = "customerid";
    private ImageView id_iv_update;
    private Button but_back;
    private TextView tv_status, tv_time;
    private ListView lv;
    private TextView id_tv_name, id_tv_phone, id_tv_address, id_tv_contract, id_tv_date;
    private View ll_customerdel;
    private CustomerDetailPresenter presenter = new CustomerDetailPresenter(this);
    private OrderPrestener orderPrestener = new OrderPrestener(this);
    private int custimerId;
    private WhorlView id_whorlview;
    private Map<String, String> map = new HashMap<String, String>();
    private Map<String, String> map_order = new HashMap<String, String>();
    private CustomerBean customerBean;
    CustomerDetailAdapter adapter;
    List<OrderDetailBean> total_order = new ArrayList<OrderDetailBean>();
    List<ProductOrderDetailsBean> detailsList = new ArrayList<ProductOrderDetailsBean>();
    int firstVisibleItem = 0, totalItemCount = 0, visibleItemCount = 0;
    int width;
    int page = 1;
    int rows = 12;
    String token = "";
    private BroadCastRelogin broadCastRelogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerdetail);
        initViews();
        initValues();
        initEvents();
    }

    private void initEvents() {
        but_back.setOnClickListener(this);
        id_iv_update.setOnClickListener(this);
        lv.setOnScrollListener(this);
        /**重新登录*/
        broadCastRelogin.setiReLoginReceive(new IReLoginReceive() {
            @Override
            public void OnReceiceReLogin() {
                token = (String) SPUtils.get(CustomerDetailAct.this, "token", "");
                map.put("token", token);
                map.put("id", String.valueOf(custimerId));
                presenter.getCustomerInfoById(map);
            }
        });
    }

    private void initValues() {
        token = (String) SPUtils.get(this, "token", "");
        registerBroadcastReceiver();
        adapter = new CustomerDetailAdapter(this, R.layout.customerdetail_pro);
        custimerId = getIntent().getIntExtra(CUTOMER_ID, 0);
        map.put("token", token);
        map.put("id", String.valueOf(custimerId));
        presenter.getCustomerInfoById(map);
        //
    }

    private void initViews() {
        width = ScreenUtils.getScreenWidth(this);
        but_back = (Button) findViewById(R.id.but_back);
        id_iv_update = (ImageView) findViewById(R.id.id_iv_update);
        id_whorlview = (WhorlView) findViewById(R.id.id_whorlview);
        tv_status = (TextView) findViewById(R.id.tv_status);
        tv_time = (TextView) findViewById(R.id.tv_time);
        lv = (ListView) findViewById(R.id.lv);
        ll_customerdel = LayoutInflater.from(this).inflate(R.layout.customerdetail_customer, null);
        lv.addHeaderView(ll_customerdel);
        id_tv_name = (TextView) ll_customerdel.findViewById(R.id.id_tv_name);
        id_tv_phone = (TextView) ll_customerdel.findViewById(R.id.id_tv_phone);
        id_tv_address = (TextView) ll_customerdel.findViewById(R.id.id_tv_address);
        id_tv_contract = (TextView) ll_customerdel.findViewById(R.id.id_tv_contract);
        id_tv_date = (TextView) ll_customerdel.findViewById(R.id.id_tv_date);
        //  id_lv_customer = (PercentLinearLayout) findViewById(R.id.id_lv_customer);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_back:
                finish();
                break;
            case R.id.id_iv_update:
                Intent intent = new Intent(this, CustomerUpdateAct.class);
                intent.putExtra(CustomerUpdateAct.CUSTOMERNAME, customerBean.getName());
                intent.putExtra(CustomerUpdateAct.PROVINCENAME, customerBean.getProvince());
                intent.putExtra(CustomerUpdateAct.CITYNAME, customerBean.getCity());
                intent.putExtra(CustomerUpdateAct.PHONE, customerBean.getPhone());
                intent.putExtra(CustomerUpdateAct.COMPANY, customerBean.getCompany());
                intent.putExtra(CustomerUpdateAct.CUSTOMERID, customerBean.getId());
                startActivity(intent);
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
    public void ShowCustomerInfoSuccess(CustomerBean customerBean) {
        this.customerBean = customerBean;
        Logger.e(">>" + customerBean.toString());
        loadCustomerInfo(customerBean);
        //获取客户订单信息
        map_order.put("token", token);
        map_order.put("page", String.valueOf(page));
        map_order.put("rows", String.valueOf(rows));
        map_order.put("status", String.valueOf(0));
        map_order.put("productUserId", String.valueOf(custimerId));
        orderPrestener.getOrderInfoByproductUserId(map_order);

    }

    /**
     * 加载客户资料
     *
     * @param customerBean
     */
    private void loadCustomerInfo(CustomerBean customerBean) {
        id_tv_name.setText(customerBean.getName());
        id_tv_phone.setText(customerBean.getPhone());
        id_tv_address.setText(customerBean.getProvince() + customerBean.getCity());
        id_tv_contract.setText(customerBean.getCompany());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date parse = sdf.parse(customerBean.getCreateTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            int i = calendar.get(Calendar.DAY_OF_MONTH);
            int j = calendar.get(Calendar.MONTH);
            id_tv_date.setText((j + 1) + "-" + i);

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void showCustomerInfoFail() {

    }

    @Override
    public void ShowCustomerOrderInfoSuccess(List<OrderDetailBean> orderDetailBeanList) {

        total_order.addAll(orderDetailBeanList);
        detailsList = new ArrayList<ProductOrderDetailsBean>();
        for (OrderDetailBean odb : total_order) {
            List<ProductOrderDetailsBean> productOrderDetailsList = odb.getProductOrderDetailsList();
            for (ProductOrderDetailsBean pdb : productOrderDetailsList) {
                pdb.setOrderDetailBean(odb);
                detailsList.add(pdb);
            }
        }
        adapter.bindDatas(detailsList);
        if (page == 1) {
            lv.setAdapter(adapter);
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
        Msg.showShort(this, "请求失败");
    }

    @Override
    public void showReLogin() {
        Intent intent = new Intent(this, LoginAct.class);
        intent.putExtra(ResultCode.LOGIN_RELOGIN, true);
        startActivity(intent);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            if (firstVisibleItem >= totalItemCount - 10) {
                map.put("page", String.valueOf(page));
                presenter.getCustomerInfoById(map);
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

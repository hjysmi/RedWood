package com.onepiece.redwood.customer.contracts;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.broadcast.BroadCastRelogin;
import com.onepiece.redwood.broadcast.IReLoginReceive;
import com.onepiece.redwood.cart.ChangeCustomerAct;
import com.onepiece.redwood.customer.CustomerBean;
import com.onepiece.redwood.customer.add.AddCustomerAct;
import com.onepiece.redwood.customer.contracts.lib.CharacterParser;
import com.onepiece.redwood.customer.contracts.lib.ClearEditText;
import com.onepiece.redwood.customer.contracts.lib.PinyinComparator;
import com.onepiece.redwood.customer.contracts.lib.SideBar;
import com.onepiece.redwood.customer.contracts.lib.SortAdapter;
import com.onepiece.redwood.customer.contracts.lib.SortModel;
import com.onepiece.redwood.customer.create.CreateCustomerPresenter;
import com.onepiece.redwood.customer.create.ICreateCustomerView;
import com.onepiece.redwood.customer.detail.CustomerDetailAct;
import com.onepiece.redwood.login.LoginAct;
import com.onepiece.redwood.order.OrderPrestener;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.SPUtils;
import com.onepiece.redwood.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通讯录中获取客户资料(电话，姓名)
 */
public class ContractAct extends Activity implements ICreateCustomerView, IContractView, View.OnClickListener {
    private Button but_back;
    private ListView sortListView;
    private SideBar sideBar;
    //   private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    private WhorlView id_whorlview;
    private ContractPrestenter prestenter = new ContractPrestenter(this, this);
    private CreateCustomerPresenter presenter = new CreateCustomerPresenter(this);
    private OrderPrestener orderPrestener = new OrderPrestener(this);
    private Map<String, String> map = new HashMap<String, String>();
    int width;
    private Boolean isCustomerChange = false;
    private Integer orderId = 0;
    private Boolean isTrading = false;
    private BroadCastRelogin broadCastRelogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract);
        initViews();
        initValues();
        initEvents();
    }

    private void initEvents() {
        but_back.setOnClickListener(this);
        /**重新登录监听*/
        broadCastRelogin.setiReLoginReceive(new IReLoginReceive() {
            @Override
            public void OnReceiceReLogin() {
                Msg.showShort(ContractAct.this, "请重新创建客户信息");
            }
        });
    }

    private void initValues() {
        registerBroadcastReceiver();
        isCustomerChange = getIntent().getBooleanExtra(ResultCode.IS_CUSTOMER_CHANGE, false);
        isTrading = getIntent().getBooleanExtra(ResultCode.ORDER_TARDING_ISTRAD, false);
        orderId = getIntent().getIntExtra(ResultCode.ORDER_TARDING_ORDERID, 0);
        prestenter.getDataInfo();
    }

    private void initViews() {
        width = ScreenUtils.getScreenWidth(this);
        id_whorlview = (WhorlView) findViewById(R.id.id_whorlview);
        but_back = (Button) findViewById(R.id.but_back);
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        //dialog = (TextView) findViewById(R.id.dialog);
        // sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                map.put("token", (String) SPUtils.get(ContractAct.this, "token", ""));
                //  String businessId = (String) SPUtils.get(ContractAct.this, "businessId", "");
                Integer businessId = (Integer) SPUtils.get(ContractAct.this, "businessId", 0);
                map.put("businessId", String.valueOf(businessId));
                map.put("name", ((SortModel) adapter.getItem(position)).getName());
                map.put("phone", ((SortModel) adapter.getItem(position)).getPhoneNum());
                presenter.createCustomerInfo(map);
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象

//  Toast.makeText(getApplication(), ((SortModel) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
        mClearEditText.setPadding((int) (0.08 * width), 0, 0, 0);
        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_back:
                finish();
                break;
        }
    }


    @Override
    public void showDataSuccess(List<SortModel> SourceDateList) {
        // 根据a-z进行排序源数据
        this.SourceDateList = SourceDateList;
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);
    }

    @Override
    public void showDataFail() {
        Msg.showShort(this, "没有读取到联系人信息");
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
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
        if (isCustomerChange) {
            //购物车选择添加客户
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

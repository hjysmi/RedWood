package com.onepiece.redwood.customer;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.onepiece.redwood.BaseFragment;
import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.broadcast.BroadCastRelogin;
import com.onepiece.redwood.broadcast.IReLoginReceive;
import com.onepiece.redwood.customer.add.AddCustomerAct;
import com.onepiece.redwood.login.LoginAct;
import com.onepiece.redwood.utils.KeyBoardUtils;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.SPUtils;
import com.onepiece.redwood.utils.ScreenUtils;
import com.onepiece.redwood.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户管理
 */
public class CustomerFrag extends BaseFragment implements View.OnClickListener, ICustomerFragView, AbsListView.OnScrollListener {
    private View view_customerfrag;
    private ImageView id_iv_addcustomer;
    private TextView id_tv_strike;
    private TextView id_tv_nostrike;
    private EditText id_et_search;
    private ListView id_lv;
    private WhorlView id_whorlview;
    private CustomerFragPresenter presenter = new CustomerFragPresenter(this);
    private BroadCastRelogin broadCastRelogin;
    private Map<String, String> map = new HashMap<String, String>();
    private CustomerFragAdapter adapter;
    private Boolean isSearch = false;
    int firstVisibleItem = 0, totalItemCount = 0, visibleItemCount = 0;
    List<CustomerBean> total = new ArrayList<CustomerBean>();
    int width;
    String token;
    int page = 1;
    //count=0未成交 count=1已成交
    int count = 0;
    int rows = 12;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_customerfrag = inflater.inflate(R.layout.customerfrag, null, false);
        initViews();
        initValues();
        initEvent();
        return view_customerfrag;
    }

    private void initEvent() {
        id_iv_addcustomer.setOnClickListener(this);
        id_tv_strike.setOnClickListener(this);
        id_tv_nostrike.setOnClickListener(this);
        id_lv.setOnScrollListener(this);
        /**验证用户，重新登录监听*/
        broadCastRelogin.setiReLoginReceive(new IReLoginReceive() {
            @Override
            public void OnReceiceReLogin() {
                setTab(1);
            }
        });
        /**监听搜索事件*/
        id_et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String str = v.getText().toString().trim();
                if (StringUtils.isEmpty(str)) {
                    Msg.showShort(getActivity(), "搜索内容不能为空");
                } else {
                    isSearch = true;
                    setTab(1);

                }
                KeyBoardUtils.closeKeybord(id_et_search, getActivity());
                return true;
            }
        });
    }

    private void initValues() {
        adapter = new CustomerFragAdapter(getActivity(), R.layout.item_customerfrag);
        registerBroadcastReceiver();
        setTab(1);
    }

    /**
     * 初始化视图
     */
    private void initViews() {
        width = ScreenUtils.getScreenWidth(getActivity());
        id_iv_addcustomer = (ImageView) view_customerfrag.findViewById(R.id.id_iv_addcustomer);
        id_tv_strike = (TextView) view_customerfrag.findViewById(R.id.id_tv_strike);
        id_tv_nostrike = (TextView) view_customerfrag.findViewById(R.id.id_tv_nostrike);
        id_lv = (ListView) view_customerfrag.findViewById(R.id.id_lv);
        id_whorlview = (WhorlView) view_customerfrag.findViewById(R.id.id_whorlview);
        id_et_search = (EditText) view_customerfrag.findViewById(R.id.id_et_search);
        id_et_search.setPadding((int) (0.09 * width), 0, 0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_iv_back:
                Toast.makeText(getActivity(), "" + "返回产品", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_iv_addcustomer:
                Intent intent = new Intent(getActivity(), AddCustomerAct.class);
                getActivity().startActivity(intent);
                break;
            case R.id.id_tv_strike:
                setTab(1);
                break;
            case R.id.id_tv_nostrike:
                setTab(2);
                break;


        }
    }

    private void setTab(int i) {
        clearColor();
        map = new HashMap<String, String>();
        token = (String) SPUtils.get(getActivity(), "token", "");
        String content = id_et_search.getText().toString().trim();
        total = new ArrayList<CustomerBean>();
        page = 1;
        switch (i) {
            case 1:
                //已成交
                count = 0;
                id_tv_strike.setTextColor(Color.parseColor("#b40000"));
                map.put("token", token);
                map.put("page", String.valueOf(page));
                map.put("rows", String.valueOf(rows));
                map.put("count", String.valueOf(count));
                if (isSearch) {
                    map.put("q", content);
                }
                presenter.getCustomerInfo(map);
                break;
            case 2:
                //未成交
                count = 1;
                id_tv_nostrike.setTextColor(Color.parseColor("#b40000"));
                map.put("token", token);
                map.put("page", String.valueOf(page));
                map.put("rows", String.valueOf(rows));
                map.put("count", String.valueOf(count));
                if (isSearch) {
                    map.put("q", content);
                }
                presenter.getCustomerInfo(map);

                break;
        }

    }

    private void clearColor() {
        id_tv_strike.setTextColor(Color.parseColor("#555555"));
        id_tv_nostrike.setTextColor(Color.parseColor("#555555"));
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
    public void showCustomerListSuccess(List<CustomerBean> customerBeanList) {
        total.addAll(customerBeanList);
        adapter.bindDatas(total);
        if (page == 1) {
            id_lv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        page++;
    }


    @Override
    public void showPendingTradSuccess() {

    }

    @Override
    public void showPendingTradFail() {

    }

    @Override
    public void OnNetError() {
        Msg.showShort(getActivity(), "请连接网络");
    }

    @Override
    public void OnRequestFail() {
        Msg.showShort(getActivity(), "请求失败");
    }

    @Override
    public void showReLogin() {
        Intent intent = new Intent(getActivity(), LoginAct.class);
        intent.putExtra(ResultCode.LOGIN_RELOGIN, true);
        startActivity(intent);
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
        getActivity().unregisterReceiver(broadCastRelogin);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            if (firstVisibleItem >= totalItemCount - 10) {
                map.put("page", String.valueOf(page));
                presenter.getCustomerInfo(map);
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;
    }
}

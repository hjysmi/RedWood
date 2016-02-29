package com.onepiece.redwood.prolist;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.onepiece.redwood.R;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.utils.DensityUtils;
import com.onepiece.redwood.utils.KeyBoardUtils;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.SPUtils;
import com.onepiece.redwood.utils.ScreenUtils;
import com.onepiece.redwood.utils.StringUtils;
import com.orhanobut.logger.Logger;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/16.
 */
public class ProListAct extends Activity implements View.OnClickListener, IProListView, AbsListView.OnScrollListener, RadioGroup.OnCheckedChangeListener {
    public static String KEY_ID = "id";
    public static String KEY_PID = "pid";
    public static String KEY_SEARCH = "isSearch";
    public static String KEY_SEARCHCONTENT = "SearchContent";
    private Button id_but_cancel, id_but_sure, id_but_clear;
    private RadioButton id_but_price1, id_but_price2, id_but_price3, id_but_price4, id_but_material1, id_but_material2;
    private PercentRelativeLayout rl_material;
    private ImageView id_back;
    private TextView id_mult;
    private TextView id_sales;
    private TextView id_price;
    private PercentRelativeLayout id_filter;
    private TextView id_tv_filter;
    private ImageView id_iv_filter;
    private EditText id_et_search;
    private GridView id_gv;
    private WhorlView id_whorlview;
    private View contentView1;
    private RadioGroup radioGroup, id_radio_material;
    int id;
    int pid;
    int page = 1;
    int rows = 12;
    Integer businessId = 0;
    int width, height;
    Double price1, price2;
    String material;
    boolean isSearch;
    String searchContent;
    String orderBy = "create_time";
    String orderType = "desc";
    ProListAdapter proListAdapter;
    ProListPrestener prestener = new ProListPrestener(this);
    PopupWindow popupWindow;
    int firstVisibleItem = 0, totalItemCount = 0, visibleItemCount = 0;
    List<ProBean> list_proBean_total = new ArrayList<ProBean>();
    Map<String, String> map = new HashMap<String, String>();
    List<ProBean> total = new ArrayList<ProBean>();
    ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prolistact);
        //初始化View
        initViews();
        //数据
        initValues();
        initEvent();
    }

    /**
     * 搜索筛选
     */
    private void initEvent() {
        id_gv.setOnScrollListener(this);
        id_back.setOnClickListener(this);
        id_mult.setOnClickListener(this);
        id_sales.setOnClickListener(this);
        id_price.setOnClickListener(this);
        id_filter.setOnClickListener(this);
        id_et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String str = v.getText().toString().trim();
                KeyBoardUtils.closeKeybord(id_et_search, ProListAct.this);
                if (StringUtils.isEmpty(str)) {
                    Msg.showShort(ProListAct.this, "搜索内容不能为空");
                } else {
                    page = 1;
                    isSearch = true;
                    list_proBean_total = new ArrayList<ProBean>();
                    proListAdapter = new ProListAdapter(ProListAct.this, R.layout.item_prolistact);

                    Logger.e("url<<" + "search2");
                    setTab(businessId, pid, 1, str);
                }

                return true;
            }
        });
    }

    private void initValues() {
        imageLoader = ImageLoader.getInstance();
        businessId = (Integer) SPUtils.get(this, "businessId", 0);
        isSearch = getIntent().getBooleanExtra(KEY_SEARCH, false);
        searchContent = getIntent().getStringExtra(KEY_SEARCHCONTENT);
        id = getIntent().getIntExtra(KEY_ID, 0);
        pid = getIntent().getIntExtra(KEY_PID, 0);

        if (!isSearch) {//产品列表展示
            proListAdapter = new ProListAdapter(this, R.layout.item_prolistact);
            setTab(id, pid, 1, "");
        } else {//产品列表搜索
            proListAdapter = new ProListAdapter(this, R.layout.item_prolistact);
            setTab(id, pid, 1, searchContent);
        }

    }

    private void initViews() {
        width = ScreenUtils.getScreenWidth(this);
        id_back = (ImageView) findViewById(R.id.id_back);
        id_mult = (TextView) findViewById(R.id.id_mult);
        id_sales = (TextView) findViewById(R.id.id_sales);
        id_price = (TextView) findViewById(R.id.id_price);
        id_filter = (PercentRelativeLayout) findViewById(R.id.id_filter);
        id_tv_filter = (TextView) findViewById(R.id.id_tv_filter);
        id_iv_filter = (ImageView) findViewById(R.id.id_iv_filter);
        id_et_search = (EditText) findViewById(R.id.id_et_search);
        id_et_search.setPadding((int) (0.09 * width), 0, 0, 0);
        id_gv = (GridView) findViewById(R.id.id_gv);
        id_whorlview = (WhorlView) findViewById(R.id.id_whorlview);

        id_mult.setTextColor(Color.parseColor("#b40000"));
        width = ScreenUtils.getScreenWidth(this);
        height = ScreenUtils.getScreenHeight(this);
      //  id_gv.setOnScrollListener(new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_back:
                finish();
                break;
            case R.id.id_mult:
                setTab(id, pid, 1, "");
                break;
            case R.id.id_sales:
                //销量筛选
                setTab(id, pid, 2, "");
                break;
            case R.id.id_price:
                setTab(id, pid, 3, "");
                break;
            case R.id.id_filter:
                //筛选
                initpopupwindow(v);
                //    id_tv_filter.setTextColor(Color.parseColor("#b40000"));
                //   id_iv_filter.setImageResource(R.drawable.filter_checked);
                //   setTab(id, pid, 4, "");
                break;
            case R.id.id_but_cancel:
                popupWindow.dismiss();
                break;
            case R.id.id_but_sure:
                //获取选中的筛选数据
                getDataByFilter();
                popupWindow.dismiss();

                break;
            case R.id.id_but_clear:
                //清除筛选
                clearFilter();

                break;
        }
    }

    /**
     * 获取选中的筛选数据
     */
    private void getDataByFilter() {
        map = new HashMap<String, String>();
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton childAt = (RadioButton) radioGroup.getChildAt(i);
            if (childAt.isChecked()) {
                switch (childAt.getText().toString()) {
                    case "0-1万":
                        price1 = 0d;
                        price2 = 10000d;
                        break;
                    case "1-5万":
                        price1 = 10000d;
                        price2 = 50000d;
                        break;
                    case "5-10万":
                        price1 = 50000d;
                        price2 = 100000d;
                        break;
                    case "10万以上":
                        price1 = 100000d;
                        break;
                    default:
                        price1 = null;
                        price2 = null;
                        break;
                }
            }

        }
        for (int i = 0; i < id_radio_material.getChildCount(); i++) {
            RadioButton childAt = (RadioButton) id_radio_material.getChildAt(i);
            if (childAt.isChecked()) {
                material = childAt.getText().toString();
            }

        }
        page = 1;
        map.put("businessId", String.valueOf(businessId));
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(rows));
        if (StringUtils.isNotEmpty(material)) {
            map.put("material", material);
        }
        if (price1 != null) {
            map.put("price1", String.valueOf(price1));
        }
        if (price2 != null) {
            map.put("price2", String.valueOf(price2));
        }
        if (map.containsKey("material") || map.containsKey("price1") || map.containsKey("price2")) {
            Logger.e("url=>" + 9);
            list_proBean_total = new ArrayList<ProBean>();
            proListAdapter = new ProListAdapter(this, R.layout.item_prolistact);
            total = new ArrayList<ProBean>();
            prestener.getProListInfo(map);
        }
    }

    /**
     * 一键清除价格区间和材质
     */
    private void clearFilter() {
        price1 = null;
        price2 = null;
        material = null;
        radioGroup.clearCheck();
        id_radio_material.clearCheck();
    }


    /**
     * 打开筛选菜单
     *
     * @param parent
     */
    private void initpopupwindow(View parent) {

        if (popupWindow == null) {
            LayoutInflater inflater = LayoutInflater.from(this);
            contentView1 = inflater.inflate(R.layout.prolistact_filter, null);
            popupWindow = new PopupWindow(contentView1, ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, false);

        }
        id_but_cancel = (Button) contentView1.findViewById(R.id.id_but_cancel);
        id_but_sure = (Button) contentView1.findViewById(R.id.id_but_sure);
        id_but_clear = (Button) contentView1.findViewById(R.id.id_but_clear);
        rl_material = (PercentRelativeLayout) contentView1.findViewById(R.id.in_filter_material);
        radioGroup = (RadioGroup) contentView1.findViewById(R.id.id_radiogroup);
        id_radio_material = (RadioGroup) contentView1.findViewById(R.id.id_radio_material);
        id_but_material1 = (RadioButton) contentView1.findViewById(R.id.id_but_material1);
        id_but_material2 = (RadioButton) contentView1.findViewById(R.id.id_but_material2);
        id_but_price1 = (RadioButton) contentView1.findViewById(R.id.id_but_price1);
        id_but_price2 = (RadioButton) contentView1.findViewById(R.id.id_but_price2);
        id_but_price3 = (RadioButton) contentView1.findViewById(R.id.id_but_price3);
        id_but_price4 = (RadioButton) contentView1.findViewById(R.id.id_but_price4);
        int width_price1 = (int) (0.19 * width);
        int left_price1 = (int) (0.03 * width);
        int height_price1 = (int) (0.06 * width);
        int width_material1 = (int) (0.19 * width);
        int height_material1 = (int) (0.06 * width);
        int textsize = (int) DensityUtils.px2sp(this, (int) (0.03 * width));
        int textsize_material = (int) DensityUtils.px2sp(this, (int) (0.03 * width));
        RadioGroup.LayoutParams layoutParams_price1 = new RadioGroup.LayoutParams(width_price1, height_price1);
        layoutParams_price1.leftMargin = left_price1;
        id_but_price1.setLayoutParams(layoutParams_price1);
        RadioGroup.LayoutParams layoutParams_price2 = new RadioGroup.LayoutParams(width_price1, height_price1);
        layoutParams_price2.leftMargin = left_price1;
        id_but_price2.setLayoutParams(layoutParams_price2);
        RadioGroup.LayoutParams layoutParams_price3 = new RadioGroup.LayoutParams(width_price1, height_price1);
        layoutParams_price3.leftMargin = left_price1;
        id_but_price3.setLayoutParams(layoutParams_price3);
        RadioGroup.LayoutParams layoutParams_price4 = new RadioGroup.LayoutParams(width_price1, height_price1);
        layoutParams_price4.leftMargin = left_price1;
        id_but_price4.setLayoutParams(layoutParams_price4);
        id_but_price1.setTextSize(textsize);
        id_but_price2.setTextSize(textsize);
        id_but_price3.setTextSize(textsize);
        id_but_price4.setTextSize(textsize);
        //材质单选
        RadioGroup.LayoutParams layoutParams_material1 = new RadioGroup.LayoutParams(width_material1, height_material1);
        layoutParams_material1.leftMargin = left_price1;
        id_but_material1.setLayoutParams(layoutParams_material1);
        RadioGroup.LayoutParams layoutParams_material2 = new RadioGroup.LayoutParams(width_material1, height_material1);
        layoutParams_material2.leftMargin = left_price1;
        id_but_material2.setLayoutParams(layoutParams_material2);
        id_but_material1.setTextSize(textsize_material);
        id_but_material2.setTextSize(textsize_material);
        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(cd);
        WindowManager.LayoutParams wl = getWindow().getAttributes();
        wl.alpha = 0.6f;
        getWindow().setAttributes(wl);
        popupWindow.setClippingEnabled(false);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation((View) parent.getParent(), Gravity.RIGHT, 0, ScreenUtils.getStatusHeight(this));
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        id_but_cancel.setOnClickListener(this);
        id_but_sure.setOnClickListener(this);
        id_but_clear.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }


    /**
     * @param id   产品Id
     * @param pid  产品父节点Id
     * @param i
     * @param name 搜索内容
     */
    private void setTab(int id, int pid, int i, String name) {
        clearColor();
        switch (i) {
            case 1://综合排序
                total = new ArrayList<ProBean>();
                id_mult.setTextColor(Color.parseColor("#b40000"));
                if (!isSearch) {
                    //产品列表展示
                    orderBy = "create_time";
                    orderType = "desc";
                    map = new HashMap<String, String>();
                    map.put("businessId", String.valueOf(businessId));
                    if (pid != 0) {
                        map.put("parentId", String.valueOf(pid));
                        if (id != 0) {
                            map.put("childId", String.valueOf(id));
                        }
                    }
                    map.put("page", String.valueOf(page));
                    map.put("rows", String.valueOf(rows));
                    map.put("orderBy", String.valueOf(orderBy));
                    map.put("orderType", String.valueOf(orderType));
                    Logger.e("url=>" + 8);
                    prestener.getProListInfo(map);
                } else {
                    //产品搜索
                    page = 1;
                    map = new HashMap<String, String>();
                    map.put("businessId", String.valueOf(businessId));
                    map.put("page", String.valueOf(page));
                    map.put("rows", String.valueOf(rows));
                    map.put("q", name);
                    Logger.e("url=>" + 1);
                    prestener.getProListInfo(map);
                }

                break;
            case 2://销量排序
                total = new ArrayList<ProBean>();
                id_sales.setTextColor(Color.parseColor("#b40000"));
                page = 1;
                orderBy = "xl";
                orderType = "desc";
                map = new HashMap<String, String>();
                map.put("businessId", String.valueOf(businessId));
                map.put("page", String.valueOf(page));
                map.put("rows", String.valueOf(rows));
                map.put("orderBy", String.valueOf(orderBy));
                map.put("orderType", String.valueOf(orderType));
                if (pid != 0) {
                    map.put("parentId", String.valueOf(pid));
                    if (id != 0) {
                        map.put("childId", String.valueOf(id));
                    }
                }
                list_proBean_total = new ArrayList<ProBean>();
                proListAdapter = new ProListAdapter(this, R.layout.item_prolistact);
                prestener.getProListInfo(map);
                break;
            case 3://价格排序
                total = new ArrayList<ProBean>();
                id_price.setTextColor(Color.parseColor("#b40000"));
                page = 1;
                orderBy = "price";
                orderType = "desc";
                map = new HashMap<String, String>();
                map.put("businessId", String.valueOf(businessId));
                map.put("page", String.valueOf(page));
                map.put("rows", String.valueOf(rows));
                map.put("orderBy", String.valueOf(orderBy));
                map.put("orderType", String.valueOf(orderType));
                if (pid != 0) {
                    map.put("parentId", String.valueOf(pid));
                    if (id != 0) {
                        map.put("childId", String.valueOf(id));
                    }
                }
                list_proBean_total = new ArrayList<ProBean>();
                proListAdapter = new ProListAdapter(this, R.layout.item_prolistact);
                prestener.getProListInfo(map);
                break;

        }
    }


    /**
     * 清除选中的颜色
     */
    private void clearColor() {
        id_mult.setTextColor(Color.parseColor("#747474"));
        id_sales.setTextColor(Color.parseColor("#747474"));
        id_price.setTextColor(Color.parseColor("#747474"));
        id_tv_filter.setTextColor(Color.parseColor("#747474"));
        id_iv_filter.setImageResource(R.drawable.filter);
    }

    @Override
    public void showProListSuccess(List<ProBean> list_proBean) {
        total.addAll(list_proBean);
        proListAdapter.bindDatas(total);
        if (page == 1) {
            id_gv.setAdapter(proListAdapter);
        } else {
            proListAdapter.notifyDataSetChanged();
        }
        page++;
    }

    @Override
    public void showNetError() {
        Msg.showShort(this, "请连接网络");
    }

    @Override
    public void showRequestError() {
        Msg.showShort(this, "请求失败,请重试");
    }

    @Override
    public void showBar() {
        id_whorlview.setVisibility(View.VISIBLE);
        id_whorlview.start();
    }

    @Override
    public void hideBar() {
        id_whorlview.setVisibility(View.INVISIBLE);
        id_whorlview.stop();
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            if (firstVisibleItem >= totalItemCount - 10) {
                map.put("page", String.valueOf(page));
                if (isSearch) {
                    //产品搜索
                    Logger.e("url=>" + 5);
                    prestener.getProListInfo(map);
                    //    prestener.searchProListInfo(businessId, page, rows, searchContent);
                } else {
                    //产品展示
                    Logger.e("url=>" + 6);
                    prestener.getProListInfo(map);
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;

    }

    /**
     * 筛选价格区间
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {


    }
}

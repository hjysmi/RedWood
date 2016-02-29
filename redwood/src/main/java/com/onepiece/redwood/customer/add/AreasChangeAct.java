package com.onepiece.redwood.customer.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.onepiece.redwood.R;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.customer.create.CreateCustomerAct;
import com.onepiece.redwood.customer.update.CustomerUpdateAct;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.SPUtils;

import java.util.List;

/**
 * 地区选择
 */
public class AreasChangeAct extends Activity implements View.OnClickListener, IAreasChangeView {
    private ImageView id_iv_back;
    private ListView id_lv;
    private WhorlView id_whorlview;
    private AreasChangePresenter presenter = new AreasChangePresenter(this);
    int pcode = 0;
    private AreasChangeAdapter adapter;
    private List<AreasBean> areasBeanList;
    public final static String AREAS_NAME = "areasname";
    public final static String ISUPDATECUSTOMER = "updatecustomer";
    private boolean isUpdateCustomer = false;
    private int ChangeCount = 1;
    private String provincename;
    private String cityname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.areaschange);
        initViews();
        initValues();
        initEvents();
    }

    private void initEvents() {
        id_iv_back.setOnClickListener(this);
        id_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isUpdateCustomer) {
                    //修改客户资料时的客户选择
                    if (ChangeCount == 1) {
                        provincename = areasBeanList.get(position).getName();
                        ChangeCount = 2;
                        SPUtils.put(AreasChangeAct.this, CustomerUpdateAct.PROVINCENAME, provincename);
                        presenter.getAreas(areasBeanList.get(position).getCode());

                    } else if (ChangeCount == 2) {

                        cityname = areasBeanList.get(position).getName();
                        SPUtils.put(AreasChangeAct.this, CustomerUpdateAct.CITYNAME, cityname);
                        AreasChangeAct.this.setResult(CustomerUpdateAct.ADDRESSCODE);
                        finish();
                    }


                } else {
                    //新增客户资料时的客户选择
                    SPUtils.put(AreasChangeAct.this, CreateCustomerAct.PCODE, areasBeanList.get(position).getCode());
                    SPUtils.put(AreasChangeAct.this, CreateCustomerAct.PCODE_NAME, areasBeanList.get(position).getName());
                    Intent intent = new Intent();
                    intent.putExtra(AREAS_NAME, areasBeanList.get(position));
                    if (pcode == 0) {
                        AreasChangeAct.this.setResult(CreateCustomerAct.KEYCODE_PROVINCE, intent);
                    } else {
                        AreasChangeAct.this.setResult(CreateCustomerAct.KEYCODE_CITY, intent);
                    }
                    finish();
                }

            }
        });
    }

    private void initValues() {
        isUpdateCustomer = getIntent().getBooleanExtra(ISUPDATECUSTOMER, false);
        pcode = getIntent().getIntExtra(CreateCustomerAct.PCODE, 0);
        presenter.getAreas(pcode);
        SPUtils.remove(AreasChangeAct.this, CreateCustomerAct.PCODE);
        SPUtils.remove(AreasChangeAct.this, CreateCustomerAct.PCODE_NAME);
    }

    private void initViews() {
        id_iv_back = (ImageView) findViewById(R.id.id_iv_back);
        id_lv = (ListView) findViewById(R.id.id_lv);
        id_whorlview = (WhorlView) findViewById(R.id.id_whorlview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_iv_back:
                Intent intent = new Intent();
                intent.putExtra(AREAS_NAME, new AreasBean());
                AreasChangeAct.this.setResult(0, intent);
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
        id_whorlview.setVisibility(View.INVISIBLE);
        id_whorlview.stop();
    }

    @Override
    public void showAreasSuccess(List<AreasBean> areasBeanList) {
        this.areasBeanList = areasBeanList;
        adapter = new AreasChangeAdapter(this, R.layout.item_areaschange);
        adapter.bindDatas(areasBeanList);
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


}

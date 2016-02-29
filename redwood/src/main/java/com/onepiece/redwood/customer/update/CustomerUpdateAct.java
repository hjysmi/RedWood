package com.onepiece.redwood.customer.update;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.onepiece.redwood.R;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.customer.CustomerBean;
import com.onepiece.redwood.customer.add.AreasChangeAct;
import com.onepiece.redwood.customer.create.CreateCustomerAct;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.SPUtils;
import com.onepiece.redwood.utils.ScreenUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改客户资料
 */
public class CustomerUpdateAct extends Activity implements ICustomerUpdateView, View.OnClickListener {
    private Button but_back;
    private Button id_but_sure;
    private TextView id_tv_address;
    private WhorlView id_whorlview;
    private EditText id_et_name, id_et_phone, id_et_company;
    public static final String ADDRESSNAME = "addressname";
    public static final int ADDRESSCODE = 3;
    public static final String PROVINCENAME = "Provincename";
    public static final String CITYNAME = "cityname";
    public static final String PHONE = "phone";
    public static final String CUSTOMERNAME = "customername";
    public static final String COMPANY = "company";
    public static final String CUSTOMERID = "customerid";
    public String provincename, cityname;
    private int customerId;
    private int width;
    private CustomerBean customerBean;
    private CustomerUpdatePresenter presenter = new CustomerUpdatePresenter(this);
    private Map<String, String> map = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerupdate);
        initViews();
        initValues();
        initEvents();
    }

    private void initEvents() {
        but_back.setOnClickListener(this);
        id_but_sure.setOnClickListener(this);
        id_tv_address.setOnClickListener(this);
    }

    private void initValues() {
        customerId = getIntent().getIntExtra(CUSTOMERID, 0);
        id_et_name.setText(getIntent().getStringExtra(CUSTOMERNAME));
        id_et_phone.setText(getIntent().getStringExtra(PHONE));
        id_et_company.setText(getIntent().getStringExtra(COMPANY));
        id_tv_address.setText(getIntent().getStringExtra(PROVINCENAME) + getIntent().getStringExtra(CITYNAME));
    }

    private void initViews() {
        width = ScreenUtils.getScreenWidth(this);
        but_back = (Button) findViewById(R.id.but_back);
        id_but_sure = (Button) findViewById(R.id.id_but_sure);
        id_tv_address = (TextView) findViewById(R.id.id_tv_address);
        id_et_name = (EditText) findViewById(R.id.id_et_name);
        id_et_phone = (EditText) findViewById(R.id.id_et_phone);
        id_et_company = (EditText) findViewById(R.id.id_et_company);
        id_whorlview = (WhorlView) findViewById(R.id.id_whorlview);
        id_et_name.setPadding((int) (0.02 * width), 0, 0, 0);
        id_et_phone.setPadding((int) (0.02 * width), 0, 0, 0);
        id_et_company.setPadding((int) (0.02 * width), 0, 0, 0);
        id_tv_address.setPadding((int) (0.02 * width), 0, 0, 0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_back:
                finish();
                break;
            case R.id.id_but_sure:
                map.put("token", (String) SPUtils.get(this, "token", ""));
                map.put("name", id_et_name.getText().toString().trim());
                map.put("phone", id_et_phone.getText().toString().trim());
                map.put("company", id_et_company.getText().toString().trim());
                map.put("province", (String) SPUtils.get(this, CustomerUpdateAct.PROVINCENAME, ""));
                map.put("city", (String) SPUtils.get(this, CustomerUpdateAct.CITYNAME, ""));
                map.put("id", String.valueOf(customerId));
                presenter.updateCustomerInfo(map);

                break;
            case R.id.id_tv_address:
                //修改地址
                Intent intent = new Intent(this, AreasChangeAct.class);
                intent.putExtra(CreateCustomerAct.PCODE, 0);
                intent.putExtra(AreasChangeAct.ISUPDATECUSTOMER, true);
                startActivityForResult(intent, ADDRESSCODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADDRESSCODE:
                provincename = (String) SPUtils.get(this, PROVINCENAME, "");
                cityname = (String) SPUtils.get(this, CITYNAME, "");
                id_tv_address.setText(provincename + cityname);
                break;
            default:
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
    public void updateCustomerInfoSuccess() {
        finish();
    }

    @Override
    public void updateCustomerInfoFail() {
        Msg.showShort(this, "更新失败");
    }
}

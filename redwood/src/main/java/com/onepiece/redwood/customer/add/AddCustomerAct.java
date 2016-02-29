package com.onepiece.redwood.customer.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.customer.contracts.ContractAct;
import com.onepiece.redwood.customer.create.CreateCustomerAct;
import com.zhy.android.percent.support.PercentRelativeLayout;

/**
 * 选择添加客户资料的方式
 */
public class AddCustomerAct extends Activity implements View.OnClickListener {
    Button but_back;
    PercentRelativeLayout in_id_new, in_id_create;
    public static AddCustomerAct instance = null;
    private Boolean isCustomerChange = false;
    private Integer orderId = 0;
    private Boolean isTrading = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcustomer);
        initViews();
        initValues();
        initEvent();
    }

    private void initEvent() {
        but_back.setOnClickListener(this);
        in_id_new.setOnClickListener(this);
        in_id_create.setOnClickListener(this);
    }

    private void initValues() {
        instance = this;
        isCustomerChange = getIntent().getBooleanExtra(ResultCode.IS_CUSTOMER_CHANGE,false);
        isTrading = getIntent().getBooleanExtra(ResultCode.ORDER_TARDING_ISTRAD, false);
        orderId = getIntent().getIntExtra(ResultCode.ORDER_TARDING_ORDERID, 0);
    }

    private void initViews() {
        but_back = (Button) findViewById(R.id.but_back);
        in_id_new = (PercentRelativeLayout) findViewById(R.id.in_id_new);
        in_id_create = (PercentRelativeLayout) findViewById(R.id.in_id_create);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_back:
                finish();
                break;
            case R.id.in_id_new:
                //创建新客户
                Intent intent1 = new Intent(this, CreateCustomerAct.class);
                intent1.putExtra(ResultCode.IS_CUSTOMER_CHANGE,isCustomerChange);
                intent1.putExtra(ResultCode.ORDER_TARDING_ISTRAD,isTrading);
                intent1.putExtra(ResultCode.ORDER_TARDING_ORDERID,orderId);
                startActivity(intent1);
                break;
            case R.id.in_id_create:
                //从通讯录中读取
                Intent intent2 = new Intent(this, ContractAct.class);
                intent2.putExtra(ResultCode.IS_CUSTOMER_CHANGE,isCustomerChange);
                intent2.putExtra(ResultCode.ORDER_TARDING_ISTRAD,isTrading);
                intent2.putExtra(ResultCode.ORDER_TARDING_ORDERID,orderId);
                startActivity(intent2);
                break;
        }
    }
}

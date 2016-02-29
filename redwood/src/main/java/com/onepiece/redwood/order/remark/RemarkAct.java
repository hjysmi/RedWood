package com.onepiece.redwood.order.remark;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.broadcast.BroadCastRelogin;
import com.onepiece.redwood.broadcast.IReLoginReceive;
import com.onepiece.redwood.login.LoginAct;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.SPUtils;
import com.onepiece.redwood.utils.ScreenUtils;

/**
 * 备注
 */
public class RemarkAct extends Activity implements View.OnClickListener, IRemarkView {
    private Button but_back, id_but_sure, but_clear;
    private EditText et_content;
    private WhorlView id_whorlview;
    private int width;
    private String remark = "";
    private int orderId = 0;
    private RemarkPrestener prestener = new RemarkPrestener(this);
    private BroadCastRelogin broadCastRelogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remark);
        initViews();
        initValues();
        initEvents();
    }

    private void initEvents() {
        but_back.setOnClickListener(this);
        id_but_sure.setOnClickListener(this);
        but_clear.setOnClickListener(this);
        broadCastRelogin.setiReLoginReceive(new IReLoginReceive() {
            @Override
            public void OnReceiceReLogin() {
                String token = (String) SPUtils.get(RemarkAct.this, "token", "");
                prestener.updateRemark(token, orderId,et_content.getText().toString().trim());
            }
        });
    }

    private void initValues() {
        registerBroadcastReceiver();
        remark = getIntent().getStringExtra(ResultCode.REMARK_TAG);
        orderId = getIntent().getIntExtra(ResultCode.ORDER_TARDING_ORDERID,0);
        et_content.setText(remark);
    }

    private void initViews() {
        width = ScreenUtils.getScreenWidth(this);
        id_but_sure = (Button) findViewById(R.id.id_but_sure);
        but_back = (Button) findViewById(R.id.but_back);
        but_clear = (Button) findViewById(R.id.but_clear);
        et_content = (EditText) findViewById(R.id.et_content);
        id_whorlview = (WhorlView) findViewById(R.id.id_whorlview);
        et_content.setPadding((int) (0.03 * width), (int) (0.03 * width), (int) (0.03 * width), (int) (0.03 * width));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_back:
                finish();
                break;
            case R.id.but_clear:
                et_content.setText("");
                break;
            case R.id.id_but_sure:
                String token = (String) SPUtils.get(this, "token", "");
                prestener.updateRemark(token, orderId,et_content.getText().toString().trim());
                break;
        }
    }

    @Override
    public void UpDateRemarkSuccess() {
        Msg.showShort(this,"备注添加成功");
        finish();
    }

    @Override
    public void showNetError() {
        Msg.showShort(this,"请连接网络");
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
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadCastRelogin);
    }

}

package com.onepiece.redwood.self.update;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.onepiece.redwood.R;
import com.onepiece.redwood.ResultCode;
import com.onepiece.redwood.WhorlView;
import com.onepiece.redwood.login.LoginAct;
import com.onepiece.redwood.self.SelfPrestener;
import com.onepiece.redwood.self.UserBean;
import com.onepiece.redwood.utils.Msg;
import com.onepiece.redwood.utils.SPUtils;
import com.onepiece.redwood.utils.ScreenUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改用户资料
 */
public class UpdateUserAct extends Activity implements IUpdateUserView, View.OnClickListener {
    private TextView tv_name;
    private Button but_back;
    private Button but_save;
    private EditText et_value;
    private WhorlView id_whorlview;
    private String name, value, key;
    private int width;
    private Map<String, String> map;
    private SelfPrestener prestener = new SelfPrestener(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_update);
        initViews();
        initValues();
        initEvents();
    }

    private void initEvents() {
        but_back.setOnClickListener(this);
        but_save.setOnClickListener(this);
    }

    private void initValues() {
        name = getIntent().getStringExtra(ResultCode.USER_UPDATE_NAME);
        value = getIntent().getStringExtra(ResultCode.USER_UPDATE_VALUE);
        key = getIntent().getStringExtra(ResultCode.USER_UPDATE_KEY);
        tv_name.setText(name);
        et_value.setText(value);
        et_value.setSelection(value.length());
    }

    private void initViews() {
        width = ScreenUtils.getScreenWidth(this);
        tv_name = (TextView) findViewById(R.id.tv_name);
        but_back = (Button) findViewById(R.id.but_back);
        but_save = (Button) findViewById(R.id.but_save);
        et_value = (EditText) findViewById(R.id.et_value);
        id_whorlview = (WhorlView) findViewById(R.id.id_whorlview);
        et_value.setPadding((int) (0.05 * width), 0, 0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_back:
                finish();
                break;
            case R.id.but_save:
                map = new HashMap<String, String>();
                map.put("token", (String) SPUtils.get(this, "token", ""));
                map.put(key, et_value.getText().toString().trim());
                prestener.updateUserInfo(map);
                break;
        }
    }

    @Override
    public void hideBar() {
        id_whorlview.setVisibility(View.GONE);
        id_whorlview.stop();
    }

    @Override
    public void showBar() {
        id_whorlview.setVisibility(View.VISIBLE);
        id_whorlview.start();
    }

    @Override
    public void showUpdateSuccess(UserBean userBean) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(ResultCode.USER_UPDATE_RESULT, et_value.getText().toString().trim());
        bundle.putString(ResultCode.USER_UPDATE_RESULT_KEY, key);
        intent.putExtra(ResultCode.USER_UPDATE_RESULT_DATA, bundle);
        setResult(ResultCode.USER_UPDATE_RESULT_CODE, intent);
        finish();
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
        intent.putExtra(ResultCode.LOGIN_RELOGIN,true);
        startActivity(intent);
    }


}
